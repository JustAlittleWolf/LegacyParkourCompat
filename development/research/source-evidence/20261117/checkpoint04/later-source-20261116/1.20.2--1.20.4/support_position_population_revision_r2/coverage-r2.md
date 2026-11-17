# 1.20.2 → 1.20.4 support-position producer coverage

## Verdict and scope

**Bounded NO_DIFF** for the source path that populates `Entity.mainSupportingBlockPos` from a grounded entity's collision support query. The setter/writer order, query volume, collision-shape candidate test, nearest-position selection and fallback/clearing gates match at both endpoints. This closes only the static producer/query dependency for the already-covered `getOnPos` support selectors. It does not establish block-provider values, resource membership, ordinary-world state distribution, pair-wide coverage or runtime parity.

The reused, independently accepted `Entity.move` collision-kernel report establishes the ordinary local-player call path: `LocalPlayer` → inherited player travel → `Entity.move` (`entity_move_collision/coverage.md`, SHA-256 `ED10266FEDB016D3BC1B2458723E5B0DB2A42C1A24909D04431AB8F09DC1C4D0`; audit `entity_move_collision_audit/verdict.md`, SHA-256 `B937CBC446A3AE58DE99889064EE8B3C1D93EDC806979A67BDCC644B03C2A77B`). That report's kernel claim is reused unchanged, not re-audited here.

## Exact source identities

Both endpoints are ready Mojmap publications. Sidecars are at each version root; the source trees are under `mojmap/`.

| Release | Ready SHA-256 | Provenance SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 |
| --- | --- | --- | --- | --- |
| 1.20.2 | `5ff3322f287c250ee914c7ca3840d053bffc3c2c5f35e91b59314d948292483f` | `1dad9f725f6cb25b6568700fa0294f83b66ea9633b0d62ed354d7bd8b45d4f05` | `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887` | `ee67da9733953b30ae4077c587184e59772b745d780abd27a7b023780b459f54` |
| 1.20.4 | `6225b97979420cc743c8b7156a0ce512ef27d0dbf48bfffbc40d3e1c8cb044d1` | `10914d9b7b3fc29de066ca24f7d905e4d4f0a451a6c81729a2d1981addfc1385` | `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` | `ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948` |

Ready markers report `status=ready`, their exact version IDs and `mapping=mojmap`; source/artifact manifest hashes match the on-disk files. Each cited Java hash below matches the corresponding source manifest entry.

## Ordered writer and query path

1. After `Entity.move` resolves position and collision, it computes `verticalCollisionBelow = verticalCollision && requestedMovement.y < 0`, then calls `setOnGroundWithKnownMovement(verticalCollisionBelow, clippedMovement)` (1.20.2 `Entity.java:635–643`; 1.20.4 `:636–644`). The exact call sequence is unchanged.
2. `setOnGroundWithKnownMovement` writes `onGround` first, then calls `checkSupportingBlock(onGround, clippedMovement)`. `setOnGround` uses the same order with a null movement argument; its body is also equal (1.20.2 `Entity.java:555–563`; 1.20.4 `:556–564`).
3. When grounded, `checkSupportingBlock` makes a thin query AABB with the entity's current X/Z bounds and Y interval `[bbox.minY - 1.0E-6, bbox.minY]`. It calls `level.findSupportingBlock(entity, queryBox)`. If that returns a position, or the prior `onGroundNoBlocks` flag is true, it stores the first result. Otherwise, if a movement vector is available, it moves the query AABB backward by the clipped X/Z movement and queries again, storing the fallback result. It then sets `onGroundNoBlocks` from the final optional's emptiness. When not grounded it clears `onGroundNoBlocks` and empties `mainSupportingBlockPos` if present. The branch order, tiny offset, fallback query and optional writes match exactly.
4. `Level` inherits the query as an interface default: `Level → LevelAccessor → CommonLevelAccessor → LevelReader → CollisionGetter`. Those declarations are the same across releases. `CollisionGetter.findSupportingBlock` (line 105 onward) creates `BlockCollisions` with the entity context, `onlySuffocatingBlocks=false`, and a mapper that returns the candidate block position. It exhausts candidates and chooses the block position whose center has the smallest squared distance to `entity.position()`. On equal distances it replaces the current candidate when `current.compareTo(candidate) < 0`, so the candidate with the greater `compareTo` order wins. `BlockPos` inherits this exact ordering from `Vec3i.compareTo` (1.20.2 and 1.20.4 `Vec3i.java:54–59`, SHA-256 `36db6e4b3001c2ea4e5c218ba601d47b76a91e60455f9088b1db4893cef623c3` both endpoints): compare Y first, then Z, then X, each by coordinate subtraction. The selected mutable position is copied with `immutable()` before it is returned.
5. `BlockCollisions.computeNext` (line 65 onward) visits the cursor cells around the query AABB. It skips unavailable chunks and cursor type 3; cursor type 1 additionally requires `hasLargeCollisionShape()`, while cursor type 2 additionally requires the state to be `MOVING_PISTON`. It obtains each surviving state's context-sensitive **collision** shape, then returns a position only when the full-block fast path intersects the query AABB or a nonempty partial shape intersects it under `BooleanOp.AND`. Since `onlySuffocatingBlocks` is false, support selection does not apply the suffocation predicate. This provider/query body is byte-identical across endpoints.
6. The consumer remains the previously checked exact selector: `getBlockPosBelowThatAffectsMyMovement()` uses `getOnPos(0.500001F)`; `getOnPos` prefers the recorded support position, applying its same fence/wall/gate Y rules. `getBlockJumpFactor` evaluates both in-entity and support factors before selecting; the previously accepted support-position report already covers that selector. This report adds the producer/query side only.

