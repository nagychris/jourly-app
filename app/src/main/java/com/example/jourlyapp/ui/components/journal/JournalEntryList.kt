package com.example.jourlyapp.ui.components.journal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.jourlyapp.model.journal.entities.JournalEntry
import com.example.jourlyapp.ui.theme.*
import com.example.jourlyapp.viewmodel.journal.JournalViewModel

@Composable
fun JournalEntryList (
    modifier: Modifier = Modifier,
    journalEntries: List<JournalEntry>,
    viewModel: JournalViewModel
) {
    Column(modifier = modifier) {
        Text("Your Timeline", style = MaterialTheme.typography.labelMedium)
        Spacer(modifier = Modifier.height(Margins.vertical))
        LazyColumn{
            items(
                items = journalEntries,
                key = { it.id!! }, //here I am using the casting from nullable to non-nullable!
                itemContent = {
                    JournalEntryListItem(
                        journalEntry = it,
                        viewModel = viewModel
                    )
                }
            )
        }

    }
}


