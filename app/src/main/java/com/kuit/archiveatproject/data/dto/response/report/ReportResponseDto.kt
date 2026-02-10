package com.kuit.archiveatproject.data.dto.response.report

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReportResponseDto(
    @SerialName("weekLabel")
    val weekLabel: String,

    @SerialName("aiComment")
    val aiComment: String,

    @SerialName("totalSavedCount")
    val totalSavedCount: Int,

    @SerialName("totalReadCount")
    val totalReadCount: Int,

    @SerialName("lightCount")
    val lightCount: Int,

    @SerialName("deepCount")
    val deepCount: Int,

    @SerialName("nowCount")
    val nowCount: Int,

    @SerialName("futureCount")
    val futureCount: Int,

    @SerialName("interestGaps")
    val interestGaps: List<ReportInterestGapDto>,

    // BaseResponse.timestamp를 전달받아 채움
    @SerialName("serverTimestamp")
    val serverTimestamp: String = ""
)
