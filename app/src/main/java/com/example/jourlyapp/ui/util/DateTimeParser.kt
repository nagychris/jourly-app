package com.example.jourlyapp.ui.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateTimeParser {
    companion object {
        private val dateFormatter = DateTimeFormatter.ofPattern("d MMM uuuu")

        fun toDateString(localDateTime: LocalDateTime): String {
            return dateFormatter.format(localDateTime)
        }
    }
}