package com.kuit.archiveatproject.presentation.explore.viewmodel

import com.kuit.archiveatproject.domain.entity.ExploreTopicNewsletterItem

data class ExploreTopicDetailUiState(
    val isSearchMode: Boolean = false,
    val query: String = "",
    val recommendedKeywords: List<String> = emptyList(),
    val recentSearches: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val newsletters: List<ExploreTopicNewsletterItem> = emptyList(),
    val hasNext: Boolean = false,
    val errorMessage: String? = null
)