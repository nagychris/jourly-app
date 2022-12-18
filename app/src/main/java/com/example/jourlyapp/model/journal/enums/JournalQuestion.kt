package com.example.jourlyapp.model.journal.enums

/**
 * Internal enum for keeping track of the different questions available. Makes re-usage and random selection of questions easier.
 * NOT used in the DB, there the values are simply stored as String values, see [com.example.jourlyapp.model.journal.entities.QuestionAnswerPair.question].
 */
enum class JournalQuestion(private val question: String) {
    QUESTION1("What made you grateful today?"),
    QUESTION2("What was your favourite thing about today?"),
    QUESTION3("What do you value most about today?"),
    QUESTION4("Who or what made your day better?"),
    QUESTION5("What could you improve tomorrow to get you closer to the person you envision to be?"), ;

    override fun toString(): String {
        return this.question
    }
}