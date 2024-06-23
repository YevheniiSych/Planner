package com.planner.ui.home

import com.planner.ui.home.category.CategoryItem

sealed class HomeEvent {
    sealed class CategoryEvent: HomeEvent() {
        data class Pin(val isPinned: Boolean = false) : CategoryEvent()
        data object Delete : CategoryEvent()
        data class AddNew(val categoryName: String): CategoryEvent()
        data class Selected(val index: Int, val category: CategoryItem) : CategoryEvent()
        data class ManageCategory(val category: CategoryItem) : CategoryEvent()
    }


}