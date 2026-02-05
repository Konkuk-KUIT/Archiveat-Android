package com.kuit.archiveatproject.domain.repository

import com.kuit.archiveatproject.domain.entity.ExploreInboxEdit
import com.kuit.archiveatproject.domain.entity.InboxClassificationResult

interface InboxClassificationRepository {
    suspend fun patchInboxClassification(
        userNewsletterId: Long,
        categoryId: Long,
        topicId: Long,
        memo: String,
    ): InboxClassificationResult

    suspend fun getExploreInboxEdit(userNewsletterId: Long): ExploreInboxEdit

    suspend fun confirmExploreInboxAll()
}