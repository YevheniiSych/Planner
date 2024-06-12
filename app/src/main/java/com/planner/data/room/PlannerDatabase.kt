package com.planner.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.planner.data.room.category.Category
import com.planner.data.room.category.CategoryDao

@Database(
    entities = [Category::class],
    version = 1
)
abstract class PlannerDatabase: RoomDatabase() {

    abstract val categoryDao: CategoryDao

    companion object {
        const val DATABASE_NAME = "planner_db"
    }
}