package com.kuit.archiveatproject.presentation.inbox.viewmodel

import com.kuit.archiveatproject.domain.entity.Inbox


data class InboxUiState(
    val isLoading: Boolean = false,
    val inbox: Inbox = Inbox(emptyList()),
    val errorMessage: String? = null,
    val isEditSheetVisible: Boolean = false,
    val selectedUserNewsletterId: Long = -1L,
)
