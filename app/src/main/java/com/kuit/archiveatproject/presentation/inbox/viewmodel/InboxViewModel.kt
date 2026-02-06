package com.kuit.archiveatproject.presentation.inbox.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.util.Log
import com.kuit.archiveatproject.domain.entity.Inbox
import com.kuit.archiveatproject.domain.entity.InboxItem
import com.kuit.archiveatproject.domain.entity.LlmStatus
import com.kuit.archiveatproject.domain.repository.InboxRepository
import com.kuit.archiveatproject.domain.repository.NewsletterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

@HiltViewModel
class InboxViewModel @Inject constructor(
    private val inboxRepository: InboxRepository,
    private val newsletterRepository: NewsletterRepository,
) : ViewModel() {

    private companion object {
        const val TAG = "InboxViewModel"
    }

    private val _uiState = MutableStateFlow(InboxUiState(isLoading = true))
    val uiState: StateFlow<InboxUiState> = _uiState
    private var pollingJob: Job? = null

    init {
        loadInbox()
    }

    fun loadInbox() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            runCatching { inboxRepository.getInbox() }
                .onSuccess { inbox ->
                    _uiState.update { it.copy(isLoading = false, inbox = inbox) }
                    updatePolling(inbox)
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
        _uiState.update { state ->
            val updatedGroups = state.inbox.inbox.mapNotNull { group ->
                val remainingItems = group.items.filterNot { it.userNewsletterId == userNewsletterId }
                if (remainingItems.isEmpty()) null else group.copy(items = remainingItems)
            }
            state.copy(inbox = Inbox(updatedGroups))
        }
    }

    private fun updatePolling(inbox: Inbox) {
        val hasRunning = inbox.inbox.any { group ->
            group.items.any { it.llmStatus == LlmStatus.RUNNING }
        }

        if (!hasRunning) {
            pollingJob?.cancel()
            pollingJob = null
            Log.d(TAG, "polling stop: no RUNNING items")
            return
        }

        if (pollingJob?.isActive == true) {
            Log.d(TAG, "polling already running")
            return
        }

        pollingJob = viewModelScope.launch {
            Log.d(TAG, "polling start")
            while (true) {
                delay(2500L) // nn초로 변경 가능
                val stillRunning = _uiState.value.inbox.inbox.any { group ->
                    group.items.any { it.llmStatus == LlmStatus.RUNNING }
                }
                if (!stillRunning) {
                    Log.d(TAG, "polling stop: RUNNING cleared")
                    break
                }
                Log.d(TAG, "polling tick: refresh inbox")
                loadInbox()
            }
        }
    }
}
