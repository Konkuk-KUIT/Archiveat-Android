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
import com.kuit.archiveatproject.presentation.report.model.InterestGapUiItem
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme
import kotlin.collections.forEachIndexed
import kotlin.collections.lastIndex

@Composable
fun ReportInterestGapCard(
    interestGaps: List<InterestGapUiItem>,
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
                top = 14.dp,
                bottom = 20.dp
            )
        ) {
            /** 제목 */
            Text(
                text = "관심사 갭 분석",
                style = ArchiveatProjectTheme.typography.Subhead_2_semibold,
                color = ArchiveatProjectTheme.colors.black
            )

            Spacer(modifier = Modifier.height(16.dp))

            interestGaps.forEachIndexed { index, item ->

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    /** 토픽 이름 */
                    Text(
                        text = item.topicName,
                        style = ArchiveatProjectTheme.typography.Body_2_medium,
                        color = ArchiveatProjectTheme.colors.gray600,
                        modifier = Modifier.width(40.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    /** 프로그레스 바 */
                    LabeledProgressBar(
                        leftLabel = if (index == 0) "저장" else "",
                        rightLabel = if (index == 0) "소비" else "",
                        percentage = item.toConsumptionPercentage(),
                        modifier = Modifier.weight(1f)
                    )
                }

                if (index != interestGaps.lastIndex) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

private fun InterestGapUiItem.toConsumptionPercentage(): Int {
    return if (savedCount == 0) 0
    else (readCount * 100 / savedCount).coerceIn(0, 100)
}

@Preview(
    name = "ReportInterestGapCard",
    showBackground = true,
    backgroundColor = 0xFFF5F5F5
)
@Composable
private fun ReportInterestGapCardPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        ReportInterestGapCard(
            interestGaps = listOf(
                InterestGapUiItem(
                    topicName = "건강",
                    savedCount = 50,
                    readCount = 5,
                    gap = 45
                ),
                InterestGapUiItem(
                    topicName = "AI",
                    savedCount = 30,
                    readCount = 25,
                    gap = 5
                )
            )
        )
    }
}