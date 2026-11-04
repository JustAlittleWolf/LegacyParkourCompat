# Registry collision key crosswalk (1.12.2 → 1.13.2)

## Frozen inputs and review gate

- 1.12.2 inventory: `build/source-drafts/registry-collision-1122.md`, SHA-256 `29278A0C2B0899866F444296BAE8575BC6E039C3BBE419D0C51051077C1624A2`; exact Ornithe Feather Gen2 build 2; source-manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`. Root reports independent acceptance for registry and effective dispatch scope.
- 1.13.2 inventory: `build/source-drafts/registry-collision-1132.md`, SHA-256 `D9BAE34A1B4F02A5AB00F7CE7909F420663625C145C328FC5F332A9957A2B139`; exact Ornithe Feather Gen2 build 2; source-manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. Independent PASS on the exact hash covers registration keys, concrete-class association, effective dispatch and provenance.
- Pairing below began only after both pinned inventories had independent acceptance. The join uses exact registry-key equality; no probable rename, alias, split or merge was inferred.

## Key-set reconciliation

| Metric | Count |
|---|---:|
| 1.12.2 registered keys | 254 |
| 1.13.2 registered keys | 598 |
| Exact-key intersections | 184 |
| 1.12.2 only keys | 70 |
| 1.13.2 only keys | 414 |
| Union rows | 668 |
| Same concrete-class label among exact-key intersections | 167 |
| Changed concrete-class label among exact-key intersections | 17 |

These are registry identity/class-label counts, not collision-equivalence results. The old inventory records 254 registered IDs across `0..255`, with IDs `253` and `254` unregistered; they are not keys and therefore do not receive crosswalk rows.

### Shared-key class-label differences

| Registry key | 1.12.2 registered class | 1.13.2 registered class |
|---|---|---|
| `minecraft:bone_block` | BoneBlock | AxisBlock |
| `minecraft:dirt` | DirtBlock | Block |
| `minecraft:glass_pane` | PaneBlock | GlassPaneBlock |
| `minecraft:grass` | GrassBlock | TallPlantBlock |
| `minecraft:iron_bars` | PaneBlock | IronBarsBlock |
| `minecraft:lava` | LiquidSourceBlock | LiquidBlock |
| `minecraft:netherrack` | NetherrackBlock | Block |
| `minecraft:obsidian` | ObsidianBlock | Block |
| `minecraft:prismarine` | PrismarineBlock | Block |
| `minecraft:purpur_slab` | PurpurSlabBlock.Single | SlabBlock |
| `minecraft:quartz_block` | QuartzBlock | Block |
| `minecraft:red_sandstone` | RedSandstoneBlock | Block |
| `minecraft:sandstone` | SandstoneBlock | Block |
| `minecraft:snow` | SnowBlock | SnowLayerBlock |
| `minecraft:stone_slab` | SingleStoneSlabBlock | SlabBlock |
| `minecraft:trapped_chest` | ChestBlock | TrappedChestBlock |
| `minecraft:water` | LiquidSourceBlock | LiquidBlock |

A differing concrete-class label does not itself prove a behavior change; a matching label does not prove the effective provider body or runtime inputs match.

## Per-key inventory crosswalk

`Pair status` is registry-only. `UNMATCHED_ALIAS_UNRESOLVED` means the key occurs on one side and no cross-release alias/rename relation was established by the frozen inputs; it does not assert that no relation exists. An em dash means that endpoint has no row for that exact key.

| Registry key | Pair status | Old ID | Old concrete class | Old effective addCollisions owner | Old effective getCollisionShape owner | New concrete class | New effective collision dispatch |
|---|---|---:|---|---|---|---|---|
| `minecraft:air` | EXACT_KEY_ONLY | 0 | AirBlock | Block | AirBlock | AirBlock | Block.getCollisionShape → AirBlock.getShape |
| `minecraft:stone` | EXACT_KEY_ONLY | 1 | StoneBlock | Block | Block | StoneBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:grass` | EXACT_KEY_ONLY | 2 | GrassBlock | Block | Block | TallPlantBlock | Block.getCollisionShape → TallPlantBlock.getShape |
| `minecraft:dirt` | EXACT_KEY_ONLY | 3 | DirtBlock | Block | Block | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:cobblestone` | EXACT_KEY_ONLY | 4 | Block | Block | Block | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:planks` | UNMATCHED_ALIAS_UNRESOLVED | 5 | PlanksBlock | Block | Block | — | — |
| `minecraft:sapling` | UNMATCHED_ALIAS_UNRESOLVED | 6 | SaplingBlock | Block | PlantBlock | — | — |
| `minecraft:bedrock` | EXACT_KEY_ONLY | 7 | BedrockBlock | Block | Block | BedrockBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:flowing_water` | UNMATCHED_ALIAS_UNRESOLVED | 8 | FlowingLiquidBlock | Block | LiquidBlock | — | — |
| `minecraft:water` | EXACT_KEY_ONLY | 9 | LiquidSourceBlock | Block | LiquidBlock | LiquidBlock | Block.getCollisionShape → LiquidBlock.getShape |
| `minecraft:flowing_lava` | UNMATCHED_ALIAS_UNRESOLVED | 10 | FlowingLiquidBlock | Block | LiquidBlock | — | — |
| `minecraft:lava` | EXACT_KEY_ONLY | 11 | LiquidSourceBlock | Block | LiquidBlock | LiquidBlock | Block.getCollisionShape → LiquidBlock.getShape |
| `minecraft:sand` | EXACT_KEY_ONLY | 12 | SandBlock | Block | Block | SandBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:gravel` | EXACT_KEY_ONLY | 13 | GravelBlock | Block | Block | GravelBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:gold_ore` | EXACT_KEY_ONLY | 14 | OreBlock | Block | Block | OreBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:iron_ore` | EXACT_KEY_ONLY | 15 | OreBlock | Block | Block | OreBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:coal_ore` | EXACT_KEY_ONLY | 16 | OreBlock | Block | Block | OreBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:log` | UNMATCHED_ALIAS_UNRESOLVED | 17 | LogBlock | Block | Block | — | — |
| `minecraft:leaves` | UNMATCHED_ALIAS_UNRESOLVED | 18 | LeavesBlock | Block | Block | — | — |
| `minecraft:sponge` | EXACT_KEY_ONLY | 19 | SpongeBlock | Block | Block | SpongeBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:glass` | EXACT_KEY_ONLY | 20 | GlassBlock | Block | Block | GlassBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:lapis_ore` | EXACT_KEY_ONLY | 21 | OreBlock | Block | Block | OreBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:lapis_block` | EXACT_KEY_ONLY | 22 | Block | Block | Block | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:dispenser` | EXACT_KEY_ONLY | 23 | DispenserBlock | Block | Block | DispenserBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:sandstone` | EXACT_KEY_ONLY | 24 | SandstoneBlock | Block | Block | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:noteblock` | UNMATCHED_ALIAS_UNRESOLVED | 25 | NoteBlock | Block | Block | — | — |
| `minecraft:bed` | UNMATCHED_ALIAS_UNRESOLVED | 26 | BedBlock | Block | Block | — | — |
| `minecraft:golden_rail` | UNMATCHED_ALIAS_UNRESOLVED | 27 | PoweredRailBlock | Block | AbstractRailBlock | — | — |
| `minecraft:detector_rail` | EXACT_KEY_ONLY | 28 | DetectorRailBlock | Block | AbstractRailBlock | DetectorRailBlock | Block.getCollisionShape → AbstractRailBlock.getShape |
| `minecraft:sticky_piston` | EXACT_KEY_ONLY | 29 | PistonBaseBlock | PistonBaseBlock | Block | PistonBaseBlock | Block.getCollisionShape → PistonBaseBlock.getShape |
| `minecraft:web` | UNMATCHED_ALIAS_UNRESOLVED | 30 | CobwebBlock | Block | CobwebBlock | — | — |
| `minecraft:tallgrass` | UNMATCHED_ALIAS_UNRESOLVED | 31 | TallPlantBlock | Block | PlantBlock | — | — |
| `minecraft:deadbush` | UNMATCHED_ALIAS_UNRESOLVED | 32 | DeadBushBlock | Block | PlantBlock | — | — |
| `minecraft:piston` | EXACT_KEY_ONLY | 33 | PistonBaseBlock | PistonBaseBlock | Block | PistonBaseBlock | Block.getCollisionShape → PistonBaseBlock.getShape |
| `minecraft:piston_head` | EXACT_KEY_ONLY | 34 | PistonHeadBlock | PistonHeadBlock | Block | PistonHeadBlock | Block.getCollisionShape → PistonHeadBlock.getShape |
| `minecraft:wool` | UNMATCHED_ALIAS_UNRESOLVED | 35 | ColoredBlock | Block | Block | — | — |
| `minecraft:piston_extension` | UNMATCHED_ALIAS_UNRESOLVED | 36 | MovingBlock | MovingBlock | MovingBlock | — | — |
| `minecraft:yellow_flower` | UNMATCHED_ALIAS_UNRESOLVED | 37 | YellowFlowerBlock | Block | PlantBlock | — | — |
| `minecraft:red_flower` | UNMATCHED_ALIAS_UNRESOLVED | 38 | RedFlowerBlock | Block | PlantBlock | — | — |
| `minecraft:brown_mushroom` | EXACT_KEY_ONLY | 39 | MushroomPlantBlock | Block | PlantBlock | MushroomPlantBlock | Block.getCollisionShape → MushroomPlantBlock.getShape |
| `minecraft:red_mushroom` | EXACT_KEY_ONLY | 40 | MushroomPlantBlock | Block | PlantBlock | MushroomPlantBlock | Block.getCollisionShape → MushroomPlantBlock.getShape |
| `minecraft:gold_block` | EXACT_KEY_ONLY | 41 | Block | Block | Block | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:iron_block` | EXACT_KEY_ONLY | 42 | Block | Block | Block | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:double_stone_slab` | UNMATCHED_ALIAS_UNRESOLVED | 43 | DoubleStoneSlabBlock | Block | Block | — | — |
| `minecraft:stone_slab` | EXACT_KEY_ONLY | 44 | SingleStoneSlabBlock | Block | Block | SlabBlock | Block.getCollisionShape → SlabBlock.getShape |
| `minecraft:brick_block` | UNMATCHED_ALIAS_UNRESOLVED | 45 | Block | Block | Block | — | — |
| `minecraft:tnt` | EXACT_KEY_ONLY | 46 | TntBlock | Block | Block | TntBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:bookshelf` | EXACT_KEY_ONLY | 47 | BookshelfBlock | Block | Block | BookshelfBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:mossy_cobblestone` | EXACT_KEY_ONLY | 48 | Block | Block | Block | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:obsidian` | EXACT_KEY_ONLY | 49 | ObsidianBlock | Block | Block | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:torch` | EXACT_KEY_ONLY | 50 | TorchBlock | Block | TorchBlock | TorchBlock | Block.getCollisionShape → TorchBlock.getShape |
| `minecraft:fire` | EXACT_KEY_ONLY | 51 | FireBlock | Block | FireBlock | FireBlock | Block.getCollisionShape → FireBlock.getShape |
| `minecraft:mob_spawner` | UNMATCHED_ALIAS_UNRESOLVED | 52 | MobSpawnerBlock | Block | Block | — | — |
| `minecraft:oak_stairs` | EXACT_KEY_ONLY | 53 | StairsBlock | StairsBlock | Block | StairsBlock | Block.getCollisionShape → StairsBlock.getShape |
| `minecraft:chest` | EXACT_KEY_ONLY | 54 | ChestBlock | Block | Block | ChestBlock | Block.getCollisionShape → ChestBlock.getShape |
| `minecraft:redstone_wire` | EXACT_KEY_ONLY | 55 | RedstoneWireBlock | Block | RedstoneWireBlock | RedstoneWireBlock | Block.getCollisionShape → RedstoneWireBlock.getShape |
| `minecraft:diamond_ore` | EXACT_KEY_ONLY | 56 | OreBlock | Block | Block | OreBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:diamond_block` | EXACT_KEY_ONLY | 57 | Block | Block | Block | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:crafting_table` | EXACT_KEY_ONLY | 58 | CraftingTableBlock | Block | Block | CraftingTableBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:wheat` | EXACT_KEY_ONLY | 59 | WheatBlock | Block | PlantBlock | WheatBlock | Block.getCollisionShape → WheatBlock.getShape |
| `minecraft:farmland` | EXACT_KEY_ONLY | 60 | FarmlandBlock | Block | Block | FarmlandBlock | Block.getCollisionShape → FarmlandBlock.getShape |
| `minecraft:furnace` | EXACT_KEY_ONLY | 61 | FurnaceBlock | Block | Block | FurnaceBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:lit_furnace` | UNMATCHED_ALIAS_UNRESOLVED | 62 | FurnaceBlock | Block | Block | — | — |
| `minecraft:standing_sign` | UNMATCHED_ALIAS_UNRESOLVED | 63 | StandingSignBlock | Block | SignBlock | — | — |
| `minecraft:wooden_door` | UNMATCHED_ALIAS_UNRESOLVED | 64 | DoorBlock | Block | Block | — | — |
| `minecraft:ladder` | EXACT_KEY_ONLY | 65 | LadderBlock | Block | Block | LadderBlock | Block.getCollisionShape → LadderBlock.getShape |
| `minecraft:rail` | EXACT_KEY_ONLY | 66 | RailBlock | Block | AbstractRailBlock | RailBlock | Block.getCollisionShape → AbstractRailBlock.getShape |
| `minecraft:stone_stairs` | UNMATCHED_ALIAS_UNRESOLVED | 67 | StairsBlock | StairsBlock | Block | — | — |
| `minecraft:wall_sign` | EXACT_KEY_ONLY | 68 | WallSignBlock | Block | SignBlock | WallSignBlock | Block.getCollisionShape → WallSignBlock.getShape |
| `minecraft:lever` | EXACT_KEY_ONLY | 69 | LeverBlock | Block | LeverBlock | LeverBlock | Block.getCollisionShape → LeverBlock.getShape |
| `minecraft:stone_pressure_plate` | EXACT_KEY_ONLY | 70 | PressurePlateBlock | Block | AbstractPressurePlateBlock | PressurePlateBlock | Block.getCollisionShape → AbstractPressurePlateBlock.getShape |
| `minecraft:iron_door` | EXACT_KEY_ONLY | 71 | DoorBlock | Block | Block | DoorBlock | Block.getCollisionShape → DoorBlock.getShape |
| `minecraft:wooden_pressure_plate` | UNMATCHED_ALIAS_UNRESOLVED | 72 | PressurePlateBlock | Block | AbstractPressurePlateBlock | — | — |
| `minecraft:redstone_ore` | EXACT_KEY_ONLY | 73 | RedstoneOreBlock | Block | Block | RedstoneOreBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:lit_redstone_ore` | UNMATCHED_ALIAS_UNRESOLVED | 74 | RedstoneOreBlock | Block | Block | — | — |
| `minecraft:unlit_redstone_torch` | UNMATCHED_ALIAS_UNRESOLVED | 75 | RedstoneTorchBlock | Block | TorchBlock | — | — |
| `minecraft:redstone_torch` | EXACT_KEY_ONLY | 76 | RedstoneTorchBlock | Block | TorchBlock | RedstoneTorchBlock | Block.getCollisionShape → TorchBlock.getShape |
| `minecraft:stone_button` | EXACT_KEY_ONLY | 77 | StoneButtonBlock | Block | ButtonBlock | StoneButtonBlock | Block.getCollisionShape → ButtonBlock.getShape |
| `minecraft:snow_layer` | UNMATCHED_ALIAS_UNRESOLVED | 78 | SnowLayerBlock | Block | SnowLayerBlock | — | — |
| `minecraft:ice` | EXACT_KEY_ONLY | 79 | IceBlock | Block | Block | IceBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:snow` | EXACT_KEY_ONLY | 80 | SnowBlock | Block | Block | SnowLayerBlock | SnowLayerBlock.getCollisionShape |
| `minecraft:cactus` | EXACT_KEY_ONLY | 81 | CactusBlock | Block | CactusBlock | CactusBlock | CactusBlock.getCollisionShape |
| `minecraft:clay` | EXACT_KEY_ONLY | 82 | ClayBlock | Block | Block | ClayBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:reeds` | UNMATCHED_ALIAS_UNRESOLVED | 83 | SugarCaneBlock | Block | SugarCaneBlock | — | — |
| `minecraft:jukebox` | EXACT_KEY_ONLY | 84 | JukeboxBlock | Block | Block | JukeboxBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:fence` | UNMATCHED_ALIAS_UNRESOLVED | 85 | FenceBlock | FenceBlock | Block | — | — |
| `minecraft:pumpkin` | EXACT_KEY_ONLY | 86 | PumpkinBlock | Block | Block | PumpkinBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:netherrack` | EXACT_KEY_ONLY | 87 | NetherrackBlock | Block | Block | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:soul_sand` | EXACT_KEY_ONLY | 88 | SoulSandBlock | Block | SoulSandBlock | SoulSandBlock | SoulSandBlock.getCollisionShape |
| `minecraft:glowstone` | EXACT_KEY_ONLY | 89 | GlowstoneBlock | Block | Block | GlowstoneBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:portal` | UNMATCHED_ALIAS_UNRESOLVED | 90 | PortalBlock | Block | PortalBlock | — | — |
| `minecraft:lit_pumpkin` | UNMATCHED_ALIAS_UNRESOLVED | 91 | PumpkinBlock | Block | Block | — | — |
| `minecraft:cake` | EXACT_KEY_ONLY | 92 | CakeBlock | Block | Block | CakeBlock | Block.getCollisionShape → CakeBlock.getShape |
| `minecraft:unpowered_repeater` | UNMATCHED_ALIAS_UNRESOLVED | 93 | RepeaterBlock | Block | Block | — | — |
| `minecraft:powered_repeater` | UNMATCHED_ALIAS_UNRESOLVED | 94 | RepeaterBlock | Block | Block | — | — |
| `minecraft:stained_glass` | UNMATCHED_ALIAS_UNRESOLVED | 95 | StainedGlassBlock | Block | Block | — | — |
| `minecraft:trapdoor` | UNMATCHED_ALIAS_UNRESOLVED | 96 | TrapdoorBlock | Block | Block | — | — |
| `minecraft:monster_egg` | UNMATCHED_ALIAS_UNRESOLVED | 97 | InfestedBlock | Block | Block | — | — |
| `minecraft:stonebrick` | UNMATCHED_ALIAS_UNRESOLVED | 98 | StonebrickBlock | Block | Block | — | — |
| `minecraft:brown_mushroom_block` | EXACT_KEY_ONLY | 99 | MushroomBlock | Block | Block | MushroomBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:red_mushroom_block` | EXACT_KEY_ONLY | 100 | MushroomBlock | Block | Block | MushroomBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:iron_bars` | EXACT_KEY_ONLY | 101 | PaneBlock | PaneBlock | Block | IronBarsBlock | PaneBlock.getCollisionShape |
| `minecraft:glass_pane` | EXACT_KEY_ONLY | 102 | PaneBlock | PaneBlock | Block | GlassPaneBlock | PaneBlock.getCollisionShape |
| `minecraft:melon_block` | UNMATCHED_ALIAS_UNRESOLVED | 103 | MelonBlock | Block | Block | — | — |
| `minecraft:pumpkin_stem` | EXACT_KEY_ONLY | 104 | StemBlock | Block | PlantBlock | StemBlock | Block.getCollisionShape → StemBlock.getShape |
| `minecraft:melon_stem` | EXACT_KEY_ONLY | 105 | StemBlock | Block | PlantBlock | StemBlock | Block.getCollisionShape → StemBlock.getShape |
| `minecraft:vine` | EXACT_KEY_ONLY | 106 | VineBlock | Block | VineBlock | VineBlock | Block.getCollisionShape → VineBlock.getShape |
| `minecraft:fence_gate` | UNMATCHED_ALIAS_UNRESOLVED | 107 | FenceGateBlock | Block | FenceGateBlock | — | — |
| `minecraft:brick_stairs` | EXACT_KEY_ONLY | 108 | StairsBlock | StairsBlock | Block | StairsBlock | Block.getCollisionShape → StairsBlock.getShape |
| `minecraft:stone_brick_stairs` | EXACT_KEY_ONLY | 109 | StairsBlock | StairsBlock | Block | StairsBlock | Block.getCollisionShape → StairsBlock.getShape |
| `minecraft:mycelium` | EXACT_KEY_ONLY | 110 | MyceliumBlock | Block | Block | MyceliumBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:waterlily` | UNMATCHED_ALIAS_UNRESOLVED | 111 | LilyPadBlock | LilyPadBlock | PlantBlock | — | — |
| `minecraft:nether_brick` | UNMATCHED_ALIAS_UNRESOLVED | 112 | NetherBrickBlock | Block | Block | — | — |
| `minecraft:nether_brick_fence` | EXACT_KEY_ONLY | 113 | FenceBlock | FenceBlock | Block | FenceBlock | PaneBlock.getCollisionShape |
| `minecraft:nether_brick_stairs` | EXACT_KEY_ONLY | 114 | StairsBlock | StairsBlock | Block | StairsBlock | Block.getCollisionShape → StairsBlock.getShape |
| `minecraft:nether_wart` | EXACT_KEY_ONLY | 115 | NetherWartBlock | Block | PlantBlock | NetherWartBlock | Block.getCollisionShape → NetherWartBlock.getShape |
| `minecraft:enchanting_table` | EXACT_KEY_ONLY | 116 | EnchantingTableBlock | Block | Block | EnchantingTableBlock | Block.getCollisionShape → EnchantingTableBlock.getShape |
| `minecraft:brewing_stand` | EXACT_KEY_ONLY | 117 | BrewingStandBlock | BrewingStandBlock | Block | BrewingStandBlock | Block.getCollisionShape → BrewingStandBlock.getShape |
| `minecraft:cauldron` | EXACT_KEY_ONLY | 118 | CauldronBlock | CauldronBlock | Block | CauldronBlock | Block.getCollisionShape → CauldronBlock.getShape |
| `minecraft:end_portal` | EXACT_KEY_ONLY | 119 | EndPortalBlock | EndPortalBlock | Block | EndPortalBlock | Block.getCollisionShape → EndPortalBlock.getShape |
| `minecraft:end_portal_frame` | EXACT_KEY_ONLY | 120 | EndPortalFrameBlock | EndPortalFrameBlock | Block | EndPortalFrameBlock | Block.getCollisionShape → EndPortalFrameBlock.getShape |
| `minecraft:end_stone` | EXACT_KEY_ONLY | 121 | Block | Block | Block | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:dragon_egg` | EXACT_KEY_ONLY | 122 | DragonEggBlock | Block | Block | DragonEggBlock | Block.getCollisionShape → DragonEggBlock.getShape |
| `minecraft:redstone_lamp` | EXACT_KEY_ONLY | 123 | RedstoneLampBlock | Block | Block | RedstoneLampBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:lit_redstone_lamp` | UNMATCHED_ALIAS_UNRESOLVED | 124 | RedstoneLampBlock | Block | Block | — | — |
| `minecraft:double_wooden_slab` | UNMATCHED_ALIAS_UNRESOLVED | 125 | DoubleWoodenSlabBlock | Block | Block | — | — |
| `minecraft:wooden_slab` | UNMATCHED_ALIAS_UNRESOLVED | 126 | SingleWoodenSlabBlock | Block | Block | — | — |
| `minecraft:cocoa` | EXACT_KEY_ONLY | 127 | CocoaBlock | Block | Block | CocoaBlock | Block.getCollisionShape → CocoaBlock.getShape |
| `minecraft:sandstone_stairs` | EXACT_KEY_ONLY | 128 | StairsBlock | StairsBlock | Block | StairsBlock | Block.getCollisionShape → StairsBlock.getShape |
| `minecraft:emerald_ore` | EXACT_KEY_ONLY | 129 | OreBlock | Block | Block | OreBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:ender_chest` | EXACT_KEY_ONLY | 130 | EnderChestBlock | Block | Block | EnderChestBlock | Block.getCollisionShape → EnderChestBlock.getShape |
| `minecraft:tripwire_hook` | EXACT_KEY_ONLY | 131 | TripwireHookBlock | Block | TripwireHookBlock | TripwireHookBlock | Block.getCollisionShape → TripwireHookBlock.getShape |
| `minecraft:tripwire` | EXACT_KEY_ONLY | 132 | TripwireBlock | Block | TripwireBlock | TripwireBlock | Block.getCollisionShape → TripwireBlock.getShape |
| `minecraft:emerald_block` | EXACT_KEY_ONLY | 133 | Block | Block | Block | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:spruce_stairs` | EXACT_KEY_ONLY | 134 | StairsBlock | StairsBlock | Block | StairsBlock | Block.getCollisionShape → StairsBlock.getShape |
| `minecraft:birch_stairs` | EXACT_KEY_ONLY | 135 | StairsBlock | StairsBlock | Block | StairsBlock | Block.getCollisionShape → StairsBlock.getShape |
| `minecraft:jungle_stairs` | EXACT_KEY_ONLY | 136 | StairsBlock | StairsBlock | Block | StairsBlock | Block.getCollisionShape → StairsBlock.getShape |
| `minecraft:command_block` | EXACT_KEY_ONLY | 137 | CommandBlock | Block | Block | CommandBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:beacon` | EXACT_KEY_ONLY | 138 | BeaconBlock | Block | Block | BeaconBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:cobblestone_wall` | EXACT_KEY_ONLY | 139 | WallBlock | WallBlock | WallBlock | WallBlock | WallBlock.getCollisionShape |
| `minecraft:flower_pot` | EXACT_KEY_ONLY | 140 | FlowerPotBlock | Block | Block | FlowerPotBlock | Block.getCollisionShape → FlowerPotBlock.getShape |
| `minecraft:carrots` | EXACT_KEY_ONLY | 141 | CarrotsBlock | Block | PlantBlock | CarrotsBlock | Block.getCollisionShape → CarrotsBlock.getShape |
| `minecraft:potatoes` | EXACT_KEY_ONLY | 142 | PotatoesBlock | Block | PlantBlock | PotatoesBlock | Block.getCollisionShape → PotatoesBlock.getShape |
| `minecraft:wooden_button` | UNMATCHED_ALIAS_UNRESOLVED | 143 | WoodenButtonBlock | Block | ButtonBlock | — | — |
| `minecraft:skull` | UNMATCHED_ALIAS_UNRESOLVED | 144 | SkullBlock | Block | Block | — | — |
| `minecraft:anvil` | EXACT_KEY_ONLY | 145 | AnvilBlock | Block | Block | AnvilBlock | Block.getCollisionShape → AnvilBlock.getShape |
| `minecraft:trapped_chest` | EXACT_KEY_ONLY | 146 | ChestBlock | Block | Block | TrappedChestBlock | Block.getCollisionShape → ChestBlock.getShape |
| `minecraft:light_weighted_pressure_plate` | EXACT_KEY_ONLY | 147 | WeightedPressurePlateBlock | Block | AbstractPressurePlateBlock | WeightedPressurePlateBlock | Block.getCollisionShape → AbstractPressurePlateBlock.getShape |
| `minecraft:heavy_weighted_pressure_plate` | EXACT_KEY_ONLY | 148 | WeightedPressurePlateBlock | Block | AbstractPressurePlateBlock | WeightedPressurePlateBlock | Block.getCollisionShape → AbstractPressurePlateBlock.getShape |
| `minecraft:unpowered_comparator` | UNMATCHED_ALIAS_UNRESOLVED | 149 | ComparatorBlock | Block | Block | — | — |
| `minecraft:powered_comparator` | UNMATCHED_ALIAS_UNRESOLVED | 150 | ComparatorBlock | Block | Block | — | — |
| `minecraft:daylight_detector` | EXACT_KEY_ONLY | 151 | DaylightDetectorBlock | Block | Block | DaylightDetectorBlock | Block.getCollisionShape → DaylightDetectorBlock.getShape |
| `minecraft:redstone_block` | EXACT_KEY_ONLY | 152 | RedstoneBlock | Block | Block | RedstoneBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:quartz_ore` | UNMATCHED_ALIAS_UNRESOLVED | 153 | OreBlock | Block | Block | — | — |
| `minecraft:hopper` | EXACT_KEY_ONLY | 154 | HopperBlock | HopperBlock | Block | HopperBlock | Block.getCollisionShape → HopperBlock.getShape |
| `minecraft:quartz_block` | EXACT_KEY_ONLY | 155 | QuartzBlock | Block | Block | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:quartz_stairs` | EXACT_KEY_ONLY | 156 | StairsBlock | StairsBlock | Block | StairsBlock | Block.getCollisionShape → StairsBlock.getShape |
| `minecraft:activator_rail` | EXACT_KEY_ONLY | 157 | PoweredRailBlock | Block | AbstractRailBlock | PoweredRailBlock | Block.getCollisionShape → AbstractRailBlock.getShape |
| `minecraft:dropper` | EXACT_KEY_ONLY | 158 | DropperBlock | Block | Block | DropperBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:stained_hardened_clay` | UNMATCHED_ALIAS_UNRESOLVED | 159 | StainedHardenedClayBlock | Block | Block | — | — |
| `minecraft:stained_glass_pane` | UNMATCHED_ALIAS_UNRESOLVED | 160 | StainedGlassPaneBlock | PaneBlock | Block | — | — |
| `minecraft:leaves2` | UNMATCHED_ALIAS_UNRESOLVED | 161 | Leaves2Block | Block | Block | — | — |
| `minecraft:log2` | UNMATCHED_ALIAS_UNRESOLVED | 162 | Log2Block | Block | Block | — | — |
| `minecraft:acacia_stairs` | EXACT_KEY_ONLY | 163 | StairsBlock | StairsBlock | Block | StairsBlock | Block.getCollisionShape → StairsBlock.getShape |
| `minecraft:dark_oak_stairs` | EXACT_KEY_ONLY | 164 | StairsBlock | StairsBlock | Block | StairsBlock | Block.getCollisionShape → StairsBlock.getShape |
| `minecraft:slime` | UNMATCHED_ALIAS_UNRESOLVED | 165 | SlimeBlock | Block | Block | — | — |
| `minecraft:barrier` | EXACT_KEY_ONLY | 166 | BarrierBlock | Block | Block | BarrierBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:iron_trapdoor` | EXACT_KEY_ONLY | 167 | TrapdoorBlock | Block | Block | TrapdoorBlock | Block.getCollisionShape → TrapdoorBlock.getShape |
| `minecraft:prismarine` | EXACT_KEY_ONLY | 168 | PrismarineBlock | Block | Block | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:sea_lantern` | EXACT_KEY_ONLY | 169 | SeaLanternBlock | Block | Block | SeaLanternBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:hay_block` | EXACT_KEY_ONLY | 170 | HayBlock | Block | Block | HayBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:carpet` | UNMATCHED_ALIAS_UNRESOLVED | 171 | CarpetBlock | Block | Block | — | — |
| `minecraft:hardened_clay` | UNMATCHED_ALIAS_UNRESOLVED | 172 | HardenedClayBlock | Block | Block | — | — |
| `minecraft:coal_block` | EXACT_KEY_ONLY | 173 | Block | Block | Block | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:packed_ice` | EXACT_KEY_ONLY | 174 | PackedIceBlock | Block | Block | PackedIceBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:double_plant` | UNMATCHED_ALIAS_UNRESOLVED | 175 | DoublePlantBlock | Block | PlantBlock | — | — |
| `minecraft:standing_banner` | UNMATCHED_ALIAS_UNRESOLVED | 176 | BannerBlock.Standing | Block | BannerBlock | — | — |
| `minecraft:wall_banner` | UNMATCHED_ALIAS_UNRESOLVED | 177 | BannerBlock.Wall | Block | BannerBlock | — | — |
| `minecraft:daylight_detector_inverted` | UNMATCHED_ALIAS_UNRESOLVED | 178 | DaylightDetectorBlock | Block | Block | — | — |
| `minecraft:red_sandstone` | EXACT_KEY_ONLY | 179 | RedSandstoneBlock | Block | Block | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:red_sandstone_stairs` | EXACT_KEY_ONLY | 180 | StairsBlock | StairsBlock | Block | StairsBlock | Block.getCollisionShape → StairsBlock.getShape |
| `minecraft:double_stone_slab2` | UNMATCHED_ALIAS_UNRESOLVED | 181 | DoubleRedSandstoneSlabBlock | Block | Block | — | — |
| `minecraft:stone_slab2` | UNMATCHED_ALIAS_UNRESOLVED | 182 | SingleRedSandstoneSlabBlock | Block | Block | — | — |
| `minecraft:spruce_fence_gate` | EXACT_KEY_ONLY | 183 | FenceGateBlock | Block | FenceGateBlock | FenceGateBlock | FenceGateBlock.getCollisionShape |
| `minecraft:birch_fence_gate` | EXACT_KEY_ONLY | 184 | FenceGateBlock | Block | FenceGateBlock | FenceGateBlock | FenceGateBlock.getCollisionShape |
| `minecraft:jungle_fence_gate` | EXACT_KEY_ONLY | 185 | FenceGateBlock | Block | FenceGateBlock | FenceGateBlock | FenceGateBlock.getCollisionShape |
| `minecraft:dark_oak_fence_gate` | EXACT_KEY_ONLY | 186 | FenceGateBlock | Block | FenceGateBlock | FenceGateBlock | FenceGateBlock.getCollisionShape |
| `minecraft:acacia_fence_gate` | EXACT_KEY_ONLY | 187 | FenceGateBlock | Block | FenceGateBlock | FenceGateBlock | FenceGateBlock.getCollisionShape |
| `minecraft:spruce_fence` | EXACT_KEY_ONLY | 188 | FenceBlock | FenceBlock | Block | FenceBlock | PaneBlock.getCollisionShape |
| `minecraft:birch_fence` | EXACT_KEY_ONLY | 189 | FenceBlock | FenceBlock | Block | FenceBlock | PaneBlock.getCollisionShape |
| `minecraft:jungle_fence` | EXACT_KEY_ONLY | 190 | FenceBlock | FenceBlock | Block | FenceBlock | PaneBlock.getCollisionShape |
| `minecraft:dark_oak_fence` | EXACT_KEY_ONLY | 191 | FenceBlock | FenceBlock | Block | FenceBlock | PaneBlock.getCollisionShape |
| `minecraft:acacia_fence` | EXACT_KEY_ONLY | 192 | FenceBlock | FenceBlock | Block | FenceBlock | PaneBlock.getCollisionShape |
| `minecraft:spruce_door` | EXACT_KEY_ONLY | 193 | DoorBlock | Block | Block | DoorBlock | Block.getCollisionShape → DoorBlock.getShape |
| `minecraft:birch_door` | EXACT_KEY_ONLY | 194 | DoorBlock | Block | Block | DoorBlock | Block.getCollisionShape → DoorBlock.getShape |
| `minecraft:jungle_door` | EXACT_KEY_ONLY | 195 | DoorBlock | Block | Block | DoorBlock | Block.getCollisionShape → DoorBlock.getShape |
| `minecraft:acacia_door` | EXACT_KEY_ONLY | 196 | DoorBlock | Block | Block | DoorBlock | Block.getCollisionShape → DoorBlock.getShape |
| `minecraft:dark_oak_door` | EXACT_KEY_ONLY | 197 | DoorBlock | Block | Block | DoorBlock | Block.getCollisionShape → DoorBlock.getShape |
| `minecraft:end_rod` | EXACT_KEY_ONLY | 198 | EndRodBlock | Block | Block | EndRodBlock | Block.getCollisionShape → EndRodBlock.getShape |
| `minecraft:chorus_plant` | EXACT_KEY_ONLY | 199 | ChorusPlantBlock | ChorusPlantBlock | Block | ChorusPlantBlock | Block.getCollisionShape → PipeBlock.getShape |
| `minecraft:chorus_flower` | EXACT_KEY_ONLY | 200 | ChorusFlowerBlock | Block | Block | ChorusFlowerBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:purpur_block` | EXACT_KEY_ONLY | 201 | Block | Block | Block | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:purpur_pillar` | EXACT_KEY_ONLY | 202 | AxisBlock | Block | Block | AxisBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:purpur_stairs` | EXACT_KEY_ONLY | 203 | StairsBlock | StairsBlock | Block | StairsBlock | Block.getCollisionShape → StairsBlock.getShape |
| `minecraft:purpur_double_slab` | UNMATCHED_ALIAS_UNRESOLVED | 204 | PurpurSlabBlock.Double | Block | Block | — | — |
| `minecraft:purpur_slab` | EXACT_KEY_ONLY | 205 | PurpurSlabBlock.Single | Block | Block | SlabBlock | Block.getCollisionShape → SlabBlock.getShape |
| `minecraft:end_bricks` | UNMATCHED_ALIAS_UNRESOLVED | 206 | Block | Block | Block | — | — |
| `minecraft:beetroots` | EXACT_KEY_ONLY | 207 | BeetrootsBlock | Block | PlantBlock | BeetrootsBlock | Block.getCollisionShape → BeetrootsBlock.getShape |
| `minecraft:grass_path` | EXACT_KEY_ONLY | 208 | GrassPathBlock | Block | Block | GrassPathBlock | Block.getCollisionShape → GrassPathBlock.getShape |
| `minecraft:end_gateway` | EXACT_KEY_ONLY | 209 | EndGatewayBlock | Block | EndGatewayBlock | EndGatewayBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:repeating_command_block` | EXACT_KEY_ONLY | 210 | CommandBlock | Block | Block | CommandBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:chain_command_block` | EXACT_KEY_ONLY | 211 | CommandBlock | Block | Block | CommandBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:frosted_ice` | EXACT_KEY_ONLY | 212 | FrostedIceBlock | Block | Block | FrostedIceBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:magma` | UNMATCHED_ALIAS_UNRESOLVED | 213 | MagmaBlock | Block | Block | — | — |
| `minecraft:nether_wart_block` | EXACT_KEY_ONLY | 214 | Block | Block | Block | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:red_nether_brick` | UNMATCHED_ALIAS_UNRESOLVED | 215 | NetherBrickBlock | Block | Block | — | — |
| `minecraft:bone_block` | EXACT_KEY_ONLY | 216 | BoneBlock | Block | Block | AxisBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:structure_void` | EXACT_KEY_ONLY | 217 | StructureVoidBlock | Block | StructureVoidBlock | StructureVoidBlock | Block.getCollisionShape → StructureVoidBlock.getShape |
| `minecraft:observer` | EXACT_KEY_ONLY | 218 | ObserverBlock | Block | Block | ObserverBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:white_shulker_box` | EXACT_KEY_ONLY | 219 | ShulkerBoxBlock | Block | Block | ShulkerBoxBlock | Block.getCollisionShape → ShulkerBoxBlock.getShape |
| `minecraft:orange_shulker_box` | EXACT_KEY_ONLY | 220 | ShulkerBoxBlock | Block | Block | ShulkerBoxBlock | Block.getCollisionShape → ShulkerBoxBlock.getShape |
| `minecraft:magenta_shulker_box` | EXACT_KEY_ONLY | 221 | ShulkerBoxBlock | Block | Block | ShulkerBoxBlock | Block.getCollisionShape → ShulkerBoxBlock.getShape |
| `minecraft:light_blue_shulker_box` | EXACT_KEY_ONLY | 222 | ShulkerBoxBlock | Block | Block | ShulkerBoxBlock | Block.getCollisionShape → ShulkerBoxBlock.getShape |
| `minecraft:yellow_shulker_box` | EXACT_KEY_ONLY | 223 | ShulkerBoxBlock | Block | Block | ShulkerBoxBlock | Block.getCollisionShape → ShulkerBoxBlock.getShape |
| `minecraft:lime_shulker_box` | EXACT_KEY_ONLY | 224 | ShulkerBoxBlock | Block | Block | ShulkerBoxBlock | Block.getCollisionShape → ShulkerBoxBlock.getShape |
| `minecraft:pink_shulker_box` | EXACT_KEY_ONLY | 225 | ShulkerBoxBlock | Block | Block | ShulkerBoxBlock | Block.getCollisionShape → ShulkerBoxBlock.getShape |
| `minecraft:gray_shulker_box` | EXACT_KEY_ONLY | 226 | ShulkerBoxBlock | Block | Block | ShulkerBoxBlock | Block.getCollisionShape → ShulkerBoxBlock.getShape |
| `minecraft:silver_shulker_box` | UNMATCHED_ALIAS_UNRESOLVED | 227 | ShulkerBoxBlock | Block | Block | — | — |
| `minecraft:cyan_shulker_box` | EXACT_KEY_ONLY | 228 | ShulkerBoxBlock | Block | Block | ShulkerBoxBlock | Block.getCollisionShape → ShulkerBoxBlock.getShape |
| `minecraft:purple_shulker_box` | EXACT_KEY_ONLY | 229 | ShulkerBoxBlock | Block | Block | ShulkerBoxBlock | Block.getCollisionShape → ShulkerBoxBlock.getShape |
| `minecraft:blue_shulker_box` | EXACT_KEY_ONLY | 230 | ShulkerBoxBlock | Block | Block | ShulkerBoxBlock | Block.getCollisionShape → ShulkerBoxBlock.getShape |
| `minecraft:brown_shulker_box` | EXACT_KEY_ONLY | 231 | ShulkerBoxBlock | Block | Block | ShulkerBoxBlock | Block.getCollisionShape → ShulkerBoxBlock.getShape |
| `minecraft:green_shulker_box` | EXACT_KEY_ONLY | 232 | ShulkerBoxBlock | Block | Block | ShulkerBoxBlock | Block.getCollisionShape → ShulkerBoxBlock.getShape |
| `minecraft:red_shulker_box` | EXACT_KEY_ONLY | 233 | ShulkerBoxBlock | Block | Block | ShulkerBoxBlock | Block.getCollisionShape → ShulkerBoxBlock.getShape |
| `minecraft:black_shulker_box` | EXACT_KEY_ONLY | 234 | ShulkerBoxBlock | Block | Block | ShulkerBoxBlock | Block.getCollisionShape → ShulkerBoxBlock.getShape |
| `minecraft:white_glazed_terracotta` | EXACT_KEY_ONLY | 235 | GlazedTerracottaBlock | Block | Block | GlazedTerracottaBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:orange_glazed_terracotta` | EXACT_KEY_ONLY | 236 | GlazedTerracottaBlock | Block | Block | GlazedTerracottaBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:magenta_glazed_terracotta` | EXACT_KEY_ONLY | 237 | GlazedTerracottaBlock | Block | Block | GlazedTerracottaBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:light_blue_glazed_terracotta` | EXACT_KEY_ONLY | 238 | GlazedTerracottaBlock | Block | Block | GlazedTerracottaBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:yellow_glazed_terracotta` | EXACT_KEY_ONLY | 239 | GlazedTerracottaBlock | Block | Block | GlazedTerracottaBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:lime_glazed_terracotta` | EXACT_KEY_ONLY | 240 | GlazedTerracottaBlock | Block | Block | GlazedTerracottaBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:pink_glazed_terracotta` | EXACT_KEY_ONLY | 241 | GlazedTerracottaBlock | Block | Block | GlazedTerracottaBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:gray_glazed_terracotta` | EXACT_KEY_ONLY | 242 | GlazedTerracottaBlock | Block | Block | GlazedTerracottaBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:silver_glazed_terracotta` | UNMATCHED_ALIAS_UNRESOLVED | 243 | GlazedTerracottaBlock | Block | Block | — | — |
| `minecraft:cyan_glazed_terracotta` | EXACT_KEY_ONLY | 244 | GlazedTerracottaBlock | Block | Block | GlazedTerracottaBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:purple_glazed_terracotta` | EXACT_KEY_ONLY | 245 | GlazedTerracottaBlock | Block | Block | GlazedTerracottaBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:blue_glazed_terracotta` | EXACT_KEY_ONLY | 246 | GlazedTerracottaBlock | Block | Block | GlazedTerracottaBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:brown_glazed_terracotta` | EXACT_KEY_ONLY | 247 | GlazedTerracottaBlock | Block | Block | GlazedTerracottaBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:green_glazed_terracotta` | EXACT_KEY_ONLY | 248 | GlazedTerracottaBlock | Block | Block | GlazedTerracottaBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:red_glazed_terracotta` | EXACT_KEY_ONLY | 249 | GlazedTerracottaBlock | Block | Block | GlazedTerracottaBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:black_glazed_terracotta` | EXACT_KEY_ONLY | 250 | GlazedTerracottaBlock | Block | Block | GlazedTerracottaBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:concrete` | UNMATCHED_ALIAS_UNRESOLVED | 251 | ColoredBlock | Block | Block | — | — |
| `minecraft:concrete_powder` | UNMATCHED_ALIAS_UNRESOLVED | 252 | ConcretePowderBlock | Block | Block | — | — |
| `minecraft:structure_block` | EXACT_KEY_ONLY | 255 | StructureBlock | Block | Block | StructureBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:acacia_button` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | WoodenButtonBlock | Block.getCollisionShape → ButtonBlock.getShape |
| `minecraft:acacia_leaves` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | LeavesBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:acacia_log` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | AbstractLogBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:acacia_planks` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:acacia_pressure_plate` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | PressurePlateBlock | Block.getCollisionShape → AbstractPressurePlateBlock.getShape |
| `minecraft:acacia_sapling` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | SaplingBlock | Block.getCollisionShape → SaplingBlock.getShape |
| `minecraft:acacia_slab` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | SlabBlock | Block.getCollisionShape → SlabBlock.getShape |
| `minecraft:acacia_trapdoor` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | TrapdoorBlock | Block.getCollisionShape → TrapdoorBlock.getShape |
| `minecraft:acacia_wood` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | AxisBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:allium` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FlowerBlock | Block.getCollisionShape → FlowerBlock.getShape |
| `minecraft:andesite` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:attached_melon_stem` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | AttachedPlantStem | Block.getCollisionShape → AttachedPlantStem.getShape |
| `minecraft:attached_pumpkin_stem` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | AttachedPlantStem | Block.getCollisionShape → AttachedPlantStem.getShape |
| `minecraft:azure_bluet` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FlowerBlock | Block.getCollisionShape → FlowerBlock.getShape |
| `minecraft:birch_button` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | WoodenButtonBlock | Block.getCollisionShape → ButtonBlock.getShape |
| `minecraft:birch_leaves` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | LeavesBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:birch_log` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | AbstractLogBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:birch_planks` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:birch_pressure_plate` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | PressurePlateBlock | Block.getCollisionShape → AbstractPressurePlateBlock.getShape |
| `minecraft:birch_sapling` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | SaplingBlock | Block.getCollisionShape → SaplingBlock.getShape |
| `minecraft:birch_slab` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | SlabBlock | Block.getCollisionShape → SlabBlock.getShape |
| `minecraft:birch_trapdoor` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | TrapdoorBlock | Block.getCollisionShape → TrapdoorBlock.getShape |
| `minecraft:birch_wood` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | AxisBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:black_banner` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StandingBannerBlock | Block.getCollisionShape → StandingBannerBlock.getShape |
| `minecraft:black_bed` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | BedBlock | Block.getCollisionShape → BedBlock.getShape |
| `minecraft:black_carpet` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CarpetBlock | Block.getCollisionShape → CarpetBlock.getShape |
| `minecraft:black_concrete` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:black_concrete_powder` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | ConcretePowderBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:black_stained_glass` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StainedGlassBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:black_stained_glass_pane` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StainedGlassPaneBlock | PaneBlock.getCollisionShape |
| `minecraft:black_terracotta` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:black_wall_banner` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | WallBannerBlock | Block.getCollisionShape → WallBannerBlock.getShape |
| `minecraft:black_wool` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:blue_banner` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StandingBannerBlock | Block.getCollisionShape → StandingBannerBlock.getShape |
| `minecraft:blue_bed` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | BedBlock | Block.getCollisionShape → BedBlock.getShape |
| `minecraft:blue_carpet` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CarpetBlock | Block.getCollisionShape → CarpetBlock.getShape |
| `minecraft:blue_concrete` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:blue_concrete_powder` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | ConcretePowderBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:blue_ice` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | BlueIceBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:blue_orchid` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FlowerBlock | Block.getCollisionShape → FlowerBlock.getShape |
| `minecraft:blue_stained_glass` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StainedGlassBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:blue_stained_glass_pane` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StainedGlassPaneBlock | PaneBlock.getCollisionShape |
| `minecraft:blue_terracotta` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:blue_wall_banner` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | WallBannerBlock | Block.getCollisionShape → WallBannerBlock.getShape |
| `minecraft:blue_wool` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:brain_coral` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CoralPlantBlock | Block.getCollisionShape → CoralPlantBlock.getShape |
| `minecraft:brain_coral_block` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CoralBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:brain_coral_fan` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CoralFanBlock | Block.getCollisionShape → BaseCoralFanBlock.getShape |
| `minecraft:brain_coral_wall_fan` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CoralWallFanBlock | Block.getCollisionShape → BaseCoralWallFanBlock.getShape |
| `minecraft:brick_slab` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | SlabBlock | Block.getCollisionShape → SlabBlock.getShape |
| `minecraft:bricks` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:brown_banner` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StandingBannerBlock | Block.getCollisionShape → StandingBannerBlock.getShape |
| `minecraft:brown_bed` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | BedBlock | Block.getCollisionShape → BedBlock.getShape |
| `minecraft:brown_carpet` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CarpetBlock | Block.getCollisionShape → CarpetBlock.getShape |
| `minecraft:brown_concrete` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:brown_concrete_powder` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | ConcretePowderBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:brown_stained_glass` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StainedGlassBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:brown_stained_glass_pane` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StainedGlassPaneBlock | PaneBlock.getCollisionShape |
| `minecraft:brown_terracotta` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:brown_wall_banner` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | WallBannerBlock | Block.getCollisionShape → WallBannerBlock.getShape |
| `minecraft:brown_wool` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:bubble_column` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | BubbleColumnBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:bubble_coral` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CoralPlantBlock | Block.getCollisionShape → CoralPlantBlock.getShape |
| `minecraft:bubble_coral_block` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CoralBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:bubble_coral_fan` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CoralFanBlock | Block.getCollisionShape → BaseCoralFanBlock.getShape |
| `minecraft:bubble_coral_wall_fan` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CoralWallFanBlock | Block.getCollisionShape → BaseCoralWallFanBlock.getShape |
| `minecraft:carved_pumpkin` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CarvedPumpkinBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:cave_air` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | AirBlock | Block.getCollisionShape → AirBlock.getShape |
| `minecraft:chipped_anvil` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | AnvilBlock | Block.getCollisionShape → AnvilBlock.getShape |
| `minecraft:chiseled_quartz_block` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:chiseled_red_sandstone` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:chiseled_sandstone` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:chiseled_stone_bricks` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:coarse_dirt` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:cobblestone_slab` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | SlabBlock | Block.getCollisionShape → SlabBlock.getShape |
| `minecraft:cobblestone_stairs` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StairsBlock | Block.getCollisionShape → StairsBlock.getShape |
| `minecraft:cobweb` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CobwebBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:comparator` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | ComparatorBlock | Block.getCollisionShape → DiodeBlock.getShape |
| `minecraft:conduit` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | ConduitBlock | Block.getCollisionShape → ConduitBlock.getShape |
| `minecraft:cracked_stone_bricks` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:creeper_head` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FloorSkullBlock | Block.getCollisionShape → FloorSkullBlock.getShape |
| `minecraft:creeper_wall_head` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | WallSkullBlock | Block.getCollisionShape → WallSkullBlock.getShape |
| `minecraft:cut_red_sandstone` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:cut_sandstone` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:cyan_banner` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StandingBannerBlock | Block.getCollisionShape → StandingBannerBlock.getShape |
| `minecraft:cyan_bed` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | BedBlock | Block.getCollisionShape → BedBlock.getShape |
| `minecraft:cyan_carpet` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CarpetBlock | Block.getCollisionShape → CarpetBlock.getShape |
| `minecraft:cyan_concrete` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:cyan_concrete_powder` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | ConcretePowderBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:cyan_stained_glass` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StainedGlassBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:cyan_stained_glass_pane` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StainedGlassPaneBlock | PaneBlock.getCollisionShape |
| `minecraft:cyan_terracotta` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:cyan_wall_banner` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | WallBannerBlock | Block.getCollisionShape → WallBannerBlock.getShape |
| `minecraft:cyan_wool` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:damaged_anvil` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | AnvilBlock | Block.getCollisionShape → AnvilBlock.getShape |
| `minecraft:dandelion` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FlowerBlock | Block.getCollisionShape → FlowerBlock.getShape |
| `minecraft:dark_oak_button` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | WoodenButtonBlock | Block.getCollisionShape → ButtonBlock.getShape |
| `minecraft:dark_oak_leaves` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | LeavesBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:dark_oak_log` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | AbstractLogBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:dark_oak_planks` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:dark_oak_pressure_plate` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | PressurePlateBlock | Block.getCollisionShape → AbstractPressurePlateBlock.getShape |
| `minecraft:dark_oak_sapling` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | SaplingBlock | Block.getCollisionShape → SaplingBlock.getShape |
| `minecraft:dark_oak_slab` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | SlabBlock | Block.getCollisionShape → SlabBlock.getShape |
| `minecraft:dark_oak_trapdoor` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | TrapdoorBlock | Block.getCollisionShape → TrapdoorBlock.getShape |
| `minecraft:dark_oak_wood` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | AxisBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:dark_prismarine` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:dark_prismarine_slab` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | SlabBlock | Block.getCollisionShape → SlabBlock.getShape |
| `minecraft:dark_prismarine_stairs` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StairsBlock | Block.getCollisionShape → StairsBlock.getShape |
| `minecraft:dead_brain_coral` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | BaseCoralPlantBlock | Block.getCollisionShape → BaseCoralPlantBlock.getShape |
| `minecraft:dead_brain_coral_block` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:dead_brain_coral_fan` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | BaseCoralFanBlock | Block.getCollisionShape → BaseCoralFanBlock.getShape |
| `minecraft:dead_brain_coral_wall_fan` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | BaseCoralWallFanBlock | Block.getCollisionShape → BaseCoralWallFanBlock.getShape |
| `minecraft:dead_bubble_coral` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | BaseCoralPlantBlock | Block.getCollisionShape → BaseCoralPlantBlock.getShape |
| `minecraft:dead_bubble_coral_block` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:dead_bubble_coral_fan` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | BaseCoralFanBlock | Block.getCollisionShape → BaseCoralFanBlock.getShape |
| `minecraft:dead_bubble_coral_wall_fan` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | BaseCoralWallFanBlock | Block.getCollisionShape → BaseCoralWallFanBlock.getShape |
| `minecraft:dead_bush` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | DeadBushBlock | Block.getCollisionShape → DeadBushBlock.getShape |
| `minecraft:dead_fire_coral` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | BaseCoralPlantBlock | Block.getCollisionShape → BaseCoralPlantBlock.getShape |
| `minecraft:dead_fire_coral_block` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:dead_fire_coral_fan` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | BaseCoralFanBlock | Block.getCollisionShape → BaseCoralFanBlock.getShape |
| `minecraft:dead_fire_coral_wall_fan` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | BaseCoralWallFanBlock | Block.getCollisionShape → BaseCoralWallFanBlock.getShape |
| `minecraft:dead_horn_coral` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | BaseCoralPlantBlock | Block.getCollisionShape → BaseCoralPlantBlock.getShape |
| `minecraft:dead_horn_coral_block` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:dead_horn_coral_fan` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | BaseCoralFanBlock | Block.getCollisionShape → BaseCoralFanBlock.getShape |
| `minecraft:dead_horn_coral_wall_fan` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | BaseCoralWallFanBlock | Block.getCollisionShape → BaseCoralWallFanBlock.getShape |
| `minecraft:dead_tube_coral` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | BaseCoralPlantBlock | Block.getCollisionShape → BaseCoralPlantBlock.getShape |
| `minecraft:dead_tube_coral_block` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:dead_tube_coral_fan` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | BaseCoralFanBlock | Block.getCollisionShape → BaseCoralFanBlock.getShape |
| `minecraft:dead_tube_coral_wall_fan` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | BaseCoralWallFanBlock | Block.getCollisionShape → BaseCoralWallFanBlock.getShape |
| `minecraft:diorite` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:dragon_head` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FloorSkullBlock | Block.getCollisionShape → FloorSkullBlock.getShape |
| `minecraft:dragon_wall_head` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | WallSkullBlock | Block.getCollisionShape → WallSkullBlock.getShape |
| `minecraft:dried_kelp_block` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:end_stone_bricks` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:fern` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | TallPlantBlock | Block.getCollisionShape → TallPlantBlock.getShape |
| `minecraft:fire_coral` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CoralPlantBlock | Block.getCollisionShape → CoralPlantBlock.getShape |
| `minecraft:fire_coral_block` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CoralBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:fire_coral_fan` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CoralFanBlock | Block.getCollisionShape → BaseCoralFanBlock.getShape |
| `minecraft:fire_coral_wall_fan` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CoralWallFanBlock | Block.getCollisionShape → BaseCoralWallFanBlock.getShape |
| `minecraft:granite` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:grass_block` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | GrassBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:gray_banner` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StandingBannerBlock | Block.getCollisionShape → StandingBannerBlock.getShape |
| `minecraft:gray_bed` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | BedBlock | Block.getCollisionShape → BedBlock.getShape |
| `minecraft:gray_carpet` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CarpetBlock | Block.getCollisionShape → CarpetBlock.getShape |
| `minecraft:gray_concrete` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:gray_concrete_powder` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | ConcretePowderBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:gray_stained_glass` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StainedGlassBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:gray_stained_glass_pane` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StainedGlassPaneBlock | PaneBlock.getCollisionShape |
| `minecraft:gray_terracotta` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:gray_wall_banner` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | WallBannerBlock | Block.getCollisionShape → WallBannerBlock.getShape |
| `minecraft:gray_wool` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:green_banner` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StandingBannerBlock | Block.getCollisionShape → StandingBannerBlock.getShape |
| `minecraft:green_bed` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | BedBlock | Block.getCollisionShape → BedBlock.getShape |
| `minecraft:green_carpet` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CarpetBlock | Block.getCollisionShape → CarpetBlock.getShape |
| `minecraft:green_concrete` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:green_concrete_powder` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | ConcretePowderBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:green_stained_glass` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StainedGlassBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:green_stained_glass_pane` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StainedGlassPaneBlock | PaneBlock.getCollisionShape |
| `minecraft:green_terracotta` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:green_wall_banner` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | WallBannerBlock | Block.getCollisionShape → WallBannerBlock.getShape |
| `minecraft:green_wool` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:horn_coral` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CoralPlantBlock | Block.getCollisionShape → CoralPlantBlock.getShape |
| `minecraft:horn_coral_block` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CoralBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:horn_coral_fan` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CoralFanBlock | Block.getCollisionShape → BaseCoralFanBlock.getShape |
| `minecraft:horn_coral_wall_fan` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CoralWallFanBlock | Block.getCollisionShape → BaseCoralWallFanBlock.getShape |
| `minecraft:infested_chiseled_stone_bricks` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | InfestedBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:infested_cobblestone` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | InfestedBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:infested_cracked_stone_bricks` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | InfestedBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:infested_mossy_stone_bricks` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | InfestedBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:infested_stone` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | InfestedBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:infested_stone_bricks` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | InfestedBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:jack_o_lantern` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CarvedPumpkinBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:jungle_button` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | WoodenButtonBlock | Block.getCollisionShape → ButtonBlock.getShape |
| `minecraft:jungle_leaves` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | LeavesBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:jungle_log` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | AbstractLogBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:jungle_planks` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:jungle_pressure_plate` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | PressurePlateBlock | Block.getCollisionShape → AbstractPressurePlateBlock.getShape |
| `minecraft:jungle_sapling` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | SaplingBlock | Block.getCollisionShape → SaplingBlock.getShape |
| `minecraft:jungle_slab` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | SlabBlock | Block.getCollisionShape → SlabBlock.getShape |
| `minecraft:jungle_trapdoor` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | TrapdoorBlock | Block.getCollisionShape → TrapdoorBlock.getShape |
| `minecraft:jungle_wood` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | AxisBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:kelp` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | KelpBlock | Block.getCollisionShape → KelpBlock.getShape |
| `minecraft:kelp_plant` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | KelpPlantBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:large_fern` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | DoublePlantBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:light_blue_banner` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StandingBannerBlock | Block.getCollisionShape → StandingBannerBlock.getShape |
| `minecraft:light_blue_bed` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | BedBlock | Block.getCollisionShape → BedBlock.getShape |
| `minecraft:light_blue_carpet` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CarpetBlock | Block.getCollisionShape → CarpetBlock.getShape |
| `minecraft:light_blue_concrete` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:light_blue_concrete_powder` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | ConcretePowderBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:light_blue_stained_glass` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StainedGlassBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:light_blue_stained_glass_pane` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StainedGlassPaneBlock | PaneBlock.getCollisionShape |
| `minecraft:light_blue_terracotta` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:light_blue_wall_banner` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | WallBannerBlock | Block.getCollisionShape → WallBannerBlock.getShape |
| `minecraft:light_blue_wool` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:light_gray_banner` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StandingBannerBlock | Block.getCollisionShape → StandingBannerBlock.getShape |
| `minecraft:light_gray_bed` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | BedBlock | Block.getCollisionShape → BedBlock.getShape |
| `minecraft:light_gray_carpet` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CarpetBlock | Block.getCollisionShape → CarpetBlock.getShape |
| `minecraft:light_gray_concrete` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:light_gray_concrete_powder` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | ConcretePowderBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:light_gray_glazed_terracotta` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | GlazedTerracottaBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:light_gray_shulker_box` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | ShulkerBoxBlock | Block.getCollisionShape → ShulkerBoxBlock.getShape |
| `minecraft:light_gray_stained_glass` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StainedGlassBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:light_gray_stained_glass_pane` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StainedGlassPaneBlock | PaneBlock.getCollisionShape |
| `minecraft:light_gray_terracotta` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:light_gray_wall_banner` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | WallBannerBlock | Block.getCollisionShape → WallBannerBlock.getShape |
| `minecraft:light_gray_wool` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:lilac` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | TallFlowerBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:lily_pad` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | LilyPadBlock | Block.getCollisionShape → LilyPadBlock.getShape |
| `minecraft:lime_banner` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StandingBannerBlock | Block.getCollisionShape → StandingBannerBlock.getShape |
| `minecraft:lime_bed` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | BedBlock | Block.getCollisionShape → BedBlock.getShape |
| `minecraft:lime_carpet` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CarpetBlock | Block.getCollisionShape → CarpetBlock.getShape |
| `minecraft:lime_concrete` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:lime_concrete_powder` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | ConcretePowderBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:lime_stained_glass` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StainedGlassBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:lime_stained_glass_pane` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StainedGlassPaneBlock | PaneBlock.getCollisionShape |
| `minecraft:lime_terracotta` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:lime_wall_banner` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | WallBannerBlock | Block.getCollisionShape → WallBannerBlock.getShape |
| `minecraft:lime_wool` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:magenta_banner` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StandingBannerBlock | Block.getCollisionShape → StandingBannerBlock.getShape |
| `minecraft:magenta_bed` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | BedBlock | Block.getCollisionShape → BedBlock.getShape |
| `minecraft:magenta_carpet` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CarpetBlock | Block.getCollisionShape → CarpetBlock.getShape |
| `minecraft:magenta_concrete` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:magenta_concrete_powder` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | ConcretePowderBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:magenta_stained_glass` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StainedGlassBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:magenta_stained_glass_pane` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StainedGlassPaneBlock | PaneBlock.getCollisionShape |
| `minecraft:magenta_terracotta` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:magenta_wall_banner` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | WallBannerBlock | Block.getCollisionShape → WallBannerBlock.getShape |
| `minecraft:magenta_wool` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:magma_block` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | MagmaBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:melon` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | MelonBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:mossy_cobblestone_wall` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | WallBlock | WallBlock.getCollisionShape |
| `minecraft:mossy_stone_bricks` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:moving_piston` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | MovingBlock | MovingBlock.getCollisionShape |
| `minecraft:mushroom_stem` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | MushroomBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:nether_brick_slab` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | SlabBlock | Block.getCollisionShape → SlabBlock.getShape |
| `minecraft:nether_bricks` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:nether_portal` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | PortalBlock | Block.getCollisionShape → PortalBlock.getShape |
| `minecraft:nether_quartz_ore` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | OreBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:note_block` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | NoteBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:oak_button` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | WoodenButtonBlock | Block.getCollisionShape → ButtonBlock.getShape |
| `minecraft:oak_door` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | DoorBlock | Block.getCollisionShape → DoorBlock.getShape |
| `minecraft:oak_fence` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FenceBlock | PaneBlock.getCollisionShape |
| `minecraft:oak_fence_gate` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FenceGateBlock | FenceGateBlock.getCollisionShape |
| `minecraft:oak_leaves` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | LeavesBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:oak_log` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | AbstractLogBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:oak_planks` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:oak_pressure_plate` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | PressurePlateBlock | Block.getCollisionShape → AbstractPressurePlateBlock.getShape |
| `minecraft:oak_sapling` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | SaplingBlock | Block.getCollisionShape → SaplingBlock.getShape |
| `minecraft:oak_slab` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | SlabBlock | Block.getCollisionShape → SlabBlock.getShape |
| `minecraft:oak_trapdoor` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | TrapdoorBlock | Block.getCollisionShape → TrapdoorBlock.getShape |
| `minecraft:oak_wood` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | AxisBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:orange_banner` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StandingBannerBlock | Block.getCollisionShape → StandingBannerBlock.getShape |
| `minecraft:orange_bed` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | BedBlock | Block.getCollisionShape → BedBlock.getShape |
| `minecraft:orange_carpet` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CarpetBlock | Block.getCollisionShape → CarpetBlock.getShape |
| `minecraft:orange_concrete` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:orange_concrete_powder` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | ConcretePowderBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:orange_stained_glass` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StainedGlassBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:orange_stained_glass_pane` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StainedGlassPaneBlock | PaneBlock.getCollisionShape |
| `minecraft:orange_terracotta` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:orange_tulip` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FlowerBlock | Block.getCollisionShape → FlowerBlock.getShape |
| `minecraft:orange_wall_banner` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | WallBannerBlock | Block.getCollisionShape → WallBannerBlock.getShape |
| `minecraft:orange_wool` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:oxeye_daisy` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FlowerBlock | Block.getCollisionShape → FlowerBlock.getShape |
| `minecraft:peony` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | TallFlowerBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:petrified_oak_slab` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | SlabBlock | Block.getCollisionShape → SlabBlock.getShape |
| `minecraft:pink_banner` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StandingBannerBlock | Block.getCollisionShape → StandingBannerBlock.getShape |
| `minecraft:pink_bed` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | BedBlock | Block.getCollisionShape → BedBlock.getShape |
| `minecraft:pink_carpet` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CarpetBlock | Block.getCollisionShape → CarpetBlock.getShape |
| `minecraft:pink_concrete` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:pink_concrete_powder` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | ConcretePowderBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:pink_stained_glass` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StainedGlassBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:pink_stained_glass_pane` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StainedGlassPaneBlock | PaneBlock.getCollisionShape |
| `minecraft:pink_terracotta` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:pink_tulip` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FlowerBlock | Block.getCollisionShape → FlowerBlock.getShape |
| `minecraft:pink_wall_banner` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | WallBannerBlock | Block.getCollisionShape → WallBannerBlock.getShape |
| `minecraft:pink_wool` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:player_head` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | PlayerFloorSkullBlock | Block.getCollisionShape → FloorSkullBlock.getShape |
| `minecraft:player_wall_head` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | PlayerWallSkullBlock | Block.getCollisionShape → WallSkullBlock.getShape |
| `minecraft:podzol` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | PodzolBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:polished_andesite` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:polished_diorite` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:polished_granite` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:poppy` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FlowerBlock | Block.getCollisionShape → FlowerBlock.getShape |
| `minecraft:potted_acacia_sapling` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FlowerPotBlock | Block.getCollisionShape → FlowerPotBlock.getShape |
| `minecraft:potted_allium` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FlowerPotBlock | Block.getCollisionShape → FlowerPotBlock.getShape |
| `minecraft:potted_azure_bluet` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FlowerPotBlock | Block.getCollisionShape → FlowerPotBlock.getShape |
| `minecraft:potted_birch_sapling` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FlowerPotBlock | Block.getCollisionShape → FlowerPotBlock.getShape |
| `minecraft:potted_blue_orchid` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FlowerPotBlock | Block.getCollisionShape → FlowerPotBlock.getShape |
| `minecraft:potted_brown_mushroom` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FlowerPotBlock | Block.getCollisionShape → FlowerPotBlock.getShape |
| `minecraft:potted_cactus` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FlowerPotBlock | Block.getCollisionShape → FlowerPotBlock.getShape |
| `minecraft:potted_dandelion` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FlowerPotBlock | Block.getCollisionShape → FlowerPotBlock.getShape |
| `minecraft:potted_dark_oak_sapling` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FlowerPotBlock | Block.getCollisionShape → FlowerPotBlock.getShape |
| `minecraft:potted_dead_bush` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FlowerPotBlock | Block.getCollisionShape → FlowerPotBlock.getShape |
| `minecraft:potted_fern` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FlowerPotBlock | Block.getCollisionShape → FlowerPotBlock.getShape |
| `minecraft:potted_jungle_sapling` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FlowerPotBlock | Block.getCollisionShape → FlowerPotBlock.getShape |
| `minecraft:potted_oak_sapling` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FlowerPotBlock | Block.getCollisionShape → FlowerPotBlock.getShape |
| `minecraft:potted_orange_tulip` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FlowerPotBlock | Block.getCollisionShape → FlowerPotBlock.getShape |
| `minecraft:potted_oxeye_daisy` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FlowerPotBlock | Block.getCollisionShape → FlowerPotBlock.getShape |
| `minecraft:potted_pink_tulip` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FlowerPotBlock | Block.getCollisionShape → FlowerPotBlock.getShape |
| `minecraft:potted_poppy` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FlowerPotBlock | Block.getCollisionShape → FlowerPotBlock.getShape |
| `minecraft:potted_red_mushroom` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FlowerPotBlock | Block.getCollisionShape → FlowerPotBlock.getShape |
| `minecraft:potted_red_tulip` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FlowerPotBlock | Block.getCollisionShape → FlowerPotBlock.getShape |
| `minecraft:potted_spruce_sapling` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FlowerPotBlock | Block.getCollisionShape → FlowerPotBlock.getShape |
| `minecraft:potted_white_tulip` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FlowerPotBlock | Block.getCollisionShape → FlowerPotBlock.getShape |
| `minecraft:powered_rail` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | PoweredRailBlock | Block.getCollisionShape → AbstractRailBlock.getShape |
| `minecraft:prismarine_brick_slab` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | SlabBlock | Block.getCollisionShape → SlabBlock.getShape |
| `minecraft:prismarine_brick_stairs` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StairsBlock | Block.getCollisionShape → StairsBlock.getShape |
| `minecraft:prismarine_bricks` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:prismarine_slab` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | SlabBlock | Block.getCollisionShape → SlabBlock.getShape |
| `minecraft:prismarine_stairs` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StairsBlock | Block.getCollisionShape → StairsBlock.getShape |
| `minecraft:purple_banner` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StandingBannerBlock | Block.getCollisionShape → StandingBannerBlock.getShape |
| `minecraft:purple_bed` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | BedBlock | Block.getCollisionShape → BedBlock.getShape |
| `minecraft:purple_carpet` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CarpetBlock | Block.getCollisionShape → CarpetBlock.getShape |
| `minecraft:purple_concrete` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:purple_concrete_powder` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | ConcretePowderBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:purple_stained_glass` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StainedGlassBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:purple_stained_glass_pane` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StainedGlassPaneBlock | PaneBlock.getCollisionShape |
| `minecraft:purple_terracotta` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:purple_wall_banner` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | WallBannerBlock | Block.getCollisionShape → WallBannerBlock.getShape |
| `minecraft:purple_wool` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:quartz_pillar` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | AxisBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:quartz_slab` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | SlabBlock | Block.getCollisionShape → SlabBlock.getShape |
| `minecraft:red_banner` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StandingBannerBlock | Block.getCollisionShape → StandingBannerBlock.getShape |
| `minecraft:red_bed` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | BedBlock | Block.getCollisionShape → BedBlock.getShape |
| `minecraft:red_carpet` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CarpetBlock | Block.getCollisionShape → CarpetBlock.getShape |
| `minecraft:red_concrete` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:red_concrete_powder` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | ConcretePowderBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:red_nether_bricks` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:red_sand` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | SandBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:red_sandstone_slab` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | SlabBlock | Block.getCollisionShape → SlabBlock.getShape |
| `minecraft:red_stained_glass` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StainedGlassBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:red_stained_glass_pane` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StainedGlassPaneBlock | PaneBlock.getCollisionShape |
| `minecraft:red_terracotta` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:red_tulip` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FlowerBlock | Block.getCollisionShape → FlowerBlock.getShape |
| `minecraft:red_wall_banner` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | WallBannerBlock | Block.getCollisionShape → WallBannerBlock.getShape |
| `minecraft:red_wool` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:redstone_wall_torch` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | RedstoneWallTorchBlock | Block.getCollisionShape → RedstoneWallTorchBlock.getShape |
| `minecraft:repeater` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | RepeaterBlock | Block.getCollisionShape → DiodeBlock.getShape |
| `minecraft:rose_bush` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | TallFlowerBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:sandstone_slab` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | SlabBlock | Block.getCollisionShape → SlabBlock.getShape |
| `minecraft:sea_pickle` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | SeaPickleBlock | Block.getCollisionShape → SeaPickleBlock.getShape |
| `minecraft:seagrass` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | SeaGrassBlock | Block.getCollisionShape → SeaGrassBlock.getShape |
| `minecraft:shulker_box` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | ShulkerBoxBlock | Block.getCollisionShape → ShulkerBoxBlock.getShape |
| `minecraft:sign` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StandingSignBlock | Block.getCollisionShape → SignBlock.getShape |
| `minecraft:skeleton_skull` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FloorSkullBlock | Block.getCollisionShape → FloorSkullBlock.getShape |
| `minecraft:skeleton_wall_skull` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | WallSkullBlock | Block.getCollisionShape → WallSkullBlock.getShape |
| `minecraft:slime_block` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | SlimeBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:smooth_quartz` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:smooth_red_sandstone` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:smooth_sandstone` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:smooth_stone` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:snow_block` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | SnowBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:spawner` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | MobSpawnerBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:spruce_button` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | WoodenButtonBlock | Block.getCollisionShape → ButtonBlock.getShape |
| `minecraft:spruce_leaves` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | LeavesBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:spruce_log` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | AbstractLogBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:spruce_planks` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:spruce_pressure_plate` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | PressurePlateBlock | Block.getCollisionShape → AbstractPressurePlateBlock.getShape |
| `minecraft:spruce_sapling` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | SaplingBlock | Block.getCollisionShape → SaplingBlock.getShape |
| `minecraft:spruce_slab` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | SlabBlock | Block.getCollisionShape → SlabBlock.getShape |
| `minecraft:spruce_trapdoor` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | TrapdoorBlock | Block.getCollisionShape → TrapdoorBlock.getShape |
| `minecraft:spruce_wood` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | AxisBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:stone_brick_slab` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | SlabBlock | Block.getCollisionShape → SlabBlock.getShape |
| `minecraft:stone_bricks` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:stripped_acacia_log` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | AbstractLogBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:stripped_acacia_wood` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | AxisBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:stripped_birch_log` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | AbstractLogBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:stripped_birch_wood` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | AxisBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:stripped_dark_oak_log` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | AbstractLogBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:stripped_dark_oak_wood` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | AxisBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:stripped_jungle_log` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | AbstractLogBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:stripped_jungle_wood` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | AxisBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:stripped_oak_log` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | AbstractLogBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:stripped_oak_wood` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | AxisBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:stripped_spruce_log` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | AbstractLogBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:stripped_spruce_wood` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | AxisBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:sugar_cane` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | SugarCaneBlock | Block.getCollisionShape → SugarCaneBlock.getShape |
| `minecraft:sunflower` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | TallFlowerBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:tall_grass` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | DoublePlantBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:tall_seagrass` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | TallSeaGrassBlock | Block.getCollisionShape → TallSeaGrassBlock.getShape |
| `minecraft:terracotta` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:tube_coral` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CoralPlantBlock | Block.getCollisionShape → CoralPlantBlock.getShape |
| `minecraft:tube_coral_block` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CoralBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:tube_coral_fan` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CoralFanBlock | Block.getCollisionShape → BaseCoralFanBlock.getShape |
| `minecraft:tube_coral_wall_fan` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CoralWallFanBlock | Block.getCollisionShape → BaseCoralWallFanBlock.getShape |
| `minecraft:turtle_egg` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | TurtleEggBlock | Block.getCollisionShape → TurtleEggBlock.getShape |
| `minecraft:void_air` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | AirBlock | Block.getCollisionShape → AirBlock.getShape |
| `minecraft:wall_torch` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | WallTorchBlock | Block.getCollisionShape → WallTorchBlock.getShape |
| `minecraft:wet_sponge` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | WetSpongeBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:white_banner` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StandingBannerBlock | Block.getCollisionShape → StandingBannerBlock.getShape |
| `minecraft:white_bed` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | BedBlock | Block.getCollisionShape → BedBlock.getShape |
| `minecraft:white_carpet` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CarpetBlock | Block.getCollisionShape → CarpetBlock.getShape |
| `minecraft:white_concrete` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:white_concrete_powder` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | ConcretePowderBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:white_stained_glass` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StainedGlassBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:white_stained_glass_pane` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StainedGlassPaneBlock | PaneBlock.getCollisionShape |
| `minecraft:white_terracotta` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:white_tulip` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FlowerBlock | Block.getCollisionShape → FlowerBlock.getShape |
| `minecraft:white_wall_banner` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | WallBannerBlock | Block.getCollisionShape → WallBannerBlock.getShape |
| `minecraft:white_wool` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:wither_skeleton_skull` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | WitherFloorSkullBlock | Block.getCollisionShape → FloorSkullBlock.getShape |
| `minecraft:wither_skeleton_wall_skull` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | WitherWallSkullBlock | Block.getCollisionShape → WallSkullBlock.getShape |
| `minecraft:yellow_banner` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StandingBannerBlock | Block.getCollisionShape → StandingBannerBlock.getShape |
| `minecraft:yellow_bed` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | BedBlock | Block.getCollisionShape → BedBlock.getShape |
| `minecraft:yellow_carpet` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | CarpetBlock | Block.getCollisionShape → CarpetBlock.getShape |
| `minecraft:yellow_concrete` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:yellow_concrete_powder` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | ConcretePowderBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:yellow_stained_glass` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StainedGlassBlock | Block.getCollisionShape → Block.getShape |
| `minecraft:yellow_stained_glass_pane` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | StainedGlassPaneBlock | PaneBlock.getCollisionShape |
| `minecraft:yellow_terracotta` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:yellow_wall_banner` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | WallBannerBlock | Block.getCollisionShape → WallBannerBlock.getShape |
| `minecraft:yellow_wool` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | Block | Block.getCollisionShape → Block.getShape |
| `minecraft:zombie_head` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | FloorSkullBlock | Block.getCollisionShape → FloorSkullBlock.getShape |
| `minecraft:zombie_wall_head` | UNMATCHED_ALIAS_UNRESOLVED | — | — | — | — | WallSkullBlock | Block.getCollisionShape → WallSkullBlock.getShape |

