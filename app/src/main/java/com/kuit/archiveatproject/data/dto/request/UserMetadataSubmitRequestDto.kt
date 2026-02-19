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
    @SerialName("pref_morning")
    val prefMorning: String,
    @SerialName("pref_lunch")
    val prefLunch: String,
    @SerialName("pref_evening")
    val prefEvening: String,
    @SerialName("pref_bedtime")
    val prefBedtime: String,
)

@Serializable
data class UserInterestsDto(
    @SerialName("categoryId")
    val categoryId: Long,
    @SerialName("topicIds")
    val topicIds: List<Long>,
)
