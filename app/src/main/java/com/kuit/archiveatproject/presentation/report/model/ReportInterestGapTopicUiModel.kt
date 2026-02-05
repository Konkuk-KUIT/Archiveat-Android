package com.kuit.archiveatproject.presentation.report.model

import kotlin.math.roundToInt

data class InterestGapTopicUiModel(
    val id: Long,
    val name: String,
    val savedCount: Int,
    val readCount: Int,
) {
    val gap: Int get() = (savedCount - readCount).coerceAtLeast(0)

    val consumptionRatePercent: Int
        get() = if (savedCount <= 0) 0 else ((readCount.toFloat() / savedCount) * 100f).roundToInt()
}

fun List<InterestGapTopicUiModel>.top4ByGap(): List<InterestGapTopicUiModel> =
    this.sortedWith(
        compareByDescending<InterestGapTopicUiModel> { it.gap }
            .thenByDescending { it.savedCount }
            .thenBy { it.name }
    ).take(4)