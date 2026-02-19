package com.kuit.archiveatproject.presentation.home.model

import com.kuit.archiveatproject.domain.entity.HomeCardType
import com.kuit.archiveatproject.domain.entity.HomeTabType

data class HomeThumbnailUiModel(
    val thumbnailUrl: String?,
    val domainName: String?
)

data class HomeContentCardUiModel(
    val archiveId: Long,
    val tabType: HomeTabType,
    val tabLabel: String,
    val cardType: HomeCardType,
    val title: String,
    val smallCardSummary: String,
    val mediumCardSummary: String,
    val imageUrls: List<String>,
    val thumbnails: List<HomeThumbnailUiModel> = emptyList(),
    val domainName: String? = null,
) {
    // 기존 코드 호환용
    val thumbnailUrl: String? get() = imageUrls.firstOrNull()
}
