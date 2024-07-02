package com.planner.ui.home

sealed class HomeUIEvent {
    data object OnTaskDeleted: HomeUIEvent()
}