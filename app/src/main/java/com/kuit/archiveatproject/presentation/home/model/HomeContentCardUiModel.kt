package com.kuit.archiveatproject.presentation.home.model

import com.kuit.archiveatproject.domain.model.HomeCardType
import com.kuit.archiveatproject.domain.model.HomeTabType

data class HomeContentCardUiModel(
    val archiveId: Long,
    val tabType: HomeTabType,
    val tabLabel: String,
    val cardType: HomeCardType,
    val title: String,
    val imageUrls: List<String>,
) {
    // 기존 코드 호환용
    val thumbnailUrl: String? get() = imageUrls.firstOrNull()
}