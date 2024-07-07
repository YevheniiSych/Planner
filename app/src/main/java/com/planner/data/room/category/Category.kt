package com.planner.data.room.category

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo( name ="id", defaultValue = "0")
    val id: Int = 0,
    @ColumnInfo( name ="title", defaultValue = "")
    val title: String = "",
    @ColumnInfo( name ="date_created_timestamp", defaultValue = "0")
    val dateCreatedTimestamp: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "is_pinned", defaultValue = "false")
    val isPinned: Boolean = false,
    @ColumnInfo(name = "last_pin_time", defaultValue = "0")
    val lastPinTime: Long = 0
) {
    companion object {
        const val CATEGORY_ALL_ID = -1
        val CATEGORY_ALL = Category(
            id = CATEGORY_ALL_ID,
            title = "All",
            dateCreatedTimestamp = -1,
            isPinned = true
        )
        val EMPTY = Category()
    }
}

class InvalidCategoryException(message: String): Exception(message)
