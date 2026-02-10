package com.kuit.archiveatproject.presentation.newsletterdetails.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuit.archiveatproject.domain.entity.CollectionDetailsResult
import com.kuit.archiveatproject.domain.entity.CollectionNewsletter
import com.kuit.archiveatproject.domain.repository.CollectionRepository
import com.kuit.archiveatproject.presentation.newsletterdetails.component.CollectionComponentUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class NewsletterDetailsCollectionViewModel @Inject constructor(
    private val collectionRepository: CollectionRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val argCollectionId: Long =
        savedStateHandle.get<Long>("collectionId") ?: -1L

    private val _uiState = MutableStateFlow(NewsletterDetailsCollectionUiState(isLoading = true))
    val uiState: StateFlow<NewsletterDetailsCollectionUiState> = _uiState

    init {
        if (argCollectionId != -1L) {
            load(argCollectionId)
        } else {
            _uiState.update { it.copy(isLoading = false, errorMessage = "collectionId가 없습니다") }
        }
    }

    fun load(collectionId: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val result = collectionRepository.getCollectionDetails(collectionId)
                _uiState.update { current ->
                    current.copy(
                        isLoading = false,
                        userName = result.collectionInfo.userNickname,
                        categoryLabel = result.collectionInfo.topicName,
                        monthLabel = currentMonthLabel(),
                        items = result.newsletters.map { it.toUiModel(result) }
                    )
                }
            } catch (e: Throwable) {
                if (e is CancellationException) throw e
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "컬렉션을 불러오지 못했습니다."
                    )
                }
            }
        }
    }

    fun toggleChecked(id: Long) {
        _uiState.update { state ->
            val updated = state.items.map { item ->
                if (item.id == id) item.copy(isChecked = !item.isChecked) else item
            }
            state.copy(items = updated)
        }
    }

    private fun currentMonthLabel(): String {
        val monthValue = LocalDate.now().monthValue
        return String.format(Locale.KOREAN, "%d월", monthValue)
    }
}

private fun CollectionNewsletter.toUiModel(result: CollectionDetailsResult): CollectionComponentUiModel =
    CollectionComponentUiModel(
        id = newsletterId,
        categoryLabel = result.collectionInfo.topicName,
        sourceIcon = "",
        sourceLabel = domainName,
        minutesLabel = "${consumptionTimeMin}분",
        thumbnailUrl = thumbnailUrl,
        title = title,
        subtitle = memo,
        isChecked = isRead,
    )
