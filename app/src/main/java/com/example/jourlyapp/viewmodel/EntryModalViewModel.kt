package com.example.jourlyapp.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.jourlyapp.JourlyApplication
import com.example.jourlyapp.model.journal.JournalRepository
import com.example.jourlyapp.model.journal.entities.JournalEntry
import com.example.jourlyapp.model.journal.entities.QuestionAnswerPair
import com.example.jourlyapp.model.journal.enums.Mood
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class EntryModalViewModel(private var journalRepository: JournalRepository): ViewModel() {

    val journalEntry = mutableStateOf(JournalEntry(null, date = LocalDateTime.now(), Mood.None))

    var answers = mutableStateListOf("", "", "")

    var questionAnswerPairs = ArrayList<QuestionAnswerPair>()
    fun updateMood(mood: Mood) {
        journalEntry.value.mood = mood
    }

    fun initQuestionAnswerPairs(question: String, index: Int) {
        questionAnswerPairs.add(QuestionAnswerPair(null, journalEntry.value.id?:0, question, answers[index]))
    }

    fun updateAnswer(answer : String, index : Int) {
        answers[index] = answer
    }

    fun createNewQuickEntry(date : LocalDateTime, mood: Mood) = viewModelScope.launch {
        journalRepository.createEntry(JournalEntry(null, date, mood))
    }

    /**
     * The date is updated since we are inserting the entry when the user clicks on the save button, which
     * realistically will happen after he wrote his answers for the detailed entry.
     */
    fun createNewDetailedEntry() = viewModelScope.launch {
        journalEntry.value.date = LocalDateTime.now()
        journalRepository.createEntry(journalEntry.value)

        val entryId = journalRepository.getLastEntryId()
        questionAnswerPairs.forEach { qap ->
            journalRepository.createQuestionAnswerPair(
                QuestionAnswerPair(null, entryId, qap.question, qap.answer)
            )
        }
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