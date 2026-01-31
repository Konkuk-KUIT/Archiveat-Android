package com.kuit.archiveatproject.data.mapper

import com.kuit.archiveatproject.data.dto.response.HomeResponseDto
import com.kuit.archiveatproject.domain.model.Home
import com.kuit.archiveatproject.domain.model.HomeCardType
import com.kuit.archiveatproject.domain.model.HomeContentCard
import com.kuit.archiveatproject.domain.model.HomeContentCollectionCard
import com.kuit.archiveatproject.domain.model.HomeTab
import com.kuit.archiveatproject.domain.model.HomeTabType

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
        contentCards = contentCards.map { dto ->
            val tabType = tabTypeByLabel[dto.tabLabel] ?: HomeTabType.ALL
            HomeContentCard(
                newsletterId = dto.newsletterId,
                tabType = tabType,
                tabLabel = dto.tabLabel,
                cardType = HomeCardType.fromLabel(dto.cardType),
                title = dto.title,
                smallCardSummary = dto.smallCardSummary,
                mediumCardSummary = dto.mediumCardSummary,
                thumbnailUrl = dto.thumbnailUrl
            )
        },
        contentCollectionCards = contentCollectionCards.map { dto ->
            val tabType = tabTypeByLabel[dto.tabLabel] ?: HomeTabType.ALL
            HomeContentCollectionCard(
                collectionId = dto.collectionId,
                tabType = tabType,
                tabLabel = dto.tabLabel,
                cardType = HomeCardType.fromLabel(dto.cardType),
                title = dto.title,
                smallCardSummary = dto.smallCardSummary,
                mediumCardSummary = dto.mediumCardSummary,
                thumbnailUrls = dto.thumbnailUrls
            )
        }
    )
}
