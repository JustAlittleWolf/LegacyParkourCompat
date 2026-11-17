# End Portal block collision provider: 1.12.2 → 1.13.2

## Bounded result

For the common registered key `minecraft:end_portal`, the ordinary block-collision provider returns no collision in both versions. In 1.12.2, `EndPortalBlock.addCollisions` is an empty override, so the old `World.getCollisions` route emits no block collision boxes for this key. In 1.13.2, the registration sets `hasCollision=false` with `.noCollision()`, and base `Block.getCollisionShape` therefore returns `VoxelShapes.empty()`; `WorldView.getBlockCollisions` filters empty shapes. No ordinary block-collision provider difference is established in this slice.

Both endpoint registrations expose one block state: `EndPortalBlock` declares no state properties and introduces no state-property builder. The old and new outline providers each return a block-wide footprint with height `0.75` (old `Box` maxY `0.75`; target `VoxelShape` height `12/16`). That outline is not used as a collision shape on the old movement route because the override is empty, and the target `.noCollision()` gate suppresses it from the target collision route.

This finding excludes portal entry/transfer and other End Portal interactions. It does not claim all state or migration paths, trajectory parity, or pair completion.

## Exact sources and identity

Both releases are the read-only Ornithe Feather Gen2 build 2 inputs. The ready markers bind these source-manifest SHA-256 identities:

- 1.12.2: `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`
- 1.13.2: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`

Each listed file SHA-256 was recomputed from the canonical source bytes and matches its endpoint manifest entry.

| Release | Source file | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/block/EndPortalBlock.java` | `b091d0fbf32b3bf18090fac68c876657e518d986eac7236ef0eb19cdacebf8e6` |
| 1.13.2 | `net/minecraft/block/EndPortalBlock.java` | `9541e703d04bf9148cb324741de73c4f3ce76021bc358f22308c946bccb72ad3` |
| 1.12.2 | `net/minecraft/block/Block.java` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.12.2 | `net/minecraft/block/state/StateDefinition.java` | `934493deb8dad66936edf94264bda735ae8062c6ef091b1ff6a1501eed2b8d37` |
| 1.13.2 | `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` |
| 1.12.2 | `net/minecraft/world/World.java` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` |
| 1.13.2 | `net/minecraft/world/WorldView.java` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` |

## Key, state, and provider route

Both versions register the exact key `end_portal` to `EndPortalBlock` (`Block.java:1017` old numeric registration 119; target named registration at `:1217–1219`). The old EndPortalBlock constructor does not define state properties, and its inherited `addCollisions` behavior is replaced by this explicit empty method (`EndPortalBlock.java:44–46`). `World.getCollisions` dispatches the block-state method at the candidate position (`World.java:1001`); old `StateDefinition.State.addCollisions` forwards it to the registered block instance (`StateDefinition.java:375–376`). Thus, irrespective of the old EndPortal outline returned from `getShape`, the block contributes no collision list entry.

The target EndPortalBlock constructor receives `Block.Properties.of(Material.PORTAL, MapColor.BLACK).noCollision().lightLevel(15).strength(...)` at registration (`Block.java:1217–1219`). `Block.Properties.noCollision` sets `hasCollision=false`; the base `Block.getCollisionShape` returns `VoxelShapes.empty()` when that flag is false (`Block.java:393–395, 1943–1946`). Target `BlockState.getCollisionShape` delegates to this provider (`state/BlockState.java:209–210`). The target world-query path asks for that collision shape and filters empty VoxelShapes (`WorldView.java:112–147`), so the 0.75-height outline from `EndPortalBlock.getShape` is not admitted as collision.

The old and target `EndPortalBlock.getShape` methods both encode the same 0.75-height outline (`EndPortalBlock.java:21, 34–36` old; `:19, 32–34` target). The target block also retains the `FaceShape.UNDEFINED` override (`EndPortalBlock.java:76–78`). These facts describe outline/face behavior only; the bounded collision conclusion above follows from the old empty `addCollisions` body and target no-collision gate.

## Limits

This report compares only the exact `end_portal` ordinary block-collision provider. It does not cover EndPortalFrame, portal entry or dimension transfer, non-collision callbacks, other block keys, malformed or custom world data, or the global source-coverage denominator. Pair coverage remains partial.
