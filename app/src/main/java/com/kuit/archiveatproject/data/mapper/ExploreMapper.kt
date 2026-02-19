package com.kuit.archiveatproject.data.mapper

import com.kuit.archiveatproject.data.dto.response.explore.ExploreCategoryDto
import com.kuit.archiveatproject.data.dto.response.explore.ExploreResponseDto
import com.kuit.archiveatproject.data.dto.response.explore.ExploreTopicDto
import com.kuit.archiveatproject.domain.model.Explore
import com.kuit.archiveatproject.domain.model.ExploreCategory
import com.kuit.archiveatproject.domain.model.ExploreTopic

fun ExploreResponseDto.toEntity(): Explore =
    Explore(
        inboxCount = inboxCount,
        // 안드에서는 exploreLlmStatus로 사용
        llmStatus = llmStatus.toEntity(),
        categories = categories.sortedBy { it.id }.map { it.toEntity() },
    )

private fun ExploreCategoryDto.toEntity(): ExploreCategory =
    ExploreCategory(
        id = id,
        name = name,
        topics = topics.sortedBy { it.id }.map { it.toEntity() },
    )

private fun ExploreTopicDto.toEntity(): ExploreTopic =
    ExploreTopic(
        id = id,
        name = name,
        newsletterCount = newsletterCount,
    )
