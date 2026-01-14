package com.kuit.archiveatproject.presentation.report.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.core.component.tag.TextTag
import com.kuit.archiveatproject.presentation.report.model.InterestGapUiItem
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun ReportChartComponent(
    totalSavedCount: Int,
    totalReadCount: Int,
    readPercentage: Int,
    lightPercentage: Int,
    nowPercentage: Int,
    interestGaps: List<InterestGapUiItem>,
    modifier: Modifier = Modifier
){

}

@Preview(showBackground = true)
@Composable
private fun ReportChartComponentPreview() {
    ReportChartComponent(
        totalSavedCount = 120,
        totalReadCount = 42,
        readPercentage = 35,
        lightPercentage = 71,
        nowPercentage = 47,
        interestGaps = listOf(
            InterestGapUiItem("건강", 50, 5, 45),
            InterestGapUiItem("AI", 30, 25, 5)
        )
    )
}