# Independent audit verdict: 1.10 → 1.10.1 water current

Verdict: **Accept the bounded no-difference result**, with a coverage note below. This is not full-pair acceptance or runtime-parity evidence.

## Frozen independent source roster

`audit-roster.json` was written before opening the author artifacts. SHA-256: `3d768c7602f248d823d165ff992d577de392350a82a0f520957cb144580d92c5`. It binds both `decompiled_minecraft/<version>/ornithe-feather/` roots to their `.decompile-provenance.json` digests and the paired source-file hashes. The manifest input records identify Feather `1.10+build.2` and `1.10.1+build.2`, with exact client and mapping artifact hashes. Each rostered source hash was recomputed and matched the manifest.

## Author artifact verification

- `comparison.md` SHA-256 matches the requested `22b0e8d3325a8c96c86694271a8dd9bb18b5931deeb91610a3fc16427914af86`.
- `source-roster.json` SHA-256 matches the requested `4aa1b97837a9ff3c6bd466a598cf607a0ea35ae497d6892a5b7ecf585ba74c24`.
- The JSON parses. Its shape has `pair`, `mappingFamily`, `sourceRoots`, and `checkedMembers`; roots are `older` and `newer`; it has 12 checked-member records. All six source entries under each root match the actual source SHA and the manifest `sources` SHA. Both manifest hashes also match the root manifests.
- The roster's checked-member equality claims agree with my paired source comparison. The file and method body hashes use different hash scopes; no canonical method-span hashing convention is declared in the JSON, so I did not treat those member digests as a portable extraction recipe.

## Paired trace

- `LocalClientPlayerEntity.tick` checks `world.isChunkLoaded(new BlockPos(x, 0.0, z))` then delegates to `super.tick()` (both releases, `LocalClientPlayerEntity.java:152–155`). `PlayerEntity.tick` delegates through `LivingEntity.tick`; the living/base tick chain reaches `Entity.baseTick`, whose `checkWaterCollisions()` call runs before that tick returns to living/player tick work (`Entity.java:304–310, 365–367`; `LivingEntity.java:208–211, 1525–1527`). The checked tick and caller ordering are equal.
- `Entity.checkWaterCollisions` skips its water scan when mounted on a boat; otherwise it calls `World.applyLiquidDrag` with `getShape().grown(0.0, -0.4F, 0.0).contract(0.001)`, then sets `inWater`, resets fall distance and clears fire timer on contact (`Entity.java:862–877`). This body is identical in both versions. Ordinary on-foot water current is therefore reached each local-player tick, including when not falling.
- `World.applyLiquidDrag` computes integer bounds as floor(min) and ceil(max), rejects when `isAreaLoaded(...)` fails, then scans x/y/z with each maximum exclusive. The loaded-area helper checks height only against `[0,256)` and checks chunk coordinates inclusively after shifting the integer x/z ends by four. For matching water cells, the top test is `ceil(maxY) >= y + 1 - LiquidBlock.getHeightLoss(level)`. Each accepted cell's flow is added in scan order; after the pooled mutable is released, the total is normalized only when its length is positive. It adds `normalized.x/y/z * 0.014` to velocity only when `entity.hasLiquidCollision()` is true (`World.java:1564–1605`). The paired operation is text-identical; the enclosing `World.java` files differ elsewhere.
- `LiquidBlock.getFlow` traverses `Direction.Plane.HORIZONTAL`; side-water depth contributes direction offsets scaled by neighbor depth minus the current depth. An open non-water side checks the cell below and uses `neighborDepth - (currentDepth - 8)`. For level `>= 8`, a solid side at the neighbor or above adds the downward bias `(0,-6,0)` to the normalized vector and stops that secondary scan. It returns a normalized per-cell vector; `applyMaterialDrag` adds it to the aggregate (`LiquidBlock.java:131–177`). The entire LiquidBlock source hash matches across the pair.
- `PlayerEntity.hasLiquidCollision()` is exactly `!this.abilities.flying` in both releases (`PlayerEntity.java:1735–1738`); flying suppresses only the final current impulse. The scan/contact return can still occur. This player predicate body matches, although its enclosing PlayerEntity files differ elsewhere.
- The author roster omitted `ClientWorld.isChunkLoadedAt`, `ClientChunkCache.getChunk`, and `EmptyChunk.getBlockState` from its evidence list. I checked these dependencies independently: the client-world gate returns `allowEmpty || !getChunk(...).isEmpty()`; the cache returns its empty-chunk fallback for absent chunks; `EmptyChunk.getBlockState` returns air. `LocalClientPlayerEntity`'s default `isChunkLoaded` call and the flow scan both pass `allowEmpty=true`. ClientWorld, ClientChunkCache and EmptyChunk source hashes match each side's provenance and each other. This omission does not change the no-difference verdict, but should be retained as an audit coverage note.

## Scope boundary

No 1.10 to 1.10.1 difference was found in the assigned ordinary water-current path: tick entry, caller box, scan and height gates, per-cell aggregation/normalization, client loaded-area behavior, or the nonflying impulse gate. I did not audit lava, falling-water special handling, eye/submersion predicates, other collision geometry, the complete player tick graph, or intermediate patch releases.
