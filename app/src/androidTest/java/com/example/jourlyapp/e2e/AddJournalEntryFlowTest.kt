package com.example.jourlyapp.e2e

import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.doubleClick
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTouchInput
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.jourlyapp.e2e.util.deleteJournalEntry
import com.example.jourlyapp.e2e.util.deleteUserDetails
import com.example.jourlyapp.e2e.util.registerUser
import com.example.jourlyapp.ui.screens.MainScreen
import com.example.jourlyapp.ui.theme.JourlyTheme
import com.example.jourlyapp.ui.util.DateTimeParser
import com.example.jourlyapp.viewmodel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime

internal class AddJournalEntryFlowTest {

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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun userCanAddQuickJournalEntry() = runTest {
        composeRule.apply {
            registerUser(this)

            onNode(hasText("Welcome back to Jourly, User")).assertIsDisplayed()

            onNode(hasContentDescription("Add Entry"))
                .performClick()

            onNode(hasText("What is your mood today?")).assertIsDisplayed()

            onNode(hasTestTag("moodIconButton")).performTouchInput {
                doubleClick()
            }

            awaitIdle()

            onNode(hasText("Quick Entry added"))
                .assertIsDisplayed()

            awaitIdle()

            onNodeWithTag("journalEntryList").performScrollToIndex(0)

            onAllNodesWithTag("journalEntry").assertAny(
                hasText(
                    "Great"
                ).and(
                    hasText(
                        DateTimeParser.toLongDateString(
                            LocalDateTime.now()
                        )
                    )
                ).and(hasContentDescription("Great Mood"))
            )
        }
    }

    @After
    fun cleanup() {
        composeRule.apply {
            deleteJournalEntry(
                this, hasText(
                    "Great"
                ).and(
                    hasText(
                        DateTimeParser.toLongDateString(
                            LocalDateTime.now()
                        )
                    )
                )
            )

            deleteUserDetails(this)
        }
    }
}