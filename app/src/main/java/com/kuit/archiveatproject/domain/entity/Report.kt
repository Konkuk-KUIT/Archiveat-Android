package com.kuit.archiveatproject.domain.entity

data class Report(
    val weekLabel: String,
    val serverTimestamp: String,
    val aiComment: String,
    val status: ReportStatus,
    val balance: ReportBalance,
    val interestGaps: List<ReportMainInterestGap>
)
data class ReportMainInterestGap(
    val topicId: Long,
    val topicName: String,
    val savedCount: Int,
    val readCount: Int
)
