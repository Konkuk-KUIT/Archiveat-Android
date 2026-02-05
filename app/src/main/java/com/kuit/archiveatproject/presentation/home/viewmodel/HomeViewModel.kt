package com.kuit.archiveatproject.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuit.archiveatproject.domain.entity.Home
import com.kuit.archiveatproject.domain.entity.HomeContentCard
import com.kuit.archiveatproject.domain.entity.HomeContentCollectionCard
import com.kuit.archiveatproject.domain.entity.HomeTabType
import com.kuit.archiveatproject.domain.repository.HomeRepository
import com.kuit.archiveatproject.presentation.home.model.GreetingUiModel
import com.kuit.archiveatproject.presentation.home.model.HomeContentCardUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState

    private var home: Home? = null

    init {
        loadHome()
    }

    private fun HomeContentCard.toUiModel(): HomeContentCardUiModel =
        HomeContentCardUiModel(
            archiveId = newsletterId,
            tabType = tabType,
            tabLabel = tabLabel,
            cardType = cardType,
            title = title,
            imageUrls = listOfNotNull(thumbnailUrl)
        )

    private fun HomeContentCollectionCard.toUiModel(): HomeContentCardUiModel =
        HomeContentCardUiModel(
            archiveId = collectionId,
            tabType = tabType,
            tabLabel = tabLabel,
            cardType = cardType,
            title = title,
            imageUrls = thumbnailUrls
        )

    private fun loadHome() {
        viewModelScope.launch {
            runCatching {
                homeRepository.getHome()
            }.onSuccess { result ->
                home = result
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        greeting = GreetingUiModel(
                            result.firstGreetingMessage,
                            result.secondGreetingMessage
                        ),
                        tabs = result.tabs,
                        selectedTab = HomeTabType.ALL
                    )
                }
                updateVisibleContent(HomeTabType.ALL)
            }
        }
    }

    fun onTabSelected(tabType: HomeTabType) {
        _uiState.update { it.copy(selectedTab = tabType) }
        updateVisibleContent(tabType)
    }

    private fun updateVisibleContent(tabType: HomeTabType) {
        val currentHome = home ?: return

        val baseCards = when (tabType) {
            HomeTabType.ALL ->
                currentHome.contentCards.take(10)

            else ->
                currentHome.contentCards.filter { it.tabType == tabType }
        }

        val uiCards = mutableListOf<HomeContentCardUiModel>()

        // 1. 일반 카드
        uiCards += baseCards.map { it.toUiModel() }

        // 2. 컬렉션 카드 (카테고리 탭에서만)
        if (tabType != HomeTabType.ALL) {
            uiCards += currentHome.contentCollectionCards
                .filter { it.tabType == tabType }
                .map { it.toUiModel() }
        }

        _uiState.update {
            it.copy(contentCards = uiCards)
        }
    }
}
