package com.example.jourlyapp.ui.components.journal

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.jourlyapp.R
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

    // If the value of isToggled is "true", it opens the dialog by calling the BuildEntryModal function
    if (isToggled) {
        BuildEntryModal(
            onMood1Click = { /*TODO: add the quick entry to the database*/ },
            onMood2Click = { /*TODO: add the quick entry to the database*/ },
            onMood3Click = { /*TODO: add the quick entry to the database*/ },
            onMood4Click = { /*TODO: add the quick entry to the database*/ },
            onMood5Click = { /*TODO: add the quick entry to the database*/ },
            onArrowClick = { /*TODO: remand to the modal for detailed daily entry*/ },
            onDismissRequest = {isToggled = !isToggled})
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
    onDismissRequest: () -> Unit
) {
     AlertDialog(
         onDismissRequest = onDismissRequest,
         properties = DialogProperties(usePlatformDefaultWidth = false),
         modifier = Modifier
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


@Preview
@Composable
fun BuildEntryModalPreview() {
    JourlyTheme {
        BuildEntryModal( onDismissRequest = {}, onArrowClick = {},
        onMood1Click = {}, onMood2Click = {}, onMood3Click = {}, onMood4Click = {},
        onMood5Click = {})
    }
}
