package com.example.jourlyapp.e2e

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.jourlyapp.e2e.util.assertJournalScreenIsDisplayed
import com.example.jourlyapp.e2e.util.deleteUserDetails
import com.example.jourlyapp.e2e.util.registerUser
import com.example.jourlyapp.e2e.util.unlockApp
import com.example.jourlyapp.ui.screens.MainScreen
import com.example.jourlyapp.ui.theme.JourlyTheme
import com.example.jourlyapp.viewmodel.MainViewModel
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

/**
 * NOTE: the test methods need to be executed in the given order, and the last
 * test has to be run after the first to delete the user details.
 * This is due to the fact that we want to test the unlock feature, which is
 * only possible when re-opening the app, so we need to have two separate tests
 * (we need [setup] to be called twice).
 * To keep the order in the execution, the test names are prefixed with letters.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
internal class RegisterFlowTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Before
    fun setup() {
        composeRule.setContent {
            JourlyTheme {
                MainScreen(
                    navController = rememberNavController(),
                    viewModel = viewModel(factory = MainViewModel.Factory)
                )
            }
        }
    }

    @Test
    fun a_userIsRedirectedToJournalAfterRegistering() {
        composeRule.apply {
            registerUser(this)

            assertJournalScreenIsDisplayed(this)
        }
    }

    @Test
    fun b_userCanUnlockAppWithRegisteredCredentials() {
        composeRule.apply {
            onNode(hasText("Unlock")).assertIsNotEnabled()

            onNode(hasTestTag("passcodeField"))
                .performTextInput("Wrong Passcode")

            onNode(hasText("Unlock")).performClick()

            onNode(hasText("Sorry, wrong passcode!")).assertIsDisplayed()

            onNode(hasTestTag("passcodeField")).performTextClearance()

            unlockApp(this)

            assertJournalScreenIsDisplayed(this)

            deleteUserDetails(this)
        }
    }

    @Test
    fun c_userIsRedirectedToJournalAfterRegisteringWithoutPasscode() {
        composeRule.apply {
            registerUser(this, passcode = null)

            assertJournalScreenIsDisplayed(this)
        }
    }

    @Test
    fun d_userCanAccessAppDirectlyWithoutPasscodeAndDeleteUserDetails() {
        composeRule.apply {
            assertJournalScreenIsDisplayed(this)

            deleteUserDetails(this)

            onNode(hasTestTag("userNameField")).assertIsDisplayed()
            onNode(hasTestTag("passcodeField")).assertIsDisplayed()
        }
    }
}