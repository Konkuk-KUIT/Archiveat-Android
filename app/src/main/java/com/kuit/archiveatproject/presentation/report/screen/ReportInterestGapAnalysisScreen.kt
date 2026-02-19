package com.kuit.archiveatproject.presentation.report.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kuit.archiveatproject.core.component.BackTopBar
import com.kuit.archiveatproject.core.component.PrimaryRoundedButton
import com.kuit.archiveatproject.presentation.report.component.interestgap.InterestGapBubbleChart
import com.kuit.archiveatproject.presentation.report.component.interestgap.InterestGapSelectedDetail
import com.kuit.archiveatproject.presentation.report.model.InterestGapTopicUiModel
import com.kuit.archiveatproject.presentation.report.model.ReportInterestGapAnalysisUiState
import com.kuit.archiveatproject.presentation.report.model.ReportInterestGapAnalysisViewModel
import com.kuit.archiveatproject.presentation.report.model.top4ByGap
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun ReportInterestGapAnalysisScreen(
    padding: PaddingValues,
    onBack: () -> Unit,
    onClickTopicShortcut: (topicId: Long, topicName: String) -> Unit,
    viewModel: ReportInterestGapAnalysisViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding) // MainActivity Scaffold에서 내려준 padding
            .background(Color.White)
    ) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = ArchiveatProjectTheme.colors.primary
                )
            }

            uiState.isError -> {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = uiState.errorMessage ?: "데이터를 불러오지 못했어요.",
                        style = ArchiveatProjectTheme.typography.Body_2_medium,
                        color = ArchiveatProjectTheme.colors.gray700
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    PrimaryRoundedButton(
                        text = "다시 시도",
                        onClick = { viewModel.fetchInterestGap() },
                        fullWidth = false,
                        containerColor = ArchiveatProjectTheme.colors.black,
                        cornerRadiusDp = 12
                    )
                }
            }

            else -> {
                ReportInterestGapAnalysisContent(
                    uiState = uiState,
                    onBack = onBack,
                    onClickTopicShortcut = onClickTopicShortcut
                )
            }
        }
    }
}

@Composable
private fun BoxScope.ReportInterestGapAnalysisContent(
    uiState: ReportInterestGapAnalysisUiState,
    onBack: () -> Unit,
    onClickTopicShortcut: (topicId: Long, topicName: String) -> Unit
) {
    val topics = uiState.topics
    val top4 = remember(topics) { topics.top4ByGap() }
    var selectedId by remember { mutableStateOf<Long?>(null) }

    LaunchedEffect(top4) {
        if (selectedId == null) selectedId = top4.firstOrNull()?.id
    }

    val selected = remember(top4, selectedId) {
        top4.firstOrNull { it.id == selectedId } ?: top4.firstOrNull()
    }

    val selectedTagBgColor = when (top4.indexOfFirst { it.id == selectedId }) {
        0 -> ArchiveatProjectTheme.colors.primary
        1 -> ArchiveatProjectTheme.colors.sub_2
        2 -> ArchiveatProjectTheme.colors.sub_1
        else -> ArchiveatProjectTheme.colors.sub_3
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        BackTopBar(
            title = "관심사 갭 분석",
            onBack = onBack,
            height = 56
        )
        LazyColumn (
            modifier = Modifier
                .fillMaxHeight()
                .background(ArchiveatProjectTheme.colors.white)
        ){
            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(32.dp))

                    Text(
                        text = "무엇이 쌓여가고 있나요?",
                        style = ArchiveatProjectTheme.typography.Heading_2_bold,
                        color = ArchiveatProjectTheme.colors.gray950
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "원을 눌러 상세 현황을 확인해보세요.",
                        style = ArchiveatProjectTheme.typography.Body_2_medium,
                        color = ArchiveatProjectTheme.colors.gray600
                    )
                    Spacer(Modifier.height(8.dp))

                    InterestGapBubbleChart(
                        topicsTop4 = top4,
                        selectedTopicId = selectedId,
                        onSelectTopic = { selectedId = it }
                    )

                    Spacer(Modifier.height(40.dp))

                    selected?.let {
                        InterestGapSelectedDetail(selected = it, tagBgColor = selectedTagBgColor)
                    }

                    Spacer(Modifier.height(90.dp))
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 14.dp)
    ) {
        PrimaryRoundedButton(
            text = selected?.name?.let { "'$it' 바로가기" } ?: "바로가기",
            onClick = {
                selected?.topicId?.let { topicId ->
                    onClickTopicShortcut(topicId, selected.name)
                }
            },
            enabled = selected?.topicId != null,
            containerColor = ArchiveatProjectTheme.colors.black,
            cornerRadiusDp = 12
        )
    }
}

@Preview
@Composable
private fun ReportInterestGapAnalysisScreenPrev() {
    ArchiveatProjectTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            ReportInterestGapAnalysisContent(
                uiState = previewInterestGapUiState(),
                onBack = {},
                onClickTopicShortcut = { _, _ -> }
            )
        }
    }
}

private fun previewInterestGapUiState() = ReportInterestGapAnalysisUiState(
    topics = listOf(
        InterestGapTopicUiModel(
            id = 1L,
            name = "테크",
            savedCount = 30,
            readCount = 8
        ),
        InterestGapTopicUiModel(
            id = 2L,
            name = "경제",
            savedCount = 25,
            readCount = 11
        ),
        InterestGapTopicUiModel(
            id = 3L,
            name = "디자인",
            savedCount = 18,
            readCount = 9
        ),
        InterestGapTopicUiModel(
            id = 4L,
            name = "커리어",
            savedCount = 16,
            readCount = 6
        )
    )
)
