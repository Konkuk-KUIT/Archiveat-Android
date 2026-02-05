package com.kuit.archiveatproject.presentation.report.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kuit.archiveatproject.core.component.BackTopBar
import com.kuit.archiveatproject.presentation.report.component.ReportButtonComponent
import com.kuit.archiveatproject.presentation.report.component.balance.BalanceCanvasComponent
import com.kuit.archiveatproject.presentation.report.component.balance.BalancePatternInsightCard
import com.kuit.archiveatproject.presentation.report.component.balance.BalanceSummaryCard
import com.kuit.archiveatproject.presentation.report.component.balance.StatusTextTag
import com.kuit.archiveatproject.presentation.report.model.ReportBalanceUiState
import com.kuit.archiveatproject.presentation.report.model.ReportStatusViewModel
import com.kuit.archiveatproject.presentation.report.model.ReportUiState
import com.kuit.archiveatproject.presentation.report.model.toActionButtonText
import com.kuit.archiveatproject.presentation.report.model.toKnowledgePosition
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun ReportBalanceScreen(
    viewModel: ReportStatusViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    ReportBalanceContent(
        uiState = uiState,
        modifier = modifier
    )
}

@Composable
fun ReportBalanceContent(
    uiState: ReportUiState,
    modifier: Modifier = Modifier
) {
    val position = uiState.toKnowledgePosition()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            BackTopBar(
                title = "나의 소비 밸런스",
                onBack = {}
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ArchiveatProjectTheme.colors.white)
                    .padding(horizontal = 20.dp, vertical = 14.dp)
                    .navigationBarsPadding()
            ) {
                ReportButtonComponent(
                    text = position.toActionButtonText(),
                    enabled = true,
                    onClick = { /* TODO */ },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        containerColor = ArchiveatProjectTheme.colors.white
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(Modifier.height(21.dp))

                // TODO: user dto 추가 후 닉네임 데이터로 교체
                StatusTextTag(
                    text = "이준님의 지식 좌표",ArchiveatProjectTheme.colors.primary)

                Spacer(Modifier.height(21.dp))
                BalanceCanvasComponent(
                    position = position,
                    modifier = Modifier.size(255.dp)
                )

                Spacer(Modifier.height(31.dp))
            }

            item {
                BalancePatternInsightCard(
                    title = uiState.balance.patternTitle,
                    description = uiState.balance.patternDescription,
                    quote = uiState.balance.patternQuote
                )

                Spacer(Modifier.height(21.dp))
            }

            item {
                Text(
                    text = "상세 비율 분석",
                    style = ArchiveatProjectTheme.typography.Subhead_2_semibold,
                    color = ArchiveatProjectTheme.colors.gray800,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )

                Spacer(Modifier.height(12.dp))

                BalanceSummaryCard(
                    balance = uiState.balance
                )

                Spacer(Modifier.height(14.dp))
            }
        }
    }
}

private fun fakeReportBalanceUiState(): ReportUiState =
    ReportUiState(
        balance = ReportBalanceUiState(
            lightPercentage = 70,
            deepPercentage = 30,
            nowPercentage = 80,
            futurePercentage = 20,
            patternTitle = "핵심을 빠르게 파악하는\n실용적 효율 추구형",
            patternDescription = "10분 미만의 요약된 콘텐츠로 당장 필요한 정보를 빠르게 습득하고 계세요. 시간 대비 효율을 중요하게 생각하는 소비 패턴입니다.",
            patternQuote = "현재에 충실한 것도 좋지만, 시야가 좁아질 수 있습니다. 가끔은 당장의 목적과 무관하더라도 새로운 관점을 접해보는 것을 추천합니다."
        )
    )

@Preview(showBackground = true)
@Composable
private fun ReportBalanceContentPreview() {
    ArchiveatProjectTheme {
        ReportBalanceContent(
            uiState = fakeReportBalanceUiState()
        )
    }
}