package com.kuit.archiveatproject.presentation.navigation

import com.kuit.archiveatproject.R

enum class NavTab(
    val label: String,
    val route: String,
    val icon: Int
) {
    Home("홈", Route.Home.route, R.drawable.ic_home),
    Explore("탐색", Route.Explore.route, R.drawable.ic_explore),
    Report("리포트", Route.Report.route, R.drawable.ic_report),
    Etc("기타", Route.Etc.route, R.drawable.ic_etc)
}