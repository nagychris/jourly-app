package com.example.jourlyapp.model.journal

import app.cash.turbine.test
import com.example.jourlyapp.model.journal.dao.JournalDao
import com.example.jourlyapp.model.journal.entities.JournalEntry
import com.example.jourlyapp.model.journal.enums.Mood
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.time.LocalDateTime

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class JournalRepositoryTest {

    private val entries = listOf(
        JournalEntry(1, LocalDateTime.now().plusDays(1), Mood.Okay),
        JournalEntry(2, LocalDateTime.now(), Mood.Okay),
        JournalEntry(3, LocalDateTime.now().minusDays(1), Mood.Great),
        JournalEntry(4, LocalDateTime.now().minusDays(2), Mood.Great),
        JournalEntry(5, LocalDateTime.now().minusDays(3), Mood.Great),
        JournalEntry(6, LocalDateTime.now().minusDays(10), Mood.Bad),
    )

    private val journalDao: JournalDao = mockk(relaxed = true)

    private var journalRepository = JournalRepositoryImpl(journalDao)

    @Before
    fun init() {
        every { journalDao.getEntries() } returns flow { emit(entries) }

        every {
            journalDao.getEntriesBetweenDates(
                any(),
                any()
            )
        } returns flow { emit(entries.subList(1, 5)) }
    }

    @Test
    fun journalEntriesCallsDao() = runTest {
        journalRepository.journalEntries().test {
            Assert.assertEquals(awaitItem(), entries)
            awaitComplete()
            verify { journalDao.getEntries() }
            cancel()
        }
    }

    @Test
    fun getEntriesBetweenDatesCallsDao() = runTest {
        journalRepository.getJournalEntriesBetweenDates(
            LocalDateTime.now(),
            LocalDateTime.now().minusDays(7)
        ).test {
            Assert.assertEquals(awaitItem(), entries.subList(1, 5))
            awaitComplete()
            verify { journalDao.getEntriesBetweenDates(any(), any()) }
            cancel()
        }
    }

    @Test
    fun createEntryCallsDao() = runTest {
        journalRepository.createEntry(
            entries[0]
        )

        coVerify { journalDao.insertEntry(entries[0]) }
    }
}