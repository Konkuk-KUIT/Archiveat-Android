package com.kuit.archiveatproject.domain.model

data class Explore(
    val inboxCount: Int,
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