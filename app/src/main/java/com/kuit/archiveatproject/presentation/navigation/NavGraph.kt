package com.kuit.archiveatproject.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kuit.archiveatproject.presentation.etc.screen.EtcScreen
import com.kuit.archiveatproject.presentation.explore.screen.ExploreScreen
import com.kuit.archiveatproject.presentation.home.screen.HomeScreen
import com.kuit.archiveatproject.presentation.inbox.screen.InboxScreen
import com.kuit.archiveatproject.presentation.login.screen.LoginScreen
import com.kuit.archiveatproject.presentation.newsletterdetails.screen.NewsletterDetailsSimpleScreen
import com.kuit.archiveatproject.presentation.newsletterdetails.screen.WebViewScreen
import com.kuit.archiveatproject.presentation.onboarding.screen.OnboardingInterestScreen
import com.kuit.archiveatproject.presentation.onboarding.screen.OnboardingJobTimeScreen
import com.kuit.archiveatproject.presentation.onboarding.screen.OnboardingScreen as OnboardingIntroScreen
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
        startDestination = Route.OnboardingIntro.route // startDestination 온보딩 인트로
    ) {
        composable(route = Route.Home.route) {
            HomeScreen()
        }
        composable(route = Route.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Route.Home.route) {
                        popUpTo(Route.Login.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onSignupSuccess = {
                    navController.navigate(Route.OnboardingJobTime.route) {
                        popUpTo(Route.Login.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(route = Route.OnboardingIntro.route) {
            OnboardingIntroScreen(
                onStart = {
                    navController.navigate(Route.Login.route) {
                        popUpTo(Route.OnboardingIntro.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(route = Route.OnboardingJobTime.route) {
            OnboardingJobTimeScreen(
                onNext = {
                    navController.navigate(Route.OnboardingInterest.route) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(route = Route.OnboardingInterest.route) {
            OnboardingInterestScreen(
                onFinished = {
                    navController.navigate(Route.Home.route) {
                        popUpTo(Route.OnboardingJobTime.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(route = Route.Explore.route) {
            ExploreScreen(modifier = modifier)
        }
        composable(route = Route.ExploreInbox.route) {
            InboxScreen(
                onBackToExploreFirstDepth = {
                    navController.popBackStack(Route.Explore.route, inclusive = false)
                },
                onOpenOriginal = { userNewsletterId ->
                    navController.navigate(Route.NewsletterSimple.createRoute(userNewsletterId))
                },
                modifier = modifier
            )
        }
        composable(
            route = Route.NewsletterSimple.route,
            arguments = listOf(navArgument("userNewsletterId") { type = NavType.LongType })
        ) {
            NewsletterDetailsSimpleScreen(
                onBack = { navController.popBackStack() },
                onClickWebView = { url ->
                    navController.navigate(Route.WebView.createRoute(url))
                },
                modifier = modifier
            )
        }
        composable(
            route = Route.WebView.route,
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ) { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url").orEmpty()
            WebViewScreen(
                url = url,
                onBack = { navController.popBackStack() },
                modifier = modifier
            )
        }
        composable(route = Route.Report.route) {
            ReportScreen(
                uiState = ReportUiState(),
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
