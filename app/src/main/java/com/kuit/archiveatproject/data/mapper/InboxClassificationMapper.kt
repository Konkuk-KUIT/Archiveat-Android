package com.kuit.archiveatproject.data.mapper

import com.kuit.archiveatproject.data.dto.response.explore.InboxClassificationCategoryDto
import com.kuit.archiveatproject.data.dto.response.explore.InboxClassificationResponseDto
import com.kuit.archiveatproject.data.dto.response.explore.InboxClassificationTopicDto
import com.kuit.archiveatproject.domain.entity.InboxClassificationCategory
import com.kuit.archiveatproject.domain.entity.InboxClassificationResult
import com.kuit.archiveatproject.domain.entity.InboxClassificationTopic

fun InboxClassificationResponseDto.toEntity(): InboxClassificationResult {
    return InboxClassificationResult(
        userNewsletterId = userNewsletterId,
        newsletterId = newsletterId,
        category = category.toEntityOrNull(),
        topic = topic.toEntityOrNull(),
        memo = memo,
        classificationConfirmedAt = classificationConfirmedAt,
        modifiedAt = modifiedAt,
    )
}

private fun InboxClassificationCategoryDto?.toEntityOrNull(): InboxClassificationCategory? {
    val dto = this ?: return null
    if (dto.id == null && dto.name == null) return null
    return InboxClassificationCategory(id = dto.id, name = dto.name)
}

private fun InboxClassificationTopicDto?.toEntityOrNull(): InboxClassificationTopic? {
    val dto = this ?: return null
    if (dto.id == null && dto.name == null) return null
    return InboxClassificationTopic(id = dto.id, name = dto.name)
}