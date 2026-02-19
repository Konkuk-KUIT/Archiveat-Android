package com.kuit.archiveatproject.presentation.onboarding.component.interest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun InterestTextChip(
    text: String,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when (text) {
        "영감수집" -> ArchiveatProjectTheme.colors.primary
        "관점확장" -> ArchiveatProjectTheme.colors.sub_3
        "집중탐구" -> ArchiveatProjectTheme.colors.sub_2
        "성장한입" -> ArchiveatProjectTheme.colors.sub_1
        else -> ArchiveatProjectTheme.colors.primary
    }

    Box(
        modifier = modifier
            .height(18.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(32.dp)
            )
            .padding(
                horizontal = 7.2.dp,
                vertical = 1.5.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = ArchiveatProjectTheme.typography.Caption_semibold,
            color = ArchiveatProjectTheme.colors.white
        )
    }
}