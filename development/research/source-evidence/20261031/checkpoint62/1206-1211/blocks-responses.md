# Blind source notes: 1.20.6 to 1.21.1 block collision and direct player responses

Status: source-only draft. Collision core and selected direct-response routes were compared across 1.20.6 -> 1.21 -> 1.21.1. This is not full-pair freeze; block-provider registrations, callback dependencies and several response/event slices remain partial or open below.

## Exact inputs

- 1.20.6 Mojmap: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.20.6/mojmap`; `mojmap.ready.json` identifies versionId `1.20.6`, mapping `mojmap`, metadata ID `1.20.6`, 5,329 Java sources; source-manifest SHA256 `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`; relevant `net/minecraft/world/entity/Entity.java` SHA256 `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`.
- 1.21 Mojmap: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.21/mojmap`; embedded `.decompile-provenance.json` identifies 1.21 input metadata and Mojmap mappings; client.jar SHA256 `4b56bc9b44de7e88f9fbb52e716c18240da34469fcbafa5ed2e75b04d60617e8`; `Entity.java` SHA256 `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850`.
- 1.21.1 Mojmap: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.21.1/mojmap`; `mojmap.ready.json` identifies versionId `1.21.1`, mapping `mojmap`, metadata ID `1.21.1`, 5,363 Java sources; source-manifest SHA256 `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`; relevant `Entity.java` SHA256 is also `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850`.

The matching Entity.java hashes confirm the examined 1.21 and 1.21.1 entity collision implementation is identical. The 1.20.6 and 1.21.1 ready manifests and the 1.21 embedded provenance are the source identity records; all sources were read only.

## Proposed finding draft

### `entity-step-up-collision-search`

1.20.6 Mojmap: `net/minecraft/world/entity/Entity.java`, `collide(Vec3)` lines 874-898. When `maxUpStep() > 0`, the old route requires `onGround() || downward movement clipped`, then tries a single full-height `(x, maxUpStep, z)` collision and a vertical-only-then-horizontal fallback, comparing horizontal distance against the original collision result.

1.21 Mojmap: `Entity.java`, `collide(Vec3)` lines 875-904 and new `collectCandidateStepUpHeights(AABB, List<VoxelShape>, float, float)` lines 906-925. For horizontal collision with either downward clipping or on-ground state, it moves the starting box to the clipped Y only for the downward case, expands the candidate box upward by max step (or down `1.0E-5F` when initially grounded), gathers candidate obstacle heights from each collision shape's Y coordinates, casts each height to `float`, excludes negative heights and the clipped-Y value, stops above max step, de-duplicates in `FloatArraySet`, unstable-sorts, then tries each candidate in order and returns the first candidate with strictly greater horizontal distance. The selected rise is compensated against the original box's minY.

1.21.1 Mojmap: same `Entity.java` methods and lines as 1.21; same exact file hash. The change is present by 1.21 and unchanged in 1.21.1; no narrower patch boundary was inspected.

Movement meaning: LocalPlayer inherits Entity movement and reaches `Entity.move` -> `collide` whenever it moves with physics enabled; piston movement also enters the same route through `MoverType.PISTON`. For a reachable player with positive step height, horizontal clipping against non-full collision shapes can now test each actual shape Y coordinate and select among multiple rises, instead of the prior fixed max-step attempts. This changes the candidate set, float conversion/order and strict-distance selection that determine the resulting movement vector and player position. A predicted trajectory difference has not been runtime-verified.

### Collision route and block-shape inventory

- `Entity.move(MoverType, Vec3)` (starts at `Entity.java:597` in 1.20.6 and `:598` in 1.21/1.21.1; its post-move callback route reaches `checkInsideBlocks()` at lines 759/760 respectively) applies no-physics bypass, piston clamping, edge backoff, collision resolution, movement/ground collision flags, position and velocity updates, fall handling, `updateEntityAfterFallOn`, `stepOn`, and then `checkInsideBlocks`. The grounded helper was renamed from `setOnGroundWithKnownMovement` (1.20.6 `Entity.java:562`) to `setOnGroundWithMovement` (1.21+ `Entity.java:563`); its body and call-site behavior are unchanged.
- `Entity.collide` obtains entity collisions for the swept player box and calls `collideBoundingBox`; `collectColliders` adds entity collisions, nearby world-border shape and block collisions. `collideWithShapes` resolves Y, then X/Z in the smaller-magnitude-first order. This axis-resolution body is unchanged in the compared releases; 1.21 adds the candidate-rise route above.
- `CollisionGetter.getBlockCollisions(Entity, AABB)` (`world/level/CollisionGetter.java:83-85`) creates `BlockCollisions`. `BlockCollisions.computeNext()` (`world/level/BlockCollisions.java:65-95`) traverses the swept query cells using `Cursor3D`, obtains collision chunks, reads the state at each cursor position, applies large-shape/moving-piston filters, calls `BlockState.getCollisionShape(collisionGetter, pos, CollisionContext.of(entity))`, moves that shape to world coordinates and intersects it with the query box. This method and `CollisionGetter` route are unchanged across the three releases.
- `BlockBehaviour.BlockStateBase.getCollisionShape` (`world/level/block/state/BlockBehaviour.java:629-634`) uses cached collision shape when available, otherwise calls the block provider with the state, getter, position and context. Base `BlockBehaviour.getCollisionShape` delegates to `getShape`. `getOffset` changed representation from Optional to nullable function in 1.21, but retains the same evaluator and zero fallback; this accessor is not applied by `BlockCollisions` to collision shapes.
- 1.20.6 block provider files with explicit `getCollisionShape` overrides, which are the terminal explicit provider set found in the block source tree: `BambooStalkBlock`, `BellBlock`, `BigDripleafBlock`, `CactusBlock`, `ComposterBlock`, `CrossCollisionBlock`, `FenceGateBlock`, `GrindstoneBlock`, `HoneyBlock`, `LecternBlock`, `LiquidBlock`, `MudBlock`, `piston/MovingPistonBlock`, `PowderSnowBlock`, `ScaffoldingBlock`, `SculkShriekerBlock`, `SnowLayerBlock`, `SoulSandBlock`, `WallBlock`, `WallHangingSignBlock`, plus `state/BlockBehaviour` base implementation. Many more subclasses override `getShape`; their base collision provider resolves through this default unless they override collision shape directly.
- The registered state/provider path is `Blocks` static registration -> each registered block constructor and `BlockBehaviour.Properties` -> block state definition/default state -> `BlockStateBase.getCollisionShape` -> provider above. `Blocks.java` gained 1.21 registrations and moved the UPDATE_1_21 feature flags off the tuff slab/stairs/wall/polished-tuff/chiseled-tuff/tuff-bricks entries; no corresponding existing provider method body difference was found in this comparison. Full per-block registration-to-provider mapping is still open.
- In the 417 common block source files, the hash-different files that also declare `getShape` or `getCollisionShape` were enumerated and their shape method bodies compared after whitespace normalization; none of those bodies changed. This is useful negative coverage, not proof that every registered block's constructor/property/state route is closed. New 1.21-only block providers were not treated as historical blocks.

### Direct player responses and event routes checked

- `ClientPacketListener.handleSetEntityMotion` (1.20.6 `ClientPacketListener.java:515-522`, 1.21.1 `:517-524`) passes the packet's three components to `Entity.lerpMotion`; source appears to remove `/ 8000.0` from the handler in 1.21, but packet getters change from raw integer fields to `double` getters dividing by `8000.0`. The effective per-axis value and call are the same, so this is not a finding. The route is relevant if a raw valid motion packet addresses the local player; no producer proof is required.
- `ClientPacketListener.handleTeleportEntity` (`:533-548` in 1.20.6, `:535-550` in 1.21.1) is unchanged: sync packet position codec, then for entities not controlled by the local instance interpolate position/rotation and set on-ground. `handleMoveEntity` is likewise unchanged and explicitly skips locally controlled entities. `handleMovePlayer(ClientboundPlayerPositionPacket)` is the local authoritative position/rotation response; no change was identified in its body in this comparison.
- `handleBlockEvent` and `handleLevelEvent` continue to forward packets to `ClientLevel.blockEvent` / `levelEvent`; method bodies are unchanged. Piston direct player motion still reaches the common `Entity.move(MoverType.PISTON, ...)` collision route above. Exact `ClientLevel` event dispatch into piston/block callbacks remains partial.
- The 1.21 portal refactor replaces client-local `handleNetherPortalClient` with `handleConfusionTransitionEffect` plus cooldown processing (`LocalPlayer.java:650-651`, `823-849`) and replaces server-side `Entity.handleNetherPortal` with `handlePortal`/`PortalProcessor`. The LocalPlayer-specific branch drives the confusion transition effect; the server transition/destination producer is not part of client historical movement. The clientbound local player position response route is unchanged, so no changed direct portal movement response was isolated here.
- `CampfireBlock.entityInside` changed from the old in-fire damage source plus Frost Walker exclusion to the campfire damage source without that check (`CampfireBlock.java:102-106` in 1.21.1). This changes damage resolution only, explicitly excluded from player movement scope; it does not change this block's shape methods.

## Coverage status and next actions

- **Changed / proposed:** Entity step-up candidate selection and swept collision collection introduced in 1.21; verify its parity effect independently and retain the exact 1.20.6 -> 1.21 boundary only as an interval claim.
- **Compared unchanged slices:** `CollisionGetter.getBlockCollisions`, `BlockCollisions.computeNext`, `BlockStateBase.getCollisionShape`, explicit collision shape provider method bodies in common hash-different block files, `Entity.collideWithShapes`, the on-ground/support helper body, selected teleport/motion/move packet handlers, and the block/level event forwarding methods above.
- **Partial:** `Blocks.java` registration-to-provider mapping for every 1.20.6-era block; all `getShape`/property-built shape providers and their constructor properties; `ClientLevel` block/level event dispatch; neighbor-sensitive block-state property propagation and cached state dependencies; `stepOn`, `updateEntityAfterFallOn` and `checkInsideBlocks` callback implementations across the full registered block set; portal and other event response semantics beyond the selected packet routes.
- **Unexamined:** exhaustive player collision/predicate reads of neighboring blocks; complete water, climbable, bubble-column, powder-snow and other block event callback routes; every client event that can apply velocity/position/pose; every packet response body outside the selected handlers; every state/property registration delta that affects historical blocks. No finding of no-change is asserted for these slices.

Next bounded action: enumerate 1.20.6 `Blocks` registrations for era-existing IDs and match each class/properties to all `getShape` and `getCollisionShape` overrides, then audit `ClientLevel` event dispatch and player-touching callbacks by exact method. Independently review the proposed step-up operation and these terminal collision/provider slices before any acceptance or implementation.


## Residual closure

### Registration and provider closure

The `Blocks.java` initializer inventory was re-parsed from the 1.20.6 exact source, including balanced `register(...)` expressions: 1,054 names/IDs were found; all have a concrete constructor or a helper whose constructor is defined in the same file. The same ID set (1,054) exists in 1.21 and 1.21.1; the normalized ID-set differences for both adjacent comparisons are empty. There are 19 initializer expression changes from 1.20.6 to 1.21 and none from 1.21 to 1.21.1: `copper_bulb`, `copper_grate`, `frosted_ice`, `tuff_wall`, `copper_door`, `tuff_bricks`, `polished_tuff`, `tuff_stairs`, `chiseled_tuff`, `crafter`, `exposed_chiseled_copper`, `weathered_chiseled_copper`, `copper_trapdoor`, `oxidized_chiseled_copper`, `chiseled_copper`, `tuff_slab`, `vault`, `heavy_core`, and `trial_spawner`. The common change removes `requiredFeatures(UPDATE_1_21)` from these new/feature-gated registrations; `FROSTED_ICE` also stops setting `.randomTicks()`. No shape-provider method/field below changed in either adjacent comparison. `Blocks.java` concrete block registration and helper constructors are in `world/level/block/Blocks.java`; helper declarations are at lines 7679-7819 in 1.20.6 (including `bed`, `log`, `netherStem`, `stainedGlass`, `leaves`, `shulkerBox`, `pistonBase`, buttons, pots, candles and stair factories).

The following exhaustive grouping maps every 1.20.6-era registered ID to its concrete block class; each constructor/helper expression was also checked against the 1.21 and 1.21.1 registrations for the counts and initializer changes above. This grouping is the registration-to-provider index: classes with explicit shape providers are compared in the next subsection; classes inheriting `BlockBehaviour` use its base shape route.

| Concrete class | Registered IDs |
|---|---|
| `AirBlock` | air, void_air, cave_air |
| `AmethystBlock` | amethyst_block |
| `AmethystClusterBlock` | amethyst_cluster, large_amethyst_bud, medium_amethyst_bud, small_amethyst_bud |
| `AnvilBlock` | anvil, chipped_anvil, damaged_anvil |
| `AzaleaBlock` | azalea, flowering_azalea |
| `BambooSaplingBlock` | bamboo_sapling |
| `BambooStalkBlock` | bamboo |
| `BannerBlock` | white_banner, orange_banner, magenta_banner, light_blue_banner, yellow_banner, lime_banner, pink_banner, gray_banner, light_gray_banner, cyan_banner, purple_banner, blue_banner, brown_banner, green_banner, red_banner, black_banner |
| `BarrelBlock` | barrel |
| `BarrierBlock` | barrier |
| `BaseCoralFanBlock` | dead_tube_coral_fan, dead_brain_coral_fan, dead_bubble_coral_fan, dead_fire_coral_fan, dead_horn_coral_fan |
| `BaseCoralPlantBlock` | dead_tube_coral, dead_brain_coral, dead_bubble_coral, dead_fire_coral, dead_horn_coral |
| `BaseCoralWallFanBlock` | dead_tube_coral_wall_fan, dead_brain_coral_wall_fan, dead_bubble_coral_wall_fan, dead_fire_coral_wall_fan, dead_horn_coral_wall_fan |
| `BeaconBlock` | beacon |
| `BedBlock` | white_bed, orange_bed, magenta_bed, light_blue_bed, yellow_bed, lime_bed, pink_bed, gray_bed, light_gray_bed, cyan_bed, purple_bed, blue_bed, brown_bed, green_bed, red_bed, black_bed |
| `BeehiveBlock` | bee_nest, beehive |
| `BeetrootBlock` | beetroots |
| `BellBlock` | bell |
| `BigDripleafBlock` | big_dripleaf |
| `BigDripleafStemBlock` | big_dripleaf_stem |
| `BlastFurnaceBlock` | blast_furnace |
| `Block` | stone, granite, polished_granite, diorite, polished_diorite, andesite, polished_andesite, dirt, coarse_dirt, cobblestone, oak_planks, spruce_planks, birch_planks, jungle_planks, acacia_planks, cherry_planks, dark_oak_planks, mangrove_planks, bamboo_planks, bamboo_mosaic, bedrock, lapis_block, sandstone, chiseled_sandstone, cut_sandstone, white_wool, orange_wool, magenta_wool, light_blue_wool, yellow_wool, lime_wool, pink_wool, gray_wool, light_gray_wool, cyan_wool, purple_wool, blue_wool, brown_wool, green_wool, red_wool, black_wool, gold_block, iron_block, bricks, bookshelf, mossy_cobblestone, obsidian, diamond_block, snow_block, clay, soul_soil, glowstone, stone_bricks, mossy_stone_bricks, cracked_stone_bricks, chiseled_stone_bricks, packed_mud, mud_bricks, nether_bricks, end_stone, emerald_block, quartz_block, chiseled_quartz_block, white_terracotta, orange_terracotta, magenta_terracotta, light_blue_terracotta, yellow_terracotta, lime_terracotta, pink_terracotta, gray_terracotta, light_gray_terracotta, cyan_terracotta, purple_terracotta, blue_terracotta, brown_terracotta, green_terracotta, red_terracotta, black_terracotta, prismarine, prismarine_bricks, dark_prismarine, sea_lantern, terracotta, coal_block, packed_ice, red_sandstone, chiseled_red_sandstone, cut_red_sandstone, smooth_stone, smooth_sandstone, smooth_quartz, smooth_red_sandstone, purpur_block, end_stone_bricks, nether_wart_block, red_nether_bricks, white_concrete, orange_concrete, magenta_concrete, light_blue_concrete, yellow_concrete, lime_concrete, pink_concrete, gray_concrete, light_gray_concrete, cyan_concrete, purple_concrete, blue_concrete, brown_concrete, green_concrete, red_concrete, black_concrete, dried_kelp_block, dead_tube_coral_block, dead_brain_coral_block, dead_bubble_coral_block, dead_fire_coral_block, dead_horn_coral_block, warped_wart_block, shroomlight, crimson_planks, warped_planks, honeycomb_block, netherite_block, ancient_debris, lodestone, blackstone, polished_blackstone, polished_blackstone_bricks, cracked_polished_blackstone_bricks, chiseled_polished_blackstone, gilded_blackstone, chiseled_nether_bricks, cracked_nether_bricks, quartz_bricks, tuff, polished_tuff, chiseled_tuff, tuff_bricks, chiseled_tuff_bricks, calcite, waxed_oxidized_chiseled_copper, waxed_weathered_chiseled_copper, waxed_exposed_chiseled_copper, waxed_chiseled_copper, waxed_copper_block, waxed_weathered_copper, waxed_exposed_copper, waxed_oxidized_copper, waxed_oxidized_cut_copper, waxed_weathered_cut_copper, waxed_exposed_cut_copper, waxed_cut_copper, dripstone_block, cobbled_deepslate, polished_deepslate, deepslate_tiles, deepslate_bricks, chiseled_deepslate, cracked_deepslate_bricks, cracked_deepslate_tiles, smooth_basalt, raw_iron_block, raw_copper_block, raw_gold_block, reinforced_deepslate |
| `BrewingStandBlock` | brewing_stand |
| `BrushableBlock` | suspicious_sand, suspicious_gravel |
| `BubbleColumnBlock` | bubble_column |
| `BuddingAmethystBlock` | budding_amethyst |
| `ButtonBlock` | stone_button, oak_button, spruce_button, birch_button, jungle_button, acacia_button, cherry_button, dark_oak_button, mangrove_button, bamboo_button, crimson_button, warped_button, polished_blackstone_button |
| `CactusBlock` | cactus |
| `CakeBlock` | cake |
| `CalibratedSculkSensorBlock` | calibrated_sculk_sensor |
| `CampfireBlock` | campfire, soul_campfire |
| `CandleBlock` | candle, white_candle, orange_candle, magenta_candle, light_blue_candle, yellow_candle, lime_candle, pink_candle, gray_candle, light_gray_candle, cyan_candle, purple_candle, blue_candle, brown_candle, green_candle, red_candle, black_candle |
| `CandleCakeBlock` | candle_cake, white_candle_cake, orange_candle_cake, magenta_candle_cake, light_blue_candle_cake, yellow_candle_cake, lime_candle_cake, pink_candle_cake, gray_candle_cake, light_gray_candle_cake, cyan_candle_cake, purple_candle_cake, blue_candle_cake, brown_candle_cake, green_candle_cake, red_candle_cake, black_candle_cake |
| `CarpetBlock` | moss_carpet |
| `CarrotBlock` | carrots |
| `CartographyTableBlock` | cartography_table |
| `CarvedPumpkinBlock` | jack_o_lantern |
| `CauldronBlock` | cauldron |
| `CaveVinesBlock` | cave_vines |
| `CaveVinesPlantBlock` | cave_vines_plant |
| `CeilingHangingSignBlock` | oak_hanging_sign, spruce_hanging_sign, birch_hanging_sign, acacia_hanging_sign, cherry_hanging_sign, jungle_hanging_sign, dark_oak_hanging_sign, crimson_hanging_sign, warped_hanging_sign, mangrove_hanging_sign, bamboo_hanging_sign |
| `ChainBlock` | chain |
| `CherryLeavesBlock` | cherry_leaves |
| `ChestBlock` | chest |
| `ChiseledBookShelfBlock` | chiseled_bookshelf |
| `ChorusFlowerBlock` | chorus_flower |
| `ChorusPlantBlock` | chorus_plant |
| `CocoaBlock` | cocoa |
| `ColoredFallingBlock` | sand, red_sand, gravel |
| `CommandBlock` | command_block, repeating_command_block, chain_command_block |
| `ComparatorBlock` | comparator |
| `ComposterBlock` | composter |
| `ConcretePowderBlock` | white_concrete_powder, orange_concrete_powder, magenta_concrete_powder, light_blue_concrete_powder, yellow_concrete_powder, lime_concrete_powder, pink_concrete_powder, gray_concrete_powder, light_gray_concrete_powder, cyan_concrete_powder, purple_concrete_powder, blue_concrete_powder, brown_concrete_powder, green_concrete_powder, red_concrete_powder, black_concrete_powder |
| `ConduitBlock` | conduit |
| `CopperBulbBlock` | waxed_copper_bulb, waxed_exposed_copper_bulb, waxed_weathered_copper_bulb, waxed_oxidized_copper_bulb |
| `CoralBlock` | tube_coral_block, brain_coral_block, bubble_coral_block, fire_coral_block, horn_coral_block |
| `CoralFanBlock` | tube_coral_fan, brain_coral_fan, bubble_coral_fan, fire_coral_fan, horn_coral_fan |
| `CoralPlantBlock` | tube_coral, brain_coral, bubble_coral, fire_coral, horn_coral |
| `CoralWallFanBlock` | tube_coral_wall_fan, brain_coral_wall_fan, bubble_coral_wall_fan, fire_coral_wall_fan, horn_coral_wall_fan |
| `CrafterBlock` | crafter |
| `CraftingTableBlock` | crafting_table |
| `CropBlock` | wheat |
| `CryingObsidianBlock` | crying_obsidian |
| `DaylightDetectorBlock` | daylight_detector |
| `DeadBushBlock` | dead_bush |
| `DecoratedPotBlock` | decorated_pot |
| `DetectorRailBlock` | detector_rail |
| `DirtPathBlock` | dirt_path |
| `DispenserBlock` | dispenser |
| `DoorBlock` | oak_door, iron_door, spruce_door, birch_door, jungle_door, acacia_door, cherry_door, dark_oak_door, mangrove_door, bamboo_door, crimson_door, warped_door, waxed_copper_door, waxed_exposed_copper_door, waxed_oxidized_copper_door, waxed_weathered_copper_door |
| `DoublePlantBlock` | tall_grass, large_fern, pitcher_plant |
| `DragonEggBlock` | dragon_egg |
| `DropExperienceBlock` | gold_ore, deepslate_gold_ore, iron_ore, deepslate_iron_ore, coal_ore, deepslate_coal_ore, nether_gold_ore, lapis_ore, deepslate_lapis_ore, diamond_ore, deepslate_diamond_ore, emerald_ore, deepslate_emerald_ore, nether_quartz_ore, copper_ore, deepslate_copper_ore |
| `DropperBlock` | dropper |
| `EnchantingTableBlock` | enchanting_table |
| `EnderChestBlock` | ender_chest |
| `EndGatewayBlock` | end_gateway |
| `EndPortalBlock` | end_portal |
| `EndPortalFrameBlock` | end_portal_frame |
| `EndRodBlock` | end_rod |
| `EquipableCarvedPumpkinBlock` | carved_pumpkin |
| `FarmBlock` | farmland |
| `FenceBlock` | oak_fence, nether_brick_fence, spruce_fence, birch_fence, jungle_fence, acacia_fence, cherry_fence, dark_oak_fence, mangrove_fence, bamboo_fence, crimson_fence, warped_fence |
| `FenceGateBlock` | oak_fence_gate, spruce_fence_gate, birch_fence_gate, jungle_fence_gate, acacia_fence_gate, cherry_fence_gate, dark_oak_fence_gate, mangrove_fence_gate, bamboo_fence_gate, crimson_fence_gate, warped_fence_gate |
| `FireBlock` | fire |
| `FletchingTableBlock` | fletching_table |
| `FlowerBlock` | dandelion, torchflower, poppy, blue_orchid, allium, azure_bluet, red_tulip, orange_tulip, white_tulip, pink_tulip, oxeye_daisy, cornflower, lily_of_the_valley |
| `FlowerPotBlock` | flower_pot, potted_torchflower, potted_oak_sapling, potted_spruce_sapling, potted_birch_sapling, potted_jungle_sapling, potted_acacia_sapling, potted_cherry_sapling, potted_dark_oak_sapling, potted_mangrove_propagule, potted_fern, potted_dandelion, potted_poppy, potted_blue_orchid, potted_allium, potted_azure_bluet, potted_red_tulip, potted_orange_tulip, potted_white_tulip, potted_pink_tulip, potted_oxeye_daisy, potted_cornflower, potted_lily_of_the_valley, potted_wither_rose, potted_red_mushroom, potted_brown_mushroom, potted_dead_bush, potted_cactus, potted_bamboo, potted_crimson_fungus, potted_warped_fungus, potted_crimson_roots, potted_warped_roots, potted_azalea_bush, potted_flowering_azalea_bush |
| `FrogspawnBlock` | frogspawn |
| `FrostedIceBlock` | frosted_ice |
| `FungusBlock` | warped_fungus, crimson_fungus |
| `FurnaceBlock` | furnace |
| `GlazedTerracottaBlock` | white_glazed_terracotta, orange_glazed_terracotta, magenta_glazed_terracotta, light_blue_glazed_terracotta, yellow_glazed_terracotta, lime_glazed_terracotta, pink_glazed_terracotta, gray_glazed_terracotta, light_gray_glazed_terracotta, cyan_glazed_terracotta, purple_glazed_terracotta, blue_glazed_terracotta, brown_glazed_terracotta, green_glazed_terracotta, red_glazed_terracotta, black_glazed_terracotta |
| `GlowLichenBlock` | glow_lichen |
| `GrassBlock` | grass_block |
| `GrindstoneBlock` | grindstone |
| `HalfTransparentBlock` | blue_ice |
| `HangingRootsBlock` | hanging_roots |
| `HayBlock` | hay_block |
| `HeavyCoreBlock` | heavy_core |
| `HoneyBlock` | honey_block |
| `HopperBlock` | hopper |
| `HugeMushroomBlock` | brown_mushroom_block, red_mushroom_block, mushroom_stem |
| `IceBlock` | ice |
| `InfestedBlock` | infested_stone, infested_cobblestone, infested_stone_bricks, infested_mossy_stone_bricks, infested_cracked_stone_bricks, infested_chiseled_stone_bricks |
| `InfestedRotatedPillarBlock` | infested_deepslate |
| `IronBarsBlock` | iron_bars, glass_pane |
| `JigsawBlock` | jigsaw |
| `JukeboxBlock` | jukebox |
| `KelpBlock` | kelp |
| `KelpPlantBlock` | kelp_plant |
| `LadderBlock` | ladder |
| `LanternBlock` | lantern, soul_lantern |
| `LavaCauldronBlock` | lava_cauldron |
| `LayeredCauldronBlock` | water_cauldron, powder_snow_cauldron |
| `LeavesBlock` | oak_leaves, spruce_leaves, birch_leaves, jungle_leaves, acacia_leaves, dark_oak_leaves, azalea_leaves, flowering_azalea_leaves |
| `LecternBlock` | lectern |
| `LeverBlock` | lever |
| `LightBlock` | light |
| `LightningRodBlock` | lightning_rod |
| `LiquidBlock` | water, lava |
| `LoomBlock` | loom |
| `MagmaBlock` | magma_block |
| `MangroveLeavesBlock` | mangrove_leaves |
| `MangrovePropaguleBlock` | mangrove_propagule |
| `MangroveRootsBlock` | mangrove_roots |
| `MossBlock` | moss_block |
| `MovingPistonBlock` | moving_piston |
| `MudBlock` | mud |
| `MushroomBlock` | brown_mushroom, red_mushroom |
| `MyceliumBlock` | mycelium |
| `NetherPortalBlock` | nether_portal |
| `NetherrackBlock` | netherrack |
| `NetherSproutsBlock` | nether_sprouts |
| `NetherWartBlock` | nether_wart |
| `NoteBlock` | note_block |
| `NyliumBlock` | warped_nylium, crimson_nylium |
| `ObserverBlock` | observer |
| `PiglinWallSkullBlock` | piglin_wall_head |
| `PinkPetalsBlock` | pink_petals |
| `PistonBaseBlock` | sticky_piston, piston |
| `PistonHeadBlock` | piston_head |
| `PitcherCropBlock` | pitcher_crop |
| `PlayerHeadBlock` | player_head |
| `PlayerWallHeadBlock` | player_wall_head |
| `PointedDripstoneBlock` | pointed_dripstone |
| `PotatoBlock` | potatoes |
| `PowderSnowBlock` | powder_snow |
| `PoweredBlock` | redstone_block |
| `PoweredRailBlock` | powered_rail, activator_rail |
| `PressurePlateBlock` | stone_pressure_plate, oak_pressure_plate, spruce_pressure_plate, birch_pressure_plate, jungle_pressure_plate, acacia_pressure_plate, cherry_pressure_plate, dark_oak_pressure_plate, mangrove_pressure_plate, bamboo_pressure_plate, crimson_pressure_plate, warped_pressure_plate, polished_blackstone_pressure_plate |
| `RailBlock` | rail |
| `RedstoneLampBlock` | redstone_lamp |
| `RedStoneOreBlock` | redstone_ore, deepslate_redstone_ore |
| `RedstoneTorchBlock` | redstone_torch |
| `RedstoneWallTorchBlock` | redstone_wall_torch |
| `RedStoneWireBlock` | redstone_wire |
| `RepeaterBlock` | repeater |
| `RespawnAnchorBlock` | respawn_anchor |
| `RootedDirtBlock` | rooted_dirt |
| `RootsBlock` | warped_roots, crimson_roots |
| `RotatedPillarBlock` | oak_log, spruce_log, birch_log, jungle_log, acacia_log, cherry_log, dark_oak_log, mangrove_log, muddy_mangrove_roots, bamboo_block, stripped_spruce_log, stripped_birch_log, stripped_jungle_log, stripped_acacia_log, stripped_cherry_log, stripped_dark_oak_log, stripped_oak_log, stripped_mangrove_log, stripped_bamboo_block, oak_wood, spruce_wood, birch_wood, jungle_wood, acacia_wood, cherry_wood, dark_oak_wood, mangrove_wood, stripped_oak_wood, stripped_spruce_wood, stripped_birch_wood, stripped_jungle_wood, stripped_acacia_wood, stripped_cherry_wood, stripped_dark_oak_wood, stripped_mangrove_wood, basalt, polished_basalt, quartz_pillar, purpur_pillar, bone_block, warped_stem, stripped_warped_stem, warped_hyphae, stripped_warped_hyphae, crimson_stem, stripped_crimson_stem, crimson_hyphae, stripped_crimson_hyphae, deepslate, ochre_froglight, verdant_froglight, pearlescent_froglight |
| `SaplingBlock` | oak_sapling, spruce_sapling, birch_sapling, jungle_sapling, acacia_sapling, cherry_sapling, dark_oak_sapling |
| `ScaffoldingBlock` | scaffolding |
| `SculkBlock` | sculk |
| `SculkCatalystBlock` | sculk_catalyst |
| `SculkSensorBlock` | sculk_sensor |
| `SculkShriekerBlock` | sculk_shrieker |
| `SculkVeinBlock` | sculk_vein |
| `SeagrassBlock` | seagrass |
| `SeaPickleBlock` | sea_pickle |
| `ShulkerBoxBlock` | shulker_box, white_shulker_box, orange_shulker_box, magenta_shulker_box, light_blue_shulker_box, yellow_shulker_box, lime_shulker_box, pink_shulker_box, gray_shulker_box, light_gray_shulker_box, cyan_shulker_box, purple_shulker_box, blue_shulker_box, brown_shulker_box, green_shulker_box, red_shulker_box, black_shulker_box |
| `SkullBlock` | skeleton_skull, zombie_head, creeper_head, dragon_head, piglin_head |
| `SlabBlock` | prismarine_slab, prismarine_brick_slab, dark_prismarine_slab, oak_slab, spruce_slab, birch_slab, jungle_slab, acacia_slab, cherry_slab, dark_oak_slab, mangrove_slab, bamboo_slab, bamboo_mosaic_slab, stone_slab, smooth_stone_slab, sandstone_slab, cut_sandstone_slab, petrified_oak_slab, cobblestone_slab, brick_slab, stone_brick_slab, mud_brick_slab, nether_brick_slab, quartz_slab, red_sandstone_slab, cut_red_sandstone_slab, purpur_slab, polished_granite_slab, smooth_red_sandstone_slab, mossy_stone_brick_slab, polished_diorite_slab, mossy_cobblestone_slab, end_stone_brick_slab, smooth_sandstone_slab, smooth_quartz_slab, granite_slab, andesite_slab, red_nether_brick_slab, polished_andesite_slab, diorite_slab, crimson_slab, warped_slab, blackstone_slab, polished_blackstone_brick_slab, polished_blackstone_slab, tuff_slab, polished_tuff_slab, tuff_brick_slab, waxed_oxidized_cut_copper_slab, waxed_weathered_cut_copper_slab, waxed_exposed_cut_copper_slab, waxed_cut_copper_slab, cobbled_deepslate_slab, polished_deepslate_slab, deepslate_tile_slab, deepslate_brick_slab |
| `SlimeBlock` | slime_block |
| `SmallDripleafBlock` | small_dripleaf |
| `SmithingTableBlock` | smithing_table |
| `SmokerBlock` | smoker |
| `SnifferEggBlock` | sniffer_egg |
| `SnowLayerBlock` | snow |
| `SnowyDirtBlock` | podzol |
| `SoulFireBlock` | soul_fire |
| `SoulSandBlock` | soul_sand |
| `SpawnerBlock` | spawner |
| `SpongeBlock` | sponge |
| `SporeBlossomBlock` | spore_blossom |
| `StainedGlassBlock` | white_stained_glass, orange_stained_glass, magenta_stained_glass, light_blue_stained_glass, yellow_stained_glass, lime_stained_glass, pink_stained_glass, gray_stained_glass, light_gray_stained_glass, cyan_stained_glass, purple_stained_glass, blue_stained_glass, brown_stained_glass, green_stained_glass, red_stained_glass, black_stained_glass |
| `StainedGlassPaneBlock` | white_stained_glass_pane, orange_stained_glass_pane, magenta_stained_glass_pane, light_blue_stained_glass_pane, yellow_stained_glass_pane, lime_stained_glass_pane, pink_stained_glass_pane, gray_stained_glass_pane, light_gray_stained_glass_pane, cyan_stained_glass_pane, purple_stained_glass_pane, blue_stained_glass_pane, brown_stained_glass_pane, green_stained_glass_pane, red_stained_glass_pane, black_stained_glass_pane |
| `StairBlock` | oak_stairs, cobblestone_stairs, brick_stairs, stone_brick_stairs, mud_brick_stairs, nether_brick_stairs, sandstone_stairs, spruce_stairs, birch_stairs, jungle_stairs, quartz_stairs, acacia_stairs, cherry_stairs, dark_oak_stairs, mangrove_stairs, bamboo_stairs, bamboo_mosaic_stairs, prismarine_stairs, prismarine_brick_stairs, dark_prismarine_stairs, red_sandstone_stairs, purpur_stairs, polished_granite_stairs, smooth_red_sandstone_stairs, mossy_stone_brick_stairs, polished_diorite_stairs, mossy_cobblestone_stairs, end_stone_brick_stairs, stone_stairs, smooth_sandstone_stairs, smooth_quartz_stairs, granite_stairs, andesite_stairs, red_nether_brick_stairs, polished_andesite_stairs, diorite_stairs, crimson_stairs, warped_stairs, blackstone_stairs, polished_blackstone_brick_stairs, polished_blackstone_stairs, tuff_stairs, polished_tuff_stairs, tuff_brick_stairs, waxed_oxidized_cut_copper_stairs, waxed_weathered_cut_copper_stairs, waxed_exposed_cut_copper_stairs, waxed_cut_copper_stairs, cobbled_deepslate_stairs, polished_deepslate_stairs, deepslate_tile_stairs, deepslate_brick_stairs |
| `StandingSignBlock` | oak_sign, spruce_sign, birch_sign, acacia_sign, cherry_sign, jungle_sign, dark_oak_sign, mangrove_sign, bamboo_sign, crimson_sign, warped_sign |
| `StonecutterBlock` | stonecutter |
| `StructureBlock` | structure_block |
| `StructureVoidBlock` | structure_void |
| `SugarCaneBlock` | sugar_cane |
| `SweetBerryBushBlock` | sweet_berry_bush |
| `TallFlowerBlock` | sunflower, lilac, rose_bush, peony |
| `TallGrassBlock` | short_grass, fern |
| `TallSeagrassBlock` | tall_seagrass |
| `TargetBlock` | target |
| `TintedGlassBlock` | tinted_glass |
| `TntBlock` | tnt |
| `TorchBlock` | torch, soul_torch |
| `TorchflowerCropBlock` | torchflower_crop |
| `TransparentBlock` | glass |
| `TrapDoorBlock` | oak_trapdoor, spruce_trapdoor, birch_trapdoor, jungle_trapdoor, acacia_trapdoor, cherry_trapdoor, dark_oak_trapdoor, mangrove_trapdoor, bamboo_trapdoor, iron_trapdoor, crimson_trapdoor, warped_trapdoor, waxed_copper_trapdoor, waxed_exposed_copper_trapdoor, waxed_oxidized_copper_trapdoor, waxed_weathered_copper_trapdoor |
| `TrappedChestBlock` | trapped_chest |
| `TrialSpawnerBlock` | trial_spawner |
| `TripWireBlock` | tripwire |
| `TripWireHookBlock` | tripwire_hook |
| `TurtleEggBlock` | turtle_egg |
| `TwistingVinesBlock` | twisting_vines |
| `TwistingVinesPlantBlock` | twisting_vines_plant |
| `VaultBlock` | vault |
| `VineBlock` | vine |
| `WallBannerBlock` | white_wall_banner, orange_wall_banner, magenta_wall_banner, light_blue_wall_banner, yellow_wall_banner, lime_wall_banner, pink_wall_banner, gray_wall_banner, light_gray_wall_banner, cyan_wall_banner, purple_wall_banner, blue_wall_banner, brown_wall_banner, green_wall_banner, red_wall_banner, black_wall_banner |
| `WallBlock` | cobblestone_wall, mossy_cobblestone_wall, brick_wall, prismarine_wall, red_sandstone_wall, mossy_stone_brick_wall, granite_wall, stone_brick_wall, mud_brick_wall, nether_brick_wall, andesite_wall, red_nether_brick_wall, sandstone_wall, end_stone_brick_wall, diorite_wall, blackstone_wall, polished_blackstone_brick_wall, polished_blackstone_wall, tuff_wall, polished_tuff_wall, tuff_brick_wall, cobbled_deepslate_wall, polished_deepslate_wall, deepslate_tile_wall, deepslate_brick_wall |
| `WallHangingSignBlock` | oak_wall_hanging_sign, spruce_wall_hanging_sign, birch_wall_hanging_sign, acacia_wall_hanging_sign, cherry_wall_hanging_sign, jungle_wall_hanging_sign, dark_oak_wall_hanging_sign, mangrove_wall_hanging_sign, crimson_wall_hanging_sign, warped_wall_hanging_sign, bamboo_wall_hanging_sign |
| `WallSignBlock` | oak_wall_sign, spruce_wall_sign, birch_wall_sign, acacia_wall_sign, cherry_wall_sign, jungle_wall_sign, dark_oak_wall_sign, mangrove_wall_sign, bamboo_wall_sign, crimson_wall_sign, warped_wall_sign |
| `WallSkullBlock` | skeleton_wall_skull, zombie_wall_head, creeper_wall_head, dragon_wall_head |
| `WallTorchBlock` | wall_torch, soul_wall_torch |
| `WaterlilyBlock` | lily_pad |
| `WaterloggedTransparentBlock` | waxed_copper_grate, waxed_exposed_copper_grate, waxed_weathered_copper_grate, waxed_oxidized_copper_grate |
| `WeatheringCopperBulbBlock` | copper_bulb, exposed_copper_bulb, weathered_copper_bulb, oxidized_copper_bulb |
| `WeatheringCopperDoorBlock` | copper_door, exposed_copper_door, oxidized_copper_door, weathered_copper_door |
| `WeatheringCopperFullBlock` | copper_block, exposed_copper, weathered_copper, oxidized_copper, oxidized_cut_copper, weathered_cut_copper, exposed_cut_copper, cut_copper, oxidized_chiseled_copper, weathered_chiseled_copper, exposed_chiseled_copper, chiseled_copper |
| `WeatheringCopperGrateBlock` | copper_grate, exposed_copper_grate, weathered_copper_grate, oxidized_copper_grate |
| `WeatheringCopperSlabBlock` | oxidized_cut_copper_slab, weathered_cut_copper_slab, exposed_cut_copper_slab, cut_copper_slab |
| `WeatheringCopperStairBlock` | oxidized_cut_copper_stairs, weathered_cut_copper_stairs, exposed_cut_copper_stairs, cut_copper_stairs |
| `WeatheringCopperTrapDoorBlock` | copper_trapdoor, exposed_copper_trapdoor, oxidized_copper_trapdoor, weathered_copper_trapdoor |
| `WebBlock` | cobweb |
| `WeepingVinesBlock` | weeping_vines |
| `WeepingVinesPlantBlock` | weeping_vines_plant |
| `WeightedPressurePlateBlock` | light_weighted_pressure_plate, heavy_weighted_pressure_plate |
| `WetSpongeBlock` | wet_sponge |
| `WitherRoseBlock` | wither_rose |
| `WitherSkullBlock` | wither_skeleton_skull |
| `WitherWallSkullBlock` | wither_skeleton_wall_skull |
| `WoolCarpetBlock` | white_carpet, orange_carpet, magenta_carpet, light_blue_carpet, yellow_carpet, lime_carpet, pink_carpet, gray_carpet, light_gray_carpet, cyan_carpet, purple_carpet, blue_carpet, brown_carpet, green_carpet, red_carpet, black_carpet |
### Provider, state and collision-query closure

Across 1.20.6 -> 1.21 and 1.21 -> 1.21.1, method-body comparison found 152 `getShape`/`getCollisionShape` implementations in the common block source inventory in each era and no changed, added or removed method body; the corresponding 387 static `VoxelShape` fields/arrays also have no changes. This includes direct collision overrides (BambooStalk, Bell, BigDripleaf, Cactus, Composter, CrossCollision, FenceGate, Grindstone, Honey, Lectern, Liquid, Mud, MovingPiston, PowderSnow, Scaffolding, SculkShrieker, SnowLayer, SoulSand, Wall and WallHangingSign) and the base `BlockBehaviour` implementation; other shape overrides flow through the base shape getter. `BlockStateBase.getCollisionShape` in `world/level/block/state/BlockBehaviour.java` (1.20.6:629-634; 1.21/1.21.1 corresponding method) dispatches cached shape or the block's provider. Representative dynamic dependencies are SlabBlock `TYPE` (1.20.6 `SlabBlock.java:30-33, 56-66`), stair orientation/half, fence/wall/gate connections, door/trapdoor open state, moving-piston state, scaffolding state and CollisionContext-dependent powder snow; those provider bodies and their shape constants are unchanged in both comparisons. `Block.updateShape` neighbor propagation (1.20.6 `Block.java:151-158`) recomputes state from adjacent state; block-specific `updateShape` methods for the checked provider classes and movement-state blocks were unchanged. `BlockCollisions.computeNext` (`world/level/BlockCollisions.java:65-95`) queries each cursor block's current state and invokes `getCollisionShape` before moving/intersecting the shape; this is the resolved movement collision route, not a render outline query.

The candidate witness is reachable as a source-level input to this route: a grounded player with box `[0.2,1,0.2]..[0.8,2.8,0.8]`, positive `maxUpStep=0.6`, and requested `(0.8,0,-0.4)` can stand on a full block floor with `Blocks.WHITE_CARPET` at `(1,1,0)` and a bottom `Blocks.OAK_SLAB` at `(0,1,-1)`. `CarpetBlock.SHAPE` is exactly 1/16 block high (`CarpetBlock.java:16,28-30`); bottom slab is 8/16 high and selected by `SlabBlock.TYPE` (`SlabBlock.java:30-33,56-65`); both registrations use those classes (`Blocks.java:3559-3561` and the `OAK_SLAB` initializer at 1.20.6:4238 onward). `Attributes.STEP_HEIGHT` defaults to 0.6 (`world/entity/ai/attributes/Attributes.java:72`); an entity can have that vanilla default step attribute.

For this input, the swept broad box contains the carpet and slab. Flat movement hits carpet first: X is resolved before Z because `abs(-0.4) < abs(0.8)` (`Entity.java:931-948`), while the unrelated slab is in the broad query. `VoxelShape.collideX` narrows transverse index bounds with `min + 1.0E-7` and `max - 1.0E-7` (`world/phys/shapes/VoxelShape.java:237-242`), so after X reaches the slab's x=1 face, the later Z step sees only boundary contact with the slab and does not collide with its interior. The new candidate collector sees the carpet top relative to player minY, `1.0625 - 1 = 0.0625`, and the slab top `1.5 - 1 = 0.5`; both lie in the expanded query and within max step. Its `0.0625F` candidate can move the complete horizontal request while clearing the carpet, and the strict `>` comparison returns that candidate (`1.21/1.21.1 Entity.java:885-925,895-899`). The old implementation attempts 0.6 first and also clears the carpet; its fallback uses a vertical-first expanded box, where the slab may clip upward travel to 0.5, but the old first attempt already has equal full horizontal distance and its strict `>` comparison retains 0.6 (`1.20.6 Entity.java:881-894`). Therefore this is a concrete reachable witness of candidate-set/selected-rise difference, not evidence of a final-position or trajectory divergence: the old candidate's downward compensation can land on the carpet top, and runtime trajectory is unexamined. It supports the logical input distinction only.

### Block callbacks, tags and neighbor-dependent movement responses

`Entity.move` dispatch order is unchanged for the compared releases: after collision/position/velocity updates it resolves fall damage and `Block.updateEntityAfterFallOn`, calls `stepOn` for supporting block(s), then `checkInsideBlocks`/state `entityInside` (1.20.6 `Entity.java:597`, `656-704`, `757-765`, `969-999`; 1.21/1.21.1 corresponding lines 598, 657-705, 758-766, 1001-1031). Base `Block.stepOn` is empty and `updateEntityAfterFallOn` clears vertical delta only (`Block.java:340,377` in 1.20.6; 1.21+:339,376). The registered callback implementations found by the complete block-package method scan for `stepOn`, `updateEntityAfterFallOn`, `entityInside`, `updateShape`, state definition and neighbor/property methods were identical across 1.21 -> 1.21.1. Six method-body differences occur only in 1.20.6 -> 1.21: `BaseFireBlock.entityInside` and `CampfireBlock.entityInside` damage/fire path, `MagmaBlock.stepOn` removes a Frost Walker damage exclusion, `CommandBlock.setPlacedBy` command configuration, and `EndPortalBlock.entityInside`/`NetherPortalBlock.entityInside` portal eligibility/inside-state path. No shape, movement callback order, or collision predicate method body differs. The fire/campfire/magma changes feed damage/fire only (excluded damage output); End/Nether portal callbacks now set inside-portal state client-side under `canUsePortal(false)` and are retained as a partial direct response because their eventual transition semantics are handled elsewhere.

Neighbor/state dependencies for movement-touching old blocks were checked at the implementation and vanilla tag layer. `VanillaBlockTagsProvider` default memberships are identical in all three versions: `CLIMBABLE` is LADDER, VINE, SCAFFOLDING, WEEPING_VINES, WEEPING_VINES_PLANT, TWISTING_VINES, TWISTING_VINES_PLANT, CAVE_VINES and CAVE_VINES_PLANT (1.20.6:693-704; 1.21+:738-749); `PORTALS`, `ICE`, `SOUL_SPEED_BLOCKS`, `FENCES`, `WALLS`, `FENCE_GATES`, `UNSTABLE_BOTTOM_CENTER` and `FALL_DAMAGE_RESETTING` are also identical at 1.20.6:427,463,597,682,687,729,743 and 1.21+:457,496,642,727,732,774,788. `FALL_DAMAGE_RESETTING` adds SWEET_BERRY_BUSH and COBWEB to CLIMBABLE. `VanillaItemTagsProvider` relevant freeze-immune wearable membership is unchanged (leather armor/horse armor); this is a freeze response, not a positional movement callback. `BubbleColumnBlock.entityInside` (1.20.6:49-56) reads `DRAG_DOWN` and the above block's air state before calling Entity's bubble response; its body is unchanged in both pairs. Water remains the registered `LiquidBlock`; fluid height/pushing and block touch response enter Entity's common fluid/inside-block paths. PowderSnowBlock's entity-dependent collision and `makeStuckInBlock` callback are unchanged; the collision shape is conditional on entity/collision context, and only living-entity inside behavior is applied. Scaffolding's shape depends on BOTTOM and held-item context; vines, ladders, gates and walls derive shape/state from facing and adjacent connection properties. Their relevant methods, property lists, tag membership and providers have no adjacent-version body/entry change in this check.

### Client event and packet dispatch; terminal responses

`ClientPacketListener.handleBlockEvent` passes block event position/id/parameter into `ClientLevel.blockEvent` (1.20.6:1303-1306; 1.21+:1309-1312). `ClientLevel` does not override `blockEvent`; inherited `Level.blockEvent` loads current state and invokes `BlockState.triggerEvent` (`Level.java:777-778` in 1.20.6, `782-783` in 1.21+). The piston route is client-reachable: `PistonMovingBlockEntity.moveCollidedEntities` includes entities returned by `Level.getEntities` with only `PushReaction.IGNORE` excluded, then calls `moveEntityByPiston`; `moveStuckEntities` similarly selects grounded supported entities, and `moveEntityByPiston` dispatches `Entity.move(MoverType.PISTON, Vec3)` (`world/level/block/piston/PistonMovingBlockEntity.java:109-117,128-175,181-199,202-205`). There is no client-side guard around those movement calls in the tick route. The exact route and `ClientPacketListener -> Level -> block state` dispatch are unchanged; piston movement is a client response through the common collision route and is not reduced to server-side production.

Terminal packet routes inspected in all three versions: `handleSetEntityMotion` resolves an entity and calls `lerpMotion`; 1.20.6 scales packet integer fields by `/8000.0` (`ClientPacketListener.java:515-520`), while 1.21+ packet getters return already-scaled doubles and the handler passes them directly (`:517-522`), preserving the per-axis value. `handleExplosion` adds the packet knockback vector to local player's delta (`1.20.6:1167-1184; 1.21+:1172-1189`); `handleMovePlayer` applies the local player's authoritative relative/absolute position, rotation and velocity response (`1.20.6:618 onward; 1.21+:620 onward`); these bodies are semantically unchanged. `handleTeleportEntity` interpolates only when the entity is not locally controlled; `handleMoveEntity` has the same local-control skip. The player position/rotation authority gate and packet response were inspected across adjacent versions; no changed terminal movement response was identified. `handleBlockEvent` and `handleLevelEvent` forwarding are unchanged. `ClientLevel.levelEvent` forwards to the renderer rather than applying player movement. The 1.21 portal refactor changes the block's client inside-portal flag route, but the later transition/destination processing is a distinct and only partially closed response dependency.

### Paired coverage disposition after this addendum

- **Checked and unchanged in 1.20.6 -> 1.21 and 1.21 -> 1.21.1:** 1,054 old registered IDs; all registration-to-concrete-class/helper mappings above; every common block `getShape`/`getCollisionShape` body and static VoxelShape field/array (152 methods, 387 fields); collision query from `Entity` through `BlockCollisions` and `BlockStateBase`; relevant state definitions, neighbor-update methods and default tag membership; base and registered step/fall/touch callback body inventory except the six listed next; `ClientLevel`/`Level` block and level event forwarding; client piston block-event-to-entity movement route; selected local motion/position/explosion/teleport packet routes.
- **Changed in 1.20.6 -> 1.21, unchanged in 1.21 -> 1.21.1:** `Entity.collide` candidate-rise search; 19 registration initializer expressions (enumerated above); the six callback bodies above. End/Nether portal inside-state behavior needs separate acceptance/impact review; it is not conflated with movement collision or a server producer finding.
- **Partial:** complete downstream consequences of the new client-side End/Nether portal state through cooldown/transition/position packets; every conceivable level-event ID and every entity event's non-positional effects; block-entity events other than piston movement; player fluid pushing, bubble-column and powder-snow response call chains were inspected for callback entry/providers but not exhaustively checked as a terminal influence chain through every tick; any non-vanilla modded block/tag additions are outside vanilla source inventory. No parity or no-difference assertion is made for these.
- **Unexamined:** nonselected packet handlers that might directly set player position/velocity/pose; dynamic modded registry/tag input; runtime trajectories and all environmental tick interleavings. No tests, builds, game execution or decompilation were performed.
