package com.planner.data.model

import com.planner.data.room.category.Category
import com.planner.data.room.task.Task

data class TaskWithCategory(
    val task: Task,
    val category: Category? = null
)
