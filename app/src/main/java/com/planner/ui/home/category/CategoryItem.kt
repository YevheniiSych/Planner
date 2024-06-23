package com.planner.ui.home.category

data class CategoryItem(
    val id: Int = 0,
    val title: String = "",
    val isPinned: Boolean = false,
    val isSelected: Boolean = false
) {
    companion object {
        const val CATEGORY_ALL_ID = -1
        val CATEGORY_ALL = CategoryItem(
            id = CATEGORY_ALL_ID,
            title = "All",
            isPinned = true
        )
        val EMPTY = CategoryItem()
    }
}