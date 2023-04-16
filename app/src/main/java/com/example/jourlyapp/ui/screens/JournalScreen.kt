package com.example.jourlyapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jourlyapp.ui.components.journal.JournalEntryList
import com.example.jourlyapp.ui.components.shared.PageHeader
import com.example.jourlyapp.ui.theme.Margins
import com.example.jourlyapp.viewmodel.journal.JournalViewModel

@Composable
fun JournalScreen(
    modifier: Modifier = Modifier,
    viewModel: JournalViewModel =
        viewModel(factory = JournalViewModel.Factory),
) {
    val journalEntries =
        viewModel.journalEntries.observeAsState(initial = emptyList())

    Column(
        modifier = modifier
            .padding(
                start = Margins.horizontal,
                end = Margins.horizontal,
                top = Margins.verticalLarge
            ),
    ) {
        PageHeader(
            title = "Welcome back to Jourly, ${viewModel.userName.value}",
            modifier = modifier.padding(bottom = Margins.verticalLarge)
        )
        JournalEntryList(
            journalEntries = journalEntries.value,
            viewModel = viewModel
        )
    }
}