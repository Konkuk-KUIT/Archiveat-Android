package com.kuit.archiveatproject.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CollectionDetailsResponseDto(
    @SerialName("collectionInfo")
    val collectionInfo: CollectionInfoDto,

    @SerialName("newsletters")
    val newsletters: List<CollectionNewsletterDto>,
)

@Serializable
data class CollectionInfoDto(
    @SerialName("collectionId")
    val collectionId: Long,
    @SerialName("userNickname")
    val userNickname: String,
    @SerialName("topicName")
    val topicName: String,
    @SerialName("totalCount")
    val totalCount: Int,
    @SerialName("readCount")
    val readCount: Int,
)

@Serializable
data class CollectionNewsletterDto(
    @SerialName("newsletterId")
    val newsletterId: Long,
    @SerialName("domainName")
    val domainName: String,
    @SerialName("title")
    val title: String,
    @SerialName("thumbnailUrl")
    val thumbnailUrl: String,
    @SerialName("consumptionTimeMin")
    val consumptionTimeMin: Int,
    @SerialName("memo")
    val memo: String,
    @SerialName("isRead")
    val isRead: Boolean,
)