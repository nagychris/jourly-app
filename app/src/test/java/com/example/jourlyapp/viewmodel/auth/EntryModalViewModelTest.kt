package com.example.jourlyapp.viewmodel.entry

import com.example.jourlyapp.model.journal.JournalRepositoryImpl
import com.example.jourlyapp.model.journal.enums.Mood
import com.example.jourlyapp.viewmodel.EntryModalViewModel
import io.mockk.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class EntryModalViewModelTest {

    private var journalRepository = mockk<JournalRepositoryImpl>()

    private val viewModel = EntryModalViewModel(journalRepository)

    @Test
    fun `mood is initialized with None value`() {
        assertEquals(viewModel.mood.value, Mood.None)
    }

    @Test
    fun `answers are initialized with empty strings`() {
        assertEquals(viewModel.answers[0], "")
        assertEquals(viewModel.answers[1], "")
        assertEquals(viewModel.answers[2], "")
    }

    @Test
    fun `mood is updated correctly`() {
        val newMood : Mood = Mood.Great
        viewModel.updateMood(newMood = newMood)
        assertEquals(viewModel.mood.value, newMood)
    }

    @Test
    fun `answers are updated correctly`() {
        val newAnswer0 = "Today I am grateful"
        val newAnswer1 = "to be able to"
        val newAnswer2 = "test the application."
        viewModel.updateAnswer(0,newAnswer0)
        viewModel.updateAnswer(1, newAnswer1)
        viewModel.updateAnswer(2, newAnswer2)
        assertEquals(viewModel.answers[0], newAnswer0)
        assertEquals(viewModel.answers[1], newAnswer1)
        assertEquals(viewModel.answers[2], newAnswer2)
    }

    @Test
    fun `Reset function correctly resets mood and answers`() {
        val newMood : Mood = Mood.Great
        val newAnswer0 = "Today I am grateful"
        val newAnswer1 = "to be able to"
        val newAnswer2 = "test the application."
        viewModel.updateMood(newMood = newMood)
        viewModel.updateAnswer(0,newAnswer0)
        viewModel.updateAnswer(1, newAnswer1)
        viewModel.updateAnswer(2, newAnswer2)

        viewModel.reset()

        assertEquals(viewModel.mood.value, Mood.None)
        assertEquals(viewModel.answers[0], "")
        assertEquals(viewModel.answers[1], "")
        assertEquals(viewModel.answers[2], "")
    }

}