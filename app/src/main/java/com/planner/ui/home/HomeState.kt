package com.planner.ui.home

import com.planner.data.room.category.Category
import com.planner.data.util.OrderType
import com.planner.data.util.category.CategoryOrder

data class HomeState(
    private val _categories: List<Category> = emptyList(),
    val categoryOrder: CategoryOrder = CategoryOrder.DateCreated(OrderType.DESC),
    val selectedCategoryIndex: Int = 0,
    val selectedCategory: Category = Category.categoryAll,
) {
    val categories: List<Category>
        get() {
            return listOf(Category.categoryAll) + _categories
        }
}
