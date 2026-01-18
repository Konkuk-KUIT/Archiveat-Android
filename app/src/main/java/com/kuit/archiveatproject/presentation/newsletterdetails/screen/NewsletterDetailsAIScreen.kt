package com.kuit.archiveatproject.presentation.newsletterdetails.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.kuit.archiveatproject.core.component.BackTopBar
import com.kuit.archiveatproject.core.component.tag.TagVariant
import com.kuit.archiveatproject.core.component.tag.TextTag
import com.kuit.archiveatproject.domain.model.HomeCardType
import com.kuit.archiveatproject.domain.model.HomeTabType
import com.kuit.archiveatproject.presentation.newsletterdetails.component.AIPreviewTextComponent
import com.kuit.archiveatproject.presentation.newsletterdetails.component.AISummaryComponent
import com.kuit.archiveatproject.presentation.newsletterdetails.component.AiSectionUiModel
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

// 임시 //
data class TagUiModel(
    val text: String,
    val variant: TagVariant,
)

data class NewsletterDetailsAiUiModel(
    val topicText: String,          // "최근 저장한 AI - 디자인"에서 "AI - 디자인" 부분
    val subtitle: String = "콘텐츠의 핵심만 AI가 요약했어요",    // "콘텐츠의 핵심만 AI가 요약했어요"
    val imageUrl: String,
    val tags: List<TagUiModel>,
    val contentTitle: String,       // "2025 UI 디자인 트렌드: ..."
    val userName: String,
    val aiSections: List<AiSectionUiModel>,
    val previewText: String,
)

@Composable
fun  NewsletterDetailsAIScreen(
    model: NewsletterDetailsAiUiModel,
    onBack: () -> Unit,
    onClickMore: () -> Unit,
    modifier: Modifier = Modifier,
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ArchiveatProjectTheme.colors.white)
            .padding(horizontal = 20.dp)
    ) {
        BackTopBar(
            title = "",
            onBack = onBack
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(12.dp))

            // "최근 저장한 AI - 디자인"
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "최근 저장한 ",
                    style = ArchiveatProjectTheme.typography.Heading_2_semibold,
                    color = ArchiveatProjectTheme.colors.gray900
                )
                Text(
                    text = model.topicText,
                    style = ArchiveatProjectTheme.typography.Heading_2_semibold,
                    color = ArchiveatProjectTheme.colors.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(
                text = model.subtitle,
                style = ArchiveatProjectTheme.typography.Heading_2_semibold,
                color = ArchiveatProjectTheme.colors.gray900
            )

            Spacer(Modifier.height(18.dp))

            if (model.imageUrl.isNotBlank()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(model.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "thumbnail",
                    modifier = modifier
                        .fillMaxWidth()
                        .height(219.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(219.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(ArchiveatProjectTheme.colors.gray100)
                )
            }

            Spacer(Modifier.height(18.dp))

            // 태그 (영감수집 / AI 요약)
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                model.tags.forEach { tag ->
                    TextTag(
                        text = tag.text,
                        variant = tag.variant
                    )
                }
            }

            Spacer(Modifier.height(14.dp))

            // 컨텐츠 제목
            Text(
                text = model.contentTitle,
                style = ArchiveatProjectTheme.typography.Heading_2_bold,
                color = ArchiveatProjectTheme.colors.black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.height(16.dp))

            // AI 요약 카드
            AISummaryComponent(
                userName = model.userName,
                sections = model.aiSections
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = "본문 미리보기",
                style = ArchiveatProjectTheme.typography.Subhead_1_semibold,
                color = ArchiveatProjectTheme.colors.gray800
            )

            Spacer(Modifier.height(8.dp))

            AIPreviewTextComponent(
                text = model.previewText,
                onClickMore = onClickMore
            )

            Spacer(Modifier.height(28.dp))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagsFlow(tags: List<TagUiModel>) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tags.forEach { tag ->
            TextTag(text = tag.text, variant = tag.variant)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NewsletterDetailsAIScreenPreview() {
    ArchiveatProjectTheme {
        NewsletterDetailsAIScreen(
            model = NewsletterDetailsAiUiModel(
                topicText = "AI - 디자인",
                subtitle = "콘텐츠의 핵심만 AI가 요약했어요",
                imageUrl = "", // 프리뷰에서는 빈 값으로 placeholder 보이게
                tags = listOf(
                    TagUiModel(
                        text = HomeTabType.INSPIRATION.label,
                        variant = TagVariant.Tab(HomeTabType.INSPIRATION)
                    ),
                    TagUiModel(
                        text = HomeCardType.AI_SUMMARY.label,
                        variant = TagVariant.CardType(HomeCardType.AI_SUMMARY)
                    ),
                    TagUiModel(
                        text = "하이",
                        variant = TagVariant.Custom
                    ),
                    TagUiModel(
                        text = "호이",
                        variant = TagVariant.Custom
                    )
                ),
                contentTitle = "2025 UI 디자인 트렌드:\n글래스모피즘의 귀환",
                userName = "잉비",
                aiSections = listOf(
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
                    )
                ),
                previewText = "불과 5년 전, 드리블(Dribbble)을 휩쓸었던 글래스모피즘이 돌아왔습니다. 하지만 이번엔 다릅니다. 애플 비전 프로가 쏘아 올린 '공간 UI'의 대중화로 인해, 평면적인 화면에서도 깊이감을 표현하는 것이 필수적인 UX 요소가 되었기 때문입니다.\u2028\n" +
                        "이번 테크크런치 아티클에서는 단순한 유행을 넘어, 개발자와 디자이너가 당장 실무에 적용해야 할 '기능적 글래스모피즘'의 3가지 법칙을 소개합니다. 특히 다크 모드에서의 명암비 해결법은 어쩌고 저쩌고 칸이 채워져있어야할듯요..."
            ),
            onBack = {},
            onClickMore = {}
        )
    }
}