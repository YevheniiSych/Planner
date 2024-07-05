package com.planner.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun PlannerNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HomeNav
    ) {
        homeScreen(
            onNavigateToTaskDetail = navController::navigate
        )
        taskDetailScreen(
            onNavigateBack = navController::navigateUp
        )

    }
}