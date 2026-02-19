package com.kuit.archiveatproject.data.dto.response.newsletter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsletterDetailResponseDto(
    @SerialName("userNewsletterId")
    val userNewsletterId: Long,
    @SerialName("categoryName")
    val categoryName: String,
    @SerialName("topicName")
    val topicName: String,
    @SerialName("title")
    val title: String,
    @SerialName("thumbnailUrl")
    val thumbnailUrl: String? = null,
    @SerialName("domainName")
    val domainName: String? = null,
    @SerialName("label")
    val label: String,
    @SerialName("memo")
    val memo: String,
    @SerialName("contentUrl")
    val contentUrl: String,
    @SerialName("isRead")
    val isRead: Boolean = false,
    @SerialName("newsletterSummary")
    val newsletterSummary: List<NewsletterSummaryItemDto> = emptyList()
)

@Serializable
data class NewsletterSummaryItemDto(
    @SerialName("title") val title: String,
    @SerialName("content") val content: String
)
