package com.kuit.archiveatproject.presentation.share.viewmodel

data class ShareUiState(
    val contentUrl: String = "",
    val memo: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)