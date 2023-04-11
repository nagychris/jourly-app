package com.example.jourlyapp.ui.components.report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jourlyapp.model.journal.entities.JournalEntry
import com.example.jourlyapp.model.journal.enums.Mood
import com.example.jourlyapp.ui.theme.Margins
import com.example.jourlyapp.ui.util.ChartUtil
import com.github.tehras.charts.line.LineChart
import com.github.tehras.charts.line.renderer.point.FilledCircularPointDrawer
import com.github.tehras.charts.line.renderer.xaxis.SimpleXAxisDrawer
import com.github.tehras.charts.piechart.animation.simpleChartAnimation
import kotlin.math.roundToInt

@Composable
fun MoodDevelopmentLineChart(
    journalEntries: List<JournalEntry>,
    xAxisLabelRatio: Int
) {
    Column {
        Text(
            "Mood Development",
            style = MaterialTheme.typography.labelMedium
        )
        Spacer(modifier = Modifier.height(Margins.verticalLarge))
        Row(
            modifier = Modifier
                .height(250.dp)
        ) {
            if (journalEntries.isEmpty())
                Text(
                    text = "No data available",
                )
            else
                LineChart(
                    linesChartData = listOf(
                        ChartUtil.getLineChartData(entries = journalEntries)
                    ),
                    modifier = Modifier.fillMaxSize(),
                    animation = simpleChartAnimation(),
                    pointDrawer = FilledCircularPointDrawer(
                        color = MaterialTheme.colorScheme.primary
                    ),
                    horizontalOffset = 1f,
                    labels = ChartUtil.journalEntriesToDateLabels(journalEntries),
                    yAxisDrawer = com.github.tehras.charts.line.renderer.yaxis.SimpleYAxisDrawer(
                        labelValueFormatter = {
                            val mood = Mood.fromOrdinal(it.roundToInt())
                            mood?.toString() ?: ""
                        }
                    ),
                    xAxisDrawer = SimpleXAxisDrawer(
                        labelRatio = xAxisLabelRatio
                    )
                )
        }
    }
}