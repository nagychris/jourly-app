package com.example.jourlyapp.viewmodel

import androidx.compose.runtime.mutableStateOf
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

    fun updateMood(mood: Mood) {
        journalEntry.value.mood = mood
    }

    fun updateDate(date: LocalDateTime) {
        journalEntry.value.date = date
    }
    fun createNewQuickEntry(date : LocalDateTime, mood: Mood) = viewModelScope.launch {
        journalRepository.createEntry(JournalEntry(null, date, mood))
    }

    fun getLastEntryId() : Int? {
        return journalRepository.getLastEntryId()
    }

    fun createNewEntry(journalEntry: JournalEntry) = viewModelScope.launch {
        journalRepository.createEntry(journalEntry)
    }

    fun createNewQuestionAnswerPair(qa: QuestionAnswerPair) = viewModelScope.launch {
        val lastEntryId = getLastEntryId()?:0
        journalRepository.createQuestionAnswerPair(QuestionAnswerPair(null, lastEntryId, qa.question, qa.answer))
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