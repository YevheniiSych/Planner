package com.planner.ui.home.components.task

import com.planner.data.room.task.Task

interface TaskLayoutCallbacks {
    fun onDelete(task: Task)
    fun onComplete(task: Task)
}