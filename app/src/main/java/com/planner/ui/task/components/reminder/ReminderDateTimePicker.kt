package com.planner.ui.task.components.reminder

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.planner.extension.isPastTimeSelected
import com.planner.extension.isTodaySelected
import com.planner.ui.util.DatePickerSelectableDates
import com.planner.ui.util.DateTimeUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderDateTimePicker(
    initialSelectedDateMillis: Long,
    initialHour: Int,
    initialMinute: Int,
    onConfirm: (reminderTime: Long) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val timePickerState = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute,
        is24Hour = true
    )

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialSelectedDateMillis,
        selectableDates = DatePickerSelectableDates.Future
    )

    var showDatePicker by rememberSaveable {
        mutableStateOf(false)
    }

    val selectedDate = remember {
        derivedStateOf {
            DateTimeUtil.from(
                datePickerState.selectedDateMillis ?: System.currentTimeMillis(),
                "dd.MM.yyyy"
            )
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = modifier
                .background(
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.surface
                )
                .padding(vertical = 20.dp),

            ) {
            if (showDatePicker) {
                BackHandler {
                    showDatePicker = false
                }
                ReminderDatePicker(
                    state = datePickerState,
                    onConfirm = {

                        showDatePicker = false
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                DateSelectionButton(
                    selectedDateText = selectedDate.value,
                    onClick = {
                        showDatePicker = true
                    },
                    modifier = Modifier
                        .align(Alignment.End)
                )

                Spacer(modifier = Modifier.height(10.dp))

                ReminderTimePicker(
                    state = timePickerState,
                    isToday = datePickerState.isTodaySelected(),
                    onConfirm = {
                        onConfirm(
                            DateTimeUtil.convertToFullDateInMillis(
                                hour = timePickerState.hour,
                                minute = timePickerState.minute,
                                dateInMillis = datePickerState.selectedDateMillis
                                    ?: System.currentTimeMillis()
                            )
                        )
                    },
                    onDismiss = onDismiss,
                    modifier = Modifier
                )
            }
        }
    }
}


@Composable
private fun DateSelectionButton(
    selectedDateText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = selectedDateText,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.width(5.dp))
        Icon(
            imageVector = Icons.Default.CalendarMonth,
            contentDescription = "Calendar icon"
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReminderTimePicker(
    state: TimePickerState,
    isToday: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isSelectedTimeEligible = remember {
        derivedStateOf {
            !(isToday && state.isPastTimeSelected())
        }
    }
    var timePickerWidth by remember {
        mutableStateOf(0.dp)
    }
    val density = LocalDensity.current
    Column(
        modifier = modifier
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TimePicker(
            modifier = Modifier
                .onGloballyPositioned { layoutCoordinates ->
                    timePickerWidth = with(density) { layoutCoordinates.size.width.toDp() }
                },
            state = state,
        )
        if (!isSelectedTimeEligible.value) {
            Text(
                modifier = Modifier
                    .width(timePickerWidth),
                text = "Reminder can't be set on the past time. Please select any time in future.",
                color = Color.Red
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.End)
        ) {
            TextButton(onClick = { onDismiss() }) {
                Text(text = "Cancel")
            }
            TextButton(
                onClick = {
                    onConfirm()
                },
                enabled = isSelectedTimeEligible.value
            ) {
                Text(text = "Confirm")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReminderDatePicker(
    state: DatePickerState,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        DatePicker(
            state = state
        )
        TextButton(
            modifier = Modifier
                .padding(end = 30.dp)
                .align(Alignment.End),
            onClick = { onConfirm() }
        ) {
            Text(
                text = "OK",
                fontSize = 16.sp
            )
        }
    }
}

@Preview
@Composable
private fun ReminderDateTimePickerPreview() {
    ReminderDateTimePicker(
        initialSelectedDateMillis = 1721892245191,
        initialHour = 10,
        initialMinute = 30,
        onConfirm = {},
        onDismiss = {},
        modifier = Modifier
    )
}