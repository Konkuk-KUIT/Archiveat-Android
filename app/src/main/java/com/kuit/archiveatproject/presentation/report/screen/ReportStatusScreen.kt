package com.kuit.archiveatproject.presentation.report.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kuit.archiveatproject.core.component.BackTopBar
import com.kuit.archiveatproject.presentation.report.component.ReportButtonComponent
import com.kuit.archiveatproject.presentation.report.component.status.CircularProgressComponent
import com.kuit.archiveatproject.presentation.report.component.status.RecentNewsletterComponent
import com.kuit.archiveatproject.presentation.report.component.status.ReportStatusBoxComponent
import com.kuit.archiveatproject.presentation.report.component.status.StatusTextTag
import com.kuit.archiveatproject.presentation.report.model.RecentReadNewsletterUiItem
import com.kuit.archiveatproject.presentation.report.model.ReportBalanceUiState
import com.kuit.archiveatproject.presentation.report.model.ReportStatusViewModel
import com.kuit.archiveatproject.presentation.report.model.ReportUiState
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun ReportStatusScreen(
    onBackClick: () -> Unit,
    onClickNewsletter: () -> Unit,
    onClickExplore: () -> Unit,

    viewModel: ReportStatusViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchReportStatus()
    }

    BackHandler { onBackClick() }

    ReportStatusContent(
        uiState = uiState,
        onBackClick = onBackClick,
        onClickNewsletter = onClickNewsletter,
        onClickExplore = onClickExplore,
        modifier = modifier
    )
}

@Composable
fun ReportStatusContent(
    uiState: ReportUiState,
    onBackClick: () -> Unit,
    onClickNewsletter: () -> Unit,
    onClickExplore: () -> Unit,

    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ArchiveatProjectTheme.colors.gray50)
    ) {
        BackTopBar(title = "핵심 소비 현황", onBack = onBackClick)
        
        LazyColumn(
            modifier = Modifier.weight(1f),
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(ArchiveatProjectTheme.colors.white)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(Modifier.height(21.dp))

                        StatusTextTag(
                            "이번 주 달성률",
                            ArchiveatProjectTheme.colors.primary
                        )

                        Spacer(Modifier.height(24.dp))

                        CircularProgressComponent(
                            percentage = uiState.readPercentage
                        )

                        Spacer(Modifier.height(32.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            ReportStatusBoxComponent(
                                text = "총 저장",
                                count = uiState.totalSavedCount,
                                textColor_1 = ArchiveatProjectTheme.colors.gray600,
                                textColor_2 = ArchiveatProjectTheme.colors.black,
                                boxColor = ArchiveatProjectTheme.colors.gray50,
                                borderColor = ArchiveatProjectTheme.colors.gray100,
                                modifier = Modifier.weight(1f)
                            )

                            ReportStatusBoxComponent(
                                text = "읽음 완료",
                                count = uiState.totalReadCount,
                                textColor_1 = ArchiveatProjectTheme.colors.primary.copy(alpha = 0.7F),
                                textColor_2 = ArchiveatProjectTheme.colors.primary,
                                boxColor = ArchiveatProjectTheme.colors.primary.copy(alpha = 0.1F),
                                borderColor = Color(0xFFE7DCFA),
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Spacer(Modifier.height(24.dp))
                    }
                }
            }

            item {
                Text(
                    text = "최근 학습 기록",
                    style = ArchiveatProjectTheme.typography.Subhead_2_semibold,
                    color = ArchiveatProjectTheme.colors.gray800,
                    modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 12.dp)
                )
            }

            items(uiState.recentReadNewsletters) { item ->
                RecentNewsletterComponent(
                    item = item,
                    serverTimestamp = item.lastViewedAt,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(ArchiveatProjectTheme.colors.white)
                .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ReportButtonComponent(
                    text = "뉴스레터 보러가기",
                    true,
                    onClick = onClickNewsletter,
                    modifier = Modifier.weight(1f)
                )

                ReportButtonComponent(
                    text = "콘텐츠 탐색하기",
                    true,
                    onClick = onClickExplore,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReportStatusContentPreview() {
    ArchiveatProjectTheme {
        ReportStatusContent(
            uiState = ReportUiState(
                referenceDate = "2026-01-25T13:14:06.480115",
                totalSavedCount = 120,
                totalReadCount = 42,
                readPercentage = 70,
                balance = ReportBalanceUiState(
                    lightPercentage = 50,
                    deepPercentage = 50,
                    nowPercentage = 50,
                    futurePercentage = 50
                ),
                interestGaps = emptyList(),
                recentReadNewsletters = listOf(
                    RecentReadNewsletterUiItem(
                        id = 1,
                        title = "2025 UI 디자인 트렌드: 글래스 모피즘",
                        categoryName = "디자인",
                        lastViewedAt = "2026-01-25"
                    ),
                    RecentReadNewsletterUiItem(
                        id = 2,
                        title = "비트코인 4년 주기론 끝났나?",
                        categoryName = "경제",
                        lastViewedAt = "2026-01-24"
                    )
                ),
                weeklyFeedbackWeekLabel = "",
                weeklyFeedbackBody = ""
            ),
            onBackClick = {},
            onClickNewsletter = {},
            onClickExplore = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}
