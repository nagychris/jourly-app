package com.example.jourlyapp.model.journal.db

import androidx.room.TypeConverter
import java.util.Date

// Provides type converters for storing complex objects (e.g. Date) in the Room DB
// https://developer.android.com/training/data-storage/room/referencing-data#type-converters
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}