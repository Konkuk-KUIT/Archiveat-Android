package com.kuit.archiveatproject.domain.repository

import com.kuit.archiveatproject.domain.entity.UserMetadataResult

interface UserMetadataRepository {
    suspend fun getUserMetadata(): UserMetadataResult
}