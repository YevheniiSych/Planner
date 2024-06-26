package com.planner.data.use_case.task

import com.planner.data.repository.TaskRepository
import com.planner.data.room.task.Task

class UpdateTaskUseCase(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(task: Task) {
        repository.updateTask(task)
    }
}