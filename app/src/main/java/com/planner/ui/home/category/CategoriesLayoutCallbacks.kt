package com.planner.ui.home.category

import com.planner.data.room.category.Category

interface CategoriesLayoutCallbacks {
    fun onCategorySelected(index: Int)
    fun onPinItemClick(category: Category)
    fun onDeleteCategory(category: Category)
}