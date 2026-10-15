# Material and ore foundation

This document records the material progression represented by the current data
and gives the intended extension point for a future Alloy Crafter.

## Ore generation

The bounds below are inclusive. Each row describes both the stone and
deepslate feature, where present. The crystal settings are preserved from the
existing implementation.

| Ore | Host | Y range | Vein | Attempts/chunk | Drop |
|---|---|---:|---:|---:|---|
| Crystal | Stone | -16 to 48 | 6 | 4 | 2–4 Crystal Shards |
| Deepslate Crystal | Deepslate | -64 to -8 | 4 | 2 | 2–4 Crystal Shards |
| Aluminum | Stone | 32 to 128 | 7 | 12 | 1–3 Raw Aluminum |
| Deepslate Aluminum | Deepslate | 0 to 64 | 5 | 7 | 1–3 Raw Aluminum |
| Tin | Stone | -16 to 64 | 6 | 8 | 1–3 Raw Tin |
| Deepslate Tin | Deepslate | -64 to 0 | 4 | 5 | 1–3 Raw Tin |
| Silver | Stone | -24 to 48 | 5 | 5 | 1–2 Raw Silver |
| Deepslate Silver | Deepslate | -56 to 0 | 4 | 3 | 1–2 Raw Silver |
| Bismuth | Stone | -16 to 48 | 5 | 6 | 1–2 Raw Bismuth |
| Deepslate Bismuth | Deepslate | -64 to -8 | 4 | 3 | 1–2 Raw Bismuth |
| Titanium | Stone | -40 to 16 | 4 | 4 | 1–2 Raw Titanium |
| Deepslate Titanium | Deepslate | -64 to -16 | 3 | 3 | 1–2 Raw Titanium |
| Tungsten | Stone | -48 to 8 | 3 | 3 | 1–2 Raw Tungsten |
| Deepslate Tungsten | Deepslate | -64 to -24 | 3 | 2 | 1–2 Raw Tungsten |
| Indium | Stone | -24 to 24 | 3 | 2 | 1 Raw Indium |
| Deepslate Indium | Deepslate | -64 to -24 | 2 | 1 | 1 Raw Indium |
| Platinum | Stone | -56 to -16 | 3 | 2 | 1 Raw Platinum |
| Deepslate Platinum | Deepslate | -64 to -32 | 2 | 1 | 1 Raw Platinum |
| Osmium | Stone | -56 to -16 | 3 | 2 | 1 Raw Osmium |
| Deepslate Osmium | Deepslate | -64 to -24 | 2 | 1 | 1 Raw Osmium |
| Ruby | Stone | -32 to 32 | 4 | 3 | 1–2 Ruby |
| Deepslate Ruby | Deepslate | -64 to -8 | 3 | 2 | 1–2 Ruby |
| Lintium | Stone | -56 to -16 | 3 | 2 | 1 Raw Lintium |
| Deepslate Lintium | Deepslate | -64 to -32 | 2 | 1 | 1 Raw Lintium |

Ore configured features target the corresponding vanilla
`stone_ore_replaceables` or `deepslate_ore_replaceables` tag. The mining tags
are extensions (`replace: false`) and require a stone tier pickaxe. Deepslate
ore blocks have greater hardness and blast resistance than their stone pair.

## Materials and processing

| Material | Acquisition | Processing / recipe |
|---|---|---|
| Crystal | Mine either Crystal Ore for shards | Craft 2 vertically stacked shards into 1 Crystal |
| Tungsten | Raw Tungsten from either ore | Smelt or blast raw material into 1 ingot |
| Titanium | Raw Titanium from either ore | Smelt or blast raw material into 1 ingot |
| Indium | Raw Indium from either ore | Smelt or blast raw material into 1 ingot |
| Aluminum | Raw Aluminum from either ore | Smelt or blast raw material into 1 ingot |
| Silver | Raw Silver from either ore | Smelt or blast raw material into 1 ingot |
| Platinum | Raw Platinum from either ore | Smelt or blast raw material into 1 ingot |
| Tin | Raw Tin from either ore | Smelt or blast raw material into 1 ingot |
| Bismuth | Raw Bismuth from either ore | Smelt or blast raw material into 1 ingot |
| Osmium | Raw Osmium from either ore | Smelt or blast raw material into 1 ingot |
| Ruby | Mine either Ruby Ore | Drops Ruby gemstone directly |
| Lintium | Raw Lintium from either deep ore | Smelt or blast raw material into 1 ingot |
| Electrum (additional alloy) | Crafted alloy | 1 Silver Ingot + 1 Gold Ingot yields 2 Electrum Ingots |

All smelting and blasting recipes consume one raw item and output one ingot;
blasting uses half the cooking time. Furnace recipes take 200 ticks and blast
furnace recipes take 100 ticks.

## Alloy extension point

The crafting table currently uses ordinary vanilla shaped recipes, keeping
definitions also live in `data/overforged/alloy_recipe/` as small JSON
records with ingredient item IDs and quantities plus an output ID and count.
That format is the proposed contract for the future Alloy Crafter: load each
record as a data-pack resource, check that each input stack meets its declared
quantity, consume those amounts, then create the declared output. Validate
duplicate ingredient IDs by combining their quantities before checking the
inventory.

Vanilla shaped recipes encode quantity by repeating ingredients in a pattern,
while shapeless recipes allow at most nine occupied crafting slots and do not
express a general numeric ratio in one ingredient entry. The Alloy Crafter
will need to use the quantity records directly instead of trying to infer
ratios from a crafting-grid recipe. New examples can be added by adding a
vanilla recipe and a matching definition JSON.

