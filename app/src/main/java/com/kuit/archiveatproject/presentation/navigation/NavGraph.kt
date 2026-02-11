package com.kuit.archiveatproject.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.kuit.archiveatproject.presentation.etc.screen.EtcScreen
import com.kuit.archiveatproject.presentation.explore.screen.ExploreScreen
import com.kuit.archiveatproject.presentation.explore.screen.ExploreTopicDetailScreen
import com.kuit.archiveatproject.presentation.explore.viewmodel.ExploreTopicDetailViewModel
import com.kuit.archiveatproject.presentation.home.screen.HomeScreen
import com.kuit.archiveatproject.presentation.inbox.screen.InboxScreen
import com.kuit.archiveatproject.presentation.login.screen.LoginScreen
import com.kuit.archiveatproject.presentation.newsletterdetails.screen.NewsletterDetailsCollectionScreen
import com.kuit.archiveatproject.presentation.newsletterdetails.screen.NewsletterDetailsSimpleScreen
import com.kuit.archiveatproject.presentation.newsletterdetails.screen.WebViewScreen
import com.kuit.archiveatproject.presentation.onboarding.screen.OnboardingInterestScreen
import com.kuit.archiveatproject.presentation.onboarding.screen.OnboardingJobTimeScreen
import com.kuit.archiveatproject.presentation.onboarding.screen.OnboardingScreen as OnboardingIntroScreen
import com.kuit.archiveatproject.presentation.onboarding.viewmodel.OnboardingViewModel
import com.kuit.archiveatproject.presentation.report.screen.ReportScreen
import com.kuit.archiveatproject.presentation.share.screen.ShareScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    padding: PaddingValues,
    modifier: Modifier = Modifier
) {
    val screenModifier = modifier.padding(padding)

    NavHost(
        navController = navController,
        startDestination = Route.OnboardingIntro.route // startDestination 온보딩 인트로
    ) {
        composable(route = Route.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Route.Main.route) {
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
        composable(route = Route.OnboardingJobTime.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Route.OnboardingJobTime.route)
            }
            val onboardingViewModel: OnboardingViewModel = hiltViewModel(parentEntry)
            OnboardingJobTimeScreen(
                viewModel = onboardingViewModel,
                onNext = {
                    navController.navigate(Route.OnboardingInterest.route) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(route = Route.OnboardingInterest.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Route.OnboardingJobTime.route)
            }
            val onboardingViewModel: OnboardingViewModel = hiltViewModel(parentEntry)
            OnboardingInterestScreen(
                viewModel = onboardingViewModel,
                onFinished = {
                    navController.navigate(Route.Main.route) {
                        popUpTo(Route.OnboardingJobTime.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        navigation(
            startDestination = Route.Home.route,
            route = Route.Main.route
        ) {
            composable(route = Route.Home.route) {
                HomeScreen(modifier = screenModifier)
            }
            composable(route = Route.Explore.route) {
                ExploreScreen(
                    modifier = screenModifier,
                    onInboxClick = {
                        navController.navigate(Route.ExploreInbox.route)
                    },
                    onTopicClick = { topicId, topicName ->
                        // topicName은 SavedStateHandle로 전달
                        navController.currentBackStackEntry
                            ?.savedStateHandle
                            ?.set("topicName", topicName)

                        navController.navigate(
                            Route.ExploreTopicDetail.createRoute(topicId)
                        )
                    }
                )
            }
            composable(route = Route.ExploreInbox.route) {
                InboxScreen(
                    onBackToExploreFirstDepth = {
                        navController.popBackStack(Route.Explore.route, inclusive = false)
                    },
                    onOpenOriginal = { userNewsletterId ->
                        navController.navigate(Route.NewsletterSimple.createRoute(userNewsletterId))
                    },
                    modifier = screenModifier
                )
            }
            composable(
                route = Route.ExploreTopicDetail.route,
                arguments = listOf(
                    navArgument("topicId") { type = NavType.LongType }
                )
            ) { backStackEntry ->

                val topicId = backStackEntry.arguments!!.getLong("topicId")

                val topicName =
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.get<String>("topicName")
                        ?: ""

                ExploreTopicDetailScreen(
                    modifier = screenModifier,
                    topicId = topicId,
                    topicName = topicName,
                    onBack = { navController.popBackStack() },
                    onClickOutlink = { userNewsletterId ->
                        navController.navigate(
                            Route.NewsletterSimple.createRoute(userNewsletterId)
                        )
                    },
                    onSearchSubmit = {}
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
                    modifier = screenModifier
                )
            }
            composable(
                route = Route.NewsletterCollection.route,
                arguments = listOf(navArgument("collectionId") { type = NavType.LongType })
            ) {
                NewsletterDetailsCollectionScreen(
                    onBack = { navController.popBackStack() },
                    onClickItem = { userNewsletterId ->
                        navController.navigate(Route.NewsletterSimple.createRoute(userNewsletterId))
                    },
                    modifier = screenModifier
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
                    modifier = screenModifier
                )
            }
            composable(route = Route.Report.route) {
                ReportScreen(
                    modifier = screenModifier
                )
            }
            composable(route = Route.Etc.route) {
                EtcScreen(modifier = screenModifier)
            }
            composable(route = Route.Share.route) {
                ShareScreen(modifier = screenModifier)
            }
        }
    }
}
