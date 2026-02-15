package com.kuit.archiveatproject.presentation.report.component.balance

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.presentation.report.model.ReportBalanceUiState
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun BalanceSummaryCard(
    balance: ReportBalanceUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = ArchiveatProjectTheme.colors.gray50,
                shape = RoundedCornerShape(16.dp)
            )
            .border(1.dp, ArchiveatProjectTheme.colors.gray100, RoundedCornerShape(16.dp))
            .padding(18.dp)
    ) {

        BalanceBarRow(
            title = "콘텐츠 길이",
            leftLabel = "10분 미만",
            leftPercent = balance.lightPercentage,
            leftColor = ArchiveatProjectTheme.colors.sub_3,
            rightLabel = "10분 이상",
            rightPercent = balance.deepPercentage,
            rightColor = ArchiveatProjectTheme.colors.sub_1
        )

        Spacer(Modifier.height(21.dp))

        BalanceBarRow(
            title = "소비 목적",
            leftLabel = "현재 필요",
            leftPercent = balance.nowPercentage,
            leftColor = ArchiveatProjectTheme.colors.sub_2,
            rightLabel = "미래 준비",
            rightPercent = balance.futurePercentage,
            rightColor = ArchiveatProjectTheme.colors.primary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BalanceSummaryCardPreview() {
    ArchiveatProjectTheme {
        BalanceSummaryCard(
            balance = ReportBalanceUiState(
                lightPercentage = 70,
                deepPercentage = 30,
                nowPercentage = 80,
                futurePercentage = 20
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}