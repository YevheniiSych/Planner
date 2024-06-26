package com.planner.data.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.planner.data.room.category.Category
import com.planner.data.room.category.CategoryDao
import com.planner.data.room.task.Task
import com.planner.data.room.task.TaskDao

@Database(
    entities = [
        Category::class,
        Task::class
    ],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
abstract class PlannerDatabase: RoomDatabase() {

    abstract val categoryDao: CategoryDao
    abstract val taskDao: TaskDao

    companion object {
        const val DATABASE_NAME = "planner_db"
    }
}