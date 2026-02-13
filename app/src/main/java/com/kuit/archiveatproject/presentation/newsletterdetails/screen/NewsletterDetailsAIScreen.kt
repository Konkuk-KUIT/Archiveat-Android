package com.kuit.archiveatproject.presentation.newsletterdetails.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.kuit.archiveatproject.core.component.BackTopBar
import com.kuit.archiveatproject.core.component.tag.TagVariant
import com.kuit.archiveatproject.core.component.tag.TextTag
import com.kuit.archiveatproject.domain.entity.HomeCardType
import com.kuit.archiveatproject.domain.entity.HomeTabType
import com.kuit.archiveatproject.presentation.newsletterdetails.component.AISummaryComponent
import com.kuit.archiveatproject.presentation.newsletterdetails.component.AiSectionUiModel
import com.kuit.archiveatproject.presentation.newsletterdetails.component.MemoComponent
import com.kuit.archiveatproject.presentation.newsletterdetails.viewmodel.NewsletterDetailsAiViewModel
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
    val memo: String,
)

@Composable
fun NewsletterDetailsAIScreen(
    onBack: () -> Unit,
    onClickWebView: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NewsletterDetailsAiViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    val model = uiState.model
    if (model != null) {
        NewsletterDetailsAIContent(
            model = model,
            onBack = onBack,
            onClickWebView = { onClickWebView(uiState.contentUrl) },
            onClickDone = viewModel::markRead,
            modifier = modifier,
            fromAI = true
        )
    } else {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            val message = uiState.errorMessage ?: "로딩 중..."
            Text(
                text = message,
                style = ArchiveatProjectTheme.typography.Subhead_2_semibold,
                color = ArchiveatProjectTheme.colors.gray600
            )
        }
    }
}

@Composable
fun NewsletterDetailsAIContent(
    model: NewsletterDetailsAiUiModel,
    onBack: () -> Unit,
    onClickWebView: () -> Unit,
    onClickDone: () -> Unit,
    modifier: Modifier = Modifier,
    fromAI: Boolean = true,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ArchiveatProjectTheme.colors.white)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        BackTopBar(
            title = "",
            onBack = onBack,
            height = 45
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
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
                    modifier = Modifier
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

            MemoComponent(
                memo = model.memo,
            )

            Spacer(Modifier.height(18.dp))

            // 버튼
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (fromAI) ArchiveatProjectTheme.colors.gray100 else ArchiveatProjectTheme.colors.black)
                    .clickable(onClick = onClickWebView),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "원본 콘텐츠 보러 가기 (WebView)",
                    color = if (fromAI) ArchiveatProjectTheme.colors.black else ArchiveatProjectTheme.colors.white,
                    style = ArchiveatProjectTheme.typography.Subhead_2_semibold
                )
            }
            if (fromAI) { // AI 요약으로 들어온 뉴스레터 상세라면 다 읽었어요 버튼(추후에 분리가 필요하다면 분리)
                Spacer(Modifier.height(18.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(ArchiveatProjectTheme.colors.gray950)
                        .clickable(onClick = onClickDone),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "다 읽었어요",
                        color = ArchiveatProjectTheme.colors.white,
                        style = ArchiveatProjectTheme.typography.Subhead_2_semibold
                    )
                }
            }
            Spacer(Modifier.height(4.dp))
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
        NewsletterDetailsAIContent(
            model = NewsletterDetailsAiUiModel(
                topicText = "AI - 디자인",
                subtitle = "콘텐츠의 핵심만 AI가 요약했어요",
                imageUrl = "", // 프리뷰에서는 빈 값으로 placeholder 보이게
                tags = listOf(
                    TagUiModel(
                        text = "영감수집",
                        variant = TagVariant.Tab(HomeTabType.INSPIRATION)
                    ),
                    TagUiModel(
                        text = HomeCardType.AI_SUMMARY.label,
                        variant = TagVariant.CardType(HomeCardType.AI_SUMMARY)
                    ),
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
                memo = "가독성을 해치지 않기 위해 배경 블러(Blur) 값을 높이고, 테두리(Border)를 명확하게 사용하는 것이 핵심 차별점입니다."
            ),
            onBack = {},
            onClickWebView = {},
            onClickDone = {},
            fromAI = false
        )
    }
}
