package com.kuit.archiveatproject.data.dto.request.newsletter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsletterSaveRequestDto(
    @SerialName("contentUrl")
    val contentUrl: String,
    @SerialName("memo")
    val memo: String
)