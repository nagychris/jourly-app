package com.example.jourlyapp.ui.components.journal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.jourlyapp.model.journal.entities.JournalEntry
import com.example.jourlyapp.ui.util.DateTimeParser

@Composable
fun JournalEntryListItem(
    modifier: Modifier = Modifier,
    journalEntry: JournalEntry,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .padding(vertical = 6.dp)
            .clickable(onClick = onClick)
            .testTag("journalEntry"),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row (
            modifier = Modifier.padding(16.dp)
        ) {
            MoodIcon(
                modifier = modifier.align(Alignment.CenterVertically),
                mood = journalEntry.mood,
            )
            Spacer(modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = journalEntry.mood.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = DateTimeParser.toLongDateString(journalEntry.date),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}