package com.kuit.archiveatproject.data.dto.response.report

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReportStatusDto(
    @SerialName("totalSavedCount")
    val totalSavedCount: Int,

    @SerialName("totalReadCount")
    val totalReadCount: Int,
)