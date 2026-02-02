package com.kuit.archiveatproject.domain.entity

import com.kuit.archiveatproject.presentation.onboarding.model.JobUiModel
import com.kuit.archiveatproject.presentation.onboarding.viewmodel.TimeSlot

data class UserMetadataSubmit(
    val employmentType: String,
    val availability: UserAvailability,
    val interests: List<UserInterests>,
)

data class UserAvailability(
    val light: List<TimeSlot>,
    val deep: List<TimeSlot>
)

data class UserInterests(
    val categoryId: Long,
    val topicIds: List<Long>,
)