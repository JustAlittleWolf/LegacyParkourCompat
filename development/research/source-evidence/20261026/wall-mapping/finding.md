# Independent audit of legacy wall/fence identity evidence

The frozen flattening JSON is correct at its recorded revision. A fresh balanced-call parser independently reproduces all 787 rows, including numeric index, metadata, complete target expression, target name/null and source line, with zero differences. The input SHA-256 is `af7c95fdab33b37fd8eefa8814fb26aa6030880979be241982a4187a929a69fa`. All 236 old numeric/name registrations were independently re-read from 1.11.2 `Block.java` (including `AIR_KEY`), and the selected 100-ID membership matches the frozen inventory exactly. The selected rows also retain the inventory's old material, isCube and effective wall result exactly.

**Count correction:** 787 is the total explicit table-row count, not the number of rows with an explicit target identity. There are **775 named-target rows for 99 IDs**, plus **12 `%%FILTER_ME%%` rows for skull ID 144**. The earlier prose calling all 787 rows explicit identities overstates that coverage; the frozen JSON itself faithfully records null target IDs for skull.

## Exact sources

1.11.2 uses Ornithe Feather `feather-gen2-1.11.2+build.2`; its source-manifest SHA-256 remains `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`. 26.2 uses published unobfuscated names; its source-manifest SHA-256 remains `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`. Every relevant source file listed in `current-identities.json` was individually hashed and matched against its manifest entry. The shared source publication was consumed read-only.

26.2 `net/minecraft/util/datafix/fixes/BlockStateData.java`, `register` (686–699), assigns the first target to `MAP[id]` and the first target per old block ID as default. `finalizeMaps` (701–707) fills unspecified metadata from that default. The 787 rows audit explicit calls, not 1,600 independent valid legacy states or a complete state conversion. Source SHA-256 remains `6196f9039890d5f67b59cb372ec06da987e8a5ea80b49fd8bee9eeca0a1784a4`.

## Later name closure

All **231 distinct named flattening outputs** now have explicit paths to 26.2 registry identities in `current-identities.json`. All 21 registered `BlockRenameFix.create` calls in 26.2 `DataFixers.java` were inspected, including their referenced maps. Fifteen of the 231 input names are affected:

| Flattening input | Later name | Data version / exact source |
| --- | --- | --- |
| `purple_shulker_box` | `shulker_box` | 1474; `DataFixers.java` 570–574 |
| `melon_block` | `melon` | 1490; `DataFixers.java` 692 |
| six wood-species `*_bark` | corresponding `*_wood` | 1510; `EntityTheRenameningFix.java` 24–31 and `DataFixers.java` 726 |
| `portal` | `nether_portal` | same 1510 block map |
| `mob_spawner` | `spawner` | same 1510 block map |
| `stone_slab` | `smooth_stone_slab` | 1802; `DataFixers.java` 752–765 |
| `sign`, `wall_sign` | `oak_sign`, `oak_wall_sign` | same 1802 map |
| `grass_path` | `dirt_path` | 2680; `DataFixers.java` 986 |
| `grass` | `short_grass` | 3692; `DataFixers.java` 1372–1373 |

`BlockRenameFix.java` 35–53 explicitly applies its rename to block names and `BLOCK_STATE.Name`, and 57–60 writes the renamed Name. This supplies authoritative identity evidence; no spelling-based rename guess was used. `stone_slab` and `purple_shulker_box` demonstrate why current registry membership cannot short-circuit this historical conversion chain: those old names still exist today with other historical meanings.

Current identity evidence follows `BlockItemIds.java` and `BlockIds.java` constants through references in `Blocks.java` and the registration helpers at `Blocks.java` 5980–5996, which reach `Registry.register(BuiltInRegistries.BLOCK, id, block)`. Two-name `BlockItemId.create(blockName,itemName)` uses its first argument for the block (`BlockItemId.java` 11–17). Generated color families use `createSimpleColored` (`BlockItemIds.java` 784–785; `BlockIds.java` 126–127), `ColorCollection.prefixWithColor` (70–71), the sixteen explicit `DyeColor` names, and `ColorCollection.registerBlocks` (44–55). Data records contain the exact defining field, color where applicable, and `Blocks.java` reference lines for every resolved name.

**Extraction limitation corrected:** The frozen inventory's 136 direct same-name / 100 unmatched split describes that extractor's direct-field recognition. It is not an exhaustive count of actual equal-name current registrations: generated color families, including old `white_shulker_box`, also create same-string current identities. This does not change the selected 100-ID audit membership or any old classifier fact.

## Skull identity closure with explicit gate

26.2 `ChunkPalettedStorageFix.java` 220–227 calls `mapSkull` for types 0–5: skeleton, wither skeleton, zombie, player, creeper, dragon. `mapSkull` (277–287) creates four horizontal wall states and sixteen floor rotations for each type. Its `case 144` conversion (951–977) reads `SkullType` with default 0, reads facing from the provisional state, selects horizontal facing or `Rot` with default 0 for up/down, and sets the corresponding mapped state. An unrecognized key falls back to type-0 north-facing wall skull. All twelve generated floor/wall identities have current registry evidence in the attached JSON. No piglin or other modern-only skull family is inferred.

This closes old skull identity correspondence for the source conversion's **non-null block-entity gate**. A missing block entity is not rewritten by that case and the table placeholder alone remains insufficient; malformed/missing-entity skull data stays an explicit gap. Old `SkullBlock.isCube` is constant false (1.11.2 `SkullBlock.java` 71–73), so all evidenced old skull variants fail the generic wall/fence solidity conjunction independently of facing or rotation.

## Predicate scope and remaining gates

The identity data does not replace the historical classifier. 1.11.2 `WallBlock.shouldConnectTo` 148–158 rejects barrier, accepts the same wall instance or a fence gate, then evaluates `material.isSolidBlocking() && state.isCube()` and excludes pumpkin material. `FenceBlock.shouldConnectTo` 124–134 shares that generic conjunction but has a **same-material FenceBlock** special branch instead of wall-self. The inventory's effective wall result must not be copied as a fence truth table: wall neighbors and same-material fences have different special handling. No modern sturdy-face or render-solidity substitute was used.

This bounded audit closes names of all 231 explicit table outputs and the twelve valid skull families. It does **not** claim exhaustive current-state correspondence, a full current-identity partition for all old blocks, or equivalence of block properties after conversion. State/neighbor/block-entity-dependent conversions must be retained separately: e.g. `ChunkPalettedStorageFix` rewrites banner colors, pots and upper double plants; `CauldronRenameFix` splits nonzero-level `cauldron` into `water_cauldron` (26.2 source 17–23). The selected 100-ID table is not a replacement for reviewing those conversions or the other 136 IDs' metadata variants. In particular, single/double slab old classifier outcomes must stay tied to their state expressions rather than collapsing to modern slab identity alone.

No source generation, product edits, build, test, game launch, or runtime-parity conclusion is part of this audit. `audit.py` is a reproducible static evidence extraction/check, not a movement test.
