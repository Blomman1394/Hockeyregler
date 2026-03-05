# HockeyRegler APK - Installationsinstruktioner

## 📱 APK-filer

Två APK-filer har skapats:

1. **HockeyRegler.apk** (29 MB) - Debug-version
   - Enklast att installera för testning
   - Innehåller debug-information

2. **HockeyRegler-release-unsigned.apk** (25 MB) - Release-version (osignerad)
   - Mindre filstorlek (optimerad)
   - Kräver signering för Google Play Store

## 🚀 Installation på Android-enhet

### Metod 1: Direkt installation (Rekommenderad för testning)

1. Kopiera **HockeyRegler.apk** till din Android-enhet
2. Öppna filen på enheten
3. Om du får varning om "Okända källor":
   - Gå till **Inställningar** → **Säkerhet**
   - Aktivera **Okända källor** eller **Installera okända appar**
4. Tryck på **Installera**

### Metod 2: Via ADB (Android Debug Bridge)

```bash
adb install HockeyRegler.apk
```

## 📦 Vad ingår i appen?

- ✅ 70 regelkunskapsfrågor (50 grundläggande + 20 sommarfrågor)
- ✅ Val mellan 5, 20 eller 40 frågor per quiz
- ✅ Fritext-svar med flexibel validering
- ✅ Sökfunktion i regelbok (PDF)
- ✅ Modern design med marinblått, gult och svartvitt tema
- ✅ Regelreferenser för varje fråga
- ✅ Detaljerad resultatsammanfattning

## 🔧 Bygga APK själv

Om du vill bygga APK:en själv:

```bash
cd /home/blomman/Hockeyregler
./gradlew assembleDebug    # För debug-version
./gradlew assembleRelease  # För release-version
```

APK-filerna skapas i:
- Debug: `app/build/outputs/apk/debug/app-debug.apk`
- Release: `app/build/outputs/apk/release/app-release-unsigned.apk`

## 📝 För Google Play Store

För att publicera på Google Play Store behöver du:

1. Skapa en keystore för signering
2. Signera release APK:en
3. Eller använd Android App Bundle (.aab) istället

Kommando för att bygga App Bundle:
```bash
./gradlew bundleRelease
```

## 🏒 Funktioner

### Regelprov
- Välj mellan 5, 20 eller 40 frågor
- Flervalsfrågor och fritextsvar
- Automatisk rättning med flexibel validering
- Resultat med procentuell poäng

### Regelbok
- Sök i ishockeyregelbok 2025-2026
- Navigera mellan kapitel
- Visa regelreferenser

### Design
- Marinblått och gult färgschema (hockeydomare-tema)
- Material 3 Design
- Responsiv layout

## 🐛 Felsökning

Om appen inte startar:
- Kontrollera att du har Android 7.0 (API 24) eller senare
- Rensa appdata: Inställningar → Appar → HockeyRegler → Rensa data

## 📧 Support

För frågor eller problem, kontakta utvecklaren.

---
**Version:** 1.0
**Byggdatum:** 2026-02-28
**Min Android-version:** 7.0 (API 24)
