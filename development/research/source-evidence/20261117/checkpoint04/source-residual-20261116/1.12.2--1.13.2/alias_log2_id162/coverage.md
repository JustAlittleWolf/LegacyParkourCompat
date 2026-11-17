# Legacy block ID 162 (`log2`) alias/state migration

**Scope:** exact 1.12.2 → 1.13.2 source path for `minecraft:log2` (numeric block ID 162), limited to its legal acacia and dark-oak variants and axis metadata. ID 17 (`log`) and ID 5 (`planks`) are outside this note. This records serialized-state migration, not a gameplay movement finding, full pair coverage, or an intermediate patch cutover.

## Source identity

Both endpoints are the exact local Ornithe Feather Gen2 build.2 sources. Their ready markers bind the source manifests: 1.12.2 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. Relevant Java file hashes below were checked against the respective `ornithe-feather.sources.sha256` manifest entries.

## Old ID and metadata domain

1.12.2 registers numeric ID 162 as `log2` backed by `Log2Block` (`net/minecraft/block/Block.java:1130`). `Log2Block.VARIANT` accepts plank variant IDs ≥4, namely acacia (4) and dark oak (5); its metadata reader selects `Variant.byId((metadata & 3) + 4)` and selects the log axis from `metadata & 12` (`net/minecraft/block/Log2Block.java:16-24,56-72`). `PlanksBlock.Variant` declares the relevant IDs/names (`net/minecraft/block/PlanksBlock.java:58-63`). Thus the scoped legal metadata pairs are:

| Metadata | Legacy species | Legacy axis |
|---:|---|---|
| 0 | acacia | Y |
| 1 | dark oak | Y |
| 4 | acacia | X |
| 5 | dark oak | X |
| 8 | acacia | Z |
| 9 | dark oak | Z |
| 12 | acacia | NONE |
| 13 | dark oak | NONE |

The low bits supply species; `metadata & 12` selects Y, X, Z, or (default) NONE. This table is restricted to the two legal `log2` variants; it makes no claim about invalid variant bits.

## Exact DataFixer route and destinations

In 1.13.2 `DataFixes.addFixes`, schema 1450 registers `C_15874933` (`DataFixes.java:228`). `C_15874933.makeRule` applies `C_80754635.m_11299400` to the remainder of each `TypeReferences.BLOCK_STATE` (`C_15874933.java:15-20`). The table initializer in `C_80754635` contains explicit pairs at lines 7043–7050. Its helper stores the first state as the table's target for that indexed state and indexes each subsequent state as a lookup key (`C_80754635.java:21-28,33-40`). The exact legacy-to-flattened entries are:

| Legacy `log2` state | Schema-1450 state |
|---|---|
| `variant=acacia, axis=y` | `minecraft:acacia_log`, `axis=y` |
| `variant=dark_oak, axis=y` | `minecraft:dark_oak_log`, `axis=y` |
| `variant=acacia, axis=x` | `minecraft:acacia_log`, `axis=x` |
| `variant=dark_oak, axis=x` | `minecraft:dark_oak_log`, `axis=x` |
| `variant=acacia, axis=z` | `minecraft:acacia_log`, `axis=z` |
| `variant=dark_oak, axis=z` | `minecraft:dark_oak_log`, `axis=z` |
| `variant=acacia, axis=none` | `minecraft:acacia_bark` (no properties) |
| `variant=dark_oak, axis=none` | `minecraft:dark_oak_bark` (no properties) |

The schema-1450 mapping therefore preserves X/Y/Z as the corresponding target `axis` values on the species-specific `_log` key. `AxisBlock` declares `Properties.AXIS`, defaults it to `Direction.Axis.Y`, and adds that property to the state definition (`net/minecraft/block/AxisBlock.java:9-14,35-38`). The `NONE` states do not map to a `_log` state or an `axis=none` value; the exact table maps them to the intermediate bark keys.

