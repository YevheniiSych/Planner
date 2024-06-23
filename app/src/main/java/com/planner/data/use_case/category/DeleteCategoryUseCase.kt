package com.planner.data.use_case.category

import com.planner.data.repository.CategoryRepository
import com.planner.data.room.category.CategoryEntity
import com.planner.data.room.category.InvalidCategoryException
import com.planner.ui.home.category.CategoryItem

class DeleteCategoryUseCase(
    private val repository: CategoryRepository
) {

    @Throws(InvalidCategoryException::class)
    suspend operator fun invoke(categoryEntity: CategoryEntity) {
        if (categoryEntity.id == CategoryItem.CATEGORY_ALL_ID) {
            throw InvalidCategoryException("Category (All) can't be deleted.")
        }
        repository.deleteCategory(categoryEntity)
    }
}