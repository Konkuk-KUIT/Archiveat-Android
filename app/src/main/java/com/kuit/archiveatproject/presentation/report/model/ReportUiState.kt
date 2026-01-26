package com.kuit.archiveatproject.presentation.report.model

data class ReportUiState(
    val referenceDate: String,
    val totalSavedCount: Int,
    val totalReadCount: Int,
    val readPercentage: Int,
    val lightPercentage: Int,
    val nowPercentage: Int,
    val interestGaps: List<InterestGapUiItem>,

    // 주간 ai 종합 피드백
    val weeklyFeedbackDateRange: String, // 1월 19일-1월 25일
    val weeklyFeedbackBody: String
    /*
    지난 주 AI 분야에 80%의 시간을 사용하셨네요.
    저장 분야를 보니 건강에도 관심이 많으신데,
    관련 콘텐츠를 확인해볼까요?
     */
)

data class InterestGapUiItem(
    val topicName: String,
    val savedCount: Int,
    val readCount: Int,
    val gap: Int
)