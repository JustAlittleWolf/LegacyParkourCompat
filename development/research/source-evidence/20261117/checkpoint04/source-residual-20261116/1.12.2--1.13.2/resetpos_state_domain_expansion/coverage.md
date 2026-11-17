# ResetPos legal state-domain expansion: 1.12.2 → 1.13.2

This source-only artifact expands registered finite state domains for the exact 184 common-key ResetPos provider inventory. It does not compare geometry, prove any shape intersection, close ResetPos equivalence, or claim pair freeze.

- Input inventory: `resetpos_block_state_residual_matrix/coverage.md` SHA-256 `C4AC654CBBE8A4958CC2B1388F05A2CE44E2FD320395DB6F60D2EB332F8CC127`.
- Input key/provider matrix SHA-256 `b7294302cd98bc923f543608ee9f5935a24a02033ac6033828c339dc702a2974`; output state table SHA-256 `f12118dc09be593fdd58b268469b985fef95cfe644f3f5916aac4525977cb382`; consulted-source table SHA-256 `a5a14938cf0b1ba5737e2f74a6774cf14ae17ec8e1c3520c69670443d4781d7a`.
- 1.12.2 manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`, ready record SHA-256 `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a`; 1.13.2 manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`, ready record SHA-256 `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38`.
- Expanded rows: 368 endpoint/key rows for 184 exact common keys.
- State-domain status: 368 rows resolved from source-declared properties; 0 rows partial/unresolved.
- The state table records each resolved finite property value set and Cartesian count. For unresolved branches/filters it retains the known property domains and the exact unresolved source condition in `notes`; do not treat partial rows as singleton.

- Registered state cardinality differs for 57 keys; registered provider class differs for 17 keys. Full lists follow.

## State-cardinality differences

- `minecraft:acacia_fence`: 1.12.2 `16` → 1.13.2 `32`
- `minecraft:acacia_stairs`: 1.12.2 `40` → 1.13.2 `80`
- `minecraft:activator_rail`: 1.12.2 `12` → 1.13.2 `8`
- `minecraft:anvil`: 1.12.2 `12` → 1.13.2 `4`
- `minecraft:birch_fence`: 1.12.2 `16` → 1.13.2 `32`
- `minecraft:birch_stairs`: 1.12.2 `40` → 1.13.2 `80`
- `minecraft:bone_block`: 1.12.2 `3` → 1.13.2 `1`
- `minecraft:brick_stairs`: 1.12.2 `40` → 1.13.2 `80`
- `minecraft:brown_mushroom_block`: 1.12.2 `13` → 1.13.2 `64`
- `minecraft:chest`: 1.12.2 `4` → 1.13.2 `24`
- `minecraft:dark_oak_fence`: 1.12.2 `16` → 1.13.2 `32`
- `minecraft:dark_oak_stairs`: 1.12.2 `40` → 1.13.2 `80`
- `minecraft:daylight_detector`: 1.12.2 `16` → 1.13.2 `32`
- `minecraft:detector_rail`: 1.12.2 `12` → 1.13.2 `8`
- `minecraft:dirt`: 1.12.2 `6` → 1.13.2 `1`
- `minecraft:ender_chest`: 1.12.2 `4` → 1.13.2 `8`
- `minecraft:flower_pot`: 1.12.2 `352` → 1.13.2 `1`
- `minecraft:furnace`: 1.12.2 `4` → 1.13.2 `8`
- `minecraft:glass_pane`: 1.12.2 `16` → 1.13.2 `32`
- `minecraft:grass`: 1.12.2 `2` → 1.13.2 `1`
- `minecraft:hay_block`: 1.12.2 `3` → 1.13.2 `1`
- `minecraft:hopper`: 1.12.2 `2` → 1.13.2 `10`
- `minecraft:iron_bars`: 1.12.2 `16` → 1.13.2 `32`
- `minecraft:iron_trapdoor`: 1.12.2 `16` → 1.13.2 `64`
- `minecraft:jungle_fence`: 1.12.2 `16` → 1.13.2 `32`
- `minecraft:jungle_stairs`: 1.12.2 `40` → 1.13.2 `80`
- `minecraft:ladder`: 1.12.2 `4` → 1.13.2 `8`
- `minecraft:lever`: 1.12.2 `16` → 1.13.2 `24`
- `minecraft:nether_brick_fence`: 1.12.2 `16` → 1.13.2 `32`
- `minecraft:nether_brick_stairs`: 1.12.2 `40` → 1.13.2 `80`
- `minecraft:oak_stairs`: 1.12.2 `40` → 1.13.2 `80`
- `minecraft:prismarine`: 1.12.2 `3` → 1.13.2 `1`
- `minecraft:pumpkin`: 1.12.2 `4` → 1.13.2 `1`
- `minecraft:purpur_pillar`: 1.12.2 `3` → 1.13.2 `1`
- `minecraft:purpur_slab`: 1.12.2 `2` → 1.13.2 `6`
- `minecraft:purpur_stairs`: 1.12.2 `40` → 1.13.2 `80`
- `minecraft:quartz_block`: 1.12.2 `5` → 1.13.2 `1`
- `minecraft:quartz_stairs`: 1.12.2 `40` → 1.13.2 `80`
- `minecraft:red_mushroom_block`: 1.12.2 `13` → 1.13.2 `64`
- `minecraft:red_sandstone`: 1.12.2 `3` → 1.13.2 `1`
- `minecraft:red_sandstone_stairs`: 1.12.2 `40` → 1.13.2 `80`
- `minecraft:redstone_lamp`: 1.12.2 `1` → 1.13.2 `2`
- `minecraft:redstone_ore`: 1.12.2 `1` → 1.13.2 `2`
- `minecraft:redstone_torch`: 1.12.2 `1` → 1.13.2 `2`
- `minecraft:sand`: 1.12.2 `2` → 1.13.2 `1`
- `minecraft:sandstone`: 1.12.2 `3` → 1.13.2 `1`
- `minecraft:sandstone_stairs`: 1.12.2 `40` → 1.13.2 `80`
- `minecraft:snow`: 1.12.2 `1` → 1.13.2 `8`
- `minecraft:sponge`: 1.12.2 `2` → 1.13.2 `1`
- `minecraft:spruce_fence`: 1.12.2 `16` → 1.13.2 `32`
- `minecraft:spruce_stairs`: 1.12.2 `40` → 1.13.2 `80`
- `minecraft:stone`: 1.12.2 `7` → 1.13.2 `1`
- `minecraft:stone_brick_stairs`: 1.12.2 `40` → 1.13.2 `80`
- `minecraft:stone_button`: 1.12.2 `12` → 1.13.2 `24`
- `minecraft:stone_slab`: 1.12.2 `16` → 1.13.2 `6`
- `minecraft:trapped_chest`: 1.12.2 `4` → 1.13.2 `24`
- `minecraft:wall_sign`: 1.12.2 `4` → 1.13.2 `8`

