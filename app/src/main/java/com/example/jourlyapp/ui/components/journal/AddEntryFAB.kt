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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AddEntryFAB() {

    // If true, a Close-Icon is shown instead of an Add-Icon.
    var isToggled by remember {
        mutableStateOf(false)
    }

    // Init a list of int that remember their own value. Used to check if the user pressed an emoji button 2 times
    var myCounters = remember { mutableListOf<Int>() }
    val moodButtonNum = 5
    repeat(moodButtonNum) { i ->
        myCounters.add(0)
    }
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context, GlobalScope)
    val journalDao = database.journalDao()

    FloatingActionButton(
        shape = CircleShape,
        onClick = {
            isToggled = !isToggled
            /*TODO*/
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
        BuildEntryModal(
            onMood1Click = {
                myCounters[0]++
                showMessage(myCounters[0], Mood.Great, context)
                if (myCounters[0] > 1) {
                    myCounters[0] = 0
                }
                /*TODO: add the quick entry to the database*/
                },
            onMood2Click = { myCounters[1]++
                showMessage(myCounters[1], Mood.Good, context)
                if (myCounters[1] > 1) {
                    myCounters[1] = 0
                }
                /*TODO: add the quick entry to the database*/ },
            onMood3Click = { myCounters[2]++
                showMessage(myCounters[2], Mood.Okay, context)
                if (myCounters[2] > 1) {
                    myCounters[2] = 0
                }
                /*TODO: add the quick entry to the database*/ },
            onMood4Click = { myCounters[3]++
                showMessage(myCounters[3], Mood.Bad, context)
                if (myCounters[3] > 1) {
                    myCounters[3] = 0
                }
                /*TODO: add the quick entry to the database*/ },
            onMood5Click = { myCounters[4]++
                showMessage(myCounters[4], Mood.Awful, context)
                if (myCounters[4] > 1) {
                    myCounters[4] = 0
                }
                /*TODO: add the quick entry to the database*/ },
            onArrowClick = { /*TODO: remand to the modal for detailed daily entry*/ },
            onDismissRequest = {isToggled = !isToggled
                repeat(moodButtonNum) { i ->
                    myCounters[i] = 0
                }
            })
    }
}

@Preview
@Composable
fun AddEntryFABPreview() {
    JourlyTheme {
        AddEntryFAB()
    }
}


/**
 * This function is used to show the Toast messages that inform the user on how to use the quick mood entry modal.
 */
fun showMessage (counter: Int, mood: Mood, context: Context) {
    if (counter == 1) {
        Toast.makeText(context, "Tap on the same button again to add a quick entry with mood value: ${mood.toString()}", Toast.LENGTH_SHORT).show()
    } else if (counter == 2){
        Toast.makeText(context, "Quick entry added!", Toast.LENGTH_SHORT).show()
    }
}

@Preview
@Composable
fun BuildEntryModalPreview() {
    JourlyTheme {
        BuildEntryModal( onDismissRequest = {}, onArrowClick = {},
        onMood1Click = {}, onMood2Click = {}, onMood3Click = {}, onMood4Click = {},
        onMood5Click = {})
    }
}
