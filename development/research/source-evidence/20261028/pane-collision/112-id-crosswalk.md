# Historical pane-neighbor ID and cube-provider crosswalk

## Scope

Source-only supplement to `inventory.md` and the accepted 1.11.2 pane-neighbor finding. The first table lists every numeric 1.12.2 Block.init registry identity with current 26.2 vanilla ID candidates or an explicit unmapped status. Registry identity does not prove statewise geometry. `inventory.md` remains authoritative for 1.12.2 FaceShape override and exclusion outcomes.

1.11.2 pane predicate: `defaultState().isCube() || block == this || block == GLASS || block == STAINED_GLASS || block == STAINED_GLASS_PANE || block instanceof PaneBlock`. Base `Block.isCube` returns true; direct false and conditional slab overrides follow. Modern-only block IDs remain native.

## Complete 1.12.2 numeric ID table

| Numeric ID | Old registry ID | Current 26.2 candidate IDs | Status | Migration note |
|---:|---|---|---|---|
| 0 | `air` | `minecraft:air` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 1 | `stone` | `minecraft:stone` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 2 | `grass` | `minecraft:grass_block` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 3 | `dirt` | `minecraft:dirt` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 4 | `cobblestone` | `minecraft:cobblestone` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 5 | `planks` | `minecraft:oak_planks`, `minecraft:spruce_planks`, `minecraft:birch_planks`, `minecraft:jungle_planks`, `minecraft:acacia_planks`, `minecraft:dark_oak_planks` | mapped/split | Species metadata split to IDs. |
| 6 | `sapling` | `minecraft:oak_sapling`, `minecraft:spruce_sapling`, `minecraft:birch_sapling`, `minecraft:jungle_sapling`, `minecraft:acacia_sapling`, `minecraft:dark_oak_sapling` | mapped/split | Species metadata split to IDs. |
| 7 | `bedrock` | `minecraft:bedrock` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 8 | `flowing_water` | `minecraft:water` | mapped/split | Merged with water; fluid state mapping separate. |
| 9 | `water` | `minecraft:water` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 10 | `flowing_lava` | `minecraft:lava` | mapped/split | Merged with lava; fluid state mapping separate. |
| 11 | `lava` | `minecraft:lava` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 12 | `sand` | `minecraft:sand` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 13 | `gravel` | `minecraft:gravel` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 14 | `gold_ore` | `minecraft:gold_ore` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 15 | `iron_ore` | `minecraft:iron_ore` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 16 | `coal_ore` | `minecraft:coal_ore` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 17 | `log` | `minecraft:oak_log`, `minecraft:spruce_log`, `minecraft:birch_log`, `minecraft:jungle_log` | mapped/split | Species split; axis migration needed. |
| 18 | `leaves` | `minecraft:oak_leaves`, `minecraft:spruce_leaves`, `minecraft:birch_leaves`, `minecraft:jungle_leaves` | mapped/split | Species/persistent/distance migration needed. |
| 19 | `sponge` | `minecraft:sponge` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 20 | `glass` | `minecraft:glass` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 21 | `lapis_ore` | `minecraft:lapis_ore` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 22 | `lapis_block` | `minecraft:lapis_block` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 23 | `dispenser` | `minecraft:dispenser` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 24 | `sandstone` | `minecraft:sandstone` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 25 | `noteblock` | `minecraft:note_block` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 26 | `bed` | `minecraft:white_bed`, `minecraft:orange_bed`, `minecraft:magenta_bed`, `minecraft:light_blue_bed`, `minecraft:yellow_bed`, `minecraft:lime_bed`, `minecraft:pink_bed`, `minecraft:gray_bed`, `minecraft:light_gray_bed`, `minecraft:cyan_bed`, `minecraft:purple_bed`, `minecraft:blue_bed`, `minecraft:brown_bed`, `minecraft:green_bed`, `minecraft:red_bed`, `minecraft:black_bed` | mapped/split | Color metadata split; bed state conversion required. |
| 27 | `golden_rail` | `minecraft:powered_rail` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 28 | `detector_rail` | `minecraft:detector_rail` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 29 | `sticky_piston` | `minecraft:sticky_piston` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 30 | `web` | `minecraft:cobweb` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 31 | `tallgrass` | `minecraft:short_grass`, `minecraft:fern` | mapped/split | Metadata variants split between short_grass and fern. |
| 32 | `deadbush` | `minecraft:dead_bush` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 33 | `piston` | `minecraft:piston` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 34 | `piston_head` | `minecraft:piston_head` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 35 | `wool` | `minecraft:white_wool`, `minecraft:orange_wool`, `minecraft:magenta_wool`, `minecraft:light_blue_wool`, `minecraft:yellow_wool`, `minecraft:lime_wool`, `minecraft:pink_wool`, `minecraft:gray_wool`, `minecraft:light_gray_wool`, `minecraft:cyan_wool`, `minecraft:purple_wool`, `minecraft:blue_wool`, `minecraft:brown_wool`, `minecraft:green_wool`, `minecraft:red_wool`, `minecraft:black_wool` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 36 | `piston_extension` | `minecraft:moving_piston` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 37 | `yellow_flower` | `minecraft:dandelion` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 38 | `red_flower` | `minecraft:poppy`, `minecraft:blue_orchid`, `minecraft:allium`, `minecraft:azure_bluet`, `minecraft:red_tulip`, `minecraft:orange_tulip`, `minecraft:white_tulip`, `minecraft:pink_tulip`, `minecraft:oxeye_daisy` | mapped/split | Metadata variants split across nine IDs. |
| 39 | `brown_mushroom` | `minecraft:brown_mushroom` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 40 | `red_mushroom` | `minecraft:red_mushroom` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 41 | `gold_block` | `minecraft:gold_block` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 42 | `iron_block` | `minecraft:iron_block` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 43 | `double_stone_slab` | `minecraft:stone_slab`, `minecraft:sandstone_slab`, `minecraft:petrified_oak_slab`, `minecraft:cobblestone_slab`, `minecraft:brick_slab`, `minecraft:stone_brick_slab`, `minecraft:nether_brick_slab`, `minecraft:quartz_slab` | mapped/split | Material metadata split; current TYPE=DOUBLE. |
| 44 | `stone_slab` | `minecraft:stone_slab`, `minecraft:sandstone_slab`, `minecraft:petrified_oak_slab`, `minecraft:cobblestone_slab`, `minecraft:brick_slab`, `minecraft:stone_brick_slab`, `minecraft:nether_brick_slab`, `minecraft:quartz_slab` | mapped/split | Material/half metadata; map current TYPE. |
| 45 | `brick_block` | `minecraft:bricks` | mapped/renamed | Legacy block ID renamed to `bricks`; identity mapping only. |
| 46 | `tnt` | `minecraft:tnt` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 47 | `bookshelf` | `minecraft:bookshelf` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 48 | `mossy_cobblestone` | `minecraft:mossy_cobblestone` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 49 | `obsidian` | `minecraft:obsidian` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 50 | `torch` | `minecraft:torch` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 51 | `fire` | `minecraft:fire` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 52 | `mob_spawner` | `minecraft:spawner` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 53 | `oak_stairs` | `minecraft:oak_stairs` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 54 | `chest` | `minecraft:chest` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 55 | `redstone_wire` | `minecraft:redstone_wire` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 56 | `diamond_ore` | `minecraft:diamond_ore` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 57 | `diamond_block` | `minecraft:diamond_block` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 58 | `crafting_table` | `minecraft:crafting_table` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 59 | `wheat` | `minecraft:wheat` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 60 | `farmland` | `minecraft:farmland` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 61 | `furnace` | `minecraft:furnace` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 62 | `lit_furnace` | `minecraft:furnace` | mapped/split | Merged into furnace LIT property. |
| 63 | `standing_sign` | `minecraft:oak_sign`, `minecraft:spruce_sign`, `minecraft:birch_sign`, `minecraft:jungle_sign`, `minecraft:acacia_sign`, `minecraft:dark_oak_sign` | mapped/split | Species split; state and block entity conversion not defined. |
| 64 | `wooden_door` | `minecraft:oak_door` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 65 | `ladder` | `minecraft:ladder` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 66 | `rail` | `minecraft:rail` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 67 | `stone_stairs` | `minecraft:stone_stairs` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 68 | `wall_sign` | `minecraft:oak_wall_sign`, `minecraft:spruce_wall_sign`, `minecraft:birch_wall_sign`, `minecraft:jungle_wall_sign`, `minecraft:acacia_wall_sign`, `minecraft:dark_oak_wall_sign` | mapped/split | Species split; state and block entity conversion not defined. |
| 69 | `lever` | `minecraft:lever` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 70 | `stone_pressure_plate` | `minecraft:stone_pressure_plate` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 71 | `iron_door` | `minecraft:iron_door` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 72 | `wooden_pressure_plate` | `minecraft:oak_pressure_plate` | mapped/split | Current oak identity; 1.12 family had only the legacy wooden pressure plate. |
| 73 | `redstone_ore` | `minecraft:redstone_ore` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 74 | `lit_redstone_ore` | `minecraft:redstone_ore` | mapped/split | Merged into redstone_ore LIT property. |
| 75 | `unlit_redstone_torch` | `minecraft:redstone_torch`, `minecraft:redstone_wall_torch` | mapped/split | Old torch block identity splits by FACING into current standing and wall IDs; both preserve the same old non-connecting pane provider. Soul/copper torch families are modern-only. |
| 76 | `redstone_torch` | `minecraft:redstone_torch`, `minecraft:redstone_wall_torch` | mapped/split | Old torch block identity splits by FACING into current standing and wall IDs; both preserve the same old non-connecting pane provider. Soul/copper torch families are modern-only. |
| 77 | `stone_button` | `minecraft:stone_button` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 78 | `snow_layer` | `minecraft:snow` | mapped/renamed | Layer identity; current `snow` layer count property represents the old height state. Horizontal old provider is UNDEFINED. |
| 79 | `ice` | `minecraft:ice` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 80 | `snow` | `minecraft:snow_block` | mapped/renamed | Full snow block maps to current `snow_block`; old class inherits base FaceShape SOLID. |
| 81 | `cactus` | `minecraft:cactus` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 82 | `clay` | `minecraft:clay` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 83 | `reeds` | `minecraft:sugar_cane` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 84 | `jukebox` | `minecraft:jukebox` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 85 | `fence` | `minecraft:oak_fence` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 86 | `pumpkin` | `minecraft:pumpkin` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 87 | `netherrack` | `minecraft:netherrack` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 88 | `soul_sand` | `minecraft:soul_sand` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 89 | `glowstone` | `minecraft:glowstone` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 90 | `portal` | `minecraft:nether_portal` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 91 | `lit_pumpkin` | `minecraft:jack_o_lantern` | mapped/split | Renamed jack_o_lantern. |
| 92 | `cake` | `minecraft:cake` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 93 | `unpowered_repeater` | `minecraft:repeater` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 94 | `powered_repeater` | `minecraft:repeater` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 95 | `stained_glass` | `minecraft:white_stained_glass`, `minecraft:orange_stained_glass`, `minecraft:magenta_stained_glass`, `minecraft:light_blue_stained_glass`, `minecraft:yellow_stained_glass`, `minecraft:lime_stained_glass`, `minecraft:pink_stained_glass`, `minecraft:gray_stained_glass`, `minecraft:light_gray_stained_glass`, `minecraft:cyan_stained_glass`, `minecraft:purple_stained_glass`, `minecraft:blue_stained_glass`, `minecraft:brown_stained_glass`, `minecraft:green_stained_glass`, `minecraft:red_stained_glass`, `minecraft:black_stained_glass` | mapped/split | Color metadata split to IDs. |
| 96 | `trapdoor` | `minecraft:oak_trapdoor` | mapped/split | Current oak identity; state conversion needed. |
| 97 | `monster_egg` | `minecraft:infested_stone`, `minecraft:infested_cobblestone`, `minecraft:infested_stone_bricks`, `minecraft:infested_mossy_stone_bricks`, `minecraft:infested_cracked_stone_bricks`, `minecraft:infested_chiseled_stone_bricks` | mapped/split | Metadata variants split across infested IDs. |
| 98 | `stonebrick` | `minecraft:stone_bricks`, `minecraft:mossy_stone_bricks`, `minecraft:cracked_stone_bricks`, `minecraft:chiseled_stone_bricks` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 99 | `brown_mushroom_block` | `minecraft:brown_mushroom_block` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 100 | `red_mushroom_block` | `minecraft:red_mushroom_block` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 101 | `iron_bars` | `minecraft:iron_bars` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 102 | `glass_pane` | `minecraft:glass_pane` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 103 | `melon_block` | `minecraft:melon` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 104 | `pumpkin_stem` | `minecraft:pumpkin_stem` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 105 | `melon_stem` | `minecraft:melon_stem` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 106 | `vine` | `minecraft:vine` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 107 | `fence_gate` | `minecraft:oak_fence_gate` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 108 | `brick_stairs` | `minecraft:brick_stairs` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 109 | `stone_brick_stairs` | `minecraft:stone_brick_stairs` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 110 | `mycelium` | `minecraft:mycelium` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 111 | `waterlily` | `minecraft:lily_pad` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 112 | `nether_brick` | `minecraft:nether_bricks` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 113 | `nether_brick_fence` | `minecraft:nether_brick_fence` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 114 | `nether_brick_stairs` | `minecraft:nether_brick_stairs` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 115 | `nether_wart` | `minecraft:nether_wart` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 116 | `enchanting_table` | `minecraft:enchanting_table` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 117 | `brewing_stand` | `minecraft:brewing_stand` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 118 | `cauldron` | `minecraft:cauldron` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 119 | `end_portal` | `minecraft:end_portal` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 120 | `end_portal_frame` | `minecraft:end_portal_frame` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 121 | `end_stone` | `minecraft:end_stone` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 122 | `dragon_egg` | `minecraft:dragon_egg` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 123 | `redstone_lamp` | `minecraft:redstone_lamp` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 124 | `lit_redstone_lamp` | `minecraft:redstone_lamp` | mapped/split | Merged into redstone_lamp LIT property. |
| 125 | `double_wooden_slab` | `minecraft:oak_slab`, `minecraft:spruce_slab`, `minecraft:birch_slab`, `minecraft:jungle_slab`, `minecraft:acacia_slab`, `minecraft:dark_oak_slab` | mapped/split | Species split; current TYPE=DOUBLE. |
| 126 | `wooden_slab` | `minecraft:oak_slab`, `minecraft:spruce_slab`, `minecraft:birch_slab`, `minecraft:jungle_slab`, `minecraft:acacia_slab`, `minecraft:dark_oak_slab` | mapped/split | Species/state split; map TYPE. |
| 127 | `cocoa` | `minecraft:cocoa` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 128 | `sandstone_stairs` | `minecraft:sandstone_stairs` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 129 | `emerald_ore` | `minecraft:emerald_ore` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 130 | `ender_chest` | `minecraft:ender_chest` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 131 | `tripwire_hook` | `minecraft:tripwire_hook` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 132 | `tripwire` | `minecraft:tripwire` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 133 | `emerald_block` | `minecraft:emerald_block` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 134 | `spruce_stairs` | `minecraft:spruce_stairs` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 135 | `birch_stairs` | `minecraft:birch_stairs` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 136 | `jungle_stairs` | `minecraft:jungle_stairs` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 137 | `command_block` | `minecraft:command_block` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 138 | `beacon` | `minecraft:beacon` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 139 | `cobblestone_wall` | `minecraft:cobblestone_wall` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 140 | `flower_pot` | `minecraft:flower_pot` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 141 | `carrots` | `minecraft:carrots` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 142 | `potatoes` | `minecraft:potatoes` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 143 | `wooden_button` | `minecraft:oak_button` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 144 | `skull` | `minecraft:skeleton_skull`, `skeleton_wall_skull`, `wither_skeleton_skull`, `wither_skeleton_wall_skull`, `zombie_head`, `zombie_wall_head`, `player_head`, `player_wall_head`, `creeper_head`, `creeper_wall_head`, `dragon_head`, `dragon_wall_head` | mapped/split | Type/facing split across IDs and states; confirm candidates against current BlockIds. |
| 145 | `anvil` | `minecraft:anvil` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 146 | `trapped_chest` | `minecraft:trapped_chest` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 147 | `light_weighted_pressure_plate` | `minecraft:light_weighted_pressure_plate` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 148 | `heavy_weighted_pressure_plate` | `minecraft:heavy_weighted_pressure_plate` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 149 | `unpowered_comparator` | `minecraft:comparator` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 150 | `powered_comparator` | `minecraft:comparator` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 151 | `daylight_detector` | `minecraft:daylight_detector` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 152 | `redstone_block` | `minecraft:redstone_block` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 153 | `quartz_ore` | `minecraft:nether_quartz_ore` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 154 | `hopper` | `minecraft:hopper` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 155 | `quartz_block` | `minecraft:quartz_block` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 156 | `quartz_stairs` | `minecraft:quartz_stairs` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 157 | `activator_rail` | `minecraft:activator_rail` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 158 | `dropper` | `minecraft:dropper` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 159 | `stained_hardened_clay` | `minecraft:white_terracotta`, `minecraft:orange_terracotta`, `minecraft:magenta_terracotta`, `minecraft:light_blue_terracotta`, `minecraft:yellow_terracotta`, `minecraft:lime_terracotta`, `minecraft:pink_terracotta`, `minecraft:gray_terracotta`, `minecraft:light_gray_terracotta`, `minecraft:cyan_terracotta`, `minecraft:purple_terracotta`, `minecraft:blue_terracotta`, `minecraft:brown_terracotta`, `minecraft:green_terracotta`, `minecraft:red_terracotta`, `minecraft:black_terracotta` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 160 | `stained_glass_pane` | `minecraft:white_stained_glass_pane`, `minecraft:orange_stained_glass_pane`, `minecraft:magenta_stained_glass_pane`, `minecraft:light_blue_stained_glass_pane`, `minecraft:yellow_stained_glass_pane`, `minecraft:lime_stained_glass_pane`, `minecraft:pink_stained_glass_pane`, `minecraft:gray_stained_glass_pane`, `minecraft:light_gray_stained_glass_pane`, `minecraft:cyan_stained_glass_pane`, `minecraft:purple_stained_glass_pane`, `minecraft:blue_stained_glass_pane`, `minecraft:brown_stained_glass_pane`, `minecraft:green_stained_glass_pane`, `minecraft:red_stained_glass_pane`, `minecraft:black_stained_glass_pane` | mapped/split | Color metadata split to IDs. |
| 161 | `leaves2` | `minecraft:acacia_leaves`, `minecraft:dark_oak_leaves` | mapped/split | Species/persistent/distance migration needed. |
| 162 | `log2` | `minecraft:acacia_log`, `minecraft:dark_oak_log` | mapped/split | Species split; axis migration needed. |
| 163 | `acacia_stairs` | `minecraft:acacia_stairs` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 164 | `dark_oak_stairs` | `minecraft:dark_oak_stairs` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 165 | `slime` | `minecraft:slime_block` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 166 | `barrier` | `minecraft:barrier` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 167 | `iron_trapdoor` | `minecraft:iron_trapdoor` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 168 | `prismarine` | `minecraft:prismarine` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 169 | `sea_lantern` | `minecraft:sea_lantern` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 170 | `hay_block` | `minecraft:hay_block` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 171 | `carpet` | `minecraft:white_carpet`, `minecraft:orange_carpet`, `minecraft:magenta_carpet`, `minecraft:light_blue_carpet`, `minecraft:yellow_carpet`, `minecraft:lime_carpet`, `minecraft:pink_carpet`, `minecraft:gray_carpet`, `minecraft:light_gray_carpet`, `minecraft:cyan_carpet`, `minecraft:purple_carpet`, `minecraft:blue_carpet`, `minecraft:brown_carpet`, `minecraft:green_carpet`, `minecraft:red_carpet`, `minecraft:black_carpet` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 172 | `hardened_clay` | `minecraft:terracotta` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 173 | `coal_block` | `minecraft:coal_block` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 174 | `packed_ice` | `minecraft:packed_ice` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 175 | `double_plant` | `minecraft:sunflower`, `minecraft:lilac`, `minecraft:tall_grass`, `minecraft:large_fern`, `minecraft:rose_bush`, `minecraft:peony` | mapped/split | Metadata variants split by identity. |
| 176 | `standing_banner` | `minecraft:white_banner`, `minecraft:orange_banner`, `minecraft:magenta_banner`, `minecraft:light_blue_banner`, `minecraft:yellow_banner`, `minecraft:lime_banner`, `minecraft:pink_banner`, `minecraft:gray_banner`, `minecraft:light_gray_banner`, `minecraft:cyan_banner`, `minecraft:purple_banner`, `minecraft:blue_banner`, `minecraft:brown_banner`, `minecraft:green_banner`, `minecraft:red_banner`, `minecraft:black_banner` | mapped/split | Color split; pattern data is block entity data. |
| 177 | `wall_banner` | `minecraft:white_wall_banner`, `minecraft:orange_wall_banner`, `minecraft:magenta_wall_banner`, `minecraft:light_blue_wall_banner`, `minecraft:yellow_wall_banner`, `minecraft:lime_wall_banner`, `minecraft:pink_wall_banner`, `minecraft:gray_wall_banner`, `minecraft:light_gray_wall_banner`, `minecraft:cyan_wall_banner`, `minecraft:purple_wall_banner`, `minecraft:blue_wall_banner`, `minecraft:brown_wall_banner`, `minecraft:green_wall_banner`, `minecraft:red_wall_banner`, `minecraft:black_wall_banner` | mapped/split | Color split; pattern data is block entity data. |
| 178 | `daylight_detector_inverted` | `minecraft:daylight_detector` | mapped/split | Merged into daylight_detector; inverter conversion needed. |
| 179 | `red_sandstone` | `minecraft:red_sandstone` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 180 | `red_sandstone_stairs` | `minecraft:red_sandstone_stairs` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 181 | `double_stone_slab2` | `minecraft:red_sandstone_slab` | mapped/split | Red sandstone current TYPE=DOUBLE. |
| 182 | `stone_slab2` | `minecraft:red_sandstone_slab` | mapped/split | Red sandstone material/half mapping; map TYPE. |
| 183 | `spruce_fence_gate` | `minecraft:spruce_fence_gate` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 184 | `birch_fence_gate` | `minecraft:birch_fence_gate` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 185 | `jungle_fence_gate` | `minecraft:jungle_fence_gate` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 186 | `dark_oak_fence_gate` | `minecraft:dark_oak_fence_gate` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 187 | `acacia_fence_gate` | `minecraft:acacia_fence_gate` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 188 | `spruce_fence` | `minecraft:spruce_fence` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 189 | `birch_fence` | `minecraft:birch_fence` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 190 | `jungle_fence` | `minecraft:jungle_fence` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 191 | `dark_oak_fence` | `minecraft:dark_oak_fence` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 192 | `acacia_fence` | `minecraft:acacia_fence` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 193 | `spruce_door` | `minecraft:spruce_door` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 194 | `birch_door` | `minecraft:birch_door` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 195 | `jungle_door` | `minecraft:jungle_door` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 196 | `acacia_door` | `minecraft:acacia_door` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 197 | `dark_oak_door` | `minecraft:dark_oak_door` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 198 | `end_rod` | `minecraft:end_rod` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 199 | `chorus_plant` | `minecraft:chorus_plant` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 200 | `chorus_flower` | `minecraft:chorus_flower` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 201 | `purpur_block` | `minecraft:purpur_block` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 202 | `purpur_pillar` | `minecraft:purpur_pillar` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 203 | `purpur_stairs` | `minecraft:purpur_stairs` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 204 | `purpur_double_slab` | `minecraft:purpur_slab` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 205 | `purpur_slab` | `minecraft:purpur_slab` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 206 | `end_bricks` | `minecraft:end_stone_bricks` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 207 | `beetroots` | `minecraft:beetroots` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 208 | `grass_path` | `minecraft:dirt_path` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 209 | `end_gateway` | `minecraft:end_gateway` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 210 | `repeating_command_block` | `minecraft:repeating_command_block` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 211 | `chain_command_block` | `minecraft:chain_command_block` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 212 | `frosted_ice` | `minecraft:frosted_ice` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 213 | `magma` | `minecraft:magma_block` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 214 | `nether_wart_block` | `minecraft:nether_wart_block` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 215 | `red_nether_brick` | `minecraft:red_nether_bricks` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 216 | `bone_block` | `minecraft:bone_block` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 217 | `structure_void` | `minecraft:structure_void` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 218 | `observer` | `minecraft:observer` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 219 | `white_shulker_box` | `minecraft:white_shulker_box` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 220 | `orange_shulker_box` | `minecraft:orange_shulker_box` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 221 | `magenta_shulker_box` | `minecraft:magenta_shulker_box` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 222 | `light_blue_shulker_box` | `minecraft:light_blue_shulker_box` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 223 | `yellow_shulker_box` | `minecraft:yellow_shulker_box` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 224 | `lime_shulker_box` | `minecraft:lime_shulker_box` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 225 | `pink_shulker_box` | `minecraft:pink_shulker_box` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 226 | `gray_shulker_box` | `minecraft:gray_shulker_box` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 227 | `silver_shulker_box` | `minecraft:light_gray_shulker_box` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 228 | `cyan_shulker_box` | `minecraft:cyan_shulker_box` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 229 | `purple_shulker_box` | `minecraft:purple_shulker_box` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 230 | `blue_shulker_box` | `minecraft:blue_shulker_box` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 231 | `brown_shulker_box` | `minecraft:brown_shulker_box` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 232 | `green_shulker_box` | `minecraft:green_shulker_box` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 233 | `red_shulker_box` | `minecraft:red_shulker_box` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 234 | `black_shulker_box` | `minecraft:black_shulker_box` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 235 | `white_glazed_terracotta` | `minecraft:white_glazed_terracotta` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 236 | `orange_glazed_terracotta` | `minecraft:orange_glazed_terracotta` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 237 | `magenta_glazed_terracotta` | `minecraft:magenta_glazed_terracotta` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 238 | `light_blue_glazed_terracotta` | `minecraft:light_blue_glazed_terracotta` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 239 | `yellow_glazed_terracotta` | `minecraft:yellow_glazed_terracotta` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 240 | `lime_glazed_terracotta` | `minecraft:lime_glazed_terracotta` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 241 | `pink_glazed_terracotta` | `minecraft:pink_glazed_terracotta` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 242 | `gray_glazed_terracotta` | `minecraft:gray_glazed_terracotta` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 243 | `silver_glazed_terracotta` | `minecraft:light_gray_glazed_terracotta` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 244 | `cyan_glazed_terracotta` | `minecraft:cyan_glazed_terracotta` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 245 | `purple_glazed_terracotta` | `minecraft:purple_glazed_terracotta` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 246 | `blue_glazed_terracotta` | `minecraft:blue_glazed_terracotta` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 247 | `brown_glazed_terracotta` | `minecraft:brown_glazed_terracotta` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 248 | `green_glazed_terracotta` | `minecraft:green_glazed_terracotta` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 249 | `red_glazed_terracotta` | `minecraft:red_glazed_terracotta` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 250 | `black_glazed_terracotta` | `minecraft:black_glazed_terracotta` | exact ID | Same-key identity only; no state/geometry equivalence implied. |
| 251 | `concrete` | `minecraft:white_concrete`, `minecraft:orange_concrete`, `minecraft:magenta_concrete`, `minecraft:light_blue_concrete`, `minecraft:yellow_concrete`, `minecraft:lime_concrete`, `minecraft:pink_concrete`, `minecraft:gray_concrete`, `minecraft:light_gray_concrete`, `minecraft:cyan_concrete`, `minecraft:purple_concrete`, `minecraft:blue_concrete`, `minecraft:brown_concrete`, `minecraft:green_concrete`, `minecraft:red_concrete`, `minecraft:black_concrete` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 252 | `concrete_powder` | `minecraft:white_concrete_powder`, `minecraft:orange_concrete_powder`, `minecraft:magenta_concrete_powder`, `minecraft:light_blue_concrete_powder`, `minecraft:yellow_concrete_powder`, `minecraft:lime_concrete_powder`, `minecraft:pink_concrete_powder`, `minecraft:gray_concrete_powder`, `minecraft:light_gray_concrete_powder`, `minecraft:cyan_concrete_powder`, `minecraft:purple_concrete_powder`, `minecraft:blue_concrete_powder`, `minecraft:brown_concrete_powder`, `minecraft:green_concrete_powder`, `minecraft:red_concrete_powder`, `minecraft:black_concrete_powder` | mapped/split | Same-key identity only; no state/geometry equivalence implied. |
| 255 | `structure_block` | `minecraft:structure_block` | exact ID | Same-key identity only; no state/geometry equivalence implied. |

