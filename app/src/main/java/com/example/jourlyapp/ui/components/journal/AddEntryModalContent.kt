package com.example.jourlyapp.ui.components.journal

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jourlyapp.R
import com.example.jourlyapp.model.journal.enums.Mood
import com.example.jourlyapp.ui.theme.Margins
import com.example.jourlyapp.viewmodel.QuickEntryModalViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
@ExperimentalMaterial3Api
fun AddEntryModalContent(
    modifier: Modifier,
    coroutineScope: CoroutineScope,
    onMoodIconClick: suspend () -> Unit,
    onExpandClick: () -> Unit
) {
    val viewModel: QuickEntryModalViewModel =
        viewModel(factory = QuickEntryModalViewModel.Factory)

    val context = LocalContext.current

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
        IconButton(onClick = onExpandClick) {
            Icon(
                painterResource(id = R.drawable.ic_baseline_keyboard_arrow_up_24),
                contentDescription = "Expand for more detailed entry",
                modifier = Modifier
                    .size(24.dp)
            )
        }

        Text(
            text = stringResource(R.string.quickEntryQuestion),
            style = MaterialTheme.typography.bodyLarge
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MoodIcon(
                mood = Mood.Great, modifier = Modifier
                    .size(32.dp)
                    .testTag("moodIconButton")
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onDoubleTap = {
                                coroutineScope.launch {
                                    addQuickEntry(
                                        viewModel,
                                        Mood.Great
                                    )
                                    onMoodIconClick()
                                }
                            }
                        )
                    }
            )
            MoodIcon(mood = Mood.Good, modifier = Modifier
                .size(32.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = {
                            coroutineScope.launch {
                                onMoodIconClick()
                                addQuickEntry(viewModel, Mood.Good)
                            }
                        }
                    )
                }
            )
            MoodIcon(mood = Mood.Okay, modifier = Modifier
                .size(32.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = {
                            coroutineScope.launch {
                                onMoodIconClick()
                                addQuickEntry(viewModel, Mood.Okay)
                            }
                        }
                    )
                }
            )
            MoodIcon(mood = Mood.Bad, modifier = Modifier
                .size(32.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = {
                            coroutineScope.launch {
                                onMoodIconClick()
                                addQuickEntry(viewModel, Mood.Bad)
                            }
                        }
                    )
                }
            )
            MoodIcon(mood = Mood.Awful, modifier = Modifier
                .size(32.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = {
                            coroutineScope.launch {
                                onMoodIconClick()
                                addQuickEntry(viewModel, Mood.Awful)
                            }
                        }
                    )
                }
            )
        }
    }
}

fun addQuickEntry(
    viewModel: QuickEntryModalViewModel,
    mood: Mood
) {
    viewModel.createNewQuickEntry(
        date = LocalDateTime.now(),
        mood = mood
    )
}