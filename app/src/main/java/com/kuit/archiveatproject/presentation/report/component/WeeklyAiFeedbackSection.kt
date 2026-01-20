package com.kuit.archiveatproject.presentation.report.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun WeeklyAiFeedbackSection(
    dateRange: String,
    body: String,
    onClickCta: () -> Unit = {},
    modifier: Modifier = Modifier,
    cardColor: Color = ArchiveatProjectTheme.colors.primary,
    titleStyle: TextStyle = ArchiveatProjectTheme.typography.Heading_2_semibold,
    dateStyle: TextStyle = ArchiveatProjectTheme.typography.Caption_semibold,
    bodyStyle: TextStyle = ArchiveatProjectTheme.typography.Body_2_semibold,
    ctaStyle: TextStyle = ArchiveatProjectTheme.typography.Caption_medium,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "주간 AI 종합 피드백", style = titleStyle, color = ArchiveatProjectTheme.colors.gray900)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = dateRange, style = dateStyle, color = ArchiveatProjectTheme.colors.gray600)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 116.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(cardColor)
                .clickable(onClick = onClickCta)
                .padding(15.dp)
                .padding(top = 1.dp)
        ) {
            Text(
                text = body,
                color = ArchiveatProjectTheme.colors.white,
                style = bodyStyle,
                modifier = Modifier.align(Alignment.TopStart)
            )

            Text(
                text = "맞춤형 솔루션 >",
                color = ArchiveatProjectTheme.colors.gray50,
                style = ctaStyle,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun WeeklyAiFeedbackSectionPreview() {
    WeeklyAiFeedbackSection(
        dateRange = "1월 19일-1월 25일",
        body = "지난 주 AI 분야에 80%의 시간을 사용하셨네요.\n저장 분야를 보니 건강에도 관심이 많으신데,\n관련 콘텐츠를 확인해볼까요?",
        onClickCta = {}
    )
}