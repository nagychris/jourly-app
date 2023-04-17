package com.example.jourlyapp.ui.components.journal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.jourlyapp.model.journal.enums.Mood
import com.example.jourlyapp.ui.components.shared.buttons.BaseButton
import com.example.jourlyapp.ui.components.shared.inputs.BaseTextField
import com.example.jourlyapp.ui.theme.Margins
import com.example.jourlyapp.viewmodel.EntryModalViewModel

@Composable
fun AddDetailedEntryModalContent(
    onDiscard: () -> Unit,
    onSave: () -> Unit,
    viewModel: EntryModalViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(Margins.verticalLarge)
    ) {
        viewModel.questions.forEachIndexed { index, question ->
            Column {
                Text(
                    text = question,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.padding(vertical = Margins.vertical))
                BaseTextField(
                    value = viewModel.answers[index],
                    onValueChange = { newAnswer ->
                        viewModel.updateAnswer(index, newAnswer)
                    },
                    textStyle = MaterialTheme.typography.bodySmall,
                    singleLine = false,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = "Type your answer...") }
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            OutlinedButton(
                onClick = {
                    viewModel.reset()
                    onDiscard()
                },
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onBackground
                )
            ) {
                Text(text = "Discard")
            }
            BaseButton(
                onClick = {
                    viewModel.createNewDetailedEntry()
                    onSave()
                },
                enabled = viewModel.mood.value !== Mood.None,
            ) {
                Text(text = "Save")
            }
        }
    }
}