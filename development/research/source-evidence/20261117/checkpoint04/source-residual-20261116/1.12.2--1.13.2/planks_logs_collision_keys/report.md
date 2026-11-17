# Planks and log registry/state/provider closure

**Scope:** only the assigned residual aliases `minecraft:planks` (1.12.2 numeric block ID 5) and `minecraft:log` (ID 17). `minecraft:log2` / ID 162 and all other aliases were not reviewed. This is a bounded source crosswalk, not pair coverage or a version-cutover claim.

## Evidence identity

Both sides use the exact local Ornithe Feather Gen2 snapshots. The ready markers bind their source-manifest hashes; hashing the manifest bytes in this checkout reproduced those digests:

| Release | Mapping artifact identity | Source manifest SHA-256 | Ready marker SHA-256 |
|---|---|---|---|
| 1.12.2 | `feather-gen2-1.12.2+build.2-mergedv2.jar` and `feather-gen2-1.12.2+build.2.tiny` | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` | `1.12.2/ornithe-feather.ready.json` |
| 1.13.2 | `feather-gen2-1.13.2+build.2-mergedv2.jar` and `feather-gen2-1.13.2+build.2.tiny` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` | `1.13.2/ornithe-feather.ready.json` |

Ready-marker source paths are `decompiled_minecraft/<version>/ornithe-feather.sources.sha256`; each marker records the source manifest digest above. Consulted Java-source SHA-256 values below are file-byte hashes from this checkout.

## Old state domains and target registered keys

