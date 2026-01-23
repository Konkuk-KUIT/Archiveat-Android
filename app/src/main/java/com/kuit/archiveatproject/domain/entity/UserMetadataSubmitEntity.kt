package com.kuit.archiveatproject.domain.entity

data class UserMetadataSubmit(
    val employmentType: String,
    val availability: UserAvailability,
    val interests: UserInterests,
)

data class UserAvailability(
    val prefMorning: List<String>,
    val prefLunch: List<String>,
    val prefEvening: List<String>,
    val prefBedtime: List<String>,
)

data class UserInterests(
    val now: List<UserInterestGroup>,
    val future: List<UserInterestGroup>,
)

data class UserInterestGroup(
    val categoryId: Long,
    val topicIds: List<Long>,
)