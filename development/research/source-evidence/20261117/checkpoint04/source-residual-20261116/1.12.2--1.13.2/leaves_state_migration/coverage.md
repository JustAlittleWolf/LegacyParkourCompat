# Leaves world-chunk state migration dependency: 1.12.2 → 1.13.2

## Bounded result

The 1.13.2 Java data-fixer chain contains an exact chunk migration from the two legacy leaves block IDs and their valid metadata states to six named leaf keys, followed by a leaf-state fixer that converts `decayable` into `PERSISTENT` and computes `DISTANCE`. For chunks passed through the inspected Anvil storage path with a data version below 1493, the path runs the 1451 legacy chunk flattening fixer and subsequently runs the registered 1496 `Leaves fix` before saving as data version 1631. This establishes source-side migration logic and the old-to-new leaf-state mapping on that chunk path.

It does not establish that every external world-import or custom storage path invokes this data fixer, prove resource or tag membership, or establish that a particular player profile loads such a migrated world. The existing leaf collision provider result remains separate; this migration packet makes no movement or trajectory claim and does not freeze the pair.

## Exact source identity and hashes

Both canonical source identities are the assigned Ornithe Feather Gen2 build 2 manifests: 1.12.2 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. The source paths below are relative to each release's `ornithe-feather` root. Each listed digest was recomputed from the read-only canonical file and matched its manifest entry.

| Version | Source path | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/block/LeavesBlock.java` | `91d268eb3d890c6f24ec8f8e0281cffd0a42f1a4c5a9ed0ac4041221251a4fdc` |
| 1.12.2 | `net/minecraft/block/Leaves2Block.java` | `54be11551779ba540ef2374e831a7c49d37157e041b31602ccd0a0636b8d5bf4` |
| 1.12.2 | `net/minecraft/block/AbstractLeavesBlock.java` | `e57e9a22e6229305be3d6feca216c18f2298d465d476c3ed51bb43a4bcf13c3a` |
| 1.12.2 | `net/minecraft/block/Block.java` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| 1.13.2 | `net/minecraft/util/datafix/DataFixes.java` | `eb0e351b692e79b3dda9c04afe75fb242ba7d7ff9c789a04ee8b18ac13f6b1e8` |
| 1.13.2 | `net/minecraft/unmapped/C_36170522.java` | `0f53d470214cfeaf6755215ff9c1635155030217c89b542c955b53c64ea4115a` |
| 1.13.2 | `net/minecraft/unmapped/C_80754635.java` | `2aff1baacd621e5719ee221181cac4593e382a6fd1c3f0215f1a7bddbbb7330e` |
| 1.13.2 | `net/minecraft/unmapped/C_58716260.java` | `7326eeb53f2c99cb41f83f12fa57003296d121a7c1509cc6f38db4d00f807a85` |
| 1.13.2 | `net/minecraft/world/chunk/storage/AnvilChunkStorage.java` | `9037625793eb4b046d4c80ebf352d73a0b750f969dfaa1fc2a53b35467dc5031` |
| 1.13.2 | `net/minecraft/nbt/NbtUtils.java` | `a228be0c95de96bdb29a19b3f2ee9cd925233bae395fc0a3a0c71e8f123f9110` |
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/LeavesBlock.java` | `8f209a4fcf08dbb4570df915795931f3f876abad8659ded4b17df6e535432006` |

The manifest identities are available in `build/source-identities-20261116/locations.tsv`; no source generation or shared-source write occurred.

## Legacy variant and metadata domain

In 1.12.2, `LeavesBlock.getStateFromMetadata` reads variant bits `metadata & 3`, sets `DECAYABLE` from bit 2 (`(metadata & 4) == 0`), and sets `CHECK_DECAY` from bit 3 (`(metadata & 8) > 0`). Its registered variants are oak, spruce, birch, and jungle. `Leaves2Block` uses the same decay flags and variant bits, with registered variants acacia and dark oak. Thus the legal metadata state sets are 16 states for legacy block ID 18 and 8 states for legacy block ID 161; the latter accepts variant values 0 and 1. The metadata decoding and registrations are source facts, not a claim about arbitrary malformed IDs or metadata.

