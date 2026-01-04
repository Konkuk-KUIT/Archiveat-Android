package com.kuit.archiveatproject.presentation.home.model

import com.kuit.archiveatproject.domain.model.HomeTabType

data class HomeTabUiModel(
    val type: HomeTabType,
    val label: String,
    val subMessage: String
)