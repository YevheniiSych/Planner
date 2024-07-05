package com.planner.ui.home.components.task

import com.planner.data.room.task.Task
import com.planner.data.room.task.TaskId

interface TaskLayoutCallbacks {
    fun onDelete(task: Task)
    fun onComplete(task: Task)
    fun onTaskClick(taskId: TaskId)
}