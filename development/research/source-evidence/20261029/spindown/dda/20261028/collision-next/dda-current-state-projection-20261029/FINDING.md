# 1.8.9 numeric block state → 26.2 DataFixer projection

## Result and boundaries

The extraction creates a complete **26.2 legacy-table candidate** for each numeric index `id << 4 | metadata` with old ID 0–197 and metadata 0–15 (3,168 rows). It does not treat that numeric table as proof that every 1.8.9 decoder state equals the later table entry. The verified old-source join is narrower: 8 IDs × all 16 metadata values (128 rows) are proven below. All other pairs retain candidate-only/open status in `block-state-data-current-name-projection.jsonl`.

The bounded set is IDs **4, 7, 22, 41, 42, 45, 48, 57**: cobblestone, bedrock, lapis block, gold block, iron block, brick block, mossy cobblestone, and diamond block. The accepted 1.8.9 projection rules establish that their base `Block` state decoder ignores metadata and returns the registered default state. Their exact source registrations instantiate base `Block`, whose state definition has no properties. In 26.2 each corresponding target in `Blocks.java` uses the two-argument `register(id, Properties)` overload; that overload calls `register(id, Block::new, properties)`. `Block.createBlockStateDefinition` is empty, so omitted `Properties` decodes to the exact empty default state. Every one of the 128 rows has an old-name, empty-property alias in the selected `BlockStateData` entry, a registered final 26.2 name, and no row-applicable later state-property fix.

The qualifier checks each `BlockItemIds` constant against its current name, requires the actual `Blocks.java` two-argument registration expression, and structurally checks both overload bodies plus the constructor/default-state and empty state-definition source. Per-row line anchors are included in `qualified-default-state-subset.jsonl`; concise registration anchors:

| 1.8.9 ID | 26.2 name | `BlockItemIds` | `Blocks.java` registration line |
|---:|---|---|---:|
| 4 | `minecraft:cobblestone` | `COBBLESTONE` | 94 |
| 7 | `minecraft:bedrock` | `BEDROCK` | 287 |
| 22 | `minecraft:lapis_block` | `LAPIS_BLOCK` | 671 |
| 41 | `minecraft:gold_block` | `GOLD_BLOCK` | 1050 |
| 42 | `minecraft:iron_block` | `IRON_BLOCK` | 1059 |
| 45 | `minecraft:bricks` | `BRICKS` | 1068 |
| 48 | `minecraft:mossy_cobblestone` | `MOSSY_COBBLESTONE` | 1211 |
| 57 | `minecraft:diamond_block` | `DIAMOND_BLOCK` | 1292 |

The verified common overload chain is `Blocks.java:5991 → 5995`; the `Block` constructor builds its state definition and default at `Block.java:238–243`, and the base `createBlockStateDefinition` method is empty at `Block.java:531–532`.

Machine-readable rows are in `qualified-default-state-subset.jsonl`; its generator checks the 16-row old decode, base-class registration, alias name and empty properties, empty output properties, current registry membership, and codec default. This is only a saved block-state projection. It does not establish old/current collision-shape equality, hit behavior, or movement-provider eligibility.

## Numeric table and rename route

`ChunkPalettedStorageFix.Section.upgrade` computes `(addBlocks << 12) | ((blocks[idx] & 255) << 4) | data[...]` and passes that integer directly to `BlockStateData.getTag` (26.2 `ChunkPalettedStorageFix.java:735–750`). `BlockStateData.register` stores entries in `MAP`, records the first state for each `id >> 4` in `BLOCK_DEFAULTS`, and `finalizeMaps` fills missing metadata slots from that per-block default (`BlockStateData.java:686–705`). `getTag` is a direct indexed lookup (`:737`). This is why the extraction records the exact row and aliases but does not infer old-source meaning from the numeric index alone.

The ordered rename extractor found 21 `BlockRenameFix.create` registrations, 105 source-backed rename pairs, and no unresolved call expressions. The implementation rewrites names in `BLOCK_STATE` and `FLAT_BLOCK_STATE` (`BlockRenameFix.java:31–56`). Examples that materially change the final registry name include `stone_slab → smooth_stone_slab`, `mob_spawner → spawner`, `melon_block → melon`, bark/log renames, and `grass → short_grass`. The exact per-row path is `renameTrace` in the JSONL. `%%FILTER_ME%%` is handled separately: `ChunkPalettedStorageFix.Section.setBlock` converts it to AIR (`ChunkPalettedStorageFix.java:704`); this is not treated as a live skull state.

