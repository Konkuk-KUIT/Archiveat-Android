package com.kuit.archiveatproject.data.dto.response.report

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReportStatusDto(
    @SerialName("totalSavedCount")
    val totalSavedCount: Int,

    @SerialName("totalReadCount")
    val totalReadCount: Int,

    @SerialName("recentReadNewsletters")
    val recentReadNewsletters: List<RecentReadNewsletterDto> = emptyList()
)

@Serializable
data class RecentReadNewsletterDto(
    @SerialName("id")
    val id: Long,

    @SerialName("title")
    val title: String,

    @SerialName("categoryName")
    val categoryName: String,

    @SerialName("lastViewedAt")
    val lastViewedAt: String
)