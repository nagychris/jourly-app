package com.example.jourlyapp.ui.components.journal

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.jourlyapp.model.journal.entities.JournalEntry
import com.example.jourlyapp.viewmodel.journal.JournalViewModel
import com.example.jourlyapp.R
import com.example.jourlyapp.ui.components.shared.buttons.BaseButton
import com.example.jourlyapp.ui.theme.Blue80
import com.example.jourlyapp.ui.util.DateTimeParser


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteEntryDialog (
    viewModel: JournalViewModel,
    currentEntry: JournalEntry,
    onDismiss: () -> Unit
    ){

    var openDialog by remember {
        mutableStateOf(true)
    }

    if(openDialog) {
        AlertDialog(
            onDismissRequest = {
                openDialog = false
                onDismiss()
            }
        ) {
            Surface(modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
                shape = MaterialTheme.shapes.large
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.deleteEntryQuestion),
                        style = MaterialTheme.typography.titleSmall,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = DateTimeParser.toLongDateString(currentEntry.date),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = currentEntry.mood.toString(),
                        textAlign = TextAlign.Center,
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ){
                        BaseButton(
                            onClick = {
                                openDialog = false
                                onDismiss()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Blue80
                            )
                        ) {
                            Text(text = "Cancel")
                        }
                        BaseButton(
                            onClick = {
                                viewModel.deleteEntry(currentEntry.id!!)
                                openDialog = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Text("Discard")
                        }
                    }
                }
            }
        }
    }

}