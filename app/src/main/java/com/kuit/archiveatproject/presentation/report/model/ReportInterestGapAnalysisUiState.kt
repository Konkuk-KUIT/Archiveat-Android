package com.kuit.archiveatproject.presentation.report.model

data class ReportInterestGapAnalysisUiState(
    val topics: List<InterestGapTopicUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)
