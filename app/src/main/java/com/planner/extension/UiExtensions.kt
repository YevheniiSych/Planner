package com.planner.extension

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

//return true if no date selected
@OptIn(ExperimentalMaterial3Api::class)
fun DatePickerState.isTodaySelected(): Boolean {
    val givenDate = Instant.ofEpochMilli(
        this.selectedDateMillis ?: return false
    )
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
    val today = LocalDate.now()
    return givenDate == today
}

@OptIn(ExperimentalMaterial3Api::class)
fun TimePickerState.isPastTimeSelected(): Boolean {
    val currentHour = LocalTime.now().hour
    val selectedHour = this.hour
    val currentMinute = LocalTime.now().minute
    val selectedMinute = this.minute
    return selectedHour < currentHour
            || (currentHour == selectedHour && selectedMinute < currentMinute)
}