Parsed 254 numeric registry registrations. Candidate IDs were checked against the exact current registry ID set. Multi-ID rows need their listed migration; no modern-only ID should receive historical behavior by broad class/tag resemblance.

## 1.11.2 `isCube` overrides

Exact class roster of direct false returns from the 1.11.2 `net/minecraft/block` sources; `SlabBlock` returns `this.isDouble()`. The accepted finding directly hashes the named pane/slab/stair/fence/wall/gate/air evidence. This table lists the remaining class-level exceptions as a source inventory; it does not imply all are current aliases.

| Class | Exact result | Old pane generic-branch result |
|---|---|---|
| `AbstractPressurePlateBlock` | `false` | Fails generic cube clause. |
| `AbstractRailBlock` | `false` | Fails generic cube clause. |
| `AirBlock` | `false` | Fails generic cube clause. |
| `AnvilBlock` | `false` | Fails generic cube clause. |
| `BannerBlock` | `false` | Fails generic cube clause. |
| `BeaconBlock` | `false` | Fails generic cube clause. |
| `BedBlock` | `false` | Fails generic cube clause. |
| `BrewingStandBlock` | `false` | Fails generic cube clause. |
| `ButtonBlock` | `false` | Fails generic cube clause. |
| `CactusBlock` | `false` | Fails generic cube clause. |
| `CakeBlock` | `false` | Fails generic cube clause. |
| `CarpetBlock` | `false` | Fails generic cube clause. |
| `CauldronBlock` | `false` | Fails generic cube clause. |
| `ChestBlock` | `false` | Fails generic cube clause. |
| `ChorusFlowerBlock` | `false` | Fails generic cube clause. |
| `ChorusPlantBlock` | `false` | Fails generic cube clause. |
| `CobwebBlock` | `false` | Fails generic cube clause. |
| `CocoaBlock` | `false` | Fails generic cube clause. |
| `DaylightDetectorBlock` | `false` | Fails generic cube clause. |
| `DiodeBlock` | `false` | Fails generic cube clause. |
| `DoorBlock` | `false` | Fails generic cube clause. |
| `DragonEggBlock` | `false` | Fails generic cube clause. |
| `EnchantingTableBlock` | `false` | Fails generic cube clause. |
| `EnderChestBlock` | `false` | Fails generic cube clause. |
| `EndGatewayBlock` | `false` | Fails generic cube clause. |
| `EndPortalBlock` | `false` | Fails generic cube clause. |
| `EndPortalFrameBlock` | `false` | Fails generic cube clause. |
| `EndRodBlock` | `false` | Fails generic cube clause. |
| `FarmlandBlock` | `false` | Fails generic cube clause. |
| `FenceBlock` | `false` | Fails generic cube clause. |
| `FenceGateBlock` | `false` | Fails generic cube clause. |
| `FireBlock` | `false` | Fails generic cube clause. |
| `FlowerPotBlock` | `false` | Fails generic cube clause. |
| `GlassBlock` | `false` | Fails generic cube clause. |
| `GrassPathBlock` | `false` | Fails generic cube clause. |
| `HopperBlock` | `false` | Fails generic cube clause. |
| `LadderBlock` | `false` | Fails generic cube clause. |
| `LeverBlock` | `false` | Fails generic cube clause. |
| `LiquidBlock` | `false` | Fails generic cube clause. |
| `MovingBlock` | `false` | Fails generic cube clause. |
| `PaneBlock` | `false` | Fails generic cube clause. |
| `PistonBaseBlock` | `false` | Fails generic cube clause. |
| `PistonHeadBlock` | `false` | Fails generic cube clause. |
| `PlantBlock` | `false` | Fails generic cube clause. |
| `PortalBlock` | `false` | Fails generic cube clause. |
| `RedstoneWireBlock` | `false` | Fails generic cube clause. |
| `ShulkerBoxBlock` | `false` | Fails generic cube clause. |
| `SignBlock` | `false` | Fails generic cube clause. |
| `SkullBlock` | `false` | Fails generic cube clause. |
| `SnowLayerBlock` | `false` | Fails generic cube clause. |
| `StainedGlassBlock` | `false` | Fails generic cube clause. |
| `StairsBlock` | `false` | Fails generic cube clause. |
| `StructureVoidBlock` | `false` | Fails generic cube clause. |
| `SugarCaneBlock` | `false` | Fails generic cube clause. |
| `TorchBlock` | `false` | Fails generic cube clause. |
| `TrapdoorBlock` | `false` | Fails generic cube clause. |
| `TripwireBlock` | `false` | Fails generic cube clause. |
| `TripwireHookBlock` | `false` | Fails generic cube clause. |
| `VineBlock` | `false` | Fails generic cube clause. |
| `WallBlock` | `false` | Fails generic cube clause. |
| `SlabBlock` | `this.isDouble()` | Passes only for default-state double slab. |

