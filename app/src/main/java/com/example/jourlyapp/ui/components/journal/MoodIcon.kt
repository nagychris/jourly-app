package com.example.jourlyapp.ui.components.journal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.jourlyapp.model.journal.enums.Mood
import com.example.jourlyapp.ui.theme.Purple80

@Composable
fun MoodIcon(mood: Mood, modifier: Modifier = Modifier, highlighted: Boolean = false) {
    Icon(
        painter = painterResource(id = mood.toDrawableId()),
        contentDescription = "Mood",
        modifier = modifier
            .size(24.dp)
            .background(if(highlighted) Purple80 else Color.Transparent),
        tint = MaterialTheme.colorScheme.secondary,
    )
}