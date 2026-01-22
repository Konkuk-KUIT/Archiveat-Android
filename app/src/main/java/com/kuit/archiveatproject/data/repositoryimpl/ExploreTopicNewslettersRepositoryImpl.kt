package com.kuit.archiveatproject.data.repositoryimpl

import com.kuit.archiveatproject.data.mapper.toTopicNewslettersEntity
import com.kuit.archiveatproject.data.service.ApiService
import com.kuit.archiveatproject.domain.entity.ExploreTopicNewsletters
import com.kuit.archiveatproject.domain.repository.ExploreTopicNewslettersRepository
import javax.inject.Inject

class ExploreTopicNewslettersRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : ExploreTopicNewslettersRepository {

    override suspend fun getTopicUserNewsletters(
        topicId: Long,
        page: Int,
        size: Int,
    ): ExploreTopicNewsletters {
        return apiService.getTopicUserNewsletters(
            topicId = topicId,
            page = page,
            size = size
        ).toTopicNewslettersEntity()
    }
}