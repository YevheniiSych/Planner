package com.planner.ui.home

import com.planner.data.room.category.Category

sealed class HomeEvent {

    data class PinCategory(val category: Category): HomeEvent()
    data class DeleteCategory(val category: Category): HomeEvent()
    data class AddNewCategory(val category: Category): HomeEvent()
}