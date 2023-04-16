package com.example.jourlyapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.jourlyapp.JourlyApplication
import com.example.jourlyapp.model.journal.JournalRepositoryImpl
import com.example.jourlyapp.model.journal.entities.JournalEntry
import com.example.jourlyapp.model.journal.enums.Mood
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class QuickEntryModalViewModel(private var journalRepository: JournalRepositoryImpl) :
    ViewModel() {
    fun createNewQuickEntry(date: LocalDateTime, mood: Mood) =
        viewModelScope.launch {
            journalRepository.createEntry(JournalEntry(null, date, mood))
        }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val journalRepository =
                    (this[APPLICATION_KEY] as JourlyApplication).journalRepository
                QuickEntryModalViewModel(
                    journalRepository = journalRepository
                )
            }
        }
    }
}