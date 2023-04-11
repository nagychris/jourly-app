package com.example.jourlyapp.ui.components.journal

import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jourlyapp.R
import com.example.jourlyapp.model.journal.enums.Mood
import com.example.jourlyapp.viewmodel.QuickEntryModalViewModel
import java.time.LocalDateTime

/**
 * This function's goal is to build and show the modal that is used to add a quick entry of
 * the user's mood.
 *
*/
@Composable
@ExperimentalMaterial3Api
fun buildEntryModal (onClose: () -> Unit) {

    val viewModel : QuickEntryModalViewModel =
        viewModel(factory = QuickEntryModalViewModel.Factory)

    val context = LocalContext.current

    ModalBottomSheet(
        onDismissRequest = onClose,
        modifier = Modifier.height(350.dp),
        containerColor = Color.White,
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // This IconButton could be removed to use only the ModalBottomSheet already present draggable functionality
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painterResource(id = R.drawable.ic_baseline_keyboard_arrow_up_24),
                        contentDescription = "Button to detailed entry",
                        modifier = Modifier.size(20.dp)
                    )
                }

                Text(text = stringResource(R.string.quickEntryQuestion), style = MaterialTheme.typography.bodyLarge)

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MoodIcon(mood = Mood.Great, modifier = Modifier
                        .size(40.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onDoubleTap = {
                                    viewModel.createNewQuickEntry(date = LocalDateTime.now(), mood = Mood.Great)
                                    Toast.makeText(context, "Quick entry added!", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    )
                    MoodIcon(mood = Mood.Good, modifier = Modifier
                        .size(40.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onDoubleTap = {
                                    viewModel.createNewQuickEntry(date = LocalDateTime.now(), mood = Mood.Good)
                                    Toast.makeText(context, "Quick entry added!", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    )
                    MoodIcon(mood = Mood.Okay, modifier = Modifier
                        .size(40.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onDoubleTap = {
                                    viewModel.createNewQuickEntry(date = LocalDateTime.now(), mood = Mood.Okay)
                                    Toast.makeText(context, "Quick entry added!", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    )
                    MoodIcon(mood = Mood.Bad, modifier = Modifier
                        .size(40.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onDoubleTap = {
                                    viewModel.createNewQuickEntry(date = LocalDateTime.now(), mood = Mood.Bad)
                                    Toast.makeText(context, "Quick entry added!", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    )
                    MoodIcon(mood = Mood.Awful, modifier = Modifier
                        .size(40.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onDoubleTap = {
                                    viewModel.createNewQuickEntry(date = LocalDateTime.now(), mood = Mood.Awful)
                                    Toast.makeText(context, "Quick entry added!", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    )
                }
            }
        }
    )

    return
}