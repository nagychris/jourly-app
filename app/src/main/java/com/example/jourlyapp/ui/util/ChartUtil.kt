package com.example.jourlyapp.ui.util

import androidx.compose.ui.graphics.Color
import com.example.jourlyapp.model.journal.entities.JournalEntry
import com.example.jourlyapp.model.journal.enums.Mood
import com.example.jourlyapp.ui.theme.Blue100
import com.example.jourlyapp.ui.theme.Blue80
import com.example.jourlyapp.ui.theme.Purple80
import com.example.jourlyapp.ui.theme.Success100
import com.example.jourlyapp.ui.theme.Success80
import com.example.jourlyapp.ui.theme.TextLight
import com.github.tehras.charts.bar.BarChartData
import com.github.tehras.charts.line.LineChartData
import com.github.tehras.charts.line.renderer.line.SolidLineDrawer

class ChartUtil {
    companion object {
        fun getBarChartData(
            entries: List<JournalEntry>,
            colorMap: Map<Mood, Color> = mapOf(
                Mood.Awful to Blue100,
                Mood.Bad to Blue80,
                Mood.Okay to TextLight,
                Mood.Good to Success80,
                Mood.Great to Success100
            )
        ): BarChartData {
            val moodFrequencies = mutableMapOf(
                Mood.Awful to 0,
                Mood.Bad to 0,
                Mood.Okay to 0,
                Mood.Good to 0,
                Mood.Great to 0
            )

            entries.forEach {
                val mood = it.mood
                // add mood occurrence to respective frequency (skip empty / invalid)
                if (mood != Mood.None && moodFrequencies[mood] != null)
                    moodFrequencies[mood] = moodFrequencies[mood]?.plus(1) ?: 0
            }
            // convert frequencies to bars
            return BarChartData(
                bars = moodFrequencies.map {
                    BarChartData.Bar(
                        it.value.toFloat(),
                        colorMap[it.key] ?: Purple80,
                        it.key.toString()
                    )
                }
            )
        }

        fun getLineChartData(
            entries: List<JournalEntry>,
        ): LineChartData {
            return LineChartData(
                points = entries.map {
                    LineChartData.Point(
                        it.mood.ordinal.toFloat(),
                        it.mood.toString()
                    )
                },
                lineDrawer = SolidLineDrawer(
                    color = Blue80
                ),
            )
        }

        fun journalEntriesToDateLabels(entries: List<JournalEntry>): List<String> {
            return entries.map {
                DateTimeParser.toShortDateString(it.date)
            }
        }
    }
}