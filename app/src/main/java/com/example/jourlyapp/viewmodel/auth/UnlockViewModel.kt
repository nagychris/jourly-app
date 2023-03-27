package com.example.jourlyapp.viewmodel.auth

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.jourlyapp.JourlyApplication
import com.example.jourlyapp.model.auth.UserRepository

class UnlockViewModel(private val userRepository: UserRepository) :
    ViewModel() {
    val passcode: MutableState<String> = mutableStateOf("")
    val errorMessage: MutableState<String> = mutableStateOf("")

    fun updatePasscode(newPasscode: String) {
        passcode.value = newPasscode
    }

    fun isPasscodeValid(): Boolean {
        val storedPasscode = userRepository.getUser()?.passcode ?: return false

        return passcode.value.equals(storedPasscode)
    }

    fun setError(message: String) {
        errorMessage.value = message
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val userRepository =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as JourlyApplication).userRepository
                UnlockViewModel(
                    userRepository = userRepository
                )
            }
        }
    }
}
