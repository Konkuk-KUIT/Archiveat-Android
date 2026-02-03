package com.kuit.archiveatproject.data.mapper

import com.kuit.archiveatproject.data.dto.response.explore.CategoryDto
import com.kuit.archiveatproject.data.dto.response.explore.ExploreInboxResponseDto
import com.kuit.archiveatproject.data.dto.response.explore.InboxDateGroupDto
import com.kuit.archiveatproject.data.dto.response.explore.InboxItemDto
import com.kuit.archiveatproject.data.dto.response.explore.LlmStatusDto
import com.kuit.archiveatproject.data.dto.response.explore.TopicDto
import com.kuit.archiveatproject.domain.entity.Inbox
import com.kuit.archiveatproject.domain.entity.InboxCategory
import com.kuit.archiveatproject.domain.entity.InboxDateGroup
import com.kuit.archiveatproject.domain.entity.InboxItem
import com.kuit.archiveatproject.domain.entity.InboxTopic
import com.kuit.archiveatproject.domain.entity.LlmStatus

fun ExploreInboxResponseDto.toEntity(): Inbox {
    return Inbox(
        inbox = inbox.map { it.toEntity() }
    )
}

private fun InboxDateGroupDto.toEntity(): InboxDateGroup {
    return InboxDateGroup(
        date = date,
        items = items.map { it.toEntity() }
    )
}

private fun InboxItemDto.toEntity(): InboxItem {
    return InboxItem(
        userNewsletterId = userNewsletterId,
        llmStatus = llmStatus.toEntity(),
        contentUrl = contentUrl,

        title = title,

        domainName = domainName,
        createdAt = createdAt,
        category = category.toEntityOrNull(),
        topic = topic.toEntityOrNull(),
    )
}

/**
 * 서버가 category/topic을 객체로 주되 {id:null, name:null}로 내려주는 케이스 방어
 * -> 의미 없는 값이면 null로 정리(UI 분기 꼬임 방지)
 */
private fun CategoryDto?.toEntityOrNull(): InboxCategory? {
    val dto = this ?: return null
    if (dto.id == null && dto.name == null) return null
    return InboxCategory(id = dto.id, name = dto.name)
}

private fun TopicDto?.toEntityOrNull(): InboxTopic? {
    val dto = this ?: return null
    if (dto.id == null && dto.name == null) return null
    return InboxTopic(id = dto.id, name = dto.name)
}

// ExploreMapper 에서도 함께 사용
fun LlmStatusDto.toEntity(): LlmStatus = when (this) {
    LlmStatusDto.PENDING -> LlmStatus.PENDING
    LlmStatusDto.RUNNING -> LlmStatus.RUNNING
    LlmStatusDto.DONE -> LlmStatus.DONE
    LlmStatusDto.FAILED -> LlmStatus.FAILED
}