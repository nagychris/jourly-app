package com.example.jourlyapp.ui.components.shared

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jourlyapp.ui.theme.Margins

@Composable
fun PageHeader(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String = ""
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        modifier = if (subtitle.isNotEmpty()) modifier.padding(bottom = Margins.vertical) else modifier
    )
    if (subtitle.isNotEmpty())
        Text(
            text = subtitle,
            style = MaterialTheme.typography.titleSmall,
            modifier = modifier.padding(bottom = 32.dp)
        )
}