package com.kuit.archiveatproject.data.mapper

import com.kuit.archiveatproject.data.dto.response.report.RecentReadNewsletterDto
import com.kuit.archiveatproject.data.dto.response.report.ReportBalanceDto
import com.kuit.archiveatproject.data.dto.response.report.ReportInterestGapDto
import com.kuit.archiveatproject.data.dto.response.report.ReportMainInterestGapDto
import com.kuit.archiveatproject.data.dto.response.report.ReportResponseDto
import com.kuit.archiveatproject.data.dto.response.report.ReportStatusDto
import com.kuit.archiveatproject.domain.entity.RecentReadNewsletter
import com.kuit.archiveatproject.domain.entity.Report
import com.kuit.archiveatproject.domain.entity.ReportBalance
import com.kuit.archiveatproject.domain.entity.ReportInterestGap
import com.kuit.archiveatproject.domain.entity.ReportMainInterestGap
import com.kuit.archiveatproject.domain.entity.ReportStatus

fun ReportResponseDto.toDomain(): Report {
    val status = ReportStatus(
        totalSavedCount = totalSavedCount,
        totalReadCount = totalReadCount,
        percentage = if (totalSavedCount == 0) 0 else (totalReadCount * 100) / totalSavedCount
    )

    val balance = ReportBalanceDto(
        lightCount = lightCount,
        deepCount = deepCount,
        nowCount = nowCount,
        futureCount = futureCount,
    ).toDomain()

    return Report(
        weekLabel = weekLabel,
        serverTimestamp = serverTimestamp,
        aiComment = aiComment,
        status = status,
        balance = balance,
        interestGaps = interestGaps.map { it.toDomain() }
    )
}

fun ReportStatusDto.toDomain(): ReportStatus =
    ReportStatus(
        totalSavedCount = totalSavedCount,
        totalReadCount = totalReadCount,
        percentage = if (totalSavedCount == 0) 0 else (totalReadCount * 100) / totalSavedCount,
        recentReadNewsletters = recentReadNewsletters.map { it.toDomain() }
    )

fun RecentReadNewsletterDto.toDomain(): RecentReadNewsletter =
    RecentReadNewsletter(
        id = id,
        title = title,
        categoryName = categoryName,
        lastViewedAt = lastViewedAt
    )

/**
 * ✅ 여기만 "안전한 퍼센트 계산"으로 교체됨 (UI 안 건드리고 음수/이상치 방지)
 */
fun ReportBalanceDto.toDomain(): ReportBalance {

    fun percentPair(left: Int, right: Int): Pair<Int, Int> {
        val l = left.coerceAtLeast(0)
        val r = right.coerceAtLeast(0)
        val total = l + r

        if (total == 0) return 50 to 50 // 데이터 없으면 중립(합 100)

        val leftPercent = ((l * 100.0) / total).toInt().coerceIn(0, 100)
        val rightPercent = (100 - leftPercent).coerceIn(0, 100) // 합 100 보정
        return leftPercent to rightPercent
    }

    val (lightP, deepP) = percentPair(lightCount, deepCount)
    val (nowP, futureP) = percentPair(nowCount, futureCount)

    return ReportBalance(
        lightPercentage = lightP,
        deepPercentage = deepP,
        nowPercentage = nowP,
        futurePercentage = futureP,
        patternTitle = patternTitle.orEmpty(),
        patternDescription = patternDescription.orEmpty(),
        patternQuote = patternQuote.orEmpty()
    )
}

fun ReportMainInterestGapDto.toDomain(): ReportMainInterestGap =
    ReportMainInterestGap(
        topicName = topicName,
        savedCount = savedCount,
        readCount = readCount
    )

fun ReportInterestGapDto.toDomain(): ReportInterestGap =
    ReportInterestGap(
        id = id,
        topicName = name,
        savedCount = savedCount,
        readCount = readCount
    )
