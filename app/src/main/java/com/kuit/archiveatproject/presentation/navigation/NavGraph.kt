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
            ReportScreen(modifier = modifier)
        }
        composable(route = Route.Etc.route) {
            EtcScreen(modifier = modifier)
        }
        composable(route = Route.Share.route) {
            ShareScreen(modifier = modifier)
        }
    }
}