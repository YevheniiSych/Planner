package com.planner.ui.home

import com.planner.data.room.category.Category
import com.planner.data.room.task.Task

data class HomeState(
    val categories: List<Category> = emptyList(),
    val tasks: List<Task> = emptyList(),
    val selectedCategoryIndex: Int = 0,
    val categoryToManage: Category = Category.EMPTY, //Needs to make interactions with existing category (like interactions from Dropdown menu)
)