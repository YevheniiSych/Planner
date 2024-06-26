package com.planner.data.use_case.task

import com.planner.data.repository.TaskRepository
import com.planner.data.room.task.InvalidTaskException
import com.planner.data.room.task.Task

class AddTaskUseCase(
    private val repository: TaskRepository
) {

    @Throws(InvalidTaskException::class)
    suspend operator fun invoke(task: Task) {
        if (task.text.isBlank()) {
            throw InvalidTaskException("Task text should not be blank")
        }
        repository.addTask(task)
    }
}