package com.kuit.archiveatproject.data.dto.response.explore

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExploreTopicNewslettersResponseDto(
    @SerialName("topicId")
    val topicId: Long, // 1
    @SerialName("topicName")
    val topicName: String, // "테크 트랜드"
    @SerialName("hasNext")
    val hasNext: Boolean,
    @SerialName("newsletters")
    val newsletters: List<ExploreTopicNewsletterDto> = emptyList(),
)

@Serializable
data class ExploreTopicNewsletterDto(
    @SerialName("userNewsletterId")
    val userNewsletterId: Long, // 101
    @SerialName("title")
    val title: String, // "엔비디아의 새로운 칩 발표..."
    @SerialName("thumbnailUrl")
    val thumbnailUrl: String? = null, // "https://image.url/..."
    @SerialName("domainName")
    val domainName: String? = null,
    @SerialName("isRead")
    val isRead: Boolean,
    @SerialName("createdAt")
    val createdAt: String? = null, // "2024-01-18T10:30:00" // nullable
)