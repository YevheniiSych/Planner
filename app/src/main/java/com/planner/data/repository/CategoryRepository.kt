package com.planner.data.repository

import com.planner.data.room.category.Category
import com.planner.data.room.category.CategoryDao
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    suspend fun addCategory(category: Category)

    suspend fun deleteCategory(category: Category)

    fun getCategories(): Flow<List<Category>>
}

class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao
) : CategoryRepository {

    override suspend fun addCategory(category: Category) {
        categoryDao.upsertCategory(category)
    }

    override suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(category)
    }

    override fun getCategories(): Flow<List<Category>> {
        return categoryDao.getCategories()
    }


}