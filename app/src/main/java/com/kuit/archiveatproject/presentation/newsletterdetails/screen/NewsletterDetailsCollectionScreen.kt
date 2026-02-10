package com.kuit.archiveatproject.presentation.newsletterdetails.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kuit.archiveatproject.core.component.BackTopBar
import com.kuit.archiveatproject.presentation.newsletterdetails.component.CollectionComponent
import com.kuit.archiveatproject.presentation.newsletterdetails.component.CollectionComponentUiModel
import com.kuit.archiveatproject.presentation.newsletterdetails.component.CollectionTopBar
import com.kuit.archiveatproject.presentation.newsletterdetails.viewmodel.NewsletterDetailsCollectionViewModel
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NewsletterDetailsCollectionScreen(
    onBack: () -> Unit,
    onClickItem: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NewsletterDetailsCollectionViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.items.isNotEmpty()) {
        NewsletterDetailsCollectionContent(
            userName = uiState.userName,
            categoryLabel = uiState.categoryLabel,
            monthLabel = uiState.monthLabel,
            items = uiState.items,
            onBack = onBack,
            onClickItem = onClickItem,
            onToggleChecked = { id -> viewModel.toggleChecked(id) },
            modifier = modifier
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
fun NewsletterDetailsCollectionContent(
    userName: String,
    categoryLabel: String,
    monthLabel: String,
    items: List<CollectionComponentUiModel>,
    onBack: () -> Unit,
    onClickItem: (Long) -> Unit,
    modifier: Modifier = Modifier,
    onToggleChecked: (Long) -> Unit = {},
) {
    val collectedCount = items.size
    val progressTotal = collectedCount.coerceAtLeast(1)
    val progressCurrent = items.count { it.isChecked }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ArchiveatProjectTheme.colors.white)
    ) {
        BackTopBar(
            onBack = onBack,
            modifier = Modifier.padding(horizontal = 20.dp),
            height = 45
        )
        CollectionTopBar(
            userName = userName,
            categoryLabel = categoryLabel,
            collectedCount = collectedCount,
            monthLabel = monthLabel,
            progressCurrent = progressCurrent,
            progressTotal = progressTotal,
            modifier = Modifier
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .background(ArchiveatProjectTheme.colors.gray50)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 20.dp, end = 20.dp,
                    top = 14.dp, bottom = 27.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    items = items,
                    key = { item: CollectionComponentUiModel -> item.id }
                ) { model: CollectionComponentUiModel ->
                    CollectionComponent(
                        model = model,
                        onClick = { onClickItem(model.id) },
                        onToggleChecked = { id -> onToggleChecked(id) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NewsletterDetailsCollectionScreenPreview() {
    ArchiveatProjectTheme {
        NewsletterDetailsCollectionContent(
            userName = "OO",
            categoryLabel = "경제",
            monthLabel = "8월",
            items = listOf(
                CollectionComponentUiModel(
                    id = 1L,
                    categoryLabel = "경제정책",
                    sourceIcon = "", // 프리뷰에서는 빈 값이면 ImageTag가 fallback/깨질 수 있어서 주의
                    sourceLabel = "Youtube",
                    minutesLabel = "30분",
                    thumbnailUrl = "", // 빈 값이면 placeholder 박스
                    title = "\"돈도 기업도 한국을 떠났다\" 2026년 한국 경제가 진짜 무서운 이유 (김정호 교수)?",
                    subtitle = "제조업 무너짐 심각해진 분위기",
                    isChecked = false
                ),
                CollectionComponentUiModel(
                    id = 2L,
                    categoryLabel = "가상자산",
                    sourceIcon = "",
                    sourceLabel = "다음뉴스",
                    minutesLabel = "35분",
                    thumbnailUrl = "",
                    title = "비트코인 '4년 주기론' 끝나나…'업토버' 사라진 이유는 [한경 코알라]",
                    subtitle = "비트코인 팔 때 참고할 것",
                    isChecked = false
                ),
                CollectionComponentUiModel(
                    id = 3L,
                    categoryLabel = "주식",
                    sourceIcon = "",
                    sourceLabel = "브런치",
                    minutesLabel = "5분",
                    thumbnailUrl = "",
                    title = "같은 3000만원 수익, 세금은 제각각?",
                    subtitle = "같은 수익 다른 세금, 뒤틀린 자본시장 과세",
                    isChecked = false
                ),
                CollectionComponentUiModel(
                    id = 4L,
                    categoryLabel = "국제경제",
                    sourceIcon = "",
                    sourceLabel = "Instagram",
                    minutesLabel = "3분",
                    thumbnailUrl = "",
                    title = "중동전쟁 오일쇼크! (~24.10 진행상황)",
                    subtitle = "제5차 중동전쟁 발발 위기?",
                    isChecked = false
                ),
                CollectionComponentUiModel(
                    id = 5L,
                    categoryLabel = "국제경제",
                    sourceIcon = "",
                    sourceLabel = "Instagram",
                    minutesLabel = "3분",
                    thumbnailUrl = "",
                    title = "중동전쟁 오일쇼크! (~24.10 진행상황)",
                    subtitle = "제5차 중동전쟁 발발 위기?",
                    isChecked = false
                )
            ),
            onBack = {},
            onClickItem = {},
            onToggleChecked = {}
        )
    }
}
