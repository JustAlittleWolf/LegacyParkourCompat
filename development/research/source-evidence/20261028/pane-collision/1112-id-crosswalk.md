# 1.11.2 registered pane-neighbor identities and cube providers

## Scope and source basis

Source-only exact 1.11.2 registry inventory for the pane predicate in accepted `pane-neighbor-components-1.11.2.md`. Every numeric `Block.init` registration is listed with its registered class, current 26.2 vanilla ID alias(es), and the old default-state `isCube` provider result inherited by that class. Current aliases are joined by identical legacy registry key to the checked 1.12.2 crosswalk where available; changed/shared aliases are marked and candidates are verified against current 26.2 `BlockItemIds`/`BlockIds`. This is registry membership, not arbitrary-state geometry equivalence.

The historical predicate first uses the registered block object’s `defaultState().isCube()`, then explicitly accepts `block == this`, `GLASS`, `STAINED_GLASS`, `STAINED_GLASS_PANE`, or `instanceof PaneBlock`. Thus a provider `false` row is still pane-eligible only when it is one of those explicit classes/identities. `SlabBlock` is conditional on the registered default state being double. Modern-only IDs must retain native behavior.

## Complete 1.11.2 numeric Block.init registry

| Numeric ID | Legacy registry ID | Registered class | Current 26.2 ID candidate(s) | `defaultState().isCube()` provider | Pane-predicate note |
|---:|---|---|---|---|---|
| 0 | `air` | `AirBlock` | `minecraft:air` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 1 | `stone` | `StoneBlock` | `minecraft:stone` | `true (base inherited)` | Passes generic cube branch. |
| 2 | `grass` | `GrassBlock` | `minecraft:grass_block` | `true (base inherited)` | Passes generic cube branch. |
| 3 | `dirt` | `DirtBlock` | `minecraft:dirt` | `true (base inherited)` | Passes generic cube branch. |
| 4 | `cobblestone` | `Block` | `minecraft:cobblestone` | `true (base inherited)` | Passes generic cube branch. |
| 5 | `planks` | `PlanksBlock` | `minecraft:oak_planks`, `minecraft:spruce_planks`, `minecraft:birch_planks`, `minecraft:jungle_planks`, `minecraft:acacia_planks`, `minecraft:dark_oak_planks` | `true (base inherited)` | Passes generic cube branch. |
| 6 | `sapling` | `SaplingBlock` | `minecraft:oak_sapling`, `minecraft:spruce_sapling`, `minecraft:birch_sapling`, `minecraft:jungle_sapling`, `minecraft:acacia_sapling`, `minecraft:dark_oak_sapling` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 7 | `bedrock` | `BedrockBlock` | `minecraft:bedrock` | `true (base inherited)` | Passes generic cube branch. |
| 8 | `flowing_water` | `FlowingLiquidBlock` | `minecraft:water` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 9 | `water` | `LiquidSourceBlock` | `minecraft:water` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 10 | `flowing_lava` | `FlowingLiquidBlock` | `minecraft:lava` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 11 | `lava` | `LiquidSourceBlock` | `minecraft:lava` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 12 | `sand` | `SandBlock` | `minecraft:sand` | `true (base inherited)` | Passes generic cube branch. |
| 13 | `gravel` | `GravelBlock` | `minecraft:gravel` | `true (base inherited)` | Passes generic cube branch. |
| 14 | `gold_ore` | `OreBlock` | `minecraft:gold_ore` | `true (base inherited)` | Passes generic cube branch. |
| 15 | `iron_ore` | `OreBlock` | `minecraft:iron_ore` | `true (base inherited)` | Passes generic cube branch. |
| 16 | `coal_ore` | `OreBlock` | `minecraft:coal_ore` | `true (base inherited)` | Passes generic cube branch. |
| 17 | `log` | `LogBlock` | `minecraft:oak_log`, `minecraft:spruce_log`, `minecraft:birch_log`, `minecraft:jungle_log` | `true (base inherited)` | Passes generic cube branch. |
| 18 | `leaves` | `LeavesBlock` | `minecraft:oak_leaves`, `minecraft:spruce_leaves`, `minecraft:birch_leaves`, `minecraft:jungle_leaves` | `true (base inherited)` | Passes generic cube branch. |
| 19 | `sponge` | `SpongeBlock` | `minecraft:sponge` | `true (base inherited)` | Passes generic cube branch. |
| 20 | `glass` | `GlassBlock` | `minecraft:glass` | `false` | Explicit glass identity clause accepts this provider despite false. |
| 21 | `lapis_ore` | `OreBlock` | `minecraft:lapis_ore` | `true (base inherited)` | Passes generic cube branch. |
| 22 | `lapis_block` | `Block` | `minecraft:lapis_block` | `true (base inherited)` | Passes generic cube branch. |
| 23 | `dispenser` | `DispenserBlock` | `minecraft:dispenser` | `true (base inherited)` | Passes generic cube branch. |
| 24 | `sandstone` | `SandstoneBlock` | `minecraft:sandstone` | `true (base inherited)` | Passes generic cube branch. |
| 25 | `noteblock` | `NoteBlock` | `minecraft:note_block` | `true (base inherited)` | Passes generic cube branch. |
| 26 | `bed` | `BedBlock` | `minecraft:white_bed`, `minecraft:orange_bed`, `minecraft:magenta_bed`, `minecraft:light_blue_bed`, `minecraft:yellow_bed`, `minecraft:lime_bed`, `minecraft:pink_bed`, `minecraft:gray_bed`, `minecraft:light_gray_bed`, `minecraft:cyan_bed`, `minecraft:purple_bed`, `minecraft:blue_bed`, `minecraft:brown_bed`, `minecraft:green_bed`, `minecraft:red_bed`, `minecraft:black_bed` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 27 | `golden_rail` | `PoweredRailBlock` | `minecraft:powered_rail` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 28 | `detector_rail` | `DetectorRailBlock` | `minecraft:detector_rail` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 29 | `sticky_piston` | `PistonBaseBlock` | `minecraft:sticky_piston` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 30 | `web` | `CobwebBlock` | `minecraft:cobweb` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 31 | `tallgrass` | `TallPlantBlock` | `minecraft:short_grass`, `minecraft:fern` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 32 | `deadbush` | `DeadBushBlock` | `minecraft:dead_bush` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 33 | `piston` | `PistonBaseBlock` | `minecraft:piston` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 34 | `piston_head` | `PistonHeadBlock` | `minecraft:piston_head` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 35 | `wool` | `ColoredBlock` | `minecraft:white_wool`, `minecraft:orange_wool`, `minecraft:magenta_wool`, `minecraft:light_blue_wool`, `minecraft:yellow_wool`, `minecraft:lime_wool`, `minecraft:pink_wool`, `minecraft:gray_wool`, `minecraft:light_gray_wool`, `minecraft:cyan_wool`, `minecraft:purple_wool`, `minecraft:blue_wool`, `minecraft:brown_wool`, `minecraft:green_wool`, `minecraft:red_wool`, `minecraft:black_wool` | `true (base inherited)` | Passes generic cube branch. |
| 36 | `piston_extension` | `MovingBlock` | `minecraft:moving_piston` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 37 | `yellow_flower` | `YellowFlowerBlock` | `minecraft:dandelion` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 38 | `red_flower` | `RedFlowerBlock` | `minecraft:poppy`, `minecraft:blue_orchid`, `minecraft:allium`, `minecraft:azure_bluet`, `minecraft:red_tulip`, `minecraft:orange_tulip`, `minecraft:white_tulip`, `minecraft:pink_tulip`, `minecraft:oxeye_daisy` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 39 | `brown_mushroom` | `MushroomPlantBlock` | `minecraft:brown_mushroom` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 40 | `red_mushroom` | `MushroomPlantBlock` | `minecraft:red_mushroom` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 41 | `gold_block` | `Block` | `minecraft:gold_block` | `true (base inherited)` | Passes generic cube branch. |
| 42 | `iron_block` | `Block` | `minecraft:iron_block` | `true (base inherited)` | Passes generic cube branch. |
| 43 | `double_stone_slab` | `DoubleStoneSlabBlock` | `minecraft:stone_slab`, `minecraft:sandstone_slab`, `minecraft:petrified_oak_slab`, `minecraft:cobblestone_slab`, `minecraft:brick_slab`, `minecraft:stone_brick_slab`, `minecraft:nether_brick_slab`, `minecraft:quartz_slab` | `this.isDouble()` | Passes only when this registered slab block default is double. |
| 44 | `stone_slab` | `SingleStoneSlabBlock` | `minecraft:stone_slab`, `minecraft:sandstone_slab`, `minecraft:petrified_oak_slab`, `minecraft:cobblestone_slab`, `minecraft:brick_slab`, `minecraft:stone_brick_slab`, `minecraft:nether_brick_slab`, `minecraft:quartz_slab` | `this.isDouble()` | Passes only when this registered slab block default is double. |
| 45 | `brick_block` | `Block` | `minecraft:bricks` | `true (base inherited)` | Passes generic cube branch. |
| 46 | `tnt` | `TntBlock` | `minecraft:tnt` | `true (base inherited)` | Passes generic cube branch. |
| 47 | `bookshelf` | `BookshelfBlock` | `minecraft:bookshelf` | `true (base inherited)` | Passes generic cube branch. |
| 48 | `mossy_cobblestone` | `Block` | `minecraft:mossy_cobblestone` | `true (base inherited)` | Passes generic cube branch. |
| 49 | `obsidian` | `ObsidianBlock` | `minecraft:obsidian` | `true (base inherited)` | Passes generic cube branch. |
| 50 | `torch` | `TorchBlock` | `minecraft:torch` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 51 | `fire` | `FireBlock` | `minecraft:fire` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 52 | `mob_spawner` | `MobSpawnerBlock` | `minecraft:spawner` | `true (base inherited)` | Passes generic cube branch. |
| 53 | `oak_stairs` | `StairsBlock` | `minecraft:oak_stairs` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 54 | `chest` | `ChestBlock` | `minecraft:chest` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 55 | `redstone_wire` | `RedstoneWireBlock` | `minecraft:redstone_wire` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 56 | `diamond_ore` | `OreBlock` | `minecraft:diamond_ore` | `true (base inherited)` | Passes generic cube branch. |
| 57 | `diamond_block` | `Block` | `minecraft:diamond_block` | `true (base inherited)` | Passes generic cube branch. |
| 58 | `crafting_table` | `CraftingTableBlock` | `minecraft:crafting_table` | `true (base inherited)` | Passes generic cube branch. |
| 59 | `wheat` | `WheatBlock` | `minecraft:wheat` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 60 | `farmland` | `FarmlandBlock` | `minecraft:farmland` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 61 | `furnace` | `FurnaceBlock` | `minecraft:furnace` | `true (base inherited)` | Passes generic cube branch. |
| 62 | `lit_furnace` | `FurnaceBlock` | `minecraft:furnace` | `true (base inherited)` | Passes generic cube branch. |
| 63 | `standing_sign` | `StandingSignBlock` | `minecraft:oak_sign`, `minecraft:spruce_sign`, `minecraft:birch_sign`, `minecraft:jungle_sign`, `minecraft:acacia_sign`, `minecraft:dark_oak_sign` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 64 | `wooden_door` | `DoorBlock` | `minecraft:oak_door` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 65 | `ladder` | `LadderBlock` | `minecraft:ladder` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 66 | `rail` | `RailBlock` | `minecraft:rail` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 67 | `stone_stairs` | `StairsBlock` | `minecraft:stone_stairs` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 68 | `wall_sign` | `WallSignBlock` | `minecraft:oak_wall_sign`, `minecraft:spruce_wall_sign`, `minecraft:birch_wall_sign`, `minecraft:jungle_wall_sign`, `minecraft:acacia_wall_sign`, `minecraft:dark_oak_wall_sign` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 69 | `lever` | `LeverBlock` | `minecraft:lever` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 70 | `stone_pressure_plate` | `PressurePlateBlock` | `minecraft:stone_pressure_plate` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 71 | `iron_door` | `DoorBlock` | `minecraft:iron_door` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 72 | `wooden_pressure_plate` | `PressurePlateBlock` | `minecraft:oak_pressure_plate` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 73 | `redstone_ore` | `RedstoneOreBlock` | `minecraft:redstone_ore` | `true (base inherited)` | Passes generic cube branch. |
| 74 | `lit_redstone_ore` | `RedstoneOreBlock` | `minecraft:redstone_ore` | `true (base inherited)` | Passes generic cube branch. |
| 75 | `unlit_redstone_torch` | `RedstoneTorchBlock` | `minecraft:redstone_torch`, `minecraft:redstone_wall_torch` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 76 | `redstone_torch` | `RedstoneTorchBlock` | `minecraft:redstone_torch`, `minecraft:redstone_wall_torch` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 77 | `stone_button` | `StoneButtonBlock` | `minecraft:stone_button` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 78 | `snow_layer` | `SnowLayerBlock` | `minecraft:snow` | `false` | Snow layer maps to current snow with its layer-count property; old isCube=false provider fails the generic pane clause. |
| 79 | `ice` | `IceBlock` | `minecraft:ice` | `true (base inherited)` | Passes generic cube branch. |
| 80 | `snow` | `SnowBlock` | `minecraft:snow_block` | `true (base inherited)` | Full snow block maps to current snow_block; the old SnowBlock inherits isCube=true. |
| 81 | `cactus` | `CactusBlock` | `minecraft:cactus` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 82 | `clay` | `ClayBlock` | `minecraft:clay` | `true (base inherited)` | Passes generic cube branch. |
| 83 | `reeds` | `SugarCaneBlock` | `minecraft:sugar_cane` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 84 | `jukebox` | `JukeboxBlock` | `minecraft:jukebox` | `true (base inherited)` | Passes generic cube branch. |
| 85 | `fence` | `FenceBlock` | `minecraft:oak_fence` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 86 | `pumpkin` | `PumpkinBlock` | `minecraft:pumpkin` | `true (base inherited)` | Passes generic cube branch. |
| 87 | `netherrack` | `NetherrackBlock` | `minecraft:netherrack` | `true (base inherited)` | Passes generic cube branch. |
| 88 | `soul_sand` | `SoulSandBlock` | `minecraft:soul_sand` | `true (base inherited)` | Passes generic cube branch. |
| 89 | `glowstone` | `GlowstoneBlock` | `minecraft:glowstone` | `true (base inherited)` | Passes generic cube branch. |
| 90 | `portal` | `PortalBlock` | `minecraft:nether_portal` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 91 | `lit_pumpkin` | `PumpkinBlock` | `minecraft:jack_o_lantern` | `true (base inherited)` | Passes generic cube branch. |
| 92 | `cake` | `CakeBlock` | `minecraft:cake` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 93 | `unpowered_repeater` | `RepeaterBlock` | `minecraft:repeater` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 94 | `powered_repeater` | `RepeaterBlock` | `minecraft:repeater` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 95 | `stained_glass` | `StainedGlassBlock` | `minecraft:white_stained_glass`, `minecraft:orange_stained_glass`, `minecraft:magenta_stained_glass`, `minecraft:light_blue_stained_glass`, `minecraft:yellow_stained_glass`, `minecraft:lime_stained_glass`, `minecraft:pink_stained_glass`, `minecraft:gray_stained_glass`, `minecraft:light_gray_stained_glass`, `minecraft:cyan_stained_glass`, `minecraft:purple_stained_glass`, `minecraft:blue_stained_glass`, `minecraft:brown_stained_glass`, `minecraft:green_stained_glass`, `minecraft:red_stained_glass`, `minecraft:black_stained_glass` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 96 | `trapdoor` | `TrapdoorBlock` | `minecraft:oak_trapdoor` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 97 | `monster_egg` | `InfestedBlock` | `minecraft:infested_stone`, `minecraft:infested_cobblestone`, `minecraft:infested_stone_bricks`, `minecraft:infested_mossy_stone_bricks`, `minecraft:infested_cracked_stone_bricks`, `minecraft:infested_chiseled_stone_bricks` | `true (base inherited)` | Passes generic cube branch. |
| 98 | `stonebrick` | `StonebrickBlock` | `minecraft:stone_bricks`, `minecraft:mossy_stone_bricks`, `minecraft:cracked_stone_bricks`, `minecraft:chiseled_stone_bricks` | `true (base inherited)` | Passes generic cube branch. |
| 99 | `brown_mushroom_block` | `MushroomBlock` | `minecraft:brown_mushroom_block` | `true (base inherited)` | Passes generic cube branch. |
| 100 | `red_mushroom_block` | `MushroomBlock` | `minecraft:red_mushroom_block` | `true (base inherited)` | Passes generic cube branch. |
| 101 | `iron_bars` | `PaneBlock` | `minecraft:iron_bars` | `false` | Explicit pane class clause accepts this provider despite false. |
| 102 | `glass_pane` | `PaneBlock` | `minecraft:glass_pane` | `false` | Explicit pane class clause accepts this provider despite false. |
| 103 | `melon_block` | `MelonBlock` | `minecraft:melon` | `true (base inherited)` | Passes generic cube branch. |
| 104 | `pumpkin_stem` | `StemBlock` | `minecraft:pumpkin_stem` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 105 | `melon_stem` | `StemBlock` | `minecraft:melon_stem` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 106 | `vine` | `VineBlock` | `minecraft:vine` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 107 | `fence_gate` | `FenceGateBlock` | `minecraft:oak_fence_gate` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 108 | `brick_stairs` | `StairsBlock` | `minecraft:brick_stairs` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 109 | `stone_brick_stairs` | `StairsBlock` | `minecraft:stone_brick_stairs` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 110 | `mycelium` | `MyceliumBlock` | `minecraft:mycelium` | `true (base inherited)` | Passes generic cube branch. |
| 111 | `waterlily` | `LilyPadBlock` | `minecraft:lily_pad` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 112 | `nether_brick` | `NetherBrickBlock` | `minecraft:nether_bricks` | `true (base inherited)` | Passes generic cube branch. |
| 113 | `nether_brick_fence` | `FenceBlock` | `minecraft:nether_brick_fence` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 114 | `nether_brick_stairs` | `StairsBlock` | `minecraft:nether_brick_stairs` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 115 | `nether_wart` | `NetherWartBlock` | `minecraft:nether_wart` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 116 | `enchanting_table` | `EnchantingTableBlock` | `minecraft:enchanting_table` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 117 | `brewing_stand` | `BrewingStandBlock` | `minecraft:brewing_stand` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 118 | `cauldron` | `CauldronBlock` | `minecraft:cauldron` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 119 | `end_portal` | `EndPortalBlock` | `minecraft:end_portal` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 120 | `end_portal_frame` | `EndPortalFrameBlock` | `minecraft:end_portal_frame` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 121 | `end_stone` | `Block` | `minecraft:end_stone` | `true (base inherited)` | Passes generic cube branch. |
| 122 | `dragon_egg` | `DragonEggBlock` | `minecraft:dragon_egg` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 123 | `redstone_lamp` | `RedstoneLampBlock` | `minecraft:redstone_lamp` | `true (base inherited)` | Passes generic cube branch. |
| 124 | `lit_redstone_lamp` | `RedstoneLampBlock` | `minecraft:redstone_lamp` | `true (base inherited)` | Passes generic cube branch. |
| 125 | `double_wooden_slab` | `DoubleWoodenSlabBlock` | `minecraft:oak_slab`, `minecraft:spruce_slab`, `minecraft:birch_slab`, `minecraft:jungle_slab`, `minecraft:acacia_slab`, `minecraft:dark_oak_slab` | `this.isDouble()` | Passes only when this registered slab block default is double. |
| 126 | `wooden_slab` | `SingleWoodenSlabBlock` | `minecraft:oak_slab`, `minecraft:spruce_slab`, `minecraft:birch_slab`, `minecraft:jungle_slab`, `minecraft:acacia_slab`, `minecraft:dark_oak_slab` | `this.isDouble()` | Passes only when this registered slab block default is double. |
| 127 | `cocoa` | `CocoaBlock` | `minecraft:cocoa` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 128 | `sandstone_stairs` | `StairsBlock` | `minecraft:sandstone_stairs` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 129 | `emerald_ore` | `OreBlock` | `minecraft:emerald_ore` | `true (base inherited)` | Passes generic cube branch. |
| 130 | `ender_chest` | `EnderChestBlock` | `minecraft:ender_chest` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 131 | `tripwire_hook` | `TripwireHookBlock` | `minecraft:tripwire_hook` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 132 | `tripwire` | `TripwireBlock` | `minecraft:tripwire` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 133 | `emerald_block` | `Block` | `minecraft:emerald_block` | `true (base inherited)` | Passes generic cube branch. |
| 134 | `spruce_stairs` | `StairsBlock` | `minecraft:spruce_stairs` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 135 | `birch_stairs` | `StairsBlock` | `minecraft:birch_stairs` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 136 | `jungle_stairs` | `StairsBlock` | `minecraft:jungle_stairs` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 137 | `command_block` | `CommandBlock` | `minecraft:command_block` | `true (base inherited)` | Passes generic cube branch. |
| 138 | `beacon` | `BeaconBlock` | `minecraft:beacon` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 139 | `cobblestone_wall` | `WallBlock` | `minecraft:cobblestone_wall` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 140 | `flower_pot` | `FlowerPotBlock` | `minecraft:flower_pot` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 141 | `carrots` | `CarrotsBlock` | `minecraft:carrots` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 142 | `potatoes` | `PotatoesBlock` | `minecraft:potatoes` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 143 | `wooden_button` | `WoodenButtonBlock` | `minecraft:oak_button` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 144 | `skull` | `SkullBlock` | `minecraft:skeleton_skull` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 145 | `anvil` | `AnvilBlock` | `minecraft:anvil` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 146 | `trapped_chest` | `ChestBlock` | `minecraft:trapped_chest` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 147 | `light_weighted_pressure_plate` | `WeightedPressurePlateBlock` | `minecraft:light_weighted_pressure_plate` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 148 | `heavy_weighted_pressure_plate` | `WeightedPressurePlateBlock` | `minecraft:heavy_weighted_pressure_plate` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 149 | `unpowered_comparator` | `ComparatorBlock` | `minecraft:comparator` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 150 | `powered_comparator` | `ComparatorBlock` | `minecraft:comparator` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 151 | `daylight_detector` | `DaylightDetectorBlock` | `minecraft:daylight_detector` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 152 | `redstone_block` | `RedstoneBlock` | `minecraft:redstone_block` | `true (base inherited)` | Passes generic cube branch. |
| 153 | `quartz_ore` | `OreBlock` | `minecraft:nether_quartz_ore` | `true (base inherited)` | Passes generic cube branch. |
| 154 | `hopper` | `HopperBlock` | `minecraft:hopper` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 155 | `quartz_block` | `QuartzBlock` | `minecraft:quartz_block` | `true (base inherited)` | Passes generic cube branch. |
| 156 | `quartz_stairs` | `StairsBlock` | `minecraft:quartz_stairs` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 157 | `activator_rail` | `PoweredRailBlock` | `minecraft:activator_rail` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 158 | `dropper` | `DropperBlock` | `minecraft:dropper` | `true (base inherited)` | Passes generic cube branch. |
| 159 | `stained_hardened_clay` | `ColoredBlock` | `minecraft:white_terracotta`, `minecraft:orange_terracotta`, `minecraft:magenta_terracotta`, `minecraft:light_blue_terracotta`, `minecraft:yellow_terracotta`, `minecraft:lime_terracotta`, `minecraft:pink_terracotta`, `minecraft:gray_terracotta`, `minecraft:light_gray_terracotta`, `minecraft:cyan_terracotta`, `minecraft:purple_terracotta`, `minecraft:blue_terracotta`, `minecraft:brown_terracotta`, `minecraft:green_terracotta`, `minecraft:red_terracotta`, `minecraft:black_terracotta` | `true (base inherited)` | Passes generic cube branch. |
| 160 | `stained_glass_pane` | `StainedGlassPaneBlock` | `minecraft:white_stained_glass_pane`, `minecraft:orange_stained_glass_pane`, `minecraft:magenta_stained_glass_pane`, `minecraft:light_blue_stained_glass_pane`, `minecraft:yellow_stained_glass_pane`, `minecraft:lime_stained_glass_pane`, `minecraft:pink_stained_glass_pane`, `minecraft:gray_stained_glass_pane`, `minecraft:light_gray_stained_glass_pane`, `minecraft:cyan_stained_glass_pane`, `minecraft:purple_stained_glass_pane`, `minecraft:blue_stained_glass_pane`, `minecraft:brown_stained_glass_pane`, `minecraft:green_stained_glass_pane`, `minecraft:red_stained_glass_pane`, `minecraft:black_stained_glass_pane` | `false` | Explicit pane class clause accepts this provider despite false. |
| 161 | `leaves2` | `Leaves2Block` | `minecraft:acacia_leaves`, `minecraft:dark_oak_leaves` | `true (base inherited)` | Passes generic cube branch. |
| 162 | `log2` | `Log2Block` | `minecraft:acacia_log`, `minecraft:dark_oak_log` | `true (base inherited)` | Passes generic cube branch. |
| 163 | `acacia_stairs` | `StairsBlock` | `minecraft:acacia_stairs` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 164 | `dark_oak_stairs` | `StairsBlock` | `minecraft:dark_oak_stairs` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 165 | `slime` | `SlimeBlock` | `minecraft:slime_block` | `true (base inherited)` | Passes generic cube branch. |
| 166 | `barrier` | `BarrierBlock` | `minecraft:barrier` | `true (base inherited)` | Passes generic cube branch. |
| 167 | `iron_trapdoor` | `TrapdoorBlock` | `minecraft:iron_trapdoor` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 168 | `prismarine` | `PrismarineBlock` | `minecraft:prismarine` | `true (base inherited)` | Passes generic cube branch. |
| 169 | `sea_lantern` | `SeaLanternBlock` | `minecraft:sea_lantern` | `true (base inherited)` | Passes generic cube branch. |
| 170 | `hay_block` | `HayBlock` | `minecraft:hay_block` | `true (base inherited)` | Passes generic cube branch. |
| 171 | `carpet` | `CarpetBlock` | `minecraft:white_carpet`, `minecraft:orange_carpet`, `minecraft:magenta_carpet`, `minecraft:light_blue_carpet`, `minecraft:yellow_carpet`, `minecraft:lime_carpet`, `minecraft:pink_carpet`, `minecraft:gray_carpet`, `minecraft:light_gray_carpet`, `minecraft:cyan_carpet`, `minecraft:purple_carpet`, `minecraft:blue_carpet`, `minecraft:brown_carpet`, `minecraft:green_carpet`, `minecraft:red_carpet`, `minecraft:black_carpet` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 172 | `hardened_clay` | `HardenedClayBlock` | `minecraft:terracotta` | `true (base inherited)` | Passes generic cube branch. |
| 173 | `coal_block` | `Block` | `minecraft:coal_block` | `true (base inherited)` | Passes generic cube branch. |
| 174 | `packed_ice` | `PackedIceBlock` | `minecraft:packed_ice` | `true (base inherited)` | Passes generic cube branch. |
| 175 | `double_plant` | `DoublePlantBlock` | `minecraft:sunflower`, `minecraft:lilac`, `minecraft:tall_grass`, `minecraft:large_fern`, `minecraft:rose_bush`, `minecraft:peony` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 176 | `standing_banner` | `Standing` | `minecraft:white_banner`, `minecraft:orange_banner`, `minecraft:magenta_banner`, `minecraft:light_blue_banner`, `minecraft:yellow_banner`, `minecraft:lime_banner`, `minecraft:pink_banner`, `minecraft:gray_banner`, `minecraft:light_gray_banner`, `minecraft:cyan_banner`, `minecraft:purple_banner`, `minecraft:blue_banner`, `minecraft:brown_banner`, `minecraft:green_banner`, `minecraft:red_banner`, `minecraft:black_banner` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 177 | `wall_banner` | `Wall` | `minecraft:white_wall_banner`, `minecraft:orange_wall_banner`, `minecraft:magenta_wall_banner`, `minecraft:light_blue_wall_banner`, `minecraft:yellow_wall_banner`, `minecraft:lime_wall_banner`, `minecraft:pink_wall_banner`, `minecraft:gray_wall_banner`, `minecraft:light_gray_wall_banner`, `minecraft:cyan_wall_banner`, `minecraft:purple_wall_banner`, `minecraft:blue_wall_banner`, `minecraft:brown_wall_banner`, `minecraft:green_wall_banner`, `minecraft:red_wall_banner`, `minecraft:black_wall_banner` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 178 | `daylight_detector_inverted` | `DaylightDetectorBlock` | `minecraft:daylight_detector` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 179 | `red_sandstone` | `RedSandstoneBlock` | `minecraft:red_sandstone` | `true (base inherited)` | Passes generic cube branch. |
| 180 | `red_sandstone_stairs` | `StairsBlock` | `minecraft:red_sandstone_stairs` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 181 | `double_stone_slab2` | `DoubleRedSandstoneSlabBlock` | `minecraft:red_sandstone_slab` | `this.isDouble()` | Passes only when this registered slab block default is double. |
| 182 | `stone_slab2` | `SingleRedSandstoneSlabBlock` | `minecraft:red_sandstone_slab` | `this.isDouble()` | Passes only when this registered slab block default is double. |
| 183 | `spruce_fence_gate` | `FenceGateBlock` | `minecraft:spruce_fence_gate` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 184 | `birch_fence_gate` | `FenceGateBlock` | `minecraft:birch_fence_gate` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 185 | `jungle_fence_gate` | `FenceGateBlock` | `minecraft:jungle_fence_gate` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 186 | `dark_oak_fence_gate` | `FenceGateBlock` | `minecraft:dark_oak_fence_gate` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 187 | `acacia_fence_gate` | `FenceGateBlock` | `minecraft:acacia_fence_gate` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 188 | `spruce_fence` | `FenceBlock` | `minecraft:spruce_fence` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 189 | `birch_fence` | `FenceBlock` | `minecraft:birch_fence` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 190 | `jungle_fence` | `FenceBlock` | `minecraft:jungle_fence` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 191 | `dark_oak_fence` | `FenceBlock` | `minecraft:dark_oak_fence` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 192 | `acacia_fence` | `FenceBlock` | `minecraft:acacia_fence` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 193 | `spruce_door` | `DoorBlock` | `minecraft:spruce_door` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 194 | `birch_door` | `DoorBlock` | `minecraft:birch_door` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 195 | `jungle_door` | `DoorBlock` | `minecraft:jungle_door` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 196 | `acacia_door` | `DoorBlock` | `minecraft:acacia_door` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 197 | `dark_oak_door` | `DoorBlock` | `minecraft:dark_oak_door` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 198 | `end_rod` | `EndRodBlock` | `minecraft:end_rod` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 199 | `chorus_plant` | `ChorusPlantBlock` | `minecraft:chorus_plant` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 200 | `chorus_flower` | `ChorusFlowerBlock` | `minecraft:chorus_flower` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 201 | `purpur_block` | `Block` | `minecraft:purpur_block` | `true (base inherited)` | Passes generic cube branch. |
| 202 | `purpur_pillar` | `AxisBlock` | `minecraft:purpur_pillar` | `true (base inherited)` | Passes generic cube branch. |
| 203 | `purpur_stairs` | `StairsBlock` | `minecraft:purpur_stairs` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 204 | `purpur_double_slab` | `Double` | `minecraft:purpur_slab` | `this.isDouble()` | Passes only when this registered slab block default is double. |
| 205 | `purpur_slab` | `Single` | `minecraft:purpur_slab` | `this.isDouble()` | Passes only when this registered slab block default is double. |
| 206 | `end_bricks` | `Block` | `minecraft:end_stone_bricks` | `true (base inherited)` | Passes generic cube branch. |
| 207 | `beetroots` | `BeetrootsBlock` | `minecraft:beetroots` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 208 | `grass_path` | `GrassPathBlock` | `minecraft:dirt_path` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 209 | `end_gateway` | `EndGatewayBlock` | `minecraft:end_gateway` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 210 | `repeating_command_block` | `CommandBlock` | `minecraft:repeating_command_block` | `true (base inherited)` | Passes generic cube branch. |
| 211 | `chain_command_block` | `CommandBlock` | `minecraft:chain_command_block` | `true (base inherited)` | Passes generic cube branch. |
| 212 | `frosted_ice` | `FrostedIceBlock` | `minecraft:frosted_ice` | `true (base inherited)` | Passes generic cube branch. |
| 213 | `magma` | `MagmaBlock` | `minecraft:magma_block` | `true (base inherited)` | Passes generic cube branch. |
| 214 | `nether_wart_block` | `Block` | `minecraft:nether_wart_block` | `true (base inherited)` | Passes generic cube branch. |
| 215 | `red_nether_brick` | `NetherBrickBlock` | `minecraft:red_nether_bricks` | `true (base inherited)` | Passes generic cube branch. |
| 216 | `bone_block` | `BoneBlock` | `minecraft:bone_block` | `true (base inherited)` | Passes generic cube branch. |
| 217 | `structure_void` | `StructureVoidBlock` | `minecraft:structure_void` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 218 | `observer` | `ObserverBlock` | `minecraft:observer` | `true (base inherited)` | Passes generic cube branch. |
| 219 | `white_shulker_box` | `ShulkerBoxBlock` | `minecraft:white_shulker_box` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 220 | `orange_shulker_box` | `ShulkerBoxBlock` | `minecraft:orange_shulker_box` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 221 | `magenta_shulker_box` | `ShulkerBoxBlock` | `minecraft:magenta_shulker_box` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 222 | `light_blue_shulker_box` | `ShulkerBoxBlock` | `minecraft:light_blue_shulker_box` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 223 | `yellow_shulker_box` | `ShulkerBoxBlock` | `minecraft:yellow_shulker_box` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 224 | `lime_shulker_box` | `ShulkerBoxBlock` | `minecraft:lime_shulker_box` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 225 | `pink_shulker_box` | `ShulkerBoxBlock` | `minecraft:pink_shulker_box` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 226 | `gray_shulker_box` | `ShulkerBoxBlock` | `minecraft:gray_shulker_box` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 227 | `silver_shulker_box` | `ShulkerBoxBlock` | `minecraft:light_gray_shulker_box` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 228 | `cyan_shulker_box` | `ShulkerBoxBlock` | `minecraft:cyan_shulker_box` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 229 | `purple_shulker_box` | `ShulkerBoxBlock` | `minecraft:purple_shulker_box` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 230 | `blue_shulker_box` | `ShulkerBoxBlock` | `minecraft:blue_shulker_box` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 231 | `brown_shulker_box` | `ShulkerBoxBlock` | `minecraft:brown_shulker_box` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 232 | `green_shulker_box` | `ShulkerBoxBlock` | `minecraft:green_shulker_box` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 233 | `red_shulker_box` | `ShulkerBoxBlock` | `minecraft:red_shulker_box` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 234 | `black_shulker_box` | `ShulkerBoxBlock` | `minecraft:black_shulker_box` | `false` | Fails generic cube branch unless an explicit pane/glass clause applies. |
| 255 | `structure_block` | `StructureBlock` | `minecraft:structure_block` | `true (base inherited)` | Passes generic cube branch. |

