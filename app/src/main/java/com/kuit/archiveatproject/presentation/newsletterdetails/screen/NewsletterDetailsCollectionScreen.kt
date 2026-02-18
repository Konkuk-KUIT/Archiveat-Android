package com.kuit.archiveatproject.presentation.newsletterdetails.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
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
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.refresh()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    if (uiState.items.isNotEmpty()) {
        NewsletterDetailsCollectionContent(
            userName = uiState.userName,
            categoryLabel = uiState.categoryLabel,
            monthLabel = uiState.monthLabel,
            items = uiState.items,
            onBack = onBack,
            onClickItem = onClickItem,
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
) {
    val collectedCount = items.size
    val progressTotal = collectedCount.coerceAtLeast(1)
    val progressCurrent = items.count { it.isChecked }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ArchiveatProjectTheme.colors.white)
            .navigationBarsPadding()
    ) {
        BackTopBar(
            onBack = onBack,
            modifier = Modifier.statusBarsPadding(),
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
                        onClick = { onClickItem(model.id) }
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
                    sourceLabel = "Instagram",
                    minutesLabel = "3분",
                    thumbnailUrl = "",
                    title = "중동전쟁 오일쇼크! (~24.10 진행상황)",
                    subtitle = "제5차 중동전쟁 발발 위기?",
                    isChecked = false
                )
            ),
            onBack = {},
            onClickItem = {}
        )
    }
}
