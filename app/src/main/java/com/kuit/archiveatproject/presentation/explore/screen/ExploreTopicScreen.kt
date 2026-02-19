package com.kuit.archiveatproject.presentation.explore.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.kuit.archiveatproject.core.component.BackTopBar
import com.kuit.archiveatproject.domain.entity.ExploreTopicNewsletterItem
import com.kuit.archiveatproject.presentation.explore.component.ExploreSearchBar
import com.kuit.archiveatproject.presentation.explore.component.ExploreSearchSuggestionPanel
import com.kuit.archiveatproject.presentation.explore.component.ExploreTopicContentCard
import com.kuit.archiveatproject.presentation.explore.component.ExploreTopicFilterChip
import com.kuit.archiveatproject.presentation.explore.viewmodel.ExploreSearchUiState
import com.kuit.archiveatproject.presentation.explore.viewmodel.ExploreTopicDetailViewModel
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme
import java.time.Instant

@Composable
fun ExploreTopicDetailScreen(
    modifier: Modifier = Modifier,
    topicId: Long,
    topicName: String,
    onBack: () -> Unit,
    onClickOutlink: (Long, Boolean) -> Unit,
    onSearchSubmit: (String) -> Unit,
    viewModel: ExploreTopicDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    val focusManager = LocalFocusManager.current
    val listState = rememberLazyListState()

    var searchBarBottomY by remember { mutableStateOf(0) }
    var rootOffsetY by remember { mutableStateOf(0) }

    var searchUiState by remember {
        mutableStateOf(
            ExploreSearchUiState(
                recommendedKeywords = listOf(
                    "최근 저장한 콘텐츠 다시 보기",
                    "AI 관련 콘텐츠만 보고 싶어"
                ),
                recentSearches = listOf("엔비디아")
            )
        )
    }

    val newsletters = uiState.newsletters.sortedForExplore()

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.fetchNewsletters()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ArchiveatProjectTheme.colors.white)
            .onGloballyPositioned { coords ->
                // 루트 Box의 window 기준 위치 저장
                rootOffsetY = coords.positionInWindow().y.toInt()
            }
    ) {

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize()
        ) {

            item {
                BackTopBar(
                    title = topicName,
                    onBack = onBack,
                    height = 56,
                )
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 9.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned { coords ->
                                val position = coords.positionInWindow()
                                searchBarBottomY =
                                    (position.y + coords.size.height).toInt()
                            }
                    ) {
                        ExploreSearchBar(
                            query = searchUiState.query,
                            onQueryChange = {
                                searchUiState = searchUiState.copy(query = it)
                            },
                            onSearchClick = {
                                onSearchSubmit(searchUiState.query)
                                focusManager.clearFocus()
                                searchUiState = searchUiState.copy(isSearchMode = false)
                            },
                            onFocus = {
                                searchUiState = searchUiState.copy(isSearchMode = true)
                            }
                        )
                    }

                    Spacer(Modifier.height(15.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        ExploreTopicFilterChip(text = "시간", onClick = {})
                        ExploreTopicFilterChip(text = "유형", onClick = {})
                        ExploreTopicFilterChip(text = "저장일", onClick = {})
                    }
                }
            }

            item {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 10000.dp),
                    userScrollEnabled = false,
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(newsletters, key = { it.userNewsletterId }) { item ->
                        ExploreTopicContentCard(
                            title = item.title,
                            thumbnailUrl = item.thumbnailUrl,
                            domainName = item.domainName,
                            isRead = item.isRead,
                            onClickCard = { onClickOutlink(item.userNewsletterId, item.isRead) },
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                                .fillMaxWidth(),
                        )
                    }
                }
            }
        }

        if (searchUiState.isSearchMode && searchBarBottomY > 0) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        focusManager.clearFocus()
                        searchUiState = searchUiState.copy(isSearchMode = false)
                    }
                    .zIndex(9f)
            )

            ExploreSearchSuggestionPanel(
                recommendedKeywords = searchUiState.recommendedKeywords,
                recentSearches = searchUiState.recentSearches,
                onKeywordClick = { keyword ->
                    searchUiState = searchUiState.copy(
                        query = keyword,
                        isSearchMode = false
                    )
                    focusManager.clearFocus()
                    onSearchSubmit(keyword)
                },
                onRemoveRecent = {},
                onClearAll = {},
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .offset {
                        IntOffset(
                            x = 0,
                            y = searchBarBottomY - rootOffsetY
                        )
                    }
                    .zIndex(10f)
            )
        }
    }
}

/** 정렬: 안읽음 -> 읽음, 각 그룹 내부는 createdAt desc(null은 가장 오래된 취급) */
private fun List<ExploreTopicNewsletterItem>.sortedForExplore(): List<ExploreTopicNewsletterItem> {
    fun ExploreTopicNewsletterItem.createdAtInstantOrMin(): Instant {
        val raw = createdAt ?: return Instant.MIN
        return runCatching { Instant.parse(raw) }.getOrDefault(Instant.MIN)
    }

    val (read, unread) = this.partition { it.isRead }

    val unreadSorted = unread.sortedByDescending { it.createdAtInstantOrMin() }
    val readSorted = read.sortedByDescending { it.createdAtInstantOrMin() }

    return unreadSorted + readSorted
}

//@Preview(showBackground = true)
//@Composable
//private fun Preview_ExploreTopicDetailScreen_Default() {
//    ArchiveatProjectTheme {
//        ExploreTopicDetailScreen(
//            topicId = 1L,
//            topicName = "테크",
//            newsletters = listOf(
//                ExploreTopicNewsletterItem(
//                    userNewsletterId = 101L,
//                    title = "디지털 시대를 설계하다 · 컴퓨터가 바꾸는 일상의 풍경",
//                    thumbnailUrl = "https://picsum.photos/400/300?1",
//                    isRead = false,
//                    createdAt = Instant.now().toString()
//                ),
//                ExploreTopicNewsletterItem(
//                    userNewsletterId = 102L,
//                    title = "알고리즘을 이해하다 · 추천 시스템 뒤에 숨은 계산법",
//                    thumbnailUrl = "https://picsum.photos/400/300?2",
//                    isRead = false,
//                    createdAt = Instant.now().minusSeconds(3600).toString()
//                ),
//                ExploreTopicNewsletterItem(
//                    userNewsletterId = 103L,
//                    title = "데이터의 바다를 항해하다 · 빅데이터 분석으로 본 트렌드",
//                    thumbnailUrl = null,
//                    isRead = true,
//                    createdAt = Instant.now().minusSeconds(7200).toString()
//                ),
//                ExploreTopicNewsletterItem(
//                    userNewsletterId = 104L,
//                    title = "인공지능과 함께 일하다 · 생산성을 높이는 스마트 워크",
//                    thumbnailUrl = "https://picsum.photos/400/300?4",
//                    isRead = true,
//                    createdAt = Instant.now().minusSeconds(9000).toString()
//                ),
//            ),
//            onBack = {},
//            onClickOutlink = {},
//            onSearchSubmit = {},
//        )
//    }
//}
