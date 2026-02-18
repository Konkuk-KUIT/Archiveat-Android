package com.kuit.archiveatproject.presentation.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.kuit.archiveatproject.core.component.PrimaryRoundedButton
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
    initialTabName: String? = null,
    onClickCollectionCard: (Long) -> Unit = {},
    onClickAiCard: (Long) -> Unit = {},
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

    /**
     * - initialTabName == null 이면 아무 것도 하지 않음 (기존 selectedTab 유지)
     * - initialTabName이 enum으로 변환 가능하면 그 탭으로 이동
     */
    LaunchedEffect(initialTabName) {
        if (initialTabName.isNullOrBlank()) return@LaunchedEffect

        val tabType = runCatching { HomeTabType.valueOf(initialTabName) }
            .getOrNull()
            ?: return@LaunchedEffect

        // 같은 탭이면 불필요한 호출 방지
        if (uiState.selectedTab != tabType) {
            viewModel.setInitialTab(tabType)
        }

    }

    HomeScreenContent(
        uiState = uiState,
        onTabSelected = viewModel::onTabSelected,
        onRetry = viewModel::refreshHome,
        onCardClick = { card ->
            when (card.cardType) {
                HomeCardType.COLLECTION -> onClickCollectionCard(card.archiveId)
                HomeCardType.AI_SUMMARY -> onClickAiCard(card.archiveId)
            }
        },
        modifier = modifier
    )
}

@Composable
fun HomeScreenContent(
    uiState: HomeUiState,
    onTabSelected: (HomeTabType) -> Unit,
    onRetry: () -> Unit,
    onCardClick: (HomeContentCardUiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ArchiveatProjectTheme.colors.white)
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = ArchiveatProjectTheme.colors.primary
            )
        } else if (uiState.errorMessage != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = uiState.errorMessage,
                    style = ArchiveatProjectTheme.typography.Body_2_medium,
                    color = ArchiveatProjectTheme.colors.gray700
                )
                Spacer(modifier = Modifier.height(12.dp))
                PrimaryRoundedButton(
                    text = "다시 시도",
                    onClick = onRetry,
                    fullWidth = false,
                    containerColor = ArchiveatProjectTheme.colors.black,
                    cornerRadiusDp = 12
                )
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize()
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

                if (uiState.contentCards.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "a!",
                            style = ArchiveatProjectTheme.typography.Logo_regular
                                .copy(fontSize = 60.sp),
                            color = ArchiveatProjectTheme.colors.gray700
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "콘텐츠를 저장해보세요!",
                            style = ArchiveatProjectTheme.typography.Body_2_medium,
                            color = ArchiveatProjectTheme.colors.gray500
                        )
                    }
                } else {
                    HomeContentCardCarousel(
                        cards = uiState.contentCards,
                        onCenterCardClick = onCardClick
                    )
                }
            }
        }
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
            contentCards = emptyList()
        ),
        onTabSelected = {},
        onRetry = {},
        onCardClick = {}
    )
}
