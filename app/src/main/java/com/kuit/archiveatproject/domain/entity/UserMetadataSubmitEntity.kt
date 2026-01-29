package com.kuit.archiveatproject.domain.entity

import com.kuit.archiveatproject.presentation.onboarding.model.JobUiModel
import com.kuit.archiveatproject.presentation.onboarding.viewmodel.TimeSlot

data class UserMetadataSubmit(
    val employmentType: String,
    val availability: UserAvailability,
    val interests: UserInterests,
)

data class UserAvailability(
    val light: List<TimeSlot>,
    val deep: List<TimeSlot>
)

data class UserInterests(
    val now: List<UserInterestGroup>,
    val future: List<UserInterestGroup>,
)

data class UserInterestGroup(
    val categoryId: Long,
    val topicIds: List<Long>,
)