- **Planks, ID 5:** 1.12.2 registers ID 5 as `planks` with `PlanksBlock` ([`Block.java:759`](decompiled_minecraft/1.12.2/ornithe-feather/net/minecraft/block/Block.java#L759)). `PlanksBlock.Variant` has legal IDs 0–5 in the order oak, spruce, birch, jungle, acacia, dark_oak ([`PlanksBlock.java:56-62`](decompiled_minecraft/1.12.2/ornithe-feather/net/minecraft/block/PlanksBlock.java#L56)). 1.13.2 registers separate target block keys `oak_planks`, `spruce_planks`, `birch_planks`, `jungle_planks`, `acacia_planks`, `dark_oak_planks` ([`Block.java:792-803`](decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/block/Block.java#L792)). These target registrations are plain `Block` instances and have no species state property.
- **Log, ID 17:** 1.12.2 registers ID 17 as `log` with `LogBlock` ([`Block.java:781`](decompiled_minecraft/1.12.2/ornithe-feather/net/minecraft/block/Block.java#L781)). Its variant predicate admits only IDs `<4`, i.e. oak, spruce, birch, jungle; `getStateFromMetadata` reads `(metadata & 3) % 4` and axis bits `metadata & 12`, mapping 0/4/8/12 to Y/X/Z/NONE respectively ([`LogBlock.java:17-26,62-78`](decompiled_minecraft/1.12.2/ornithe-feather/net/minecraft/block/LogBlock.java#L17); [`AbstractLogBlock.java:15,77-87`](decompiled_minecraft/1.12.2/ornithe-feather/net/minecraft/block/AbstractLogBlock.java#L15)). Thus the old ID has four species × four representable axis values, including `NONE` for metadata 12–15.
- 1.13.2 registers `oak_log`, `spruce_log`, `birch_log`, and `jungle_log` as `AbstractLogBlock` ([`Block.java:837-842`](decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/block/Block.java#L837)). `AbstractLogBlock` extends `AxisBlock`; the latter declares `Properties.AXIS`, defaults to Y, and adds only that property to the state definition ([`AbstractLogBlock.java:9-16`](decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/block/AbstractLogBlock.java#L9); [`AxisBlock.java:8-14,35-38`](decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/block/AxisBlock.java#L8)). The target log states therefore expose the ordinary X/Y/Z axis values; the 1.12 `NONE` state has no corresponding target property value. Target `_wood` blocks also exist ([`Block.java:872-884`](decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/block/Block.java#L872)), but their registration alone does not establish that `NONE` migrates to one of them.

**Exact migration key/state mapping remains open.** The inspected 1.13.2 Minecraft sources establish registered target keys and target state domains, but do not expose a verifiable block-state flattener mapping old numeric ID/metadata (including the old enum distinction between serialized `dark_oak` and `getName()` value `big_oak`) to those target block/property pairs. `DataFixes.java` builds the DFU pipeline and delegates through several `C_…` schemas/fixers; its visible registrations do not supply that per-block map ([`DataFixes.java:133-151`](decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/util/datafix/DataFixes.java#L133)). In particular, the source inspected here cannot confirm the precise destination for the old log `axis=NONE` states. Do not treat the candidate target names above as a closed migration table.

## Effective shape and face providers

For 1.12.2, the generic `Block` provider returns `FULL_BLOCK_SHAPE` from `getShape`, reports `FaceShape.SOLID` from `getFaceShape`, and routes `getCollisionShape` through the state shape ([`Block.java:307`](decompiled_minecraft/1.12.2/ornithe-feather/net/minecraft/block/Block.java#L307); [`Block.java:362,387-388`](decompiled_minecraft/1.12.2/ornithe-feather/net/minecraft/block/Block.java#L362)). `PlanksBlock` has no override; `LogBlock`/`AbstractLogBlock` have no collision or face override.

For 1.13.2, `Block.getShape` returns `VoxelShapes.block()` and `getFaceShape` returns `FaceShape.SOLID`; collision returns the state shape when `hasCollision` is true, otherwise empty ([`Block.java:383-394`](decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/block/Block.java#L383)). `Block.Properties` defaults `hasCollision` to true ([`Block.java:1901`](decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/block/Block.java#L1901)); the six plank registrations use ordinary properties and all four scoped log registrations use ordinary wood properties without `.noCollision()` ([`Block.java:792-803,837-842`](decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/block/Block.java#L792)). Block-state collision and face queries delegate to these block providers ([`BlockState.java:209-210,285-286`](decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/block/state/BlockState.java#L209)).

**Provider result:** for ordinary player block-state collision/face queries on these scoped registered planks and log states, both releases resolve to a full unit cube and solid face shape. Species and axis do not alter these providers. No collision-geometry or collision-state-input difference is established for these aliases. The old `NONE` to target-state migration gap above remains relevant to deciding which target state an old serialized block becomes, but all registered target states in scope share the same provider behavior.

## Consulted source file hashes

| Release | Relative source path | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/block/Block.java` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| 1.12.2 | `net/minecraft/block/Blocks.java` | `8d34c5e531407689108668a84bb8fe705bf987b1481ae8b6d3a6e39424243e2e` |
| 1.12.2 | `net/minecraft/block/PlanksBlock.java` | `f190065f853a6aa9ad64e760607f057752bdeb4fe3ac724116174a23eddae29e` |
| 1.12.2 | `net/minecraft/block/LogBlock.java` | `858f2d75883cb1d1b13ffd2b0f88d2d4b6ac9e244a69c8065b2ee9f2041344ea` |
| 1.12.2 | `net/minecraft/block/AbstractLogBlock.java` | `c06d98f0df4003557009a701616334c10a17c87f74825d8edefd070fd74c611e` |
| 1.12.2 | `net/minecraft/block/AxisBlock.java` | `043094888447998c451a572d303cdf2c572d7760e311573f7ce12c91e5ceed4a` |
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` |
| 1.13.2 | `net/minecraft/block/AbstractLogBlock.java` | `cd3b60a9250bfa699d9ec5889709bd941490a57942189e37e0d78370a476915c` |
| 1.13.2 | `net/minecraft/block/AxisBlock.java` | `6d80b24a181d5891794b16576d4641fd25b2b8c5eb879362ffdca671975919a4` |
| 1.13.2 | `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` |
| 1.13.2 | `net/minecraft/util/datafix/DataFixes.java` | `eb0e351b692e79b3dda9c04afe75fb242ba7d7ff9c789a04ee8b18ac13f6b1e8` |