`checkSupportingBlock` has one persistence case worth preserving: if grounded, the first query is empty, `onGroundNoBlocks` is false, and the setter was called with null movement, the code does not assign or clear the existing `mainSupportingBlockPos`; it only updates `onGroundNoBlocks` from the empty query. This branch also matches at both endpoints.

## Other writers / limits

A scoped source-tree search found the `mainSupportingBlockPos` field and its assignments only in `Entity`; the debug support renderer is a reader. The player-specific direct `setOnGround(false)` caller is the spectator branch in `Player.tick`, which routes through the same helper and clears support while also setting `noPhysics`; it is not the ordinary collision-support path. Server-side correction/setter calls and non-player callers do not establish additional historical local-player writers and are outside this client simulation slice.

The query considers only the collision shapes and loaded collision chunks available to `CollisionGetter`; which particular registered block states satisfy it is outside this slice. `onGroundNoBlocks` persistence and the fallback are source facts, not claims about placement or world-generation reachability. Other direct position/support producers, data/tag inputs, intermediate releases, remaining movement operations, full-pair dependency closure, and runtime parity remain open.

## Exact cited Java-file SHA-256 values

| Relative source path | 1.20.2 SHA-256 | 1.20.4 SHA-256 |
| --- | --- | --- |
| `net/minecraft/world/entity/Entity.java` | `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` |
| `net/minecraft/world/entity/player/Player.java` | `25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` |
| `net/minecraft/world/level/CollisionGetter.java` | `f21daa1ed0365cc16a2638373d702fbaeb4242ad12a80a41105cf43c711e6d95` | `f21daa1ed0365cc16a2638373d702fbaeb4242ad12a80a41105cf43c711e6d95` |
| `net/minecraft/world/level/BlockCollisions.java` | `3313a38f9ebbd389f62e95dcbedab6a4f7f3cd3920b9ffa0ecf1a77dd869725b` | `3313a38f9ebbd389f62e95dcbedab6a4f7f3cd3920b9ffa0ecf1a77dd869725b` |
| `net/minecraft/world/level/Level.java` | `c13ead6630a8f77529574afcd6009b0395698489ff8ad6fdf75b0e745c65f61c` | `d8c1d41b53836a1c2e19de3ab6078febca82707c0c49e2e595a1525384a45143` |
| `net/minecraft/world/level/LevelReader.java` | `ec8c1f883dc1a71d2666f57cf7e124298272ae8b483770d32ecdb32c385b0bdd` | `ec8c1f883dc1a71d2666f57cf7e124298272ae8b483770d32ecdb32c385b0bdd` |
| `net/minecraft/world/level/CommonLevelAccessor.java` | `c0bdcf73ff5029f6e0da4c1019e45973cd543b992818b19bca843a737dd416b4` | `c0bdcf73ff5029f6e0da4c1019e45973cd543b992818b19bca843a737dd416b4` |
| `net/minecraft/world/level/LevelAccessor.java` | `43140fde2da53df2d87e97b7ac9c47b062313a0032c8c6efa7121b44200b0220` | `43140fde2da53df2d87e97b7ac9c47b062313a0032c8c6efa7121b44200b0220` |
| `net/minecraft/core/Vec3i.java` | `36db6e4b3001c2ea4e5c218ba601d47b76a91e60455f9088b1db4893cef623c3` | `36db6e4b3001c2ea4e5c218ba601d47b76a91e60455f9088b1db4893cef623c3` |

No canonical research/backlog/product files, sources, or Git state were modified. No build, tests, runtime, or source generation was run.
