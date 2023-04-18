package com.example.jourlyapp.model.journal.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * A pair of a question and the corresponding answer. Typically one [JournalEntry] has a questionnaire consisting of
 * several [QuestionAnswerPair]s.
 */
@Entity(
    tableName = "question_answer_pair",
    foreignKeys = [ForeignKey(
        entity = JournalEntry::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("journal_entry_id"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class QuestionAnswerPair(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "journal_entry_id", index = true)
    val journalEntryId: Int,
    var question: String,
    var answer: String = ""
)