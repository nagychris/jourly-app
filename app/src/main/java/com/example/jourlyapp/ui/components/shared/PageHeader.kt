package com.example.jourlyapp.ui.components.shared

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jourlyapp.ui.theme.Typography

@Composable
fun PageHeader(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String = ""
) {
    Text(
        text = title,
        fontSize = Typography.titleLarge.fontSize,
        modifier = modifier.padding(bottom = if (subtitle.isEmpty()) 32.dp else 6.dp)
    )
    if (subtitle.isNotEmpty())
        Text(
            text = subtitle,
            fontSize = Typography.titleSmall.fontSize,
            modifier = modifier.padding(bottom = 32.dp)
        )
}