package com.kuit.archiveatproject.presentation.explore.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kuit.archiveatproject.R
import com.kuit.archiveatproject.domain.entity.LlmStatus
import com.kuit.archiveatproject.presentation.explore.component.ExploreCategoryTabBar
import com.kuit.archiveatproject.presentation.explore.component.ExploreInboxComponent
import com.kuit.archiveatproject.presentation.explore.component.ExploreSearchBar
import com.kuit.archiveatproject.presentation.explore.component.ExploreSearchSuggestionPanel
import com.kuit.archiveatproject.presentation.explore.component.ExploreTopicList
import com.kuit.archiveatproject.presentation.explore.viewmodel.ExploreCategoryTabItem
import com.kuit.archiveatproject.presentation.explore.viewmodel.ExploreCategoryUiItem
import com.kuit.archiveatproject.presentation.explore.viewmodel.ExploreSearchUiState
import com.kuit.archiveatproject.presentation.explore.viewmodel.ExploreTopicUiItem
import com.kuit.archiveatproject.presentation.explore.viewmodel.ExploreUiState
import com.kuit.archiveatproject.presentation.explore.viewmodel.ExploreViewModel
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme
import kotlin.math.roundToInt

@Composable
fun ExploreScreen(
    modifier: Modifier = Modifier,
    onInboxClick: () -> Unit,
    onTopicClick: (Long, String) -> Unit,
    viewModel: ExploreViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.fetchExplore()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // TODO: 서버에서 데이터 내려주면 하드 코딩 부분 교체
    var searchUiState by remember {
        mutableStateOf(
            ExploreSearchUiState(
                recommendedKeywords = listOf(
                    "지난 주말에 저장했던 유튜브 콘텐츠 뭐였지?",
                    "AI와 관련된 데이터/보안에 대한 컨텐츠를 보고 싶어"
                ),
                recentSearches = listOf(
                    "엔비디아 관련된 내용이 있었는데…"
                )
            )
        )
    }

    ExploreContent(
        uiState = uiState,
        searchUiState = searchUiState,

        onCategorySelected = viewModel::onCategorySelected,
        onInboxClick = onInboxClick,
        onTopicClick = onTopicClick,

        onSearchFocus = {
            searchUiState = searchUiState.copy(isSearchMode = true)
        },
        onQueryChange = { query ->
            searchUiState = searchUiState.copy(query = query)
        },
        onKeywordClick = { keyword ->
            searchUiState = searchUiState.copy(
                query = keyword,
                isSearchMode = false
            )
        },
        onClearSearchMode = {
            searchUiState = searchUiState.copy(isSearchMode = false)
        },

        modifier = modifier
    )
}

