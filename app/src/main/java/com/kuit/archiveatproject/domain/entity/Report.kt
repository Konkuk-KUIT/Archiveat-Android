package com.kuit.archiveatproject.domain.entity

data class Report(
    val referenceDate: String,
    val status: ReportStatus,
    val balance: ReportBalance,
    val interestGaps: List<ReportInterestGap>
)