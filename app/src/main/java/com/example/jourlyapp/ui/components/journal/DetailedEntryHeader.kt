package com.example.jourlyapp.ui.components.journal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.jourlyapp.ui.util.DateTimeParser
import java.time.LocalDateTime

@Composable
fun DetailedEntryHeader(
    date: LocalDateTime,
    editable: Boolean,
    onBackClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onSaveClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier.clickable { onBackClick() }
        )
        Text(
            DateTimeParser.toLongDateString(date),
            style = MaterialTheme.typography.titleMedium
        )
        if (editable) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Save",
                modifier = Modifier.clickable { onSaveClick() }
            )
        } else {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "Edit",
                modifier = Modifier.clickable { onEditClick() }
            )
        }
    }
}