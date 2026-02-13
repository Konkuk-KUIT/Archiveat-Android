package com.kuit.archiveatproject.presentation.report.screen

import android.R.attr.bottom
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kuit.archiveatproject.presentation.report.component.ReportChartComponent
import com.kuit.archiveatproject.presentation.report.component.WeeklyAiFeedbackSection
import com.kuit.archiveatproject.presentation.report.model.InterestGapUiItem
import com.kuit.archiveatproject.presentation.report.model.MainInterestGapUiItem
import com.kuit.archiveatproject.presentation.report.model.ReportBalanceUiState
import com.kuit.archiveatproject.presentation.report.model.ReportUiState
import com.kuit.archiveatproject.presentation.report.model.ReportViewModel
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun ReportScreen(
    viewModel: ReportViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchReport()
    }

    ReportScreenContent(
        uiState = uiState,
        modifier = modifier
    )
}

@Composable
fun ReportScreenContent(
    uiState: ReportUiState,
    modifier: Modifier = Modifier
) {
    if (uiState.isError) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(ArchiveatProjectTheme.colors.white)
                .padding(20.dp)
        ) {
            Text(
                text = uiState.errorMessage ?: "리포트를 불러오지 못했어요.",
                style = ArchiveatProjectTheme.typography.Body_1_semibold,
                color = ArchiveatProjectTheme.colors.gray800
            )
        }
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ArchiveatProjectTheme.colors.white)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = ArchiveatProjectTheme.colors.white)
                .padding(start = 20.dp, top = 12.dp, bottom = 12.dp)
        ) {
            Text(
                text = "리포트",
                style = ArchiveatProjectTheme.typography.Heading_1_bold,
                color = ArchiveatProjectTheme.colors.black
            )
        }

        Spacer(Modifier.height(16.dp))
        WeeklyAiFeedbackSection(
            dateRange = uiState.weeklyFeedbackWeekLabel,
            body = uiState.weeklyFeedbackBody,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(ArchiveatProjectTheme.colors.gray50)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                item {
                    Spacer(Modifier.height(24.dp))
                    ReportChartComponent(
                        totalSavedCount = uiState.totalSavedCount,
                        totalReadCount = uiState.totalReadCount,
                        readPercentage = uiState.readPercentage,
                        lightPercentage = uiState.balance.lightPercentage,
                        nowPercentage = uiState.balance.nowPercentage,
                        interestGaps = uiState.interestGaps
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun ReportScreenPreview() {
    ArchiveatProjectTheme {
        ReportScreenContent(
            uiState = ReportUiState(
                referenceDate = "2026-01-25T13:14:06.480115",
                totalSavedCount = 120,
                totalReadCount = 42,
                readPercentage = 35,
                balance = ReportBalanceUiState(
                    lightPercentage = 30,
                    deepPercentage = 70,
                    nowPercentage = 50,
                    futurePercentage = 50
                ),
                interestGaps = listOf(
                    MainInterestGapUiItem(
                        topicName = "건강",
                        savedCount = 50,
                        readCount = 5
                    ),
                    MainInterestGapUiItem(
                        topicName = "AI",
                        savedCount = 30,
                        readCount = 25
                    )
                ),
                weeklyFeedbackWeekLabel = "1월 19일-1월 25일",
                weeklyFeedbackBody = "지난 주 AI 분야에 80%의 시간을 사용하셨네요.\n저장 분야를 보니 건강에도 관심이 많으신데,\n관련 콘텐츠를 확인해볼까요?"
            )
        )
    }
}
