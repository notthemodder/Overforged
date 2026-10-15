# Overforged

![Minecraft](https://img.shields.io/badge/Minecraft-26.1.2-green?style=flat-square&logo=minecraft)
![Fabric](https://img.shields.io/badge/Fabric-0.19.5-blue?style=flat-square)
![Version](https://img.shields.io/badge/mod%20version-0.1.0a-orange?style=flat-square)
![License](https://img.shields.io/badge/license-MIT-lightgrey?style=flat-square)

Overforged is a Fabric mod that (will) massively expand Minecraft's material and gear progression. New ores, new alloys, new tools and armor, and a couple of custom functional blocks.

---

## Warning
 - This mod is on a very early development stage and has lots of bugs, missing textures and functionalities.
 - Do not expect playable or enjoyable gameplay on this version.

---

## Features

### New Ores
Twelve new ore types generate across the overworld, each with a stone and deepslate variant. From shallow **Aluminum** (Y 32–128) to the rare deep **Lintium** (Y -64 to -32).

### Material Progression
A full gear tier ladder from "slightly worse than iron" all the way to "knockback in a normal sword":

| Tier | Highlights |
|---|---|
| Silver, Tin, Aluminum | Sub-iron warmup materials |
| Bismuth, Platinum | Mid-tier stepping stones |
| Osmium, Tungsten | Diamond-adjacent powerhouses |
| Ruby, Titanium, Emerald | Gemstone gear, because why not |
| Vismite, Lintium | Post-Netherite territory |
| **Thuranium** | Each armor piece adds 2 max health. Sword has knockback. You're welcome. |

### Alloy Blast Furnace
A custom blast furnace that takes two ingots, some fuel and outputs a brand new alloy.

| Input A | Input B | Output |
|---|---|---|
| Titanium | Tungsten | Titanstene (will be changed, sounds stupid) |
| Tin | Bismuth | Bismuthane |
| Aluminum | Silver | Silvarium |
| Platinum | Bismuth | Platine |

Alloys can be reinforced with a Crystal in a crafting table for an even stronger ingot variant.

### Forger
A catalyst-driven machine that lets you upgrade existing swords and ingots using Crystals, Rubies, or Emeralds.
Kind of like an anvil.

---

## Getting Started

### Requirements
- Minecraft `26.1.2`
- [Fabric Loader](https://fabricmc.net/use/installer/) `0.19.5+`
- [Fabric API](https://modrinth.com/mod/fabric-api) `0.155.3+26.1.2`

### Installation
1. Install Fabric Loader for Minecraft 26.1.2
2. Drop the mod `.jar` into your `mods/` folder
3. Drop Fabric API in there too if you haven't already
4. Launch the game and go do something. 

---

## Building from Source

```bash
git clone https://github.com/notthemodder/overforged.git
cd overforged
./gradlew build
```

The output jar will be in `build/libs/`. Requires Java 25.

---

## Ore Generation Reference

| Ore | Y Range | Vein Size |
|---|---|---|
| Crystal | -16 to 48 | 6 |
| Aluminum | 32 to 128 | 7 |
| Tin | -16 to 64 | 6 |
| Silver | -24 to 48 | 5 |
| Bismuth | -16 to 48 | 5 |
| Titanium | -40 to 16 | 4 |
| Tungsten | -48 to 8 | 3 |
| Ruby | -32 to 32 | 4 |
| Indium | -24 to 24 | 3 |
| Platinum | -56 to -16 | 3 |
| Osmium | -56 to -16 | 3 |
| Lintium | -56 to -16 | 3 |

All ores have deepslate variants. Deepslate variants are harder, rarer, and more annoying to mine, obviously

---

## Contributing

PRs and issues are welcome. If you find a balance issue, have a suggestion or also want to develop, also feel free to mention them on my **[DISCORD SERVER!!](https://discord.gg/9FQh5Zvppj)**
(seriously, I'm tired of making textures)

---

## License

MIT: I don't care what you do with it, just give me credit.
