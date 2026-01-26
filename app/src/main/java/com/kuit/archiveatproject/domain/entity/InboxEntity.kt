package com.kuit.archiveatproject.domain.entity

data class Inbox(
    val inbox: List<InboxDateGroup>,
)

data class InboxDateGroup(
    val date: String,
    val count: Int,
    val items: List<InboxItem>,
)

data class InboxItem(
    val newsletterId: Long,
    val llmStatus: LlmStatus,
    val contentUrl: String?,

    val title: String?,

    val domainName: String?,
    val createdAt: String?,
    val category: InboxCategory?,
    val topic: InboxTopic?,
)

data class InboxCategory(
    val id: Long?,
    val name: String?,
)

data class InboxTopic(
    val id: Long?,
    val name: String?,
)

enum class LlmStatus {
    PENDING, RUNNING, DONE, FAILED
}