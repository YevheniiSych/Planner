package com.planner.ui.home.category

import com.planner.data.room.category.Category

interface CategoriesLayoutCallbacks {
    fun onCategorySelected(index: Int)
    fun onMenuOpened(category: Category)
    fun onPinItemClick(isPinned: Boolean)
    fun onDeleteItemClick()
}