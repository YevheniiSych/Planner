package com.planner.data.repository

import com.planner.data.room.category.Category
import com.planner.data.room.category.CategoryDao
import kotlinx.coroutines.flow.Flow

fun createCategoryRepository(categoryDao: CategoryDao): CategoryRepository =
    CategoryRepositoryImpl(categoryDao)

interface CategoryRepository {

    suspend fun addCategory(category: Category)

    suspend fun deleteCategory(category: Category)

    fun getCategories(): Flow<List<Category>>

   suspend fun updateCategory(category: Category)
}

private class CategoryRepositoryImpl(
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

    override suspend fun updateCategory(category: Category) {
        return categoryDao.updateCategory(category)
    }


}