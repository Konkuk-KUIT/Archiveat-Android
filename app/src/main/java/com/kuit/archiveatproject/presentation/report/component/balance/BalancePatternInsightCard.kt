package com.kuit.archiveatproject.presentation.report.component.balance

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun BalancePatternInsightCard(
    title: String,
    description: String,
    quote: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = ArchiveatProjectTheme.colors.gray50,
                shape = RoundedCornerShape(16.dp)
            )
            .border(1.dp, ArchiveatProjectTheme.colors.gray100, RoundedCornerShape(16.dp))
            .padding(20.dp)
    ) {

        // 제목
        Text(
            text = title,
            style = ArchiveatProjectTheme.typography.Subhead_1_semibold,
            color = ArchiveatProjectTheme.colors.gray800
        )

        Spacer(Modifier.height(15.dp))

        // 설명
        Text(
            text = description,
            style = ArchiveatProjectTheme.typography.Body_2_medium,
            color = ArchiveatProjectTheme.colors.gray700
        )

        Spacer(Modifier.height(21.dp))

        // 인용 / 강조 박스
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = ArchiveatProjectTheme.colors.white,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 16.5.dp, vertical = 24.5.dp)
        ) {
            Text(
                text = quote,
                style = ArchiveatProjectTheme.typography.Body_2_semibold,
                color = ArchiveatProjectTheme.colors.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PatternInsightCardPreview() {
    ArchiveatProjectTheme {
        BalancePatternInsightCard(
            title = "핵심을 빠르게 파악하는\n실용적 효율 추구형",
            description = "10분 미만의 요약된 콘텐츠로 당장 필요한 정보를 빠르게 습득하고 계세요. 시간 대비 효율을 중요하게 생각하는 소비 패턴입니다.",
            quote = "현재에 충실한 것도 좋지만, 시야가 좁아질 수 있습니다. 가끔은 당장의 목적과 무관하더라도 새로운 관점을 접해보는 것을 추천합니다.",
            modifier = Modifier.padding(16.dp)
        )
    }
}