### Pane-specific class cases

| Predicate case | Old ID(s) | Current ID(s) | Mapping note |
|---|---|---|---|
| `block == this` / `instanceof PaneBlock` | `iron_bars`, `glass_pane`, `stained_glass_pane` | iron_bars, glass_pane, all 16 color stained_glass_pane IDs | Pane instances pass despite false cube predicate. |
| Explicit glass | `glass`, `stained_glass` | glass, all color stained_glass IDs | Explicitly accepted, independent of ordinary class fallback. |
| Slabs | legacy stone/wood/purpur single/double IDs | material slab candidates in registry table | Old registered default state decides double; current TYPE must be DOUBLE vs BOTTOM/TOP. Adjacent placed state is not queried. |
| Named false providers | stairs, fence, fence gate, wall, air | same-era IDs in accepted finding and inventory | Old default-state predicate returns false; do not infer from modern support/collision API. |

## Provenance

Accepted 1.11.2 finding `.native-source/20261028/collision-next/pane-neighbor-1112/pane-neighbor-components-1.11.2.md` SHA `B65F0A121F394243B4A0D47A4C8326F44A964E32E37F98DFB8CA0985A5CE434A`; audit `.native-source/20261028/collision-next/pane-neighbor-1112-audit/audit.md` SHA `A8C1A9EAA21B109F96DB4C1A2D89BCC9233182EB5F30107A4EC9BB0EFE62259C`.

