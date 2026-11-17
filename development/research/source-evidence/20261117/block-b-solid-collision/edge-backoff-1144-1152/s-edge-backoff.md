# S-EDGE-BACKOFF: 1.14.4 → 1.15.2

## Disposition

Author coverage record: **no difference found** for the bounded careful/backoff operation in the exact 1.14.4 and 1.15.2 client sources. This is an operation-level no-difference record, not a positive finding. The implementation owner changes from `Entity.applySneaking` to `Player.maybeBackOffFromEdge`; the effective gates, query, order, arithmetic, and returned movement vector are equivalent for LocalPlayer movement.

## Exact source and provenance identity

Source root is the primary checkout's read-only `decompiled_minecraft/` tree. Both sides are Mojmap namespace, decompiled with Vineflower 1.12.0 under Java 25, from exact release IDs recorded in their provenance and ready markers.

| Release | Source root | Provenance SHA-256 | Ready marker SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | Client SHA-256 |
|---|---|---|---|---|---|---|
| 1.14.4 | `decompiled_minecraft/1.14.4/mojmap/` | `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97` | `ec7b6a6d9ba72f8a19908527977c970a8e2b3007b0e0b4323a725fb5943d3043` | `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` | `308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970` | `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a` |
| 1.15.2 | `decompiled_minecraft/1.15.2/mojmap/` | `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e` | `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0` | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` | `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406` | `4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c` |

The ready markers' recorded source-manifest SHA-256 values match recomputed manifest hashes. Every source-file hash listed below was recomputed and matches its line in that release's source manifest. Ready markers report `status=ready`; no source tree was generated or modified during this review.

## Compared slices

All ranges are one-based and inclusive.

| Operation / dependency | 1.14.4 Mojmap | 1.15.2 Mojmap |
|---|---|---|
| LocalPlayer movement dispatch | `net/minecraft/client/player/LocalPlayer.java`, `move`, 844–850 | `net/minecraft/client/player/LocalPlayer.java`, `move`, 850–856 |
| Entity movement gate and call | `net/minecraft/world/entity/Entity.java`, `move`, 446–466; `applySneaking`, 575–623 | `net/minecraft/world/entity/Entity.java`, `move`, 450–470; virtual `maybeBackOffFromEdge`, 604–606 |
| Player-specific backoff and retained flag | `Entity.applySneaking`, 575–623; `Entity.isSneaking`, 1812 onward | `net/minecraft/world/entity/player/Player.java`, `isStayingOnGroundSurface`, 294–295; `maybeBackOffFromEdge`, 1002–1050; `Entity.isSteppingCarefully`, 1787–1789 |
| Empty-collision predicate and block/entity stream collector | `net/minecraft/world/level/LevelReader.java`, `noCollision`, 110–124; `getEntityCollisions` and `getCollisions`, 126–132 | `net/minecraft/world/level/CollisionGetter.java`, 43–65 |
| Candidate cell bounds, cursor sampling, collision-shape dispatch | `LevelReader.getBlockCollisions`, 134–187; `net/minecraft/core/Cursor3D.java`, 1–69; `net/minecraft/world/level/block/state/BlockState.java`, `hasLargeCollisionShape` 94 and `getCollisionShape` 194–200 | `CollisionGetter.getBlockCollisions`, 67–120; `Cursor3D.java`, 1–69; `BlockState.java`, `hasLargeCollisionShape` 96 and `getCollisionShape` 192–198 |
| Client chunk lookup used by sampled block collision query | `net/minecraft/world/level/Level.java`, `statusForCollisions`, 877–880 | `Level.java`, `getChunkForCollisions`, 753–757 |

## Closed behavior and query depth

- `LocalPlayer.move` calls `super.move(moverType, vec3)` before its auto-jump update on both sides. Only the X/Z coordinate accessor spelling differs in the wrapper; the movement vector passed to the superclass is the same.
- 1.14.4's predicate is `this instanceof Player`, `SELF` or `PLAYER` mover, `onGround`, and `isSneaking()`. 1.15.2 dispatches to a `Player` override with the same mover and `onGround` gates and `isStayingOnGroundSurface()`. That player predicate reads `isShiftKeyDown`; 1.14.4 `Entity.isSneaking()` reads the same shared flag. Thus the player gate is equivalent under the bounded LocalPlayer call path.
- Both implementations keep local X/Z movement components and use `0.05` increments. They test X alone, Z alone, then X and Z together; each query uses the original bounding box moved by candidate X/Z and lowered by `maxUpStep`. The same signed comparisons zero a remainder in `[-0.05, 0.05)` or move it toward zero by `0.05`. Both return a new vector with the original Y component. The methods do not write movement flags or velocity; the reads of `onGround` and the shift/sneak flag remain reads.
- Both `noCollision(entity, box)` implementations require `getCollisions(...).allMatch(VoxelShape::isEmpty)`, concatenating block and entity collision streams. The interface default entity stream returns empty on both sides. Block collision sampling expands bounds with the same `1.0E-7` offsets and one-cell margins, constructs `Cursor3D` with the same axis bounds, applies the same cell-type filters, samples the same state, translates its collision shape by the sampled block coordinates, and intersects against `Shapes.create(aABB)`.
- The collision query's chunk API moved: 1.14.4 obtains `FULL` through `Level.statusForCollisions()` and passes `false`; the client `Level` returns `FULL`. 1.15.2 `Level.getChunkForCollisions` explicitly requests `FULL, false`. For the client level this preserves the chunk status and non-loading argument. The call to `BlockState.getCollisionShape` retains the entity collision context (`CollisionContext.of(entity)`).
- `Cursor3D.java` has the same SHA-256 on both sides. The compared collision-query dispatcher, ordering, offsets, filters, and shape translation/intersection therefore show no source change in this bounded path.

## Residual scope

This record covers the player gate and edge-backoff operation, the `noCollision` stream defaults, the block-cell collector and cursor, the Level chunk lookup, and the `BlockState` collision-shape dispatch call. It does not claim that each individual block's collision-shape provider is unchanged, or cover other collision/shape providers. It does not assess the separate near-edge step-return outcome claim, complete pair coverage, patch-introduction boundary, runtime parity, or acceptance beyond the bounded source slice.

## Recomputed source-file hashes

| Release | Relative source file | SHA-256 |
|---|---|---|
| 1.14.4 | `net/minecraft/client/player/LocalPlayer.java` | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` |
| 1.14.4 | `net/minecraft/world/entity/Entity.java` | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` |
| 1.14.4 | `net/minecraft/world/entity/player/Player.java` | `e9ce5eb18c5581ffe2b610b273ffd85786587a00ba853e5dba0ac96593622b12` |
| 1.14.4 | `net/minecraft/world/level/LevelReader.java` | `1f86a029e4404b91aa532ca33ab0dbb63b085334c561ce44e0d0875aa50fdbc9` |
| 1.14.4 | `net/minecraft/world/level/Level.java` | `9278afc8e69353e5a483b345f9a6db2f11e67cfc98c43f9e7bd62cd0bebe5b02` |
| 1.14.4 | `net/minecraft/core/Cursor3D.java` | `a679c4938bfa717f1c6614c361ec9605c6d0ec6a1b764138c567f519066c6cfb` |
| 1.14.4 | `net/minecraft/world/level/block/state/BlockState.java` | `2bcc0167cb566df8745abbe967f0a8750038ad5a9b7843f5fb25bab714d73188` |
| 1.15.2 | `net/minecraft/client/player/LocalPlayer.java` | `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd` |
| 1.15.2 | `net/minecraft/world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| 1.15.2 | `net/minecraft/world/entity/player/Player.java` | `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793` |
| 1.15.2 | `net/minecraft/world/level/CollisionGetter.java` | `63daec0623c816d53f5dbdde407a27222fa21e62680c1096b63cf9082b1c25fd` |
| 1.15.2 | `net/minecraft/world/level/Level.java` | `39cbd69f51c58711a3072bce84c80adfddf19837dba3e9582b0d6315d53e62bf` |
| 1.15.2 | `net/minecraft/core/Cursor3D.java` | `a679c4938bfa717f1c6614c361ec9605c6d0ec6a1b764138c567f519066c6cfb` |
| 1.15.2 | `net/minecraft/world/level/block/state/BlockState.java` | `77320fd1e50e58d7a3ed593ccbcf21853c97d125fccebde2e0e305dc47524f21` |
