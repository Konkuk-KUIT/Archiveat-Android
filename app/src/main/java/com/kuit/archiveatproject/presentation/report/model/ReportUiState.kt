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
    val weeklyFeedbackWeekLabel: String = "",
    val weeklyFeedbackBody: String = "",

    // 에러 상태
    val isError: Boolean = false,
    val errorMessage: String? = null
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
    val futurePercentage: Int = 0,

    val patternTitle: String = "",
    val patternDescription: String = "",
    val patternQuote: String = ""
)

// 소비 밸런스 판단 함수
fun ReportUiState.toKnowledgePosition(): KnowledgePosition {
    val timeAxis =
        if (balance.nowPercentage >= balance.futurePercentage)
            TimeAxis.NOW
        else
            TimeAxis.FUTURE

    val depthAxis =
        if (balance.lightPercentage >= balance.deepPercentage)
            DepthAxis.LIGHT
        else
            DepthAxis.DEEP

    return KnowledgePosition(
        timeAxis = timeAxis,
        depthAxis = depthAxis
    )
}

enum class TimeAxis { NOW, FUTURE }
enum class DepthAxis { LIGHT, DEEP }

data class KnowledgePosition(
    val timeAxis: TimeAxis,
    val depthAxis: DepthAxis
)

// balance 스크린 액션 버튼 text 매핑 함수
fun KnowledgePosition.toActionButtonText(): String =
    when (timeAxis to depthAxis) {
        TimeAxis.NOW to DepthAxis.LIGHT -> "영감 수집하러 가기"
        TimeAxis.NOW to DepthAxis.DEEP -> "집중 탐구하러 가기"
        TimeAxis.FUTURE to DepthAxis.LIGHT -> "성장 한입하러 가기"
        TimeAxis.FUTURE to DepthAxis.DEEP -> "관점 확장하러 가기"
        else -> ""
    }