@Composable
fun ExploreContent(
    uiState: ExploreUiState,
    searchUiState: ExploreSearchUiState,
    onCategorySelected: (Long) -> Unit,
    onInboxClick: () -> Unit,
    onTopicClick: (Long, String) -> Unit,
    onSearchFocus: () -> Unit,
    onQueryChange: (String) -> Unit,
    onKeywordClick: (String) -> Unit,
    onClearSearchMode: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val selectedCategory = uiState.categories
        .firstOrNull { it.id == uiState.selectedCategoryId }

    var searchBarBottomY by remember { mutableStateOf(0f) }
    var headerHeight by remember { mutableStateOf(0.dp) }

    val density = LocalDensity.current
    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ArchiveatProjectTheme.colors.white)
            .pointerInput(Unit) {
                detectTapGestures {
                    focusManager.clearFocus()
                    onClearSearchMode()
                }
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(ArchiveatProjectTheme.colors.white)
                .onGloballyPositioned { coords ->
                    with(density) {
                        headerHeight = coords.size.height.toDp()
                    }
                }
                .zIndex(1f)
        ) {
            Text(
                text = "탐색",
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                style = ArchiveatProjectTheme.typography.Heading_1_bold
            )
            Spacer(Modifier.height(12.dp))
            ExploreCategoryTabBar(
                categories = uiState.categoryTabs,
                selectedCategoryId = uiState.selectedCategoryId,
                onCategorySelected = onCategorySelected
            )

            Spacer(Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .onGloballyPositioned { coords ->
                        searchBarBottomY =
                            coords.positionInRoot().y + coords.size.height
                    }
            ) {
                ExploreSearchBar(
                    query = searchUiState.query,
                    onQueryChange = onQueryChange,
                    onSearchClick = onSearchFocus,
                    onFocus = onSearchFocus
                )
            }

            Spacer(Modifier.height(16.dp))

            ExploreInboxComponent(
                title = "방금 담은 지식",
                showLlmProcessingMessage =
                    uiState.llmStatus == LlmStatus.RUNNING ||
                            uiState.llmStatus == LlmStatus.PENDING,
                onClick = onInboxClick,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(Modifier.height(12.dp))
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                Spacer(modifier = Modifier.height(270.dp))
            }

            selectedCategory?.let { category ->
                item {
                    Spacer(Modifier.height(24.dp))

                    val totalCount = category.topics.sumOf { it.newsletterCount }

                    Text(
                        text = "${category.name} 분야에 총 ${totalCount}건의\n콘텐츠를 저장했어요",
                        style = ArchiveatProjectTheme.typography.Heading_2_semibold,
                        color = ArchiveatProjectTheme.colors.gray950,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )

                    Spacer(Modifier.height(24.dp))
                }

                item {
                    ExploreTopicList(
                        topics = category.topics,
                        onTopicClick = onTopicClick,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                    Spacer(Modifier.height(12.dp))
                }
            }
        }

        if (searchUiState.isSearchMode && searchBarBottomY > 0f) {
            ExploreSearchSuggestionPanel(
                recommendedKeywords = searchUiState.recommendedKeywords,
                recentSearches = searchUiState.recentSearches,
                onKeywordClick = onKeywordClick,
                onRemoveRecent = { /* TODO */ },
                onClearAll = { /* TODO */ },
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .offset {
                        IntOffset(
                            x = 0,
                            y = searchBarBottomY.roundToInt()
                        )
                    }
                    .zIndex(2f) // 🔑 모든 것 위
            )
        }
    }
}

