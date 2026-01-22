package com.kuit.archiveatproject.data.mapper

import com.kuit.archiveatproject.data.dto.response.explore.ExploreCategoryDto
import com.kuit.archiveatproject.data.dto.response.explore.ExploreResponseDto
import com.kuit.archiveatproject.data.dto.response.explore.ExploreTopicDto
import com.kuit.archiveatproject.domain.model.Explore
import com.kuit.archiveatproject.domain.model.ExploreCategory
import com.kuit.archiveatproject.domain.model.ExploreTopic

fun ExploreResponseDto.toDomain(): Explore =
    Explore(
        inboxCount = inboxCount,
        categories = categories.map { it.toDomain() },
    )

private fun ExploreCategoryDto.toDomain(): ExploreCategory =
    ExploreCategory(
        id = id,
        name = name,
        topics = topics.map { it.toDomain() },
    )

private fun ExploreTopicDto.toDomain(): ExploreTopic =
    ExploreTopic(
        id = id,
        name = name,
        newsletterCount = newsletterCount,
    )