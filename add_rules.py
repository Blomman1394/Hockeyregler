#!/usr/bin/env python3
import json

# Läs in befintliga frågor
with open('/home/blomman/Hockeyregler/app/src/main/assets/Frgor.json', 'r', encoding='utf-8') as f:
    questions = json.load(f)

# Lägg till regelreferenser för några frågor (exempel)
rule_mappings = {
    "Hur lång ska en officiell ishockeyrink vara enligt IIHF?": "Regel 1.1 - Rinkens mått",
    "Hur bred ska rinken vara enligt IIHF-reglerna?": "Regel 1.1 - Rinkens mått",
    "Vad kallas zonen där det egna målet står?": "Regel 1.2 - Zoner",
    "Vilken färg har mittlinjen på isen?": "Regel 1.3 - Linjer",
    "Hur många spelare får maximalt finnas i ett lags matchtrupp?": "Regel 3.1 - Laguppställning",
    "Vad måste alla tröjor i ett lag ha?": "Regel 3.2 - Spelardräkt",
    "Hur högt ska numret på ryggen vara?": "Regel 3.2 - Spelardräkt",
    "Vilken bokstav bär assisterande lagkaptener?": "Regel 3.3 - Lagkapten",
    "Hur många assisterande lagkaptener får ett lag ha?": "Regel 3.3 - Lagkapten",
    "Vad ska en spelare göra om hjälmen lossnar under spel?": "Regel 3.4 - Skyddsutrustning",
    "Vad kallas ett straff som är 2 minuter?": "Regel 5.1 - Mindre straff",
    "Hur länge varar en större utvisning?": "Regel 5.2 - Större straff",
    "Vad händer om en spelare får matchstraff?": "Regel 5.3 - Matchstraff",
    "Hur många perioder spelas i en ishockeymatch?": "Regel 4.1 - Matchens längd",
    "Hur lång är varje period?": "Regel 4.1 - Matchens längd",
    "Vad är offside?": "Regel 7.1 - Offside",
    "När är det icing?": "Regel 7.2 - Icing",
    "Vad är en straff?": "Regel 6.1 - Straffslag",
}

# Uppdatera frågor med regelreferenser
for q in questions:
    if q['question'] in rule_mappings:
        q['rule'] = rule_mappings[q['question']]

# Spara uppdaterade frågor
with open('/home/blomman/Hockeyregler/app/src/main/assets/Frgor.json', 'w', encoding='utf-8') as f:
    json.dump(questions, f, ensure_ascii=False, indent=2)

print(f"Uppdaterade {len([q for q in questions if 'rule' in q])} frågor med regelreferenser")
