package com.kuit.archiveatproject.domain.repository

import kotlinx.coroutines.flow.Flow

interface TokenRepository {

    /** 화면/VM에서 관찰용 */
    val accessTokenFlow: Flow<String?>
    val refreshTokenFlow: Flow<String?>

    /** Interceptor에서 즉시 읽기용 (동기) */
    fun getCachedAccessToken(): String?
    fun getCachedRefreshToken(): String?

    /** 로그인 성공 후 저장 */
    suspend fun saveAccessToken(token: String)
    suspend fun saveRefreshToken(token: String)

    /** 로그아웃 등 */
    suspend fun clearAccessToken()
    suspend fun clearRefreshToken()
}
