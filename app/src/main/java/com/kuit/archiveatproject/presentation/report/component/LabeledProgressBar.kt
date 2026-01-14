package com.kuit.archiveatproject.presentation.report.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun LabeledProgressBar(
    leftLabel: String,
    rightLabel: String,
    percentage: Int,
    modifier: Modifier = Modifier
) {
    val safePercentage = percentage.coerceIn(0, 100)

    val progressColor = ArchiveatProjectTheme.colors.primary
    val trackColor = ArchiveatProjectTheme.colors.gray50
    val labelColor = ArchiveatProjectTheme.colors.gray600

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        /** 상단 라벨 */
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = leftLabel,
                style = ArchiveatProjectTheme.typography.Caption_medium,
                color = labelColor
            )
            Text(
                text = rightLabel,
                style = ArchiveatProjectTheme.typography.Caption_medium,
                color = labelColor
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        /** 진행 바 */
        Box(
            modifier = Modifier
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
}

@Preview(
    name = "LabeledProgressBar Preview",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
private fun LabeledProgressBarPreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // 30%
        LabeledProgressBar(
            leftLabel = "짧은 글",
            rightLabel = "긴 글",
            percentage = 30
        )

        // 50%
        LabeledProgressBar(
            leftLabel = "단기성",
            rightLabel = "장기성",
            percentage = 50
        )

        // 70%
        LabeledProgressBar(
            leftLabel = "저장",
            rightLabel = "소비",
            percentage = 70
        )

        // Edge case: 100%
        LabeledProgressBar(
            leftLabel = "관심",
            rightLabel = "소비",
            percentage = 100
        )
    }
}