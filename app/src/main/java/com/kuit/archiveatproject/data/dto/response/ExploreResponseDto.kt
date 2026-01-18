package com.kuit.archiveatproject.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExploreResponseDto(
    @SerialName("categories")
    val categories: List<ExploreCategoryDto> = emptyList(),
    @SerialName("topics")
    val topics: List<ExploreTopicDto> = emptyList(),
)

@Serializable
data class ExploreCategoryDto(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
)

@Serializable
data class ExploreTopicDto(
    @SerialName("id")
    val id: Long,
    @SerialName("categoryId")
    val categoryId: Long,
    @SerialName("name")
    val name: String,
    @SerialName("readArticleCount")
    val readArticleCount: Int,
    @SerialName("unreadArticleCount")
    val unreadArticleCount: Int,
)