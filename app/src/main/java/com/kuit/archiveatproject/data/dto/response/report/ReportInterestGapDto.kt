package com.kuit.archiveatproject.data.dto.response.report

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReportInterestGapDto(
    @SerialName("topicName")
    val topicName: String,

    @SerialName("savedCount")
    val savedCount: Int,

    @SerialName("readCount")
    val readCount: Int
)