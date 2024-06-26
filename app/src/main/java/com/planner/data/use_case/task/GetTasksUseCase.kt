package com.planner.data.use_case.task

import com.planner.data.repository.TaskRepository
import com.planner.data.room.task.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTasksUseCase(
    private val repository: TaskRepository
) {

    fun getAllTasks(): Flow<List<Task>> {
        return repository.getTasks().standardSort()

    }

    fun getTasksByCategory(categoryId: Int): Flow<List<Task>> {
        return repository.getTasksByCategory(categoryId).standardSort()
    }

    private fun Flow<List<Task>>.standardSort() = this.map { tasks ->
        tasks.sortedWith(
            compareBy<Task> { it.isCompleted }
                .thenByDescending { it.completedAt }
                .thenByDescending { it.createdAt }

        )
    }

}