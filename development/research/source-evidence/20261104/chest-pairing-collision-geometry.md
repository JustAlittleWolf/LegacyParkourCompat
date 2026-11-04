# Chest collision geometry for legal single and paired states (1.12.2 → 1.13.2)

## Scope and accepted inputs

This source-only slice checks effective movement collision geometry dispatch for normal and trapped chests in exact local 1.12.2 and 1.13.2 sources. Accepted state-producer context: `build/source-drafts/chest-pairing-state-coverage.md`, SHA-256 `05dc658dc7d7d473e6d468f0e1b1cc6e8a4cb2d17bd677610ad35b5e473e96e6`. The accepted E1 A provider review is `build/source-drafts/residual-common-collision-a.md`, SHA-256 `4f822062f3e07e4d79e5a5fb976ccd1573d3036ee3f734504d6389845770a668`.

Pair-state preconditions used here are limited to (a) a single chest with no horizontally adjacent chest of the same registered type, or (b) an ordinary pair with exactly one same-type horizontal partner and, in 1.13.2, valid updated `TYPE`/`FACING` such that `getConnectedDirection(state)` points to that partner. A normal chest and trapped chest do not pair with one another. Malformed/stale and multiply-adjacent states are excluded. This is a provider geometry check, not a player trajectory audit.

## Exact local evidence

Both source sets are Ornithe Feather Gen2 build.2. Relevant source hashes below were verified against their endpoint manifests.

| Release | Ready marker SHA-256 | Source manifest SHA-256 |
|---|---|---|
| 1.12.2 | `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a` | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` |
| 1.13.2 | `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` |

| Version | Source file | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/block/ChestBlock.java` | `cc63486b970a8a7670910b9d4e24c8de4b616dc53c9ce2f01260cfad53a5e178` |
| 1.12.2 | `net/minecraft/block/Block.java` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| 1.12.2 | `net/minecraft/block/state/StateDefinition.java` | `934493deb8dad66936edf94264bda735ae8062c6ef091b1ff6a1501eed2b8d37` |
| 1.12.2 | `net/minecraft/world/World.java` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` |
| 1.12.2 | `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| 1.13.2 | `net/minecraft/block/ChestBlock.java` | `483ed7dc673f8acb33fbac6d5d883c652db05de2314931c522e0bee482792541` |
| 1.13.2 | `net/minecraft/block/TrappedChestBlock.java` | `ce2db33ed4b4e5dec67d757ad0211b78c9f92d406bee833485961a3a3ff611e8` |
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` |
| 1.13.2 | `net/minecraft/world/WorldView.java` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` |
| 1.13.2 | `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |

## Effective movement collision dispatch

- **1.12.2:** entity movement collision queries call `World.getCollisions` (`Entity.java:567,613`). The world visits block states and calls `BlockState.addCollisions` (`World.java:964–1001`; `StateDefinition.java:375–376`). Effective block callback is inherited `Block.addCollisions` (`Block.java:372–381`), which asks `state.getCollisionShape`. The inherited `Block.getCollisionShape` returns `state.getShape` (`:385–389`), and `ChestBlock.getShape` supplies the chest-specific `Box` (`ChestBlock.java:68–77`). The registered normal and trapped blocks are separate `ChestBlock` instances (`Block.java:865,1086`), and its neighbor test compares block identity to `this`, keeping their geometry selection separate.
- **1.13.2:** entity movement requests `World.getCollisions` (`Entity.java:580,612`); the block portion passes through `WorldView.getCollisions` to `getBlockCollisions`, which obtains each state's `getCollisionShape` (`WorldView.java:154–177, 104–105`). `BlockState` delegates this to `Block.getCollisionShape`; base `Block.getCollisionShape` returns the state's `getShape` if `hasCollision` is enabled, otherwise empty (`Block.java:393–395`). Both chest registrations use default `Block.Properties` without `.noCollision()` (`Block.java:994,1287`); `Properties.hasCollision` defaults true and is copied to the block (`:1901,231`). `ChestBlock.getShape` is therefore the effective chest geometry provider for both normal and trapped chests. `TrappedChestBlock` extends `ChestBlock` and does not override `getShape` (`TrappedChestBlock.java:15–17`).

The old and new collectors use different representations (`Box` callback emission versus `VoxelShape` return). The comparison below concerns the per-block box/cuboid coordinates selected by the chest provider, not the collectors' overlap/contact filtering.

## Shape selection and coordinate comparison

Old 1.12.2 `ChestBlock.getShape` checks same-instance neighbors in fixed priority north, south, west, east, else single (`ChestBlock.java:68–77`). For a legal single chest, it returns `SHAPE`. For a legal one-partner pair, exactly one branch selects the partner's cardinal direction. It does not consult `FACING` when selecting these boxes.

New 1.13.2 `ChestBlock.getShape` returns `SHAPE` for `TYPE == SINGLE`; otherwise it switches on `getConnectedDirection(state)` (`ChestBlock.java:93–114`). `getConnectedDirection` derives a cardinal side from `FACING` and `TYPE`. For a valid updated pair this direction is the partner side, so it selects the same directional cuboid as the old neighbor-based branch. The helper `Block.block` divides each integer coordinate by 16 (`Block.java:136–137`).

| State case / partner side | 1.12.2 returned `Box` bounds | 1.13.2 selected `VoxelShape` bounds | Result |
|---|---|---|---|
| Single | `(1/16, 0, 1/16)–(15/16, 14/16, 15/16)` | `Block.block(1, 0, 1, 15, 14, 15)` | Same cuboid |
| Pair partner north | `(1/16, 0, 0)–(15/16, 14/16, 15/16)` | `Block.block(1, 0, 0, 15, 14, 15)` | Same cuboid |
| Pair partner south | `(1/16, 0, 1/16)–(15/16, 14/16, 1)` | `Block.block(1, 0, 1, 15, 14, 16)` | Same cuboid |
| Pair partner west | `(0, 0, 1/16)–(15/16, 14/16, 15/16)` | `Block.block(0, 0, 1, 15, 14, 15)` | Same cuboid |
| Pair partner east | `(1/16, 0, 1/16)–(1, 14/16, 15/16)` | `Block.block(1, 0, 1, 16, 14, 15)` | Same cuboid |

The vertical top is `14/16` (`0.875`) in every case. The single shape and each direction's old `Box` bounds equal the new one-cuboid `VoxelShape` bounds exactly after the `1/16` scaling. Directional equality for paired states is conditional on the accepted pairing audit's valid-updated-state prerequisite; it is not true by inference for arbitrary stored `TYPE` values.

## Bounded result and limits

- For legal single normal/trapped chests and legal one-partner paired states meeting the preconditions above, the exact local providers select equal per-block cuboid bounds. No chest cuboid geometry delta is established in this slice.
- The provider dependency changed: 1.12.2 derives the side from live same-instance neighbors; 1.13.2 derives it from stored `TYPE`/`FACING`. The equality is conditional on pairing state accurately representing the live partner.
- Excluded: malformed/stale `TYPE`, multiple same-instance neighbors, neighbor/chunk-load state repair, cross-version migration/datafix, collector overlap/touch behavior, collision composition with other blocks/entities, and player trajectory. This note does not establish global movement parity or pair completion and does not alter the accepted E1 A review.
