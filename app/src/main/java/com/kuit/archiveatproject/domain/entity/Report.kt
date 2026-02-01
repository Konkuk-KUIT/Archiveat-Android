package com.kuit.archiveatproject.domain.entity

data class Report(
    val weekLabel: String,
    val aiComment: String,
    val status: ReportStatus,
    val balance: ReportBalance,
    val interestGaps: List<ReportInterestGap>
)