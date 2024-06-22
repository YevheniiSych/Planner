package com.planner.ui.home

import com.planner.data.room.category.Category

data class HomeState(
    private val _categories: List<Category> = emptyList(),
    val selectedCategoryIndex: Int = 0,
    val selectedCategory: Category = Category.CATEGORY_ALL,
    val categoryToManage: Category = Category.EMPTY //Needs to make interactions with existing category (like interactions from Dropdown menu)
) {
    val categories: List<Category>
        get() {
            return listOf(Category.CATEGORY_ALL) + _categories
        }
}
