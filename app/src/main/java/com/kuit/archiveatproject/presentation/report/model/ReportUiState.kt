package com.kuit.archiveatproject.presentation.report.model

import com.kuit.archiveatproject.domain.entity.RecentReadNewsletter

data class ReportUiState(
    val referenceDate: String = "",

    // 핵심 소비 현황
    val totalSavedCount: Int = 0,
    val totalReadCount: Int = 0,
    val readPercentage: Int = 0,

    // 리딩 밸런스
    val balance: ReportBalanceUiState = ReportBalanceUiState(),

    // 관심사별 소비 격차
    val interestGaps: List<InterestGapUiItem> = emptyList(),

    // 최근 학습 기록
    val recentReadNewsletters: List<RecentReadNewsletterUiItem> = emptyList(),

    // 주간 AI 종합 피드백
    val weeklyFeedbackDateRange: String = "",
    val weeklyFeedbackBody: String = ""
)

data class InterestGapUiItem(
    val topicName: String,
    val savedCount: Int,
    val readCount: Int,
    val gap: Int
)

data class RecentReadNewsletterUiItem(
    val id: Long,
    val title: String,
    val categoryName: String,
    val lastViewedAt: String
)

data class ReportBalanceUiState(
    val lightPercentage: Int = 0,
    val deepPercentage: Int = 0,
    val nowPercentage: Int = 0,
    val futurePercentage: Int = 0
)