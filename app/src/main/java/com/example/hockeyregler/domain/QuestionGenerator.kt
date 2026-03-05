package com.example.hockeyregler.domain

import kotlin.random.Random

/**
 * Very small question generator for MVP. From a chapter (string), it
 * creates either a simple multiple-choice question or a short answer.
 */
object QuestionGenerator {
    data class GeneratedQuestion(val prompt: String, val options: List<String>, val answer: String, val isMultipleChoice: Boolean)

    fun fromChapterText(chapterText: String): GeneratedQuestion {
        val words = chapterText
            .replace(Regex("[^a-zåäöA-ZÅÄÖ0-9\\s]"), " ")
            .split(Regex("\\s+"))
            .map { it.trim() }
            .filter { it.length >= 3 }

        if (words.isEmpty()) {
            return GeneratedQuestion("Sammanfatta kapitlet med ett ord:", emptyList(), "", false)
        }

        // Pick a word to be the answer (randomly pick a 'important-looking' word near the middle)
        val candidateIndex = words.size / 2
        val answerWord = words.getOrNull(candidateIndex) ?: words.random()

        // Build a few distractors by randomly picking other words
        val distractors = words.shuffled().filter { it.lowercase() != answerWord.lowercase() }.take(3).distinct()
        val options = (distractors + answerWord).shuffled()

        return GeneratedQuestion(
            prompt = "Vilket ord/term är centralt i kapitlet?",
            options = options,
            answer = answerWord,
            isMultipleChoice = true
        )
    }
}

