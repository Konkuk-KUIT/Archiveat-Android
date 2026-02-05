package com.kuit.archiveatproject.presentation.onboarding.component.interest

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun InterestChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor =
        if (selected) ArchiveatProjectTheme.colors.primary
        else Color.White

    val borderColor =
        if (selected) ArchiveatProjectTheme.colors.primary
        else ArchiveatProjectTheme.colors.gray200

    val textColor =
        if (selected) Color.White
        else ArchiveatProjectTheme.colors.gray700

    Box(
        modifier = modifier
            .height(33.dp)
            .clip(RoundedCornerShape(50.dp))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(50.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 13.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = ArchiveatProjectTheme.typography.Caption_semibold,
            color = textColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InterestChipPreview() {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        InterestChip(
            text = "인공지능",
            selected = true,
            onClick = {}
        )
        InterestChip(
            text = "백엔드/인프라",
            selected = false,
            onClick = {}
        )
    }
}