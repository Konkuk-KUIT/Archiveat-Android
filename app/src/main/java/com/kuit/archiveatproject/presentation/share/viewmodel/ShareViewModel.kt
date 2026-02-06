package com.kuit.archiveatproject.presentation.share.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuit.archiveatproject.domain.repository.NewsletterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShareViewModel @Inject constructor(
    private val newsletterRepository: NewsletterRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShareUiState())
    val uiState: StateFlow<ShareUiState> = _uiState

    /** Activity에서 전달받은 공유 텍스트 → contentUrl 추출 */
    fun setContentUrlFromSharedText(sharedText: String) {
        val contentUrl = extractContentUrl(sharedText)
        _uiState.update {
            it.copy(contentUrl = contentUrl)
        }
    }

    fun updateMemo(memo: String) {
        _uiState.update { it.copy(memo = memo) }
    }

    fun saveNewsletter() {
        val state = _uiState.value

        if (state.contentUrl.isBlank()) {
            _uiState.update {
                it.copy(errorMessage = "유효한 링크를 찾을 수 없습니다.")
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            runCatching {
                newsletterRepository.saveNewsletter(
                    contentUrl = state.contentUrl,
                    memo = state.memo
                )
            }.onSuccess {
                _uiState.update {
                    it.copy(isLoading = false, isSuccess = true)
                }
            }.onFailure {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "저장에 실패했습니다."
                    )
                }
            }
        }
    }

    /** 공유 텍스트에서 contentUrl 추출 */
    private fun extractContentUrl(text: String): String {
        val regex = "(https?://[^\\s]+)".toRegex()
        return regex.find(text)?.value.orEmpty()
    }
}