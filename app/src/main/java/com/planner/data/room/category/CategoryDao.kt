package com.planner.data.room.category

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Upsert
    suspend fun upsertCategory(categoryEntity: CategoryEntity)

    @Delete
    suspend fun deleteCategory(categoryEntity: CategoryEntity)

    @Query("SELECT * FROM category")
    fun getCategories(): Flow<List<CategoryEntity>>

    @Update
    fun updateCategory(categoryEntity: CategoryEntity)
}