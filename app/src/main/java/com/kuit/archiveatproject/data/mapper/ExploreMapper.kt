package com.kuit.archiveatproject.data.mapper

import com.kuit.archiveatproject.data.dto.response.explore.ExploreCategoryDto
import com.kuit.archiveatproject.data.dto.response.explore.ExploreResponseDto
import com.kuit.archiveatproject.data.dto.response.explore.ExploreTopicDto
import com.kuit.archiveatproject.domain.entity.Explore
import com.kuit.archiveatproject.domain.entity.ExploreCategory
import com.kuit.archiveatproject.domain.entity.ExploreTopic

fun ExploreResponseDto.toEntity(): Explore =
    Explore(
        inboxCount = inboxCount,
        // 안드에서는 exploreLlmStatus로 사용
        exploreLlmStatus = llmStatus.toEntity(),
        categories = categories.map { it.toEntity() },
    )

private fun ExploreCategoryDto.toEntity(): ExploreCategory =
    ExploreCategory(
        id = id,
        name = name,
        topics = topics.map { it.toEntity() },
    )

private fun ExploreTopicDto.toEntity(): ExploreTopic =
    ExploreTopic(
        id = id,
        name = name,
        newsletterCount = newsletterCount,
    )