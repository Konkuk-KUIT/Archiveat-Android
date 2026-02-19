package com.kuit.archiveatproject.presentation.onboarding.component.time

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun TimeSelectionItem(
    text: String,
    isSelected: Boolean,
    isDisabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when {
        isDisabled -> ArchiveatProjectTheme.colors.gray50
        isSelected -> ArchiveatProjectTheme.colors.primary
        else -> ArchiveatProjectTheme.colors.white
    }

    val borderColor = when {
        isDisabled -> ArchiveatProjectTheme.colors.gray50
        isSelected -> ArchiveatProjectTheme.colors.primary
        else -> ArchiveatProjectTheme.colors.gray200
    }

    val textColor = when {
        isDisabled -> ArchiveatProjectTheme.colors.gray300
        isSelected -> ArchiveatProjectTheme.colors.white
        else -> ArchiveatProjectTheme.colors.gray600
    }

    Box(
        modifier = modifier
            .height(40.dp)
            .width(158.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(
                enabled = !isDisabled,
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = ArchiveatProjectTheme.typography.Body_2_medium,
            color = textColor
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TimeSelectionItemPreview() {
    ArchiveatProjectTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 기본 상태
            TimeSelectionItem(
                text = "등굣길(아침)",
                isSelected = false,
                isDisabled = false,
                onClick = {}
            )

            // 선택된 상태
            TimeSelectionItem(
                text = "공강/점심",
                isSelected = true,
                isDisabled = false,
                onClick = {}
            )

            // 비활성화 상태
            TimeSelectionItem(
                text = "자기 전",
                isSelected = false,
                isDisabled = true,
                onClick = {}
            )
        }
    }
}