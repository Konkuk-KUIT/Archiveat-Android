package com.kuit.archiveatproject.presentation.report.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kuit.archiveatproject.presentation.report.component.ReportChartComponent
import com.kuit.archiveatproject.presentation.report.component.WeeklyAiFeedbackSection
import com.kuit.archiveatproject.presentation.report.model.ReportUiState
import com.kuit.archiveatproject.presentation.report.model.ReportViewModel
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun ReportScreen(
    padding: PaddingValues,
    onClickStatus: () -> Unit,
    onClickBalance: () -> Unit,
    viewModel: ReportViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        android.util.Log.d("ReportScreen", "LaunchedEffect called")
        viewModel.fetchReport()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ArchiveatProjectTheme.colors.gray50)
    ) {
        ReportScreenContent(
            uiState = uiState,
            padding = padding,
            onClickStatus = onClickStatus,
            onClickBalance = onClickBalance
        )
    }
}

@Composable
fun ReportScreenContent(
    uiState: ReportUiState,
    padding: PaddingValues,
    onClickStatus: () -> Unit,
    onClickBalance: () -> Unit
) {
    val topPadding = padding.calculateTopPadding()
    val bottomPadding = padding.calculateBottomPadding()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(ArchiveatProjectTheme.colors.white)
                .padding(top = topPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
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
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(ArchiveatProjectTheme.colors.gray50),
            contentPadding = PaddingValues(
                bottom = bottomPadding + 24.dp
            )
        ) {
            item {
                Spacer(Modifier.height(24.dp))

                ReportChartComponent(
                    totalSavedCount = uiState.totalSavedCount,
                    totalReadCount = uiState.totalReadCount,
                    readPercentage = uiState.readPercentage,
                    lightPercentage = uiState.balance.lightPercentage,
                    nowPercentage = uiState.balance.nowPercentage,
                    interestGaps = uiState.interestGaps,
                    onClickStatus = onClickStatus,
                    onClickBalance = onClickBalance
                )
            }
        }
    }
}
