package com.planner.data.use_case.category

import com.planner.data.repository.CategoryRepository
import com.planner.data.room.category.Category
import com.planner.data.room.category.InvalidCategoryException

class AddCategoryUseCase(
    private val repository: CategoryRepository
) {

    @Throws(InvalidCategoryException::class)
    suspend operator fun invoke(category: Category) {
        if (category.title.isBlank()) {
            throw InvalidCategoryException("Category title should not be be blank")
        }
        repository.addCategory(category)
    }
}

