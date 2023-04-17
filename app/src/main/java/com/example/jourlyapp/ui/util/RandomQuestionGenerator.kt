package com.example.jourlyapp.ui.util

import com.example.jourlyapp.model.journal.enums.JournalQuestion

class RandomQuestionGenerator {
    companion object {
        private var randomNumberGenerator = (0..4).toList()

        private fun shuffleNumbers () {
            randomNumberGenerator = randomNumberGenerator.shuffled()
        }

        fun randomQuestions(): List<String> {
            shuffleNumbers()
            return listOf(
                JournalQuestion.values()[randomNumberGenerator[0]].toString(),
                JournalQuestion.values()[randomNumberGenerator[1]].toString(),
                JournalQuestion.values()[randomNumberGenerator[2]].toString()
            )
        }

    }
}