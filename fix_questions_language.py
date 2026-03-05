#!/usr/bin/env python3
import json

# Läs in befintliga frågor
with open('/home/blomman/Hockeyregler/app/src/main/assets/Frgor.json', 'r', encoding='utf-8') as f:
    questions = json.load(f)

# Hitta och ersätt mina tidigare frågor med korrekta formuleringar
updated_questions = {
    "En spelare får matchstraff i förlängningen. Hur påverkar detta laget?": {
        "question": "En spelare i Lag A får Game Misconduct i förlängningen. Hur påverkar detta laget numerärt?",
        "options": [
            "Laget spelar i numerärt underläge resten av förlängningen",
            "Laget spelar i numerärt underläge i 5 minuter nästa match",
            "Endast spelaren utvisas, laget påverkas inte numerärt"
        ],
        "answer": "Endast spelaren utvisas, laget påverkas inte numerärt",
        "rule": "Regel 23.2 - Game Misconduct"
    },
    "När är en spelare i offside-position vid försenat offside?": {
        "question": "Under en avvaktande offside spelar en lagkamrat inne i anfallszonen avsiktligt pucken. Var sker nästkommande nedsläpp?",
        "options": [
            "I felande lags försvarszon",
            "På mittpunkten",
            "I neutral zon"
        ],
        "answer": "I felande lags försvarszon",
        "rule": "Regel 83.2 - Avvaktande offside"
    },
    "Kan en målvakt få assist på ett mål?": {
        "question": "Kan en målvakt få assist?",
        "options": [
            "Ja, om målvakten spelar pucken till en medspelare som gör mål",
            "Nej, målvakter kan aldrig få poäng",
            "Endast vid powerplay"
        ],
        "answer": "Ja, om målvakten spelar pucken till en medspelare som gör mål",
        "rule": "Regel 78.1 - Målvaktsassist"
    },
    "Vad händer om en spelare får sin andra större utvisning i samma match?": {
        "question": "En spelare får sitt andra större straff (Major Penalty) i samma match. Vad händer?",
        "options": [
            "Spelaren får Game Misconduct och utvisas från matchen",
            "Spelaren får ytterligare 5 minuter",
            "Spelaren får Misconduct Penalty"
        ],
        "answer": "Spelaren får Game Misconduct och utvisas från matchen",
        "rule": "Regel 20.3 - Andra större straffet"
    },
    "När får en målvakt lämna målområdet för att spela pucken?": {
        "question": "Målvakten i Lag A lämnar sitt målområde för att spela pucken bakom målet. En spelare från Lag B hindrar medvetet målvakten från att ta sig tillbaka till målområdet. I sekvensen direkt efter spelas pucken in i den nu ovaktade målburen av Lag B. Domslut?",
        "options": [
            "Målet ska underkännas för Goaltender Interference",
            "Målet godkänns",
            "Mindre straffet på Lag B, målet godkänns"
        ],
        "answer": "Målet ska underkännas för Goaltender Interference",
        "rule": "Regel 69.4 - Goaltender Interference"
    },
    "Hur många minuter är straffet för boarding med skada?": {
        "question": "En spelare tacklar våldsamt en motståndare in i sargen. Motståndaren blir skadad. Vilket straff ska utdömas?",
        "options": [
            "Större straffet + Game Misconduct",
            "Mindre straffet",
            "Större straffet"
        ],
        "answer": "Större straffet + Game Misconduct",
        "rule": "Regel 41.3 - Boarding med skada"
    },
    "Vad händer om en spelare kastar sin klubba mot pucken i eget försvar?": {
        "question": "Lag B är i färd med att plocka ut sin målvakt. Lag A kommer helt fri mot målet. B21 kastar sin klubba och slår bort pucken för A22 som går miste om sin målchans. Målvakten har inte hunnit lämna banan. Domslut?",
        "options": [
            "Straffslag för Lag A",
            "Tilldömt mål",
            "Mindre straffet på B21"
        ],
        "answer": "Straffslag för Lag A",
        "rule": "Regel 53.7 - Kasta utrustning"
    },
    "När kan en lagkapten begära mättid?": {
        "question": "När kan ett lag begära mättid (timeout)?",
        "options": [
            "Endast vid stopp i spelet",
            "När som helst under matchen",
            "Endast mellan perioderna"
        ],
        "answer": "Endast vid stopp i spelet",
        "rule": "Regel 87.1 - Mättid"
    },
    "Vad är straffet för too many men on ice?": {
        "question": "Ett lag har för många spelare på isen (Too Many Men). Vilket straff ska utdömas?",
        "options": [
            "Mindre straffet (bänkstraff)",
            "Större straffet",
            "Varning första gången"
        ],
        "answer": "Mindre straffet (bänkstraff)",
        "rule": "Regel 74.2 - För många spelare"
    },
    "Kan en spelare som sitter utvisad bytas ut när straffet är slut?": {
        "question": "En utvisad spelare har avtjänat sitt straff. Kan spelaren bytas ut direkt?",
        "options": [
            "Ja, direkt när straffet är slut",
            "Ja, men endast vid nästa speluppehåll",
            "Nej, spelaren måste spela vidare"
        ],
        "answer": "Ja, direkt när straffet är slut",
        "rule": "Regel 82.1 - Byte efter utvisning"
    },
    "Vad händer om pucken går i mål direkt från en ansiktstavlan?": {
        "question": "Pucken går i mål direkt från en ansiktstavlan utan att någon annan spelare rör pucken. Domslut?",
        "options": [
            "Målet godkänns inte",
            "Målet godkänns",
            "Ny ansiktstavlan"
        ],
        "answer": "Målet godkänns inte",
        "rule": "Regel 85.4 - Mål från nedsläpp"
    },
    "När döms icing om laget är i numerärt underläge?": {
        "question": "Ett lag är i numerärt underläge. Kan laget dömas för icing?",
        "options": [
            "Nej, icing döms inte vid numerärt underläge",
            "Ja, endast om skillnaden är 2 spelare",
            "Ja, som vanligt"
        ],
        "answer": "Nej, icing döms inte vid numerärt underläge",
        "rule": "Regel 81.1 - Icing vid underläge"
    },
    "Hur lång tid har ett lag på sig att genomföra ett byte?": {
        "question": "En målvakt lämnar sitt målområde för att bytas ut mot en extra utespelare under pågående spel. Utespelaren beträder isen innan målvakten är inom 1,5 meter från spelarbåset. Hur ska domarna agera?",
        "options": [
            "Stoppa spelet omedelbart (om felande laget har pucken)",
            "Låta spelet fortsätta",
            "Utdöma mindre straffet"
        ],
        "answer": "Stoppa spelet omedelbart (om felande laget har pucken)",
        "rule": "Regel 71.1 - För tidigt byte"
    },
    "Vad är straffet för att avsiktligt flytta målburen?": {
        "question": "En försvarare flyttar avsiktligt målburen med mindre än 2 minuter kvar av matchen. Anfallande lag har pucken. Domslut?",
        "options": [
            "Mindre straffet + straffslag",
            "Endast mindre straffet",
            "Större straffet"
        ],
        "answer": "Mindre straffet + straffslag",
        "rule": "Regel 63.6 - Flytta målburen"
    },
    "Kan en spelare få utvisning för att simulera (diving)?": {
        "question": "En målvakt initierar avsiktligt kontakt med en anfallare i målområdet och faller till isen för att få en utvisning. Domslut?",
        "options": [
            "Mindre straffet på målvakten för Embellishment",
            "Mindre straffet på anfallaren",
            "Ingen utvisning"
        ],
        "answer": "Mindre straffet på målvakten för Embellishment",
        "rule": "Regel 64.1 - Embellishment"
    }
}

# Uppdatera frågorna
for i, q in enumerate(questions):
    if q['question'] in updated_questions:
        questions[i] = updated_questions[q['question']]
        print(f"Uppdaterade: {q['question'][:50]}...")

# Spara uppdaterade frågor
with open('/home/blomman/Hockeyregler/app/src/main/assets/Frgor.json', 'w', encoding='utf-8') as f:
    json.dump(questions, f, ensure_ascii=False, indent=2)

print(f"\nUppdaterade {len(updated_questions)} frågor med korrekt domarsvenska")
