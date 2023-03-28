package com.example.jourlyapp.ui.components.shared.buttons

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BaseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
    ),
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        colors = colors,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
    ) {
        content()
    }
}