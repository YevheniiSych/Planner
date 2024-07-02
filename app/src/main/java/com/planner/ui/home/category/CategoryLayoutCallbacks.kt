package com.planner.ui.home.category

import com.planner.data.room.category.Category

interface CategoryLayoutCallbacks {
    fun onCategorySelected(category: Category)
    fun onPinCategory(category: Category)
    fun onDeleteCategory(category: Category)
}