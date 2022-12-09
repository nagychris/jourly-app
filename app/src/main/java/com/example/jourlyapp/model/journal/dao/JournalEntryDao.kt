package com.example.jourlyapp.model.journal.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.jourlyapp.model.journal.entities.JournalEntry
import com.example.jourlyapp.model.journal.entities.QuestionAnswerPair

// For accessing journal entries in local DB
// https://developer.android.com/training/data-storage/room/accessing-data
@Dao
interface JournalEntryDAO {
    @Insert
    fun insertEntries(entries: List<JournalEntry>)

    @Insert
    fun insertQuestionAnswerPairs(questionAnswerPairs: List<QuestionAnswerPair>)

    @Query("SELECT * FROM journal_entry")
    fun getEntries(): List<JournalEntry>

    @Query(
        "SELECT * FROM journal_entry " +
                "JOIN question_answer_pair ON journal_entry.id = question_answer_pair.journal_entry_id"
    )
    fun getEntryWithQuestionnaire(): Map<JournalEntry, List<QuestionAnswerPair>>
}
