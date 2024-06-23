package com.planner.data.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.planner.data.room.category.CategoryEntity
import com.planner.data.room.category.CategoryDao

@Database(
    entities = [CategoryEntity::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
abstract class PlannerDatabase: RoomDatabase() {

    abstract val categoryDao: CategoryDao

    companion object {
        const val DATABASE_NAME = "planner_db"
    }
}