package com.example.jourlyapp.ui.components.journal

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.jourlyapp.model.journal.enums.JournalQuestion
import com.example.jourlyapp.model.journal.enums.Mood
import com.example.jourlyapp.ui.components.shared.buttons.BaseButton
import com.example.jourlyapp.ui.theme.Margins
import com.example.jourlyapp.ui.util.RandomQuestionGenerator
import com.example.jourlyapp.viewmodel.EntryModalViewModel

@Composable
fun AddDetailedEntryModalContent(
    onClose: () -> Unit,
    viewModel: EntryModalViewModel
) {

    val context = LocalContext.current

    val questions = RandomQuestionGenerator.randomQuestions()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            questions.forEachIndexed { index, question ->
                Column() {
                    Text(
                        text = question,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.padding(vertical = Margins.vertical))
                    TextField(
                        value = viewModel.answers[index],
                        onValueChange = { newText ->
                                viewModel.updateAnswer(newText, index)
                            },
                        textStyle = MaterialTheme.typography.bodySmall,
                        singleLine = false,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(text = "Lorem ipsum dolor sit amet")}
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = {
                        viewModel.updateMood(Mood.None)
                        onClose()
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    Text(text = "Discard")
                }
                BaseButton(onClick = {
                    onClose()
                    questions.forEachIndexed { index, question ->
                        viewModel.initQuestionAnswerPairs(question, index)
                    }
                    addDetailedEntry(viewModel, context)
                }) {
                    Text(text = " Save  ")
                }
            }
        }
    }
}

fun addDetailedEntry(
    viewModel: EntryModalViewModel,
    context: Context
    ) {
    viewModel.createNewDetailedEntry()
    Toast
        .makeText(
            context,
            "Detailed entry added!",
            Toast.LENGTH_SHORT
        )
        .show()
}