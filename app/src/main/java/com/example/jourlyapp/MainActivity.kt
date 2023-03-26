package com.example.jourlyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.jourlyapp.ui.screens.MainScreen
import com.example.jourlyapp.ui.theme.JourlyTheme
import com.example.jourlyapp.viewmodel.MainViewModel

/**
 * Starting point of the app, which is also providing the [navController] used for navigating through the app.
 */
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            navController = rememberNavController()
            JourlyTheme {
                MainScreen(
                    navController = navController,
                    viewModel = viewModel(factory = MainViewModel.Factory),
                )
            }
        }
    }
}