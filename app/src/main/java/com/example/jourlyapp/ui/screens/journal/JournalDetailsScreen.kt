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
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch

@Composable
fun JournalDetailsScreen(navController: NavController) {
    val viewModel: JournalDetailsViewModel =
        viewModel(factory = JournalDetailsViewModel.Factory)

    val coroutineScope = rememberCoroutineScope()

    val journalEntry = viewModel.journalEntry
    val editable = viewModel.editable.value
    val mood = viewModel.mood.value

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
                onBackClick = {
                    coroutineScope.launch {
                        navController.navigate(AppRoute.Journal.route)
                    }
                },
                onEditClick = { viewModel.setEditable() },
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.saveChanges()
                    }
                }
            )

            Spacer(modifier = Modifier.height(Margins.verticalLarge))

            if (editable) {
                MoodSelect(
                    onTap = {
                        coroutineScope.launch {
                            viewModel.updateMood(it)
                        }
                    },
                    selectedMood = mood
                )
            } else {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Margins.horizontal),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MoodIcon(mood = mood)
                    Text(mood.toString())
                }
            }

            Spacer(modifier = Modifier.height(Margins.verticalLarge))

            Column(verticalArrangement = Arrangement.spacedBy(Margins.verticalLarge)) {
                viewModel.questionAnswerPairs.forEachIndexed { index, pair ->
                    Column {
                        Text(
                            text = pair.question,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.padding(vertical = Margins.vertical))
                        BaseTextField(
                            enabled = editable,
                            value = viewModel.answers[index],
                            onValueChange = { newAnswer ->
                                coroutineScope.launch {
                                    viewModel.updateAnswer(index, newAnswer)
                                }
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