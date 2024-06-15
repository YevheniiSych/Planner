package com.planner.ui.tasks

import com.planner.data.room.category.Category
import com.planner.data.util.OrderType
import com.planner.data.util.category.CategoryOrder

data class TasksState(
    private val _categories: List<Category> = emptyList(),
    val categoryOrder: CategoryOrder = CategoryOrder.DateCreated(OrderType.DESC)
) {
    val categories: List<Category>
        get() {
            return listOf(Category.categoryAll) + _categories
        }
}
