package com.example.jourlyapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jourlyapp.ui.components.journal.JournalEntryList
import com.example.jourlyapp.ui.components.shared.PageHeader
import com.example.jourlyapp.viewmodel.journal.JournalViewModel

@Composable
fun JournalScreen(
    modifier: Modifier,
    viewModel: JournalViewModel =
        viewModel(factory = JournalViewModel.Factory),
) {
    val journalEntries =
        viewModel.journalEntries.observeAsState(initial = emptyList())

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        PageHeader(
            title = "Welcome back to Jourly, ${viewModel.userName.value}",
            modifier = Modifier.padding(bottom = 8.dp)
        )
        JournalEntryList(
            modifier = Modifier.fillMaxSize(),
            journalEntries = journalEntries.value
        )
    }
}