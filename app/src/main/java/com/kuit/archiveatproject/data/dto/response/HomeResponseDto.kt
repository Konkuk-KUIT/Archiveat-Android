package com.kuit.archiveatproject.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeResponseDto(
    @SerialName("firstGreetingMessage")
    val firstGreetingMessage: String,

    @SerialName("secondGreetingMessage")
    val secondGreetingMessage: String,

    @SerialName("tabs")
    val tabs: List<HomeTabDto>,

    @SerialName("contentCards")
    val contentCards: List<HomeContentCardDto> = emptyList(),

    @SerialName("contentCollectionCards")
    val contentCollectionCards: List<HomeContentCollectionCardDto> = emptyList(),
)

@Serializable
data class HomeTabDto(
    @SerialName("type")
    val type: String,   // ALL, INSPIRATION, DEEP_DIVE, GROWTH, VIEW_EXPANSION

    @SerialName("label")
    val label: String,  // "영감수집"

    @SerialName("subMessage")
    val subMessage: String
)

@Serializable
data class HomeContentCardDto(
    @SerialName("userNewsletterId")
    val userNewsletterId: Long,

    @SerialName("tabLabel")
    val tabLabel: String,     // TabDto.label과 동일 ("영감수집")

    @SerialName("cardType")
    val cardType: String,     // "AI 요약"

    @SerialName("title")
    val title: String? = null,

    @SerialName("smallCardSummary")
    val smallCardSummary: String? = null,

    @SerialName("mediumCardSummary")
    val mediumCardSummary: String? = null,

    @SerialName("thumbnailUrl")
    val thumbnailUrl: String?,

    @SerialName("domainName")
    val domainName: String? = null,

    @SerialName("createdAt")
    val createdAt: String? = null
)

@Serializable
data class HomeContentCollectionCardDto(
    @SerialName("collectionId")
    val collectionId: Long,
    @SerialName("tabLabel")
    val tabLabel: String,
    @SerialName("cardType")
    val cardType: String, // "컬렉션"
    @SerialName("title")
    val title: String? = null,
    @SerialName("smallCardSummary")
    val smallCardSummary: String? = null,
    @SerialName("mediumCardSummary")
    val mediumCardSummary: String? = null,
    @SerialName("thumbnails")
    val thumbnails: List<HomeThumbnailDto> = emptyList(),
)

@Serializable
data class HomeThumbnailDto(
    @SerialName("thumbnailUrl")
    val thumbnailUrl: String? = null,
    @SerialName("domainName")
    val domainName: String? = null,
)
