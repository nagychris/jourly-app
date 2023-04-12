package com.example.jourlyapp.ui.screens

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jourlyapp.ui.components.journal.AddEntryFAB
import com.example.jourlyapp.ui.components.journal.AddEntryModalContent
import com.example.jourlyapp.ui.components.nav.BottomNavigationBar
import com.example.jourlyapp.ui.navigation.RootNavigationGraph
import com.example.jourlyapp.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainViewModel,
) {
    // subscribe to route changes, and update isAuthScreen
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    viewModel.updateIsAuthScreen(navBackStackEntry?.destination?.route)

    val coroutineScope = rememberCoroutineScope()
    val addEntryModalSheetState =
        rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded }
        )

    BackHandler(addEntryModalSheetState.isVisible) {
        coroutineScope.launch { addEntryModalSheetState.hide() }
    }

    ModalBottomSheetLayout(
        sheetState = addEntryModalSheetState,
        sheetContent = { AddEntryModalContent() },
    ) {
        Scaffold(
            bottomBar = {
                if (!viewModel.isAuthScreen.value) BottomNavigationBar(
                    navController
                )
            },
            floatingActionButton = {
                if (!viewModel.isAuthScreen.value) AddEntryFAB(
                    onClick = {
                        coroutineScope.launch {
                            if (addEntryModalSheetState.isVisible) {
                                addEntryModalSheetState.hide()
                            } else {
                                addEntryModalSheetState.show()
                            }
                        }
                    }
                ) else Unit
            },
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
}


