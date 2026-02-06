package com.kuit.archiveatproject.domain.repository

import com.kuit.archiveatproject.domain.entity.ExploreTopicNewsletters

interface ExploreTopicNewslettersRepository {
    suspend fun getTopicUserNewsletters(
        topicId: Long,
        page: Int = 0,
        size: Int = 20,
    ): ExploreTopicNewsletters
}