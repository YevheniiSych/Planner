package com.planner.data.room.category

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Category")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
    val dateCreatedTimestamp: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "isPinned", defaultValue = "false")
    val isPinned: Boolean = false,
    @ColumnInfo(name = "lastPinTime", defaultValue = "0")
    val lastPinTime: Long = 0
)

class InvalidCategoryException(message: String): Exception(message)