## Registered chunk-fixer chain

1. `DataFixes` registers `new C_36170522(schema39, true)` at schema 1451:1 (`DataFixes.java:231–232`). The `C_36170522` chunk fixer consumes legacy section `Blocks`, `Data`, and optional `Add` arrays and emits `Palette` plus packed `BlockStates` (`C_36170522.java`, section conversion and `C_41900556.m_99127153` / `m_47043446`). It composes block ID and metadata into the legacy state index and calls `C_80754635.m_46884624` for the palette entry.
2. `C_80754635` has explicit legacy palette mappings for every valid leaves state. Indices 288–303 are ID 18 × 16 plus metadata 0–15; output keys are oak, spruce, birch, or jungle leaves, retaining string properties `check_decay` and `decayable`. Indices 2576, 2577, 2580, 2581, 2584, 2585, 2588, and 2589 cover ID 161 × 16 plus valid metadata 0, 1, 4, 5, 8, 9, 12, and 13; output keys are acacia or dark oak leaves with the same two string properties. Unassigned leaf2 metadata variants are not described as legal states by the old registered state manager; this report does not infer their migration behavior from the valid-domain mappings.
3. `DataFixes` registers `new C_58716260(schema64, false)` at schema version 1496 (`DataFixes.java:394–395`), named “Leaves fix” in the rule (`C_58716260.java:83–88`). This fixer scans each chunk section palette. For the six named leaves keys, it sets `persistent` to `true` exactly when the source `decayable` property string is `false`, then replaces the prior properties with `persistent` and `distance='7'` (`C_58716260.java:298–311, 270–280`). It subsequently propagates distances 1 through 6 from recognized log palette states over the six axial neighbor directions; leaves not reached remain distance 7. At section/chunk borders, the fixer accumulates and writes the relevant `UpgradeData.Sides` bits when log-adjacent state may require later edge processing (`:105–166`). `check_decay` does not participate in this conversion.

The six registered target keys are `oak_leaves`, `spruce_leaves`, `birch_leaves`, `jungle_leaves`, `acacia_leaves`, and `dark_oak_leaves` (`Block.java:884–889`). Target `LeavesBlock` declares `DISTANCE` and `PERSISTENT` and defines default `distance=7,persistent=false` (`LeavesBlock.java:25–33`).

## Chunk-load application gate

`AnvilChunkStorage.loadChunk` obtains NBT through `getChunkNbt`, which calls `upgradeChunkNbt` (`AnvilChunkStorage.java:77–80,122–130`). That reader compares the chunk's `DataVersion`; for `i < 1493` it calls `NbtUtils.fix(dfu, DataFixTypes.CHUNK, nbt, i, 1493)`, then calls `NbtUtils.fix(dfu, DataFixTypes.CHUNK, nbt, max(1493,i))` whose default target is 1631 (`AnvilChunkStorage.java:83–107`; `NbtUtils.java:247–252`). Therefore legacy chunks below 1493 are advanced past schema 1451:1 and, on the second update from at least 1493 to 1631, past the schema-1496 Leaves fix. The explicit source path covers this Anvil chunk storage reader. Applicability through other callers, external importers, or formats not shown in this path remains open.

## Remaining gates and disposition

- Closed dependency: source-defined old leaves variants and valid metadata map through the registered chunk fixer to the six named keys; the registered Leaves fix produces target `PERSISTENT` from `decayable` and computes target `DISTANCE`.
- Open: whether a particular external world/import workflow calls this Anvil storage path; malformed or non-vanilla leaf metadata; resource/tag membership; any product-specific world-loading path.
- Reused unchanged provider packet: `leaves_collision_provider/report.md` SHA `2dd4ba32a887fcf3a4cca6952a821d91f0f7048eba47585270695c8001d0d21e`, independently accepted by `leaves_collision_provider_audit/verdict.md` SHA `809b8c2208812369d61ff0ef8729c6e7243c7972590b81f21377d6087e616fed`. That packet only establishes its registered leaf collision provider/state domains. This migration note adds no shape, movement, or pair-freeze claim.
