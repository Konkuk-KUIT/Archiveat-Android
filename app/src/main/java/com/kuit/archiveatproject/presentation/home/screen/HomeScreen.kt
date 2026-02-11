package com.kuit.archiveatproject.presentation.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.kuit.archiveatproject.core.component.TopLogoBar
import com.kuit.archiveatproject.domain.entity.HomeCardType
import com.kuit.archiveatproject.domain.entity.HomeTab
import com.kuit.archiveatproject.domain.entity.HomeTabType
import com.kuit.archiveatproject.presentation.home.component.GreetingBar
import com.kuit.archiveatproject.presentation.home.component.HomeCategoryTabBar
import com.kuit.archiveatproject.presentation.home.component.HomeContentCardCarousel
import com.kuit.archiveatproject.presentation.home.model.GreetingUiModel
import com.kuit.archiveatproject.presentation.home.model.HomeContentCardUiModel
import com.kuit.archiveatproject.presentation.home.viewmodel.HomeUiState
import com.kuit.archiveatproject.presentation.home.viewmodel.HomeViewModel
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.refreshHome()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    HomeScreenContent(
        uiState = uiState,
        onTabSelected = viewModel::onTabSelected,
        onCardClick = { /* navigate */ },
        modifier = modifier
    )
}

@Composable
fun HomeScreenContent(
    uiState: HomeUiState,
    onTabSelected: (HomeTabType) -> Unit,
    onCardClick: (HomeContentCardUiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ArchiveatProjectTheme.colors.white)
    ) {
        TopLogoBar(
            modifier = Modifier.padding(top = 11.dp)
        )
        uiState.greeting?.let {
            GreetingBar(
                nickname = uiState.nickname,
                firstGreetingMessage = it.firstMessage,
                secondGreetingMessage = it.secondMessage,
            )
        }

        HomeCategoryTabBar(
            tabs = uiState.tabs,
            selectedTab = uiState.selectedTab,
            onTabSelected = onTabSelected
        )

        Spacer(Modifier.height(27.dp))

        HomeContentCardCarousel(
            cards = uiState.contentCards,
            onCenterCardClick = onCardClick
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreenContent(
        uiState = HomeUiState(
            nickname = "홍길동",
            greeting = GreetingUiModel(
                firstMessage = "좋은 아침이에요!",
                secondMessage = "오늘도 한 걸음 성장해볼까요?"
            ),
            tabs = listOf(
                HomeTab(
                    type = HomeTabType.ALL,
                    label = "전체",
                    subMessage = "수집한 자료를 기반으로 발행된, 나만의 지식 뉴스레터"
                ),
                HomeTab(
                    type = HomeTabType.INSPIRATION,
                    label = "영감수집",
                    subMessage = "잠깐의 틈을 채워줄, 현재의 관심사와 맞닿은 인사이트 "
                ),
                HomeTab(
                    type = HomeTabType.DEEP_DIVE,
                    label = "집중탐구",
                    subMessage = "관심 주제를 깊이 파고들어, 온전히 내 것으로 만드는 시간 "
                ),
                HomeTab(
                    type = HomeTabType.GROWTH,
                    label = "성장한입",
                    subMessage = "매일 조금씩 지식을 채우며 당신의 성장을 체감해 보세요 "
                ),
                HomeTab(
                    type = HomeTabType.VIEW_EXPANSION,
                    label = "관점확장",
                    subMessage = "새로운 시각으로 세상을 바라볼 수 있는 깊이 있는 통찰 "
                )
            ),
            selectedTab = HomeTabType.ALL,
            contentCards = listOf(
                HomeContentCardUiModel(
                    archiveId = 1L,
                    tabType = HomeTabType.ALL,
                    tabLabel = "전체",
                    cardType = HomeCardType.AI_SUMMARY,
                    title = "2025 UI 디자인 트렌드: 글래스모피즘의 귀환",
                    smallCardSummary = "저장한 'Design' 아티클 요약",
                    mediumCardSummary = "UI 디자인 트렌드를 한눈에 정리한 핵심 요약.",
                    imageUrls = listOf("https://picsum.photos/id/10/800/600")
                ),
                HomeContentCardUiModel(
                    archiveId = 2L,
                    tabType = HomeTabType.ALL,
                    tabLabel = "전체",
                    cardType = HomeCardType.COLLECTION,
                    title = "AI 에이전트, 검색을 넘어 행동으로",
                    smallCardSummary = "저장한 'AI' 아티클 요약",
                    mediumCardSummary = "AI 에이전트가 실제 업무 흐름에서 하는 일과 변화 포인트.",
                    imageUrls = listOf(
                        "https://picsum.photos/id/11/800/600",
                        "https://picsum.photos/id/12/800/600"
                    )
                )
            )
        ),
        onTabSelected = {},
        onCardClick = {}
    )
}
