package com.kuit.archiveatproject.data.repositoryimpl

import com.kuit.archiveatproject.data.dto.request.InboxClassificationRequestDto
import com.kuit.archiveatproject.data.mapper.toEntity
import com.kuit.archiveatproject.data.service.ApiService
import com.kuit.archiveatproject.data.util.requireData
import com.kuit.archiveatproject.data.util.requireSuccess
import com.kuit.archiveatproject.domain.entity.ExploreInboxEdit
import com.kuit.archiveatproject.domain.entity.InboxClassificationResult
import com.kuit.archiveatproject.domain.repository.InboxClassificationRepository
import javax.inject.Inject

class InboxClassificationRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : InboxClassificationRepository {

    override suspend fun patchInboxClassification(
        userNewsletterId: Long,
        categoryId: Long,
        topicId: Long,
        memo: String,
    ): InboxClassificationResult {
        val dto = apiService.patchInboxClassification(
            userNewsletterId = userNewsletterId,
            body = InboxClassificationRequestDto(
                categoryId = categoryId,
                topicId = topicId,
                memo = memo,
            )
        ).requireData()

        return dto.toEntity()
    }

    override suspend fun getExploreInboxEdit(userNewsletterId: Long): ExploreInboxEdit {
        val res = apiService.getExploreInboxEdit(userNewsletterId)
        return res.requireData().toEntity()
    }

    override suspend fun confirmExploreInboxAll() {
        val res = apiService.confirmExploreInboxAll()
        // data가 null이므로 requireData() X
        res.requireSuccess()
    }
}