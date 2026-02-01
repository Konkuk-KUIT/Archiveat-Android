package com.kuit.archiveatproject.data.dto.response.report

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReportBalanceDto(
    @SerialName("lightCount")
    val lightCount: Int,

    @SerialName("deepCount")
    val deepCount: Int,

    @SerialName("nowCount")
    val nowCount: Int,

    @SerialName("futureCount")
    val futureCount: Int
)