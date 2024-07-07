package com.planner.data.use_case.task

import com.planner.data.model.TaskWithCategory
import com.planner.data.repository.CategoryRepository
import com.planner.data.repository.TaskRepository
import com.planner.data.room.task.TaskId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip

class GetTaskWithCategoryUseCase(
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository
) {
    operator fun invoke(taskId: TaskId): Flow<TaskWithCategory> {
        val taskFlow = taskRepository.getTask(taskId)
        val categoryFlow = categoryRepository.getCategoryByTask(taskId)
        return taskFlow.zip(categoryFlow) { task, category ->
            TaskWithCategory(
                task = task,
                category = category
            )
        }
    }
}