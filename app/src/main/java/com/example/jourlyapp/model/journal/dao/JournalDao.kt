package com.example.jourlyapp.model.journal.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.jourlyapp.model.journal.entities.JournalEntry
import com.example.jourlyapp.model.journal.entities.QuestionAnswerPair
import kotlinx.coroutines.flow.Flow

// For accessing journal entries in local DB
// https://developer.android.com/training/data-storage/room/accessing-data
@Dao
interface JournalDao {
    @Insert
    suspend fun insertEntries(entries: List<JournalEntry>)

    @Insert
    suspend fun insertQuestionAnswerPairs(questionAnswerPairs: List<QuestionAnswerPair>)

    @Query("SELECT * FROM journal_entry ORDER BY date DESC")
    fun getEntries(): Flow<List<JournalEntry>>

    @Query(
        "SELECT qa.question, qa.answer FROM journal_entry " +
                "JOIN question_answer_pair as qa ON journal_entry.id = qa.journal_entry_id AND journal_entry_id = :journalEntryId"
    )
    fun getQuestionnaireByEntryId(journalEntryId: Int): List<QuestionAnswerPair>

    @Query("DELETE FROM journal_entry")
    suspend fun deleteAllEntries()

    @Query("DELETE FROM question_answer_pair")
    suspend fun deleteAllQuestionAnswerPairs()
}
