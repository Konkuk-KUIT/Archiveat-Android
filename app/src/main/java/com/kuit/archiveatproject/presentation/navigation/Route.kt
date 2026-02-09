package com.kuit.archiveatproject.presentation.navigation

sealed class Route (
    val route: String
){
    data object Home: Route(route = "home")
    data object Login: Route(route = "login")
    data object OnboardingIntro: Route(route = "onboarding/intro")
    data object OnboardingJobTime: Route(route = "onboarding/job-time")
    data object OnboardingInterest: Route(route = "onboarding/interest")
    data object Main: Route(route = "main") // 탭 네비게이션 용 그래프
    data object Share: Route(route = "share")
    data object Explore: Route(route = "explore")
    data object ExploreInbox: Route(route = "explore/inbox")

    data object ExploreTopicDetail : Route(
        route = "explore/topic/{topicId}"
    ) {
        fun createRoute(topicId: Long): String =
            "explore/topic/$topicId"
    }
    data object NewsletterSimple: Route(route = "newsletters/{userNewsletterId}/simple") {
        fun createRoute(userNewsletterId: Long): String =
            "newsletters/$userNewsletterId/simple"
    }
    data object NewsletterCollection: Route(route = "collections/{collectionId}") {
        fun createRoute(collectionId: Long): String =
            "collections/$collectionId"
    }
    data object WebView: Route(route = "webview?url={url}") {
        fun createRoute(url: String): String =
            "webview?url=${android.net.Uri.encode(url)}"
    }
    data object Report: Route(route = "report")
    data object Etc: Route(route = "etc")

}
