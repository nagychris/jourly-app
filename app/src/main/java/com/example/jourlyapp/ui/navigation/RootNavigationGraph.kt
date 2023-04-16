package com.example.jourlyapp.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jourlyapp.ui.screens.ChallengesScreen
import com.example.jourlyapp.ui.screens.JournalScreen
import com.example.jourlyapp.ui.screens.ProfileScreen
import com.example.jourlyapp.ui.screens.RegisterScreen
import com.example.jourlyapp.ui.screens.ReportScreen
import com.example.jourlyapp.ui.screens.UnlockScreen

@Composable
fun RootNavigationGraph(
    navController: NavHostController,
    startDestination: String,
    paddingValues: PaddingValues,
    journalEntryListState: LazyListState
) {
    NavHost(
        navController,
        startDestination = startDestination
    ) {
        composable(route = AuthRoute.Unlock.route) {
            UnlockScreen(
                onUnlockClick = {
                    navController.popBackStack()
                    navController.navigate(AppRoute.Journal.route)
                })
        }
        composable(route = AuthRoute.Register.route) {
            RegisterScreen(
                onRegisterClick = {
                    navController.popBackStack()
                    navController.navigate(AppRoute.Journal.route)
                },
            )
        }
        composable(route = AppRoute.Journal.route) {
            JournalScreen(listState = journalEntryListState)
        }
        composable(route = AppRoute.Profile.route) {
            ProfileScreen(
                onChangeUserClick = {
                    navController.popBackStack()
                    navController.navigate(AuthRoute.Register.route)
                })
        }
        composable(route = AppRoute.Challenges.route) {
            ChallengesScreen()
        }
        composable(route = AppRoute.Report.route) {
            ReportScreen(
                modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())
            )
        }

    }
}