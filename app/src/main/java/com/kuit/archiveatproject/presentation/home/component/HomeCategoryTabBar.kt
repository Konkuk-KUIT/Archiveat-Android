package com.kuit.archiveatproject.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.domain.model.HomeTabType
import com.kuit.archiveatproject.presentation.home.model.HomeTabUiModel
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun HomeCategoryTabBar(
    tabs: List<HomeTabUiModel>,
    selectedTab: HomeTabType,
    onTabSelected: (HomeTabType) -> Unit,
    modifier: Modifier = Modifier
){
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .background(ArchiveatProjectTheme.colors.white),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(
            space = 10.dp,
            alignment = Alignment.CenterHorizontally
        )
    ) {
        items(tabs) { tab ->
            CategoryTabItem(
                label = tab.label,
                tabType = tab.type,
                selected = tab.type == selectedTab,
                onClick = { onTabSelected(tab.type) }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeCategoryTabBarPreview() {
    HomeCategoryTabBar(
        tabs = listOf(
            HomeTabUiModel(HomeTabType.ALL, "전체", "흩어진 모든 지식을 한눈에"),
            HomeTabUiModel(HomeTabType.INSPIRATION, "영감수집", "잠깐의 틈을 채워줄 인사이트"),
            HomeTabUiModel(HomeTabType.DEEP_DIVE, "집중탐구", "깊이 파고드는 시간"),
            HomeTabUiModel(HomeTabType.GROWTH, "성장한입", "잠깐의 틈을 채워줄, 현재의 관심사와 맞닿은 인사이트"),
            HomeTabUiModel(HomeTabType.VIEW_EXPANSION, "관점확장", "생각의 크기를 키워주는 깊이 있는 통찰"),
        ),
        selectedTab = HomeTabType.INSPIRATION,
        onTabSelected = {}
    )
}
