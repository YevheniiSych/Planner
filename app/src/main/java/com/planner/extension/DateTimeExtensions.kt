package com.planner.extension

import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun LocalTime.format(pattern: String): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return this.format(formatter)
}