Parsed 236 numeric registrations. Candidate values are constrained to current registry IDs, so IDs introduced after 1.11.2 cannot enter this table unless they are explicit aliases for an old registry identity. Aliases reused from the 1.12.2 crosswalk cover shared registry spellings and renamed/split counterparts; changed 1.11.2 names with no verified counterpart remain `unmapped`.

## Exact direct `isCube` override providers

| Class | Provider result | Source line | Source SHA-256 |
|---|---|---:|---|
| `AbstractPressurePlateBlock` | `false` | 55 | `A1D08F0E8E9A4C40AF49E3843E1138E51994CC2A160FB4ECC3BF26F559A04BED` |
| `AbstractRailBlock` | `false` | 57 | `879E954F3EAE6CABB2A5129CBDD071E02E9AD1F235B84712DB41D2087C5DD621` |
| `AirBlock` | `false` | 48 | `D46692F1982E1BE4FF4CA8B72872E10BFF8729825F986CBAD6711EAF35F76C26` |
| `AnvilBlock` | `false` | 44 | `0E8EA6479DD3710DAFD00A6B0B62E4D96A09F5DD3650886699902414CA71A997` |
| `BannerBlock` | `false` | 44 | `CFE2E2DDE3225DC96B178A7ED2DD51A1B9E7A6E1BD5DF3F134C029428C209A3E` |
| `BeaconBlock` | `false` | 58 | `4526CD043C9C3888B69283682807E0B08D680854652946D0C6FEAE49A961AE6E` |
| `BedBlock` | `false` | 104 | `84F56AAB19ADAE4B953A27040E5F431F65C8923D6BD169995AC5A5F629A024E1` |
| `BrewingStandBlock` | `false` | 65 | `D890D11F9123D2F82F3358B732F7A93464A8E3117D8AE7183FB0A66527BE5E18` |
| `ButtonBlock` | `false` | 63 | `4F195398296E8DD512A875FF6C48159121C86BA37614E3B224EF67392F84900C` |
| `CactusBlock` | `false` | 65 | `849FBCE6B4B98826DB80652D68A3D9CB64E4AE26D7E63336C1D964AE0508C922` |
| `CakeBlock` | `false` | 45 | `C3CD4AC3F7D8345DA53D8AF9CCF0AF438331AB5465BD21AE529C752C367E8C69` |
| `CarpetBlock` | `false` | 46 | `804BC2442040A433ECA543452DFCAA8FCF40F0339003668E8495FEDF8D9AD2DE` |
| `CauldronBlock` | `false` | 66 | `8B95C69FEBCE0002140C2F960CFA191737DD51DABF5AB99F7E74416DD382C0E7` |
| `ChestBlock` | `false` | 53 | `719E5AC976B277AC83CB847CCB8F3F205DD3DD7361E7A45DF114B0D60BA3557D` |
| `ChorusFlowerBlock` | `false` | 131 | `3D801969F9CA324131BCC174B9CC9EE19308F004CE29D8940B69CA2B4CC553EF` |
| `ChorusPlantBlock` | `false` | 122 | `ECD52AE5749B6112CCD58100F6E043713CF111592455B7988F5479A8FA87A8BA` |
| `CobwebBlock` | `false` | 44 | `F458EFC121D33ACE844824D6FCDE3B2676960583C14B03E982F1AE487AA2F7C7` |
| `CocoaBlock` | `false` | 67 | `97B1AC16965A9A6AF7E052DE9108C17CCCB9579ED08283E4C9813F288BD9CD78` |
| `DaylightDetectorBlock` | `false` | 107 | `C0CB72AE03891AB3027F839DF833FBB2E9A1A9AE76AACC74ABED2E48A748DC02` |
| `DiodeBlock` | `false` | 30 | `F017894413D83282AE5670CA17B1C444B73D0697BFB4C7405529E4A2DD1ACE0F` |
| `DoorBlock` | `false` | 85 | `847A07FAE13B4BEFB5204A7412F21F72DCEC45977AD3C3085F39908FE887746F` |
| `DragonEggBlock` | `false` | 120 | `96B3C3BA8DCF28342501820B0F91874A1642758C2C74FB7ED1C5950755CFC866` |
| `EnchantingTableBlock` | `false` | 37 | `A191FDAE900988CDA0CB0EEDA62E148DE04EEB9CBF01AEA217847686613B86A1` |
| `EndGatewayBlock` | `false` | 48 | `EB29B053D3DB7D606777CF1438E3068A87D7B20B5E3C5D8DC6DE3844A60593BC` |
| `EndPortalBlock` | `false` | 53 | `3E246CD437D5CA61758CD03E3DD258A14F84DE9121D043193016274D1B6C03D2` |
| `EndPortalFrameBlock` | `false` | 109 | `E4E9B5AECA29EFF111A8ECCE69C7267A6BC1D0458A9E528F9522D08F58584AF4` |
| `EndRodBlock` | `false` | 58 | `85A2E48C2B88BA81AAC678EC4EF7055A9DAC1BF370CD27FBD8CEED123C8E98B6` |
| `EnderChestBlock` | `false` | 47 | `3782D9DD282358301EC3908F069AAE77CC7B56F28FB0D5A6644C28DCE1CDD15D` |
| `FarmlandBlock` | `false` | 40 | `09E260528FD78B4BBDBC302726C7AE937FBC49F17B674C4A7AD802E0F7B6F5E8` |
| `FenceBlock` | `false` | 115 | `856CB92D4613289747A34A6BC85F306CA9D44B7057A45C79F2020D13025D1CF7` |
| `FenceGateBlock` | `false` | 95 | `62CF70369634810D50137D846BE46F3901DEF38DF38EE0366DF2A0B06268BB67` |
| `FireBlock` | `false` | 108 | `424B82599E6D9FFB1042AD406D0A2383F97C577668747D7DBD210BCAB0128F1E` |
| `FlowerPotBlock` | `false` | 62 | `D749DA19156E319C25671FD75EA80DC25EA65CDF39B7F59E1FDC5178F5C563C4` |
| `GlassBlock` | `false` | 26 | `554A0884BFEF7BC61B4A2535193037DFB0DE0E338227AE0335DBD6432EF62471` |
| `GrassPathBlock` | `false` | 62 | `E513AE3A935DA418653EF09FE31F43120867E105E005B5E28DF9C32AB9C306D8` |
| `HopperBlock` | `false` | 146 | `C372402B14B648B0C2AB9EF5629ACCC9AE0E4CB5A9A78B7A0D4B7E2FA524A828` |
| `LadderBlock` | `false` | 50 | `3CEAFEDEB39E0BC3862BED2CC784F490340D832563764ADB225ECEA40EE83EE7` |
| `LeverBlock` | `false` | 50 | `36FD87A8A876A074BECE21DC750453482814FE23AB10A983416AB855EC13AC8A` |
| `LiquidBlock` | `false` | 68 | `C91618C8445DD488DD61F4A01AED0799DAE2FAAF3585BC6F2D5164891E988244` |
| `MovingBlock` | `false` | 82 | `F2F99070CAD0B64D8ECEDE16B44E0D0383E11C5B4E0C787EDEAA2B8FC5C8AF18` |
| `PaneBlock` | `false` | 127 | `1E8968E7D642358CCE90F0D3156F07F19C5BCA28D2008D5D2D4BF0ECCC7266A9` |
| `PistonBaseBlock` | `false` | 220 | `3A055F33949305827C3159BB5FBDDF8BF51D0EC942F1B30F1FA59FF1BAB9BE64` |
| `PistonHeadBlock` | `false` | 131 | `DA30307ABF1E88698A220AB3B61538BE9B2F5F16D6648C8F41A2671DC06B7ED9` |
| `PlantBlock` | `false` | 80 | `26904DB8724439814CCC2100DD4AE042085ED2041A6E4CDF1FD0CCCC57AF054E` |
| `PortalBlock` | `false` | 89 | `CC1F875BD9DAAB78FD628EC9C321BE02E00F5BA2FE7A4D5246B035968E13F287` |
| `RedstoneWireBlock` | `false` | 140 | `B5A4D449DFD2E285598BD7CBAF0F3695BDFD4E1C54DF4197719B329966B10DD9` |
| `ShulkerBoxBlock` | `false` | 62 | `C3A4082E6DFF9B59C61C0EA1FD79FCC6B349C85D4ABF47398E414C1CC52D17F5` |
| `SignBlock` | `false` | 39 | `1F75A575944CF81D3B9293EE7DB64EEF9A20BF96C1297BCD7EA8ADA3467F9B7C` |
| `SkullBlock` | `false` | 71 | `38EB7C51F13E54E7FDCA757378ACD9737AE0466228228083D378DA43720B0E2A` |
| `SlabBlock` | `this.isDouble()` | 73 | `15955DB0D4E2607BDF4D90FF93876BD0D0875AF3A378100225FB99B7EBB6BC8D` |
| `SnowLayerBlock` | `false` | 74 | `74BC424529548CDD37ABC67CD7BFFB5A1EBC7B82589C08862C5EA553DA2AD172` |
| `StainedGlassBlock` | `false` | 60 | `D38CF13B537D0F04163011020A1B815C7207D334582E6E61A50A4091964C8045` |
| `StairsBlock` | `false` | 145 | `5DF1782DA6EB7ACDB9935B3910A0484D6417661BFD6230ECF81DF0DE6D782627` |
| `StructureVoidBlock` | `false` | 42 | `D0D2F0A885BF5CAFDA3FDCF39A9B3BF7A083D31F0D75BA4F40EA348FF4DF7F5B` |
| `SugarCaneBlock` | `false` | 116 | `3ACB3E43802238694FBC0AA5BF9BD731BA73D881600FDC252B8FFE4C12F57B88` |
| `TorchBlock` | `false` | 67 | `E3015018B125B53BC1F93F20BECBCE7EC75DBCD233C9CB85D10E014FDB6F3B7D` |
| `TrapdoorBlock` | `false` | 72 | `956E2ADD467FB81D28578ECC7F0018BAC5C155217DB0B66BAFF466B8BCAA839A` |
| `TripwireBlock` | `false` | 74 | `C14D38C7B10B6D2CCF87B26D59F243C31BBBD9969475C7E7A493445359A371EA` |
| `TripwireHookBlock` | `false` | 66 | `730D8AC0130B103C44124DB0CFC5F81E1F8BE46FBC711E5C2B1AAA13CA8E62F1` |
| `VineBlock` | `false` | 94 | `B06BAFD1F06A7A75EF0239E87F190AFE9709DFF05CCBEEE72144222C2C1C558E` |
| `WallBlock` | `false` | 134 | `17DA6D881365E4DCE5134F88CB9A9E5273D1FA1734812D13410DC6924A3ACBFD` |

