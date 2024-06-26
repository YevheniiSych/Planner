package com.planner.data.repository

import com.planner.data.room.task.Task
import com.planner.data.room.task.TaskDao
import kotlinx.coroutines.flow.Flow

fun createTaskRepository(taskDao: TaskDao): TaskRepository =
    TaskRepositoryImpl(taskDao)

interface TaskRepository {
    suspend fun addTask(task: Task)

    suspend fun deleteTask(task: Task)

    suspend fun updateTask(task: Task)

    fun getTasks(): Flow<List<Task>>

    fun getTasksByCategory(categoryId: Int): Flow<List<Task>>
}

class TaskRepositoryImpl(private val taskDao: TaskDao) : TaskRepository {
    override suspend fun addTask(task: Task) {
        return taskDao.upsertTask(task)
    }

    override suspend fun deleteTask(task: Task) {
        return taskDao.deleteTask(task)
    }

    override suspend fun updateTask(task: Task) {
        return taskDao.updateTask(task)
    }

    override fun getTasks(): Flow<List<Task>> {
        return taskDao.getTasks()
    }

    override fun getTasksByCategory(categoryId: Int): Flow<List<Task>> {
        return taskDao.getTasksByCategory(categoryId)
    }

}