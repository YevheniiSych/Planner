package com.planner.ui.tasks

import com.planner.data.room.category.Category

sealed class TaskEvent {

    data class PinCategory(val category: Category): TaskEvent()
    data class DeleteCategory(val category: Category): TaskEvent()
    data class AddNewCategory(val category: Category): TaskEvent()
}