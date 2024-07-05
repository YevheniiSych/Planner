package com.planner.ui.nav

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.planner.ui.home.HomeScreen
import com.planner.ui.home.HomeViewModel
import kotlinx.serialization.Serializable

@Serializable
object HomeNav

fun NavGraphBuilder.homeScreen(onNavigateToTaskDetail: (TaskDetailNav) -> Unit) {
    composable<HomeNav> {
        val viewModel = hiltViewModel<HomeViewModel>()
        val state by viewModel.state.collectAsState()
        HomeScreen(
            state = state,
            onEvent = viewModel::onEvent,
            eventFlow = viewModel.eventFlow,
            onEditTask = { taskId ->
                onNavigateToTaskDetail(
                    TaskDetailNav(taskId)
                )
            },
            onCreateNewTask = {
                onNavigateToTaskDetail(TaskDetailNav())
            }
        )
    }
}