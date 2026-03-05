# HockeyRegler v4.0 - Ny Design

## 🎨 Designuppdatering

Inspirerat av [Domarakademin.nu](https://domarakademin.nu/) har appen fått en modern, ren design med fokus på användarvänlighet och läsbarhet.

### Färgschema

**Primära färger:**
- **Orange/Röd** (#E63946) - Huvudfärg för knappar och accenter
- **Mörk Blå** (#1D3557) - För text och kontrast
- **Ljus Blå** (#457B9D) - Sekundära element

**Bakgrunder:**
- **Off-White** (#F8F9FA) - Huvudbakgrund (ljust och rent)
- **Vit** (#FFFFFF) - Kort och ytor
- **Ljusgrå** (#E9ECEF) - Subtila avskiljningar

**Feedback-färger:**
- **Grön** (#2A9D8F) - Rätt svar
- **Orange/Röd** (#E76F51) - Fel svar

### Designprinciper

✅ **Ljust tema som standard** - Bättre läsbarhet
✅ **Rena, vita kort** - Tydlig hierarki
✅ **Röd/orange accenter** - Energi och fokus
✅ **Generösa marginaler** - Luftig design
✅ **Tydliga ikoner** - Visuell vägledning

### Före vs Efter

**Före:**
- Mörk bakgrund (#121212)
- Blå header (#0D47A1)
- Gula accenter (#FFD700)
- Mörkgrå kort (#1E1E1E)

**Efter:**
- Ljus bakgrund (#F8F9FA)
- Röd/orange header (#E63946)
- Blå accenter (#1D3557)
- Vita kort (#FFFFFF)

## 🎯 Ikonförslag

### Gratis ikoner från Flaticon & Freepik

**Rekommenderade ikonpaket:**

1. **Hockey Icons by Freepik** (Gratis)
   - URL: https://www.flaticon.com/free-icons/hockey
   - Stil: Moderna, rena linjeikoner
   - Format: SVG, PNG
   - Licens: Gratis med attribution

2. **Sports Icons by Flaticon** (Gratis)
   - Innehåller: Puck, klubba, målbur, visselpipa
   - Stil: Minimalistiska
   - Perfekt för: Menyikoner

### Föreslagna ikoner för appen:

**Huvudmeny:**
- 📝 **Regelprov** → Clipboard med penna
- 🔍 **Sök** → Förstoringsglas
- 📖 **Regelbok** → Öppen bok
- 🏠 **Hem** → Hus

**Quiz:**
- ✓ **Rätt svar** → Grön checkmark
- ✗ **Fel svar** → Röd kryss
- 📊 **Resultat** → Stapeldiagram
- 🎯 **Studieförslag** → Målskylt

**Navigation:**
- ☰ **Meny** → Hamburger (tre linjer)
- ← **Tillbaka** → Vänsterpil
- → **Nästa** → Högerpil

### Implementering

För att använda egna ikoner istället för emojis:

1. Ladda ner ikoner från Flaticon (gratis med attribution)
2. Placera i `app/src/main/res/drawable/`
3. Använd i Compose:
```kotlin
Icon(
    painter = painterResource(id = R.drawable.ic_quiz),
    contentDescription = "Regelprov"
)
```

### Attribution

Om du använder gratis ikoner från Flaticon/Freepik, lägg till i appen:
```
Ikoner av Freepik från www.flaticon.com
```

## 🚀 Nästa steg

För att implementera egna ikoner:
1. Besök https://www.flaticon.com/free-icons/hockey
2. Ladda ner önskade ikoner (SVG eller PNG)
3. Konvertera till Android Vector Drawable (om SVG)
4. Placera i drawable-mappen
5. Ersätt emoji-ikoner med Icon()-komponenter

## 📱 Skärmdumpar

### Hemskärm
- Röd hero-sektion med "🏒 HockeyRegler"
- Tre vita kort med ikoner och beskrivningar
- Generösa marginaler och luftig layout

### Quiz-val
- Tre alternativ: 5, 20, 40 frågor
- Varje kort visar antal, label och beskrivning
- Stora, klickbara ytor

### Resultat
- Grön för godkänt (≥80%)
- Orange för OK (50-79%)
- Röd för underkänt (<50%)
- Studieförslag i egen sektion

---

**Version:** 4.0  
**Design:** Inspirerad av Domarakademin.nu  
**Färgschema:** Röd/Orange + Blå på ljus bakgrund  
**Ikoner:** Emoji (kan ersättas med Flaticon)
