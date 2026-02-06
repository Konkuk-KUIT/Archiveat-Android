package com.kuit.archiveatproject.data.mapper

import com.kuit.archiveatproject.data.dto.request.UserAvailabilityDto
import com.kuit.archiveatproject.data.dto.request.UserInterestsDto
import com.kuit.archiveatproject.data.dto.request.UserMetadataSubmitRequestDto
import com.kuit.archiveatproject.domain.entity.UserAvailability
import com.kuit.archiveatproject.domain.entity.UserInterests
import com.kuit.archiveatproject.domain.entity.UserMetadataSubmit
import com.kuit.archiveatproject.presentation.onboarding.viewmodel.TimeSlot

fun UserMetadataSubmit.toRequestDto(): UserMetadataSubmitRequestDto {
    return UserMetadataSubmitRequestDto(
        employmentType = employmentType,
        availability = availability.toDto(),
        interests = interests.toDto(),
    )
}

private fun UserAvailability.toDto(): UserAvailabilityDto {

    fun modesFor(slot: TimeSlot): List<String> {
        val modes = mutableListOf<String>()
        if (slot in light) modes.add("LIGHT")
        if (slot in deep) modes.add("DEEP")
        return modes
    }

    return UserAvailabilityDto(
        prefMorning = modesFor(TimeSlot.MORNING),
        prefLunch = modesFor(TimeSlot.LUNCHTIME),
        prefEvening = modesFor(TimeSlot.EVENING),
        prefBedtime = modesFor(TimeSlot.BEDTIME)
    )
}

private fun List<UserInterests>.toDto(): List<UserInterestsDto> =
    map { it.toDto() }

private fun UserInterests.toDto(): UserInterestsDto {
    return UserInterestsDto(
        categoryId = categoryId,
        topicIds = topicIds,
    )
}