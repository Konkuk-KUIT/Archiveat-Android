package com.kuit.archiveatproject.data.dto.response.report

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReportBalanceDto(
    @SerialName("lightPercentage")
    val lightPercentage: Int,

    @SerialName("nowPercentage")
    val nowPercentage: Int
)