package com.kuit.archiveatproject.data.dto.response.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserMetadataResponseDto(
    @SerialName("employmentTypes")
    val employmentTypes: List<String>,
    @SerialName("availabilityOptions")
    val availabilityOptions: List<String>,
    @SerialName("categories")
    val categories: List<UserMetadataCategoryDto>,
)

@Serializable
data class UserMetadataCategoryDto(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("topics")
    val topics: List<UserMetadataTopicDto>,
)

@Serializable
data class UserMetadataTopicDto(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
)