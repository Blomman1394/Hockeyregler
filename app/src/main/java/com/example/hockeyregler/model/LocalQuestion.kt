package com.example.hockeyregler.model

data class LocalQuestion(
    val question: String,
    val options: List<String>,
    val answer: String,
    val rule: String? = null,
    val acceptedAnswers: List<String>? = null
) {
    fun isCorrectAnswer(userAnswer: String): Boolean {
        val normalized = userAnswer.trim().lowercase()
        
        acceptedAnswers?.let { accepted ->
            return accepted.any { it.lowercase().trim() == normalized }
        }
        
        return answer.trim().lowercase() == normalized
    }
}
