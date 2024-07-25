package com.planner.ui.task.components.reminder

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text

@Composable
fun TaskReminderLayout(
    state: ReminderState,
    onReminderSet: (isReminderEnabled: Boolean, reminderTime: Long?) -> Unit,
    modifier: Modifier = Modifier
) {

    var showDateTimePicker by rememberSaveable {
        mutableStateOf(false)
    }

    Row(
        modifier = modifier
            .clickable {
                showDateTimePicker = true
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Alarm,
            contentDescription = "Reminder icon",
            modifier = Modifier
                .size(22.dp),
            tint = Color.Black
        )
        Spacer(modifier = Modifier.width(20.dp))

        Text(
            text = "Reminder",
            fontSize = 18.sp,
            color = Color.Black
        )

        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            if (state.isEnabled) {
                Text(
                    text = state.timeString,
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = state.dateString,
                    fontSize = 10.sp,
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.width(20.dp))

        Switch(
            checked = state.isEnabled,
            onCheckedChange = { isChecked ->
                if (isChecked) {
                    showDateTimePicker = true
                } else {
                    onReminderSet(false, null)
                }
            }
        )
    }

    if (showDateTimePicker) {
        ReminderDateTimePicker(
            initialSelectedDateMillis = state.timeMillis ?: System.currentTimeMillis(),
            initialHour = state.hour,
            initialMinute = state.minute,
            onConfirm = {
                onReminderSet(
                    true,
                    it
                )
                showDateTimePicker = false
            },
            onDismiss = {
                showDateTimePicker = false
            },
            modifier = Modifier,
        )
    }
}

@Preview
@Composable
private fun TaskReminderLayoutPreview() {
    TaskReminderLayout(
        state = ReminderState(),
        onReminderSet = { _, _ -> },
        modifier = Modifier
            .fillMaxWidth()
    )
}