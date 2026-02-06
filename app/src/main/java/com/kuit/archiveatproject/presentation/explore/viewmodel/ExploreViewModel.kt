package com.kuit.archiveatproject.presentation.explore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private fun Explore.toUiState(): ExploreUiState =
        ExploreUiState(
            isLoading = false,
            inboxCount = inboxCount,
            llmStatus = llmStatus,
            categories = categories.map { category ->
                ExploreCategoryUiItem(
                    id = category.id,
                    name = category.name,
                    topics = category.topics.map { topic ->
                        ExploreTopicUiItem(
                            id = topic.id,
                            name = topic.name,
                            newsletterCount = topic.newsletterCount,
                        )
                    }
                )
            }
        )

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