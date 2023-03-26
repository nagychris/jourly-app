package com.example.jourlyapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.jourlyapp.JourlyApplication
import com.example.jourlyapp.model.journal.JournalRepository

class QuickEntryModalViewModel(journalRepository: JournalRepository): ViewModel() {
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