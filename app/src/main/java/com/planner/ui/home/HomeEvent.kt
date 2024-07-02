package com.planner.ui.home

import com.planner.data.room.category.Category
import com.planner.data.room.task.Task

sealed class HomeEvent {
    sealed class CategoryEvent: HomeEvent() {
        data class Pin(val isPinned: Boolean = false) : CategoryEvent()
        data object Delete : CategoryEvent()
        data class AddNew(val categoryName: String): CategoryEvent()
        data class Selected(val index: Int): CategoryEvent()
        data class ManageCategory(val category: Category): CategoryEvent()
    }

    sealed class TaskEvent: HomeEvent() {
        data class AddNew(
            val text: String,
            val categoryId: Int? = null,
            val reminderTime: Long? = null
        ) : TaskEvent()
        data class Delete(val task: Task): TaskEvent()
        data class Complete(val task: Task): TaskEvent()
        data object Restore: TaskEvent()
    }

}