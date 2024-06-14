package com.planner.ui.nav

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.planner.ui.tasks.TasksScreen
import com.planner.ui.tasks.TasksViewModel

@Composable
fun InitNavigation() {
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