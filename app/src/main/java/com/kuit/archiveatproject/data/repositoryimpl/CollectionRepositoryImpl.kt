package com.kuit.archiveatproject.data.repositoryimpl

import com.kuit.archiveatproject.data.mapper.toEntity
import com.kuit.archiveatproject.data.service.ApiService
import com.kuit.archiveatproject.data.util.requireData
import com.kuit.archiveatproject.domain.entity.CollectionDetailsResult
import com.kuit.archiveatproject.domain.repository.CollectionRepository
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : CollectionRepository {

    override suspend fun getCollectionDetails(collectionId: Long): CollectionDetailsResult {
        val dto = apiService.getCollectionDetails(collectionId).requireData()
        return dto.toEntity()
    }
}