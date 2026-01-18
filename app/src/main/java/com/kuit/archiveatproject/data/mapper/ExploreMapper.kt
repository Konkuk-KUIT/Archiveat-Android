package com.kuit.archiveatproject.data.mapper

import com.kuit.archiveatproject.data.dto.response.ExploreCategoryDto
import com.kuit.archiveatproject.data.dto.response.ExploreResponseDto
import com.kuit.archiveatproject.data.dto.response.ExploreTopicDto
import com.kuit.archiveatproject.domain.model.Explore
import com.kuit.archiveatproject.domain.model.ExploreCategory
import com.kuit.archiveatproject.domain.model.ExploreTopic

fun ExploreResponseDto.toDomain(): Explore =
    Explore(
        categories = categories.map { it.toDomain() },
        topics = topics.map { it.toDomain() }
    )

private fun ExploreCategoryDto.toDomain(): ExploreCategory =
    ExploreCategory(
        id = id,
        name = name
    )

private fun ExploreTopicDto.toDomain(): ExploreTopic =
    ExploreTopic(
        id = id,
        categoryId = categoryId,
        name = name,
        readArticleCount = readArticleCount,
        unreadArticleCount = unreadArticleCount
    )