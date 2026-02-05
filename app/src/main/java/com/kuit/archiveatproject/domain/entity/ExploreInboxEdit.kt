package com.kuit.archiveatproject.domain.entity

data class ExploreInboxEdit(
    val current: ExploreInboxEditCurrent,
    val categories: List<InboxCategory>, // 재사용
    val topics: List<ExploreInboxEditTopic>,
)

data class ExploreInboxEditCurrent(
    val userNewsletterId: Long,
    val categoryId: Long?,
    val topicId: Long?,
    val memo: String?,
)

data class ExploreInboxEditTopic(
    val id: Long,
    val categoryId: Long,
    val name: String,
)