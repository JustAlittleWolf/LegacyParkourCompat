# Source residual: legacy block ID 17 log alias/state join

## Scope and identity

Bounded source-only join for old `minecraft:log` (numeric block ID 17), metadata/species states only. `log2` (ID 162) and planks are excluded. This note does not revisit collision geometry; the accepted provider report is `build/source-residual-20261116/1.12.2--1.13.2/planks_logs_collision_keys/report.md` SHA-256 `61511ba8ac01a9e3afad8a7e61fea8195825b3065f0f8234a91de24c1eb0d2c6`, with ACCEPT verdict at `.../planks_logs_collision_keys_audit/verdict.md` SHA-256 `2448ef91f2ba28c995d432caceea8641ce7930fbaae684a2eb7dbdadb9896fdc`.

Exact source identities supplied for this pair: 1.12.2 Feather Gen2 b2 manifest `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 1.13.2 Feather Gen2 b2 manifest `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.

## Old state encoding

1.12.2 `net/minecraft/block/LogBlock.java` (source SHA-256 `858f2d75883cb1d1b13ffd2b0f88d2d4b6ac9e244a69c8065b2ee9f2041344ea`): `getStateFromMetadata` uses species `metadata & 3` and axis selector `metadata & 12`: `0→Y`, `4→X`, `8→Z`, and default→`NONE` (method lines 49–67). `getMetadataFromState` writes the matching 0/4/8/12 axis bits (lines 70–85). The `VARIANT` property accepts variant IDs below 4 (lines 18–26). Thus ID 17 metadata 0–15 represents four species each with Y/X/Z/NONE; metadata 12–15 are not discarded or invalid in the source block.

## 1.13.2 canonical legacy-state table

1.13.2 `net/minecraft/unmapped/C_80754635.java` (SHA-256 `2aff1baacd621e5719ee221181cac4593e382a6fd1c3f0215f1a7bddbbb7330e`) constructs `f_50521423` as indexed canonical states and registers accepted legacy `Dynamic` keys in `f_34904805` in `m_11856336` (lines 14–28). Its table entries at indices 272–287 are exactly ID-17 index `17*16 + metadata`:

| Old metadata | Old variant / axis | Index | Canonical 1.13.2 state |
|---:|---|---:|---|
| 0 | oak / Y | 272 | `minecraft:oak_log[axis=y]` |
| 1 | spruce / Y | 273 | `minecraft:spruce_log[axis=y]` |
| 2 | birch / Y | 274 | `minecraft:birch_log[axis=y]` |
| 3 | jungle / Y | 275 | `minecraft:jungle_log[axis=y]` |
| 4–7 | oak, spruce, birch, jungle / X | 276–279 | corresponding `*_log[axis=x]` |
| 8–11 | oak, spruce, birch, jungle / Z | 280–283 | corresponding `*_log[axis=z]` |
| 12 | oak / NONE | 284 | `minecraft:oak_bark` |
| 13 | spruce / NONE | 285 | `minecraft:spruce_bark` |
| 14 | birch / NONE | 286 | `minecraft:birch_bark` |
| 15 | jungle / NONE | 287 | `minecraft:jungle_bark` |

Table source anchors: `m_46884624` indexes the canonical array at lines 71–78; table rows are lines 204–219. Each row registers both the target canonical state and its old property-form key (e.g. `Name:minecraft:log, Properties:{axis:none,variant:oak}`) at the same integer index. Therefore old axis `NONE` is explicitly mapped, not omitted: the normalized target is the matching `*_bark` block, with no `axis` property. It does not map to `*_wood`.

## Reachability and route boundary

1.13.2 `C_29689164.java` (SHA-256 `5f327c8aaf217f59975635e382860f92fbc7a40202d8e61200eebc8779c6bf1c`) includes the legacy block-name-to-ID table with `minecraft:log→17` (`m_91167426`, lines 20–24 and 311–314). Its entity block-state fix converts a numeric block ID and low four metadata bits into `m_46884624((id<<4)|data)`; `m_35696925` handles falling-block `TileID`/`Tile` plus `Data` (lines 315–336), and `m_42094563` handles entity `*Tile`/`*Data` fields (lines 338–349). This establishes the numeric palette-index route for those entity fixes, including ID 17 metadata 12–15.

`C_15874933.java` (SHA-256 `8d62c370f468ca9534a4db65338dacc69a0f416079d4c499cc8d0e9250c3ae71`) applies `C_80754635.m_11299400` to the `BLOCK_STATE` remainder (lines 14–20); `DataFixes.java` registers it as the `BlockStateStructureTemplateFix` at schema 1450 (SHA-256 `eb0e351b692e79b3dda9c04afe75fb242ba7d7ff9c789a04ee8b18ac13f6b1e8`, lines 228–229). This is evidence for that registered state-key conversion path, not a claim that every third-party importer uses it.

Separately, `C_37931705.java` (SHA-256 `2717df7465597f98634ff1569968ff247a4ccda472209e2628ae4ab21c7364de`) has name-flattening entries `minecraft:log.0`–`.3`→the four `*_log` names (lines 40–43), but those rows carry species only and do not represent the axis-state mapping above. They do not negate the complete `C_80754635` ID-plus-metadata table.

## Disposition

**Closed bounded source fact:** for old block ID 17, metadata 0–15 all have explicit 1.13.2 canonical mappings; axis NONE (12–15) becomes the species-matched `*_bark` key. Provider geometry remains the already accepted bounded claim cited above. This closes only this alias/state join and its identified DataFixer/palette routes; it does not establish whole-pair state migration, arbitrary external importer behavior, or pair completion.
