package com.kuit.archiveatproject.domain.model

data class Explore(
    val categories: List<ExploreCategory>,
    val topics: List<ExploreTopic>,
)

data class ExploreCategory(
    val id: Long,
    val name: String,
)

data class ExploreTopic(
    val id: Long,
    val categoryId: Long,
    val name: String,
    val readArticleCount: Int,
    val unreadArticleCount: Int,
)