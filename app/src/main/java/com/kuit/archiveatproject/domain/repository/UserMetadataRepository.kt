package com.kuit.archiveatproject.domain.repository

import com.kuit.archiveatproject.domain.entity.UserMetadataResult
import com.kuit.archiveatproject.domain.entity.UserMetadataSubmit

interface UserMetadataRepository {
    suspend fun getUserMetadata(): UserMetadataResult

    suspend fun submitUserMetadata(request: UserMetadataSubmit): Result<Unit>
}