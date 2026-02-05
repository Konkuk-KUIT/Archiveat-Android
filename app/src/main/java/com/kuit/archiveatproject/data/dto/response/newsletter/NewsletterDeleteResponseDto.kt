package com.kuit.archiveatproject.data.dto.response.newsletter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsletterDeleteResponseDto(
    @SerialName("userNewsletterId")
    val userNewsletterId: Long
)