private fun fakeExploreUiState(
    selectedCategoryId: Long
): ExploreUiState =
    ExploreUiState(
        llmStatus = LlmStatus.RUNNING,
        selectedCategoryId = selectedCategoryId,

        categoryTabs = listOf(
            ExploreCategoryTabItem(1, "IT/과학", R.drawable.ic_category_it),
            ExploreCategoryTabItem(2, "경제", R.drawable.ic_category_economy),
            ExploreCategoryTabItem(3, "국제", R.drawable.ic_category_internation),
            ExploreCategoryTabItem(4, "문화", R.drawable.ic_category_culture),
            ExploreCategoryTabItem(5, "생활", R.drawable.ic_category_living),
        ),

        categories = listOf(
            // ===== IT/과학 =====
            ExploreCategoryUiItem(
                id = 1,
                name = "IT/과학",
                topics = listOf(
                    ExploreTopicUiItem(1, "AI", 3, R.drawable.ic_topic_ai),
                    ExploreTopicUiItem(2, "백엔드/인프라", 2, R.drawable.ic_topic_backend),
                    ExploreTopicUiItem(3, "프론트/모바일", 2, R.drawable.ic_topic_front),
                    ExploreTopicUiItem(4, "데이터/보안", 1, R.drawable.ic_topic_security),
                    ExploreTopicUiItem(5, "테크", 4, R.drawable.ic_topic_tech),
                    ExploreTopicUiItem(99, "기타", 1, R.drawable.ic_topic_etc),
                )
            ),

            // ===== 국제 =====
            ExploreCategoryUiItem(
                id = 2,
                name = "국제",
                topics = listOf(
                    ExploreTopicUiItem(6, "지정학/외교", 3, R.drawable.ic_topic_diplomacy),
                    ExploreTopicUiItem(7, "미국/중국", 2, R.drawable.ic_topic_macroeconomy),
                    ExploreTopicUiItem(8, "글로벌비즈니스", 2, R.drawable.ic_topic_business),
                    ExploreTopicUiItem(9, "기후/에너지", 1, R.drawable.ic_topic_climate),
                    ExploreTopicUiItem(199, "기타", 1, R.drawable.ic_topic_etc),
                )
            ),

            // ===== 경제 =====
            ExploreCategoryUiItem(
                id = 3,
                name = "경제",
                topics = listOf(
                    ExploreTopicUiItem(10, "주식/투자", 4, R.drawable.ic_topic_stock),
                    ExploreTopicUiItem(11, "부동산", 2, R.drawable.ic_topic_property),
                    ExploreTopicUiItem(12, "가상화폐", 1, R.drawable.ic_topic_virtualcurrency),
                    ExploreTopicUiItem(13, "창업/스타트업", 2, R.drawable.ic_topic_startup),
                    ExploreTopicUiItem(14, "브랜드/마케팅", 3, R.drawable.ic_topic_marketing),
                    ExploreTopicUiItem(15, "거시경제", 2, R.drawable.ic_topic_macroeconomy),
                    ExploreTopicUiItem(299, "기타", 1, R.drawable.ic_topic_etc),
                )
            ),

            // ===== 문화 =====
            ExploreCategoryUiItem(
                id = 4,
                name = "문화",
                topics = listOf(
                    ExploreTopicUiItem(16, "영화/OTT", 2, R.drawable.ic_topic_movie),
                    ExploreTopicUiItem(17, "음악", 2, R.drawable.ic_topic_music),
                    ExploreTopicUiItem(18, "도서/아티클", 1, R.drawable.ic_topic_book),
                    ExploreTopicUiItem(19, "팝컬쳐/트렌드", 3, R.drawable.ic_topic_popculture),
                    ExploreTopicUiItem(20, "공간/플레이스", 1, R.drawable.ic_topic_place),
                    ExploreTopicUiItem(21, "디자인/예술", 2, R.drawable.ic_topic_design),
                    ExploreTopicUiItem(399, "기타", 1, R.drawable.ic_topic_etc),
                )
            ),
            ExploreCategoryUiItem(
                id = 5,
                name = "생활",
                topics = listOf(
                    ExploreTopicUiItem(31, "주니어/취업", 2, R.drawable.ic_topic_junior),
                    ExploreTopicUiItem(32, "업무생산성", 3, R.drawable.ic_topic_work),
                    ExploreTopicUiItem(33, "리더십/조직", 1, R.drawable.ic_topic_leadership),
                    ExploreTopicUiItem(34, "심리/마인드", 2, R.drawable.ic_topic_mind),
                    ExploreTopicUiItem(35, "건강/리빙", 4, R.drawable.ic_topic_health),
                    ExploreTopicUiItem(399, "기타", 1, R.drawable.ic_topic_etc),
                )
            )

        )
    )
private fun fakeSearchUiState() = ExploreSearchUiState(
    isSearchMode = true,
    query = "",
    recommendedKeywords = listOf(
        "지난 주말에 저장했던 유튜브 콘텐츠 뭐였지?",
        "AI와 관련된 데이터/보안에 대한 컨텐츠를 보고 싶어"
    ),
    recentSearches = listOf(
        "엔비디아 관련된 내용이 있었는데…"
    )
)

@Preview(showBackground = true)
@Composable
private fun ExploreContentPreview() {
    ArchiveatProjectTheme {

        var searchUiState by remember {
            mutableStateOf(fakeSearchUiState())
        }

        ExploreContent(
            uiState = fakeExploreUiState(1L),
            searchUiState = searchUiState,

            onCategorySelected = {},
            onInboxClick = {},
            onTopicClick = {} as (Long, String) -> Unit,

            onSearchFocus = {
                searchUiState = searchUiState.copy(isSearchMode = true)
            },
            onQueryChange = { query ->
                searchUiState = searchUiState.copy(query = query)
            },
            onKeywordClick = { keyword ->
                searchUiState = searchUiState.copy(
                    query = keyword,
                    isSearchMode = false
                )
            },
            onClearSearchMode = {
                searchUiState = searchUiState.copy(isSearchMode = false)
            }
        )
    }
}