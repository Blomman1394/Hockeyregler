package com.example.hockeyregler.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Enkel skärm för att välja PDF från filer och visa "kapitel" (i MVP: varje N sidor)
 */

@Composable
fun ChapterSelectionScreen(
    onPdfSelected: (Uri) -> Unit,
    chapters: List<String> = emptyList(),
    onChapterClick: (Int) -> Unit = {},
    selectedFileName: String? = null
) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
        uri?.let { onPdfSelected(it) }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = { launcher.launch(arrayOf("application/pdf")) }) {
            Text("Välj PDF")
        }

        if (!selectedFileName.isNullOrEmpty()) {
            Text("Vald fil: $selectedFileName", modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))
        }

        Text("Tillgängliga kapitel/sektioner:")

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(chapters) { index, title ->
                Card(modifier = Modifier.padding(8.dp).clickable { onChapterClick(index) }) {
                    Text(title, modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}