`BlockState.CODEC` resolves the registry block and uses `Block::defaultBlockState`; `StateHolder.codec` supplies the block default if `Properties` is absent and validates supplied values with current property codecs. The bounded subset proves exact empty current property tuples using the base-`Block` overload chain. The full candidate rows list target properties and current registry membership, but exact current default property values outside the subset are intentionally not promoted.

## DataFixer coverage audit

I scanned the ordered `DataFixers.java` registration interval and every `References.BLOCK_STATE` occurrence in the 26.2 `fixes` directory. State-affecting registrations and dispositions relevant to these serialized inputs are:

- `ChunkPalettedStorageFix` at 1451.1: numeric palette conversion, with `FIX` and `VIRTUAL` neighbor/shape passes recorded per row.
- `BlockNameFlatteningFix` at 1451.4: operates on `BLOCK_NAME` choices, not the newly emitted `BLOCK_STATE` palette. It is kept distinct from `ChunkPalettedStorageFix.getTag`.
- 21 `BlockRenameFix` registrations: exact ordered name changes over block states and flat block states, extracted as above.
- `LeavesFix` at 1496: a chunk neighbor-derived leaves/log pass. Rows with its recognized name families retain it in `laterStateFixes`; the full section/neighborhood result is not reducible to a single palette row.
- `WallPropertyFix` at 2503 and `RedstoneWireConnectionsFix` at 2531: block-state property updates; affected names are separately marked in the row output.
- `CauldronRenameFix` at 2679: the extractor models its source branch exactly: cauldron level defaults to `0`; level `0` removes `Properties`, nonzero level renames to `water_cauldron` and retains `level`.
- `JigsawPropertiesFix`/`JigsawRotationFix` and later `BlockPropertyRenameAndFix` registrations: scoped to modern jigsaw, creaking-heart, and test-block state names; none is a 1.8.9 registered-ID target in this inventory.
- `BitStorageAlignFix` at 2527: changes packed section/heightmap bit storage layout, not the decoded block names or properties.
- `BlockStateStructureTemplateFix` at 1450 applies the old-state map to structure-template state payloads, a distinct input route from chunk palettes.
- `BlockEntityBlockStateFix`, `EntityBlockStateFix`, and `LevelFlatGeneratorInfoFix` also call `BlockStateData.getTag`, but apply to block-entity, entity, and flat-generator payloads, not the ordinary chunk-section input inventory.

The direct-reference scan also found the `AbstractBlockPropertyFix` base and `BlockStateData` API references; these were joined to their actual DataFixer registrations above rather than counted as additional independent chunk transforms. The inventory retains all possible listed transforms. It does not claim that the complete chunk repair behavior can be modeled row-wise for every ID; unresolved contextual outputs remain open.

## Static extraction checks

- 1,695 `BlockStateData.register` calls and 1,695 unique table indices parsed.
- 254 per-block defaults; 3,168 candidate `(oldId, metadata)` rows generated.
- 1,417 explicit table indices and 1,751 default-filled indices; no index-0 fallback for IDs 0–197.
- 21 ordered block rename fix registrations, 105 pairs, zero unresolved mappings.
- Current registry inventory resolves IDs only when they occur as the first argument of a `register`/stair/slab/wall registration call, or as the first argument of `ColorCollection.registerBlocks`. The extraction found 852 distinct direct IDs and 12 registered `BlockItemIds` color groups plus 2 registered `BlockIds` color groups; each group expands through the exact 16 `ColorCollection.VALUES` entries and `DyeColor` names. `BlockIds`, `BlockItemIds`, `BlockItemId`, `DyeColor`, `ColorCollection`, and `Blocks.java` are hash-bound. All 347 final names in the table, after the explicit `FILTER_ME → air` rule, are among these actual registered-key candidates.
- Bounded accepted subset: 8 IDs × 16 metadata values = 128 rows; each old decoder result is valid/default-only, each old-name alias has empty properties, each current final key is registered, and each current default tuple is empty.

## Inputs

Accepted old-source inputs: `.native-source/20261028/collision-next/dda-first-hit-crosswalk-20261029/range-000-063/crosswalk.tsv` and its accepted review; `.native-source/20261028/collision-next/dda-189-low-projection-000-031/metadata-current-projection.tsv` and its accepted review; and `.native-source/20261028/collision-next/dda-189-low-projection-032-063/metadata-projection.tsv`, `rules.tsv`, `provenance.tsv`, and its accepted review. IDs 4, 7, and 22 are covered by default-state rule L0 in the 000–031 partition; IDs 41, 42, 45, 48, and 57 are covered by default-state rule M0 in the 032–063 partition. The hash manifest binds both projection partitions, their reviews, and the full-range crosswalk evidence chain. No product source was read. No test, build, or game/runtime was run.
