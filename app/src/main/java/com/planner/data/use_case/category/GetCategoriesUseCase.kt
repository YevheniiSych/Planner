package com.planner.data.use_case.category

import com.planner.data.repository.CategoryRepository
import com.planner.data.room.category.Category
import com.planner.data.util.OrderType
import com.planner.data.util.category.CategoryOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCategoriesUseCase(
    private val repository: CategoryRepository
) {

    operator fun invoke(categoryOrder: CategoryOrder): Flow<List<Category>> {
        return repository.getCategories().map { categories ->
            when (categoryOrder.orderType) {
                OrderType.ASC -> {
                    when (categoryOrder) {
                        is CategoryOrder.Title -> categories.sortedBy { it.title.lowercase() }
                        is CategoryOrder.DateCreated -> categories.sortedBy { it.dateCreatedTimestamp }
                    }
                }

                OrderType.DESC -> {
                    when (categoryOrder) {
                        is CategoryOrder.Title -> categories.sortedByDescending { it.title.lowercase() }
                        is CategoryOrder.DateCreated -> categories.sortedByDescending { it.dateCreatedTimestamp }
                    }
                }
            }
        }
    }
}