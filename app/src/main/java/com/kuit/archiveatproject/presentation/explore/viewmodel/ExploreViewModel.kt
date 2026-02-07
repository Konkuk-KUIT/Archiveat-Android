package com.kuit.archiveatproject.presentation.explore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuit.archiveatproject.R
import com.kuit.archiveatproject.domain.model.Explore
import com.kuit.archiveatproject.domain.repository.ExploreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val exploreRepository: ExploreRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExploreUiState(isLoading = true))
    val uiState: StateFlow<ExploreUiState> = _uiState.asStateFlow()

    init {
        fetchExplore()
    }

    private fun Explore.toUiState(): ExploreUiState {
        return ExploreUiState(
            inboxCount = inboxCount,
            llmStatus = llmStatus,

            categoryTabs = categories.map {
                ExploreCategoryTabItem(
                    id = it.id,
                    name = it.name,
                    iconResId = mapCategoryIcon(it.name)
                )
            },

            categories = categories.map {
                ExploreCategoryUiItem(
                    id = it.id,
                    name = it.name,
                    topics = it.topics.map { topic ->
                        ExploreTopicUiItem(
                            id = topic.id,
                            name = topic.name,
                            newsletterCount = topic.newsletterCount,
                            iconResId = mapTopicIcon(topic.name)
                        )
                    }
                )
            },

            selectedCategoryId = categories.firstOrNull()?.id ?: 0L
        )
    }

    private fun mapCategoryIcon(name: String): Int =
        when (name) {
            "IT/과학" -> R.drawable.ic_category_it
            "국제" -> R.drawable.ic_category_internation
            "경제" -> R.drawable.ic_category_economy
            "문화" -> R.drawable.ic_category_culture
            "생활" -> R.drawable.ic_category_living
            else -> R.drawable.ic_category_it
        }

    private fun mapTopicIcon(name: String): Int =
        when (name) {
            "AI" -> R.drawable.ic_topic_ai
            "백엔드/인프라" -> R.drawable.ic_topic_backend
            "프론트/모바일" -> R.drawable.ic_topic_front
            "데이터/보안" -> R.drawable.ic_topic_security
            "테크" -> R.drawable.ic_topic_tech
            "주식/투자" -> R.drawable.ic_topic_stock
            "부동산" -> R.drawable.ic_topic_property
            "가상화폐" -> R.drawable.ic_topic_virtualcurrency
            "창업/스타트업" -> R.drawable.ic_topic_startup
            "브랜드/마케팅" -> R.drawable.ic_topic_marketing
            "거시경제" -> R.drawable.ic_topic_macroeconomy
            "지정학/외교" -> R.drawable.ic_topic_diplomacy
            "미국/중국" -> R.drawable.ic_topic_macroeconomy
            "글로벌비즈니스" -> R.drawable.ic_topic_business
            "기후/에너지" -> R.drawable.ic_topic_climate
            "영화/OTT" -> R.drawable.ic_topic_movie
            "음악" -> R.drawable.ic_topic_music
            "도서/아티클" -> R.drawable.ic_topic_book
            "팝컬쳐/트렌드" -> R.drawable.ic_topic_popculture
            "공간/플레이스" -> R.drawable.ic_topic_place
            "디자인/예술" -> R.drawable.ic_topic_design
            "주니어/취업" -> R.drawable.ic_topic_junior
            "업무생산성" -> R.drawable.ic_topic_work
            "리더십/조직" -> R.drawable.ic_topic_leadership
            "심리/마인드" -> R.drawable.ic_topic_mind
            "건강/리빙" -> R.drawable.ic_topic_health
            "기타" -> R.drawable.ic_topic_etc
            else -> R.drawable.ic_topic_etc
        }

    fun onCategorySelected(categoryId: Long) {
        _uiState.update {
            it.copy(selectedCategoryId = categoryId)
        }
    }

    fun fetchExplore() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            runCatching {
                exploreRepository.getExplore()
            }.onSuccess { explore ->
                _uiState.value = explore.toUiState()
            }.onFailure { throwable ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = throwable.message ?: "탐색 정보를 불러오지 못했습니다."
                    )
                }
            }
        }
    }
}