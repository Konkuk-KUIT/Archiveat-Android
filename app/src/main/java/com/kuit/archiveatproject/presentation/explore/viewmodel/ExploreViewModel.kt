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
        val tabs = categories.map { category ->
            ExploreCategoryTabItem(
                id = category.id,
                name = category.name,
                iconResId = mapCategoryIcon(category.name)
            )
        }

        return ExploreUiState(
            inboxCount = inboxCount,
            llmStatus = llmStatus,
            categoryTabs = tabs,
            selectedCategoryId = tabs.firstOrNull()?.id ?: 0L,
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