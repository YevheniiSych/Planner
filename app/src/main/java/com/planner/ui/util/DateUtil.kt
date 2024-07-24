package com.planner.ui.util

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.Calendar
import java.util.Locale

object DateUtil {

    fun from(millis: Long, format: String): String {
        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = millis
        return formatter.format(calendar.time)
    }

    fun convertToFullDateInMillis(hour: Int, minute: Int, dateInMillis: Long): Long {
        val instant = Instant.ofEpochMilli(dateInMillis)
        val localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate()
        val localTime = LocalTime.of(hour, minute)
        val localDateTime = LocalDateTime.of(localDate, localTime)
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

}