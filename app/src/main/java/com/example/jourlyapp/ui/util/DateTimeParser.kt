package com.example.jourlyapp.ui.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateTimeParser {
    companion object {
        private val longFormatter = DateTimeFormatter.ofPattern("d MMM uuuu")
        private val shortFormatter = DateTimeFormatter.ofPattern("d/M")

        fun toLongDateString(localDateTime: LocalDateTime): String {
            return longFormatter.format(localDateTime)
        }

        fun toShortDateString(localDateTime: LocalDateTime): String {
            return shortFormatter.format(localDateTime)
        }
    }
}