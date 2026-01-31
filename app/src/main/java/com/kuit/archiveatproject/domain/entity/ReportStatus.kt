package com.kuit.archiveatproject.domain.entity

data class ReportStatus(
    val totalSavedCount: Int,
    val totalReadCount: Int,
    val percentage: Int
)

data class RecentReadNewsletter(
    val id: Long,
    val title: String,
    val categoryName: String,
    val lastViewedAt: String
)