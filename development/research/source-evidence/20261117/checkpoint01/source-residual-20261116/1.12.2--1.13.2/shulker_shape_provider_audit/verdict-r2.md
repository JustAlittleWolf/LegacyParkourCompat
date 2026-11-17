# Independent audit: Shulker box collision-shape provider (r2)

**Verdict: ACCEPT, bounded provider-output comparison only.**

## Inputs and identities

- Audited `build/source-residual-20261116/1.12.2--1.13.2/shulker_shape_provider/coverage.md`, SHA-256 `f36e61787ddfd246aa9b2ab61ff95ba764b0e47bd16ad9a7e9ead3df554b9f77`.
- 1.12.2 Feather ready marker identifies version `1.12.2`, mapping `ornithe-feather`, status `ready`; ready marker `sourceManifestSha256` and on-disk `ornithe-feather.sources.sha256` both equal `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`.
- 1.13.2 Feather ready marker identifies version `1.13.2`, mapping `ornithe-feather`, status `ready`; ready marker `sourceManifestSha256` and on-disk `ornithe-feather.sources.sha256` both equal `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- Independently recomputed the SHA-256 of each of the report's 14 Java files. All match both the report and corresponding entry in the version's source sidecar. This includes the corrected 1.12.2 `entity/Entity.java` and 1.13.2 `world/WorldView.java` and `block/shape/VoxelShapes.java` hashes.

## Source claim audit

The paired `ShulkerBoxBlock.getShape` methods use the same block-entity-presence gate and delegate to `ShulkerBoxBlockEntity.getShape(state)`; absent block entity returns the full cube. The older provider returns the `Box` directly. The newer provider wraps the same `Box` using `VoxelShapes.block(Box)`, with full-cube fallback `VoxelShapes.block()`.

Both block-entity methods use the same facing and calculate each extension as `0.5F * getProgress(1.0F) * facing.getOffset*()`. Both `Box.expanded` implementations update only the facing-side min/max in the same way. The same `tick` and animation transitions maintain progress; the same interpolation expression at tick delta 1 returns the current progress. In 1.13.2, `VoxelShapes.block(Box)` either creates a grid shape for representable coordinates or uses an `ArrayVoxelShape` whose coordinate lists are the exact input bounds; `bounds()` therefore preserves the supplied box extrema. No provider-bound difference is present in this bounded comparison.

The player collision-query path is reachable on both sides. In 1.12.2, `Entity.move` invokes `World.getCollisions`; block enumeration reaches `BlockState.addCollisions`, and default block collision shape delegates to the state shape, which dispatches to the Shulker provider. In 1.13.2, `Entity.move` invokes the `WorldView` collision query; it asks each state for its collision shape, and `Block.getCollisionShape` delegates to `state.getShape` when the block has collision. `ShulkerBoxBlock` supplies the provider above. This confirms local-player reachability for the provider output; the block-entity `moveEntities` push behavior is outside this verdict.

## Disposition and limits

Accept **bounded no-difference** for ShulkerBoxBlock collision provider output, including block-entity fallback, direction, animation progress and returned bounds, for exact Feather 1.12.2 vs 1.13.2. No independent movement finding. This does not close other block shape/state/neighbor providers, general collision-query or solver parity, or either pair's coverage and dependency closure; it is not a pair freeze.
