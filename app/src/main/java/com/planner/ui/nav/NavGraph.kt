package com.planner.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.planner.ui.tasks.TasksScreen

@Composable
fun Nav() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.TasksScreen.route
    ) {
        composable(route = Screen.TasksScreen.route) {
            TasksScreen()
        }
    }
}