# Leaves collision provider: 1.12.2 → 1.13.2

## Bounded result

No ordinary player block-collision difference is established for the registered leaves provider slice. Every examined legal 1.12.2 leaves state returns the unit cube through the inherited collision route. Every registered 1.13.2 leaves key has collision enabled by default, and its inherited shape and collision providers also return the unit cube for every legal leaf state. The per-state properties and IDs changed, but none is read by the compared shape providers.

This is a provider/state-domain result only. It does not assert a complete old-block-state migration path, resource membership, wall-specific predicate parity, or pair freeze.

## Exact source identities

Both endpoints are Ornithe Feather Gen2 build 2. Ready markers bind the on-disk source manifests:

- 1.12.2 manifest SHA-256: `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`.
- 1.13.2 manifest SHA-256: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.

Every listed source file's bytes were rehashed locally and matched the path/hash in its endpoint manifest.

| Version | Canonical source file | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/block/Block.java` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| 1.12.2 | `net/minecraft/block/AbstractLeavesBlock.java` | `e57e9a22e6229305be3d6feca216c18f2298d465d476c3ed51bb43a4bcf13c3a` |
| 1.12.2 | `net/minecraft/block/LeavesBlock.java` | `91d268eb3d890c6f24ec8f8e0281cffd0a42f1a4c5a9ed0ac4041221251a4fdc` |
| 1.12.2 | `net/minecraft/block/Leaves2Block.java` | `54be11551779ba540ef2374e831a7c49d37157e041b31602ccd0a0636b8d5bf4` |
| 1.12.2 | `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| 1.12.2 | `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| 1.12.2 | `net/minecraft/entity/living/player/PlayerEntity.java` | `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e` |
| 1.12.2 | `net/minecraft/world/World.java` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` |
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/LeavesBlock.java` | `8f209a4fcf08dbb4570df915795931f3f876abad8659ded4b17df6e535432006` |
| 1.13.2 | `net/minecraft/state/property/Properties.java` | `3882c1b832ac8b2b16f5a83978da441ab03cdfe66f053a873342758045827f4c` |
| 1.13.2 | `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.13.2 | `net/minecraft/entity/living/LivingEntity.java` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| 1.13.2 | `net/minecraft/entity/living/player/PlayerEntity.java` | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |
| 1.13.2 | `net/minecraft/world/World.java` | `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a` |
| 1.13.2 | `net/minecraft/world/WorldView.java` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` |

## Registered keys and state domains

In 1.12.2, registry ID 18 is `leaves` (`Block.java:782`), owned by `LeavesBlock`; ID 161 is `leaves2` (`Block.java:1129`), owned by `Leaves2Block`. Their `VARIANT` property domains are IDs 0–3 and 4–5 respectively (`LeavesBlock.java` and `Leaves2Block.java` declarations/predicates); both also carry Boolean `CHECK_DECAY` and `DECAYABLE`. Thus every state-manager combination consists of one of six leaf variants plus those two Booleans. Metadata decoding supplies the variant and both Booleans from the four low metadata bits; metadata bits outside that encoding do not introduce another state property.

1.13.2 registers six concrete keys, all to `LeavesBlock` (`Block.java:884–889`): `oak_leaves`, `spruce_leaves`, `birch_leaves`, `jungle_leaves`, `acacia_leaves`, and `dark_oak_leaves`. `LeavesBlock.java:26–32,188–190` defines `DISTANCE` and `PERSISTENT`; `Properties.java:38,79` defines their domains as Boolean and integer 1–7. Every key therefore has 14 state-manager combinations. This report does not assert that every possible stored combination is reachable through chunk/data loading or that a specific migration route maps each old state to one target state; the provider result holds for the entire declared state domains on both sides.

## Provider comparison

**1.12.2.** `LeavesBlock` and `Leaves2Block` inherit `AbstractLeavesBlock`, which declares no `getShape`, `getCollisionShape`, or `addCollisions` override. `Block.getShape` returns `FULL_BLOCK_SHAPE` (`Block.java:307–309`), the unit box defined at line 52. The base `getCollisionShape` returns `state.getShape` (`:387–388`), and `addCollisions` submits that returned shape after moving it to the block position (`:372–373`). None of these methods branches on leaf variant, decay flag, material, opacity, or block position. Therefore all declared leaf states return the unit collision box.

**1.13.2.** The six registrations construct leaves with `Block.Properties.of(Material.LEAVES)...` and do not call `noCollision` (`Block.java:884–889`). `Block.Properties.hasCollision` defaults to `true` (`Block.java:1901`). `LeavesBlock` declares no shape or collision override. Base `Block.getShape` returns `VoxelShapes.block()` (`Block.java:388–389`); base `getCollisionShape` returns `state.getShape` when `hasCollision` is true (`:393–395`). Therefore every declared target leaf state returns the full unit cube.

Both versions' `Entity.move` call their world block-collision query (1.12.2 `Entity.java:567,613` → `World.getCollisions`, `World.java:964–1001`; 1.13.2 `Entity.java:580,612` → `WorldView.getCollisions` / block collision iteration). Ordinary player movement uses this entity path because players inherit `Entity`. The target `WorldView` block-collision iteration obtains each block state's collision shape; the old world collision loop invokes `BlockState.addCollisions`. The identical unit-box result means this provider slice presents the same collision obstacle for any intersecting query box in either version.

## Coverage boundary

Closed here: all declared old LeavesBlock/Leaves2Block state-domain combinations and all declared target six-key LeavesBlock state combinations, through the exact inherited collision providers and the ordinary entity-movement query path.

Open: complete cross-version world-state key/property conversion and its runtime applicability; any wall-specific face predicate; unrelated leaf behavior such as support, neighbor, fluid, or decay callbacks. No claim is made for providers outside leaves or for full-pair source coverage.



