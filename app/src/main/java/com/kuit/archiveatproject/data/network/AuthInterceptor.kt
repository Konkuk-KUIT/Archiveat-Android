package com.kuit.archiveatproject.data.network

import com.kuit.archiveatproject.domain.repository.TokenRepository
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val tokenRepository: TokenRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val token = tokenRepository.getCachedAccessToken()
        val builder = original.newBuilder()

        if (!token.isNullOrBlank()) {
            builder.addHeader("Authorization", "Bearer $token")
        }

        return chain.proceed(builder.build())
    }
}