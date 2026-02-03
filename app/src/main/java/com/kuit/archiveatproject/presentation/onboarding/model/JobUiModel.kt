package com.kuit.archiveatproject.presentation.onboarding.model

import androidx.annotation.DrawableRes

data class JobUiModel(
    val type: String,
    val label: String,
    @DrawableRes val iconRes: Int,
)