package com.example.jourlyapp.model.report

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

enum class DateRange(val string: String, val startDate: LocalDateTime) {
    PAST_WEEK(
        string = "Past Week",
        startDate = LocalDateTime.now().minusWeeks(1)
            .truncatedTo(ChronoUnit.MINUTES)
    ),
    PAST_MONTH(
        string = "Past Month",
        startDate = LocalDateTime.now().minusMonths(1)
            .truncatedTo(ChronoUnit.MINUTES)
    ),
    PAST_SIX_MONTHS(
        string = "Past Six Months",
        startDate = LocalDateTime.now().minusMonths(6)
            .truncatedTo(ChronoUnit.MINUTES)
    ),
    PAST_YEAR(
        string = "Past Year",
        startDate = LocalDateTime.now().minusYears(1)
            .truncatedTo(ChronoUnit.MINUTES)
    );

    companion object {
        fun fromString(string: String): DateRange? {
            return values().find { it.string == string }
        }
    }
}