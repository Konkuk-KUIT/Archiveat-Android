package com.kuit.archiveatproject.domain.repository

interface AuthRepository {
    suspend fun signup(email: String, password: String, nickname: String): String
    suspend fun login(email: String, password: String): String
    suspend fun logout()
    suspend fun checkEmail(email: String): Boolean
    // MVP에서 안 씀
    suspend fun reissue(): String
}