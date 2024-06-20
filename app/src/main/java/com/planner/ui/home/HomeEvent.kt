package com.planner.ui.home

import com.planner.data.room.category.Category

sealed class HomeEvent {
    sealed class CategoryEvent: HomeEvent() {
        data class Pin(val category: Category) : CategoryEvent()
        data class Delete(val category: Category) : CategoryEvent()
        data class AddNew(val name: String): CategoryEvent()

        data class Select(val index: Int, val category: Category): CategoryEvent()
    }


}