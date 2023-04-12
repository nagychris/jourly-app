package com.example.jourlyapp.ui.components.journal

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.jourlyapp.ui.theme.JourlyTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AddEntryFAB(
    showCloseIcon: Boolean = false,
    onClick: () -> Unit = {}
) {
    FloatingActionButton(
        shape = CircleShape,
        onClick = {
            onClick()
        },
        contentColor = Color.White,
        containerColor = MaterialTheme.colorScheme.secondary
    ) {
        AnimatedContent(targetState = showCloseIcon) { showCloseIcon ->
            if (showCloseIcon) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Cancel"
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Entry"
                )
            }
        }
    }
}

@Preview
@Composable
fun AddEntryFABPreview() {
    JourlyTheme {
        AddEntryFAB()
    }
}
