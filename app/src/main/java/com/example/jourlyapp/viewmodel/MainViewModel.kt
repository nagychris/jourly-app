package com.example.jourlyapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.jourlyapp.JourlyApplication
import com.example.jourlyapp.model.auth.User
import com.example.jourlyapp.model.auth.UserRepository
import com.example.jourlyapp.ui.navigation.AppRoute
import com.example.jourlyapp.ui.navigation.AuthRoute
import com.example.jourlyapp.ui.navigation.authRoutes

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {
    val user = mutableStateOf(User())
    val startDestination = mutableStateOf(AuthRoute.Register.route)
    val isAuthScreen = mutableStateOf(true)

    init {
        fetchUser()
        startDestination.value = getStartDestination()
    }

    private fun fetchUser() {
        user.value = userRepository.getUser() ?: User()
    }

    private fun getStartDestination(): String {
        return if (!user.value.isValid()) {
            // First visit, no user stored
            isAuthScreen.value = true
            AuthRoute.Register.route
        } else {
            if (user.value.passcode.isNotEmpty()) {
                // Passcode set => locked
                isAuthScreen.value = true
                AuthRoute.Unlock.route
            } else {
                // No passcode set => no lock
                AppRoute.Journal.route
            }
        }

    }

    fun updateIsAuthScreen(currentRoute: String?) {
        if (currentRoute == null) return

        isAuthScreen.value = authRoutes.any { it.route == currentRoute }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val userRepository =
                    (this[APPLICATION_KEY] as JourlyApplication).userRepository
                MainViewModel(
                    userRepository = userRepository
                )
            }
        }
    }
}