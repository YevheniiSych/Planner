package com.planner.data.use_case.category

import com.planner.data.repository.CategoryRepository
import com.planner.data.room.category.CategoryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCategoriesUseCase(
    private val repository: CategoryRepository
) {

    operator fun invoke(): Flow<List<CategoryEntity>> {
        return repository.getCategories().map { categories ->
            categories.sortedWith(
                compareByDescending<CategoryEntity> { it.isPinned }
                    .thenByDescending { it.lastPinTime }
                    .thenByDescending { it.dateCreatedTimestamp }

            )
        }
    }

}