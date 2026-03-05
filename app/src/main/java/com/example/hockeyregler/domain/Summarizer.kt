package com.example.hockeyregler.domain

/**
 * Enkel extractive summarizer för MVP.
 * Algoritm: dela text i meningar, räkna ordsvikt (utan stopwords), ranka meningar
 * efter summa av ordfrekvenser och välj top N meningar. Undvik duplicerade meningar.
 */

object Summarizer {
    private val stopwords = setOf(
        "och", "i", "att", "det", "som", "en", "av", "för", "är", "med",
        "på", "till", "den", "har", "inte", "om", "ett", "vid", "de", "än",
        "men", "var", "så", "kan", "ska"
    )

    fun summarize(text: String, maxSentences: Int = 3): String {
        val sentences = splitToSentences(text)
        if (sentences.isEmpty()) return ""

        val wordFreq = mutableMapOf<String, Int>()
        for (s in sentences) {
            val words = tokenize(s)
            for (w in words) {
                if (w in stopwords) continue
                wordFreq[w] = wordFreq.getOrDefault(w, 0) + 1
            }
        }

        val sentenceScores = sentences.map { s ->
            val words = tokenize(s)
            var score = 0
            for (w in words) {
                score += wordFreq.getOrDefault(w, 0)
            }
            s to score
        }

        // Sortera meningarna efter score och välj upp till maxSentences unika meningar
        val sorted = sentenceScores.sortedByDescending { it.second }.map { it.first }
        val selected = mutableListOf<String>()
        val seen = mutableSetOf<String>()
        for (s in sorted) {
            val normalized = s.trim()
            if (normalized.isEmpty()) continue
            if (seen.add(normalized)) {
                selected.add(normalized)
            }
            if (selected.size >= maxSentences) break
        }

        // Fallback: om vi inte hittade tillräckligt många unika meningar, fyll på med första meningar
        if (selected.isEmpty() && sentences.isNotEmpty()) {
            return sentences.first()
        }

        return selected.joinToString(separator = " ")
    }

    private fun splitToSentences(text: String): List<String> {
        // Enkel split på punkt/utrop/frågetecken följt av mellanslag eller slut
        return text.split(Regex("(?<=[.!?])\\s+"))
            .map { it.trim() }
            .filter { it.isNotEmpty() }
    }

    private fun tokenize(sentence: String): List<String> {
        return sentence
            .lowercase()
            .replace(Regex("[^a-zåäö0-9\\s]"), " ")
            .split(Regex("\\s+"))
            .map { it.trim() }
            .filter { it.isNotEmpty() }
    }
}
