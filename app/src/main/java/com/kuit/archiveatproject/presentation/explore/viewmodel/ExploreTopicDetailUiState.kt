package com.kuit.archiveatproject.presentation.explore.viewmodel

import com.kuit.archiveatproject.domain.entity.ExploreTopicNewsletterItem

data class ExploreTopicDetailUiState(
    val isLoading: Boolean = false,
    val newsletters: List<ExploreTopicNewsletterItem> = emptyList(), val hasNext: Boolean = false,
    val errorMessage: String? = null
)