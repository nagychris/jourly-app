package com.example.jourlyapp.model.journal.db

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Provides type converters for storing complex objects (e.g. Date) in the Room DB
 * See: https://developer.android.com/training/data-storage/room/referencing-data#type-converters
 */
class Converters {

    @TypeConverter
    fun toDate(dateString: String?): LocalDateTime? {
        return if (dateString == null) {
            null
        } else if (dateString.length == 10) {
            // LocalDate values without time information
            LocalDate.parse(dateString).atStartOfDay()
        } else {
            LocalDateTime.parse(dateString)
        }
    }

    @TypeConverter
    fun toDateString(date: LocalDateTime?): String? {
        return date?.toString()
    }
}