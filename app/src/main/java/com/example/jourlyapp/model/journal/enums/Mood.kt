package com.example.jourlyapp.model.journal.enums

import com.example.jourlyapp.R

/**
 * Possible mood values used to represent the user's current feeling.
 */
enum class Mood {
    None, Great, Good, Okay, Bad, Awful;

    fun toDrawableId(): Int {
        return when (this) {
            None -> R.drawable.baseline_question_mark_24
            Great -> R.drawable.baseline_sentiment_satisfied_alt_24
            Good -> R.drawable.baseline_sentiment_satisfied_24
            Okay -> R.drawable.baseline_sentiment_neutral_24
            Bad -> R.drawable.baseline_sentiment_dissatisfied_24
            Awful -> R.drawable.outline_sentiment_dissatisfied_24
        }
    }
}
