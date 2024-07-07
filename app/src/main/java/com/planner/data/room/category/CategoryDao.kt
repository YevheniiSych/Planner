package com.planner.data.room.category

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.planner.data.room.task.TaskId
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Upsert
    suspend fun upsertCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("SELECT * FROM category")
    fun getCategories(): Flow<List<Category>>

    @Update
    fun updateCategory(category: Category)

    @Transaction
    @Query(
        "SELECT category.* " +
                "FROM task " +
                "LEFT JOIN category ON task.categoryId IS NOT NULL AND task.categoryId = category.id " +
                "WHERE task.id = :taskId AND task.categoryId IS NOT NULL"
    )
    fun getCategoryByTask(taskId: TaskId): Flow<Category?>
}