# DDA mutable-bounds closure: 1.8.9 block ID 54 (Chest)

## Evidence identity

- Historical source: Minecraft 1.8.9, `ornithe-feather`, canonical tree `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.8.9/ornithe-feather`.
- Current source: Minecraft 26.2, `unobfuscated`, canonical tree `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/26.2/unobfuscated`.
- The accepted low projection is `.native-source/20261028/collision-next/dda-189-registry-bounds-map/ids-000-127/registry-state-bounds.csv`. Its ID 54 row records key `chest`, runtime class `ChestBlock`, metadata decoder `ChestBlock.getStateFromMetadata`, and `NOT_PROVEN_BY_IDENTITY_CROSSWALK` for state conversion.
- SHA-256 values for the projection and each cited source file are in `source-hashes.sha256`.

## Historical ID and metadata decode

`Block.java:942` registers numeric ID 54 as `chest` with one `new ChestBlock(0)` instance. The state definition contains only `FACING` (`ChestBlock.java:28-37, 448-451`). `getStateFromMetadata` reads `Direction.byId(metadata)` and replaces vertical directions with NORTH (`ChestBlock.java:434-441`). `Direction.byId` indexes the six-value enum array with `abs(id % 6)` (`Direction.java:11-17, 168-170`). For stored metadata nibbles 0..15, decoded facing is:

| Metadata | Facing |
|---|---|
| 0, 1, 2, 6, 7, 8, 12, 13, 14 | NORTH |
| 3, 9, 15 | SOUTH |
| 4, 10 | WEST |
| 5, 11 | EAST |

This is decoder output, not a claim that each nibble was normally emitted by placement. Metadata stores facing only; it does not encode neighbor-selected bounds or paired-chest type.

## Historical pair inputs and bounds writes

`ChestBlock.updateShape(world, pos)` checks adjacent positions in short-circuit priority order NORTH, SOUTH, WEST, EAST (`ChestBlock.java:56-68`). A match means `world.getBlockState(neighbor).getBlock() == this`; it does not inspect facing, metadata, or block-entity contents. The first match writes these local bounds to the shared `ChestBlock` object:

| First matching neighbor | Local bounds `(minX,minY,minZ)..(maxX,maxY,maxZ)` |
|---|---|
| NORTH | `(1/16,0,0)..(15/16,14/16,15/16)` |
| SOUTH | `(1/16,0,1/16)..(15/16,14/16,1)` |
| WEST | `(0,0,1/16)..(15/16,14/16,15/16)` |
| EAST | `(1/16,0,1/16)..(1,14/16,15/16)` |
| none | `(1/16,0,1/16)..(15/16,14/16,15/16)` |

`Block.setShape` stores six floats on `this` (`Block.java:292-299`), and ID registration constructs one chest block instance. The old collision provider builds its box from those fields and ignores its `state` argument (`Block.java:339-350`). `rayTrace` explicitly calls `updateShape(world, pos)` before reading those same fields for its plane tests (`Block.java:456-465`). Thus ray bounds are refreshed by the ordered neighbor scan; `getCollisionShape` itself only reads the bounds currently held by the instance. The geometry storage is shared per block instance, not per position or per `BlockState`.

## Current 26.2 registration and shape inputs

Current registration uses a resource key, not the old numeric ID: `BlockItemIds.CHEST` is `BlockItemId.create("chest")` (`references/BlockItemIds.java:312`), and `BlockItemId.create(String)` uses the default namespace and creates the block and item keys (`references/BlockItemId.java:18-20`). `Blocks.CHEST` registers a `ChestBlock` under that block key (`world/level/block/Blocks.java:1274-1278, 5980-5989`), yielding `minecraft:chest`. This does not establish an old-to-current state conversion.

Current `ChestBlock` defines `FACING`, `TYPE`, and `WATERLOGGED`, defaulting to NORTH/SINGLE/false (`ChestBlock.java:72-77, 137-147, 381-384`). `TYPE` has SINGLE, LEFT, and RIGHT values (`BlockStateProperties.java:130`; `ChestType.java:6-9`). Neighbor updates schedule a water tick when waterlogged, then change `TYPE` based on horizontal neighbor direction, neighbor type/facing, and connected-direction checks (`ChestBlock.java:158-185`). The shape provider reads only the current state's `TYPE` and `FACING`: SINGLE selects `SHAPE`; LEFT/RIGHT select a directional `HALF_SHAPES` entry through `getConnectedDirection` (`ChestBlock.java:76-77, 192-203`). WATERLOGGED does not affect this provider.

The current single shape is `Block.column(14, 0, 14)`, a centered 14/16-wide, 14/16-high shape. Each half shape is built from `Block.boxZ(14, 0, 14, 0, 15)` and horizontal rotations (`ChestBlock.java:76-77`; `Block.java:177-181, 196-198`; `Shapes.rotateHorizontal`, `world/phys/shapes/Shapes.java`). The default collision provider delegates to the state's shape when collision is enabled (`BlockBehaviour.java:323-328, 340`); BlockState's collision query uses that provider or its state cache (`BlockBehaviour.java:678-691, 932`). There is no chest collision override. Its geometry is therefore reproducible from state without a mutable per-block bounds cache.

## Equivalence boundary and ordering

The sources support a limited geometric correspondence: a current state representing a valid paired half can select a directional half shape with the same broad one-edge-extension dimensions as an old neighbor-selected box. The mapping is not established from block ID or old metadata alone: historical state has only FACING, current shape also needs TYPE, and the projection explicitly leaves cross-version state conversion unproven. Neighbor identity/position alone does not guarantee correspondence either. Old code accepts any horizontal same-instance neighbor, ignores facing, and resolves multiple neighbors NORTH > SOUTH > WEST > EAST. Current geometry comes from SINGLE/LEFT/RIGHT state and connected direction; update logic validates pair facing/type and handles directional neighbor callbacks rather than scanning four neighbors in that priority order. A valid one-pair mapping needs explicit state/context evidence; it cannot be inferred from ID 54 alone.

Block-entity inventory, lid animation/occlusion, and other tile/entity context are unsupported here. Neither historical bound selection nor current `getShape` reads chest block-entity state; this closure makes no claim about those separate behaviors.
