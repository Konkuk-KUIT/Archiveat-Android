package com.kuit.archiveatproject.data.dto.response.report

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReportBalanceDto(
    @SerialName("patternTitle")
    val patternTitle: String? = null,

    @SerialName("patternDescription")
    val patternDescription: String? = null,

    @SerialName("patternQuote")
    val patternQuote: String? = null,

    @SerialName("lightCount")
    val lightCount: Int,

    @SerialName("deepCount")
    val deepCount: Int,

    @SerialName("nowCount")
    val nowCount: Int,

    @SerialName("futureCount")
    val futureCount: Int
)