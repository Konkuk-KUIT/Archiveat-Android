package com.kuit.archiveatproject.presentation.report.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun BasicProgressBar(
    percentage: Int,
    modifier: Modifier = Modifier
) {
    val safePercentage = percentage.coerceIn(0, 100)

    val progressColor = ArchiveatProjectTheme.colors.primary
    val trackColor = ArchiveatProjectTheme.colors.gray50

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(6.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(trackColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(safePercentage / 100f)
                .clip(RoundedCornerShape(3.dp))
                .background(progressColor)
        )
    }
}