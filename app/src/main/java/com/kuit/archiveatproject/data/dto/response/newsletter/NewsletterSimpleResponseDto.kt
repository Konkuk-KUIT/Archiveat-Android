package com.kuit.archiveatproject.data.dto.response.newsletter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class NewsletterSimpleResponseDto(
    @SerialName("isConfirmed")
    val isConfirmed: Boolean,
)
