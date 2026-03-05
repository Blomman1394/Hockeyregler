package com.example.hockeyregler

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.hockeyregler.data.QuestionLoader
import com.example.hockeyregler.model.LocalQuestion
import com.example.hockeyregler.ui.theme.HockeyReglerTheme
import kotlinx.coroutines.launch
import java.io.File

enum class NavScreen { HOME, QUIZ_SELECT, QUIZ, SUMMARY, DETAIL, SEARCH, READ }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HockeyReglerTheme {
                AppWithNavigation()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppWithNavigation() {
    var screen by remember { mutableStateOf(NavScreen.HOME) }
    var quizQuestions by remember { mutableStateOf<List<LocalQuestion>>(emptyList()) }
    var quizResults by remember { mutableStateOf<List<Pair<LocalQuestion, String?>>>(emptyList()) }
    var detailIndex by remember { mutableStateOf(0) }
    
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(drawerContainerColor = MaterialTheme.colorScheme.surface) {
                Spacer(Modifier.height(16.dp))
                Text("🏒 HockeyRegler", modifier = Modifier.padding(16.dp), 
                    style = MaterialTheme.typography.headlineSmall)
                HorizontalDivider()
                NavItem(Icons.Default.Home, "Hem") { screen = NavScreen.HOME; scope.launch { drawerState.close() } }
                NavItem(Icons.Default.Edit, "Regelprov") { screen = NavScreen.QUIZ_SELECT; scope.launch { drawerState.close() } }
                NavItem(Icons.Default.Search, "Sök i regelbok") { screen = NavScreen.SEARCH; scope.launch { drawerState.close() } }
                NavItem(Icons.Default.Info, "Läs regelbok") { screen = NavScreen.READ; scope.launch { drawerState.close() } }
            }
        }
    ) {
        Scaffold(
            topBar = {
                if (screen !in listOf(NavScreen.QUIZ, NavScreen.SUMMARY, NavScreen.DETAIL)) {
                    TopAppBar(
                        title = { Text(when(screen) {
                            NavScreen.HOME -> "HockeyRegler"
                            NavScreen.QUIZ_SELECT -> "Välj antal frågor"
                            NavScreen.SEARCH -> "Sök i regelbok"
                            NavScreen.READ -> "Läs regelbok"
                            else -> ""
                        }) },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, "Menu", tint = MaterialTheme.colorScheme.onPrimary)
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { padding ->
            Box(Modifier.fillMaxSize().padding(padding)) {
                when (screen) {
                    NavScreen.HOME -> HomeScreenNew { screen = it }
                    NavScreen.QUIZ_SELECT -> QuizSelectScreen(
                        onSelect = { count ->
                            val all = QuestionLoader.loadFromAssets(context)
                            quizQuestions = if (all.size <= count) all.shuffled() 
                                else all.shuffled().take(count).map { 
                                    if (it.options.isNotEmpty()) it.copy(options = it.options.shuffled()) else it 
                                }
                            screen = NavScreen.QUIZ
                        },
                        onBack = { screen = NavScreen.HOME }
                    )
                    NavScreen.QUIZ -> QuizScreenNew(
                        questions = quizQuestions,
                        onComplete = { results ->
                            quizResults = results
                            screen = NavScreen.SUMMARY
                        },
                        onCancel = { screen = NavScreen.HOME }
                    )
                    NavScreen.SUMMARY -> SummaryScreenNew(
                        results = quizResults,
                        onClose = { screen = NavScreen.HOME },
                        onDetail = { idx -> detailIndex = idx; screen = NavScreen.DETAIL }
                    )
                    NavScreen.DETAIL -> DetailScreenNew(
                        result = quizResults[detailIndex],
                        index = detailIndex,
                        onBack = { screen = NavScreen.SUMMARY }
                    )
                    NavScreen.SEARCH -> SearchScreenNew()
                    NavScreen.READ -> ReadScreenNew()
                }
            }
        }
    }
}

@Composable
fun NavItem(icon: ImageVector, label: String, onClick: () -> Unit) {
    Row(
        Modifier.fillMaxWidth().clickable(onClick = onClick).padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, label, tint = MaterialTheme.colorScheme.onSurface)
        Spacer(Modifier.width(16.dp))
        Text(label, color = MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
fun HomeScreenNew(onNavigate: (NavScreen) -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        // Hero sektion
        Card(
            Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
            shape = MaterialTheme.shapes.large
        ) {
            Column(Modifier.padding(24.dp)) {
                Text(
                    "🏒 HockeyRegler",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "Utveckla din regelkunskap",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f)
                )
            }
        }
        
        Spacer(Modifier.height(24.dp))
        
        Text(
            "Utforska",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        
        Spacer(Modifier.height(12.dp))
        
        MenuCardNew(
            icon = "📝",
            title = "Regelprov",
            desc = "Testa dina kunskaper med 5, 20 eller 40 frågor"
        ) { onNavigate(NavScreen.QUIZ_SELECT) }
        
        Spacer(Modifier.height(12.dp))
        
        MenuCardNew(
            icon = "🔍",
            title = "Sök i regelbok",
            desc = "Hitta specifika regler snabbt och enkelt"
        ) { onNavigate(NavScreen.SEARCH) }
        
        Spacer(Modifier.height(12.dp))
        
        MenuCardNew(
            icon = "📖",
            title = "Läs regelbok",
            desc = "Bläddra genom hela regelboken"
        ) { onNavigate(NavScreen.READ) }
    }
}

@Composable
fun MenuCardNew(icon: String, title: String, desc: String, onClick: () -> Unit) {
    Card(
        Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                icon,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(end = 16.dp)
            )
            Column(Modifier.weight(1f)) {
                Text(
                    title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    desc,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
fun QuizSelectScreen(onSelect: (Int) -> Unit, onBack: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            "Välj antal frågor",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "Välj hur många frågor du vill ha i ditt regelprov",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
        Spacer(Modifier.height(24.dp))
        
        listOf(
            Triple(5, "Snabbtest", "Perfekt för en snabb repetition"),
            Triple(20, "Standard", "Rekommenderat för regelbunden träning"),
            Triple(40, "Fullständigt", "Utmana dig själv med ett längre prov")
        ).forEach { (count, label, desc) ->
            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onSelect(count) },
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        Modifier
                            .size(60.dp)
                            .padding(end = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "$count",
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Column(Modifier.weight(1f)) {
                        Text(
                            label,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            desc,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun QuizScreenNew(questions: List<LocalQuestion>, onComplete: (List<Pair<LocalQuestion, String?>>) -> Unit, onCancel: () -> Unit) {
    var index by rememberSaveable { mutableStateOf(0) }
    val answers = remember { mutableStateListOf<Pair<LocalQuestion, String?>>() }
    var selected by rememberSaveable { mutableStateOf<String?>(null) }
    var textAnswer by rememberSaveable { mutableStateOf("") }

    if (questions.isEmpty()) return

    val q = questions[index]

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp)) {
        Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)) {
            Column(Modifier.padding(16.dp)) {
                Text("Fråga ${index + 1} av ${questions.size}", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
        Spacer(Modifier.height(16.dp))
        Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)) {
            Text(q.question, Modifier.padding(16.dp), color = MaterialTheme.colorScheme.onSurface)
        }
        Spacer(Modifier.height(16.dp))

        if (q.options.isNotEmpty()) {
            q.options.forEach { opt ->
                val isSelected = selected == opt
                Card(
                    Modifier.fillMaxWidth().padding(vertical = 6.dp).clickable { selected = opt },
                    colors = CardDefaults.cardColors(
                        if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surface
                    )
                ) {
                    Text(opt, Modifier.padding(16.dp), 
                        color = if (isSelected) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onSurface)
                }
            }
        } else {
            TextField(textAnswer, { textAnswer = it }, Modifier.fillMaxWidth(), 
                placeholder = { Text("Skriv ditt svar") })
        }

        Spacer(Modifier.height(20.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = onCancel) { Text("Avbryt") }
            Button(onClick = {
                val ans = if (q.options.isNotEmpty()) selected else textAnswer.takeIf { it.isNotBlank() }
                answers.add(q to ans)
                selected = null
                textAnswer = ""
                if (index + 1 >= questions.size) onComplete(answers.toList())
                else index++
            }) { Text(if (index + 1 >= questions.size) "Avsluta" else "Nästa") }
        }
    }
}

@Composable
fun SummaryScreenNew(results: List<Pair<LocalQuestion, String?>>, onClose: () -> Unit, onDetail: (Int) -> Unit) {
    val correct = results.count { (q, ans) -> ans?.let { q.isCorrectAnswer(it) } == true }
    val pct = if (results.isNotEmpty()) (correct * 100) / results.size else 0
    val context = LocalContext.current
    
    val wrongAnswers = results.filter { (q, ans) -> ans?.let { q.isCorrectAnswer(it) } != true }
    val relevantRules = wrongAnswers.mapNotNull { it.first.rule }.distinct()
    val studyPages = remember(relevantRules) {
        if (relevantRules.isNotEmpty()) {
            com.example.hockeyregler.data.PdfSearcher.findRelevantPages(context, relevantRules)
        } else emptyList()
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
            if (pct >= 80) Color(0xFF2E7D32) else if (pct >= 50) MaterialTheme.colorScheme.secondary else Color(0xFFB71C1C)
        )) {
            Column(Modifier.padding(20.dp)) {
                Text("Resultat", style = MaterialTheme.typography.headlineMedium, 
                    color = if (pct >= 50) Color.Black else Color.White)
                Text("$correct / ${results.size} rätt ($pct%)", style = MaterialTheme.typography.titleLarge,
                    color = if (pct >= 50) Color.Black else Color.White)
            }
        }
        
        if (studyPages.isNotEmpty()) {
            Spacer(Modifier.height(16.dp))
            Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary)) {
                Column(Modifier.padding(16.dp)) {
                    Text("📚 Studieförslag", style = MaterialTheme.typography.titleLarge, color = Color.Black)
                    Spacer(Modifier.height(8.dp))
                    Text("Baserat på dina fel svar rekommenderar vi att du läser:", color = Color.Black)
                    Spacer(Modifier.height(8.dp))
                    studyPages.take(5).forEach { page ->
                        Button(
                            onClick = { openPdfAtPage(context, page.pageNumber) },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                        ) {
                            Text("${page.snippet} - Sida ${page.pageNumber}")
                        }
                    }
                }
            }
        }
        
        Spacer(Modifier.height(16.dp))
        LazyColumn(Modifier.weight(1f)) {
            itemsIndexed(results) { idx, (q, ans) ->
                val isCorrect = ans?.let { q.isCorrectAnswer(it) } == true
                Card(Modifier.fillMaxWidth().padding(vertical = 6.dp), 
                    colors = CardDefaults.cardColors(if (isCorrect) Color(0xFFE8F5E9) else Color(0xFFFFEBEE))) {
                    Column(Modifier.padding(12.dp)) {
                        Text("${idx + 1}. ${q.question}", color = Color.Black)
                        Spacer(Modifier.height(4.dp))
                        Text("Ditt svar: ${ans ?: "(inget)"}", 
                            color = if (isCorrect) Color(0xFF2E7D32) else Color(0xFFB71C1C))
                        Text("Rätt svar: ${q.answer}", color = Color(0xFF2E7D32))
                        q.rule?.let { Text("Regel: $it", color = Color.Black) }
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = { onDetail(idx) }) { Text("Detaljer") }
                    }
                }
            }
        }
        Button(onClick = onClose, modifier = Modifier.fillMaxWidth()) { Text("Stäng") }
    }
}

@Composable
fun DetailScreenNew(result: Pair<LocalQuestion, String?>, index: Int, onBack: () -> Unit) {
    val (q, ans) = result
    val isCorrect = ans?.let { q.isCorrectAnswer(it) } == true

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp)) {
        Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
            if (isCorrect) Color(0xFF2E7D32) else Color(0xFFB71C1C)
        )) {
            Column(Modifier.padding(16.dp)) {
                Text(if (isCorrect) "✓ Rätt" else "✗ Fel", 
                    style = MaterialTheme.typography.headlineMedium, color = Color.White)
                Text("Fråga ${index + 1}", color = Color.White)
            }
        }
        Spacer(Modifier.height(16.dp))
        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text("Fråga:", style = MaterialTheme.typography.titleMedium)
                Text(q.question)
            }
        }
        Spacer(Modifier.height(12.dp))
        Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
            if (isCorrect) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
        )) {
            Column(Modifier.padding(16.dp)) {
                Text("Ditt svar:", color = if (isCorrect) Color(0xFF2E7D32) else Color(0xFFB71C1C))
                Text(ans ?: "(inget)", color = if (isCorrect) Color(0xFF2E7D32) else Color(0xFFB71C1C))
            }
        }
        Spacer(Modifier.height(12.dp))
        Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(Color(0xFFE8F5E9))) {
            Column(Modifier.padding(16.dp)) {
                Text("Rätt svar:", color = Color(0xFF2E7D32))
                Text(q.answer, color = Color(0xFF2E7D32))
            }
        }
        q.rule?.let {
            Spacer(Modifier.height(12.dp))
            Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary)) {
                Column(Modifier.padding(16.dp)) {
                    Text("Regelreferens:", color = Color.Black)
                    Text(it, color = Color.Black)
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Tillbaka") }
    }
}

