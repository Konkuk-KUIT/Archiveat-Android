package com.kuit.archiveatproject.presentation.onboarding.model

import androidx.compose.ui.graphics.Color


data class OnboardingButtonStyle(
    val backgroundColor: Color,
    val textColor: Color,
    val disabledBackgroundColor: Color = backgroundColor.copy(alpha = 0.4f),
    val disabledTextColor: Color = textColor.copy(alpha = 0.4f)
)