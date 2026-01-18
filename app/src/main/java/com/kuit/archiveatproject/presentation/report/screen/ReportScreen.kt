package com.kuit.archiveatproject.presentation.report.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.presentation.report.component.ReportChartComponent
import com.kuit.archiveatproject.presentation.report.model.InterestGapUiItem
import com.kuit.archiveatproject.presentation.report.model.ReportUiState
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun ReportScreen(
    uiState: ReportUiState,
    modifier: Modifier = Modifier
){
    Column(

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = ArchiveatProjectTheme.colors.white)
                .padding(start = 20.dp, top = 55.dp, bottom = 12.dp)
        ){
            Text(
                text = "리포트",
                style = ArchiveatProjectTheme.typography.Heading_1_bold,
                color = ArchiveatProjectTheme.colors.black
            )
        }
        Spacer(Modifier.height(24.dp))
        ReportChartComponent(
            totalSavedCount = uiState.totalSavedCount,
            totalReadCount = uiState.totalReadCount,
            readPercentage = uiState.readPercentage,
            lightPercentage = uiState.lightPercentage,
            nowPercentage = uiState.nowPercentage,
            interestGaps = uiState.interestGaps
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun ReportScreenPreview() {
    ReportScreen(
        uiState = ReportUiState(
            referenceDate = "2025년 11월 6일",
            totalSavedCount = 120,
            totalReadCount = 42,
            readPercentage = 35,
            lightPercentage = 71,
            nowPercentage = 47,
            interestGaps = listOf(
                InterestGapUiItem(
                    topicName = "건강",
                    savedCount = 50,
                    readCount = 5,
                    gap = 45
                ),
                InterestGapUiItem(
                    topicName = "AI",
                    savedCount = 30,
                    readCount = 25,
                    gap = 5
                )
            )
        )
    )
}