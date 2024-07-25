package com.planner.ui.util

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

object DateTimeUtil {

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

    fun getLocalTimeFromMillis(millis: Long): LocalTime {
        val instant = Instant.ofEpochMilli(millis)
        val localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime()
        return localDateTime.toLocalTime()
    }
}