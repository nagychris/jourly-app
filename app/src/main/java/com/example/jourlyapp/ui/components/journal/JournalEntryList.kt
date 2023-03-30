package com.example.jourlyapp.ui.components.journal

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jourlyapp.model.journal.entities.JournalEntry


@Composable
fun JournalEntryList(
    modifier: Modifier = Modifier,
    journalEntries: List<JournalEntry>
) {
    Text("Your Timeline", style = MaterialTheme.typography.bodyLarge)
    Spacer(modifier = Modifier.height(8.dp))
    LazyColumn(modifier = modifier) {
        items(items = journalEntries, itemContent = {
            JournalEntryListItem(journalEntry = it, onClick = {
                // TODO: navigate to detail view of journal entry with id here
            })
        })
    }
}