package com.example.hockeyregler.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hockeyregler.model.LocalQuestion

@Composable
fun QuizScreen(questions: List<LocalQuestion>, onFinish: (score: Int, total: Int) -> Unit) {
    if (questions.isEmpty()) {
        Text("Inga frågor hittades")
        return
    }

    var index by remember { mutableStateOf(0) }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    val answers = remember { mutableStateMapOf<Int, String>() }
    var showResultButton by remember { mutableStateOf(false) }

    val q = questions[index]

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Fråga ${index + 1} av ${questions.size}")
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = q.question)
        Spacer(modifier = Modifier.height(12.dp))

        q.options.forEachIndexed { i, opt ->
            Row(modifier = Modifier.fillMaxWidth().selectable(selected = (selectedIndex == i), onClick = { selectedIndex = i }).padding(8.dp)) {
                Text(text = opt)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            // spara svar
            if (selectedIndex != null) {
                answers[index] = q.options[selectedIndex!!]
                selectedIndex = null
                if (index < questions.size - 1) {
                    index += 1
                } else {
                    showResultButton = true
                }
            }
        }) {
            Text(if (index < questions.size - 1) "Nästa" else "Slutför")
        }

        if (showResultButton) {
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = {
                // räkna poäng
                var score = 0
                for ((i, ans) in answers) {
                    if (ans == questions[i].answer) score += 1
                }
                onFinish(score, questions.size)
            }) {
                Text("Rätta prov")
            }
        }
    }
}
