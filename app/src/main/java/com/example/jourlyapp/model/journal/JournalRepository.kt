package com.example.jourlyapp.model.journal

import com.example.jourlyapp.model.journal.dao.JournalDao
import com.example.jourlyapp.model.journal.entities.JournalEntry
import com.example.jourlyapp.model.journal.entities.QuestionAnswerPair
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface JournalRepository {
    fun journalEntries(): Flow<List<JournalEntry>>

    fun getJournalEntriesBetweenDates(
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): Flow<List<JournalEntry>>

    suspend fun createEntry(journalEntry: JournalEntry)

    suspend fun deleteEntryById(entryId: Int)

    suspend fun createQuestionAnswerPair(questionAnswerPair: QuestionAnswerPair)

    fun getLastEntryId () : Int
}

class JournalRepositoryImpl(private val journalDao: JournalDao) :
    JournalRepository {

    override fun journalEntries(): Flow<List<JournalEntry>> =
        journalDao.getEntries()

    override fun getJournalEntriesBetweenDates(
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): Flow<List<JournalEntry>> {
        return journalDao.getEntriesBetweenDates(
            startDate,
            endDate
        )
    }

    override suspend fun createEntry(journalEntry: JournalEntry) =
        journalDao.insertEntry(journalEntry)

    override suspend fun deleteEntryById(entryId: Int) =
        journalDao.deleteEntryById(entryId)

    override suspend fun createQuestionAnswerPair(questionAnswerPair: QuestionAnswerPair) =
        journalDao.insertQuestionAnswerPair(questionAnswerPair)

    override fun getLastEntryId () : Int = journalDao.getLastEntryId()
}