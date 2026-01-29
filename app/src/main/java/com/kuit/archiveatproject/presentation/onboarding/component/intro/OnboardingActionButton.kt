package com.kuit.archiveatproject.presentation.onboarding.component.intro

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.presentation.onboarding.model.OnboardingButtonStyle
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun OnboardingActionButton(
    text: String,
    style: OnboardingButtonStyle,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = style.backgroundColor,
            disabledContainerColor = style.disabledBackgroundColor
        ),
        elevation = ButtonDefaults.buttonElevation(0.dp)
    ) {
        Text(
            text = text,
            color = if (enabled) style.textColor else style.disabledTextColor,
            style = ArchiveatProjectTheme.typography.Body_1_semibold
        )
    }
}
