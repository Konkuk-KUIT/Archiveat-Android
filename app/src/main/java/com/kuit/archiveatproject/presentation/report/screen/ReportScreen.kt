package com.kuit.archiveatproject.presentation.report.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kuit.archiveatproject.presentation.report.model.InterestGapUiItem
import com.kuit.archiveatproject.presentation.report.model.ReportUiState

@Composable
fun ReportScreen(
    uiState: ReportUiState,
    modifier: Modifier = Modifier
){

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