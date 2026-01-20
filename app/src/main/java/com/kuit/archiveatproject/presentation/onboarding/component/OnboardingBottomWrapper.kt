package com.kuit.archiveatproject.presentation.onboarding.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.presentation.onboarding.model.OnboardingButtonStyle
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun OnboardingBottomWrapper(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 6.dp,
                shape = RectangleShape,
                clip = false
            )
            .background(ArchiveatProjectTheme.colors.white)
            .navigationBarsPadding()
            .padding(
                horizontal = 24.dp,
                vertical = 16.dp
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            content = content
        )
    }
}

@Preview(
    name = "OnboardingBottomWrapper",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun OnboardingBottomWrapperPreview() {

    val prevStyle = OnboardingButtonStyle(
        backgroundColor = ArchiveatProjectTheme.colors.gray200,
        textColor = ArchiveatProjectTheme.colors.gray600
    )

    val nextStyle = OnboardingButtonStyle(
        backgroundColor = ArchiveatProjectTheme.colors.primary,
        textColor = ArchiveatProjectTheme.colors.white
    )

    OnboardingBottomWrapper {

        OnboardingActionButton(
            text = "이전",
            style = prevStyle,
            modifier = Modifier.weight(1f),
            onClick = {}
        )

        OnboardingActionButton(
            text = "다음",
            style = nextStyle,
            modifier = Modifier.weight(1f),
            onClick = {}
        )
    }
}

