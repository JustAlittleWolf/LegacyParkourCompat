# Complete 1.12 horizontal pane FaceShape inventory and 26.2 selectors

## Scope and source identity

This immutable supplement extends `bridge.md`; it inventories every declared 1.12 block-package `getFaceShape` override and the exact horizontal outcomes consumed by `PaneBlock.shouldConnectTo`. It also records the pane's class exclusions and the bounded current IDs/properties needed to select the legacy rule for these provider families. It is source-only: it does not claim input/runtime reachability and does not inspect product code or backlog.

Canonical source inputs are the 1.12 Ornithe Feather tree `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.12\ornithe-feather\` and the 26.2 `unobfuscated` tree `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.2\unobfuscated\`. 1.12/1.12.1 aggregate ready/source-manifest limitations recorded by the accepted alias closure remain. The 26.2 root's ready/provenance/source/artifact manifest hashes are in `bridge.md`.

In 1.12, `Block.getFaceShape(WorldView, BlockState, BlockPos, Direction)` returns `SOLID` by default (`Block.java:362`). `BlockState.getFaceShape` delegates to that virtual provider (`state/StateDefinition.java:415-416`). Every concrete override in the block package was enumerated by source search; the table below excludes only this state delegator and the `BlockProperties` interface declaration.

The pane connection expression is exactly `!shouldConnectTo(block) && faceShape == SOLID || faceShape == MIDDLE_POLE_THIN` (`PaneBlock.java:138-139`), parsed as `(!excluded && SOLID) || MIDDLE_POLE_THIN`. Thus excluded class identity suppresses only `SOLID`; a thin-pole result connects regardless. The pane reads the neighboring state, position and the face toward the pane before applying this predicate.

## Every declared 1.12 block provider override

Horizontal directions are NORTH/EAST/SOUTH/WEST. `UNDEFINED` and `MIDDLE_POLE`/`MIDDLE_POLE_THICK` produce no pane arm; horizontal `SOLID` connects unless the block is excluded; horizontal `MIDDLE_POLE_THIN` connects even if excluded.

| Provider override and exact member line | Horizontal return and gates |
|---|---|
| `Block.java:362` | Base provider always `SOLID`; applies to legacy block identities whose dynamic class does not override this method. Pane's explicit excluded-block test still applies. |
| `AirBlock.java:54`; `AbstractPressurePlateBlock.java:177`; `AbstractRailBlock.java:57`; `AnvilBlock.java:48`; `BannerBlock.java:111`; `BedBlock.java:327`; `BrewingStandBlock.java:180`; `ButtonBlock.java:318`; `CactusBlock.java:124`; `CakeBlock.java:145`; `ChestBlock.java:474`; `ChorusFlowerBlock.java:258`; `ChorusPlantBlock.java:188`; `CobwebBlock.java:75`; `CocoaBlock.java:185`; `DoorBlock.java:360`; `DragonEggBlock.java:130`; `EndGatewayBlock.java:95`; `EndPortalBlock.java:95`; `EndRodBlock.java:127`; `EnderChestBlock.java:161`; `FireBlock.java:403`; `FlowerPotBlock.java:343`; `HopperBlock.java:215`; `LadderBlock.java:133`; `LeverBlock.java:265`; `LiquidBlock.java:375`; `MovingBlock.java:193`; `PlantBlock.java:91`; `PortalBlock.java:307`; `RedstoneWireBlock.java:470`; `SignBlock.java:96`; `SkullBlock.java:282`; `StructureVoidBlock.java:62`; `SugarCaneBlock.java:146`; `TorchBlock.java:256`; `TripwireBlock.java:246`; `TripwireHookBlock.java:300`; `VineBlock.java:387` | Always `UNDEFINED`; no state or face condition changes the horizontal pane result. For the old counterparts listed in the bounded selector table below, select false directly. |
| `CarpetBlock.java:110`; `DaylightDetectorBlock.java:154`; `DiodeBlock.java:247`; `EndPortalFrameBlock.java:149`; `EnchantingTableBlock.java:118`; `FarmlandBlock.java:150`; `GrassPathBlock.java:83`; `SnowLayerBlock.java:60` | Returns `SOLID` only for DOWN, otherwise `UNDEFINED`; therefore horizontal pane result is always false. |
| `TrapdoorBlock.java:214` | Returns `SOLID` only when queried face is UP with `HALF=TOP` or DOWN with `HALF=BOTTOM`, and `OPEN=false`; every horizontal face is always `UNDEFINED`, independent of `OPEN`, `HALF`, `FACING`, or `POWERED`. Therefore a horizontal pane neighbor never connects. |
| `CauldronBlock.java:253` | UP is `BOWL`, DOWN is `UNDEFINED`, every horizontal face is `SOLID`; the `CAULDRON` block identity is unconditionally excluded by the pane caller, so the final pane result is false. |
| `DoubleStoneSlabBlock.java:15` | Always `SOLID`; final result is true unless the block were in the exclusion list (it is not). |
| `SlabBlock.java:53` | If `isDouble()` then `SOLID` for all faces. Otherwise horizontal faces are `UNDEFINED`; `HALF` only affects UP/DOWN. Connect iff the legacy slab instance is double. |
| `StairsBlock.java:140` | Calls `resolveVirtualProperties(state,world,pos)` first. Horizontal results: OUTER_LEFT/OUTER_RIGHT are `UNDEFINED`; STRAIGHT is `SOLID` iff `FACING == face`; INNER_RIGHT is `SOLID` iff `FACING == face || FACING == face.counterClockwiseY()`; INNER_LEFT is `SOLID` iff `FACING == face || FACING == face.clockwiseY()`. The resolved SHAPE depends on same-half neighboring stairs and their FACING; no pane exclusion applies. |
| `FenceBlock.java:202` | Every horizontal face is `MIDDLE_POLE`; pane result false. |
| `WallBlock.java:205` | Every horizontal face is `MIDDLE_POLE_THICK`; pane result false. |
| `FenceGateBlock.java:175` | Horizontal face is `MIDDLE_POLE` when `FACING.axis == face.clockwiseY().axis`, otherwise `UNDEFINED`; either category yields false. The exact category uses FACING and queried face, but no gate state ever connects a pane. |
| `PaneBlock.java:206` | Every horizontal face is `MIDDLE_POLE_THIN`; pane result true, independent of neighbor connection properties. |
| `PistonBaseBlock.java:368` | Calls `resolveVirtualProperties`. Returns `UNDEFINED` when `EXTENDED` and `FACING != face.opposite()`; otherwise `SOLID`. Both piston base block identities are excluded by the pane caller, so the final result is always false. |
| `PistonHeadBlock.java:210` | `SOLID` only when `face == FACING`, otherwise `UNDEFINED`; `PISTON_HEAD` is excluded, so final result always false. |
| `ShulkerBoxBlock.java:313` | Calls `resolveVirtualProperties`, then reads block entity `AnimationStage`. Returns `SOLID` when CLOSED; for OPENED, only when `FACING == face || FACING == face.opposite()`; otherwise `UNDEFINED`. Every shulker box is excluded, so final pane result is always false. |

Overrides with vertical-only branches are grouped because the pane supplies horizontal directions only. This table is exhaustive for concrete `getFaceShape` method declarations under the 1.12 block package; it does not claim that every class inheriting `Block` is a full cube or every new 26.2 block has a 1.12 predecessor.

## Bounded 1.12-era ID membership and 26.2 inputs

The left column is the 1.12 registry identity or old variant group from the 1.12 `Block.java` registration initializer. The right column is the current vanilla ID or family in 26.2 `Blocks.java`/`BlockItemIds.java`; state inputs are only those required by the historical horizontal pane predicate. IDs and names are source-derived; entries absent from this table are not asserted to have a 1.12 counterpart. Modern-only IDs retain native behavior.

| 1.12-era block/provider membership | Current 26.2 selector and historical input |
|---|---|
| `iron_bars`, `glass_pane`, `stained_glass_pane` colored variants | `minecraft:iron_bars`, `minecraft:glass_pane`, every `minecraft:<color>_stained_glass_pane`; map to `MIDDLE_POLE_THIN`/connect true. Ignore current pane NORTH/EAST/SOUTH/WEST and WATERLOGGED properties for the neighbor predicate. Current stained-pane class extends `IronBarsBlock`; color is not part of the old result. |
| Ordinary glass, stained glass | `minecraft:glass`, every `minecraft:<color>_stained_glass`; map to legacy SOLID/connect true (glass is not excluded). Current default collision/support geometry is full block, but use the explicit source identity result. |
| Legacy `stone_slab`/`double_stone_slab` variant values: `stone`, `sandstone`, `wood_old`, `cobblestone`, `brick`, `stone_brick`, `nether_brick`, `quartz` (`StoneSlabBlock.java:113-121`) | Map these old variants to current `stone_slab`, `sandstone_slab`, `petrified_oak_slab`, `cobblestone_slab`, `brick_slab`, `stone_brick_slab`, `nether_brick_slab`, `quartz_slab`, respectively. The old `double_stone_slab` maps to the same material ID with `TYPE=DOUBLE`; old `stone_slab` maps to the single type. |
| Legacy `wooden_slab`/`double_wooden_slab` with `PlanksBlock.VARIANT` oak, spruce, birch, jungle, acacia, dark_oak | Map to current `oak_slab`, `spruce_slab`, `birch_slab`, `jungle_slab`, `acacia_slab`, `dark_oak_slab`. Legacy double IDs map to `TYPE=DOUBLE`; single IDs map to BOTTOM/TOP `TYPE` as appropriate. Horizontal output depends only on single/double, never HALF. |
| Legacy `stone_slab2`/`double_stone_slab2` red-sandstone variant and `purpur_slab`/`purpur_double_slab` | Map to current `red_sandstone_slab` and `purpur_slab`, respectively, with `TYPE=DOUBLE` for old double IDs. Other current slab IDs (for example smooth/cut variants, mud, blackstone, bamboo or pale oak) have no 1.12 counterpart and retain native behavior. |
| Legacy stairs registered as `oak_stairs`, `stone_stairs`, `brick_stairs`, `stone_brick_stairs`, `sandstone_stairs`, `spruce_stairs`, `birch_stairs`, `jungle_stairs`, `nether_brick_stairs`, `quartz_stairs`, `acacia_stairs`, `dark_oak_stairs`, `red_sandstone_stairs`, `purpur_stairs` | Same current `minecraft:<name>` IDs; read `FACING`, `HALF`, `SHAPE` and neighboring stair states needed to reproduce 1.12 `resolveVirtualProperties` (same-half neighboring stairs and exact `FACING` gate). Do not use `isFaceSturdy` or modern collision/support face classification as the old rule. Any current stair IDs introduced after 1.12 retain native behavior. |
| Legacy fences: `fence`, `spruce_fence`, `birch_fence`, `jungle_fence`, `acacia_fence`, `dark_oak_fence`, `nether_brick_fence` | Current old-equivalent IDs are `minecraft:oak_fence`, the five named wood fence IDs, and `minecraft:nether_brick_fence`; all map to legacy MIDDLE_POLE/no arm. New fence IDs (mangrove/cherry/bamboo/crimson/warped/pale oak) are modern-only and stay native. |
| Legacy walls: `cobblestone_wall`, `mossy_cobblestone_wall` | Same current IDs; MIDDLE_POLE_THICK/no arm. New wall IDs are modern-only/native. |
| Legacy fence gates: `fence_gate`, `spruce_fence_gate`, `birch_fence_gate`, `jungle_fence_gate`, `acacia_fence_gate`, `dark_oak_fence_gate` | Current old-equivalent IDs are `oak_fence_gate` and those five named wood IDs. Exact category takes current `FACING` and neighbor face; final pane result is always false. New gate IDs stay native. |
| Legacy `cauldron` | Current `minecraft:cauldron` (empty) and `minecraft:water_cauldron` (water level state) map to the old cauldron identity; both are excluded and return false for the pane Boolean. `minecraft:lava_cauldron` and `minecraft:powder_snow_cauldron` have no 1.12 identity and retain native behavior. |
| Legacy `water`, `flowing_water`, `lava`, `flowing_lava` | Current `minecraft:water` represents both old water IDs; current `minecraft:lava` represents both old lava IDs. The old `LiquidBlock` horizontal result is `UNDEFINED` regardless of source/flowing level; the pane result is false. 26.2 expresses flow through fluid state, not separate `flowing_water`/`flowing_lava` block IDs. |
| `piston`, `sticky_piston`, `piston_head` | Same current IDs. Historical `EXTENDED`/`FACING` could alter provider categories, but all three identities are explicitly excluded, so the pane operation returns false without needing those properties. |
| All 1.12 shulker box color IDs: white, orange, magenta, light_blue, yellow, lime, pink, gray, silver, cyan, purple, blue, brown, green, red, black | Same current colored IDs `minecraft:<color>_shulker_box`, with old `silver` mapping to current `light_gray`. All 16 identities are excluded. Current uncolored `minecraft:shulker_box` has no 1.12 registry identity and stays native. The old provider category nevertheless reads `FACING` and block-entity `AnimationStage`; no such current property read is needed to determine the final pane Boolean. |
| Legacy `standing_sign` and `wall_sign` | Map to current `minecraft:oak_sign` and `minecraft:oak_wall_sign`, respectively, both horizontal `UNDEFINED`/false. Other current wood sign identities have no separate 1.12 registry key; hanging signs are modern-only/native. |
| Legacy `standing_banner` and `wall_banner` | Map to all 16 current color-specific `minecraft:<color>_banner` / `minecraft:<color>_wall_banner` identities; all are horizontal `UNDEFINED`/false. Legacy banner color is block-entity data, while current color is in the registry key; no color-dependent pane result exists. |
| Legacy `bed` | Map to all 16 current color-specific `minecraft:<color>_bed` identities; all are horizontal `UNDEFINED`/false. Legacy bed color is read from its block entity; current color is in the registry key; no color-dependent pane result exists. |
| Legacy `wooden_pressure_plate` and `wooden_button` | Map to current `minecraft:oak_pressure_plate` and `minecraft:oak_button`, respectively; both are horizontal `UNDEFINED`/false. Other current wood variants have no separate 1.12 registry identity and stay native. `stone_pressure_plate` and `stone_button` retain their direct IDs. |
| Legacy `wooden_door`, `iron_door`, `spruce_door`, `birch_door`, `jungle_door`, `acacia_door`, `dark_oak_door` | Map to current `oak_door`, `iron_door`, and same-name wood door IDs, respectively; all are horizontal `UNDEFINED`/false. Current later wood doors have no separate 1.12 identity and stay native. |
| Legacy `flower_pot` with 1.12 `FlowerPotBlock.Contents` | Empty maps to current `minecraft:flower_pot`. Old contents map to current potted IDs: `POPPY` (`rose`)→`potted_poppy`; `BLUE_ORCHID`→`potted_blue_orchid`; `ALLIUM`→`potted_allium`; `HOUSTONIA` (`houstonia`)→`potted_azure_bluet`; `RED_TULIP`, `ORANGE_TULIP`, `WHITE_TULIP`, `PINK_TULIP`→the same `potted_*_tulip`; `OXEYE_DAISY`→`potted_oxeye_daisy`; `DANDELION`→`potted_dandelion`; `OAK_SAPLING`, `SPRUCE_SAPLING`, `BIRCH_SAPLING`, `JUNGLE_SAPLING`, `ACACIA_SAPLING`, `DARK_OAK_SAPLING`→same-name `potted_*_sapling`; `MUSHROOM_RED`/`MUSHROOM_BROWN`→`potted_red_mushroom`/`potted_brown_mushroom`; `DEAD_BUSH`, `FERN`, `CACTUS`→same-name `potted_*`. Every mapped ID has the old FlowerPotBlock horizontal `UNDEFINED` result, independent of content. Other current `potted_*` IDs, including torchflower, cherry/pale oak/mangrove saplings, golden dandelion, cornflower, lily of the valley, wither rose, bamboo, nether fungi/roots, azalea variants, and eyeblossoms, have no 1.12 content identity and remain native. |
| Legacy `unlit_redstone_torch` and `redstone_torch` | Depending on legacy `FACING`, map standing states to current `minecraft:redstone_torch` and wall-mounted states to `minecraft:redstone_wall_torch`; both old identities inherit `TorchBlock.getFaceShape` and are horizontal `UNDEFINED`/false. Current redstone standing/wall distinction supplies only the block ID; lit/unlit historical block identity does not affect this result. Soul and copper torches have no 1.12 identity and remain native. |
| 1.12 `leaves` metadata variants and `leaves2` variants | Map only the six era leaf identities: `oak_leaves`, `spruce_leaves`, `birch_leaves`, `jungle_leaves`, `acacia_leaves`, `dark_oak_leaves`. They are excluded by old `instanceof AbstractLeavesBlock`. Current later leaf IDs (mangrove, cherry, pale oak, azalea, flowering azalea) have no 1.12 identity and must stay native; do not use the broad modern leaves tag as a historical exclusion. |
| Remaining explicit excluded blocks | `beacon`, `glowstone`, `ice`, `sea_lantern`, `melon` (legacy `melon_block`), `pumpkin`, `jack_o_lantern` (legacy `lit_pumpkin`), `barrier`; all map to false by identity. Current `packed_ice`/`blue_ice` and `carved_pumpkin` are modern-only here and stay native. |
| Legacy `trapdoor` and `iron_trapdoor` | Map to current `minecraft:oak_trapdoor` and `minecraft:iron_trapdoor`, respectively. Both map to horizontal `UNDEFINED`/false. The current `FACING`, `OPEN`, `HALF`, `POWERED`, and `WATERLOGGED` properties need no read for this horizontal result. Modern spruce/birch/jungle/acacia/dark_oak/cherry/pale_oak/mangrove/bamboo/crimson/warped and copper trapdoors have no separate 1.12 registry identity and retain native behavior. |
| Current IDs corresponding to other constant-UNDEFINED override classes | Select by current old-equivalent block ID (examples/families are below); no block property is read for horizontal pane outcome. A current block introduced after 1.12 that merely resembles or subclasses a modern provider is not included in the old override map and remains native. |

The complete explicit pane-exclusion expression in `PaneBlock.shouldConnectTo(Block)` is `instanceof ShulkerBoxBlock || instanceof AbstractLeavesBlock || block == BEACON || block == CAULDRON || block == GLOWSTONE || block == ICE || block == SEA_LANTERN || block == PISTON || block == STICKY_PISTON || block == PISTON_HEAD || block == MELON_BLOCK || block == PUMPKIN || block == LIT_PUMPKIN || block == BARRIER` (`PaneBlock.java:142-156`). The modern ID selector must map only the old equivalents named above; it must not apply these exclusions to unrelated new blocks.

### Constant-UNDEFINED override IDs for the old provider set

These 1.12 override families have no horizontal state gate: their result is `UNDEFINED` and they never connect a pane. The listed current IDs are the old-equivalent 26.2 registry families; new variants added after 1.12 are omitted and remain native.

- `AbstractPressurePlateBlock`: `stone_pressure_plate`, `light_weighted_pressure_plate`, `heavy_weighted_pressure_plate`, and current `oak_pressure_plate` for the sole 1.12 wooden ID `wooden_pressure_plate`. Other wood pressure plates (including spruce, birch, jungle, acacia, dark_oak and later woods) have no separate 1.12 registry identity and stay native.
- `AbstractRailBlock`: `rail`, `powered_rail`, `detector_rail`, `activator_rail`.
- `AirBlock`, `AnvilBlock`, `BannerBlock`, `BedBlock`: `air`, `anvil`, all 16 color `*_banner`/`*_wall_banner` block identities, and all 16 color `*_bed` identities.
- `BrewingStandBlock`, `ButtonBlock`, `CactusBlock`, `CakeBlock`, `CarpetBlock`: `brewing_stand`; `stone_button` and current `oak_button` for the sole 1.12 wooden ID `wooden_button`; `cactus`; `cake`; the 16 era color `*_carpet` IDs. Other wood buttons have no separate 1.12 registry identity and stay native. Carpet's vertical DOWN SOLID case is irrelevant to horizontal pane neighbors.
- `ChestBlock`, `ChorusFlowerBlock`, `ChorusPlantBlock`, `CobwebBlock`, `CocoaBlock`: `chest`, `trapped_chest`, `chorus_flower`, `chorus_plant`, `cobweb`, `cocoa`.
- `DoorBlock`, `DragonEggBlock`, `EndGatewayBlock`, `EndPortalBlock`, `EndPortalFrameBlock`, `EndRodBlock`, `EnderChestBlock`, `TrapdoorBlock`: current `oak_door` for legacy `wooden_door`, same current `spruce_door`, `birch_door`, `jungle_door`, `acacia_door`, `dark_oak_door`, and `iron_door`; `dragon_egg`, `end_gateway`, `end_portal`, `end_portal_frame`, `end_rod`, `ender_chest`; and legacy `trapdoor`/`iron_trapdoor`, mapping to current `oak_trapdoor`/`iron_trapdoor`. EndPortalFrame's SOLID DOWN case and Trapdoor's closed-face SOLID UP/DOWN cases are irrelevant horizontally.
- `FireBlock`, `FlowerPotBlock`, `FarmlandBlock`, `GrassPathBlock`, `HopperBlock`, `LadderBlock`, `LeverBlock`: `fire`; `flower_pot` plus only the exact old `Contents`→current `potted_*` aliases enumerated above; `farmland`; current `dirt_path` for legacy `grass_path`; `hopper`; `ladder`; `lever`. Current soul fire and post-1.12 potted IDs are modern-only/native.
- `LiquidBlock`, `MovingBlock`, `PlantBlock`, `PortalBlock`, `RedstoneWireBlock`: legacy `water`/`flowing_water` map to current `water`, and legacy `lava`/`flowing_lava` map to current `lava`; `moving_piston`; only block IDs whose 1.12 block class inherited this `PlantBlock` override; `nether_portal`; `redstone_wire`. Modern-only plants keep native behavior.
- `SignBlock`, `SkullBlock`, `SnowLayerBlock`, `StructureVoidBlock`, `SugarCaneBlock`, `TorchBlock`, `TripwireBlock`, `TripwireHookBlock`, `VineBlock`: current `oak_sign` for legacy `standing_sign`, `oak_wall_sign` for legacy `wall_sign`; old skull/head identities; `snow`, `structure_void`, `sugar_cane`, ordinary `torch`/`wall_torch`, and redstone standing/wall torch IDs for legacy `redstone_torch`/`unlit_redstone_torch`; `tripwire`, `tripwire_hook`, `vine`. Other wood signs and all hanging signs, plus soul/copper torches, are modern-only/native.
- Vertical-only overrides also include `DaylightDetectorBlock` (`daylight_detector`) and `DiodeBlock` (`repeater`, `comparator`); horizontal result is `UNDEFINED`.

For the old default-provider set, the 1.12 block registry initializer in `Block.java:747-1350` is the membership source. For a current 26.2 `minecraft:` ID, only map to the base SOLID provider if it is an old registry ID or an explicit old-to-current renamed alias and its old class is not one of the override classes above. Otherwise preserve native behavior. This is a bounded source-derived rule, not a claim that all modern blocks inherit old SOLID behavior.

### Explicit base-provider selector

The selector is a finite registry-key whitelist plus the exhaustive provider overrides above, not a geometry heuristic:

1. Read the adjacent state's current `minecraft:` registry key. It is in historical scope only if that exact key is registered by the 1.12 `Block.init()` registry initializer (`Block.java:747-1350`) or it is one of the explicit renamed/current aliases in the preceding table. For direct keys, require the same key to exist in 26.2 `Blocks.java`/`BlockIds.java`; do not infer an old identity from class inheritance, tags, or visual similarity. For renamed IDs and folded slab metadata, use only the enumerated aliases and old state mapping.
2. Resolve that old identity to its 1.12 registered block class using the same initializer. If that class inherits any concrete `getFaceShape` override inventoried above, use that override's horizontal result and apply the exact old pane exclusion expression. If the resolved class inherits the base `Block.getFaceShape`, its horizontal result is `SOLID`; then apply the same exclusions.
3. Current keys outside that finite historical key/alias set retain native behavior, even when 26.2 assigns them a class or shape similar to an old provider.

This specifies the default-SOLID mapping exactly as `HistoricalRegistryKeys ∩ CurrentRegistryKeys − HistoricalOverrideIdentities`, with the explicit aliases substituted before the set comparison, followed by the 1.12 exclusion predicate. `HistoricalRegistryKeys` and each registered class are the source literals/constructor expressions in `Block.init()`; the preceding override inventory is exhaustive for the block package. The 26.2 current-key evidence is the hashed `Blocks.java`, `BlockIds.java`, and `BlockItemIds.java` above. The rule is intentionally not expanded to arbitrary current IDs or modern full-face support results. Implementations may materialize this finite set as ID constants; they must not use the sentence as permission to treat unlisted IDs as historical.

The concrete, machine-usable 185-ID result of that selector is bound by the sibling crosswalk [legacy-block-id-crosswalk.md](../pane-faceshape-overrides/legacy-block-id-crosswalk.md), section “Materialized 1.12.2 base-`SOLID` current-ID set”, SHA-256 `AD9B694AAF5EBF9BDFEFDFB5ACB75EFDF568456C8718F5FEEE7690845C5B93A1`. That artifact derives each current candidate from its numeric 1.12 registry identity, follows local class inheritance, removes provider overrides and pane exclusions, and filters against enumerated 26.2 registry IDs. Use that exact set for the base-provider case; do not regenerate it by modern geometry or broaden it from tags.

## Source fingerprints

Every concrete 1.12 provider file below was rehashed from the canonical source root; `Block.java` is the source for both the default provider and the complete 1.12 ID registration initializer. Current registry membership is sourced from exact `Blocks.java`, `BlockItemIds.java`, `BlockIds.java`, and `BlockTags.java` files. Current class files are listed for the stateful providers and exclusions.

| Exact source file | SHA-256 |
|---|---|
| 1.12 `net/minecraft/block/AbstractPressurePlateBlock.java` | `B9DF850102E619002D9CE6AA6A69A133465B709602EBDAFFBFBC5BC67015DD5D` |
| 1.12 `net/minecraft/block/AbstractRailBlock.java` | `C68E554D052BB9E6D9ED11A9C21A89601E03D032A8B8B3D09FA5681DE9F83DE1` |
| 1.12 `net/minecraft/block/AirBlock.java` | `AC009EAAC5EDC3693F5827DFE330EC4F36749D51814E8901EFDEF95480043202` |
| 1.12 `net/minecraft/block/AnvilBlock.java` | `80729F34A74FF8B1B4B2D59E55C5D8DD9D61FFF94C47F862C61C12D9947EB8BB` |
| 1.12 `net/minecraft/block/BannerBlock.java` | `CD6793DCF6344FE613E1245A299424BD5BD0F4A1E34F3B4CA72F774D6AB5192D` |
| 1.12 `net/minecraft/block/BedBlock.java` | `48C127B2DF425CB8D7FF60A284207DE10A8408E90EE18A8449522A004A0B4369` |
| 1.12 `net/minecraft/block/Block.java` | `E4A90ECA411E7B0E14F5018F7385EC29891F6CDF1A1E917FA648B5712F9B33A1` |
| 1.12 `net/minecraft/block/BrewingStandBlock.java` | `E875022B9A47CA6336878BCEC205459462687BB35D567EE183F7EC2F1924A4FD` |
| 1.12 `net/minecraft/block/ButtonBlock.java` | `D1D17EC936C10A4F82A0746C75C80E9B9B75C23A73CA73BF193065BA77786894` |
| 1.12 `net/minecraft/block/CactusBlock.java` | `AEA8EDECA0B91CD7EED1980A59DCC2ABB53298A4CE445231FAD98B5D6C1D04F4` |
| 1.12 `net/minecraft/block/CakeBlock.java` | `14BA2AAE3167B2BC1D14C8EB40DA8C9FDBE762B5991BD360165A34EF501EC483` |
| 1.12 `net/minecraft/block/CarpetBlock.java` | `67DB1AC10DBE872D9DDEB13914333F614EEF52003EA8F274FD113488AD84D1F2` |
| 1.12 `net/minecraft/block/CauldronBlock.java` | `0AFF4A0DE147E0C677F4B931CD9A881154666BB288F128C9D8041B70C38D63F9` |
| 1.12 `net/minecraft/block/ChestBlock.java` | `CC63486B970A8A7670910B9D4E24C8DE4B616DC53C9CE2F01260CFAD53A5E178` |
| 1.12 `net/minecraft/block/ChorusFlowerBlock.java` | `FF3D646A0951E486F0754657D01692E8BDC36CA22CF5E85EF93382AE59D0E0CE` |
| 1.12 `net/minecraft/block/ChorusPlantBlock.java` | `E726A991D9A1F9D3FBC14DADCFF261BD2780920F8E75A4EECCD128F9CC69E336` |
| 1.12 `net/minecraft/block/CobwebBlock.java` | `DB5E0E9197AEB5DC518E2EDA91BC785F2FBC1E0459DB7AEFCB0505D8B3AE8767` |
| 1.12 `net/minecraft/block/CocoaBlock.java` | `653021A977A09F4C6BEFED7F5A84DCBC336560D4291BA8534105D778F6AEB52C` |
| 1.12 `net/minecraft/block/DaylightDetectorBlock.java` | `04D8142DCE7000E86F910F070991F43E56FC7B62D060B1872295A14D4A41D3C0` |
| 1.12 `net/minecraft/block/DiodeBlock.java` | `31B9F15F6FCD5EFCD6D1C2074881D7E3B4764EBB4E604DC0ECAD016F6F6ACFE8` |
| 1.12 `net/minecraft/block/DoorBlock.java` | `500C8088648398C23ABFB8404158A669CDDAD1687F426C12AD4952638F45BB73` |
| 1.12 `net/minecraft/block/DoubleStoneSlabBlock.java` | `1A3734AC32471179BA9B4637E857F0131968077ABBE1F82535ACC3FE73300729` |
| 1.12 `net/minecraft/block/DragonEggBlock.java` | `F44781C433B00AC27B7924DF5DA0A8E4AE31778A76CF1F49DF5C698A57AFCF1A` |
| 1.12 `net/minecraft/block/EnchantingTableBlock.java` | `8A40C6C0F548F0852F9FDD62FC9AB372B7FB87773039038268BA5B5C57EB04DA` |
| 1.12 `net/minecraft/block/EnderChestBlock.java` | `70C8E7A2DC1DDD8F9585BF1EF3BC7A222022213C184631298C7D3D4A654C4AFD` |
| 1.12 `net/minecraft/block/EndGatewayBlock.java` | `68A5026CACEB31CE4645ACF7F978F99DAB3C8F567ED1CB4618031C44C2947FCC` |
| 1.12 `net/minecraft/block/EndPortalBlock.java` | `EC3EDA037B464A6B6FDEE78703934B1404A4AC2908B56157D7B42DF865FFC544` |
| 1.12 `net/minecraft/block/EndPortalFrameBlock.java` | `418A00540523C106571DF82C95716E305D4FBB55AD5867148028A7611A9CCA11` |
| 1.12 `net/minecraft/block/EndRodBlock.java` | `AD3F9D6ABF8D990C4E2D7DC86389A066C10683DA814B2F9562CEEE6944C0CBD0` |
| 1.12 `net/minecraft/block/FarmlandBlock.java` | `B5902F8FAB6BB9388BE5E488021A4977783FEA0CA9404798460F207980374DA2` |
| 1.12 `net/minecraft/block/FenceBlock.java` | `633AD3B20250BD010A28D0B1362B24C81A5508BE8A81A9D826953A3C9C4A703E` |
| 1.12 `net/minecraft/block/FenceGateBlock.java` | `38AE9E6AA1057A8E9B65FF78414D0D6A9A70CF2645F1EA42141FF2815AD6A477` |
| 1.12 `net/minecraft/block/FireBlock.java` | `E3B16D307D1EDDD34823E586ED41C32A0A14729F1DC3D88172248F25AA9A8213` |
| 1.12 `net/minecraft/block/FlowerPotBlock.java` | `BC4504C49957276963AE2F5800D8B35FE0CBBB5B6D251D0D3D99B68187662E26` |
| 1.12 `net/minecraft/block/GrassPathBlock.java` | `E5F773E5E8874360A5CE1B8F41DB3785D3132E8F1CAF07E70F9C8DB1FD374C3` |
| 1.12 `net/minecraft/block/HopperBlock.java` | `3510F351F1A6B913DD834D1C8696D49E888D3A7CD9DD307388D3551C9CABFB6C` |
| 1.12 `net/minecraft/block/LadderBlock.java` | `C9D59CD266AF92A731710FDA0E2A8E60C61FB07131FECDD3B04759277AE24D94` |
| 1.12 `net/minecraft/block/LeverBlock.java` | `13B4DD69759F7DE12E6ECF9A67464DC5704B249B0E6306EB6279FF5F85A9BF2C` |
| 1.12 `net/minecraft/block/LiquidBlock.java` | `00F7995DD49A48BF97316E30B3D6E41D8EE1E65DD212F3DC64891883621C4260` |
| 1.12 `net/minecraft/block/MovingBlock.java` | `2BCEA815334849934EAA3283EEAD3A7A27F7B77503A8789AAE1479AF725DF5C4` |
| 1.12 `net/minecraft/block/PaneBlock.java` | `83862F6633B8241C3788E9C9A7E4FD0275C010EC790019B8003908996F4814B3` |
| 1.12 `net/minecraft/block/PistonBaseBlock.java` | `A0B929DEC717A33355B291C6483430288A6A0EDB9A4F3338B7D708B282898626` |
| 1.12 `net/minecraft/block/PistonHeadBlock.java` | `849757A70F525192FC01BAC4883388326F9CBE55267AC336D78F378BEDDEAC39` |
| 1.12 `net/minecraft/block/PlantBlock.java` | `919EB37D8E751B1515D0E9CB3AF6B8CE516D2409D9639FDF4BFA6260FAD5808C` |
| 1.12 `net/minecraft/block/PortalBlock.java` | `D8FB94FF43876692BB61FBB61780995560C9C162C24702D2E25C33062C02ABDE` |
| 1.12 `net/minecraft/block/RedstoneWireBlock.java` | `32D0565B06C515B2958C000432A23F41AFF76E3E4435337117ABF74BABF2C24E` |
| 1.12 `net/minecraft/block/ShulkerBoxBlock.java` | `AF7C152C3752223234EF406485BB667346D3511143B77F3384847F95AC81C076` |
| 1.12 `net/minecraft/block/SignBlock.java` | `973597D3B36632DEC0275A5C58A8547B8EE1BE2A242ADA73961C37B7C1BE6987` |
| 1.12 `net/minecraft/block/SkullBlock.java` | `75139B117771CCF795EB63BF578EB3B16BF3F988660FCE04C4DEAD5F7CB97B0C` |
| 1.12 `net/minecraft/block/SlabBlock.java` | `DC510F95B373BC2ECCD128FBC82E2F07BABC658D8446AB1DBC4E5DD4F27293E0` |
| 1.12 `net/minecraft/block/StoneSlabBlock.java` | `00562D8697BF21950356A54B01D5CC13310623A672330DF4105261E539D362C4` |
| 1.12 `net/minecraft/block/WoodenSlabBlock.java` | `A9C768C41157611D357596923DFC44A7B3688A772EBFAA44BA36D69CD8DB30ED` |
| 1.12 `net/minecraft/block/RedSandstoneSlabBlock.java` | `1A8B040422BC9C96A5A5428D953CA2AC1D3996C4335C6429EBEE2070A5ED9ADB` |
| 1.12 `net/minecraft/block/PurpurSlabBlock.java` | `0A49F331E58CE2B75153DC36C38179EDE0A927A9E92E3F91F8BDC859BD304E28` |
| 1.12 `net/minecraft/block/SnowLayerBlock.java` | `6ED1D83D9230CD3C1434B4EF5055963048763EEA4DBDEAA6A58DD3B8591B915B` |
| 1.12 `net/minecraft/block/StairsBlock.java` | `C52A011A759B6F3EF6D45162D3B61F464CECEEE5D2D8735DE6F6E25A56E55611` |
| 1.12 `net/minecraft/block/StructureVoidBlock.java` | `857B239BA586B19BF50F094A27E023C03D0B1A1C19DBC8FC1B398A2A9E3F494E` |
| 1.12 `net/minecraft/block/SugarCaneBlock.java` | `818AFDC116666C4174485AB9F9A71A7A44A274AF676A344392FC13585C2D00ED` |
| 1.12 `net/minecraft/block/TorchBlock.java` | `78AEEE1E8C03AA0544F6D7E9FBBA2555608CA9C721D1541D0D01EBF2AA9EA009` |
| 1.12 `net/minecraft/block/TrapdoorBlock.java` | `A7033C517B6B71EA1926BBB16FF67C00172FE753226DAE689C215FF24E8B26CB` |
| 1.12 `net/minecraft/block/TripwireBlock.java` | `54A4FE498C0865EC06268E75731104BBD3D372F6C84FB5309E6DCE39CE0C02C7` |
| 1.12 `net/minecraft/block/TripwireHookBlock.java` | `C8D9A5BC7CD3057314CB66F67FB3273EB3119FC54D8B97B65A578DAF680B5EEA` |
| 1.12 `net/minecraft/block/VineBlock.java` | `3EE4302208BA5820E5FA7F3B166988B673BA691DC78823D1CCFD5E62712368AC` |
| 1.12 `net/minecraft/block/WallBlock.java` | `7E4378C46E367C32DECA14475E5161B6E4223947D83153CE3E7BA5C8A0D18E50` |
| 26.2 `net/minecraft/world/level/block/TrapDoorBlock.java` | `3FE7FF071A7BF9D03B072DD453FE18D546F5D7F780839A39E5EA2D08BB48F3F8` |
| 26.2 `net/minecraft/world/level/block/FlowerPotBlock.java` | `0C0BE9DB4BB3F386EE64D0A19A60001753F6894EEA5A904E9625FC2E211A54A2` |
| 26.2 `net/minecraft/world/level/block/RedstoneTorchBlock.java` | `6BC5E1A04E24BBEC5150B1336E6B1E3D1BD203D27105B3974B1A37750B3D08E3` |
| 26.2 `net/minecraft/world/level/block/RedstoneWallTorchBlock.java` | `CC0DAC4348D3DB476709B48D42582EC7D609496D874894B1A8D956C881173CA1` |
| 26.2 registry `net/minecraft/world/level/block/Blocks.java` | `F3F2FAEED23E9697407069A1D523107491590B8710175523EA05294D5BD00435` |
| 26.2 item/block key table `net/minecraft/references/BlockItemIds.java` | `D9D57F2F04801DA5D8D583C23B87B08F45D8B6C681980D1A77308753C801BAC6` |
| 26.2 registry-only key table `net/minecraft/references/BlockIds.java` | `0F342F57727CFE99966DA9706DA7DDB330654CB59684529166B6F175171BA69F` |
| 26.2 `net/minecraft/tags/BlockTags.java` | `9834ECBE2FACD79CF3D9FC79BABD452ED0E000684029B2BB5E9F90AA4F51529E` |

## Exact validation limits

This supplements the accepted 1.12 alias and pane-neighbor findings; it does not upgrade missing aggregate 1.12 source attestations. All current IDs that did not exist in 1.12—including later wood families, later leaf families, newer potted blocks, newer stairs/slabs/fences/walls/gates, soul fire/torches, and hanging signs—remain on native behavior. For legacy blocks whose ID was renamed or whose single/double variants were folded into a modern state property, only the explicit aliases above are closed. The base-SOLID rule may be applied only after bounded old-ID membership and override/exclusion classification; do not fall back to a blanket modern full-cube/support-shape test.

No source generation, product edit, test, build, game, TAS, Gym or runtime was used.

## Selector digest correction and rebind

The preceding 185-ID binding records the original crosswalk revision `AD9B694AAF5EBF9BDFEFDFB5ACB75EFDF568456C8718F5FEEE7690845C5B93A1` for provenance only. It is superseded as an implementation selector. The corrected sibling [legacy-block-id-crosswalk.md](../pane-faceshape-overrides/legacy-block-id-crosswalk.md) has SHA-256 `9C2CDC0CFA4A1A6CE37E1B4AC22AB19183E3006968ADC3EA4C7F896D2FF6E486`; use its corrected 185-ID materialized base-`SOLID` set. In particular, old `snow_layer` maps to current `snow` (horizontal `UNDEFINED` override), while old full `snow` maps to current `snow_block` (base `SOLID`).

The previous independent audit `pane-faceshape-audit/audit.md`, SHA-256 `A3FF1D19FDE826C07A52FE06B8BF845F03323B4D5DE24981F50D0A9819B6E788`, reviewed the superseded crosswalk digest and is stale for the corrected selector set; it does not accept this rebind. Re-audit must bind the exact corrected digest before the changed set is treated as independently accepted.

The separate [legacy-1112-block-id-crosswalk.md](../pane-faceshape-overrides/legacy-1112-block-id-crosswalk.md), SHA-256 `1BAC7F23D7DB8561099CB8DBC0350BEE73B39AC89FE738358EE8A348DD59B982`, describes 1.11.2 default-state cube-provider and pane-predicate policy. It is referenced for adjacent historical provider policy only; it is not the 1.12 FaceShape base-`SOLID` selector and must not be substituted for the corrected 1.12 set.
