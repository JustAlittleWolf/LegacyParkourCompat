# 1.8.9 Anvil (block ID 145): serialized-state projection for the legacy bounds read

## Result

For valid 1.8.9 Anvil states, the serialized chunk palette maps metadata 0–11 to three registered 26.2 block identities and the same horizontal facing. The old metadata decoder yields damage tiers 0–2; metadata 12–15 attempts disallowed damage 3 and is not a valid old block state. The modern target has no `damage` property: damage is represented by the selected block key (`anvil`, `chipped_anvil`, `damaged_anvil`), while `facing` remains the only state property. The old mutable collision bounds depend only on the decoded facing axis, not damage.

This is the exact flattened 26.2 `BlockStateData` used by `ChunkPalettedStorageFix`, joined by the old decoder tuple. It does not infer equality from numeric 1.12 table indices. The per-metadata projection is in `anvil-projection.tsv`.

## Exact old decoder and bounds writer

1.8.9 `ornithe-feather` `net/minecraft/block/Block.java:1141` registers ID 145 as the Anvil singleton. `AnvilBlock.java:27–31,104–117`: `DAMAGE` is `IntegerProperty.of("damage", 0, 2)`; `getStateFromMetadata(m)` sets `FACING` to `Direction.byIdHorizontal(m & 3)`, then sets `DAMAGE` to `(m & 15) >> 2`. `Direction.java:12–18,172–174,226–230` assigns horizontal IDs 0=SOUTH, 1=WEST, 2=NORTH, 3=EAST; the lookup is indexed by the four horizontal IDs. `StateDefinition.java:119–130` rejects values absent from the property's allowed set. Therefore 0–11 decode as three consecutive tiers with SOUTH/WEST/NORTH/EAST order; 12–15 decode to damage 3 and fail instead of producing an old state.

The mutable shape writer is `AnvilBlock.updateShape(WorldView, BlockPos)` (`AnvilBlock.java:67–74`): X-axis facing writes `[0,0,0.125]–[1,1,0.875]`; Z-axis facing writes `[0.125,0,0]–[0.875,1,1]`. It reads neither damage nor neighboring/world context beyond the queried state's facing axis.

## Serialized chunk palette route and ID-145 table slice

26.2 `DataFixers.java:532–535` registers `ChunkPalettedStorageFix` at schema `1451.1`. In `ChunkPalettedStorageFix.Section.upgrade` (`ChunkPalettedStorageFix.java:710–750`), the old per-cell ID is `(addBlocks << 12) | ((blocks[idx] & 255) << 4) | data[...]`; the code calls `BlockStateData.getTag(id)` for the resulting index. `BlockStateData.register` (`BlockStateData.java:686–705`) stores the first argument as the modern `MAP[index]` output and registers later legacy tuples in `ID_BY_OLD`; `finalizeMaps` fills missing metadata slots from that block's first registered output, and `getTag` is a direct indexed lookup (`:737–744`).

The ID-145 slice is exactly `index = 145 * 16 + metadata`:

| Metadata | Old decoded tuple | DFU index | 26.2 output block state |
|---:|---|---:|---|
| 0 | damage=0, facing=south | 2320 | `minecraft:anvil[facing=south]` |
| 1 | damage=0, facing=west | 2321 | `minecraft:anvil[facing=west]` |
| 2 | damage=0, facing=north | 2322 | `minecraft:anvil[facing=north]` |
| 3 | damage=0, facing=east | 2323 | `minecraft:anvil[facing=east]` |
| 4–7 | damage=1, facing south/west/north/east | 2324–2327 | `minecraft:chipped_anvil[facing=<same>]` |
| 8–11 | damage=2, facing south/west/north/east | 2328–2331 | `minecraft:damaged_anvil[facing=<same>]` |
| 12–15 | invalid old damage=3 tuple | 2332–2335 | raw numeric fallback only: `minecraft:anvil[facing=south]` |

