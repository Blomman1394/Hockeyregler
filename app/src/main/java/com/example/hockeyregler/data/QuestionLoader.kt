package com.example.hockeyregler.data

import android.content.Context
import com.example.hockeyregler.model.LocalQuestion
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream

object QuestionLoader {
    /**
     * Läs frågor från en JSON-fil liggande i appens assets.
     * Stödjer två format:
     * - En platt JSON-array: [ {question:..., options:[...], answer:..., rule:...}, ... ]
     * - Ett top-level objekt med named arrays: { "block1": [ {...}, ... ], "block2": [ ... ] }
     */
    fun loadFromAssets(context: Context, assetFileName: String = "Frgor.json"): List<LocalQuestion> {
        return try {
            val input: InputStream = context.assets.open(assetFileName)
            val text = input.bufferedReader().use { it.readText() }

            val out = mutableListOf<LocalQuestion>()

            fun parseArray(arr: JSONArray) {
                for (i in 0 until arr.length()) {
                    val obj = arr.optJSONObject(i) ?: continue
                    val question = obj.optString("question")
                    val optionsJson = obj.optJSONArray("options")
                    val options = mutableListOf<String>()
                    if (optionsJson != null) {
                        for (j in 0 until optionsJson.length()) {
                            options.add(optionsJson.optString(j))
                        }
                    }
                    val answer = obj.optString("answer")
                    val rule = obj.optString("rule").takeIf { it.isNotBlank() }
                    out.add(LocalQuestion(question = question, options = options, answer = answer, rule = rule))
                }
            }

            // Try flat array first
            try {
                val arr = JSONArray(text)
                parseArray(arr)
                return out
            } catch (_: Exception) {
                // not a flat array, try object with arrays
            }

            try {
                val root = JSONObject(text)
                val keys = root.keys()
                while (keys.hasNext()) {
                    val key = keys.next()
                    val value = root.opt(key)
                    if (value is JSONArray) {
                        parseArray(value)
                    } else if (value is JSONObject) {
                        // if someone wrapped questions under an object with a field 'questions'
                        val maybeArr = value.optJSONArray("questions")
                        if (maybeArr != null) parseArray(maybeArr)
                    }
                }
                return out
            } catch (_: Exception) {
                // fallback to empty
            }

            emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}
