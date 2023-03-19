package com.example.jourlyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.jourlyapp.model.lock.EncryptedStorageService
import com.example.jourlyapp.ui.components.journal.AddEntryFAB
import com.example.jourlyapp.ui.components.nav.BottomNavigationBar
import com.example.jourlyapp.ui.components.nav.MainScreenNavigation
import com.example.jourlyapp.ui.theme.JourlyTheme

/**
 * Starting point of the app, which is also providing the [navController] used for navigation.
 */
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavController
    private lateinit var encryptedStorageService: EncryptedStorageService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        encryptedStorageService = EncryptedStorageService(applicationContext)

        setContent {
            navController = rememberNavController()
            JourlyTheme {
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController) },
                    floatingActionButton = { AddEntryFAB() },
                    floatingActionButtonPosition = FabPosition.Center,
                    isFloatingActionButtonDocked = true
                ) { innerPadding ->
                    MainScreenNavigation(navController as NavHostController, innerPadding)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    JourlyTheme {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { BottomNavigationBar(navController) },
            floatingActionButton = { AddEntryFAB() },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,
        ) { innerPadding ->
            MainScreenNavigation(navController, innerPadding)
        }
    }
}
