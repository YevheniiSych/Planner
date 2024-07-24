package com.planner.ui.util

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
sealed class DatePickerSelectableDates : SelectableDates {
    data object Future : DatePickerSelectableDates() {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis >= LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()
                .toEpochMilli()
        }

        override fun isSelectableYear(year: Int): Boolean {
            return year >= LocalDate.now().year
        }
    }

    data object Past : DatePickerSelectableDates() {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis <= System.currentTimeMillis()
        }

        override fun isSelectableYear(year: Int): Boolean {
            return year <= LocalDate.now().year
        }
    }
}