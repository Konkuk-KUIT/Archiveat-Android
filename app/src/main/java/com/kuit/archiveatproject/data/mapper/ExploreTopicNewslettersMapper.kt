package com.kuit.archiveatproject.data.mapper

import com.kuit.archiveatproject.data.dto.response.explore.ExploreTopicNewslettersResponseDto
import com.kuit.archiveatproject.domain.entity.ExploreTopicNewsletterItem
import com.kuit.archiveatproject.domain.entity.ExploreTopicNewsletters

fun ExploreTopicNewslettersResponseDto.toEntity(): ExploreTopicNewsletters {
    return ExploreTopicNewsletters(
        topicId = topicId,
        topicName = topicName,
        hasNext = hasNext,
        newsletters = newsletters.map { it.toEntity() }
    )
}

private fun com.kuit.archiveatproject.data.dto.response.explore.ExploreTopicNewsletterDto.toEntity(): ExploreTopicNewsletterItem {
    return ExploreTopicNewsletterItem(
        userNewsletterId = userNewsletterId,
        title = title,
        thumbnailUrl = thumbnailUrl?.trim()?.takeIf { it.isNotBlank() },
        isRead = isRead,
        createdAt = createdAt,
    )
}