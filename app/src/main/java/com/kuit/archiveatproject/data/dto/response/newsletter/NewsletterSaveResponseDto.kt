package com.kuit.archiveatproject.data.dto.response.newsletter

import com.kuit.archiveatproject.data.dto.response.explore.LlmStatusDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsletterSaveResponseDto(
    @SerialName("newsletterId")
    val newsletterId: Long,
    @SerialName("llmStatus")
    val llmStatus: LlmStatusDto
)