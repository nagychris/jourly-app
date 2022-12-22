package com.example.jourlyapp.ui.components.journal

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
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
fun AddEntryFAB() {
    /**
     * If true, a Close-Icon is shown instead of an Add-Icon.
     */
    var isToggled by remember {
        mutableStateOf(false)
    }

    FloatingActionButton(
        shape = CircleShape,
        onClick = {
            isToggled = !isToggled
            // TODO: show add entry modal
        },
        contentColor = Color.White,
        containerColor = MaterialTheme.colorScheme.secondary
    ) {
        AnimatedContent(targetState = isToggled) { showClose ->
            if (showClose) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Cancel"
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add New Entry"
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