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
    val interests: UserInterestsDto,
)

@Serializable
data class UserAvailabilityDto(
    @SerialName("pref_morning")
    val prefMorning: List<String>,
    @SerialName("pref_lunch")
    val prefLunch: List<String>,
    @SerialName("pref_evening")
    val prefEvening: List<String>,
    @SerialName("pref_bedtime")
    val prefBedtime: List<String>,
)

@Serializable
data class UserInterestsDto(
    @SerialName("now")
    val now: List<UserInterestGroupDto>,
    @SerialName("future")
    val future: List<UserInterestGroupDto>,
)

@Serializable
data class UserInterestGroupDto(
    @SerialName("categoryId")
    val categoryId: Long,
    @SerialName("topicIds")
    val topicIds: List<Long>,
)