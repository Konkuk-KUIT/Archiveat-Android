package com.kuit.archiveatproject.data.mapper

import com.kuit.archiveatproject.data.dto.response.HomeContentCardDto
import com.kuit.archiveatproject.data.dto.response.HomeResponseDto
import com.kuit.archiveatproject.data.dto.response.HomeTabDto
import com.kuit.archiveatproject.domain.model.HomeTabType
import com.kuit.archiveatproject.presentation.home.model.GreetingUiModel
import com.kuit.archiveatproject.presentation.home.model.HomeContentCardUiModel
import com.kuit.archiveatproject.presentation.home.model.HomeTabUiModel


/**
 * Home API Response → UI Model Mapper
 */

fun HomeResponseDto.toGreetingUiModel(): GreetingUiModel {
    return GreetingUiModel(
        firstMessage = firstGreetingMessage,
        secondMessage = secondGreetingMessage
    )
}

fun HomeTabDto.toUiModel(): HomeTabUiModel {
    return HomeTabUiModel(
        type = HomeTabType.from(type),
        label = label,
        subMessage = subMessage
    )
}

fun HomeContentCardDto.toUiModel(): HomeContentCardUiModel {
    return HomeContentCardUiModel(
        archiveId = archiveId,
        tabType = HomeTabType.from(tabLabelTypeOrFallback()),
        tabLabel = tabLabel,
        cardType = cardType,
        title = title,
        thumbnailUrl = thumbnailUrl
    )
}

private fun HomeContentCardDto.tabLabelTypeOrFallback(): String {
    return when (tabLabel.replace(" ", "")) {
        "전체" -> "ALL"
        "영감수집" -> "INSPIRATION"
        "집중탐구" -> "DEEP_DIVE"
        "성장한입" -> "GROWTH"
        "관점확장" -> "VIEW_EXPANSION"
        else -> "ALL"
    }
}