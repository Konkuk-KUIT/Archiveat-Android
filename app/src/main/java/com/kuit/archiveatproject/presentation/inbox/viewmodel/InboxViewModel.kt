package com.kuit.archiveatproject.presentation.inbox.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuit.archiveatproject.domain.entity.Inbox
import com.kuit.archiveatproject.domain.entity.InboxItem
import com.kuit.archiveatproject.domain.repository.InboxRepository
import com.kuit.archiveatproject.domain.repository.NewsletterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class InboxViewModel @Inject constructor(
    private val inboxRepository: InboxRepository,
    private val newsletterRepository: NewsletterRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(InboxUiState(isLoading = true))
    val uiState: StateFlow<InboxUiState> = _uiState

    init {
        loadInbox()
    }

    fun loadInbox() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            runCatching { inboxRepository.getInbox() }
                .onSuccess { inbox ->
                    _uiState.update { it.copy(isLoading = false, inbox = inbox) }
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = e.message ?: "인박스 조회에 실패했습니다."
                        )
                    }
                }
        }
    }

    fun openEditSheet(item: InboxItem) {
        _uiState.update {
            it.copy(
                isEditSheetVisible = true,
                selectedUserNewsletterId = item.userNewsletterId
            )
        }
    }

    fun dismissEditSheet() {
        _uiState.update { it.copy(isEditSheetVisible = false) }
    }

    fun onEditSaved() {
        dismissEditSheet()
        loadInbox()
    }

    fun deleteNewsletter(userNewsletterId: Long, onDeleted: (Long) -> Unit = {}) {
        viewModelScope.launch {
            runCatching { newsletterRepository.deleteNewsletter(userNewsletterId) }
                .onSuccess {
                    deleteLocal(userNewsletterId)
                    onDeleted(userNewsletterId)
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(errorMessage = e.message ?: "삭제에 실패했습니다.")
                    }
                }
        }
    }

    private fun deleteLocal(userNewsletterId: Long) {
        val current = _uiState.value.inbox
        val updatedGroups = current.inbox.mapNotNull { group ->
            val remainingItems = group.items.filterNot { it.userNewsletterId == userNewsletterId }
            if (remainingItems.isEmpty()) null else group.copy(items = remainingItems)
        }
        _uiState.update { it.copy(inbox = Inbox(updatedGroups)) }
    }
}