## Registered provider-class aliases

- `minecraft:bone_block`: `BoneBlock` → `AxisBlock`
- `minecraft:dirt`: `DirtBlock` → `Block`
- `minecraft:glass_pane`: `PaneBlock` → `GlassPaneBlock`
- `minecraft:grass`: `GrassBlock` → `TallPlantBlock`
- `minecraft:iron_bars`: `PaneBlock` → `IronBarsBlock`
- `minecraft:lava`: `LiquidSourceBlock` → `LiquidBlock`
- `minecraft:netherrack`: `NetherrackBlock` → `Block`
- `minecraft:obsidian`: `ObsidianBlock` → `Block`
- `minecraft:prismarine`: `PrismarineBlock` → `Block`
- `minecraft:purpur_slab`: `PurpurSlabBlock.Single` → `SlabBlock`
- `minecraft:quartz_block`: `QuartzBlock` → `Block`
- `minecraft:red_sandstone`: `RedSandstoneBlock` → `Block`
- `minecraft:sandstone`: `SandstoneBlock` → `Block`
- `minecraft:snow`: `SnowBlock` → `SnowLayerBlock`
- `minecraft:stone_slab`: `SingleStoneSlabBlock` → `SlabBlock`
- `minecraft:trapped_chest`: `ChestBlock` → `TrappedChestBlock`
- `minecraft:water`: `LiquidSourceBlock` → `LiquidBlock`

## Scope limits

No provider result is inferred from state count or names. Registration-constructor parameters can choose dynamic state-definition branches (for example single/double slab shapes); if not established from exact key construction this report leaves the row partial. Aliases, property product reachability in maps, and geometric intersection with ResetPos probe boxes remain open. All consulted Java bytes are listed in `consulted-source-hashes.csv` and compared with endpoint source manifests.
