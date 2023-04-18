package com.example.jourlyapp.model.journal

import com.example.jourlyapp.model.journal.dao.JournalDao
import com.example.jourlyapp.model.journal.entities.JournalEntry
import com.example.jourlyapp.model.journal.entities.QuestionAnswerPair
import com.example.jourlyapp.model.journal.enums.Mood
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface JournalRepository {
    fun journalEntries(): Flow<List<JournalEntry>>

    fun getJournalEntryById(id: Int): JournalEntry?

    fun getJournalEntriesBetweenDates(
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): Flow<List<JournalEntry>>

    suspend fun createEntry(journalEntry: JournalEntry)

    suspend fun deleteEntryById(entryId: Int)

    suspend fun createQuestionAnswerPair(questionAnswerPair: QuestionAnswerPair)

    fun getLastEntryId(): Int

    fun getQuestionAnswerPairsByEntryId(entryId: Int): List<QuestionAnswerPair>

    fun updateJournalEntryMood(mood: Mood, entryId: Int)

    fun updateQuestionAnswerPair(answer: String, id: Int)
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

    override fun getLastEntryId(): Int = journalDao.getLastEntryId()

    override fun getJournalEntryById(id: Int): JournalEntry? =
        journalDao.getEntryById(id)

    override fun getQuestionAnswerPairsByEntryId(entryId: Int): List<QuestionAnswerPair> =
        journalDao.getQuestionnaireByEntryId(entryId)

    override fun updateJournalEntryMood(mood: Mood, entryId: Int) {
        journalDao.updateJournalEntryMood(mood, entryId)
    }

    override fun updateQuestionAnswerPair(answer: String, id: Int) {
        journalDao.updateAnswer(answer, id)
    }
}