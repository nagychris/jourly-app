package com.example.jourlyapp.viewmodel.journal

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.jourlyapp.JourlyApplication
import com.example.jourlyapp.model.auth.UserRepository
import com.example.jourlyapp.model.journal.JournalRepository
import com.example.jourlyapp.model.journal.entities.JournalEntry

class JournalViewModel(
    private val userRepository: UserRepository,
    private val journalRepository: JournalRepository
) :
    ViewModel() {
    val userName: MutableState<String> = mutableStateOf("")

    val journalEntries: LiveData<List<JournalEntry>> =
        journalRepository.journalEntries.asLiveData()

    init {
        userName.value = userRepository.getUser()?.name ?: ""
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val userRepository =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as JourlyApplication).userRepository
                val journalRepository =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as JourlyApplication).journalRepository
                JournalViewModel(
                    userRepository = userRepository,
                    journalRepository = journalRepository
                )
            }
        }
    }
}