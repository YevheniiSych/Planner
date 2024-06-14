package com.planner.data.use_case.category

import com.planner.data.repository.CategoryRepository
import com.planner.data.room.category.Category

class DeleteCategoryUseCase(
    private val repository: CategoryRepository
) {


    suspend operator fun invoke(category: Category) {
        repository.deleteCategory(category)
    }
}