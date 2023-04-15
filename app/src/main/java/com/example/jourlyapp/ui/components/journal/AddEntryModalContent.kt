package com.example.jourlyapp.ui.components.journal

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jourlyapp.R
import com.example.jourlyapp.model.journal.enums.Mood
import com.example.jourlyapp.ui.theme.Margins
import com.example.jourlyapp.viewmodel.EntryModalViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
@ExperimentalMaterial3Api
fun AddEntryModalContent(
    modifier: Modifier,
    coroutineScope: CoroutineScope,
    onMoodIconClick: suspend () -> Unit,
    onExpandClick: () -> Unit,
    onShrinkClick: () -> Unit,
    isFullScreen: Boolean,
    onDetailedClose: () -> Unit
) {
    val viewModel: EntryModalViewModel =
        viewModel(factory = EntryModalViewModel.Factory)

    val context = LocalContext.current

    var boxStrings = remember { mutableStateListOf<String>() }
    repeat(3) { i ->
        boxStrings.add("Lorem ipsum dolor sit amet")
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(
                horizontal = Margins.horizontalLarge,
                vertical = Margins.verticalLarge
            ),
        verticalArrangement = Arrangement.spacedBy(Margins.verticalLarge)
    ) {
        // This IconButton could be removed to use only the ModalBottomSheet already present draggable functionality
        if (!isFullScreen) {
            IconButton(
                onClick = onExpandClick
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_baseline_keyboard_arrow_up_24),
                    contentDescription = "Expand for more detailed entry",
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        } else {
            IconButton(
                onClick = {
                    viewModel.updateMood(Mood.None)
                    onShrinkClick()
                }
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_baseline_keyboard_arrow_down_24),
                    contentDescription = "Expand for more detailed entry",
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        }

        Text(
            text = stringResource(R.string.quickEntryQuestion),
            style = MaterialTheme.typography.bodyLarge
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Margins.vertical),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MoodIcon(
                mood = Mood.Great,
                modifier = Modifier
                    .size(32.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onDoubleTap = {
                                coroutineScope.launch {
                                    onMoodIconClick()
                                    addQuickEntry(viewModel, context, Mood.Great)
                                }
                            },
                            onTap = {
                                viewModel.updateMood(mood = Mood.Great)
                                onExpandClick()
                            }
                        )
                    },
                highlighted = viewModel.journalEntry.value.mood == Mood.Great
            )
            MoodIcon(
                mood = Mood.Good,
                modifier = Modifier
                .size(32.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = {
                            coroutineScope.launch {
                                onMoodIconClick()
                                addQuickEntry(viewModel, context, Mood.Good)
                            }
                        },
                        onTap = {
                            viewModel.updateMood(mood = Mood.Good)
                            onExpandClick()
                        }
                    )
                },
                highlighted = viewModel.journalEntry.value.mood == Mood.Good
            )
            MoodIcon(
                mood = Mood.Okay,
                modifier = Modifier
                .size(32.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = {
                            coroutineScope.launch {
                                onMoodIconClick()
                                addQuickEntry(viewModel, context, Mood.Okay)
                            }
                        },
                        onTap = {
                            viewModel.updateMood(mood = Mood.Okay)
                            onExpandClick()
                        }
                    )
                },
                highlighted = viewModel.journalEntry.value.mood == Mood.Okay
            )
            MoodIcon(
                mood = Mood.Bad,
                modifier = Modifier
                .size(32.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = {
                            coroutineScope.launch {
                                onMoodIconClick()
                                addQuickEntry(viewModel, context, Mood.Bad)
                            }
                        },
                        onTap = {
                            viewModel.updateMood(mood = Mood.Bad)
                            onExpandClick()
                        }
                    )
                },
                highlighted = viewModel.journalEntry.value.mood == Mood.Bad
            )
            MoodIcon(
                mood = Mood.Awful,
                modifier = Modifier
                .size(32.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = {
                            coroutineScope.launch {
                                onMoodIconClick()
                                addQuickEntry(viewModel, context, Mood.Awful)
                            }
                        },
                        onTap = {
                            viewModel.updateMood(mood = Mood.Awful)
                            onExpandClick()
                        }
                    )
                },
                highlighted = viewModel.journalEntry.value.mood == Mood.Awful
            )
        }

        if (isFullScreen) {
            AddDetailedEntryModalContent(onClose = onDetailedClose, viewModel = viewModel)
        }
    }
}

fun addQuickEntry(
    viewModel: EntryModalViewModel,
    context: Context,
    mood: Mood
) {
    viewModel.createNewQuickEntry(
        date = LocalDateTime.now(),
        mood = mood
    )
    Toast
        .makeText(
            context,
            "Quick entry added!",
            Toast.LENGTH_SHORT
        )
        .show()
}