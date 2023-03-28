package com.example.jourlyapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jourlyapp.ui.components.shared.PageHeader
import com.example.jourlyapp.viewmodel.journal.JournalViewModel

@Composable
fun JournalScreen(modifier: Modifier) {
    val viewModel =
        viewModel<JournalViewModel>(factory = JournalViewModel.Factory)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        PageHeader(title = "Welcome back to Jourly, ${viewModel.userName.value}")
        // TODO: implement entry list @CostyaC
    }
}