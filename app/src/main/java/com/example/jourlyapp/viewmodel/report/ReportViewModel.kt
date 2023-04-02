package com.example.jourlyapp.viewmodel.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.jourlyapp.JourlyApplication
import com.example.jourlyapp.model.journal.JournalRepository
import com.example.jourlyapp.model.journal.entities.JournalEntry
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class ReportViewModel(val journalRepository: JournalRepository) : ViewModel() {
    private val today = LocalDateTime.now()

    // end date is always today
    private val endDate = today.truncatedTo(ChronoUnit.MINUTES)

    // startDate: today - 1 week OR today - 1 month OR ...
    private val startDate = today.minusWeeks(1).truncatedTo(ChronoUnit.MINUTES)

    val journalEntries: LiveData<List<JournalEntry>> =
        journalRepository.getJournalEntriesBetweenDates(startDate, endDate)
            .asLiveData()

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val journalRepository =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as JourlyApplication).journalRepository
                ReportViewModel(
                    journalRepository = journalRepository
                )
            }
        }
    }
}