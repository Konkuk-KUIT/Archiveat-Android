package com.kuit.archiveatproject.data.mapper

import com.kuit.archiveatproject.data.dto.response.user.UserMetadataCategoryDto
import com.kuit.archiveatproject.data.dto.response.user.UserMetadataResponseDto
import com.kuit.archiveatproject.data.dto.response.user.UserMetadataTopicDto
import com.kuit.archiveatproject.domain.entity.UserMetadataCategory
import com.kuit.archiveatproject.domain.entity.UserMetadataResult
import com.kuit.archiveatproject.domain.entity.UserMetadataTopic

fun UserMetadataResponseDto.toEntity(): UserMetadataResult {
    return UserMetadataResult(
        employmentTypes = employmentTypes,
        availabilityOptions = availabilityOptions,
        categories = categories.map { it.toEntity() },
    )
}

private fun UserMetadataCategoryDto.toEntity(): UserMetadataCategory {
    return UserMetadataCategory(
        id = id,
        name = name,
        topics = topics.map { it.toEntity() },
    )
}

private fun UserMetadataTopicDto.toEntity(): UserMetadataTopic {
    return UserMetadataTopic(
        id = id,
        name = name,
    )
}