package com.example.jourlyapp.ui.components.nav

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jourlyapp.ui.screens.ChallengesScreen
import com.example.jourlyapp.ui.screens.JournalScreen
import com.example.jourlyapp.ui.screens.ProfileScreen
import com.example.jourlyapp.ui.screens.ReportScreen

/**
 * Displays the current screen (Composable) depending on the current state of the [navController].
 * All routes that should be navigated to must be registered within this component, see https://developer.android.com/jetpack/compose/navigation#create-navhost.
 */
@Composable
fun MainScreenNavigation(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(
        navController,
        startDestination = NavigationItem.Journal.route,
        Modifier.padding(innerPadding)
    ) {
        composable(NavigationItem.Challenges.route) {
            ChallengesScreen()
        }
        composable(NavigationItem.Journal.route) {
            JournalScreen()
        }
        composable(NavigationItem.Report.route) {
            ReportScreen()
        }
        composable(NavigationItem.Profile.route) {
            ProfileScreen()
        }
    }
}