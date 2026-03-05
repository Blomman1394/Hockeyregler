package com.example.hockeyregler.data

import android.content.Context
import java.io.File

data class SearchResult(
    val pageNumber: Int,
    val snippet: String,
    val relevanceScore: Int = 0
)

object PdfSearcher {
    // Mappning av vanliga söktermer till sidor i regelboken
    private val ruleIndex = mapOf(
        "offside" to listOf(83 to "Regel 83 - Off-side"),
        "icing" to listOf(81 to "Regel 81 - Icing"),
        "boarding" to listOf(41 to "Regel 41 - Boarding"),
        "charging" to listOf(42 to "Regel 42 - Charging"),
        "checking from behind" to listOf(43 to "Regel 43 - Checking from Behind"),
        "cross-checking" to listOf(59 to "Regel 59 - Cross-checking"),
        "elbowing" to listOf(45 to "Regel 45 - Elbowing"),
        "fighting" to listOf(46 to "Regel 46 - Fighting"),
        "high-sticking" to listOf(60 to "Regel 60 - High-sticking"),
        "holding" to listOf(54 to "Regel 54 - Holding"),
        "hooking" to listOf(55 to "Regel 55 - Hooking"),
        "interference" to listOf(56 to "Regel 56 - Interference"),
        "slashing" to listOf(61 to "Regel 61 - Slashing"),
        "spearing" to listOf(62 to "Regel 62 - Spearing"),
        "tripping" to listOf(57 to "Regel 57 - Tripping"),
        "roughing" to listOf(51 to "Regel 51 - Roughing"),
        "misconduct" to listOf(23 to "Regel 23 - Game Misconduct"),
        "game misconduct" to listOf(23 to "Regel 23 - Game Misconduct"),
        "match penalty" to listOf(21 to "Regel 21 - Match Penalty"),
        "penalty shot" to listOf(24 to "Regel 24 - Penalty Shot"),
        "straffslag" to listOf(24 to "Regel 24 - Penalty Shot"),
        "målvakt" to listOf(27 to "Regel 27 - Målvakter"),
        "goaltender" to listOf(27 to "Regel 27 - Målvakter"),
        "too many players" to listOf(74 to "Regel 74 - Too Many Players"),
        "för många spelare" to listOf(74 to "Regel 74 - Too Many Players"),
        "delay of game" to listOf(63 to "Regel 63 - Delay of Game"),
        "handpass" to listOf(79 to "Regel 79 - Hand Pass"),
        "high stick" to listOf(80 to "Regel 80 - High-sticking the Puck"),
        "embellishment" to listOf(64 to "Regel 64 - Embellishment"),
        "diving" to listOf(64 to "Regel 64 - Embellishment"),
        "utvisning" to listOf(20 to "Regel 20 - Straff"),
        "straff" to listOf(20 to "Regel 20 - Straff"),
        "mindre straff" to listOf(20 to "Regel 20.2 - Mindre straff"),
        "större straff" to listOf(20 to "Regel 20.3 - Större straff"),
        "ansiktstavlan" to listOf(76 to "Regel 76 - Ansiktstavlan"),
        "face-off" to listOf(76 to "Regel 76 - Ansiktstavlan"),
        "mål" to listOf(78 to "Regel 78 - Mål"),
        "goal" to listOf(78 to "Regel 78 - Mål"),
        "byte" to listOf(82 to "Regel 82 - Spelarbyten"),
        "substitution" to listOf(82 to "Regel 82 - Spelarbyten")
    )
    
    fun searchInPdf(context: Context, query: String): List<SearchResult> {
        if (query.isBlank()) return emptyList()
        
        val results = mutableListOf<SearchResult>()
        val lowerQuery = query.lowercase().trim()
        
        // Sök i index
        ruleIndex.forEach { (term, pages) ->
            if (term.contains(lowerQuery) || lowerQuery.contains(term)) {
                val relevance = if (term == lowerQuery) 10 else 5
                pages.forEach { (pageNum, snippet) ->
                    results.add(SearchResult(
                        pageNumber = pageNum,
                        snippet = snippet,
                        relevanceScore = relevance
                    ))
                }
            }
        }
        
        // Om inga resultat, försök hitta liknande termer
        if (results.isEmpty()) {
            ruleIndex.forEach { (term, pages) ->
                val words = lowerQuery.split(" ")
                if (words.any { word -> word.length > 3 && term.contains(word) }) {
                    pages.forEach { (pageNum, snippet) ->
                        results.add(SearchResult(
                            pageNumber = pageNum,
                            snippet = snippet,
                            relevanceScore = 2
                        ))
                    }
                }
            }
        }
        
        return results
            .distinctBy { it.pageNumber }
            .sortedByDescending { it.relevanceScore }
            .take(10)
    }
    
    fun findRelevantPages(context: Context, rules: List<String>): List<SearchResult> {
        val results = mutableListOf<SearchResult>()
        
        rules.forEach { rule ->
            val ruleNumber = extractRuleNumber(rule)
            if (ruleNumber != null) {
                // Försök hitta regeln i index
                val pageNum = ruleNumber.toIntOrNull() ?: 0
                if (pageNum > 0) {
                    results.add(SearchResult(
                        pageNumber = pageNum,
                        snippet = rule,
                        relevanceScore = 10
                    ))
                }
            }
        }
        
        return results.distinctBy { it.pageNumber }.sortedBy { it.pageNumber }
    }
    
    private fun extractRuleNumber(rule: String): String? {
        val regex = Regex("""regel\s*(\d+\.?\d*)""", RegexOption.IGNORE_CASE)
        return regex.find(rule)?.groupValues?.get(1)
    }
}
