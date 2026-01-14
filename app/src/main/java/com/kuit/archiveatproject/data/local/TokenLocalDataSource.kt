package com.kuit.archiveatproject.data.local

import kotlinx.coroutines.flow.Flow

interface TokenLocalDataSource {
    val accessTokenFlow: Flow<String?>
    suspend fun saveAccessToken(token: String)
    suspend fun clearAccessToken()
}