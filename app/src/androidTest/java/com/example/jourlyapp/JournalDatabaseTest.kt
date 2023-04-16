package com.example.jourlyapp

import android.util.Log
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
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@SmallTest
class JournalDatabaseTest {
    private lateinit var database: AppDatabase
    private lateinit var journalDao: JournalDao

    private val entries = listOf(
        JournalEntry(null, LocalDateTime.now().plusDays(1), Mood.Okay),
        JournalEntry(null, LocalDateTime.now(), Mood.Okay),
        JournalEntry(null, LocalDateTime.now().minusDays(1), Mood.Great),
        JournalEntry(null, LocalDateTime.now().minusDays(2), Mood.Great),
        JournalEntry(null, LocalDateTime.now().minusDays(3), Mood.Great),
        JournalEntry(null, LocalDateTime.now().minusDays(10), Mood.Bad),
    )

    private val expectedEntries = listOf(
        JournalEntry(1, LocalDateTime.now().plusDays(1), Mood.Okay),
        JournalEntry(2, LocalDateTime.now(), Mood.Okay),
        JournalEntry(3, LocalDateTime.now().minusDays(1), Mood.Great),
        JournalEntry(4, LocalDateTime.now().minusDays(2), Mood.Great),
        JournalEntry(5, LocalDateTime.now().minusDays(3), Mood.Great),
        JournalEntry(6, LocalDateTime.now().minusDays(10), Mood.Bad),
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

        // pre-populate with dummy entries
        runTest {
            entries.forEach {
                journalDao.insertEntry(it)
            }
            questionAnswerPairs.forEach {
                journalDao.insertQuestionAnswerPair(it)
            }
        }
    }

    @After
    @Throws(IOException::class)
    fun cleanup() {
        database.close()
    }

    @Test
    fun insertedEntriesShouldBePresent() = runTest {
        journalDao.getEntries().test {
            val list = awaitItem()
            assert(list == expectedEntries)
            cancel()
        }
    }

    @Test
    fun getEntriesBetweenDatesReturnsExpectedEntries() = runTest {
        val expected = listOf(
            JournalEntry(5, LocalDateTime.now().minusDays(3), Mood.Great),
            JournalEntry(4, LocalDateTime.now().minusDays(2), Mood.Great),
            JournalEntry(3, LocalDateTime.now().minusDays(1), Mood.Great),
            JournalEntry(2, LocalDateTime.now(), Mood.Okay),
        )

        journalDao.getEntriesBetweenDates(
            LocalDateTime.now().minusDays(7).truncatedTo(ChronoUnit.MINUTES),
            LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        ).test {
            val list = awaitItem()
            Log.d("TEST", list.toString())
            assert(expected.size == list.size)
            assert(list == expected)
            cancel()
        }
    }

    @Test
    fun deletedEntryShouldNotBePresent() = runTest {
        journalDao.deleteEntryById(expectedEntries[1].id!!)

        journalDao.getEntries().test {
            val list = awaitItem()
            assert(list.size == expectedEntries.size - 1)
            assert(list.contains(expectedEntries[0]))
            assert(!list.contains(expectedEntries[1]))
            assert(list.contains(expectedEntries[2]))
            assert(list.contains(expectedEntries[3]))
            assert(list.contains(expectedEntries[4]))
            cancel()
        }
    }

    @Test
    fun deleteAllEntriesClearsEntries() = runTest {
        journalDao.deleteAllEntries()

        journalDao.getEntries().test {
            val list = awaitItem()
            assert(list.isEmpty())
            cancel()
        }
    }

    @Test
    fun insertedQAPairsShouldBeAssociatedWithCorrectEntry() = runTest {
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
        journalDao.deleteAllQuestionAnswerPairs()

        val questionnaire1 = journalDao.getQuestionnaireByEntryId(1)
        val questionnaire2 = journalDao.getQuestionnaireByEntryId(1)

        assert(questionnaire1.isEmpty())
        assert(questionnaire2.isEmpty())
    }

    @Test
    fun getEntryByIdReturnsExpectedEntry() = runTest {
        val expectedEntry = expectedEntries[0]

        val entry = journalDao.getEntryById(1)

        assert(entry == expectedEntry)
    }
}