Canonical roots: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.12.2/ornithe-feather/`, `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.11.2/ornithe-feather/`, `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/26.2/unobfuscated/`. 1.12.2 `Block.java` SHA `E4A90ECA411E7B0E14F5018F7385EC29891F6CDF1A1E917FA648B5712F9B33A1`. Current `BlockItemIds.java` SHA `D9D57F2F04801DA5D8D583C23B87B08F45D8B6C681980D1A77308753C801BAC6`; `BlockIds.java` SHA `0F342F57727CFE99966DA9706DA7DDB330654CB59684529166B6F175171BA69F`.

1.11.2 source ready/provenance/manifest hashes are `CEABFB266993567AAB8C867525365E18AA0A0D7BA5A2CB7B6CFCC83D21318C40`, `DCDB65E1361297A584C5B93F581B1B539911911C6CEC1A203740BAA5379732C6`, `D908C2AF76598AD26999563BFEF0089F1D3C8C32483EEE6B108F0DEFA2773DF0`; 1.12.2 hashes are `B0AEEC721E5C0AF02B33D9E217CB8272889FA139DED1ECC2A31E42B930138F7A`, `747425A26DA363D1ABC8A2CECD83A74504AEB1F58BE93AA53F48C690E35318CE`, `B8A37CCFCCD2AAC5F40F5E34FEC85873DBDFA93A266043C103E592E4A8C949DA`; 26.2 hashes are `F9406ADB6BF7CB4C1AB0792A798AB2CB90E082AD4C2EEE032C9F674D0EA8070F`, `A328F5333A1B27AADDE4422B23159A323AE219CCD26F11207C1A5461366F27D1`, `A7AD74FC712567EB87136AFC1EAFCFD602B4088E9869F3596331FD34FAEFE894`.

- Exact 1.11.2 source audit limits and accepted provider hashes are in the finding/audit links above.
- Unmapped identities remain native; modded registry behavior is not covered.
- No product/backlog, test/build/game/runtime was inspected.

## Registry classes from exact `Block.init` expressions

| Numeric ID | Old ID | Registered class |
|---:|---|---|
| 0 | `air` | `AirBlock` |
| 1 | `stone` | `StoneBlock` |
| 2 | `grass` | `GrassBlock` |
| 3 | `dirt` | `DirtBlock` |
| 4 | `cobblestone` | `Block` |
| 5 | `planks` | `PlanksBlock` |
| 6 | `sapling` | `SaplingBlock` |
| 7 | `bedrock` | `BedrockBlock` |
| 8 | `flowing_water` | `FlowingLiquidBlock` |
| 9 | `water` | `LiquidSourceBlock` |
| 10 | `flowing_lava` | `FlowingLiquidBlock` |
| 11 | `lava` | `LiquidSourceBlock` |
| 12 | `sand` | `SandBlock` |
| 13 | `gravel` | `GravelBlock` |
| 14 | `gold_ore` | `OreBlock` |
| 15 | `iron_ore` | `OreBlock` |
| 16 | `coal_ore` | `OreBlock` |
| 17 | `log` | `LogBlock` |
| 18 | `leaves` | `LeavesBlock` |
| 19 | `sponge` | `SpongeBlock` |
| 20 | `glass` | `GlassBlock` |
| 21 | `lapis_ore` | `OreBlock` |
| 22 | `lapis_block` | `Block` |
| 23 | `dispenser` | `DispenserBlock` |
| 24 | `sandstone` | `SandstoneBlock` |
| 25 | `noteblock` | `NoteBlock` |
| 26 | `bed` | `BedBlock` |
| 27 | `golden_rail` | `PoweredRailBlock` |
| 28 | `detector_rail` | `DetectorRailBlock` |
| 29 | `sticky_piston` | `PistonBaseBlock` |
| 30 | `web` | `CobwebBlock` |
| 31 | `tallgrass` | `TallPlantBlock` |
| 32 | `deadbush` | `DeadBushBlock` |
| 33 | `piston` | `PistonBaseBlock` |
| 34 | `piston_head` | `PistonHeadBlock` |
| 35 | `wool` | `ColoredBlock` |
| 36 | `piston_extension` | `MovingBlock` |
| 37 | `yellow_flower` | `YellowFlowerBlock` |
| 38 | `red_flower` | `RedFlowerBlock` |
| 39 | `brown_mushroom` | `MushroomPlantBlock` |
| 40 | `red_mushroom` | `MushroomPlantBlock` |
| 41 | `gold_block` | `Block` |
| 42 | `iron_block` | `Block` |
| 43 | `double_stone_slab` | `DoubleStoneSlabBlock` |
| 44 | `stone_slab` | `SingleStoneSlabBlock` |
| 45 | `brick_block` | `Block` |
| 46 | `tnt` | `TntBlock` |
| 47 | `bookshelf` | `BookshelfBlock` |
| 48 | `mossy_cobblestone` | `Block` |
| 49 | `obsidian` | `ObsidianBlock` |
| 50 | `torch` | `TorchBlock` |
| 51 | `fire` | `FireBlock` |
| 52 | `mob_spawner` | `MobSpawnerBlock` |
| 53 | `oak_stairs` | `StairsBlock` |
| 54 | `chest` | `ChestBlock` |
| 55 | `redstone_wire` | `RedstoneWireBlock` |
| 56 | `diamond_ore` | `OreBlock` |
| 57 | `diamond_block` | `Block` |
| 58 | `crafting_table` | `CraftingTableBlock` |
| 59 | `wheat` | `WheatBlock` |
| 60 | `farmland` | `FarmlandBlock` |
| 61 | `furnace` | `FurnaceBlock` |
| 62 | `lit_furnace` | `FurnaceBlock` |
| 63 | `standing_sign` | `StandingSignBlock` |
| 64 | `wooden_door` | `DoorBlock` |
| 65 | `ladder` | `LadderBlock` |
| 66 | `rail` | `RailBlock` |
| 67 | `stone_stairs` | `StairsBlock` |
| 68 | `wall_sign` | `WallSignBlock` |
| 69 | `lever` | `LeverBlock` |
| 70 | `stone_pressure_plate` | `PressurePlateBlock` |
| 71 | `iron_door` | `DoorBlock` |
| 72 | `wooden_pressure_plate` | `PressurePlateBlock` |
| 73 | `redstone_ore` | `RedstoneOreBlock` |
| 74 | `lit_redstone_ore` | `RedstoneOreBlock` |
| 75 | `unlit_redstone_torch` | `RedstoneTorchBlock` |
| 76 | `redstone_torch` | `RedstoneTorchBlock` |
| 77 | `stone_button` | `StoneButtonBlock` |
| 78 | `snow_layer` | `SnowLayerBlock` |
| 79 | `ice` | `IceBlock` |
| 80 | `snow` | `SnowBlock` |
| 81 | `cactus` | `CactusBlock` |
| 82 | `clay` | `ClayBlock` |
| 83 | `reeds` | `SugarCaneBlock` |
| 84 | `jukebox` | `JukeboxBlock` |
| 85 | `fence` | `FenceBlock` |
| 86 | `pumpkin` | `PumpkinBlock` |
| 87 | `netherrack` | `NetherrackBlock` |
| 88 | `soul_sand` | `SoulSandBlock` |
| 89 | `glowstone` | `GlowstoneBlock` |
| 90 | `portal` | `PortalBlock` |
| 91 | `lit_pumpkin` | `PumpkinBlock` |
| 92 | `cake` | `CakeBlock` |
| 93 | `unpowered_repeater` | `RepeaterBlock` |
| 94 | `powered_repeater` | `RepeaterBlock` |
| 95 | `stained_glass` | `StainedGlassBlock` |
| 96 | `trapdoor` | `TrapdoorBlock` |
| 97 | `monster_egg` | `InfestedBlock` |
| 98 | `stonebrick` | `StonebrickBlock` |
| 99 | `brown_mushroom_block` | `MushroomBlock` |
| 100 | `red_mushroom_block` | `MushroomBlock` |
| 101 | `iron_bars` | `PaneBlock` |
| 102 | `glass_pane` | `PaneBlock` |
| 103 | `melon_block` | `MelonBlock` |
| 104 | `pumpkin_stem` | `StemBlock` |
| 105 | `melon_stem` | `StemBlock` |
| 106 | `vine` | `VineBlock` |
| 107 | `fence_gate` | `FenceGateBlock` |
| 108 | `brick_stairs` | `StairsBlock` |
| 109 | `stone_brick_stairs` | `StairsBlock` |
| 110 | `mycelium` | `MyceliumBlock` |
| 111 | `waterlily` | `LilyPadBlock` |
| 112 | `nether_brick` | `NetherBrickBlock` |
| 113 | `nether_brick_fence` | `FenceBlock` |
| 114 | `nether_brick_stairs` | `StairsBlock` |
| 115 | `nether_wart` | `NetherWartBlock` |
| 116 | `enchanting_table` | `EnchantingTableBlock` |
| 117 | `brewing_stand` | `BrewingStandBlock` |
| 118 | `cauldron` | `CauldronBlock` |
| 119 | `end_portal` | `EndPortalBlock` |
| 120 | `end_portal_frame` | `EndPortalFrameBlock` |
| 121 | `end_stone` | `Block` |
| 122 | `dragon_egg` | `DragonEggBlock` |
| 123 | `redstone_lamp` | `RedstoneLampBlock` |
| 124 | `lit_redstone_lamp` | `RedstoneLampBlock` |
| 125 | `double_wooden_slab` | `DoubleWoodenSlabBlock` |
| 126 | `wooden_slab` | `SingleWoodenSlabBlock` |
| 127 | `cocoa` | `CocoaBlock` |
| 128 | `sandstone_stairs` | `StairsBlock` |
| 129 | `emerald_ore` | `OreBlock` |
| 130 | `ender_chest` | `EnderChestBlock` |
| 131 | `tripwire_hook` | `TripwireHookBlock` |
| 132 | `tripwire` | `TripwireBlock` |
| 133 | `emerald_block` | `Block` |
| 134 | `spruce_stairs` | `StairsBlock` |
| 135 | `birch_stairs` | `StairsBlock` |
| 136 | `jungle_stairs` | `StairsBlock` |
| 137 | `command_block` | `CommandBlock` |
| 138 | `beacon` | `BeaconBlock` |
| 139 | `cobblestone_wall` | `WallBlock` |
| 140 | `flower_pot` | `FlowerPotBlock` |
| 141 | `carrots` | `CarrotsBlock` |
| 142 | `potatoes` | `PotatoesBlock` |
| 143 | `wooden_button` | `WoodenButtonBlock` |
| 144 | `skull` | `SkullBlock` |
| 145 | `anvil` | `AnvilBlock` |
| 146 | `trapped_chest` | `ChestBlock` |
| 147 | `light_weighted_pressure_plate` | `WeightedPressurePlateBlock` |
| 148 | `heavy_weighted_pressure_plate` | `WeightedPressurePlateBlock` |
| 149 | `unpowered_comparator` | `ComparatorBlock` |
| 150 | `powered_comparator` | `ComparatorBlock` |
| 151 | `daylight_detector` | `DaylightDetectorBlock` |
| 152 | `redstone_block` | `RedstoneBlock` |
| 153 | `quartz_ore` | `OreBlock` |
| 154 | `hopper` | `HopperBlock` |
| 155 | `quartz_block` | `QuartzBlock` |
| 156 | `quartz_stairs` | `StairsBlock` |
| 157 | `activator_rail` | `PoweredRailBlock` |
| 158 | `dropper` | `DropperBlock` |
| 159 | `stained_hardened_clay` | `StainedHardenedClayBlock` |
| 160 | `stained_glass_pane` | `StainedGlassPaneBlock` |
| 161 | `leaves2` | `Leaves2Block` |
| 162 | `log2` | `Log2Block` |
| 163 | `acacia_stairs` | `StairsBlock` |
| 164 | `dark_oak_stairs` | `StairsBlock` |
| 165 | `slime` | `SlimeBlock` |
| 166 | `barrier` | `BarrierBlock` |
| 167 | `iron_trapdoor` | `TrapdoorBlock` |
| 168 | `prismarine` | `PrismarineBlock` |
| 169 | `sea_lantern` | `SeaLanternBlock` |
| 170 | `hay_block` | `HayBlock` |
| 171 | `carpet` | `CarpetBlock` |
| 172 | `hardened_clay` | `HardenedClayBlock` |
| 173 | `coal_block` | `Block` |
| 174 | `packed_ice` | `PackedIceBlock` |
| 175 | `double_plant` | `DoublePlantBlock` |
| 176 | `standing_banner` | `Standing` |
| 177 | `wall_banner` | `Wall` |
| 178 | `daylight_detector_inverted` | `DaylightDetectorBlock` |
| 179 | `red_sandstone` | `RedSandstoneBlock` |
| 180 | `red_sandstone_stairs` | `StairsBlock` |
| 181 | `double_stone_slab2` | `DoubleRedSandstoneSlabBlock` |
| 182 | `stone_slab2` | `SingleRedSandstoneSlabBlock` |
| 183 | `spruce_fence_gate` | `FenceGateBlock` |
| 184 | `birch_fence_gate` | `FenceGateBlock` |
| 185 | `jungle_fence_gate` | `FenceGateBlock` |
| 186 | `dark_oak_fence_gate` | `FenceGateBlock` |
| 187 | `acacia_fence_gate` | `FenceGateBlock` |
| 188 | `spruce_fence` | `FenceBlock` |
| 189 | `birch_fence` | `FenceBlock` |
| 190 | `jungle_fence` | `FenceBlock` |
| 191 | `dark_oak_fence` | `FenceBlock` |
| 192 | `acacia_fence` | `FenceBlock` |
| 193 | `spruce_door` | `DoorBlock` |
| 194 | `birch_door` | `DoorBlock` |
| 195 | `jungle_door` | `DoorBlock` |
| 196 | `acacia_door` | `DoorBlock` |
| 197 | `dark_oak_door` | `DoorBlock` |
| 198 | `end_rod` | `EndRodBlock` |
| 199 | `chorus_plant` | `ChorusPlantBlock` |
| 200 | `chorus_flower` | `ChorusFlowerBlock` |
| 201 | `purpur_block` | `Block` |
| 202 | `purpur_pillar` | `AxisBlock` |
| 203 | `purpur_stairs` | `StairsBlock` |
| 204 | `purpur_double_slab` | `Double` |
| 205 | `purpur_slab` | `Single` |
| 206 | `end_bricks` | `Block` |
| 207 | `beetroots` | `BeetrootsBlock` |
| 208 | `grass_path` | `GrassPathBlock` |
| 209 | `end_gateway` | `EndGatewayBlock` |
| 210 | `repeating_command_block` | `CommandBlock` |
| 211 | `chain_command_block` | `CommandBlock` |
| 212 | `frosted_ice` | `FrostedIceBlock` |
| 213 | `magma` | `MagmaBlock` |
| 214 | `nether_wart_block` | `Block` |
| 215 | `red_nether_brick` | `NetherBrickBlock` |
| 216 | `bone_block` | `BoneBlock` |
| 217 | `structure_void` | `StructureVoidBlock` |
| 218 | `observer` | `ObserverBlock` |
| 219 | `white_shulker_box` | `ShulkerBoxBlock` |
| 220 | `orange_shulker_box` | `ShulkerBoxBlock` |
| 221 | `magenta_shulker_box` | `ShulkerBoxBlock` |
| 222 | `light_blue_shulker_box` | `ShulkerBoxBlock` |
| 223 | `yellow_shulker_box` | `ShulkerBoxBlock` |
| 224 | `lime_shulker_box` | `ShulkerBoxBlock` |
| 225 | `pink_shulker_box` | `ShulkerBoxBlock` |
| 226 | `gray_shulker_box` | `ShulkerBoxBlock` |
| 227 | `silver_shulker_box` | `ShulkerBoxBlock` |
| 228 | `cyan_shulker_box` | `ShulkerBoxBlock` |
| 229 | `purple_shulker_box` | `ShulkerBoxBlock` |
| 230 | `blue_shulker_box` | `ShulkerBoxBlock` |
| 231 | `brown_shulker_box` | `ShulkerBoxBlock` |
| 232 | `green_shulker_box` | `ShulkerBoxBlock` |
| 233 | `red_shulker_box` | `ShulkerBoxBlock` |
| 234 | `black_shulker_box` | `ShulkerBoxBlock` |
| 235 | `white_glazed_terracotta` | `GlazedTerracottaBlock` |
| 236 | `orange_glazed_terracotta` | `GlazedTerracottaBlock` |
| 237 | `magenta_glazed_terracotta` | `GlazedTerracottaBlock` |
| 238 | `light_blue_glazed_terracotta` | `GlazedTerracottaBlock` |
| 239 | `yellow_glazed_terracotta` | `GlazedTerracottaBlock` |
| 240 | `lime_glazed_terracotta` | `GlazedTerracottaBlock` |
| 241 | `pink_glazed_terracotta` | `GlazedTerracottaBlock` |
| 242 | `gray_glazed_terracotta` | `GlazedTerracottaBlock` |
| 243 | `silver_glazed_terracotta` | `GlazedTerracottaBlock` |
| 244 | `cyan_glazed_terracotta` | `GlazedTerracottaBlock` |
| 245 | `purple_glazed_terracotta` | `GlazedTerracottaBlock` |
| 246 | `blue_glazed_terracotta` | `GlazedTerracottaBlock` |
| 247 | `brown_glazed_terracotta` | `GlazedTerracottaBlock` |
| 248 | `green_glazed_terracotta` | `GlazedTerracottaBlock` |
| 249 | `red_glazed_terracotta` | `GlazedTerracottaBlock` |
| 250 | `black_glazed_terracotta` | `GlazedTerracottaBlock` |
| 251 | `concrete` | `ColoredBlock` |
| 252 | `concrete_powder` | `ConcretePowderBlock` |
| 255 | `structure_block` | `StructureBlock` |

The complete source class table supplements the ID crosswalk directly above. A locally constructed object whose class could not be recovered from a preceding `Block` local is marked `local/shared expression`; no class mapping is inferred in that case.

### 1.11.2 override source-file hashes

| Class | Canonical source relative path | SHA-256 |
|---|---|---|
| `AbstractPressurePlateBlock` | `net/minecraft/block/AbstractPressurePlateBlock.java` | `A1D08F0E8E9A4C40AF49E3843E1138E51994CC2A160FB4ECC3BF26F559A04BED` |
| `AbstractRailBlock` | `net/minecraft/block/AbstractRailBlock.java` | `879E954F3EAE6CABB2A5129CBDD071E02E9AD1F235B84712DB41D2087C5DD621` |
| `AirBlock` | `net/minecraft/block/AirBlock.java` | `D46692F1982E1BE4FF4CA8B72872E10BFF8729825F986CBAD6711EAF35F76C26` |
| `AnvilBlock` | `net/minecraft/block/AnvilBlock.java` | `0E8EA6479DD3710DAFD00A6B0B62E4D96A09F5DD3650886699902414CA71A997` |
| `BannerBlock` | `net/minecraft/block/BannerBlock.java` | `CFE2E2DDE3225DC96B178A7ED2DD51A1B9E7A6E1BD5DF3F134C029428C209A3E` |
| `BeaconBlock` | `net/minecraft/block/BeaconBlock.java` | `4526CD043C9C3888B69283682807E0B08D680854652946D0C6FEAE49A961AE6E` |
| `BedBlock` | `net/minecraft/block/BedBlock.java` | `84F56AAB19ADAE4B953A27040E5F431F65C8923D6BD169995AC5A5F629A024E1` |
| `BrewingStandBlock` | `net/minecraft/block/BrewingStandBlock.java` | `D890D11F9123D2F82F3358B732F7A93464A8E3117D8AE7183FB0A66527BE5E18` |
| `ButtonBlock` | `net/minecraft/block/ButtonBlock.java` | `4F195398296E8DD512A875FF6C48159121C86BA37614E3B224EF67392F84900C` |
| `CactusBlock` | `net/minecraft/block/CactusBlock.java` | `849FBCE6B4B98826DB80652D68A3D9CB64E4AE26D7E63336C1D964AE0508C922` |
| `CakeBlock` | `net/minecraft/block/CakeBlock.java` | `C3CD4AC3F7D8345DA53D8AF9CCF0AF438331AB5465BD21AE529C752C367E8C69` |
| `CarpetBlock` | `net/minecraft/block/CarpetBlock.java` | `804BC2442040A433ECA543452DFCAA8FCF40F0339003668E8495FEDF8D9AD2DE` |
| `CauldronBlock` | `net/minecraft/block/CauldronBlock.java` | `8B95C69FEBCE0002140C2F960CFA191737DD51DABF5AB99F7E74416DD382C0E7` |
| `ChestBlock` | `net/minecraft/block/ChestBlock.java` | `719E5AC976B277AC83CB847CCB8F3F205DD3DD7361E7A45DF114B0D60BA3557D` |
| `ChorusFlowerBlock` | `net/minecraft/block/ChorusFlowerBlock.java` | `3D801969F9CA324131BCC174B9CC9EE19308F004CE29D8940B69CA2B4CC553EF` |
| `ChorusPlantBlock` | `net/minecraft/block/ChorusPlantBlock.java` | `ECD52AE5749B6112CCD58100F6E043713CF111592455B7988F5479A8FA87A8BA` |
| `CobwebBlock` | `net/minecraft/block/CobwebBlock.java` | `F458EFC121D33ACE844824D6FCDE3B2676960583C14B03E982F1AE487AA2F7C7` |
| `CocoaBlock` | `net/minecraft/block/CocoaBlock.java` | `97B1AC16965A9A6AF7E052DE9108C17CCCB9579ED08283E4C9813F288BD9CD78` |
| `DaylightDetectorBlock` | `net/minecraft/block/DaylightDetectorBlock.java` | `C0CB72AE03891AB3027F839DF833FBB2E9A1A9AE76AACC74ABED2E48A748DC02` |
| `DiodeBlock` | `net/minecraft/block/DiodeBlock.java` | `F017894413D83282AE5670CA17B1C444B73D0697BFB4C7405529E4A2DD1ACE0F` |
| `DoorBlock` | `net/minecraft/block/DoorBlock.java` | `847A07FAE13B4BEFB5204A7412F21F72DCEC45977AD3C3085F39908FE887746F` |
| `DragonEggBlock` | `net/minecraft/block/DragonEggBlock.java` | `96B3C3BA8DCF28342501820B0F91874A1642758C2C74FB7ED1C5950755CFC866` |
| `EnchantingTableBlock` | `net/minecraft/block/EnchantingTableBlock.java` | `A191FDAE900988CDA0CB0EEDA62E148DE04EEB9CBF01AEA217847686613B86A1` |
| `EnderChestBlock` | `net/minecraft/block/EnderChestBlock.java` | `3782D9DD282358301EC3908F069AAE77CC7B56F28FB0D5A6644C28DCE1CDD15D` |
| `EndGatewayBlock` | `net/minecraft/block/EndGatewayBlock.java` | `EB29B053D3DB7D606777CF1438E3068A87D7B20B5E3C5D8DC6DE3844A60593BC` |
| `EndPortalBlock` | `net/minecraft/block/EndPortalBlock.java` | `3E246CD437D5CA61758CD03E3DD258A14F84DE9121D043193016274D1B6C03D2` |
| `EndPortalFrameBlock` | `net/minecraft/block/EndPortalFrameBlock.java` | `E4E9B5AECA29EFF111A8ECCE69C7267A6BC1D0458A9E528F9522D08F58584AF4` |
| `EndRodBlock` | `net/minecraft/block/EndRodBlock.java` | `85A2E48C2B88BA81AAC678EC4EF7055A9DAC1BF370CD27FBD8CEED123C8E98B6` |
| `FarmlandBlock` | `net/minecraft/block/FarmlandBlock.java` | `09E260528FD78B4BBDBC302726C7AE937FBC49F17B674C4A7AD802E0F7B6F5E8` |
| `FenceBlock` | `net/minecraft/block/FenceBlock.java` | `856CB92D4613289747A34A6BC85F306CA9D44B7057A45C79F2020D13025D1CF7` |
| `FenceGateBlock` | `net/minecraft/block/FenceGateBlock.java` | `62CF70369634810D50137D846BE46F3901DEF38DF38EE0366DF2A0B06268BB67` |
| `FireBlock` | `net/minecraft/block/FireBlock.java` | `424B82599E6D9FFB1042AD406D0A2383F97C577668747D7DBD210BCAB0128F1E` |
| `FlowerPotBlock` | `net/minecraft/block/FlowerPotBlock.java` | `D749DA19156E319C25671FD75EA80DC25EA65CDF39B7F59E1FDC5178F5C563C4` |
| `GlassBlock` | `net/minecraft/block/GlassBlock.java` | `554A0884BFEF7BC61B4A2535193037DFB0DE0E338227AE0335DBD6432EF62471` |
| `GrassPathBlock` | `net/minecraft/block/GrassPathBlock.java` | `E513AE3A935DA418653EF09FE31F43120867E105E005B5E28DF9C32AB9C306D8` |
| `HopperBlock` | `net/minecraft/block/HopperBlock.java` | `C372402B14B648B0C2AB9EF5629ACCC9AE0E4CB5A9A78B7A0D4B7E2FA524A828` |
| `LadderBlock` | `net/minecraft/block/LadderBlock.java` | `3CEAFEDEB39E0BC3862BED2CC784F490340D832563764ADB225ECEA40EE83EE7` |
| `LeverBlock` | `net/minecraft/block/LeverBlock.java` | `36FD87A8A876A074BECE21DC750453482814FE23AB10A983416AB855EC13AC8A` |
| `LiquidBlock` | `net/minecraft/block/LiquidBlock.java` | `C91618C8445DD488DD61F4A01AED0799DAE2FAAF3585BC6F2D5164891E988244` |
| `MovingBlock` | `net/minecraft/block/MovingBlock.java` | `F2F99070CAD0B64D8ECEDE16B44E0D0383E11C5B4E0C787EDEAA2B8FC5C8AF18` |
| `PaneBlock` | `net/minecraft/block/PaneBlock.java` | `1E8968E7D642358CCE90F0D3156F07F19C5BCA28D2008D5D2D4BF0ECCC7266A9` |
| `PistonBaseBlock` | `net/minecraft/block/PistonBaseBlock.java` | `3A055F33949305827C3159BB5FBDDF8BF51D0EC942F1B30F1FA59FF1BAB9BE64` |
| `PistonHeadBlock` | `net/minecraft/block/PistonHeadBlock.java` | `DA30307ABF1E88698A220AB3B61538BE9B2F5F16D6648C8F41A2671DC06B7ED9` |
| `PlantBlock` | `net/minecraft/block/PlantBlock.java` | `26904DB8724439814CCC2100DD4AE042085ED2041A6E4CDF1FD0CCCC57AF054E` |
| `PortalBlock` | `net/minecraft/block/PortalBlock.java` | `CC1F875BD9DAAB78FD628EC9C321BE02E00F5BA2FE7A4D5246B035968E13F287` |
| `RedstoneWireBlock` | `net/minecraft/block/RedstoneWireBlock.java` | `B5A4D449DFD2E285598BD7CBAF0F3695BDFD4E1C54DF4197719B329966B10DD9` |
| `ShulkerBoxBlock` | `net/minecraft/block/ShulkerBoxBlock.java` | `C3A4082E6DFF9B59C61C0EA1FD79FCC6B349C85D4ABF47398E414C1CC52D17F5` |
| `SignBlock` | `net/minecraft/block/SignBlock.java` | `1F75A575944CF81D3B9293EE7DB64EEF9A20BF96C1297BCD7EA8ADA3467F9B7C` |
| `SkullBlock` | `net/minecraft/block/SkullBlock.java` | `38EB7C51F13E54E7FDCA757378ACD9737AE0466228228083D378DA43720B0E2A` |
| `SnowLayerBlock` | `net/minecraft/block/SnowLayerBlock.java` | `74BC424529548CDD37ABC67CD7BFFB5A1EBC7B82589C08862C5EA553DA2AD172` |
| `StainedGlassBlock` | `net/minecraft/block/StainedGlassBlock.java` | `D38CF13B537D0F04163011020A1B815C7207D334582E6E61A50A4091964C8045` |
| `StairsBlock` | `net/minecraft/block/StairsBlock.java` | `5DF1782DA6EB7ACDB9935B3910A0484D6417661BFD6230ECF81DF0DE6D782627` |
| `StructureVoidBlock` | `net/minecraft/block/StructureVoidBlock.java` | `D0D2F0A885BF5CAFDA3FDCF39A9B3BF7A083D31F0D75BA4F40EA348FF4DF7F5B` |
| `SugarCaneBlock` | `net/minecraft/block/SugarCaneBlock.java` | `3ACB3E43802238694FBC0AA5BF9BD731BA73D881600FDC252B8FFE4C12F57B88` |
| `TorchBlock` | `net/minecraft/block/TorchBlock.java` | `E3015018B125B53BC1F93F20BECBCE7EC75DBCD233C9CB85D10E014FDB6F3B7D` |
| `TrapdoorBlock` | `net/minecraft/block/TrapdoorBlock.java` | `956E2ADD467FB81D28578ECC7F0018BAC5C155217DB0B66BAFF466B8BCAA839A` |
| `TripwireBlock` | `net/minecraft/block/TripwireBlock.java` | `C14D38C7B10B6D2CCF87B26D59F243C31BBBD9969475C7E7A493445359A371EA` |
| `TripwireHookBlock` | `net/minecraft/block/TripwireHookBlock.java` | `730D8AC0130B103C44124DB0CFC5F81E1F8BE46FBC711E5C2B1AAA13CA8E62F1` |
| `VineBlock` | `net/minecraft/block/VineBlock.java` | `B06BAFD1F06A7A75EF0239E87F190AFE9709DFF05CCBEEE72144222C2C1C558E` |
| `WallBlock` | `net/minecraft/block/WallBlock.java` | `17DA6D881365E4DCE5134F88CB9A9E5273D1FA1734812D13410DC6924A3ACBFD` |
| `SlabBlock` | `net/minecraft/block/SlabBlock.java` | `15955DB0D4E2607BDF4D90FF93876BD0D0875AF3A378100225FB99B7EBB6BC8D` |

## Materialized 1.12.2 base-`SOLID` current-ID set

This derived finite set is the union of current IDs for old registered 1.12.2 block identities whose registered class inherits `Block.getFaceShape == SOLID` without an override, after removing explicit pane exclusion identities/classes, including every legacy leaves subclass and dyed shulker-box ID. The override-class names are taken from the complete `inventory.md` horizontal override list; class inheritance is followed through local source declarations. Specialized overridden providers (including slabs, stairs, pistons, shulkers, panes, fences, gates, walls, and direction-filtered classes) are deliberately absent and require their separately described historical operation. Candidate IDs were filtered against the enumerated 26.2 ID set.

| Current 26.2 ID |
|---|
| `minecraft:acacia_log` |
| `minecraft:acacia_planks` |
| `minecraft:bedrock` |
| `minecraft:birch_log` |
| `minecraft:birch_planks` |
| `minecraft:black_concrete` |
| `minecraft:black_concrete_powder` |
| `minecraft:black_glazed_terracotta` |
| `minecraft:black_stained_glass` |
| `minecraft:black_terracotta` |
| `minecraft:black_wool` |
| `minecraft:blue_concrete` |
| `minecraft:blue_concrete_powder` |
| `minecraft:blue_glazed_terracotta` |
| `minecraft:blue_stained_glass` |
| `minecraft:blue_terracotta` |
| `minecraft:blue_wool` |
| `minecraft:bone_block` |
| `minecraft:bookshelf` |
| `minecraft:bricks` |
| `minecraft:brown_concrete` |
| `minecraft:brown_concrete_powder` |
| `minecraft:brown_glazed_terracotta` |
| `minecraft:brown_mushroom_block` |
| `minecraft:brown_stained_glass` |
| `minecraft:brown_terracotta` |
| `minecraft:brown_wool` |
| `minecraft:chain_command_block` |
| `minecraft:chiseled_stone_bricks` |
| `minecraft:clay` |
| `minecraft:coal_block` |
| `minecraft:coal_ore` |
| `minecraft:cobblestone` |
| `minecraft:command_block` |
| `minecraft:cracked_stone_bricks` |
| `minecraft:crafting_table` |
| `minecraft:cyan_concrete` |
| `minecraft:cyan_concrete_powder` |
| `minecraft:cyan_glazed_terracotta` |
| `minecraft:cyan_stained_glass` |
| `minecraft:cyan_terracotta` |
| `minecraft:cyan_wool` |
| `minecraft:dark_oak_log` |
| `minecraft:dark_oak_planks` |
| `minecraft:diamond_block` |
| `minecraft:diamond_ore` |
| `minecraft:dirt` |
| `minecraft:dispenser` |
| `minecraft:dropper` |
| `minecraft:emerald_block` |
| `minecraft:emerald_ore` |
| `minecraft:end_stone` |
| `minecraft:end_stone_bricks` |
| `minecraft:frosted_ice` |
| `minecraft:furnace` |
| `minecraft:glass` |
| `minecraft:gold_block` |
| `minecraft:gold_ore` |
| `minecraft:grass_block` |
| `minecraft:gravel` |
| `minecraft:gray_concrete` |
| `minecraft:gray_concrete_powder` |
| `minecraft:gray_glazed_terracotta` |
| `minecraft:gray_stained_glass` |
| `minecraft:gray_terracotta` |
| `minecraft:gray_wool` |
| `minecraft:green_concrete` |
| `minecraft:green_concrete_powder` |
| `minecraft:green_glazed_terracotta` |
| `minecraft:green_stained_glass` |
| `minecraft:green_terracotta` |
| `minecraft:green_wool` |
| `minecraft:hay_block` |
| `minecraft:infested_chiseled_stone_bricks` |
| `minecraft:infested_cobblestone` |
| `minecraft:infested_cracked_stone_bricks` |
| `minecraft:infested_mossy_stone_bricks` |
| `minecraft:infested_stone` |
| `minecraft:infested_stone_bricks` |
| `minecraft:iron_block` |
| `minecraft:iron_ore` |
| `minecraft:jukebox` |
| `minecraft:jungle_log` |
| `minecraft:jungle_planks` |
| `minecraft:lapis_block` |
| `minecraft:lapis_ore` |
| `minecraft:light_blue_concrete` |
| `minecraft:light_blue_concrete_powder` |
| `minecraft:light_blue_glazed_terracotta` |
| `minecraft:light_blue_stained_glass` |
| `minecraft:light_blue_terracotta` |
| `minecraft:light_blue_wool` |
| `minecraft:light_gray_concrete` |
| `minecraft:light_gray_concrete_powder` |
| `minecraft:light_gray_glazed_terracotta` |
| `minecraft:light_gray_stained_glass` |
| `minecraft:light_gray_terracotta` |
| `minecraft:light_gray_wool` |
| `minecraft:lime_concrete` |
| `minecraft:lime_concrete_powder` |
| `minecraft:lime_glazed_terracotta` |
| `minecraft:lime_stained_glass` |
| `minecraft:lime_terracotta` |
| `minecraft:lime_wool` |
| `minecraft:magenta_concrete` |
| `minecraft:magenta_concrete_powder` |
| `minecraft:magenta_glazed_terracotta` |
| `minecraft:magenta_stained_glass` |
| `minecraft:magenta_terracotta` |
| `minecraft:magenta_wool` |
| `minecraft:magma_block` |
| `minecraft:mossy_cobblestone` |
| `minecraft:mossy_stone_bricks` |
| `minecraft:mycelium` |
| `minecraft:nether_bricks` |
| `minecraft:nether_quartz_ore` |
| `minecraft:nether_wart_block` |
| `minecraft:netherrack` |
| `minecraft:note_block` |
| `minecraft:oak_log` |
| `minecraft:oak_planks` |
| `minecraft:observer` |
| `minecraft:obsidian` |
| `minecraft:orange_concrete` |
| `minecraft:orange_concrete_powder` |
| `minecraft:orange_glazed_terracotta` |
| `minecraft:orange_stained_glass` |
| `minecraft:orange_terracotta` |
| `minecraft:orange_wool` |
| `minecraft:packed_ice` |
| `minecraft:pink_concrete` |
| `minecraft:pink_concrete_powder` |
| `minecraft:pink_glazed_terracotta` |
| `minecraft:pink_stained_glass` |
| `minecraft:pink_terracotta` |
| `minecraft:pink_wool` |
| `minecraft:prismarine` |
| `minecraft:purple_concrete` |
| `minecraft:purple_concrete_powder` |
| `minecraft:purple_glazed_terracotta` |
| `minecraft:purple_stained_glass` |
| `minecraft:purple_terracotta` |
| `minecraft:purple_wool` |
| `minecraft:purpur_block` |
| `minecraft:purpur_pillar` |
| `minecraft:quartz_block` |
| `minecraft:red_concrete` |
| `minecraft:red_concrete_powder` |
| `minecraft:red_glazed_terracotta` |
| `minecraft:red_mushroom_block` |
| `minecraft:red_nether_bricks` |
| `minecraft:red_sandstone` |
| `minecraft:red_stained_glass` |
| `minecraft:red_terracotta` |
| `minecraft:red_wool` |
| `minecraft:redstone_block` |
| `minecraft:redstone_lamp` |
| `minecraft:redstone_ore` |
| `minecraft:repeating_command_block` |
| `minecraft:sand` |
| `minecraft:sandstone` |
| `minecraft:slime_block` |
| `minecraft:snow_block` |
| `minecraft:soul_sand` |
| `minecraft:spawner` |
| `minecraft:sponge` |
| `minecraft:spruce_log` |
| `minecraft:spruce_planks` |
| `minecraft:stone` |
| `minecraft:stone_bricks` |
| `minecraft:structure_block` |
| `minecraft:terracotta` |
| `minecraft:tnt` |
| `minecraft:white_concrete` |
| `minecraft:white_concrete_powder` |
| `minecraft:white_glazed_terracotta` |
| `minecraft:white_stained_glass` |
| `minecraft:white_terracotta` |
| `minecraft:white_wool` |
| `minecraft:yellow_concrete` |
| `minecraft:yellow_concrete_powder` |
| `minecraft:yellow_glazed_terracotta` |
| `minecraft:yellow_stained_glass` |
| `minecraft:yellow_terracotta` |
| `minecraft:yellow_wool` |

Excluded pane identities follow the source predicate even when an exclusion has the default `SOLID` result. The 1.11.2 cube-provider table is a separate policy and must not be substituted for this 1.12.2 set. Unmapped aliases do not enter the set.
