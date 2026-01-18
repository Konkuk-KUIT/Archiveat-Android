package com.kuit.archiveatproject.presentation.report.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun ReportConsumptionSummaryCard(
    totalSavedCount: Int,
    totalReadCount: Int,
    readPercentage: Int,
    modifier: Modifier = Modifier
) {
    val summaryText = buildAnnotatedString {
        append("총 ${totalSavedCount}개 중 ")

        withStyle(
            style = SpanStyle(
                color = ArchiveatProjectTheme.colors.primary
            )
        ) {
            append("${totalReadCount}개")
        }

        append(" 소비")
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.25.dp,
                color = ArchiveatProjectTheme.colors.gray100,
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = ArchiveatProjectTheme.colors.white
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "핵심 소비 현황",
                    style = ArchiveatProjectTheme.typography.Subhead_2_semibold,
                    color = ArchiveatProjectTheme.colors.black
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = summaryText,
                    style = ArchiveatProjectTheme.typography.Body_1_semibold,
                    color = ArchiveatProjectTheme.colors.gray600
                )
            }

            ConsumptionCircularProgress(
                percentage = readPercentage,
                modifier = Modifier.size(50.dp)
            )
        }
    }
}


@Preview(
    name = "ReportConsumptionSummaryCard",
    showBackground = true,
    backgroundColor = 0xFFF5F5F5
)
@Composable
private fun ReportConsumptionSummaryCardPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        ReportConsumptionSummaryCard(
            totalSavedCount = 120,
            totalReadCount = 42,
            readPercentage = 70
        )
    }
}