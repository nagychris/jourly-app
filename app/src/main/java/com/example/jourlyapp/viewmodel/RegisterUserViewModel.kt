package com.example.jourlyapp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.jourlyapp.JourlyApplication
import com.example.jourlyapp.model.auth.UserRepository

class RegisterUserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    val userName: MutableState<String> = mutableStateOf("")
    val userPasscode: MutableState<String> = mutableStateOf("")

    fun updateUserPasscode(newPasscode: String) {
        userPasscode.value = newPasscode
    }

    fun updateUserName(newName: String) {
        userName.value = newName
    }

    fun isUserValid(): Boolean {
        return userName.value.isNotEmpty()
    }

    fun createUser() {
        userRepository.createUser(userName.value, userPasscode.value)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val userRepository =
                    (this[APPLICATION_KEY] as JourlyApplication).userRepository
                RegisterUserViewModel(
                    userRepository = userRepository
                )
            }
        }
    }
}