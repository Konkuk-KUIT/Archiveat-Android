package com.kuit.archiveatproject.presentation.explore.screen

import com.kuit.archiveatproject.presentation.explore.component.QuickSavedKnowledgeCard
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.R
import com.kuit.archiveatproject.presentation.explore.component.*
import com.kuit.archiveatproject.presentation.explore.model.ExploreCategoryUi
import com.kuit.archiveatproject.presentation.explore.model.ExploreTopTabUi

@Composable
fun ExploreScreen(
    modifier: Modifier = Modifier,
    onCategoryClick: (String) -> Unit = {},
    onQuickSavedClick: () -> Unit = {}
) {
    val topTabs = remember {
        listOf(
            ExploreTopTabUi("it_science", "IT/과학", R.drawable.ic_tab_it_science),
            ExploreTopTabUi("world", "국제", R.drawable.ic_tab_world),
            ExploreTopTabUi("economy", "경제", R.drawable.ic_tab_economy),
            ExploreTopTabUi("culture", "문화", R.drawable.ic_tab_culture),
            ExploreTopTabUi("life", "생활", R.drawable.ic_tab_life),
        )
    }
    var selectedTabId by remember { mutableStateOf("it_science") }

    val categories = remember(selectedTabId) {
        listOf(
            ExploreCategoryUi("tech", "테크", "2건의 아티클", R.drawable.ic_bot_tech),
            ExploreCategoryUi("it_company", "IT기업", "2건의 아티클", R.drawable.ic_bot_it),
            ExploreCategoryUi("game", "게임", "2건의 아티클", R.drawable.ic_bot_game),
            ExploreCategoryUi("science", "과학", "2건의 아티클", R.drawable.ic_bot_science),
        )
    }

    var query by remember { mutableStateOf("") }
    var isSearchPanelOpen by remember { mutableStateOf(false) }

    val recommended = remember {
        listOf(
            "지난 주말에 저장했던 유튜브 컨텐츠 뭐였지?",
            "지난 주말에 저장했던 유튜브 컨텐츠 뭐였지?",
            "AI와 관련된 데이터/보안에 대한 컨텐츠를 보고 싶어"
        )
    }

    var history by remember { mutableStateOf(listOf("엔비디아 관련된 내용이 있었는데...")) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        contentPadding = PaddingValues(top = 18.dp, bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "탐색",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )
        }

        item {
            ExploreTopTabsRow(
                tabs = topTabs,
                selectedTabId = selectedTabId,
                onTabClick = { selectedTabId = it }
            )
        }

        item {
            ExploreSearchTextField(
                query = query,
                onQueryChange = {
                    query = it
                    isSearchPanelOpen = true
                },
                placeholder = "기억나지 않는 아티클을 검색해보세요",
                onClear = { query = "" },
                onFocusChange = { focused ->
                    isSearchPanelOpen = focused
                }
            )
        }

        item {
            if (isSearchPanelOpen) {
                ExploreSearchPanel(
                    recommendedQueries = recommended,
                    history = history,
                    onClickRecommended = { text ->
                        query = text
                        if (text.isNotBlank() && !history.contains(text)) {
                            history = listOf(text) + history
                        }

                    },
                    onClickHistory = { text ->
                        query = text

                    },
                    onRemoveHistoryItem = { text ->
                        history = history.filterNot { it == text }
                    },
                    onClearAllHistory = {
                        history = emptyList()
                    }
                )
            }
        }

        item {
            QuickSavedKnowledgeCard(
                title = "방금 담은 지식",
                onClick = onQuickSavedClick
            )
        }

        item {
            Text(
                text = "IT/과학 분야에 총 233건의\n콘텐츠를 저장했어요",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.padding(top = 6.dp)
            )
        }

        items(categories) { cat ->
            ExploreCategoryCard(
                title = cat.title,
                subtitle = cat.subtitle,
                iconRes = cat.iconRes,
                onClick = { onCategoryClick(cat.id) }
            )
        }
    }
}
