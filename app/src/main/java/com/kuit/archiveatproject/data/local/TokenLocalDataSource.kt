package com.kuit.archiveatproject.data.local

import kotlinx.coroutines.flow.Flow

interface TokenLocalDataSource {
    val accessTokenFlow: Flow<String?>
    val refreshTokenFlow: Flow<String?>
    suspend fun saveAccessToken(token: String)
    suspend fun saveRefreshToken(token: String)
    suspend fun clearAccessToken()
    suspend fun clearRefreshToken()
}
