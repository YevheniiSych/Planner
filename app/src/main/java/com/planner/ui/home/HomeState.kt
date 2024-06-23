package com.planner.ui.home

import com.planner.ui.home.category.CategoryItem

data class HomeState(
    private val _categories: List<CategoryItem> = emptyList(),
    val selectedCategoryIndex: Int = 0,
    val selectedCategory: CategoryItem = CategoryItem.CATEGORY_ALL,
    val categoryToManage: CategoryItem = CategoryItem.EMPTY //Needs to make interactions with existing category (like interactions from Dropdown menu)
) {
    val categories: List<CategoryItem>
        get() {
            return listOf(CategoryItem.CATEGORY_ALL) + _categories
        }
}
