package com.example.jourlyapp.ui.navigation

sealed class AuthRoute(val route: String) {
    object Register : AuthRoute("register")
    object Unlock : AuthRoute("unlock")
}

val authRoutes = listOf(AuthRoute.Unlock, AuthRoute.Register)