Each row is literal in `BlockStateData.java:6682–6693` as `register(index, create(modernName, modernProperties), create("minecraft:anvil", {damage, facing}))`. The legacy aliases are compared directly with the exact 1.8.9 decoder output. Fallback rows have no valid old state and must not be treated as a compatibility projection.

`ChunkPalettedStorageFix` contextual section flags `FIX` and `VIRTUAL` are initialized at `:611–670`; ID 145 is in neither set. Ordinary ID-145 palette conversion schedules no neighbor/virtual update. The inspected 26.2 `DataFixers.java` has no later Anvil-specific block-state fix. `BlockNameFlatteningFix` at schema `1451.4` is separately registered for `BLOCK_NAME`; it is not another name substitution over the already emitted chunk `BLOCK_STATE` palette.

## Ordered renames and current registry/codec

`DataFixers.java` is the ordered registration source for `BlockRenameFix` through 26.2. `BlockRenameFix.makeRule` (`BlockRenameFix.java:25–56`) updates only `Name` in a block-state tag, retaining its other fields. The ordered registration inventory contains no rename targeting `minecraft:anvil`, `minecraft:chipped_anvil`, or `minecraft:damaged_anvil`; the three table names and their `facing` tuples therefore survive those later renames unchanged. The exact `DataFixers.java` and `BlockRenameFix.java` files are hash-bound below.

`BlockItemIds.java:441–443` declares all three keys; `BlockItemId.create(String)` (`BlockItemId.java:17–19`) supplies the default `minecraft` namespace. `Blocks.java:2853–2881` registers each key with `AnvilBlock::new` and ordinary collision-enabled properties. `AnvilBlock.java:34,49,121–122` defines only `FACING`, defaults it to NORTH, and adds only `FACING` to its state definition. Its shape method (`:77–79`) selects by `state.FACING` axis; level, position and `CollisionContext` do not affect it. Current `BlockState.CODEC` (`BlockState.java:9`) dispatches by the registered block-name codec and defaults through `Block::defaultBlockState`; `StateHolder.codec` (`StateHolder.java:145–156`) decodes `Properties` with that block's property codec or uses the block default when `Properties` is absent. Since the DFU output has an explicit valid facing, the current tuple is exact; the damage tier resides solely in the block key.

The current modern Anvil shape is a composed silhouette (`AnvilBlock.java:35–37,77–79`), not the legacy full-height rectangular collision box. A historical bounds response must synthesize the 1.8.9 box from the projected state's facing axis; passing through the modern Anvil shape is not equivalent. The historical shape is independent of damage tier.

## Gates and limits

- **Tuple gate:** source-faithful conversion is established only for valid old metadata 0–11, using both old decoder values and the flattened-table legacy tuple. Invalid 12–15 are not accepted merely because `getTag` fills their slots.
- **Name-only gate:** `BlockStateData.upgradeBlock(String)` uses `ID_BY_OLD_NAME`, while `upgradeBlockStateTag` uses a complete old tag and section conversion uses the numeric tuple. A block-name-only route cannot preserve decoded facing or damage tier; state projection needs the old tuple / palette index.
- **Context gate:** ID 145 is outside both `FIX` and `VIRTUAL`; the serialized projection needs no adjacent-block or section-neighbor data. The current anvil shape provider also ignores position and context. This closes static state projection only, not the old shared mutable-bounds lifecycle/scheduling condition or other movement operations.

## Source identity

Canonical read-only sources are `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.8.9/ornithe-feather` (Ornithe Feather Gen2 build 2) and `.../26.2/unobfuscated`. Exact source digests plus ready/provenance/manifest identities are recorded in `source-hashes.sha256`. This reuses the accepted lifecycle and newer-provider reports at `.native-source/20261030/dda-bounds-order/` and `.native-source/20261030/bounds-newer-contract/`; claims above were checked against the exact methods listed.
