package com.planner.ui.nav

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.planner.data.room.task.Task
import com.planner.data.room.task.TaskId
import com.planner.ui.task.TaskDetailScreen
import com.planner.ui.task.TaskDetailViewModel
import kotlinx.serialization.Serializable

@Serializable
data class TaskDetailNav(
    val taskId: TaskId = Task.INVALID_TASK_ID
)

fun NavGraphBuilder.taskDetailScreen(onNavigateBack: () -> Unit) {
    composable<TaskDetailNav> {
        val viewModel = hiltViewModel<TaskDetailViewModel>()
        val state by viewModel.stateFlow.collectAsState()
        TaskDetailScreen(
            state = state,
            onEvent = viewModel::onEvent,
            onNavigateBack = onNavigateBack
        )
    }
}