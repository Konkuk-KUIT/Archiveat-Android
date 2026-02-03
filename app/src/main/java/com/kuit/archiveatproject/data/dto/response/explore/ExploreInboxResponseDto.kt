package com.kuit.archiveatproject.data.dto.response.explore

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExploreInboxResponseDto(
    @SerialName("inbox")
    val inbox: List<InboxDateGroupDto> = emptyList(),
)

@Serializable
data class InboxDateGroupDto(
    @SerialName("date")
    val date: String, // "2026-01-18"
    @SerialName("items")
    val items: List<InboxItemDto> = emptyList(),
)

@Serializable
data class InboxItemDto(
    @SerialName("userNewsletterId")
    val userNewsletterId: Long, // 345
    @SerialName("llmStatus")
    val llmStatus: LlmStatusDto,
    @SerialName("contentUrl")
    val contentUrl: String? = null,

    @SerialName("title")
    val title: String? = null,

    @SerialName("domainName")
    val domainName: String? = null, // google
    @SerialName("createdAt")
    val createdAt: String? = null, // "2026-01-18T09:12:33+09:00"
    @SerialName("category")
    val category: CategoryDto? = null,
    @SerialName("topic")
    val topic: TopicDto? = null,
)

@Serializable
data class CategoryDto(
    @SerialName("id") val id: Long? = null, // 1
    @SerialName("name") val name: String? = null, // "IT/과학"
)

@Serializable
data class TopicDto(
    @SerialName("id") val id: Long? = null, // 1
    @SerialName("name") val name: String? = null, // AI
)

// ExploreInboxResponseDto 에서도 같이 사용
// NewsletterSaveResponseDto 에서도 같이 사용
@Serializable
enum class LlmStatusDto {
    @SerialName("PENDING") PENDING,
    @SerialName("RUNNING") RUNNING,
    @SerialName("DONE") DONE,
    @SerialName("FAILED") FAILED,
}