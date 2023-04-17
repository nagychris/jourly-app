package com.example.jourlyapp.ui.screens

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performTextInput
import com.example.jourlyapp.ui.theme.JourlyTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RegisterScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        composeTestRule.setContent {
            JourlyTheme {
                RegisterScreen(
                    onRegisterClick = {}
                )
            }
        }
    }

    @Test
    fun userCanNotRegisterWithoutName() {
        composeTestRule.onNode(hasText("Save")).assertIsNotEnabled()
    }

    @Test
    fun userCanRegisterWithName() {
        composeTestRule.onNode(hasTestTag("userNameField"))
            .performTextInput("User")

        composeTestRule.onNode(hasText("Save")).assertIsEnabled()
    }

    @Test
    fun userCanRegisterWithNameAndPasscode() {
        composeTestRule.onNode(hasTestTag("userNameField"))
            .performTextInput("User")

        composeTestRule.onNode(hasTestTag("passcodeField"))
            .performTextInput("Passcode")

        composeTestRule.onNode(hasText("Save")).assertIsEnabled()
    }
}