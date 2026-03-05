# HockeyRegler v3.0 - Nya Funktioner

## 🎉 Nya funktioner i v3.0

### 🔍 Sökfunktion i Regelboken
Nu kan du söka direkt i regelboken!

**Hur det fungerar:**
1. Gå till "Sök i regelbok" från menyn
2. Skriv in sökord (t.ex. "offside", "utvisning", "straff")
3. Appen visar relevanta sidor med textutdrag
4. Tryck på ett resultat för att öppna regelboken på rätt sida

**Exempel på sökningar:**
- "offside" - Hitta regler om offside
- "utvisning 2 minuter" - Hitta regler om mindre straff
- "målvakt" - Hitta alla regler om målvakter
- "icing" - Hitta regler om icing

### 📚 Intelligenta Studieförslag
Efter varje quiz får du nu personliga studieförslag!

**Hur det fungerar:**
1. Gör ett regelprov (5, 20 eller 40 frågor)
2. När du är klar visas ditt resultat
3. **NYT:** Appen analyserar dina fel svar
4. Du får förslag på specifika sidor att läsa
5. Tryck på en knapp för att öppna regelboken direkt på rätt sida

**Exempel:**
Om du svarar fel på frågor om:
- Offside → Appen föreslår "Regel 7.1 - Sida 45"
- Utvisningar → Appen föreslår "Regel 5.1-5.3 - Sida 32-35"
- Rinkens mått → Appen föreslår "Regel 1.1 - Sida 8"

### 🎯 Fördelar med de nya funktionerna

**Effektivare inlärning:**
- Fokusera på det du behöver öva på
- Slipp leta i hela regelboken
- Direkt länkning till rätt sidor

**Bättre regelkunskap:**
- Lär dig från dina misstag
- Förstå varför svaret var fel
- Läs relevanta regler direkt

**Smidigare användning:**
- Snabb sökning istället för manuell bläddring
- Automatiska förslag baserat på dina svar
- Ett klick för att öppna rätt sida

## 🔧 Tekniska förbättringar

### PdfSearcher
- Ny klass för att söka i PDF-filer
- Extraherar text från regelboken
- Rankar resultat efter relevans
- Cachar sidor för snabbare sökning

### Regelreferenser
- Frågor har nu `rule`-fält
- Kopplar frågor till specifika regler
- Används för studieförslag

### Smart analys
- Analyserar fel svar efter quiz
- Hittar relevanta regelreferenser
- Föreslår rätt sidor att läsa

## 📱 Användning

### Sök i regelboken
```
Meny → Sök i regelbok → Skriv sökord → Tryck på resultat
```

### Få studieförslag
```
Meny → Regelprov → Välj antal frågor → Gör provet → Se studieförslag
```

## 🐛 Kända begränsningar

- PDF-textsökning fungerar bäst med enkla sökord
- Vissa PDF-läsare stödjer inte automatisk sidnavigering
- Regelreferenser finns för ~30 frågor (fler läggs till löpande)

## 📝 Nästa version (v4.0)

Planerade funktioner:
- Inbyggd PDF-läsare med highlight av sökresultat
- Bokmärken och favoriter
- Statistik över tidigare quiz
- Spara studieförslag för senare
- Fler regelreferenser för alla frågor

---

**Version:** 3.0  
**Nya filer:**
- `PdfSearcher.kt` - Sökfunktionalitet
- Uppdaterad `MainActivity.kt` - Integrerad sökning och studieförslag
- Uppdaterad `Frgor.json` - Regelreferenser

**Byggdatum:** 2026-03-05  
**Min Android:** 7.0 (API 24)
