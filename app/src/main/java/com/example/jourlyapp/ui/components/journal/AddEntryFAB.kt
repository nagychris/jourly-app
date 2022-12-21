package com.example.jourlyapp.ui.components.journal

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun AddEntryFAB() {
    FloatingActionButton(
        shape = CircleShape,
        onClick = {
            // TODO: show add entry modal
        },
        contentColor = Color.White,
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add New Entry")
    }
}