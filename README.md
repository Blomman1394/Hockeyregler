# 🏒 HockeyRegler - Ishockey Regelkunskap App

En Android-app för ishockeydomare och spelare att lära sig och testa sina kunskaper om ishockeyregler.

## 📱 Funktioner

### ✅ Regelprov
- Välj mellan 5, 20 eller 40 frågor
- Flervalsfrågor och fritextsvar
- Automatisk rättning
- Detaljerad resultatsammanfattning

### 🔍 Sök i Regelbok
- Sök direkt i regelboken efter nyckelord
- Visar relevanta sidor med textutdrag
- Klicka för att öppna PDF på rätt sida
- Rankade sökresultat

### 📚 Intelligenta Studieförslag
- Efter varje quiz analyseras dina fel svar
- Får personliga förslag på sidor att läsa
- Direkt länkning till relevanta regler
- Fokusera på det du behöver öva på

### 📖 Läs Regelbok
- Öppna hela regelboken i PDF-läsare
- Navigera till specifika sidor från sökresultat
- Baserad på IIHF:s officiella spelregler 2025-2026

## 🎨 Design

- **Dark mode** som standard
- **Marinblått och gult** färgschema
- **Hamburger-meny** för navigation (Instagram-stil)
- **Material 3 Design**
- Scrollbara skärmar för bättre användarupplevelse

## 📦 Installation

### Från APK
1. Ladda ner senaste APK från [Releases](../../releases)
2. Kopiera filen till din Android-enhet
3. Öppna filen och aktivera "Okända källor" om nödvändigt
4. Tryck på "Installera"

### Bygga från källkod
```bash
git clone https://github.com/Blomman1394/Hockeyregler.git
cd Hockeyregler
./gradlew assembleDebug
```

APK finns sedan i: `app/build/outputs/apk/debug/app-debug.apk`

## 🔧 Teknisk Stack

- **Språk:** Kotlin
- **UI:** Jetpack Compose (Material 3)
- **Min Android:** 7.0 (API 24)
- **Build System:** Gradle 8.13
- **PDF:** PdfRenderer för sökning och navigation

## 📂 Projektstruktur

```
app/src/main/
├── java/com/example/hockeyregler/
│   ├── MainActivity.kt              # Huvudaktivitet och navigation
│   ├── data/
│   │   ├── QuestionLoader.kt        # Laddar frågor från JSON
│   │   ├── PdfSearcher.kt          # Söker i PDF-regelbok
│   │   └── PdfTextExtractor.kt     # Extraherar text från PDF
│   ├── model/
│   │   ├── Question.kt             # Frågemodell
│   │   ├── LocalQuestion.kt        # Lokal frågemodell
│   │   └── Chapter.kt              # Kapitelmodell
│   ├── domain/
│   │   ├── QuestionGenerator.kt    # Genererar frågor
│   │   └── Summarizer.kt           # Sammanfattar resultat
│   └── ui/
│       ├── screens/                # UI-skärmar
│       └── theme/                  # Tema och färger
├── assets/
│   ├── Frgor.json                  # Frågedatabas (699 frågor)
│   └── spelregler-foer-ishockey-2025-2026-1.pdf
└── res/                            # Resurser (ikoner, färger, etc.)
```

## 🎯 Användning

### Gör ett Regelprov
1. Öppna appen
2. Välj "Regelprov" från menyn
3. Välj antal frågor (5, 20 eller 40)
4. Svara på frågorna
5. Se ditt resultat och studieförslag

### Sök i Regelboken
1. Välj "Sök i regelbok" från menyn
2. Skriv sökord (t.ex. "offside", "utvisning")
3. Tryck på sökknapp
4. Klicka på resultat för att öppna PDF

### Få Studieförslag
1. Gör ett regelprov
2. Efter resultat visas "Studieförslag"
3. Tryck på föreslagna regler
4. PDF öppnas på rätt sida

## 📊 Statistik

- **699 frågor** i databasen
- **31 frågor** med regelreferenser (fler läggs till)
- **Baserad på IIHF 2025-2026** spelregler
- **~12 MB** PDF-regelbok

## 🚀 Versionshistorik

### v3.0 (2026-03-05)
- ✨ Ny: Sökfunktion i regelboken
- ✨ Ny: Intelligenta studieförslag efter quiz
- ✨ Ny: Öppna PDF på specifik sida
- 📝 Lagt till regelreferenser för 31 frågor

### v2.0 (2026-02-28)
- ✅ Fixat scrollning på alla skärmar
- 🎨 Instagram-liknande navigation med hamburgermeny
- 🌙 Dark mode som standard
- 📱 Förbättrad UX

### v1.0
- 📝 Grundläggande regelprov
- 📖 Läs regelbok i extern app
- 🎨 Första versionen av UI

## 🐛 Kända Begränsningar

- PDF-textsökning fungerar bäst med enkla sökord
- Vissa PDF-läsare stödjer inte automatisk sidnavigering
- Regelreferenser finns för ~30 frågor (fler läggs till löpande)

## 🔮 Framtida Funktioner

- [ ] Inbyggd PDF-läsare med highlight
- [ ] Bokmärken och favoriter
- [ ] Statistik över tidigare quiz
- [ ] Spara studieförslag för senare
- [ ] Fler regelreferenser för alla frågor
- [ ] Offline-stöd
- [ ] Delning av resultat

## 🤝 Bidra

Bidrag är välkomna! Öppna gärna en issue eller pull request.

1. Forka projektet
2. Skapa en feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit dina ändringar (`git commit -m 'Add some AmazingFeature'`)
4. Push till branchen (`git push origin feature/AmazingFeature`)
5. Öppna en Pull Request

## 📄 Licens

Detta projekt är licensierat under MIT License - se [LICENSE](LICENSE) filen för detaljer.

## 👤 Författare

**Blomman1394**
- GitHub: [@Blomman1394](https://github.com/Blomman1394)

## 🙏 Erkännanden

- IIHF för officiella spelregler
- Android Jetpack Compose community
- Material Design 3

## 📞 Support

Om du hittar en bug eller har en feature request, öppna gärna en [issue](../../issues).

---

**Gjord med ❤️ för ishockeydomare och spelare**
