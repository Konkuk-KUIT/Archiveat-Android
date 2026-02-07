package com.kuit.archiveatproject.presentation.explore.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kuit.archiveatproject.R
import com.kuit.archiveatproject.domain.entity.LlmStatus
import com.kuit.archiveatproject.presentation.explore.component.ExploreCategoryTabBar
import com.kuit.archiveatproject.presentation.explore.component.ExploreInboxComponent
import com.kuit.archiveatproject.presentation.explore.component.ExploreSearchBar
import com.kuit.archiveatproject.presentation.explore.component.ExploreTopicList
import com.kuit.archiveatproject.presentation.explore.viewmodel.ExploreCategoryTabItem
import com.kuit.archiveatproject.presentation.explore.viewmodel.ExploreCategoryUiItem
import com.kuit.archiveatproject.presentation.explore.viewmodel.ExploreTopicUiItem
import com.kuit.archiveatproject.presentation.explore.viewmodel.ExploreUiState
import com.kuit.archiveatproject.presentation.explore.viewmodel.ExploreViewModel
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun ExploreScreen(
    modifier: Modifier = Modifier,
    viewModel: ExploreViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ExploreContent(
        uiState = uiState,
        onCategorySelected = viewModel::onCategorySelected,
        onInboxClick = { /* TODO */ },
        onTopicClick = { /* TODO */ },
        modifier = modifier
    )
}

@Composable
fun ExploreContent(
    uiState: ExploreUiState,
    onCategorySelected: (Long) -> Unit,
    onInboxClick: () -> Unit,
    onTopicClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val selectedCategory = uiState.categories
        .firstOrNull { it.id == uiState.selectedCategoryId }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ArchiveatProjectTheme.colors.white)
    ) {
        Row(
            modifier = Modifier
                .padding(top = 13.dp, bottom = 13.dp, start = 20.dp)
        ) {
            Text(
                text = "탐색",
                style = ArchiveatProjectTheme.typography.Heading_1_bold,
                color = ArchiveatProjectTheme.colors.gray950
            )
        }

        ExploreCategoryTabBar(
            categories = uiState.categoryTabs,
            selectedCategoryId = uiState.selectedCategoryId,
            onCategorySelected = onCategorySelected
        )

        LazyColumn {

            item {
                Spacer(Modifier.height(24.dp))

                ExploreSearchBar(
                    query = "",
                    onQueryChange = {},
                    onSearchClick = {},
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            item {
                Spacer(Modifier.height(16.dp))

                ExploreInboxComponent(
                    title = "방금 담은 지식",
                    showLlmProcessingMessage = uiState.llmStatus == LlmStatus.RUNNING,
                    onClick = onInboxClick,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
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
                }

                item {
                    Spacer(Modifier.height(24.dp))

                    ExploreTopicList(
                        topics = category.topics,
                        onTopicClick = onTopicClick,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }
            }
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

@Preview(showBackground = true)
@Composable
private fun ExploreContentInteractivePreview() {
    ArchiveatProjectTheme {

        var selectedCategoryId by remember { mutableStateOf(1L) }

        ExploreContent(
            uiState = fakeExploreUiState(selectedCategoryId),
            onCategorySelected = { selectedCategoryId = it },
            onInboxClick = {},
            onTopicClick = {},
        )
    }
}