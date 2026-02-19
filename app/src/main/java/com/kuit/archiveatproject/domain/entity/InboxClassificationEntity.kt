package com.kuit.archiveatproject.domain.entity

data class InboxClassificationResult(
    val userNewsletterId: Long,
    val newsletterId: Long,
    val category: InboxClassificationCategory?,
    val topic: InboxClassificationTopic?,
    val memo: String?,
    val classificationConfirmedAt: String?,
    val modifiedAt: String?,
)

data class InboxClassificationCategory(
    val id: Long?,
    val name: String?,
)

data class InboxClassificationTopic(
    val id: Long?,
    val name: String?,
)