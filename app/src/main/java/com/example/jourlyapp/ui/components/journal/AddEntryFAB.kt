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
import com.example.jourlyapp.model.journal.enums.Mood
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
    var myCounters = remember { mutableListOf<Int>() }
    val moodButtonNum = 5
    repeat(moodButtonNum) { i ->
        myCounters.add(0)
    }
    val context = LocalContext.current

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
 * This function's goal is to build and show the modal that is used to add a quick entry of
 * the user's mood.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BuildEntryModal(
    //title: String,
    onMood1Click: () -> Unit,
    onMood2Click: () -> Unit,
    onMood3Click: () -> Unit,
    onMood4Click: () -> Unit,
    onMood5Click: () -> Unit,
    onArrowClick: () -> Unit,
    onDismissRequest: () -> Unit,
) {

    /*
    Please note that there are 2 ways for padding. The commented one brings the modal to the bottom of
    the screen as we designed it during the design phase, while the second one shows it in the middle
    of the screen. The problem of the first solution is that it elicits the possibility to dismiss the
    modal by clicking in any part of the screen outside of it, while the second one allows this.
    */
     AlertDialog(
         onDismissRequest = onDismissRequest,
         properties = DialogProperties(usePlatformDefaultWidth = false),
         modifier = Modifier
             //.padding(start = 28.dp, end = 28.dp, top = 550.dp, bottom = 100.dp)
             .padding(28.dp)
             .fillMaxWidth()
             .wrapContentHeight()
             .wrapContentWidth(),
         buttons = {
             Column(
                 horizontalAlignment = Alignment.CenterHorizontally,
                 modifier = Modifier
                     .fillMaxWidth()
                     .padding(horizontal = 16.dp)
             ) {
                 // This button could be moved out of the row
                 IconButton(onClick = onArrowClick) {
                     Icon(
                         painterResource(id = R.drawable.ic_baseline_keyboard_arrow_up_24),
                         contentDescription = "Button to detailed entry",
                         modifier = Modifier.size(20.dp)
                     )
                 }

                 Text(text = "What is your today's mood?", fontWeight = FontWeight.Bold)

                 Row (
                     Modifier
                         .fillMaxWidth()
                         .padding(vertical = 8.dp),
                     horizontalArrangement = Arrangement.SpaceBetween,
                     verticalAlignment = Alignment.CenterVertically,
                 ){
                     IconButton(onClick = onMood1Click) {
                         Icon(
                             painterResource(id = R.drawable.great_mood_emoji),
                             contentDescription = "Button to add a quick entry of the mood: Great",
                             modifier = Modifier.size(40.dp),
                             tint = Color.Unspecified
                         )
                     }
                     IconButton(onClick = onMood2Click) {
                         Icon(
                             painterResource(id = R.drawable.good_mood_emoji),
                             contentDescription = "Button to add a quick entry of the mood: Good",
                             modifier = Modifier.size(40.dp),
                             tint = Color.Unspecified
                         )
                     }
                     IconButton(onClick = onMood3Click) {
                         Icon(
                             painterResource(id = R.drawable.okay_mood_emoji),
                             contentDescription = "Button to add a quick entry of the mood: Okay",
                             modifier = Modifier.size(40.dp),
                             tint = Color.Unspecified
                         )
                     }
                     IconButton(onClick = onMood4Click) {
                         Icon(
                             painterResource(id = R.drawable.bad_mood_emoji),
                             contentDescription = "Button to add a quick entry of the mood: Bad",
                             modifier = Modifier.size(40.dp),
                             tint = Color.Unspecified
                         )
                     }
                     IconButton(onClick = onMood5Click) {
                         Icon(
                             painterResource(id = R.drawable.awful_mood_emoji),
                             contentDescription = "Button to add a quick entry of the mood: Awful",
                             modifier = Modifier.size(40.dp),
                             tint = Color.Unspecified
                         )
                     }
                 }
             }
         }
     )
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
