package com.example.jourlyapp.ui.components.journal

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.jourlyapp.model.journal.entities.QuestionAnswerPair
import com.example.jourlyapp.model.journal.enums.JournalQuestion
import com.example.jourlyapp.model.journal.enums.Mood
import com.example.jourlyapp.ui.components.shared.buttons.BaseButton
import com.example.jourlyapp.ui.theme.Margins
import com.example.jourlyapp.viewmodel.EntryModalViewModel

@Composable
fun AddDetailedEntryModalContent(
    onClose: () -> Unit,
    viewModel: EntryModalViewModel
) {

    val context = LocalContext.current

    var boxStrings = remember { mutableStateListOf<String>() }
    repeat(3) { i ->
        boxStrings.add("Lorem ipsum dolor sit amet")
    }

    // Decide the questions randomly and set the mock strings
    var randomQuestionsGenerator = (0.. 4).toList()
    randomQuestionsGenerator = randomQuestionsGenerator.slice(0..2)

    var questionAnswerPairs = ArrayList<QuestionAnswerPair>()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            randomQuestionsGenerator.forEach { num ->
                Column() {
                    Text(
                        text = JournalQuestion.values()[randomQuestionsGenerator[num]].toString(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.padding(vertical = Margins.vertical))
                    TextField(
                        value = boxStrings[num],
                        onValueChange = { boxStrings[num] = it},
                        textStyle = MaterialTheme.typography.bodySmall,
                        singleLine = false,
                        modifier = Modifier.fillMaxWidth()
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
                    repeat(3) { i ->
                        questionAnswerPairs.add(
                            QuestionAnswerPair(
                                id = null,
                                journalEntryId = viewModel.journalEntry.value.id?:0,
                                question = JournalQuestion.values()[randomQuestionsGenerator[i]].toString(),
                                answer = if(boxStrings[i] != "Lorem ipsum dolor sit amet") boxStrings[i] else null
                            )
                        )
                    }
                    onClose()
                    addDetailedEntry(viewModel, context, questionAnswerPairs)
                }) {
                    Text(text = " Save  ")
                }
            }
        }
    }
}

fun addDetailedEntry(
    viewModel: EntryModalViewModel,
    context: Context,
    questionAnswerPairs: ArrayList<QuestionAnswerPair>
    ) {
    viewModel.createNewEntry(journalEntry = viewModel.journalEntry.value)
    questionAnswerPairs.forEach { qa ->
        viewModel.createNewQuestionAnswerPair(qa)
    }
    Toast
        .makeText(
            context,
            "Detailed entry added!",
            Toast.LENGTH_SHORT
        )
        .show()
}