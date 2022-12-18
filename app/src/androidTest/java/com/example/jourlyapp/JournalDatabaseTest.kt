package com.example.jourlyapp

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.example.jourlyapp.model.journal.dao.JournalDao
import com.example.jourlyapp.model.journal.db.AppDatabase
import com.example.jourlyapp.model.journal.entities.JournalEntry
import com.example.jourlyapp.model.journal.entities.QuestionAnswerPair
import com.example.jourlyapp.model.journal.enums.JournalQuestion
import com.example.jourlyapp.model.journal.enums.Mood
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import java.io.IOException
import java.time.LocalDateTime

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@SmallTest
class JournalDatabaseTest {
    private val TAG = this::class.simpleName
    private lateinit var database: AppDatabase
    private lateinit var journalDao: JournalDao

    private val entries = listOf(
        JournalEntry(null, LocalDateTime.of(2022, 12, 1, 12, 0, 0), Mood.Okay),
        JournalEntry(null, LocalDateTime.of(2022, 12, 2, 12, 0, 0), Mood.Great),
        JournalEntry(null, LocalDateTime.of(2022, 12, 3, 12, 0, 0), Mood.Bad),
    )

    private val expectedEntries = listOf(
        JournalEntry(1, LocalDateTime.of(2022, 12, 1, 12, 0, 0), Mood.Okay),
        JournalEntry(2, LocalDateTime.of(2022, 12, 2, 12, 0, 0), Mood.Great),
        JournalEntry(3, LocalDateTime.of(2022, 12, 3, 12, 0, 0), Mood.Bad),
    )

    private val questionAnswerPairs = listOf(
        QuestionAnswerPair(null, 1, JournalQuestion.QUESTION1.toString(), "Answer"),
        QuestionAnswerPair(null, 1, JournalQuestion.QUESTION2.toString(), null),
        QuestionAnswerPair(null, 2, JournalQuestion.QUESTION3.toString(), "Answer"),
    )

    private val expectedQaPairs = listOf(
        QuestionAnswerPair(1, 1, JournalQuestion.QUESTION1.toString(), "Answer"),
        QuestionAnswerPair(2, 1, JournalQuestion.QUESTION2.toString(), null),
        QuestionAnswerPair(3, 2, JournalQuestion.QUESTION3.toString(), "Answer"),
    )

    @Before
    fun setup() {
        // in-memory database deletes all data after each run
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
        journalDao = database.journalDao()
    }

    @After
    @Throws(IOException::class)
    fun cleanup() {
        database.close()
    }

    @Test
    fun insertedEntriesShouldBePresent() = runTest {
        entries.forEach {
            journalDao.insertEntry(it)
        }

        journalDao.getEntries().test {
            val list = awaitItem()
            assert(list.size == 3)
            assert(list.contains(expectedEntries[0]))
            assert(list.contains(expectedEntries[1]))
            assert(list.contains(expectedEntries[2]))
            cancel()
        }
    }

    @Test
    fun deletedEntryShouldNotBePresent() = runTest {
        entries.forEach {
            journalDao.insertEntry(it)
        }

        journalDao.deleteEntryById(expectedEntries[1].id!!)

        journalDao.getEntries().test {
            val list = awaitItem()
            assert(list.size == 2)
            assert(list.contains(expectedEntries[0]))
            assert(!list.contains(expectedEntries[1]))
            assert(list.contains(expectedEntries[2]))
            cancel()
        }
    }

    @Test
    fun deleteAllEntriesClearsEntries() = runTest {
        entries.forEach {
            journalDao.insertEntry(it)
        }

        journalDao.deleteAllEntries()

        journalDao.getEntries().test {
            val list = awaitItem()
            assert(list.isEmpty())
            cancel()
        }
    }

    @Test
    fun insertedQAPairsShouldBeAssociatedWithCorrectEntry() = runTest {
        entries.forEach {
            journalDao.insertEntry(it)
        }
        questionAnswerPairs.forEach {
            journalDao.insertQuestionAnswerPair(it)
        }

        val questionnaire1 = journalDao.getQuestionnaireByEntryId(1)
        assert(questionnaire1.size == 2)
        assert(questionnaire1.contains(expectedQaPairs[0]))
        assert(questionnaire1.contains(expectedQaPairs[1]))
        val questionnaire2 = journalDao.getQuestionnaireByEntryId(2)
        assert(questionnaire2.size == 1)
        assert(questionnaire2.contains(expectedQaPairs[2]))
    }

    @Test
    fun deleteAllQuestionAnswerPairsShouldClearAllQAPairs() = runTest {
        entries.forEach {
            journalDao.insertEntry(it)
        }
        questionAnswerPairs.forEach {
            journalDao.insertQuestionAnswerPair(it)
        }

        journalDao.deleteAllQuestionAnswerPairs()

        val questionnaire1 = journalDao.getQuestionnaireByEntryId(1)
        val questionnaire2 = journalDao.getQuestionnaireByEntryId(1)

        assert(questionnaire1.isEmpty())
        assert(questionnaire2.isEmpty())
    }
}