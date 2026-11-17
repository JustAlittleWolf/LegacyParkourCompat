# Pumpkin neighbor-exception state mapping: 1.12.2 → 1.13.2

## Scope and exact source identities

This note closes only the pumpkin key-mapping gate in the accepted pane neighbor-connection report. It does not repeat pane predicates, collision geometry, or movement analysis. The pane report marks the differing old/target exception entries (`PUMPKIN`, `LIT_PUMPKIN` versus `PUMPKIN`, `CARVED_PUMPKIN`, `LIT_PUMPKIN`) as requiring state mapping evidence.

The canonical Ornithe Feather Gen2 build 2 source-manifest hashes are 1.12.2 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` and 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. Each file hash below was recomputed from the read-only canonical file and checked against its version's source manifest.

| Version | Source file | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/block/Block.java` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| 1.12.2 | `net/minecraft/block/PumpkinBlock.java` | `732365c7ba81c3301cf164ab749bf52b5ce540a7b3a84ad50798db465ab77c9d` |
| 1.12.2 | `net/minecraft/block/HorizontalFacingBlock.java` | `99c33e79b000c5545640894d0a291fe62dd07ed7330a5b08ca23c1db3d83dd83` |
| 1.13.2 | `net/minecraft/unmapped/C_80754635.java` | `2aff1baacd621e5719ee221181cac4593e382a6fd1c3f0215f1a7bddbbb7330e` |
| 1.13.2 | `net/minecraft/unmapped/C_15874933.java` | `8d62c370f468ca9534a4db65338dacc69a0f416079d4c499cc8d0e9250c3ae71` |
| 1.13.2 | `net/minecraft/unmapped/C_29689164.java` | `5f327c8aaf217f59975635e382860f92fbc7a40202d8e61200eebc8779c6bf1c` |
| 1.13.2 | `net/minecraft/unmapped/C_36170522.java` | `0f53d470214cfeaf6755215ff9c1635155030217c89b542c955b53c64ea4115a` |
| 1.13.2 | `net/minecraft/util/datafix/DataFixes.java` | `eb0e351b692e79b3dda9c04afe75fb242ba7d7ff9c789a04ee8b18ac13f6b1e8` |
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` |
| 1.13.2 | `net/minecraft/block/CarvedPumpkinBlock.java` | `227d2975c736c3dbc9f83331a897c383d8ffef5b6c035d04b2d6b661a1007291` |
| 1.13.2 | `net/minecraft/block/HorizontalFacingBlock.java` | `7ce4cee45329c93352c8a1740d691b046c8cd573dbb9e4b6549924f07f2fe1ad` |

## Legacy states and target registrations

In 1.12.2, `Block.java` registers numeric ID 86 as `minecraft:pumpkin` and ID 91 as `minecraft:lit_pumpkin`, each using `PumpkinBlock`. `PumpkinBlock` inherits `HorizontalFacingBlock.FACING`, defaults to NORTH, and its valid facing domain is NORTH, SOUTH, EAST, WEST.

In 1.13.2, `Block.java` registers `carved_pumpkin` and `jack_o_lantern`, both as `CarvedPumpkinBlock`; its sole state property is the inherited horizontal `FACING`. `Blocks.java` aliases `CARVED_PUMPKIN` to `carved_pumpkin` and `LIT_PUMPKIN` to `jack_o_lantern`. The separate target key `pumpkin` is registered as the growable pumpkin plant; it is not the flattened destination of the legacy block ID 86.

## Registered state-fixer result

`C_80754635.m_11856336` records a target `Dynamic` for each flattened palette index and indexes each exact legacy `{Name, Properties}` dynamic by that destination. Its `m_11299400` method returns the mapped destination for a matching block-state remainder and otherwise leaves the input unchanged. The relevant valid state entries are:

| Legacy key and facing | Flattened index | Target key and facing |
|---|---:|---|
| `pumpkin`, SOUTH | 1376 | `carved_pumpkin`, SOUTH |
| `pumpkin`, WEST | 1377 | `carved_pumpkin`, WEST |
| `pumpkin`, NORTH | 1378 | `carved_pumpkin`, NORTH |
| `pumpkin`, EAST | 1379 | `carved_pumpkin`, EAST |
| `lit_pumpkin`, SOUTH | 1456 | `jack_o_lantern`, SOUTH |
| `lit_pumpkin`, WEST | 1457 | `jack_o_lantern`, WEST |
| `lit_pumpkin`, NORTH | 1458 | `jack_o_lantern`, NORTH |
| `lit_pumpkin`, EAST | 1459 | `jack_o_lantern`, EAST |

Thus the target `CARVED_PUMPKIN` exception corresponds to the old `PUMPKIN` states. The old `LIT_PUMPKIN` states map to target `LIT_PUMPKIN` (`jack_o_lantern`), not to carved pumpkin. The target pane exception list's additional `PUMPKIN` entry is a distinct target-only registered block key in this source mapping; this note does not assign it a legacy predecessor.

## Fixer registration and applicable input paths

`C_15874933.makeRule` applies `C_80754635.m_11299400` to the remainder of `TypeReferences.BLOCK_STATE`. `DataFixes` registers this rule at schema version 1450. In the structured entity-state path, `C_29689164`'s name-to-legacy-ID table identifies `minecraft:pumpkin` as ID 86 and `minecraft:lit_pumpkin` as ID 91; its entity fixer composes the legacy ID and metadata and calls `C_80754635.m_46884624` to produce the same flattened state entries. `DataFixes` registers that fixer at schema 1451:3.

For ordinary legacy Anvil chunks, the accepted `leaves_state_migration/coverage.md` (SHA-256 `ec5c38a6c3fe86a58a02047d51e7f5d832eed6fbc90ead028f4b744fe9b53a81`) and independent verdict (SHA-256 `4328171e022fe686c343c85dd8cc3148c65f43d8a19e074e17f6043d9117e693`) trace the registered schema 1451:1 chunk-flattening fixer `C_36170522` through the Anvil load path; that fixer uses the same `C_80754635` legacy ID/metadata table for palette entries. This note reuses only that path/registration result and supplies the pumpkin-specific valid states and outputs above.

## Bounded conclusion and limits

The registered vanilla state mapping preserves the old pane-exception membership for both valid pumpkin families on the traced fixer paths: old `PUMPKIN` becomes target `CARVED_PUMPKIN`, while old `LIT_PUMPKIN` becomes target `LIT_PUMPKIN`/`jack_o_lantern`. Therefore the target `CARVED_PUMPKIN` exception is not an unmapped substitute for old `LIT_PUMPKIN`; it corresponds to old `PUMPKIN`.

This establishes source-defined registered Java fixer mappings and the cited Anvil chunk path. It does not prove every external/custom world importer runs these fixers, does not disposition malformed or non-vanilla states, and does not compare pumpkin neighbor `FaceShape` values, pane collision, player movement, or pair completion. The pane provider's remaining upstream neighbor-predicate and stored-state gates stay separate.
