package com.planner.data.repository

import com.planner.data.room.task.Task
import com.planner.data.room.task.TaskDao
import com.planner.data.room.task.TaskId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

fun createTaskRepository(taskDao: TaskDao): TaskRepository =
    TaskRepositoryImpl(taskDao)

interface TaskRepository {
    suspend fun addTask(task: Task)

    suspend fun deleteTask(task: Task)

    suspend fun updateTask(task: Task)

    fun getTasks(): Flow<List<Task>>

    fun getTasksByCategory(categoryId: Int): Flow<List<Task>>

    fun getTask(taskId: TaskId): Flow<Task>
}

class TaskRepositoryImpl(private val taskDao: TaskDao) : TaskRepository {
    override suspend fun addTask(task: Task) = withContext((Dispatchers.IO)) {
        taskDao.upsertTask(task)
    }

    override suspend fun deleteTask(task: Task) = withContext((Dispatchers.IO)) {
        taskDao.deleteTask(task)
    }

    override suspend fun updateTask(task: Task) = withContext((Dispatchers.IO)) {
        taskDao.updateTask(task)
    }

    override fun getTasks(): Flow<List<Task>> {
        return taskDao.getTasks()
    }

    override fun getTasksByCategory(categoryId: Int): Flow<List<Task>> {
        return taskDao.getTasksByCategory(categoryId)
    }

    override fun getTask(taskId: TaskId): Flow<Task> {
        return taskDao.getTask(taskId)
    }

}