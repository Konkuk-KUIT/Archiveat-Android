package com.kuit.archiveatproject.domain.entity

data class ReportStatus(
    val totalSavedCount: Int,
    val totalReadCount: Int,
    val percentage: Int
)