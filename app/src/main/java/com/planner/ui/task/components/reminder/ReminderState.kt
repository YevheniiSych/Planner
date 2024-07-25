package com.planner.ui.task.components.reminder

import com.planner.extension.format
import com.planner.ui.util.DateTimeUtil
import java.time.LocalTime

data class ReminderState(
    val timeMillis: Long? = null,
    val isEnabled: Boolean = false
) {
    val dateString: String
        get() =
            DateTimeUtil.from(
                millis = timeMillis ?: System.currentTimeMillis(),
                format = "dd.MM.yyyy"
            )

    val timeString: String
        get() = localTime.format("HH:mm")

    val hour: Int
        get() = localTime.hour

    val minute: Int
        get() = localTime.minute


    private val localTime: LocalTime
        get() = DateTimeUtil.getLocalTimeFromMillis(
            timeMillis ?: System.currentTimeMillis()
        )

}
