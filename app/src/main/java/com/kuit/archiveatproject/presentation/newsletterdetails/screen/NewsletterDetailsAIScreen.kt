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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.kuit.archiveatproject.R
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
    val imageUrl: String?,
    val domainName: String?,
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
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ArchiveatProjectTheme.colors.white)
            .navigationBarsPadding()
    ) {
        BackTopBar(
            modifier = Modifier.statusBarsPadding(),
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

            when {

                // 서버 이미지 존재
                !model.imageUrl.isNullOrBlank() -> {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
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
                }

                // 썸네일 없고 Tistory
                model.domainName == "tistory" -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(219.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFFFC5100)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_logo_tistory),
                            contentDescription = "tistory logo",
                            tint = Color.Unspecified,
                        )
                    }
                }

                // 썸네일 없고 Naver
                model.domainName?.lowercase() == "naver news" -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(219.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFF2DB300)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_logo_naver),
                            contentDescription = "naver logo",
                            tint = Color.Unspecified,
                        )
                    }
                }

                // 그 외 (simple)
                else -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(219.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(ArchiveatProjectTheme.colors.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_logo_simple),
                            contentDescription = "default logo",
                            tint = Color.Unspecified,
                        )
                    }
                }
            }

            Spacer(Modifier.height(18.dp))

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

            Text(
                text = model.contentTitle,
                style = ArchiveatProjectTheme.typography.Heading_2_bold,
                color = ArchiveatProjectTheme.colors.black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.height(16.dp))

            AISummaryComponent(
                userName = model.userName,
                sections = model.aiSections
            )

            Spacer(Modifier.height(24.dp))

            MemoComponent(
                memo = model.memo,
            )

            Spacer(Modifier.height(18.dp))

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

            if (fromAI) {
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

@Preview(showBackground = true, name = "Tistory Placeholder")
@Composable
private fun NewsletterDetailsAIScreenPreview_Tistory() {
    ArchiveatProjectTheme {
        NewsletterDetailsAIContent(
            model = NewsletterDetailsAiUiModel(
                topicText = "AI - 디자인",
                imageUrl = null, // 썸네일 없음
                domainName = "tistory",
                tags = emptyList(),
                contentTitle = "Tistory 뉴스레터 예시",
                userName = "잉비",
                aiSections = emptyList(),
                memo = ""
            ),
            onBack = {},
            onClickWebView = {},
            onClickDone = {},
            fromAI = false
        )
    }
}

@Preview(showBackground = true, name = "Naver Placeholder")
@Composable
private fun NewsletterDetailsAIScreenPreview_Naver() {
    ArchiveatProjectTheme {
        NewsletterDetailsAIContent(
            model = NewsletterDetailsAiUiModel(
                topicText = "AI - 디자인",
                imageUrl = null,
                domainName = "Naver News",
                tags = emptyList(),
                contentTitle = "Naver 뉴스레터 예시",
                userName = "잉비",
                aiSections = emptyList(),
                memo = ""
            ),
            onBack = {},
            onClickWebView = {},
            onClickDone = {},
            fromAI = false
        )
    }
}

@Preview(showBackground = true, name = "Null Domain → Gray Box")
@Composable
private fun NewsletterDetailsAIScreenPreview_NullDomain() {
    ArchiveatProjectTheme {
        NewsletterDetailsAIContent(
            model = NewsletterDetailsAiUiModel(
                topicText = "AI - 디자인",
                imageUrl = null,
                domainName = null,
                tags = emptyList(),
                contentTitle = "도메인 없음 예시",
                userName = "잉비",
                aiSections = emptyList(),
                memo = ""
            ),
            onBack = {},
            onClickWebView = {},
            onClickDone = {},
            fromAI = false
        )
    }
}