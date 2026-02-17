package com.kuit.archiveatproject.presentation.inbox.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.util.Log
import com.kuit.archiveatproject.domain.entity.Inbox
import com.kuit.archiveatproject.domain.entity.InboxItem
import com.kuit.archiveatproject.domain.entity.LlmStatus
import com.kuit.archiveatproject.domain.repository.InboxClassificationRepository
import com.kuit.archiveatproject.domain.repository.InboxRepository
import com.kuit.archiveatproject.domain.repository.NewsletterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
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
    private val inboxClassificationRepository: InboxClassificationRepository,
) : ViewModel() {

    private companion object {
        const val TAG = "InboxViewModel"
    }

    private val _uiState = MutableStateFlow(InboxUiState(isLoading = true))
    val uiState: StateFlow<InboxUiState> = _uiState
    private var pollingJob: Job? = null

    init {
        viewModelScope.launch {
            val inbox = fetchInbox(showLoading = true)
            if (inbox != null) updatePolling(inbox)
        }
    }

    fun loadInbox() {
        viewModelScope.launch {
            val inbox = fetchInbox(showLoading = true)
            if (inbox != null) updatePolling(inbox)
        }
    }

    suspend fun confirmExploreInboxAll() {
        try {
            inboxClassificationRepository.confirmExploreInboxAll()
        } catch (e: Throwable) {
            if (e is CancellationException) throw e
            Log.e(TAG, "confirmExploreInboxAll failed", e)
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
            try {
                newsletterRepository.deleteNewsletter(userNewsletterId)
                deleteLocal(userNewsletterId)
                onDeleted(userNewsletterId)
            } catch (e: Throwable) {
                if (e is CancellationException) throw e
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
        val hasInProgress = hasInProgress(inbox)

        if (!hasInProgress) {
            pollingJob?.cancel()
            pollingJob = null
            Log.d(TAG, "polling stop: no in-progress items")
            return
        }

        if (pollingJob?.isActive == true) {
            Log.d(TAG, "polling already running")
            return
        }

        pollingJob = viewModelScope.launch {
            Log.d(TAG, "polling start")
            while (true) {
                delay(5000L)
                val stillInProgress = hasInProgress(_uiState.value.inbox)
                if (!stillInProgress) {
                    Log.d(TAG, "polling stop: in-progress cleared")
                    break
                }
                Log.d(TAG, "polling tick: refresh inbox")
                try {
                    val refreshed = inboxRepository.getInbox()
                    _uiState.update { it.copy(inbox = refreshed, errorMessage = null) }
                    if (!hasInProgress(refreshed)) {
                        Log.d(TAG, "polling stop: in-progress cleared")
                        break
                    }
                } catch (e: Throwable) {
                    if (e is CancellationException) throw e
                    _uiState.update {
                        it.copy(errorMessage = e.message ?: "인박스 조회에 실패했습니다.")
                    }
                }
            }
        }
    }

    private suspend fun fetchInbox(showLoading: Boolean): Inbox? {
        if (showLoading) {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        }

        return try {
            val inbox = inboxRepository.getInbox()
            _uiState.update { it.copy(isLoading = false, inbox = inbox) }
            inbox
        } catch (e: Throwable) {
            if (e is CancellationException) throw e
            _uiState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "인박스 조회에 실패했습니다."
                )
            }
            null
        }
    }

    private fun hasInProgress(inbox: Inbox): Boolean =
        inbox.inbox.any { group ->
            group.items.any { item ->
                item.llmStatus != LlmStatus.DONE && item.llmStatus != LlmStatus.FAILED
            }
        }
}
