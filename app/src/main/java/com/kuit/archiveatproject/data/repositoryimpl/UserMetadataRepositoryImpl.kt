package com.kuit.archiveatproject.data.repositoryimpl

import com.kuit.archiveatproject.data.mapper.toRequestDto
import com.kuit.archiveatproject.data.mapper.toEntity
import com.kuit.archiveatproject.data.service.ApiService
import com.kuit.archiveatproject.domain.entity.UserMetadataResult
import com.kuit.archiveatproject.domain.entity.UserMetadataSubmit
import com.kuit.archiveatproject.domain.repository.UserMetadataRepository
import javax.inject.Inject

class UserMetadataRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : UserMetadataRepository {

    override suspend fun getUserMetadata(): UserMetadataResult {
        return apiService.getUserMetadata().toEntity()
    }

    override suspend fun submitUserMetadata(request: UserMetadataSubmit): Unit {
        apiService.submitUserMetadata(request.toRequestDto())
    }
}