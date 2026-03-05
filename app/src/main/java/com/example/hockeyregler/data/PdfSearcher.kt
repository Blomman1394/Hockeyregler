package com.example.hockeyregler.data

import android.content.Context
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import java.io.File

data class SearchResult(
    val pageNumber: Int,
    val snippet: String,
    val relevanceScore: Int = 0
)

object PdfSearcher {
    private var cachedPages: Map<Int, String>? = null
    
    fun searchInPdf(context: Context, query: String): List<SearchResult> {
        if (query.isBlank()) return emptyList()
        
        val pages = cachedPages ?: extractAllPages(context).also { cachedPages = it }
        val results = mutableListOf<SearchResult>()
        val searchTerms = query.lowercase().split(" ").filter { it.length > 2 }
        
        pages.forEach { (pageNum, text) ->
            val lowerText = text.lowercase()
            var relevance = 0
            val snippets = mutableListOf<String>()
            
            searchTerms.forEach { term ->
                val occurrences = lowerText.split(term).size - 1
                relevance += occurrences
                
                val index = lowerText.indexOf(term)
                if (index != -1) {
                    val start = maxOf(0, index - 50)
                    val end = minOf(text.length, index + term.length + 50)
                    snippets.add("...${text.substring(start, end)}...")
                }
            }
            
            if (relevance > 0) {
                results.add(SearchResult(
                    pageNumber = pageNum,
                    snippet = snippets.firstOrNull() ?: text.take(100),
                    relevanceScore = relevance
                ))
            }
        }
        
        return results.sortedByDescending { it.relevanceScore }.take(20)
    }
    
    fun findRelevantPages(context: Context, rules: List<String>): List<SearchResult> {
        val pages = cachedPages ?: extractAllPages(context).also { cachedPages = it }
        val results = mutableListOf<SearchResult>()
        
        rules.forEach { rule ->
            val ruleNumber = extractRuleNumber(rule)
            if (ruleNumber != null) {
                pages.forEach { (pageNum, text) ->
                    if (text.contains(ruleNumber, ignoreCase = true)) {
                        results.add(SearchResult(
                            pageNumber = pageNum,
                            snippet = "Regel $ruleNumber",
                            relevanceScore = 10
                        ))
                    }
                }
            }
        }
        
        return results.distinctBy { it.pageNumber }.sortedBy { it.pageNumber }
    }
    
    private fun extractRuleNumber(rule: String): String? {
        val regex = Regex("""regel\s*(\d+\.?\d*)""", RegexOption.IGNORE_CASE)
        return regex.find(rule)?.groupValues?.get(1)
    }
    
    private fun extractAllPages(context: Context): Map<Int, String> {
        val pdfFile = copyPdfToCache(context, "spelregler-foer-ishockey-2025-2026-1.pdf")
        val pages = mutableMapOf<Int, String>()
        
        try {
            val fileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)
            val renderer = PdfRenderer(fileDescriptor)
            
            for (i in 0 until renderer.pageCount) {
                renderer.openPage(i).use { page ->
                    pages[i + 1] = "Sida ${i + 1}"
                }
            }
            
            renderer.close()
            fileDescriptor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        
        return pages
    }
    
    private fun copyPdfToCache(context: Context, name: String): File {
        val out = File(context.cacheDir, name)
        if (out.exists()) return out
        context.assets.open(name).use { input ->
            out.outputStream().use { output -> input.copyTo(output) }
        }
        return out
    }
}
