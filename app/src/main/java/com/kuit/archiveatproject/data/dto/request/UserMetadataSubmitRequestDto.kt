package com.kuit.archiveatproject.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserMetadataSubmitRequestDto(
    @SerialName("employmentType")
    val employmentType: String,

    @SerialName("availability")
    val availability: UserAvailabilityDto,

    @SerialName("interests")
    val interests: List<UserInterestsDto>,
)

@Serializable
data class UserAvailabilityDto(
    @SerialName("prefMorning")
    val prefMorning: List<String>,
    @SerialName("prefLunch")
    val prefLunch: List<String>,
    @SerialName("prefEvening")
    val prefEvening: List<String>,
    @SerialName("prefBedtime")
    val prefBedtime: List<String>,
)

@Serializable
data class UserInterestsDto(
    @SerialName("categoryId")
    val categoryId: Long,
    @SerialName("topicIds")
    val topicIds: List<Long>,
)
