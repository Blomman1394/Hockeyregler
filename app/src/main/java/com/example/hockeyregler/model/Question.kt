package com.example.hockeyregler.model

sealed class Question {
    data class MultipleChoiceQuestion(
        val prompt: String,
        val options: List<String>,
        val correctIndex: Int
    ) : Question()

    data class ShortAnswerQuestion(
        val prompt: String,
        val answer: String
    ) : Question()
}

