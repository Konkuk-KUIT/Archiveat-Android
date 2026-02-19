package com.kuit.archiveatproject.data.dto.response.explore

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExploreInboxEditResponseDto(
    @SerialName("current")
    val current: ExploreInboxEditCurrentDto,
    @SerialName("categories")
    val categories: List<CategoryDto> = emptyList(), // 그대로 재사용
    @SerialName("topics")
    val topics: List<ExploreInboxEditTopicDto> = emptyList(),
)

@Serializable
data class ExploreInboxEditCurrentDto(
    @SerialName("userNewsletterId")
    val userNewsletterId: Long,
    @SerialName("categoryId")
    val categoryId: Long?,   // 서버가 null 줄 가능성 대비
    @SerialName("topicId")
    val topicId: Long?,      // 서버가 null 줄 가능성 대비
    @SerialName("memo")
    val memo: String? = null,
)

@Serializable
data class ExploreInboxEditTopicDto(
    @SerialName("id")
    val id: Long,
    @SerialName("categoryId")
    val categoryId: Long,
    @SerialName("name")
    val name: String,
)
