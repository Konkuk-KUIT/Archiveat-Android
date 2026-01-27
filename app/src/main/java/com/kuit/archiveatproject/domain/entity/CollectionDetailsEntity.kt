package com.kuit.archiveatproject.domain.entity

data class CollectionDetailsResult(
    val collectionInfo: CollectionInfo,
    val newsletters: List<CollectionNewsletter>,
)

data class CollectionInfo(
    val collectionId: Long,
    val userNickname: String,
    val topicName: String,
    val totalCount: Int,
    val readCount: Int,
)

data class CollectionNewsletter(
    val newsletterId: Long,
    val domainName: String,
    val title: String,
    val thumbnailUrl: String,
    val consumptionTimeMin: Int,
    val memo: String,
    val isRead: Boolean,
)