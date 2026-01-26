package com.kuit.archiveatproject.presentation.onboarding.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kuit.archiveatproject.R
import com.kuit.archiveatproject.presentation.onboarding.model.OnboardingSlideModel

@Composable
fun OnboardingSlideContent(
    slide: OnboardingSlideModel,
    modifier: Modifier = Modifier
) {
    OnboardingSlide(
        imageRes = slide.imageRes,
        title = slide.title
    )
}
