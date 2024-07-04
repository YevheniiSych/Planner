package com.planner.ui.task

sealed class TaskEvent {
    data class OnTitleInput(val text: String): TaskEvent()
    data class OnReminderSet(val isEnabled: Boolean, val time: Long): TaskEvent()
}