package com.planner.data.room.task

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.planner.data.room.category.Category

@Entity(
    tableName = "Task",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
)
data class Task(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", defaultValue = "0")
    val id: Int = 0,
    @ColumnInfo(name = "text", defaultValue = "")
    val text: String = "",
    @ColumnInfo(name = "createdAt", defaultValue = "0")
    val createdAt: Long = 0,
    @ColumnInfo(name = "isCompleted", defaultValue = "false")
    val isCompleted: Boolean = false,
    @ColumnInfo(name = "completedAt", defaultValue = "false")
    val completedAt: Long = 0,
    @ColumnInfo(name = "isReminderEnabled", defaultValue = "false")
    val isReminderEnabled: Boolean = false,
    @ColumnInfo(name = "reminderTime", defaultValue = "null")
    val reminderTime: Long? = null,
    @ColumnInfo(name = "categoryId")
    val categoryId: Int? = null
) {
    companion object {
        val EMPTY = Task()
    }
}

class InvalidTaskException(message: String): Exception(message)
