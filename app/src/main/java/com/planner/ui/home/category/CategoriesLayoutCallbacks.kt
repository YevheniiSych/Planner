package com.planner.ui.home.category

interface CategoriesLayoutCallbacks {
    fun onCategorySelected(index: Int, category: CategoryItem)
    fun onMenuOpened(category: CategoryItem)
    fun onPinItemClick(isPinned: Boolean)
    fun onDeleteItemClick()
}