## Tungsten and Osmium equipment

Tungsten and Osmium each have a sword, pickaxe, axe, shovel, hoe, helmet,
chestplate, leggings, and boots. Their standard shaped crafting recipes use
the corresponding ingots and sticks. They appear in the Tools & Utilities and
Combat creative tabs. Other material equipment has now been added. Plutonium
has an ingot item but no equipment or recipe until its special sword design
is specified.

| Material | Tool durability | Mining speed | Attack bonus | Enchantability | Armor durability base | Armor defense (head/chest/legs/feet) | Toughness | Knockback resistance |
|---|---:|---:|---:|---:|---:|---|---:|---:|
| Osmium | 520 | 7.0 | 2.25 | 18 | 20 | 3/7/5/2 | 0.5 | 0 |
| Diamond (reference) | 1561 | 8.0 | 3.0 | 10 | 33 | 3/8/6/3 | 2.0 | 0 |
| Tungsten | 1850 | 8.5 | 3.5 | 12 | 35 | 3/8/6/3 | 2.5 | 0.05 |
| Netherite (reference) | 2031 | 9.0 | 4.0 | 15 | 37 | 3/8/6/3 | 3.0 | 0.1 |

Osmium uses the iron harvest tier and Tungsten uses the diamond harvest tier.
The vanilla 26.1.2 tags do not distinguish Diamond from Netherite harvest
requirements, so Tungsten's advantage over Diamond is its tool and armor
statistics. Equipment asset definitions are in `assets/overforged/equipment/`.

## Added material equipment tiers

Each row below represents a sword, pickaxe, axe, shovel, hoe, helmet,
chestplate, leggings, and boots. The repair ingredient is the matching ingot;
Ruby uses Ruby and Emerald uses the vanilla Emerald item.

| Material | Tool durability | Mining speed | Attack bonus | Armor durability base | Armor defense (head/chest/legs/feet) | Toughness | Notes |
|---|---:|---:|---:|---:|---|---:|---|
| Silver | 170 | 5.5 | 1.7 | 12 | 2/5/4/1 | 0 | Below iron |
| Tin | 190 | 5.2 | 1.5 | 13 | 2/5/4/1 | 0 | — |
| Aluminum | 230 | 5.8 | 1.9 | 14 | 2/5/4/2 | 0 | Slightly below iron |
| Bismuth | 270 | 6.1 | 2.0 | 16 | 2/6/4/2 | 0.25 | — |
| Platinum | 400 | 6.5 | 2.1 | 18 | 3/6/5/2 | 0.5 | Below Osmium |
| Emerald | 700 | 7.5 | 2.5 | 24 | 3/7/5/2 | 1.0 | Below Ruby |
| Ruby | 1250 | 7.8 | 2.8 | 30 | 3/8/6/3 | 1.5 | Below Diamond |
| Titanium | 900 | 7.8 | 2.8 | 27 | 3/7/6/3 | 1.5 | Between Osmium and Tungsten |
| Vismite | 2300 | 10.0 | 4.5 | 40 | 4/9/7/4 | 3.5 | Above Netherite |
| Lintium | 2700 | 11.0 | 5.0 | 45 | 4/10/8/4 | 4.0 | Above Vismite |
| Thuranium | 3200 | 12.0 | 6.0 | 52 | 5/11/9/5 | 5.0 | Each armor piece adds 2 health; sword and axe add knockback |

Tin and Silver use the stone harvest tier; Aluminum, Bismuth, Platinum, and
Emerald use the iron tier; Ruby, Titanium, Vismite, Lintium, and Thuranium use
the diamond tier. These are initial balance values.

## Alloy Blast Furnace

The furnace has two metal input slots, one fuel slot, and one output slot in a
one-row interface. Put one ingot of each listed metal in the input slots and
fuel in the fuel slot. It cooks one alloy ingot in 100 ticks and accepts Coal,
Charcoal, Blaze Rods, and Coal Blocks. Stored items drop when it is broken.

| Input A | Input B | Output |
|---|---|---|
| Titanium Ingot | Tungsten Ingot | 1 Titanstene Ingot |
| Tin Ingot | Bismuth Ingot | 1 Bismuthane Ingot |
| Aluminum Ingot | Silver Ingot | 1 Silvarium Ingot |
| Platinum Ingot | Bismuth Ingot | 1 Platine Ingot |

Each alloy can be reinforced in a crafting table with one Crystal and one
alloy ingot to produce one reinforced alloy ingot.

Further alloy ideas using existing ingredients: **Osmiridium** (Osmium +
three-input recipe). These are ideas only, not registered recipes.

## Forger

The Forger has a catalyst slot, a material/sword slot, and a takeable output.
Insert one Crystal, Ruby, or vanilla Emerald in the first slot. The second slot
accepts registered mod ingots or base material swords. Crystal plus an ingot
produces its `crystallized_*_ingot` form; Crystal plus a base sword produces a
`crystallized_*_sword`. Ruby or Emerald plus a base material sword produces a
`rubyforged_*_sword` or `emeraldforged_*_sword`. Ruby/Emerald plus an ingot has
no output. Taking an output consumes one item from each input. The Forger has
no fuel or experience cost. Crystallized swords can also be crafted with the
matching crystallized ingot and two sticks in the normal sword pattern.
