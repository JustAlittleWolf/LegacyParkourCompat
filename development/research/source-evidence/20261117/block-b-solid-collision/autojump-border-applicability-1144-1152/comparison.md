# AutoJump border applicability: 1.14.4–1.15.2

Scope: exact-source-only review of the AutoJump collision probe for an ordinary local player near a stationary rectangular world border. No product/wiki/backlog/Git inputs were used. No game, tests, build, launch, decompile, or geometry expansion was performed.

## Source identities

Both endpoints are exact version-root Mojmap source sets (Vineflower, Java 25); the checked source-file SHA-256 values below match their version-root `*.sources.sha256` manifest entries.

- 1.14.4: `decompiled_minecraft/1.14.4/mojmap`; ready marker source-manifest SHA-256 `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`; artifact-manifest SHA-256 `308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970`; provenance mapping option `feather,mojmap`, bundled mapping artifacts `feather-gen2-1.14.4+build.2-mergedv2.jar` and `feather-gen2-1.14.4+build.2.tiny`; ready client SHA-256 `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`. `mojmap.verification.json` states `verified-identical-source-and-input-artifacts` (3,250 source files, 0 source differences).
- 1.15.2: `decompiled_minecraft/1.15.2/mojmap`; ready marker source-manifest SHA-256 `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`; artifact-manifest SHA-256 `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406`; provenance exact run requests `--mappings=mojmap`; ready client SHA-256 `4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c`.

Per-file SHA-256 (all equal the corresponding manifest entries):

- 1.14.4 `client/player/LocalPlayer.java`: `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f`
- 1.14.4 `world/level/LevelReader.java`: `1f86a029e4404b91aa532ca33ab0dbb63b085334c561ce44e0d0875aa50fdbc9`
- 1.14.4 `world/level/border/WorldBorder.java`: `8fde5c95c74676020882be72011d8adb92787c8375ca1aa21ea1c52d1ebf2207`
- 1.14.4 `world/phys/shapes/VoxelShape.java`: `6a49550faa944196e16d55a8d28ca8b6cd49f1b75bb491ddc345b935ea0651b4`
- 1.14.4 `world/phys/shapes/Shapes.java`: `f1a2e60ae358236de7379e66543f99d474e3d431e42c5afbda688cb99b70985d`
- 1.14.4 `world/phys/AABB.java`: `82324c0e6a3d69e80424656e6098b39ce41f0f17a5e6efbe6542fe8eaca24a18`
- 1.14.4 `core/BlockPos.java`, `core/Vec3i.java`, `util/Mth.java`: `7c4c006a47c9644af99213c88860bac59765109efe8e084f266f1b413eb6c136`, `c3a2d3add563193ae9f361f0bebf796c6cc7d6627b8d693ddd477cca2da3854f`, `57e0babfbc9ce864e539c7632ebb72c2e14a208c2db1c3a1bfc16ee0237ae576`
- 1.15.2 `client/player/LocalPlayer.java`: `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd`
- 1.15.2 `world/level/CollisionGetter.java`: `63daec0623c816d53f5dbdde407a27222fa21e62680c1096b63cf9082b1c25fd`
- 1.15.2 `world/level/Level.java`: `39cbd69f51c58711a3072bce84c80adfddf19837dba3e9582b0d6315d53e62bf`
- 1.15.2 `world/level/LevelReader.java`: `d09aa8eaaa7f8ca8703091facb4d7e1a26c9f4bea8b99c5c8956682875241d37`
- 1.15.2 `world/level/border/WorldBorder.java`: `5030e47d299d2741339daae0274dab41e63ae1eb1468171ef14f181a71a03086f`
- 1.15.2 `world/phys/shapes/VoxelShape.java`: `5faceea0e4ce9ad0f2a1a2c72a89d9a768ab877c8728ae2968fe34aa1e03d3ea`
- 1.15.2 `world/phys/shapes/Shapes.java`: `81bf70d2c8a1d1336df72beedb30b0c9504f14decc9a06814a794aabf38203c2`
- 1.15.2 `world/phys/AABB.java`: `82324c0e6a3d69e80424656e6098b39ce41f0f17a5e6efbe6542fe8eaca24a18`
- 1.15.2 `core/BlockPos.java`, `core/Vec3i.java`, `util/Mth.java`: `c71cc6f248a5c929879dd22a818d270dc6c586900e3dce57301ba86aa3115950`, `dd4ad167ad9b4be90e9b13216e8b450b7bc401ca700732c2dd78970739942c7d`, `a842059e498147d368126c896f0036916a90bdd125a09b09f9fe6658f2aaf0a4`

## Findings

### Probe and gates

- 1.14.4 `LocalPlayer.move` calls `updateAutoJump` with actual post-move X/Z deltas (lines 844–851). 1.15.2 does the same using `getX/getZ` (851–857). In 1.14.4, AutoJump requires enabled, timer `<= 0`, on-ground, not sneaking, not passenger, and nonzero input movement (856–863). 1.15.2 moves the conditions into `canAutoJump`: enabled, timer `<= 0`, on-ground, not staying on ground surface, not passenger, moving, and block jump factor `>= 1` (962–970). Thus 1.15 adds/changes applicability gates independent of the border.
- Once admitted, both versions calculate the probe from the moved player position, use the actual horizontal delta unless its squared length is `<= 0.001F` (then derive a direction from input), extend by `max(speed*7, 1/n)`, and create the AutoJump query AABB from the path, player height, and width inflation (`1.14.4 LocalPlayer.java:862–905`; `1.15.2 LocalPlayer.java:864–905`). It then obtains `getCollisions(this, query, emptySet)`, flattens each shape with `toAabbs()` in stream/list order, and accepts the first AABB whose broad segment-AABB test intersects either side probe (`1.14.4:910–919`; `1.15.2:914–923`). It sets the timer only if the resulting rise is `> 0.5F` and `<= q` (`1.14.4:946–950`, measured from bounding-box minY; `1.15.2:950–954`, measured from player Y).

