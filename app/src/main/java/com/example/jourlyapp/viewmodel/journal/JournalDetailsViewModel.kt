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

    val journalEntry = journalRepository.getJournalEntryById(entryId)

    val mood = mutableStateOf(journalEntry?.mood ?: Mood.None)

    val questionAnswerPairs =
        journalRepository.getQuestionAnswerPairsByEntryId(entryId)

    var answers = mutableStateListOf<String>()

    init {
        questionAnswerPairs.forEach {
            answers.add(
                it.answer
            )
        }
    }

    fun setEditable() {
        editable.value = true
    }

    fun saveChanges() {
        editable.value = false
        journalRepository.updateJournalEntryMood(mood.value, entryId)
        questionAnswerPairs.forEachIndexed { index, questionAnswerPair ->
            val id = questionAnswerPair.id
            id?.let {
                journalRepository.updateQuestionAnswerPair(
                    answers[index],
                    it
                )
            }
        }
    }

    fun updateMood(newMood: Mood) {
        mood.value = newMood
    }

    fun updateAnswer(index: Int, answer: String) {
        answers[index] = answer
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