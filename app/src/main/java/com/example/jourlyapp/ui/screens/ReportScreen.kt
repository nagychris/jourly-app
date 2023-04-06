package com.example.jourlyapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jourlyapp.model.journal.entities.JournalEntry
import com.example.jourlyapp.model.report.DateRange
import com.example.jourlyapp.ui.components.shared.PageHeader
import com.example.jourlyapp.ui.components.shared.inputs.DropdownMenuField
import com.example.jourlyapp.ui.theme.Margins
import com.example.jourlyapp.ui.util.ChartUtil
import com.example.jourlyapp.viewmodel.report.ReportViewModel
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.renderer.bar.SimpleBarDrawer

@Composable
fun ReportScreen(modifier: Modifier = Modifier) {
    val viewModel: ReportViewModel =
        viewModel(factory = ReportViewModel.Factory)

    val journalEntries =
        viewModel.journalEntries.value.collectAsState(initial = listOf())

    Column(
        modifier = modifier
            .padding(
                horizontal = Margins.horizontal,
                vertical = Margins.verticalLarge
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        PageHeader(
            title = "Your Personal Report",
            modifier = Modifier.padding(bottom = Margins.verticalLarge)
        )
        DropdownMenuField(
            label = { Text(text = "Select Time Range") },
            items = DateRange.values().map { it.string },
            onValueChange = {
                val dateRange = DateRange.fromString(it)
                if (dateRange != null) {
                    viewModel.updateStartDate(dateRange)
                }
            })
        Spacer(modifier = Modifier.height(Margins.verticalLarge))
        MoodFrequenciesBarChart(journalEntries = journalEntries.value)
    }
}

@Composable
private fun MoodFrequenciesBarChart(journalEntries: List<JournalEntry>?) {
    Column {
        Text(
            "Mood Frequencies",
            style = MaterialTheme.typography.labelMedium
        )
        Spacer(modifier = Modifier.height(Margins.horizontalLarge))
        Row(
            modifier = Modifier
                .height(150.dp)
        ) {
            if (journalEntries.isNullOrEmpty())
                Text(
                    text = "No data available",
                )
            else
                BarChart(
                    barChartData = ChartUtil.getBarChartData(entries = journalEntries),
                    barDrawer = SimpleBarDrawer(),
                )
        }
    }
}