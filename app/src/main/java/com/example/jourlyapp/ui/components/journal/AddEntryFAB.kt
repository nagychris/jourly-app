package com.example.jourlyapp.ui.components.journal

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material.AlertDialog
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.jourlyapp.R
import com.example.jourlyapp.model.journal.dao.JournalDao
import com.example.jourlyapp.model.journal.db.AppDatabase
import com.example.jourlyapp.model.journal.entities.JournalEntry
import com.example.jourlyapp.model.journal.enums.Mood
import com.example.jourlyapp.ui.theme.JourlyTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddEntryFAB() {

    // If true, a Close-Icon is shown instead of an Add-Icon.
    var isToggled by remember {
        mutableStateOf(false)
    }

    FloatingActionButton(
        shape = CircleShape,
        onClick = {
            isToggled = !isToggled
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

    // If the value of isToggled is "true", it opens the dialog by calling the BuildEntryModal function
    if (isToggled) {
        onToggle(onClose = {isToggled = false})
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun onToggle(onClose: () -> Unit) {
    buildEntryModal(onClose)
}

@Preview
@Composable
fun AddEntryFABPreview() {
    JourlyTheme {
        AddEntryFAB()
    }
}
