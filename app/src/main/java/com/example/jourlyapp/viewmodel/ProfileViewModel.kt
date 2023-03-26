package com.example.jourlyapp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.jourlyapp.JourlyApplication
import com.example.jourlyapp.model.auth.UserRepository

class ProfileViewModel(private val userRepository: UserRepository) :
    ViewModel() {
    val userName: MutableState<String> = mutableStateOf("")

    init {
        userName.value = userRepository.getUser()?.name ?: ""
    }

    fun removeUserDetails() {
        userRepository.removeUser()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val userRepository =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as JourlyApplication).userRepository
                ProfileViewModel(
                    userRepository = userRepository
                )
            }
        }
    }
}