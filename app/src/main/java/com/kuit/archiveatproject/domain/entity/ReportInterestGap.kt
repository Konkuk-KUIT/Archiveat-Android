package com.kuit.archiveatproject.domain.entity

data class ReportInterestGap(
    val id: Long?,
    val topicName: String,
    val savedCount: Int,
    val readCount: Int
)
