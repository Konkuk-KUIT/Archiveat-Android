package com.kuit.archiveatproject.domain.repository

import com.kuit.archiveatproject.domain.entity.CollectionDetailsResult

interface CollectionRepository {
    suspend fun getCollectionDetails(collectionId: Long): CollectionDetailsResult
}