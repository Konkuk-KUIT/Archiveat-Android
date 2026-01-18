package com.kuit.archiveatproject.data.mapper

import com.kuit.archiveatproject.data.dto.response.report.ReportBalanceDto
import com.kuit.archiveatproject.data.dto.response.report.ReportInterestGapDto
import com.kuit.archiveatproject.data.dto.response.report.ReportResponseDto
import com.kuit.archiveatproject.data.dto.response.report.ReportStatusDto
import com.kuit.archiveatproject.domain.entity.Report
import com.kuit.archiveatproject.domain.entity.ReportBalance
import com.kuit.archiveatproject.domain.entity.ReportInterestGap
import com.kuit.archiveatproject.domain.entity.ReportStatus

fun ReportResponseDto.toDomain(): Report {
    return Report(
        referenceDate = referenceDate,
        status = status.toDomain(),
        balance = balance.toDomain(),
        interestGaps = interestGaps.map { it.toDomain() }
    )
}

fun ReportStatusDto.toDomain() =
    ReportStatus(
        totalSavedCount = totalSavedCount,
        totalReadCount = totalReadCount,
        percentage = percentage
    )

fun ReportBalanceDto.toDomain() =
    ReportBalance(
        lightPercentage = lightPercentage,
        nowPercentage = nowPercentage
    )

fun ReportInterestGapDto.toDomain() =
    ReportInterestGap(
        topicName = topicName,
        savedCount = savedCount,
        readCount = readCount
    )