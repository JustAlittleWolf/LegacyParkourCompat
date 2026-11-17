# Cobweb collision-shape provider: 1.12.2 → 1.13.2

## Bounded source identity

- 1.12.2 Ornithe Feather Gen2 build 2 ready source manifest SHA-256: `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` (`decompiled_minecraft/1.12.2/ornithe-feather.ready.json`).
- 1.13.2 Ornithe Feather Gen2 build 2 ready source manifest SHA-256: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` (`decompiled_minecraft/1.13.2/ornithe-feather.ready.json`).
- Provider-roster identity: `provider-override-roster.md`, SHA-256 `524e27cf0618ea8942a961f27835fabf7091248a0629b9f03f4bd4d39c05a979`; its old CobwebBlock file hash matches the canonical source bytes below.

Canonical source-file SHA-256 values (all under the manifest-bound `decompiled_minecraft/<version>/ornithe-feather/` trees):

| Version | Source file | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/block/CobwebBlock.java` | `db5e0e9197aeb5dc518e2eda91bc785f2fbc1e0459db7aefcb0505d8b3ae8767` |
| 1.12.2 | `net/minecraft/block/Block.java` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| 1.12.2 | `net/minecraft/block/state/StateDefinition.java` | `934493deb8dad66936edf94264bda735ae8062c6ef091b1ff6a1501eed2b8d37` |
| 1.12.2 | `net/minecraft/world/World.java` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` |
| 1.12.2 | `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| 1.12.2 | `net/minecraft/entity/living/player/PlayerEntity.java` | `e4e0fdbE07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e` |
| 1.13.2 | `net/minecraft/block/CobwebBlock.java` | `a0848fddd8d8c526e99241f6f6fab74df108d0f8861a4f04cc331fd6fd058875` |
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` |
| 1.13.2 | `net/minecraft/world/WorldView.java` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` |
| 1.13.2 | `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.13.2 | `net/minecraft/entity/living/player/PlayerEntity.java` | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |

## Provider comparison

- **1.12.2:** `CobwebBlock.getCollisionShape(BlockState, WorldView, BlockPos)` at `net/minecraft/block/CobwebBlock.java:38–42` returns `EMPTY_BLOCK_SHAPE` unconditionally. In `Block.java:54`, that sentinel is `null`; base `addCollisions` at lines 372–374 delegates through the state to the provider, and `addCollision` at lines 376–382 adds no box for the sentinel. The method does not read its state, world, or position arguments.
- **1.13.2:** `CobwebBlock.java` (class begins at line 18) declares no collision-shape override. Its registered instance at `Block.java:920` is constructed with `Block.Properties.of(Material.COBWEB).noCollision()`. `Block.Properties.noCollision()` at lines 1943–1945 sets `hasCollision = false`; the constructor copies that value at lines 224–238. The inherited `Block.getCollisionShape` at lines 392–395 therefore returns `VoxelShapes.empty()` without calling `state.getShape(world, pos)`. The fallback state shape is a full block (`Block.getShape`, lines 387–390), but it is unreachable for this cobweb instance through the collision-shape provider.

**State/context inputs:** neither release has a CobwebBlock state-dependent collision result. The old override ignores all three arguments. The new inherited base path reads the registered block instance's fixed `hasCollision` flag, then returns empty; it does not read block-state properties, world contents, position, neighbors, entity, or movement direction. The state accessor merely dispatches to `getBlock().getCollisionShape(state, world, pos)` in 1.13.2 `BlockState.java:209–210`.

## Direct player collision reachability

- In both releases `PlayerEntity` extends `LivingEntity` (1.12.2 `PlayerEntity.java:94`; 1.13.2 `PlayerEntity.java:98`), and movement collision resolution is inherited from `Entity.move`.
- **1.12.2 route:** `Entity.move` at lines 462 onward queries `World.getCollisions`; for the normal movement collision scan, `World.java:964–1001` iterates candidate blocks and calls `BlockState.addCollisions`. The concrete state implementation forwards to its block at `StateDefinition.java:370–376`; base `Block.addCollisions` invokes the CobwebBlock provider. Thus a player whose expanded movement box reaches a cobweb gets no cobweb collision box.
- **1.13.2 route:** `Entity.move` at lines 473–483 queries `World.getCollisions` for a nonzero movement; `World.java:1755–1757` delegates block shapes to `WorldAccess`/`WorldView`. `WorldView.getBlockCollisions` at lines 112–151 obtains each loaded candidate's `BlockState.getCollisionShape(world, pos)`, then filters empty shapes. A cobweb contributes empty and is omitted from the movement collision stream.

The provider result is therefore empty on both sides along a reachable player movement route. This bounded shape-provider comparison finds **no collision geometry change for cobwebs**. `CobwebBlock.onEntityCollision` still invokes `Entity.onCobwebCollision` in both releases; that direct velocity response is outside this shape-provider packet and is already identified as covered in the supplied callback packet. This report does not independently re-review that callback or close any other shape, registry-alias, tick-graph, audit, or pair-freeze dependency.

## Bounded verdict and remaining gates

Provider behavior and player collision reachability for the CobwebBlock class are source-closed for this packet: unconditional empty sentinel (1.12.2) and fixed no-collision base result (1.13.2). No finding is drafted. Pair coverage, independent audit, other provider families and pair completion remain open under the dispatch scope.
