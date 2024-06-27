package com.planner.data.repository

import com.planner.data.room.category.Category
import com.planner.data.room.category.CategoryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

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

    override suspend fun addCategory(category: Category) = withContext((Dispatchers.IO)) {
        categoryDao.upsertCategory(category)
    }

    override suspend fun deleteCategory(category: Category) = withContext((Dispatchers.IO)) {
        categoryDao.deleteCategory(category)
    }

    override fun getCategories(): Flow<List<Category>> {
        return categoryDao.getCategories()
    }

    override suspend fun updateCategory(category: Category) = withContext((Dispatchers.IO)) {
        categoryDao.updateCategory(category)
    }

}