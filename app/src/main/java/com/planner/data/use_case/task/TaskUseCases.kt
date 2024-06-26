package com.planner.data.use_case.task

data class TaskUseCases(
    val addTaskUseCase: AddTaskUseCase,
    val deleteTaskUseCase: DeleteTaskUseCase,
    val updateTaskUseCase: UpdateTaskUseCase,
    val getTasksUseCase: GetTasksUseCase
)
