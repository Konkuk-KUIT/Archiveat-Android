package com.kuit.archiveatproject.presentation.newsletterdetails.viewmodel

import com.kuit.archiveatproject.presentation.newsletterdetails.screen.NewsletterDetailsAiUiModel

data class NewsletterDetailsAiUiState(
    val isLoading: Boolean = false,
    val model: NewsletterDetailsAiUiModel? = null,
    val contentUrl: String = "",
    val isRead: Boolean = false,
    val showReadToast: Boolean = false,
    val errorMessage: String? = null,
)
