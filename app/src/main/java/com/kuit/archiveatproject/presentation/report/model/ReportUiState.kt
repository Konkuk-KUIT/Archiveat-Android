package com.kuit.archiveatproject.presentation.report.model

data class ReportUiState(
    val referenceDate: String,
    val totalSavedCount: Int,
    val totalReadCount: Int,
    val readPercentage: Int,
    val lightPercentage: Int,
    val nowPercentage: Int,
    val interestGaps: List<InterestGapUiItem>
)

data class InterestGapUiItem(
    val topicName: String,
    val savedCount: Int,
    val readCount: Int,
    val gap: Int
)