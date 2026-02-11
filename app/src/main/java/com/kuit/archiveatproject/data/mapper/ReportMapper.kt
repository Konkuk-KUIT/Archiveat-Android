package com.kuit.archiveatproject.data.mapper

import com.kuit.archiveatproject.data.dto.response.report.RecentReadNewsletterDto
import com.kuit.archiveatproject.data.dto.response.report.ReportBalanceDto
import com.kuit.archiveatproject.data.dto.response.report.ReportInterestGapDto
import com.kuit.archiveatproject.data.dto.response.report.ReportResponseDto
import com.kuit.archiveatproject.data.dto.response.report.ReportStatusDto
import com.kuit.archiveatproject.domain.entity.RecentReadNewsletter
import com.kuit.archiveatproject.domain.entity.Report
import com.kuit.archiveatproject.domain.entity.ReportBalance
import com.kuit.archiveatproject.domain.entity.ReportInterestGap
import com.kuit.archiveatproject.domain.entity.ReportStatus

fun ReportResponseDto.toDomain(): Report {
    val status = ReportStatus(
        totalSavedCount = totalSavedCount,
        totalReadCount = totalReadCount,
        percentage = if (totalSavedCount == 0) {
            0
        } else {
            (totalReadCount * 100) / totalSavedCount
        }
    )

    val balance = ReportBalanceDto(
        lightCount = lightCount,
        deepCount = deepCount,
        nowCount = nowCount,
        futureCount = futureCount
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
        percentage = if (totalSavedCount == 0) {
            0
        } else {
            (totalReadCount * 100) / totalSavedCount
        },
        recentReadNewsletters = recentReadNewsletters.map { it.toDomain() }
    )

fun RecentReadNewsletterDto.toDomain(): RecentReadNewsletter =
    RecentReadNewsletter(
        id = id,
        title = title,
        categoryName = categoryName,
        lastViewedAt = lastViewedAt
    )


fun ReportBalanceDto.toDomain(): ReportBalance {
    val readingTotal = lightCount + deepCount
    val timeTotal = nowCount + futureCount

    return ReportBalance(
        lightPercentage = if (readingTotal == 0) 0 else (lightCount * 100) / readingTotal,
        deepPercentage = if (readingTotal == 0) 0 else 100 - (lightCount * 100) / readingTotal,
        nowPercentage = if (timeTotal == 0) 0 else (nowCount * 100) / timeTotal,
        futurePercentage = if (timeTotal == 0) 0 else 100 - (nowCount * 100) / timeTotal,
        patternTitle = patternTitle.orEmpty(),
        patternDescription = patternDescription.orEmpty(),
        patternQuote = patternQuote.orEmpty()
    )
}

fun ReportInterestGapDto.toDomain(): ReportInterestGap =
    ReportInterestGap(
        id = id,
        topicName = name,
        savedCount = savedCount,
        readCount = readCount
    )
