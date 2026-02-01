package com.kuit.archiveatproject.presentation.report.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.kuit.archiveatproject.presentation.report.model.ReportStatusViewModel
import com.kuit.archiveatproject.presentation.report.model.ReportUiState

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
){

}

@Preview(showBackground = true)
@Composable
private fun ReportBalanceContentPreview() {

}