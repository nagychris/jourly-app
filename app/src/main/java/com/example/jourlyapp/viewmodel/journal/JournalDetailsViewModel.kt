package com.example.jourlyapp.viewmodel.journal

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.jourlyapp.JourlyApplication
import com.example.jourlyapp.model.journal.JournalRepository
import com.example.jourlyapp.model.journal.enums.Mood

class JournalDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val journalRepository: JournalRepository
) : ViewModel() {
    val entryId: Int = checkNotNull(savedStateHandle["entryId"])
    val editable: MutableState<Boolean> =
        mutableStateOf(checkNotNull(savedStateHandle["editable"]))

    val journalEntry =
        mutableStateOf(journalRepository.getJournalEntryById(entryId))

    val questionAnswerPairs = mutableStateListOf(
        journalRepository.getQuestionAnswerPairsByEntryId(entryId)
    )

    fun setEditable() {
        editable.value = true
    }

    fun saveChanges() {
        editable.value = false
        // TODO: save entry / QA-pairs to DB
    }

    fun updateMood(mood: Mood) {
        journalEntry.value?.mood = mood
    }

    fun updateAnswer(index: Int, answer: String) {
        questionAnswerPairs.first()[index].answer = answer
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                // Get the Application object from extras
                val application = checkNotNull(this[APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras
                val savedStateHandle = this.createSavedStateHandle()

                JournalDetailsViewModel(
                    savedStateHandle = savedStateHandle,
                    journalRepository = (application as JourlyApplication).journalRepository
                )
            }
        }
    }
}