package com.example.hockeyregler.data

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import java.io.File
import java.io.FileOutputStream

class PdfTextExtractor(private val context: Context) {

    suspend fun extractPages(uri: Uri): List<String> = withContext(Dispatchers.IO) {
        val pagesText = mutableListOf<String>()

        // Kopiera content URI till en temporär fil i appens filesDir
        val tmpFile = File(context.filesDir, "imported.pdf")
        context.contentResolver.openInputStream(uri)?.use { input ->
            FileOutputStream(tmpFile).use { output ->
                input.copyTo(output)
            }
        }

        // Använd PDFBox (pdfbox-android) för att extrahera text
        PDDocument.load(tmpFile).use { document ->
            val pageCount = document.getNumberOfPages()
            val stripper = PDFTextStripper()
            for (i in 1..pageCount) {
                stripper.startPage = i
                stripper.endPage = i
                val text = stripper.getText(document)
                pagesText.add(if (text.isNullOrBlank()) "Sida $i (ingen extraherad text)" else text)
            }
        }

        pagesText
    }
}
