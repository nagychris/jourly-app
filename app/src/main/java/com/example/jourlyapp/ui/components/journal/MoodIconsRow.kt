package com.example.jourlyapp.ui.components.journal

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.jourlyapp.model.journal.enums.Mood
import com.example.jourlyapp.viewmodel.EntryModalViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MoodIconsRow (
    coroutineScope: CoroutineScope,
    onMoodIconClick: suspend () -> Unit,
    onExpandClick: () -> Unit,
    viewModel: EntryModalViewModel,
) {
    val moodsNumbers = (5 downTo 1).toList()

    moodsNumbers.forEach { num ->
        MoodIcon(
            mood = Mood.values()[num],
            modifier = Modifier
                .size(32.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = {
                            coroutineScope.launch {
                                onMoodIconClick()
                                addQuickEntry(viewModel, Mood.values()[num])
                            }
                        },
                        onTap = {
                            viewModel.updateMood(newMood = Mood.values()[num])
                            onExpandClick()
                        }
                    )
                },
            highlighted = viewModel.mood.value == Mood.values()[num]
        )
    }
}