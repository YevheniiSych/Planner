package com.planner.data.use_case.category

import com.planner.data.repository.CategoryRepository
import com.planner.data.room.category.Category
import com.planner.data.room.category.InvalidCategoryException

class UpdateCategoryUseCase(
    private val repository: CategoryRepository
) {

    @Throws(InvalidCategoryException::class)
    suspend operator fun invoke(category: Category) {
        if (category.id == Category.CATEGORY_ALL_ID) {
            throw InvalidCategoryException("Category \"All\" can't be updated by default")
        }
        repository.updateCategory(category)
    }
}