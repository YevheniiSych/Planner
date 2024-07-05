package com.planner.ui.task

sealed class TaskDetailEvent {
    data class OnTitleInput(val text: String): TaskDetailEvent()
    data class OnReminderSet(val isEnabled: Boolean, val time: Long): TaskDetailEvent()
}