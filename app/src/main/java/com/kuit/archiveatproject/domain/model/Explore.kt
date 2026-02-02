package com.kuit.archiveatproject.domain.model

import com.kuit.archiveatproject.domain.entity.LlmStatus

data class Explore(
    val inboxCount: Int,
    val exploreLlmStatus: LlmStatus,
    val categories: List<ExploreCategory>,
)

data class ExploreCategory(
    val id: Long,
    val name: String,
    val topics: List<ExploreTopic>,
)

data class ExploreTopic(
    val id: Long,
    val name: String,
    val newsletterCount: Int,
)