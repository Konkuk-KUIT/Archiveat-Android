package com.kuit.archiveatproject.domain.entity

data class ExploreTopicNewsletters(
    val topicId: Long,
    val topicName: String,
    val hasNext: Boolean,
    val newsletters: List<ExploreTopicNewsletterItem>,
)

data class ExploreTopicNewsletterItem(
    val userNewsletterId: Long,
    val title: String,
    val thumbnailUrl: String?, // null -> 기본 이미지
    val domainName: String?,
    val isRead: Boolean,
    val createdAt: String?,
)