package com.kuit.archiveatproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kuit.archiveatproject.presentation.navigation.BottomNavBar
import com.kuit.archiveatproject.presentation.navigation.NavGraph
import com.kuit.archiveatproject.presentation.navigation.NavTab
import com.kuit.archiveatproject.presentation.navigation.Route
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArchiveatProjectTheme {
                val navController = rememberNavController()
                var selectedRoute by remember { mutableStateOf(Route.Home.route) }
                val currentDestination =
                    navController.currentBackStackEntryAsState().value?.destination

                val currentTab: NavTab? =
                    NavTab.entries.find { tab ->
                        currentDestination?.route == tab.route
                    }
                val bottomNavRoutes = listOf(
                    Route.Home.route,
                    Route.Report.route,
                    Route.Explore.route,
                    Route.Etc.route
                )

                val showBottomBar =
                    currentDestination?.route in bottomNavRoutes


                Scaffold(//0xFFF1F3F6
                    containerColor = Color(0xFFFFFFFF),
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(
                                    elevation = 12.dp,
                                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
                                    clip = false
                                )
                                .background(
                                    Color.White,
                                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                                )
                        ) {
                            BottomNavBar(
                                visible = showBottomBar,
                                tabs = NavTab.entries,
                                currentTab = currentTab,
                                onItemSelected = { tab ->
                                    navController.navigate(tab.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }

                                },
                            )
                        }
                    }
                ) { innerPadding ->
                    NavGraph(
                        navController = navController,
                        padding = innerPadding
                    )
                }
            }
        }
    }
}