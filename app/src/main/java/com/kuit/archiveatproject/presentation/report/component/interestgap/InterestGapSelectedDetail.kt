package com.kuit.archiveatproject.presentation.report.component.interestgap

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.presentation.report.model.InterestGapTopicUiModel
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun InterestGapSelectedDetail(
    selected: InterestGapTopicUiModel,
    tagBgColor: Color,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = selected.name,
            color = Color.White,
            style = ArchiveatProjectTheme.typography.Body_1_semibold,
            modifier = Modifier
                .background(tagBgColor, RoundedCornerShape(999.dp))  // ✅ 여기만 교체
                .padding(horizontal = 12.dp, vertical = 6.dp),
        )

        Spacer(Modifier.height(12.dp))

        val accent = Color(0xFFFD4C4C)
        Row {
            Text(
                text = "${selected.savedCount}",
                style = ArchiveatProjectTheme.typography.Heading_1_bold,
                color = ArchiveatProjectTheme.colors.primary
            )
            Spacer(modifier = Modifier.size(1.dp))
            Text(
                text = "번 저장",
                style = ArchiveatProjectTheme.typography.Heading_1_semibold,
                color = ArchiveatProjectTheme.colors.primary
            )
            Text(
                text = "했지만,",
                style = ArchiveatProjectTheme.typography.Heading_1_semibold,
                color = ArchiveatProjectTheme.colors.gray700
            )
        }

        Row {
            Text(
                text = "${selected.readCount}",
                style = ArchiveatProjectTheme.typography.Heading_1_bold,
                color = accent
            )
            Spacer(modifier = Modifier.size(1.dp))
            Text(
                text = "번 소비",
                style = ArchiveatProjectTheme.typography.Heading_1_semibold,
                color = accent
            )
            Text(
                text = "했어요.",
                style = ArchiveatProjectTheme.typography.Heading_1_semibold,
                color = ArchiveatProjectTheme.colors.gray700
            )
        }

        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                title = "안 읽고 쌓인 지식",
                value = "${selected.gap}",
                bg = ArchiveatProjectTheme.colors.white,
                border = Color(0xFF9747FF).copy(alpha = 0.1f),
                modifier = Modifier.weight(61f)
            )
            StatCard(
                title = "소비율",
                value = "${selected.consumptionRatePercent}",
                unit = "%",
                bg = Color(0xFF9747FF).copy(alpha = 0.1f),
                contentColor = ArchiveatProjectTheme.colors.primary.copy(alpha = 0.7f),
                border = Color(0xFF9747FF).copy(alpha = 0.1f),
                modifier = Modifier.weight(44f)
            )
        }
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    unit: String = "개",
    bg: Color,
    contentColor: Color? = null,
    border: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .border(1.25.dp, border, RoundedCornerShape(16.dp))
            .background(bg, RoundedCornerShape(16.dp))
            .padding(horizontal = 28.dp, vertical = 12.5.dp)
    ) {
        Text(
            text = title,
            style = ArchiveatProjectTheme.typography.Body_2_medium,
            color = contentColor ?: ArchiveatProjectTheme.colors.gray500
        )
        Spacer(Modifier.height(5.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = value,
                style = ArchiveatProjectTheme.typography.Heading_1_bold,
                color = if (contentColor != null) ArchiveatProjectTheme.colors.primary else ArchiveatProjectTheme.colors.gray950
            )
            Spacer(Modifier.size(4.dp))
            Text(
                text = unit,
                style = ArchiveatProjectTheme.typography.Body_2_medium,
                color = contentColor ?: ArchiveatProjectTheme.colors.gray400
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InterestGapSelectedDetailPrev() {
    InterestGapSelectedDetail(
        InterestGapTopicUiModel(1, "경제", 24, 2),
        ArchiveatProjectTheme.colors.primary,
    )
}