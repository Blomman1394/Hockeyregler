#!/usr/bin/env python3
import json

# Läs in befintliga frågor
with open('/home/blomman/Hockeyregler/app/src/main/assets/Frgor.json', 'r', encoding='utf-8') as f:
    questions = json.load(f)

# Könsneutrala ersättningar
replacements = {
    'Too Many Men on the Ice': 'Too Many Players on the Ice',
    'Too Many Men': 'Too Many Players',
    'för många spelare på isen (Too Many Men on the Ice)': 'för många spelare på isen (Too Many Players)',
}

count = 0
for q in questions:
    original_question = q['question']
    for old, new in replacements.items():
        if old in q['question']:
            q['question'] = q['question'].replace(old, new)
            count += 1
            print(f"Ändrade: {old} → {new}")
    
    # Uppdatera regelreferenser om de innehåller könsbundna termer
    if 'rule' in q and 'Too Many Men' in q['rule']:
        q['rule'] = q['rule'].replace('Too Many Men', 'Too Many Players')

# Spara uppdaterade frågor
with open('/home/blomman/Hockeyregler/app/src/main/assets/Frgor.json', 'w', encoding='utf-8') as f:
    json.dump(questions, f, ensure_ascii=False, indent=2)

print(f"\nUppdaterade {count} förekomster till könsneutralt språk")
