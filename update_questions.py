#!/usr/bin/env python3
import json
import random

# Läs in befintliga frågor
with open('/home/blomman/Hockeyregler/app/src/main/assets/Frgor.json', 'r', encoding='utf-8') as f:
    questions = json.load(f)

# Identifiera "lätta" frågor att ta bort
easy_keywords = [
    "vilken färg har mittlinjen",
    "vad kallas zonen där det egna målet",
    "hur många perioder spelas",
    "hur lång är varje period",
    "hur bred ska rinken vara",
    "hur lång ska en officiell ishockeyrink",
    "vilken bokstav bär assisterande lagkaptener",
    "hur högt ska numret på ryggen",
    "vad måste alla tröjor i ett lag ha"
]

# Filtrera bort lätta frågor
filtered_questions = []
removed_count = 0

for q in questions:
    question_lower = q['question'].lower()
    is_easy = any(keyword in question_lower for keyword in easy_keywords)
    
    if not is_easy:
        filtered_questions.append(q)
    else:
        removed_count += 1
        print(f"Tar bort: {q['question']}")

print(f"\nTog bort {removed_count} lätta frågor")

# Blanda svarsalternativen så rätt svar inte alltid är i mitten
for q in filtered_questions:
    if q['options'] and len(q['options']) > 0:
        correct_answer = q['answer']
        random.shuffle(q['options'])
        # Verifiera att rätt svar fortfarande finns
        if correct_answer not in q['options']:
            print(f"VARNING: Rätt svar saknas för: {q['question']}")

print(f"Blandade svarsalternativ för alla frågor")

# Lägg till nya komplexa frågor
new_complex_questions = [
    {
        "question": "En spelare får matchstraff i förlängningen. Hur påverkar detta laget?",
        "options": [
            "Laget spelar i underläge resten av förlängningen",
            "Laget spelar i underläge i 5 minuter nästa match",
            "Endast spelaren utvisas, laget påverkas inte"
        ],
        "answer": "Laget spelar i underläge resten av förlängningen",
        "rule": "Regel 5.3 - Matchstraff i förlängning"
    },
    {
        "question": "När är en spelare i offside-position vid försenat offside?",
        "options": [
            "När någon lagkamrat rör pucken i anfallszonen",
            "När spelaren själv rör pucken",
            "När spelaren passerar blålinjen"
        ],
        "answer": "När någon lagkamrat rör pucken i anfallszonen",
        "rule": "Regel 7.1 - Försenat offside"
    },
    {
        "question": "Kan en målvakt få assist på ett mål?",
        "options": [
            "Ja, om målvakten spelar pucken till en medspelare som gör mål",
            "Nej, målvakter kan aldrig få poäng",
            "Endast i powerplay"
        ],
        "answer": "Ja, om målvakten spelar pucken till en medspelare som gör mål",
        "rule": "Regel 6.7 - Målvaktsassist"
    },
    {
        "question": "Vad händer om en spelare får sin andra större utvisning i samma match?",
        "options": [
            "Spelaren får matchstraff och utvisas från matchen",
            "Spelaren får ytterligare 5 minuter",
            "Spelaren får misconduct"
        ],
        "answer": "Spelaren får matchstraff och utvisas från matchen",
        "rule": "Regel 5.2 - Andra större utvisningen"
    },
    {
        "question": "När får en målvakt lämna målområdet för att spela pucken?",
        "options": [
            "När som helst under spel",
            "Endast inom försvarszonen",
            "Endast bakom mållinjen"
        ],
        "answer": "När som helst under spel",
        "rule": "Regel 2.6 - Målvaktens spelområde"
    },
    {
        "question": "Hur många minuter är straffet för boarding med skada?",
        "options": [
            "5 minuter + matchstraff",
            "2 minuter",
            "5 minuter"
        ],
        "answer": "5 minuter + matchstraff",
        "rule": "Regel 8.3 - Boarding med skada"
    },
    {
        "question": "Vad händer om en spelare kastar sin klubba mot pucken i eget försvar?",
        "options": [
            "Straffslag till motståndarlaget",
            "2 minuters utvisning",
            "5 minuters utvisning"
        ],
        "answer": "Straffslag till motståndarlaget",
        "rule": "Regel 9.4 - Kasta klubba"
    },
    {
        "question": "När kan en lagkapten begära mättid?",
        "options": [
            "Endast vid stopp i spelet",
            "När som helst under matchen",
            "Endast mellan perioderna"
        ],
        "answer": "Endast vid stopp i spelet",
        "rule": "Regel 4.3 - Mättid"
    },
    {
        "question": "Vad är straffet för too many men on ice?",
        "options": [
            "2 minuters utvisning för bänkstraff",
            "5 minuters utvisning",
            "Varning första gången"
        ],
        "answer": "2 minuters utvisning för bänkstraff",
        "rule": "Regel 7.8 - För många spelare på isen"
    },
    {
        "question": "Kan en spelare som sitter utvisad bytas ut när straffet är slut?",
        "options": [
            "Ja, men endast vid nästa speluppehåll",
            "Ja, direkt när straffet är slut",
            "Nej, spelaren måste spela vidare"
        ],
        "answer": "Ja, direkt när straffet är slut",
        "rule": "Regel 5.5 - Byte efter utvisning"
    },
    {
        "question": "Vad händer om pucken går i mål direkt från en ansiktstavlan?",
        "options": [
            "Målet godkänns inte",
            "Målet godkänns",
            "Ny ansiktstavlan"
        ],
        "answer": "Målet godkänns inte",
        "rule": "Regel 6.2 - Mål från ansiktstavlan"
    },
    {
        "question": "När döms icing om laget är i numerärt underläge?",
        "options": [
            "Aldrig, icing döms inte vid underläge",
            "Endast om skillnaden är 2 spelare",
            "Som vanligt"
        ],
        "answer": "Aldrig, icing döms inte vid underläge",
        "rule": "Regel 7.2 - Icing vid underläge"
    },
    {
        "question": "Hur lång tid har ett lag på sig att genomföra ett byte?",
        "options": [
            "5 sekunder",
            "10 sekunder",
            "Ingen tidsgräns"
        ],
        "answer": "5 sekunder",
        "rule": "Regel 8.2 - Bytestid"
    },
    {
        "question": "Vad är straffet för att avsiktligt flytta målburen?",
        "options": [
            "2 minuters utvisning + straffslag om i sista minuten",
            "Endast varning",
            "5 minuters utvisning"
        ],
        "answer": "2 minuters utvisning + straffslag om i sista minuten",
        "rule": "Regel 9.6 - Flytta målburen"
    },
    {
        "question": "Kan en spelare få utvisning för att simulera (diving)?",
        "options": [
            "Ja, 2 minuters utvisning",
            "Nej, endast varning",
            "Ja, 5 minuters utvisning"
        ],
        "answer": "Ja, 2 minuters utvisning",
        "rule": "Regel 9.5 - Simulation/Diving"
    }
]

# Lägg till nya frågor
filtered_questions.extend(new_complex_questions)
print(f"\nLade till {len(new_complex_questions)} nya komplexa frågor")

# Spara uppdaterade frågor
with open('/home/blomman/Hockeyregler/app/src/main/assets/Frgor.json', 'w', encoding='utf-8') as f:
    json.dump(filtered_questions, f, ensure_ascii=False, indent=2)

print(f"\nTotalt antal frågor nu: {len(filtered_questions)}")
print("Frågor uppdaterade!")
