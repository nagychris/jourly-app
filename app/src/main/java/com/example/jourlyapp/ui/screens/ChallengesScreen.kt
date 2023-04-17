package com.example.jourlyapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.jourlyapp.R
import com.example.jourlyapp.ui.components.shared.PageHeader
import com.example.jourlyapp.ui.theme.Margins

@Composable
fun ChallengesScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(scope) {
        Toast.makeText(
            context,
            "Not yet implemented \uD83D\uDE3F",
            Toast.LENGTH_SHORT
        ).show()
    }

    Column(
        modifier = Modifier
            .padding(
                start = Margins.horizontal,
                end = Margins.horizontal,
                top = Margins.verticalLarge
            ),
    ) {
        PageHeader(
            title = stringResource(id = R.string.challenges),
            modifier = Modifier.padding(bottom = Margins.verticalLarge)
        )
    }
}