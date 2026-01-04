package com.kuit.archiveatproject.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeTabDto(
    @SerialName("type")
    val type: String,   // INSPIRATION, DEEP_DIVE, GROWTH, VIEW_EXPANSION

    @SerialName("label")
    val label: String,  // "영감수집"

    @SerialName("subMessage")
    val subMessage: String
)