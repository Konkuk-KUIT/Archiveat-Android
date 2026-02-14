package com.kuit.archiveatproject.data.mapper

import com.kuit.archiveatproject.data.dto.response.HomeResponseDto
import com.kuit.archiveatproject.domain.entity.Home
import com.kuit.archiveatproject.domain.entity.HomeCardType
import com.kuit.archiveatproject.domain.entity.HomeContentCard
import com.kuit.archiveatproject.domain.entity.HomeContentCollectionCard
import com.kuit.archiveatproject.domain.entity.HomeTab
import com.kuit.archiveatproject.domain.entity.HomeTabType

fun HomeResponseDto.toDomain(): Home {
    // label("영감수집") -> type(INSPIRATION) 역매핑
    val tabTypeByLabel: Map<String, HomeTabType> =
        tabs.associate { it.label to HomeTabType.from(it.type) }

    return Home(
        firstGreetingMessage = firstGreetingMessage,
        secondGreetingMessage = secondGreetingMessage,
        tabs = tabs.map {
            HomeTab(
                type = HomeTabType.from(it.type),
                label = it.label,
                subMessage = it.subMessage
            )
        },
        contentCards = contentCards.mapNotNull { dto ->
            val tabType = tabTypeByLabel[dto.tabLabel] ?: HomeTabType.ALL
            val title = dto.title?.takeIf { it.isNotBlank() } ?: return@mapNotNull null
            val smallSummary = dto.smallCardSummary.orEmpty()
            val mediumSummary = dto.mediumCardSummary.orEmpty()
            HomeContentCard(
                newsletterId = dto.userNewsletterId,
                tabType = tabType,
                tabLabel = dto.tabLabel,
                cardType = HomeCardType.fromLabel(dto.cardType),
                title = title,
                smallCardSummary = smallSummary,
                mediumCardSummary = mediumSummary,
                thumbnailUrl = dto.thumbnailUrl
            )
        },
        contentCollectionCards = contentCollectionCards.mapNotNull { dto ->
            val tabType = tabTypeByLabel[dto.tabLabel] ?: HomeTabType.ALL
            val title = dto.title?.takeIf { it.isNotBlank() } ?: return@mapNotNull null
            val smallSummary = dto.smallCardSummary.orEmpty()
            val mediumSummary = dto.mediumCardSummary.orEmpty()
            HomeContentCollectionCard(
                collectionId = dto.collectionId,
                tabType = tabType,
                tabLabel = dto.tabLabel,
                cardType = HomeCardType.fromLabel(dto.cardType),
                title = title,
                smallCardSummary = smallSummary,
                mediumCardSummary = mediumSummary,
                thumbnailUrls = dto.thumbnailUrls
            )
        }
    )
}
