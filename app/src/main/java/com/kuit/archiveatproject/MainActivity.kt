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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kuit.archiveatproject.presentation.navigation.BottomNavBar
import com.kuit.archiveatproject.presentation.navigation.NavGraph
import com.kuit.archiveatproject.presentation.navigation.NavTab
import com.kuit.archiveatproject.presentation.navigation.Route
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavGraph.Companion.findStartDestination
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArchiveatProjectTheme {
                val navController = rememberNavController()
                val currentDestination =
                    navController.currentBackStackEntryAsState().value?.destination

                val currentTab: NavTab? =
                    NavTab.entries.find { tab ->
                        currentDestination?.route == tab.route
                    }
                val showBottomBar = currentTab != null


                Scaffold(//0xFFF1F3F6
                    containerColor = Color(0xFFFFFFFF),
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showBottomBar) {
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
                                    visible = true,
                                    tabs = NavTab.entries,
                                    currentTab = currentTab,
                                    onItemSelected = { tab ->
                                        val mainGraphStartId = (navController.graph
                                            .findNode(Route.Main.route) as? NavGraph)
                                            ?.findStartDestination()
                                            ?.id
                                        navController.navigate(tab.route) {
                                            if (mainGraphStartId != null) {
                                                popUpTo(mainGraphStartId) {
                                                    saveState = true
                                                }
                                            } else {
                                                popUpTo(Route.Home.route) {
                                                    saveState = true
                                                }
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }

                                    },
                                )
                            }
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
