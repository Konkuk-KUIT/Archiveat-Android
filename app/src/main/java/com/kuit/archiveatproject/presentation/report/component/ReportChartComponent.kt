package com.kuit.archiveatproject.presentation.report.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.presentation.report.model.InterestGapUiItem
import com.kuit.archiveatproject.presentation.report.model.MainInterestGapUiItem

@Composable
fun ReportChartComponent(
    totalSavedCount: Int,
    totalReadCount: Int,
    readPercentage: Int,
    lightPercentage: Int,
    nowPercentage: Int,
    interestGaps: List<MainInterestGapUiItem>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        ReportConsumptionSummaryCard(
            totalSavedCount = totalSavedCount,
            totalReadCount = totalReadCount,
            readPercentage = readPercentage
        )

        ReportConsumptionBalanceCard(
            lengthBalancePercentage = lightPercentage,
            purposeBalancePercentage = nowPercentage
        )

        ReportInterestGapCard(
            interestGaps = interestGaps
        )
    }
}

@Preview(
    name = "ReportChartComponent",
    showBackground = true,
    backgroundColor = 0xFFF5F5F5
)
@Composable
private fun ReportChartComponentPreview() {
    ReportChartComponent(
        totalSavedCount = 120,
        totalReadCount = 42,
        readPercentage = 35,
        lightPercentage = 71,
        nowPercentage = 47,
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
    )
}