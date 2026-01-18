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

    private val cachedToken = MutableStateFlow<String?>(null)

    override val accessTokenFlow: Flow<String?> = local.accessTokenFlow

    init {
        // 앱 스코프에서 토큰을 계속 캐싱
        appScope.launch {
            local.accessTokenFlow.collectLatest { token ->
                cachedToken.value = token
            }
        }
    }

    override fun getCachedAccessToken(): String? = cachedToken.value

    override suspend fun saveAccessToken(token: String) {
        local.saveAccessToken(token)
        cachedToken.value = token // 즉시 반영
    }

    override suspend fun clearAccessToken() {
        local.clearAccessToken()
        cachedToken.value = null
    }
}

/*
[RepositoryImpl/UseCase/VM/...]
로그인 성공했을 때
: tokenRepository.saveAccessToken(accessToken)
로그아웃
: tokenRepository.clearAccessToken()
*/