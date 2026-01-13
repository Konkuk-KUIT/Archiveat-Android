package com.kuit.archiveatproject.domain.entity

data class ReportInterestGap(
    val topicName: String,
    val savedCount: Int,
    val readCount: Int
) {
    val gap: Int
        get() = savedCount - readCount
}