package com.planner.data.room.category

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val dateCreatedTimestamp: Long
) {
    companion object {
        const val CATEGORY_ALL_ID = -1
        val categoryAll = Category(
            id = CATEGORY_ALL_ID,
            title = "All",
            dateCreatedTimestamp = -1
        )
    }
}

class InvalidCategoryException(message: String): Exception(message)
