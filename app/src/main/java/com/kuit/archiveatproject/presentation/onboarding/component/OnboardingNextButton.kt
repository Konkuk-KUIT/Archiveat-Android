package com.kuit.archiveatproject.presentation.onboarding.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun OnboardingNextButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ArchiveatProjectTheme.colors.black,
            disabledContainerColor = ArchiveatProjectTheme.colors.gray200
        )
    ) {
        Text(
            text = text,
            style = ArchiveatProjectTheme.typography.Body_1_semibold,
            color = ArchiveatProjectTheme.colors.white
        )
    }
}