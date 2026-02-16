package com.kuit.archiveatproject.data.network

import com.kuit.archiveatproject.data.service.ReissueApiService
import com.kuit.archiveatproject.data.util.requireData
import com.kuit.archiveatproject.domain.repository.TokenRepository
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthAuthenticator @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val reissueApiService: ReissueApiService,
) : Authenticator {

    // 동시 401 다발 시 reissue를 한 번만 수행하도록 직렬화(한 번에 하나씩만 실행)
    private val refreshMutex = Mutex()

    override fun authenticate(route: Route?, response: Response): Request? {
        // reissue 자체가 실패한 경우에는 재시도 루프를 막음
        val path = response.request.url.encodedPath.removeSuffix("/")
        if (path.endsWith("/auth/reissue")) return null
        // 최대 1회만 재시도 (원요청 + 재시도 요청)
        if (responseCount(response) >= 2) return null

        val requestToken = response.request.authBearerToken()
        val latestToken = tokenRepository.getCachedAccessToken()

        // 이미 다른 요청이 토큰 갱신을 끝낸 경우, 그 토큰으로 즉시 재시도
        if (!latestToken.isNullOrBlank() && requestToken != latestToken) {
            return response.request.withBearerToken(latestToken)
        }

        return runBlocking {
            refreshMutex.withLock {
                val refreshedToken = tokenRepository.getCachedAccessToken()
                if (!refreshedToken.isNullOrBlank() && requestToken != refreshedToken) {
                    return@withLock response.request.withBearerToken(refreshedToken)
                }

                val refreshToken = tokenRepository.getCachedRefreshToken()
                if (refreshToken.isNullOrBlank()) {
                    // 재발급 근거가 없으면 인증 상태 종료
                    clearTokens()
                    return@withLock null
                }

                val newAccessToken = runCatching {
                    // 서버 스펙: Cookie 헤더로 refreshToken 전달
                    reissueApiService.reissue(cookie = "refreshToken=$refreshToken")
                        .requireData()
                        .accessToken
                }.getOrNull()

                if (newAccessToken.isNullOrBlank()) {
                    // reissue 실패 시 세션 만료로 간주
                    clearTokens()
                    return@withLock null
                }

                tokenRepository.saveAccessToken(newAccessToken)
                // 원 요청을 새 access token으로 1회 재시도
                return@withLock response.request.withBearerToken(newAccessToken)
            }
        }
    }

    private suspend fun clearTokens() {
        tokenRepository.clearAccessToken()
        tokenRepository.clearRefreshToken()
    }

    private fun Request.authBearerToken(): String? =
        header("Authorization")
            ?.removePrefix("Bearer ")
            ?.trim()
            ?.takeIf { it.isNotEmpty() }

    private fun Request.withBearerToken(token: String): Request =
        newBuilder()
            .header("Authorization", "Bearer $token")
            .build()

    private fun responseCount(response: Response): Int {
        var count = 1
        var prior = response.priorResponse
        while (prior != null) {
            count++
            prior = prior.priorResponse
        }
        return count
    }
}
