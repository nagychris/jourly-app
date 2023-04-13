package com.example.jourlyapp.ui.components.journal

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.jourlyapp.ui.theme.JourlyTheme

@Composable
fun AddEntryFAB(
    onClick: () -> Unit = {}
) {
    FloatingActionButton(
        shape = CircleShape,
        onClick = onClick,
        contentColor = Color.White,
        containerColor = MaterialTheme.colorScheme.secondary
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add Entry"
        )
    }
}

@Preview
@Composable
fun AddEntryFABPreview() {
    JourlyTheme {
        AddEntryFAB()
    }
}
