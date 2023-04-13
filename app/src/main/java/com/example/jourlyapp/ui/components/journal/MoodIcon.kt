package com.example.jourlyapp.ui.components.journal

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.jourlyapp.model.journal.enums.Mood

@Composable
fun MoodIcon(mood: Mood, modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = mood.toDrawableId()),
        contentDescription = "Mood",
        modifier = modifier.size(24.dp),
        tint = MaterialTheme.colorScheme.secondary
    )
}