For the legacy chunk palette path, schema 1451.1 registers `C_36170522` as `ChunkPalettedStorageFix` on `TypeReferences.CHUNK` (`DataFixes.java:229-232`; `C_36170522.java:416-418`). Its palette conversion combines the old `Add`, `Blocks`, and `Data` arrays into `n = Add << 12 | blockByte << 4 | metadata` and writes `C_80754635.m_46884624(n)` into the flattened palette (`C_36170522.java:616,630`). For block ID 162, `n = 162 << 4 | metadata = 2592 + metadata`; the eight scoped metadata values therefore index exactly rows 2592, 2593, 2596, 2597, 2600, 2601, 2604, and 2605 above. This directly binds the mapping table to chunk palette conversion, rather than relying only on the separate schema-1450 structure-template `BLOCK_STATE` fixer.
The full registered fixer pipeline then contains a later schema-1510 `Block renamening fix` which rewrites block names and `BLOCK_STATE.Name` values via `C_81767953` (`DataFixes.java:405-408`; `C_81767953.java:23-34`). The supplied map `C_00119465.f_76383611` explicitly maps `minecraft:acacia_bark` → `minecraft:acacia_wood` and `minecraft:dark_oak_bark` → `minecraft:dark_oak_wood` (`C_00119465.java:23-36`). Accordingly, when this later fixer is in the applied schema path, metadata 12/13's bark intermediate is subsequently renamed to the corresponding `_wood` key. This is a proven chained rename, not an inferred fallback. The target registration source lists acacia/dark-oak `_log` and `_wood` blocks (`net/minecraft/block/Block.java:844-847,876-883`).

## Bounded disposition

The source-only state join is closed for the eight legal metadata cases above, including the explicit `NONE` → bark → later wood rename chain. It establishes no direct migration from `log2` `axis=none` to an `_log` state. The independently accepted `planks_logs_collision_keys` report remains the basis for the bounded provider fact that the scoped registered log providers resolve to full cubes; this note adds no geometry comparison and does not widen that provider verdict.

## Exact source file hashes

| Release | Relative source path | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/block/Block.java` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| 1.12.2 | `net/minecraft/block/Log2Block.java` | `c0a6a578ac3c7261790cd74c342b38aec5a58be8e4ebd4fe22b30660a5990cca` |
| 1.12.2 | `net/minecraft/block/PlanksBlock.java` | `f190065f853a6aa9ad64e760607f057752bdeb4fe3ac724116174a23eddae29e` |
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/AxisBlock.java` | `6d80b24a181d5891794b16576d4641fd25b2b8c5eb879362ffdca671975919a4` |
| 1.13.2 | `net/minecraft/util/datafix/DataFixes.java` | `eb0e351b692e79b3dda9c04afe75fb242ba7d7ff9c789a04ee8b18ac13f6b1e8` |
| 1.13.2 | `net/minecraft/unmapped/C_15874933.java` | `8d62c370f468ca9534a4db65338dacc69a0f416079d4c499cc8d0e9250c3ae71` |`r`n| 1.13.2 | `net/minecraft/unmapped/C_36170522.java` | `0f53d470214cfeaf6755215ff9c1635155030217c89b542c955b53c64ea4115a` |
| 1.13.2 | `net/minecraft/unmapped/C_80754635.java` | `2aff1baacd621e5719ee221181cac4593e382a6fd1c3f0215f1a7bddbbb7330e` |
| 1.13.2 | `net/minecraft/unmapped/C_81767953.java` | `dc453a507d63d7bd371dc2c6315b7a54d356bf6e1f7ecb40af7debaaa90dd474` |
| 1.13.2 | `net/minecraft/unmapped/C_00119465.java` | `8e995b8101bbd4a2ad0f51ac8de432361cb533d3d3d3e65ac2f16da9a7b96cfd` |

The reused collision/provider report is `build/source-residual-20261116/1.12.2--1.13.2/planks_logs_collision_keys/report.md` SHA-256 `61511ba8ac01a9e3afad8a7e61fea8195825b3065f0f8234a91de24c1eb0d2c6`, with independent ACCEPT verdict SHA-256 `2448ef91f2ba28c995d432caceea8641ce7930fbaae684a2eb7dbdadb9896fdc`.


