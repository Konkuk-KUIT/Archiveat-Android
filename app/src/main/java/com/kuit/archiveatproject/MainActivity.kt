package com.kuit.archiveatproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kuit.archiveatproject.presentation.navigation.BottomNavBar
import com.kuit.archiveatproject.presentation.navigation.NavGraph
import com.kuit.archiveatproject.presentation.navigation.NavTab
import com.kuit.archiveatproject.presentation.navigation.Route
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
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


                Scaffold(
                    containerColor = Color(0xFFF1F3F6),
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 30.dp,
                                        topEnd = 30.dp
                                    )
                                )
                                .background(Color.White)
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