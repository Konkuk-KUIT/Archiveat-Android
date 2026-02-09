package com.kuit.archiveatproject.presentation.home.viewmodel

import com.kuit.archiveatproject.domain.entity.HomeContentCollectionCard
import com.kuit.archiveatproject.domain.entity.HomeTab
import com.kuit.archiveatproject.domain.entity.HomeTabType
import com.kuit.archiveatproject.presentation.home.model.GreetingUiModel
import com.kuit.archiveatproject.presentation.home.model.HomeContentCardUiModel

data class HomeUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val nickname: String = "",
    val greeting: GreetingUiModel? = null,

    val tabs: List<HomeTab> = emptyList(),
    val selectedTab: HomeTabType = HomeTabType.ALL,

    val contentCards: List<HomeContentCardUiModel> = emptyList(),
)
