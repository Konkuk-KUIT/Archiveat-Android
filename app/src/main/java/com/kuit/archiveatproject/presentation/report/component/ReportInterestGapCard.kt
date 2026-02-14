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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.presentation.report.model.InterestGapUiItem
import com.kuit.archiveatproject.presentation.report.model.MainInterestGapUiItem
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme
import kotlin.collections.forEachIndexed
import kotlin.collections.lastIndex

@Composable
fun ReportInterestGapCard(
    interestGaps: List<MainInterestGapUiItem>,
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

            /** 저장 / 소비 헤더 */
            if (interestGaps.isEmpty()) {
                Text(
                    text = "아직 저장한 아티클이 없습니다",
                    style = ArchiveatProjectTheme.typography.Body_2_medium,
                    color = ArchiveatProjectTheme.colors.gray500,
                    modifier = Modifier.fillMaxWidth(),
                )
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 64.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "저장",
                        style = ArchiveatProjectTheme.typography.Caption_medium,
                        color = ArchiveatProjectTheme.colors.gray600
                    )
                    Text(
                        text = "소비",
                        style = ArchiveatProjectTheme.typography.Caption_medium,
                        color = ArchiveatProjectTheme.colors.gray600
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                interestGaps.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(36.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.topicName.toDisplayTopicName(),
                            style = ArchiveatProjectTheme.typography.Body_2_medium,
                            color = ArchiveatProjectTheme.colors.gray600,
                            modifier = Modifier.width(52.dp)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        BasicProgressBar(
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
}

private fun MainInterestGapUiItem.toConsumptionPercentage(): Int {
    return if (savedCount == 0) 0
    else (readCount * 100 / savedCount).coerceIn(0, 100)
}

private fun String.toDisplayTopicName(): String {
    return when (this) {
        "백엔드/인프라" -> "백엔드\n/인프라"
        "프론트/모바일" -> "프론트\n/모바일"
        "글로벌 비즈니스" -> "글로벌\n비즈니스"
        "창업/스타트업" -> "창업/\n스타트업"
        "브랜드/마케팅" -> "브랜드\n/마케팅"
        "팝컬쳐/트렌드" -> "팝컬쳐\n/트렌드"
        "공간/플레이스" -> "공간/\n플레이스"
        else -> this
    }
}


@Preview(showBackground = true)
@Composable
private fun ReportInterestGapCardPreview() {
    ArchiveatProjectTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            ReportInterestGapCard(
                interestGaps = listOf(
                    MainInterestGapUiItem("건강", 50, 5),
                    MainInterestGapUiItem("AI", 30, 25),

                    MainInterestGapUiItem("인공지능", 40, 12),
                    MainInterestGapUiItem("백엔드/인프라", 32, 8),
                    MainInterestGapUiItem("프론트/모바일", 27, 15),
                    MainInterestGapUiItem("글로벌 비즈니스", 22, 10),
                    MainInterestGapUiItem("창업/스타트업", 18, 4),
                    MainInterestGapUiItem("브랜드/마케팅", 35, 19),
                    MainInterestGapUiItem("거시경제", 14, 6),
                    MainInterestGapUiItem("팝컬쳐/트렌드", 29, 13),
                    MainInterestGapUiItem("공간/플레이스", 16, 7),
                )
            )
        }
    }
}