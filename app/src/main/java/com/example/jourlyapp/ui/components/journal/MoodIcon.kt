package com.example.jourlyapp.ui.components.journal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.jourlyapp.model.journal.enums.Mood
import com.example.jourlyapp.ui.theme.Purple60

@Composable
fun MoodIcon(
    modifier: Modifier = Modifier,
    mood: Mood,
    highlighted: Boolean = false
) {
    Icon(
        painter = painterResource(id = mood.toDrawableId()),
        contentDescription = "$mood Mood",
        modifier = modifier
            .size(32.dp)
            .background(
                if (highlighted) Purple60 else Color.Transparent,
                shape = RoundedCornerShape(6.dp)
            ),
        tint = MaterialTheme.colorScheme.secondary
    )
}