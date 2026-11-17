# Base collision dispatch coverage: 1.12.2 → 1.13.2

## Evidence identity

Exact source namespace: Ornithe Feather Gen2 build 2 on both sides. The 1.12.2 source-manifest digest is `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 1.13.2 is `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. Each digest was rechecked against its ready marker and local `ornithe-feather.sources.sha256`; all specifically consulted Java-file hashes below match the corresponding source manifest.

## Compared slice and verdict

**Verdict: base dispatch has a source-confirmed candidate-filter change reachable by ordinary player movement, independent of individual shape-provider overrides. Final displacement impact remains unclosed because the collision consumer also changes.**

- **1.12.2 route.** `World.getCollisions` scans the expanded player AABB with the historical floor/ceil plus one-cell bounds, skips unavailable chunks and selected corner cells, uses stone as the outside-border fallback when applicable, then calls `BlockState.addCollisions` (World.java:964–1013). StateDefinition's concrete state forwards that call to its block (StateDefinition.java:375–377). Base `Block.addCollisions` obtains `state.getCollisionShape`; base `getCollisionShape` returns `state.getShape`; the default shape is a unit `Box`. `addCollision` translates that box and appends it only when the input AABB strictly intersects it (Block.java:307, 372–389). Air and plant behavior can come from specialized ancestors/providers, as recorded in the reused override roster; they are not used to infer the base route.
- **1.13.2 route.** `Entity.move` supplies the current player shape and requested deltas to the collision query (Entity.java:580, 612). `WorldView.getCollisions` constructs a swept shape and an `excludeBlock` shape from the current box (WorldView.java:154–177); `getBlockCollisions` scans the expanded voxel bounds, uses the border block fallback, asks each state for its collision shape, discards shapes overlapping `excludeBlock`, and emits only nonempty shapes overlapping the swept query (WorldView.java:112–150). BlockState forwards to the block; base `Block.getShape` returns the unit voxel cube and base `getCollisionShape` gates that shape on `hasCollision` (BlockState.java:205–210; Block.java:388–395). World adds entity collision shapes separately (World.java:1755–1757), outside this block-provider slice.
- **Direct difference.** The old block collector tests each translated collision box against the supplied expanded AABB and has no current-player-volume exclusion. The new movement query removes the current player volume from the sweep and explicitly filters candidates overlapping that excluded volume. This changes the block-candidate set presented to ordinary movement even when a registered block inherits the default base shape. At this layer, ordinary base full-cube geometry remains unit-volume geometry (`Box` versus full `VoxelShape`); do not infer equal tick displacement because the downstream clipping operation changed from per-Box `intersectY/X/Z` to `VoxelShapes.calculateMaxOffset` in Entity.java:567–615.
- **Base collision gate.** 1.13 adds `hasCollision ? state.getShape(...) : empty` to base Block dispatch. The old base method has no corresponding flag. For a new state with `hasCollision=false` and no more-derived collision override, the effective base route now returns empty even if its shape is nonempty. The roster shows many inherited/specialized providers and unresolved key joins, so this review does not claim that this gate changes a particular legacy registered key with no special provider. Ordinary inherited full-collision blocks take the `true` gate and retain the unit base shape.

## Explicit remaining dependency

The base collector/query delta is established, but its resulting player-displacement delta is not: close the paired `Entity.move` collision clipping and candidate-consumption math, including overlap-at-start and axis order. Separately join the roster's exact registered keys/state-property construction against the `hasCollision` registrations to determine whether any legacy key reaches the new false gate through base dispatch without an old specialized `getCollisionShape` provider. This packet does not close the provider roster's broader state/context and alias dependencies.

## Consulted source-file SHA-256

| Release | Relative file | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/world/World.java` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` |
| 1.12.2 | `net/minecraft/block/Block.java` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| 1.12.2 | `net/minecraft/block/state/StateDefinition.java` | `934493deb8dad66936edf94264bda735ae8062c6ef091b1ff6a1501eed2b8d37` |
| 1.12.2 | `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| 1.13.2 | `net/minecraft/world/WorldView.java` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` |
| 1.13.2 | `net/minecraft/world/World.java` | `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a` |
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` |
| 1.13.2 | `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
