package com.example.jourlyapp.ui.components.report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jourlyapp.model.journal.entities.JournalEntry
import com.example.jourlyapp.ui.theme.Margins
import com.example.jourlyapp.ui.util.ChartUtil
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.renderer.bar.SimpleBarDrawer
import com.github.tehras.charts.bar.renderer.label.SimpleValueDrawer
import com.github.tehras.charts.bar.renderer.yaxis.SimpleYAxisDrawer
import kotlin.math.roundToInt


@Composable
fun MoodFrequenciesBarChart(
    journalEntries: List<JournalEntry>,
    yAxisLabelRatio: Int
) {
    Column {
        Text(
            "Mood Frequencies",
            style = MaterialTheme.typography.labelMedium
        )
        Spacer(modifier = Modifier.height(Margins.verticalLarge))
        Row(
            modifier = Modifier
                .height(150.dp)
        ) {
            if (journalEntries.isEmpty())
                Text(
                    text = "No data available",
                )
            else
                BarChart(
                    barChartData = ChartUtil.getBarChartData(entries = journalEntries),
                    barDrawer = SimpleBarDrawer(),
                    labelDrawer = SimpleValueDrawer(),
                    yAxisDrawer = SimpleYAxisDrawer(
                        labelValueFormatter = {
                            it.roundToInt().toString()
                        },
                        labelRatio = yAxisLabelRatio
                    )
                )
        }
    }
}