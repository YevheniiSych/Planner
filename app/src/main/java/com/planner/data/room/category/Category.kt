package com.planner.data.room.category

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val dateCreatedTimestamp: Long
)
