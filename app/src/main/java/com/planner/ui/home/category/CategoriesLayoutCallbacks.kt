package com.planner.ui.home.category

import com.planner.data.room.category.Category

interface CategoriesLayoutCallbacks {
    fun onCategorySelected(index: Int, category: Category)
    fun onPinItemClick(category: Category)
    fun onDeleteItemClick(category: Category)
    fun onAddNewItemClick()
}