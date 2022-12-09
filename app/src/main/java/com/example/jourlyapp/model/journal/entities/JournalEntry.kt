package com.example.jourlyapp.model.journal.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.jourlyapp.model.journal.enums.Mood
import java.util.Date

@Entity(tableName = "journal_entry")
class JournalEntry(
    @PrimaryKey val id: Int,
    val date: Date,
    var mood: Mood = Mood.None,
)