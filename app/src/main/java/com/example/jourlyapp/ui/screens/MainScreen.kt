package com.example.jourlyapp.ui.screens

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jourlyapp.ui.components.journal.AddEntryFAB
import com.example.jourlyapp.ui.components.journal.AddEntryModalContent
import com.example.jourlyapp.ui.components.nav.BottomNavigationBar
import com.example.jourlyapp.ui.navigation.AppRoute
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
    var isSheetFullScreen by remember { mutableStateOf(false) }

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )

    val roundedCornerRadius = if (isSheetFullScreen) 0.dp else 12.dp

    BackHandler(modalSheetState.isVisible) {
        coroutineScope.launch { modalSheetState.hide() }
    }

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetContent = {
            AddEntryModalContent(
                modifier = if (isSheetFullScreen)
                    Modifier
                        .fillMaxSize()
                else
                    Modifier.fillMaxWidth(),
                coroutineScope = coroutineScope,
                onMoodIconClick = suspend {
                    modalSheetState.hide()
                },
                onExpandClick = {
                    coroutineScope.launch {
                        isSheetFullScreen = true
                    }
                },
                onShrinkClick = {
                    coroutineScope.launch {
                        isSheetFullScreen = false
                    }
                },
                isFullScreen = isSheetFullScreen,
                onDetailedClose = {
                    coroutineScope.launch {
                        modalSheetState.hide()
                        isSheetFullScreen = false
                    }
                }
            )
        },
        sheetShape = RoundedCornerShape(
            topStart = roundedCornerRadius,
            topEnd = roundedCornerRadius
        )
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
                            // navigate to journal page and show modal
                            navController.navigate(AppRoute.Journal.route)
                            modalSheetState.show()
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


