package com.kuit.archiveatproject.presentation.report.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun ReportConsumptionBalanceCard(
    lengthBalancePercentage: Int,
    purposeBalancePercentage: Int,
    modifier: Modifier = Modifier
) {
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
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(
                start = 20.dp,
                end = 20.dp,
                top = 16.dp,
                bottom = 20.dp
            )
        ) {
            /** 제목 */
            Text(
                text = "나의 소비 밸런스",
                style = ArchiveatProjectTheme.typography.Subhead_2_semibold,
                color = ArchiveatProjectTheme.colors.black
            )

            Spacer(modifier = Modifier.height(16.dp))

            /** 길이 Row */
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "길이",
                    style = ArchiveatProjectTheme.typography.Body_2_medium,
                    color = ArchiveatProjectTheme.colors.gray600,
                    modifier = Modifier.width(62.dp) // 라벨 고정폭
                )

                Spacer(modifier = Modifier.width(6.dp))

                LabeledProgressBar(
                    leftLabel = "짧은 글",
                    rightLabel = "긴 글",
                    percentage = lengthBalancePercentage,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            /** 목적 Row */
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "목적",
                    style = ArchiveatProjectTheme.typography.Body_2_medium,
                    color = ArchiveatProjectTheme.colors.gray600,
                    modifier = Modifier.width(62.dp)
                )

                Spacer(modifier = Modifier.width(6.dp))

                LabeledProgressBar(
                    leftLabel = "단기성",
                    rightLabel = "장기성",
                    percentage = purposeBalancePercentage,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}


@Preview(
    name = "ReportConsumptionBalanceCard",
    showBackground = true,
    backgroundColor = 0xFFF5F5F5
)
@Composable
private fun ReportConsumptionBalanceCardPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        ReportConsumptionBalanceCard(
            lengthBalancePercentage = 72,
            purposeBalancePercentage = 64
        )
    }
}
