package com.example.jourlyapp.e2e.util

import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft

fun registerUser(
    composeTestRule: ComposeTestRule,
    userName: String = "User",
    passcode: String = "Passcode"
) {
    composeTestRule.apply {
        onNode(hasTestTag("userNameField"))
            .performTextInput(userName)
        onNode(hasTestTag("passcodeField"))
            .performTextInput(passcode)

        onNode(hasText("Save")).performClick()
    }
}

fun unlockApp(
    composeTestRule: ComposeTestRule,
    passcode: String = "Passcode"
) {
    composeTestRule.apply {
        onNode(hasTestTag("passcodeField"))
            .performTextInput(passcode)

        onNode(hasText("Unlock")).performClick()
    }
}

fun deleteUserDetails(
    composeTestRule: ComposeTestRule,
) {
    composeTestRule.apply {
        onNode(hasText("Profile")).performClick()

        onNode(hasText("Delete User Details")).performClick()

        onNode(hasTestTag("userNameField")).assertIsDisplayed()
        onNode(hasTestTag("passcodeField")).assertIsDisplayed()
    }
}

fun deleteJournalEntry(
    composeTestRule: ComposeTestRule,
    entryMatcher: SemanticsMatcher
) {
    composeTestRule.apply {
        onNode(hasTestTag("journalEntry").and(entryMatcher)).performTouchInput {
            swipeLeft()
        }
        onNode(hasText("Delete")).performClick()
    }
}