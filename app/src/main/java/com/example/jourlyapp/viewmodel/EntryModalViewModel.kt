package com.example.jourlyapp.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.jourlyapp.JourlyApplication
import com.example.jourlyapp.model.journal.JournalRepository
import com.example.jourlyapp.model.journal.entities.JournalEntry
import com.example.jourlyapp.model.journal.entities.QuestionAnswerPair
import com.example.jourlyapp.model.journal.enums.Mood
import com.example.jourlyapp.ui.util.RandomQuestionGenerator
import java.time.LocalDateTime

const val TAG = "EntryModalViewModel"

class EntryModalViewModel(private var journalRepository: JournalRepository) :
    ViewModel() {

    val mood = mutableStateOf(Mood.None)

    val questions = RandomQuestionGenerator.randomQuestions()

    var answers = mutableStateListOf("", "", "")

    fun updateMood(newMood: Mood) {
        mood.value = newMood
    }

    fun updateAnswer(index: Int, newAnswer: String) {
        answers[index] = newAnswer
    }

    suspend fun createNewQuickEntry(mood: Mood) {
        val journalEntry = JournalEntry(
            null,
            LocalDateTime.now(),
            mood
        )
        journalRepository.createEntry(journalEntry)

        // query id of inserted entry => associate with QA-pairs
        val entryId = journalRepository.getLastEntryId()
        Log.d(TAG, "Created Entry: $journalEntry with id $entryId")

        questions.forEachIndexed { _, question ->
            val questionAnswerPair =
                QuestionAnswerPair(null, entryId, question, "")
            journalRepository.createQuestionAnswerPair(questionAnswerPair)
            Log.d(
                TAG,
                "Created QA pair:  ${questionAnswerPair.question} - ${questionAnswerPair.answer}"
            )
        }
        reset()

    }

    /**
     * The date is updated since we are inserting the entry when the user clicks on the save button, which
     * realistically will happen after he wrote his answers for the detailed entry.
     */
    suspend fun createNewDetailedEntry() {
        val journalEntry = JournalEntry(
            null,
            LocalDateTime.now(),
            mood.value
        )
        journalRepository.createEntry(journalEntry)

        // query id of inserted entry => associate with QA-pairs
        val entryId = journalRepository.getLastEntryId()
        Log.d(TAG, "Created Entry: $journalEntry with id $entryId")

        questions.forEachIndexed { index, question ->
            val questionAnswerPair =
                QuestionAnswerPair(null, entryId, question, answers[index])
            journalRepository.createQuestionAnswerPair(questionAnswerPair)
            Log.d(
                TAG,
                "Created QA pair:  ${questionAnswerPair.question} - ${questionAnswerPair.answer}"
            )
        }
        reset()
    }

    fun reset() {
        mood.value = Mood.None
        answers = mutableStateListOf("", "", "")
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val journalRepository =
                    (this[APPLICATION_KEY] as JourlyApplication).journalRepository
                EntryModalViewModel(
                    journalRepository = journalRepository
                )
            }
        }
    }
}