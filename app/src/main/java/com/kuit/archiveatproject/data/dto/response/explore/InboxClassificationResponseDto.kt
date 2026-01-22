package com.kuit.archiveatproject.data.dto.response.explore

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InboxClassificationResponseDto(
    @SerialName("userNewsletterId")
    val userNewsletterId: Long,
    @SerialName("newsletterId")
    val newsletterId: Long,
    @SerialName("category")
    val category: InboxClassificationCategoryDto? = null,
    @SerialName("topic")
    val topic: InboxClassificationTopicDto? = null,
    @SerialName("memo")
    val memo: String? = null,
    @SerialName("classificationConfirmedAt")
    val classificationConfirmedAt: String? = null, // "2026-01-18T14:22:10+09:00"
    @SerialName("modifiedAt")
    val modifiedAt: String? = null, // "2026-01-18T14:22:10+09:00"
)

@Serializable
data class InboxClassificationCategoryDto(
    @SerialName("id") val id: Long? = null,
    @SerialName("name") val name: String? = null,
)

@Serializable
data class InboxClassificationTopicDto(
    @SerialName("id") val id: Long? = null,
    @SerialName("name") val name: String? = null,
)
