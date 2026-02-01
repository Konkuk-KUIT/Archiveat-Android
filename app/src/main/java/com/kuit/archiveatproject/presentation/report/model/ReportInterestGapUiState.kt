package com.kuit.archiveatproject.presentation.report.model

data class ReportInterestGapUiState(
    val topicsTop4: List<InterestGapTopicUiModel> = emptyList(),
    val selectedTopicId: Long? = null,
) {
    val selectedTopic: InterestGapTopicUiModel?
        get() = topicsTop4.firstOrNull { it.id == selectedTopicId } ?: topicsTop4.firstOrNull()
}