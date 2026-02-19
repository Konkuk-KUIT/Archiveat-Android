package com.kuit.archiveatproject.domain.repository

interface UserRepository {
    suspend fun getNickname(): String
}
