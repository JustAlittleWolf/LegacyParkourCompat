# 1.14–1.15.2 border auto-jump caller correction (r3)

This correction supersedes the 1.14.1–1.14.3 auto-jump eligibility conclusions in r1 and r2. Both earlier reports remain unchanged. The independent audit verdict is `review-114-1152-border-alias-caller-matrix/verdict.md` (SHA-256 `e09248e3bd16af5743335f3667829eef590e0d76d17ff858838218cad6e8a417`). I retraced the exact overloaded calls and agree with its correction.

## Corrected 1.14 caller matrix

| Release | Client call and selected overload | Border path into the auto-jump scan |
|---|---|---|
| 1.14.1 / 1.14.2 Yarn | `ClientPlayerEntity.move` calls `method_3148` after `super.move`. That method passes a `BoundingBox` to `world.getCollisionShapes(Entity, BoundingBox, Set)`. `ViewableWorld`'s overload builds a lazy block-collision stream and concatenates it before `getCollisionShapes(Entity, VoxelShape, Set)`. | Yes. First `tryAdvance` checks border against the player's contracted and expanded current AABB; it emits the border when `!overlap(contracted) && overlap(expanded)`, before iterating block candidates. The similarly named `VoxelShape` overload is only the entity-collision leaf; the player does not select it. |
| 1.14.3 Yarn | `ClientPlayerEntity.move` calls `method_3148`; its `Box` argument selects `ViewableWorld.getCollisionShapes(Entity, Box, Set)`. | Yes. The aggregate is `Streams.concat(method_20812(arg, box), method_20743(arg, box, set))`. `method_20812` emits the border first, before block candidates; `method_20743` is the entity-only branch. `IWorld.method_20743` delegates that branch to `EntityView`, but does not replace the aggregate caller. |
| 1.14 Feather | `LocalClientPlayerEntity.move` calls `autoJump`; `WorldView.getCollisions(Entity, Box, Set)` includes the border path. | Yes. |
| 1.14.4 Feather | `LocalClientPlayerEntity.move` calls `autoJump`; `WorldView.getCollisions` begins with block collisions, whose lazy border admission precedes block candidates. | Yes. |

Therefore the infinite-Y border AABB can enter the local-player auto-jump scan in 1.14, 1.14.1, 1.14.2, 1.14.3, 1.14.4 and 1.15–1.15.2. The earlier claim that it was ineligible through 1.14.3 and appeared by 1.14.4 is withdrawn. No 1.14.4 introduction is established by this comparison.

## Conditional sample and timer path

The border shape is flattened to collision AABBs; the first intersecting border box can be the west exterior strip. Its unbounded coordinates pass through `Box`/`BoundingBox.getCenter()`'s `lerp(0.5, min, max)`. Non-finite center components produce NaN; `Vec3i(double, …)` floors those to integer zero. The resulting base sample is `(0,0,0)`, and the auto-jump loop reads the block at `(0,1,0)` first. A nonempty sample can replace the border box's initial `top = +infinity`. The timer write is reachable only if the sampled top is more than `0.5` and no greater than the active jump-height threshold above the player. This is a source-path condition, not a runtime trajectory result.

## Exact hidden Yarn identities and caller hashes

The exact hidden `.decompile-provenance.json` sidecars and pinned client/mapping inputs are recorded in r2. Their current actual sidecar SHA-256 values are `.1` `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907` (3,155 source entries), `.2` `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a` (3,155), `.3` `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a` (3,159). I checked the actual source SHA-256 values against each sidecar's per-file source map; the listed files matched.

| Relevant source | 1.14.1 / 1.14.2 | 1.14.3 |
|---|---|---|
| `client/network/ClientPlayerEntity.java` | `6af7e75e80619ad77ce2c84692136b0a1d562a44d0e7910dcc3f6040b2dc9933` / `90c96701849c9212ea7714f02e7bc9011d0a7d0aaf1f7b46464497ba3ca14947` | `28b6b4031e02afd4a2e6b4a80e5a7ca06f16dac42ca0f92cab25e2c2dc9e14ae` |
| `world/ViewableWorld.java` | `67e88826a77d5ed6dbdf7701acab287bd6b34f1e394625a6a701c1181556fd2e` | `3e6f183c66c98206cef362551c53dbf8ea7ac11e44123060c640da09c944d29d` |
| `world/EntityView.java` | `fef34e5144d87f77a777e4dc1b62ace7a92af2ef9dba7ceb25a80c7068170eea` | `d5f906a1b0332f9ff978e9c0a92bb913cdff68ac173a33aad44aedccc9516ebb` |
| `world/IWorld.java` | `662d377c31f4e4366d558e24f325db0b00f48507f298cd718293917994bca72c` | `0930f466c86ebb07b77ecbf85661ea519ab43d884d5874edeaf52f21d534947a` |
| `world/border/WorldBorder.java` | `e191b5b5fcfc43c4412b9da49e5146219906c9bc6995b40ac6cab25f62a7cd52` | `2da4c764b819fdc78b66eedf4cdfd71aadfcf9e7800b80515a88cd050c877506` |

Exact locations: 1.14.1/.2 `ClientPlayerEntity.java:834–940`, `ViewableWorld.java:125–194`, `EntityView.java:45–60`, `IWorld.java:72–80`; 1.14.3 `ClientPlayerEntity.java:833–939`, `ViewableWorld.java:125–185`, `EntityView.java:45–59`, `IWorld.java:73–81`. The 1.14, 1.14.4 and 1.15–1.15.2 evidence paths and hashes remain in r1.
