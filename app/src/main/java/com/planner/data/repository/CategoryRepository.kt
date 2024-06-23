package com.planner.data.repository

import com.planner.data.room.category.CategoryDao
import com.planner.data.room.category.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    suspend fun addCategory(categoryEntity: CategoryEntity)

    suspend fun deleteCategory(categoryEntity: CategoryEntity)

    fun getCategories(): Flow<List<CategoryEntity>>

    suspend fun updateCategory(categoryEntity: CategoryEntity)
}

class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao
) : CategoryRepository {

    override suspend fun addCategory(categoryEntity: CategoryEntity) {
        categoryDao.upsertCategory(categoryEntity)
    }

    override suspend fun deleteCategory(categoryEntity: CategoryEntity) {
        categoryDao.deleteCategory(categoryEntity)
    }

    override fun getCategories(): Flow<List<CategoryEntity>> {
        return categoryDao.getCategories()
    }

    override suspend fun updateCategory(categoryEntity: CategoryEntity) {
        return categoryDao.updateCategory(categoryEntity)
    }


}