package com.kuit.archiveatproject.presentation.navigation

sealed class Route (
    val route: String
){
    data object Home: Route(route = "home")
    data object Login: Route(route = "login")
    data object OnboardingIntro: Route(route = "onboarding/intro")
    data object OnboardingJobTime: Route(route = "onboarding/job-time")
    data object OnboardingInterest: Route(route = "onboarding/interest")
    data object Share: Route(route = "share")
    data object Explore: Route(route = "explore")
    data object ExploreInbox: Route(route = "explore/inbox")
    data object NewsletterSimple: Route(route = "newsletters/{userNewsletterId}/simple") {
        fun createRoute(userNewsletterId: Long): String =
            "newsletters/$userNewsletterId/simple"
    }
    data object WebView: Route(route = "webview?url={url}") {
        fun createRoute(url: String): String =
            "webview?url=${android.net.Uri.encode(url)}"
    }
    data object Report: Route(route = "report")
    data object Etc: Route(route = "etc")

}
