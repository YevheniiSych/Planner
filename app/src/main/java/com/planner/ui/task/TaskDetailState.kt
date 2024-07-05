package com.planner.ui.task

import com.planner.data.room.category.Category
import com.planner.data.room.task.Task

data class TaskDetailState(
    val categories: List<Category> = emptyList(),
    val task: Task = Task.EMPTY
)