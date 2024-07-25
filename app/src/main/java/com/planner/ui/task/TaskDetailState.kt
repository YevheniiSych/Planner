package com.planner.ui.task

import com.planner.data.room.category.Category
import com.planner.data.room.task.Task
import com.planner.ui.task.components.reminder.ReminderState

data class TaskDetailState(
    val categoryMenuItems: List<Category> = emptyList(),
    val task: Task = Task.EMPTY,
//    val reminderState: ReminderState = ReminderState()
) {
    val reminderState: ReminderState
        get() = ReminderState(
            timeMillis = task.reminderTime,
            isEnabled = task.isReminderEnabled
        )
}