@Composable
fun SearchScreenNew() {
    var query by remember { mutableStateOf("") }
    var results by remember { mutableStateOf<List<com.example.hockeyregler.data.SearchResult>>(emptyList()) }
    val context = LocalContext.current
    
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        TextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Sök efter regel, t.ex. 'offside', 'utvisning'...") },
            trailingIcon = {
                IconButton(onClick = {
                    results = com.example.hockeyregler.data.PdfSearcher.searchInPdf(context, query)
                }) {
                    Icon(Icons.Default.Search, "Sök")
                }
            }
        )
        Spacer(Modifier.height(16.dp))
        
        if (results.isEmpty() && query.isNotBlank()) {
            Text("Inga resultat hittades", color = MaterialTheme.colorScheme.onBackground)
        } else {
            LazyColumn {
                itemsIndexed(results) { _, result ->
                    Card(
                        Modifier.fillMaxWidth().padding(vertical = 6.dp).clickable {
                            openPdfAtPage(context, result.pageNumber)
                        },
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text("Sida ${result.pageNumber}", 
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary)
                            Spacer(Modifier.height(4.dp))
                            Text(result.snippet, 
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 3)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ReadScreenNew() {
    val context = LocalContext.current
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Button(
            onClick = {
                try {
                    val cached = copyPdfToCache(context, "spelregler-foer-ishockey-2025-2026-1.pdf")
                    val uri = androidx.core.content.FileProvider.getUriForFile(
                        context, "${context.packageName}.fileprovider", cached
                    )
                    val intent = android.content.Intent(android.content.Intent.ACTION_VIEW).apply {
                        setDataAndType(uri, "application/pdf")
                        flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK or 
                                android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
                    }
                    context.startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(context, "Fel: ${e.message}", Toast.LENGTH_LONG).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Öppna regelbok")
        }
    }
}

fun copyPdfToCache(context: android.content.Context, name: String): File {
    val out = File(context.cacheDir, name)
    if (out.exists()) return out
    context.assets.open(name).use { input ->
        out.outputStream().use { output -> input.copyTo(output) }
    }
    return out
}

fun openPdfAtPage(context: android.content.Context, pageNumber: Int) {
    try {
        val cached = copyPdfToCache(context, "spelregler-foer-ishockey-2025-2026-1.pdf")
        val uri = androidx.core.content.FileProvider.getUriForFile(
            context, "${context.packageName}.fileprovider", cached
        )
        val intent = android.content.Intent(android.content.Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK or 
                    android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
            putExtra("page", pageNumber - 1)
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Kunde inte öppna PDF: ${e.message}", Toast.LENGTH_LONG).show()
    }
}
