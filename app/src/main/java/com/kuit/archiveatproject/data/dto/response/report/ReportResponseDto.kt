package com.kuit.archiveatproject.data.dto.response.report

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReportResponseDto(
    @SerialName("referenceDate")
    val referenceDate: String,

    @SerialName("status")
    val status: ReportStatusDto,

    @SerialName("balance")
    val balance: ReportBalanceDto,

    @SerialName("interestGaps")
    val interestGaps: List<ReportInterestGapDto>
)