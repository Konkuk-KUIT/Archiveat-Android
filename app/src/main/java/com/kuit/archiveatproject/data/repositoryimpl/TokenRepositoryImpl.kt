package com.kuit.archiveatproject.data.repositoryimpl

import com.kuit.archiveatproject.data.local.TokenLocalDataSource
import com.kuit.archiveatproject.di.ApplicationScope
import com.kuit.archiveatproject.domain.repository.TokenRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepositoryImpl @Inject constructor(
    private val local: TokenLocalDataSource,
    @ApplicationScope private val appScope: CoroutineScope,
) : TokenRepository {

    private val cachedAccessToken = MutableStateFlow<String?>(null)
    private val cachedRefreshToken = MutableStateFlow<String?>(null)

    override val accessTokenFlow: Flow<String?> = local.accessTokenFlow
    override val refreshTokenFlow: Flow<String?> = local.refreshTokenFlow

    init {
        // 앱 스코프에서 access token을 계속 캐싱
        appScope.launch {
            local.accessTokenFlow.collectLatest { token ->
                cachedAccessToken.value = token
            }
        }
        // 앱 스코프에서 refresh token을 계속 캐싱
        appScope.launch {
            local.refreshTokenFlow.collectLatest { token ->
                cachedRefreshToken.value = token
            }
        }
    }

    override fun getCachedAccessToken(): String? = cachedAccessToken.value
    override fun getCachedRefreshToken(): String? = cachedRefreshToken.value

    override suspend fun saveAccessToken(token: String) {
        local.saveAccessToken(token)
        cachedAccessToken.value = token // 즉시 반영
    }

    override suspend fun saveRefreshToken(token: String) {
        local.saveRefreshToken(token)
        cachedRefreshToken.value = token
    }

    override suspend fun clearAccessToken() {
        local.clearAccessToken()
        cachedAccessToken.value = null
    }

    override suspend fun clearRefreshToken() {
        local.clearRefreshToken()
        cachedRefreshToken.value = null
    }
}

/*
[RepositoryImpl/UseCase/VM/...]
로그인 성공했을 때
: tokenRepository.saveAccessToken(accessToken)
: refreshToken은 Set-Cookie를 RefreshTokenCookieInterceptor가 저장
로그아웃
: tokenRepository.clearAccessToken()
: tokenRepository.clearRefreshToken()
*/
