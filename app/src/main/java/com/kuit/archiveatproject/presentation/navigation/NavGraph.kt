package com.kuit.archiveatproject.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kuit.archiveatproject.presentation.etc.screen.EtcScreen
import com.kuit.archiveatproject.presentation.explore.screen.ExploreScreen
import com.kuit.archiveatproject.presentation.home.screen.HomeScreen
import com.kuit.archiveatproject.presentation.report.model.InterestGapUiItem
import com.kuit.archiveatproject.presentation.report.model.ReportUiState
import com.kuit.archiveatproject.presentation.report.screen.ReportScreen
import com.kuit.archiveatproject.presentation.share.screen.ShareScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    padding: PaddingValues,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Route.Home.route
    ) {
        composable(route = Route.Home.route) {
            HomeScreen(modifier = modifier)
        }
        composable(route = Route.Explore.route) {
            ExploreScreen(modifier = modifier)
        }
        composable(route = Route.Report.route) {
            ReportScreen(
                uiState = fakeReportUiState(),
                modifier = modifier
            )
        }
        composable(route = Route.Etc.route) {
            EtcScreen(modifier = modifier)
        }
        composable(route = Route.Share.route) {
            ShareScreen(modifier = modifier)
        }
    }
}

// TODO: view Model 연결 시 제거
private fun fakeReportUiState() = ReportUiState(
    referenceDate = "2025년 11월 6일",
    totalSavedCount = 120,
    totalReadCount = 42,
    readPercentage = 70,
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
