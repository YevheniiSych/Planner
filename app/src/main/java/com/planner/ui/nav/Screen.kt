package com.planner.ui.nav

sealed class Screen(val route: String) {
    data object TasksScreen: Screen("tasks")
}