package com.example.jourlyapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jourlyapp.ui.components.journal.AddEntryFAB
import com.example.jourlyapp.ui.components.nav.BottomNavigationBar
import com.example.jourlyapp.ui.navigation.RootNavigationGraph
import com.example.jourlyapp.viewmodel.MainViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainViewModel,
) {
    // subscribe to route changes, and update isAuthScreen
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    viewModel.updateIsAuthScreen(navBackStackEntry?.destination?.route)

    Scaffold(
        bottomBar = {
            if (!viewModel.isAuthScreen.value) BottomNavigationBar(
                navController
            )
        },
        floatingActionButton = { if (!viewModel.isAuthScreen.value) AddEntryFAB() else Unit },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true
    ) { paddingValues ->
        RootNavigationGraph(
            navController = navController,
            paddingValues = paddingValues,
            startDestination = viewModel.startDestination.value,
        )
    }
}


