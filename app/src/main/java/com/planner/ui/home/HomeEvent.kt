package com.planner.ui.home

import com.planner.data.room.category.Category

sealed class HomeEvent {
    sealed class CategoryEvent: HomeEvent() {
        data object Pin : CategoryEvent()
        data object Delete : CategoryEvent()
        data class AddNew(val categoryName: String): CategoryEvent()
        data class Selected(val index: Int, val category: Category): CategoryEvent()
        data class ManageCategory(val category: Category): CategoryEvent()
    }


}