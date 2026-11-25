package com.example.vocabularyflashcards.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.vocabularyflashcards.views.ActivityScreen
import com.example.vocabularyflashcards.views.LandingScreen
import com.example.vocabularyflashcards.views.SettingsScreen
import com.example.vocabularyflashcards.views.StatisticsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val navItems = listOf(
        Screens.Landing to Icons.Default.Home,
        Screens.Activity to Icons.Default.PlayArrow,
        Screens.Statistics to Icons.Default.Info,
        Screens.Settings to Icons.Default.Settings
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    val title = navItems.firstOrNull { it.first.route == currentRoute }?.first?.title
                        ?: "Vocabulary Flashcards"
                    Text(text = title)
                }
            )
        },
        bottomBar = {
            NavigationBar {
                navItems.forEach { (screen, icon) ->
                    NavigationBarItem(
                        selected = currentRoute == screen.route,
                        onClick = {
                            if (currentRoute != screen.route) {
                                navController.navigate(screen.route) {
                                    popUpTo(Screens.Landing.route) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = { Icon(imageVector = icon, contentDescription = screen.title) },
                        label = { Text(text = screen.title) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screens.Landing.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screens.Landing.route) {
                LandingScreen(
                    onStartStudy = { navController.navigate(Screens.Activity.route) }
                )
            }
            composable(Screens.Activity.route) {
                ActivityScreen(
                    onExitStudy = { navController.popBackStack() }
                )
            }
            composable(Screens.Statistics.route) {
                StatisticsScreen()
            }
            composable(Screens.Settings.route) {
                SettingsScreen()
            }
        }
    }
}