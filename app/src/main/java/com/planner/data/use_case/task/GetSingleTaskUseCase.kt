package com.planner.data.use_case.task

import com.planner.data.repository.TaskRepository
import com.planner.data.room.task.InvalidTaskException
import com.planner.data.room.task.Task
import com.planner.data.room.task.TaskId
import kotlinx.coroutines.flow.Flow

class GetSingleTaskUseCase(
    val repository: TaskRepository
) {
    @Throws(InvalidTaskException::class)
    operator fun invoke(taskId: TaskId): Flow<Task> {
        if (taskId == Task.INVALID_TASK_ID) {
            throw InvalidTaskException("Invalid task id.")
        }
        return repository.getTask(taskId)
    }
}