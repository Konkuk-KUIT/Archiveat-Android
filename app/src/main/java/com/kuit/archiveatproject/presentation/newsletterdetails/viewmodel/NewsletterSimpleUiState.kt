package com.kuit.archiveatproject.presentation.newsletterdetails.viewmodel

import com.kuit.archiveatproject.presentation.newsletterdetails.screen.NewsletterDetailsAiUiModel


data class NewsletterSimpleUiState(
    val isLoading: Boolean = false,
    val model: NewsletterDetailsAiUiModel? = null,
    val contentUrl: String = "",
    val errorMessage: String? = null,
)
