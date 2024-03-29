package com.example.jourlyapp.viewmodel.report

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.jourlyapp.JourlyApplication
import com.example.jourlyapp.model.journal.JournalRepositoryImpl
import com.example.jourlyapp.model.journal.entities.JournalEntry
import com.example.jourlyapp.model.report.DateRange
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class ReportViewModel(val journalRepository: JournalRepositoryImpl) :
    ViewModel() {
    // end date is always today, because we consider the past x days
    private val endDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)

    // default is past week
    private val startDate = mutableStateOf(
        DateRange.PAST_WEEK.startDate
    )

    val journalEntries: State<Flow<List<JournalEntry>>> =
        derivedStateOf {
            journalRepository.getJournalEntriesBetweenDates(
                startDate.value,
                endDate
            )
        }

    val isWeekRange = derivedStateOf {
        startDate.value == DateRange.PAST_WEEK.startDate
    }

    fun updateStartDate(dateRange: DateRange) {
        startDate.value = dateRange.startDate
        Log.d(TAG, "New startDate ${startDate.value}")
    }

    companion object {
        const val TAG = "ReportViewModel"

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