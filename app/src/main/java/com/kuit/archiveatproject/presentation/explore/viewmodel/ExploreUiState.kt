package com.kuit.archiveatproject.presentation.explore.viewmodel

import com.kuit.archiveatproject.domain.entity.LlmStatus

data class ExploreUiState(
    val isLoading: Boolean = false,

    // 상단 상태
    val inboxCount: Int = 0,
    val llmStatus: LlmStatus = LlmStatus.DONE,

    // 탐색 카테고리
    val categoryTabs: List<ExploreCategoryTabItem> = emptyList(),
    val selectedCategoryId: Long = 0L,

    // 에러 처리 (옵션)
    val errorMessage: String? = null,
)

data class ExploreCategoryUiItem(
    val id: Long,
    val name: String,
    val topics: List<ExploreTopicUiItem>,
)

data class ExploreTopicUiItem(
    val id: Long,
    val name: String,
    val newsletterCount: Int,
)

data class ExploreCategoryTabItem(
    val id: Long,
    val name: String,
    val iconResId: Int,
)
