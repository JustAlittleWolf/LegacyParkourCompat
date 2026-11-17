# Independent audit: torch collision provider

## Binding

Audited author report `build/source-residual-20261116/1.12.2--1.13.2/torch_collision_provider/report.md`, SHA-256 `13e8d74e2625b3a72b9e1be7e02e4c0887f057ac7f6b135b418d3788dccabe67`. The exact endpoint ready markers bind the source manifests to 1.12.2 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` and 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. Recomputed SHA-256 values for all 11 cited Java files match the report and their endpoint source manifests. Registry inventory/crosswalk hashes also match the report.

## Independent findings

The crosswalk rows identify the common `minecraft:torch` as `TorchBlock` in both releases and identify the common `minecraft:redstone_torch` separately from old-only `minecraft:unlit_redstone_torch`. This confirms the scope and key join; it does not imply aliasing.

For `minecraft:torch`, old `TorchBlock` has the five-value `FACING` property (all directions except DOWN), state-selected outline boxes, and an override returning `EMPTY_BLOCK_SHAPE` from `getCollisionShape`. The old `World.getCollisions` loop dispatches through `BlockState.addCollisions` to `Block.addCollisions`, which inserts only that effective collision shape. In 1.13.2 the plain torch is registered with `.noCollision()`, has no facing state, and returns the floor shape from `getShape`; `Block.getCollisionShape` checks the registered `hasCollision` flag before calling the state shape, and `BlockState.getCollisionShape` dispatches there. Its player collision contribution is empty despite the outline change.

For the common redstone torch, old `RedstoneTorchBlock` inherits old `TorchBlock`'s facing states, outline provider and empty collision override. Target `RedstoneTorchBlock` adds `LIT`, inherits the floor-only `TorchBlock.getShape`, and is registered with `.noCollision()`, so the effective collision shape remains empty for every registered state. The report correctly excludes the old-only unlit key and distinct wall-torch classes.

Both versions' `TorchBlock.getFaceShape` return `UNDEFINED`. The report's bounded conclusion—state and outline changes without a source-confirmed ordinary player-block-collision delta for these exact common keys—is supported. No wall-torch claim or pair-wide completion is made.

## Verdict

**ACCEPT.** No factual, identity, route, or scope correction is required within the assigned comparison.
