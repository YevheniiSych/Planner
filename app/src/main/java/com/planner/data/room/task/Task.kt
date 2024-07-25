package com.planner.data.room.task

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.planner.data.room.category.Category

typealias TaskId = Int

@Entity(
    tableName = "Task",
)
data class Task(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", defaultValue = "0")
    val id: Int = 0,
    @ColumnInfo(name = "text", defaultValue = "")
    val text: String = "",
    @ColumnInfo(name = "created_at", defaultValue = "0")
    val createdAt: Long = 0,
    @ColumnInfo(name = "is_completed", defaultValue = "false")
    val isCompleted: Boolean = false,
    @ColumnInfo(name = "completed_At", defaultValue = "false")
    val completedAt: Long = 0,
    @ColumnInfo(name = "is_reminder_enabled", defaultValue = "false")
    val isReminderEnabled: Boolean = false,
    @ColumnInfo(name = "reminder_time", defaultValue = "null")
    val reminderTime: Long? = null,
    @Embedded(prefix = "category_")
    val category: Category? = null
) {
    companion object {
        const val INVALID_TASK_ID: TaskId = -1
        val EMPTY = Task()
    }
}

class InvalidTaskException(message: String) : Exception(message)
