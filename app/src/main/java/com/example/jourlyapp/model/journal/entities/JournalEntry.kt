package com.example.jourlyapp.model.journal.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.jourlyapp.model.journal.enums.Mood
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

/**
 * Simple journal entry with the current date and the mood.
 * The questionnaire of an entry is created using [QuestionAnswerPair]s that are linked to this entity using the foreign
 * key [QuestionAnswerPair.journalEntryId].
 */
@Entity(tableName = "journal_entry")
data class JournalEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    var date: LocalDateTime = LocalDateTime.now(),
    var mood: Mood = Mood.None,
) {
    init {
        // for now accuracy of minutes are enough
        date = date.truncatedTo(ChronoUnit.MINUTES)
    }
}