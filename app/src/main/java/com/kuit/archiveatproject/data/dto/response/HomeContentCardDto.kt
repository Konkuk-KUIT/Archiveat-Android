package com.kuit.archiveatproject.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeContentCardDto(
    @SerialName("archiveId")
    val archiveId: Long,

    @SerialName("tabLabel")
    val tabLabel: String,     // TabDto.label과 동일 ("영감수집")

    @SerialName("cardType")
    val cardType: String,     // "AI 요약"

    @SerialName("title")
    val title: String,

    @SerialName("thumbnailUrl")
    val thumbnailUrl: String
)