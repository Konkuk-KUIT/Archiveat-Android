package com.kuit.archiveatproject.data.dto.response.explore

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExploreResponseDto(
    @SerialName("inboxCount")
    val inboxCount: Int = 0,

    @SerialName("llmStatus")
    val llmStatus: LlmStatusDto = LlmStatusDto.DONE,

    @SerialName("categories")
    val categories: List<ExploreCategoryDto> = emptyList(),
)

@Serializable
data class ExploreCategoryDto(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("topics")
    val topics: List<ExploreTopicDto> = emptyList(),
)

@Serializable
data class ExploreTopicDto(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("newsletterCount")
    val newsletterCount: Int,
)