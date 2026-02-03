package com.kuit.archiveatproject.data.mapper

import com.kuit.archiveatproject.data.dto.response.explore.CategoryDto
import com.kuit.archiveatproject.data.dto.response.explore.ExploreInboxEditResponseDto
import com.kuit.archiveatproject.domain.entity.ExploreInboxEdit
import com.kuit.archiveatproject.domain.entity.ExploreInboxEditCurrent
import com.kuit.archiveatproject.domain.entity.ExploreInboxEditTopic
import com.kuit.archiveatproject.domain.entity.InboxCategory

fun ExploreInboxEditResponseDto.toEntity(): ExploreInboxEdit {
    return ExploreInboxEdit(
        current = ExploreInboxEditCurrent(
            userNewsletterId = current.userNewsletterId,
            categoryId = current.categoryId,
            topicId = current.topicId,
            memo = current.memo
        ),
        categories = categories.map { it.toEntityOrNull() }.filterNotNull(),
        topics = topics.map {
            ExploreInboxEditTopic(
                id = it.id,
                categoryId = it.categoryId,
                name = it.name
            )
        }
    )
}

/*
 * 서버가 {id:null, name:null} 같은 의미 없는 값 내려 주는 케이스 방어
 */
private fun CategoryDto.toEntityOrNull(): InboxCategory? {
    if (id == null && name == null) return null
    return InboxCategory(id = id, name = name)
}