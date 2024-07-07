package com.planner.ui.task

import com.planner.data.room.category.Category

sealed interface TaskDetailEvent {
    data class OnCategorySelected(val category: Category): TaskDetailEvent
    data class OnTitleInput(val text: String): TaskDetailEvent
    data class OnReminderSet(val isEnabled: Boolean, val time: Long): TaskDetailEvent
    data object OnNoCategorySelected: TaskDetailEvent
}