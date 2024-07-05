package com.planner.ui.task

import androidx.compose.runtime.Composable
import kotlin.reflect.KFunction1

@Composable
fun TaskDetailScreen(
    onEvent: KFunction1<TaskDetailEvent, Unit>,
    state: TaskDetailState,
    onNavigateBack: () -> Unit
) {

}