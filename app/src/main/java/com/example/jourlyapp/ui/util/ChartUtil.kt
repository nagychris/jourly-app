package com.example.jourlyapp.ui.util

import androidx.compose.ui.graphics.Color
import com.example.jourlyapp.model.journal.entities.JournalEntry
import com.example.jourlyapp.model.journal.enums.Mood
import com.example.jourlyapp.ui.theme.Blue100
import com.example.jourlyapp.ui.theme.Blue80
import com.example.jourlyapp.ui.theme.Danger100
import com.example.jourlyapp.ui.theme.Danger80
import com.example.jourlyapp.ui.theme.Purple80
import com.example.jourlyapp.ui.theme.TextLight
import com.github.tehras.charts.bar.BarChartData

class ChartUtil {
    companion object {
        fun getBarChartData(
            entries: List<JournalEntry>,
            colorMap: Map<Mood, Color> = mapOf(
                Mood.Awful to Danger100,
                Mood.Bad to Danger80,
                Mood.Okay to TextLight,
                Mood.Good to Blue80,
                Mood.Great to Blue100
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
    }
}