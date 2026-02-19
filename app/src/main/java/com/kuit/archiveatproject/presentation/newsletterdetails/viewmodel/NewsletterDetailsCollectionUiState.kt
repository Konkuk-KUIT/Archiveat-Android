package com.kuit.archiveatproject.presentation.newsletterdetails.viewmodel

import com.kuit.archiveatproject.presentation.newsletterdetails.component.CollectionComponentUiModel

data class NewsletterDetailsCollectionUiState(
    val isLoading: Boolean = false,
    val userName: String = "",
    val categoryLabel: String = "",
    val monthLabel: String = "",
    val items: List<CollectionComponentUiModel> = emptyList(),
    val errorMessage: String? = null,
)
