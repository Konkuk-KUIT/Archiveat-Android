package com.kuit.archiveatproject.presentation.navigation

sealed class Route (
    val route: String
){
    data object Home: Route(route = "home")
    data object Share: Route(route = "share")
    data object Explore: Route(route = "explore")
    data object Report: Route(route = "report")
    data object Etc: Route(route = "etc")

}