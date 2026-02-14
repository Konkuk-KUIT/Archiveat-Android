package com.kuit.archiveatproject.data.dto.response.report

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReportInterestGapResponseDto(
    @SerialName("topics")
    val topics: List<ReportInterestGapDto>
)

@Serializable
data class ReportInterestGapDto(
    @SerialName("id")
    val id: Long?,

    @SerialName("name")
    val name: String,

    @SerialName("savedCount")
    val savedCount: Int,

    @SerialName("readCount")
    val readCount: Int
)
