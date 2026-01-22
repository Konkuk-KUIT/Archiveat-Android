package com.kuit.archiveatproject.data.repositoryimpl

import com.kuit.archiveatproject.data.mapper.toUserMetadataEntity
import com.kuit.archiveatproject.data.service.ApiService
import com.kuit.archiveatproject.domain.entity.UserMetadataResult
import com.kuit.archiveatproject.domain.repository.UserMetadataRepository
import javax.inject.Inject

class UserMetadataRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : UserMetadataRepository {

    override suspend fun getUserMetadata(): UserMetadataResult {
        return apiService.getUserMetadata().toUserMetadataEntity()
    }
}