## What this crosswalk establishes

- Every old registry key appears once, and every new registry key absent from the old set is appended once. The parse verified 254 old keys, 598 new keys, 184 exact-key matches, 70 old-only keys and 414 new-only keys, with no duplicate parsed new keys.
- For each exact key match, the table aligns frozen concrete-class labels and dispatch columns as reported by each endpoint inventory. The old endpoint reports nearest effective `addCollisions` and `getCollisionShape` owners; the new endpoint reports its route through `getCollisionShape` and, where delegated, `getShape`. The columns remain separate; no one-to-one method equivalence is asserted.
- The 17 shared keys with differing registered concrete-class labels are listed above. A label difference or match is not promoted to a shape or movement conclusion.

## Open reconciliation gates

- **Aliases and renames:** all 70 old-only and 414 new-only rows remain `UNMATCHED_ALIAS_UNRESOLVED`. Neither frozen input provides a cross-release alias/rename relation. Inspect exact registration or migration evidence in a separately bounded follow-up before declaring old-only/new-only semantics. Do not match by English resemblance, numeric ID, or Java class name alone.
- **Secondary legacy getter dispatch:** the old inventory records the effective `getCollisionShape` owner per registered class. Where that owner is base `Block`, its body calls `state.getShape`; the accepted old-side table does not provide a per-key effective second-stage `getShape` owner. Those rows remain open for a getter-route crosswalk. The new inventory records the second-stage shape route.
- **Callbacks versus shape results:** no bodies, gates, constants, box/voxel decomposition, ordering, state values, or collision outcomes were compared. Old callback-owner and new shape-getter columns are different API layers, not an equivalence test.
- **Eligibility and context:** neither inventory resolves per-instance `hasCollision` for every registered key, all state/world/position inputs, dynamic neighbor dependencies, fluids/tags/resources, block-entity presence/type, or transient call context. `minecraft:moving_piston` has a block-entity dependency called out by the new inventory.
- **Declarations and resources:** this key crosswalk does not establish a complete paired list of unregistered provider declarations or movement-relevant resources/tags.
- **Cross-lane evidence:** transient, virtual-neighbor, shulker, bespoke, and other separately owned providers need their own immutable reviewed packets before provider-level closure. This key crosswalk does not acquire or duplicate those findings.
- **Fallback:** until aliases, effective dispatch, properties, provider bodies, and dependencies are closed by the proper owner, retain native behavior for unresolved cases. This is not a shape-equivalence claim, accepted movement finding, implementation authorization, source-pair closure, or runtime-parity result.