### Border enumeration and overlap gates

- In both versions `getCollisions` concatenates block collisions before entity collisions (`1.14.4 LevelReader.java:130–132`; `1.15.2 CollisionGetter.java:63–65`). At the start of `getBlockCollisions.tryAdvance`, before `Cursor3D`/block enumeration, a non-null entity gets one border check. It asks whether the border collision shape intersects the entity's actual bounding box deflated by `1.0E-7` and inflated by `1.0E-7`. The border is yielded only when the deflated overlap is false and inflated overlap true (`1.14.4 LevelReader.java:145–157`; `1.15.2 CollisionGetter.java:78–90`). If the inner/deflated box already overlaps, or even the inflated box does not, it is omitted. The check is against the entity's current box, not the AutoJump query AABB. So a query reaching a border while the player remains clear of the border collision shape does not bring that border into the list.
- The gate is a thin contact shell around the collision shape, not a general near-border check. Both WorldBorder implementations define the outside collision shape as `Shapes.INFINITY` minus a box bounded by floor(minX/minZ) and ceil(maxX/maxZ), with Y from negative to positive infinity (`WorldBorder.java:330–342` for moving extents and 410–422 for static extent in both versions). Therefore the relevant contact is with the rounded collision-shape boundary, which may differ from fractional configured coordinates.
- When yielded, the border is the first block-collision shape, ahead of every `Cursor3D`-visited block shape, and hence ahead of entity collisions. The border shape is not clipped to the AutoJump query AABB. `VoxelShape.toAabbs()` preserves the shape's `forAllBoxes` traversal order (`1.14.4 VoxelShape.java:92–114`, `1.15.2:93–115`). Border AABBs retain unbounded Y because their source shape has infinite Y limits. `AABB.intersects(Vec3,Vec3)` is an axis-aligned bounding-box overlap check on the two endpoints, not a first-contact ray cast (`AABB.java:208–220` in both versions).
- A bounded source witness for border inclusion is possible for an enabled, grounded, non-riding ordinary player with normal jump factor and clear headroom whose post-move bounding box is tangent to an integer-aligned border wall while input points outward. Collision clipping can leave actual movement delta near zero; the 1.14.4 fallback direction and 1.15.2 admitted moving-input branch still form an outward probe. The overlap shell can emit the synthetic border shape, and the probe-side segment bounds can overlap its outside AABB. However, it is not source-safe to conclude that this border hit cannot arm the timer: after `u=(float)maxY` becomes `+Infinity`, the same first-hit branch calls `aABB2.getCenter()`, constructs a `BlockPos`, and checks the block one position above it (both LocalPlayer loops around 922–937 / 926–941). Since border AABBs have infinite Y limits, `getCenter()` interpolates `-Infinity` to `+Infinity`, yielding NaN Y; `BlockPos(Vec3)` floors that NaN through `Mth.floor(double)`, which casts to integer zero. If the looked-up state has a collision shape, that shape can overwrite `u` with a finite top. Whether the resulting rise arms the timer therefore depends on the border AABB's x/z center, the state and chunk at that position, player Y, and the early-return check. This is an unresolved source gate for a full legal witness. If the center lookup is empty, `u` remains infinite and fails the timer range; the border still consumes the first-hit branch and prevents testing later collision AABBs. The subsequent `getBlockState` is a separate lookup path from collision enumeration: the latter uses `load=false`, while `Level.getBlockState` calls `getChunk(x,z)` without an explicit no-load flag. Whether that center chunk is available, and what state is found there, remains unresolved here. This is a source-bounded conditional witness for border inclusion only, not a runtime replay.

### Chunk and iteration behavior

- Border check runs before chunk lookups, so border insertion does not depend on whether a probe's neighboring chunks are loaded. After that, both enumerators traverse `Cursor3D`, skip type 3 cells, fetch a collision chunk with `load=false`, and emit a block shape only for allowed cursor types and nonempty query/block-shape intersection (`1.14.4 LevelReader.java:160–180`; `1.15.2 CollisionGetter.java:93–113`). For concrete client Levels the chunk policies are equivalent: 1.14.4 `Level.statusForCollisions()` returns `FULL` and passes `false` (`Level.java:878–880`, `LevelReader.java:168`); 1.15.2 `Level.getChunkForCollisions()` asks for `FULL,false` (`Level.java:754–757`). Null/unavailable chunks are skipped. The interface fallback differs (`EMPTY,false` in each era's reader interface), but the local player uses concrete client Level, which overrides it.

## Pair verdict for this bounded operation

Border gate, border shape construction, first stream position, AABB flattening, and the infinite-height result are source-equivalent across 1.14.4 and 1.15.2. No separate border-applicability delta is supported for this pair. The AutoJump admission guard and Y reference did change, but those are outside this border-specific applicability review. For a player merely near the border while the actual player box does not touch the rounded border collision shape's 1e-7 shell, the border is absent from the AutoJump candidate list even when the swept probe box extends through it.



