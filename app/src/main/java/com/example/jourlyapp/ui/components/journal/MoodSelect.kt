package com.example.jourlyapp.ui.components.journal

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.example.jourlyapp.model.journal.enums.Mood
import com.example.jourlyapp.ui.theme.Margins

@Composable
fun MoodSelect(
    selectedMood: Mood,
    onDoubleTap: ((mood: Mood) -> Unit) = {},
    onTap: (mood: Mood) -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Margins.horizontalLarge),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Mood.values().asList().subList(1, 6).forEach { mood ->
            MoodIcon(
                mood = mood,
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onDoubleTap = {
                                onDoubleTap(mood)
                            },
                            onTap = {
                                onTap(mood)
                            }
                        )
                    },
                highlighted = selectedMood == mood
            )
        }
    }
}