package com.kuit.archiveatproject.presentation.newsletterdetails.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.R
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

// 임시 //
data class AiSectionUiModel(
    val heading: String,    // "Vision Pro의 나비효과"
    val body: String,       // 본문 문단
)

@Composable
fun AISummaryComponent(
    modifier: Modifier = Modifier,
    userName: String,
    sections: List<AiSectionUiModel>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(color = ArchiveatProjectTheme.colors.gray50)
            .padding(vertical = 16.dp, horizontal = 20.dp)
            .padding(end = 24.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                painter = painterResource(id = R.drawable.ic_ai),
                modifier = Modifier.size(24.dp),
                contentDescription = "ai icon",
                tint = ArchiveatProjectTheme.colors.primary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "${userName}님을 위한 요약본",
                style = ArchiveatProjectTheme.typography.Subhead_2_semibold,
                color = ArchiveatProjectTheme.colors.black)
        }
        Spacer(modifier = Modifier.height(1.dp))
        sections.forEach { section ->
            Column() {
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = section.heading,
                    style = ArchiveatProjectTheme.typography.Subhead_2_semibold,
                    color = ArchiveatProjectTheme.colors.gray800,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = section.body,
                    style = ArchiveatProjectTheme.typography.Body_2_medium,
                    color = ArchiveatProjectTheme.colors.gray700,
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun AISummaryComponentPrev(){
    AISummaryComponent(
        userName = "아영",
        sections = listOf(
            AiSectionUiModel(
                heading = "Vision Pro의 나비효과",
                body = "애플의 공간 컴퓨팅(Spatial Computing) 도입으로 인해, 배경과 콘텐츠를 겹쳐 보여주는 반투명 소재(Glass)가 다시 표준으로 자리 잡고 있습니다."
            ),
            AiSectionUiModel(
                heading = "심미성보다 깊이감",
                body = "과거의 글래스모피즘이 예쁘게 보이기 위함이었다면, 2025년의 트렌드는 정보의 위계(Hierarchy)를 명확히 구분하기 위한 기능적 수단입니다."
            ),
            AiSectionUiModel(
                heading = "접근성(Accessibility) 강화",
                body = "가독성을 해치지 않기 위해 배경 블러(Blur) 값을 높이고, 테두리(Border)를 명확하게 사용하는 것이 핵심 차별점입니다."
            ),
        )
    )
}