Pane registration and the explicit predicate cases are source-backed by the accepted 1.11.2 finding and its audit. The full registry table here expands that finding’s named cases into era-specific membership. Source provenance ready/provenance/manifest hashes: ready `CEABFB266993567AAB8C867525365E18AA0A0D7BA5A2CB7B6CFCC83D21318C40`, provenance `DCDB65E1361297A584C5B93F581B1B539911911C6CEC1A203740BAA5379732C6`, manifest `D908C2AF76598AD26999563BFEF0089F1D3C8C32483EEE6B108F0DEFA2773DF0`.

Accepted finding `.native-source/20261028/collision-next/pane-neighbor-1112/pane-neighbor-components-1.11.2.md` SHA-256 `B65F0A121F394243B4A0D47A4C8326F44A964E32E37F98DFB8CA0985A5CE434A`; accepted audit `.native-source/20261028/collision-next/pane-neighbor-1112-audit/audit.md` SHA-256 `A8C1A9EAA21B109F96DB4C1A2D89BCC9233182EB5F30107A4EC9BB0EFE62259C`.

Canonical roots: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.11.2/ornithe-feather/` and `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/26.2/unobfuscated/`. Current candidate membership was enumerated from 1,076 current vanilla IDs. The 1.12 crosswalk source reused for aliases: `.native-source/20261028/collision-next/pane-faceshape-overrides/legacy-block-id-crosswalk.md`, SHA-256 `9C2CDC0CFA4A1A6CE37E1B4AC22AB19183E3006968ADC3EA4C7F896D2FF6E486`.

## Limits

- The old `isCube` predicate uses the Block object default state, not the encountered neighbor state or face.
- State/property migrations are not exhaustively specified here; source provider result and era membership are the bounded operation.
- Current IDs not mapped from a 1.11.2 registered identity remain native. Modded registries are not covered.
- No product/backlog source, tests, builds, game/runtime, or source generation was used.
