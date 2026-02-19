package com.kuit.archiveatproject.data.mapper

import com.kuit.archiveatproject.data.dto.response.CollectionDetailsResponseDto
import com.kuit.archiveatproject.data.dto.response.CollectionInfoDto
import com.kuit.archiveatproject.data.dto.response.CollectionNewsletterDto
import com.kuit.archiveatproject.domain.entity.CollectionDetailsResult
import com.kuit.archiveatproject.domain.entity.CollectionInfo
import com.kuit.archiveatproject.domain.entity.CollectionNewsletter

fun CollectionDetailsResponseDto.toEntity(): CollectionDetailsResult =
    CollectionDetailsResult(
        collectionInfo = collectionInfo.toEntity(),
        newsletters = newsletters.map { it.toEntity() },
    )

private fun CollectionInfoDto.toEntity(): CollectionInfo =
    CollectionInfo(
        collectionId = collectionId,
        userNickname = userNickname,
        topicName = topicName,
        totalCount = totalCount,
        readCount = readCount,
    )

private fun CollectionNewsletterDto.toEntity(): CollectionNewsletter =
    CollectionNewsletter(
        userNewsletterId = userNewsletterId,
        newsletterId = newsletterId,
        domainName = domainName,
        title = title,
        thumbnailUrl = thumbnailUrl,
        consumptionTimeMin = consumptionTimeMin,
        memo = memo,
        isRead = isRead,
    )
