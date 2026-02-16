package com.kuit.archiveatproject.data.network

import com.kuit.archiveatproject.domain.repository.TokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefreshTokenCookieInterceptor @Inject constructor(
    private val tokenRepository: TokenRepository,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        // 로그인/재발급 응답의 Set-Cookie에서 refreshToken을 추출해 로컬에 반영
        val parsed = parseRefreshToken(response.headers.values("Set-Cookie"))
        if (parsed.found) {
            runBlocking {
                if (parsed.value.isNullOrBlank()) {
                    // 서버가 refreshToken 만료/삭제 쿠키를 보낸 경우
                    tokenRepository.clearRefreshToken()
                } else {
                    tokenRepository.saveRefreshToken(parsed.value)
                }
            }
        }
        return response
    }

    private fun parseRefreshToken(setCookies: List<String>): ParsedRefreshToken {
        setCookies.forEach { cookie ->
            // Set-Cookie: refreshToken=...; Path=/; HttpOnly ...
            val firstPair = cookie.substringBefore(";")
            val key = firstPair.substringBefore("=").trim()
            if (!key.equals("refreshToken", ignoreCase = true)) return@forEach

            val value = firstPair.substringAfter("=", missingDelimiterValue = "").trim()
            val unquoted = value.removeSurrounding("\"")
            return ParsedRefreshToken(found = true, value = unquoted)
        }
        return ParsedRefreshToken(found = false, value = null)
    }

    private data class ParsedRefreshToken(
        val found: Boolean,
        val value: String?,
    )
}
