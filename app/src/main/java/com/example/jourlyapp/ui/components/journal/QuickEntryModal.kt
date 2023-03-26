package com.example.jourlyapp.ui.components.journal

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.jourlyapp.R

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