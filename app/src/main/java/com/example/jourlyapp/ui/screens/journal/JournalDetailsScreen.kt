package com.example.jourlyapp.ui.screens.journal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jourlyapp.ui.components.journal.DetailedEntryHeader
import com.example.jourlyapp.ui.components.journal.MoodIcon
import com.example.jourlyapp.ui.components.journal.MoodSelect
import com.example.jourlyapp.ui.components.shared.inputs.BaseTextField
import com.example.jourlyapp.ui.navigation.AppRoute
import com.example.jourlyapp.ui.theme.Margins
import com.example.jourlyapp.viewmodel.journal.JournalDetailsViewModel

@Composable
fun JournalDetailsScreen(navController: NavController) {
    val viewModel: JournalDetailsViewModel =
        viewModel(factory = JournalDetailsViewModel.Factory)

    val journalEntry = viewModel.journalEntry.value
    val editable = viewModel.editable.value

    if (journalEntry == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Sorry, no entry with id ${viewModel.entryId} found")
        }
    } else {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = Margins.horizontal,
                    vertical = Margins.verticalLarge
                )
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DetailedEntryHeader(
                date = journalEntry.date,
                editable = editable,
                onBackClick = { navController.navigate(AppRoute.Journal.route) },
                onEditClick = { viewModel.setEditable() },
                onSaveClick = { viewModel.saveChanges() }
            )

            Spacer(modifier = Modifier.height(Margins.verticalLarge))

            if (editable) {
                MoodSelect(
                    onTap = {
                        viewModel.updateMood(it)
                    },
                    selectedMood = journalEntry.mood
                )
            } else {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Margins.horizontal),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MoodIcon(mood = journalEntry.mood)
                    Text(journalEntry.mood.toString())
                }
            }

            Spacer(modifier = Modifier.height(Margins.verticalLarge))

            Column(verticalArrangement = Arrangement.spacedBy(Margins.verticalLarge)) {
                viewModel.questionAnswerPairs.first()
                    .forEachIndexed { index, pair ->
                        Column {
                            Text(
                                text = pair.question,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.padding(vertical = Margins.vertical))
                            BaseTextField(
                                enabled = editable,
                                value = pair.answer,
                                onValueChange = { newAnswer ->
                                    viewModel.updateAnswer(index, newAnswer)
                                },
                                textStyle = MaterialTheme.typography.bodySmall,
                                singleLine = false,
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text(text = if (editable) "Type your answer..." else "No answer yet...") }
                            )
                        }
                    }
            }
        }
    }


}