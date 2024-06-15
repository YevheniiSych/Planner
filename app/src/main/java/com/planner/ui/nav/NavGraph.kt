package com.planner.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.planner.ui.home.HomeScreen
import com.planner.ui.home.HomeViewModel

@Composable
fun InitNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(route = Screen.HomeScreen.route) {
            val viewModel = hiltViewModel<HomeViewModel>()
            val state by viewModel.state
            HomeScreen(
                state = state,
                onEvent = viewModel::onEvent
            )
        }
    }
}