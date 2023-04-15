package com.example.jourlyapp.model.journal

import com.example.jourlyapp.model.journal.dao.JournalDao
import com.example.jourlyapp.model.journal.entities.JournalEntry
import com.example.jourlyapp.model.journal.entities.QuestionAnswerPair
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

class JournalRepository(private val journalDao: JournalDao) {

    val journalEntries: Flow<List<JournalEntry>> = journalDao.getEntries()

    fun getJournalEntriesBetweenDates(
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): Flow<List<JournalEntry>> {
        return journalDao.getEntriesBetweenDates(
            startDate,
            endDate
        )
    }

    suspend fun createEntry(journalEntry: JournalEntry) = journalDao.insertEntry(journalEntry)

    suspend fun createQuestionAnswerPair(questionAnswerPair: QuestionAnswerPair) =
        journalDao.insertQuestionAnswerPair(questionAnswerPair)
    suspend fun deleteEntryById (entryId: Int) = journalDao.deleteEntryById(journalEntryId = entryId)

    fun getLastEntryId () : Int? = journalDao.getLastEntry().id

}