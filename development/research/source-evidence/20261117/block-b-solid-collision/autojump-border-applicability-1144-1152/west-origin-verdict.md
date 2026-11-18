# Independent source verdict: 1.14.4–1.15.2 border west-origin sample

**Verdict: ACCEPT, with the addendum's stated case bounds.** The source supports the corrected first-hit decomposition, the west-face loaded witness that arms `autoJumpTime`, and suppression for the centered south, north and east first-hit cases when their sampled block lookup returns the client's out-of-range empty chunk. This is not a border-specific movement delta between 1.14.4 and 1.15.2, and it does not establish behavior for corner paths or arbitrary borders/blocks.

## Immutable review binding

- Reviewed addendum: `review-1144-1152-autojump-border-applicability/addendum.md`, SHA-256 `c73f4d8f5da9b7507a8617f47f8553bdb41d97623aa22c8748b7b1ccd79287b0`.
- The addendum identifies the reviewed finding as SHA-256 `8161f41f5b750bc63ddb6d1ae2d4a6012a62d1e71fe3e79d43cc5bbc4112d1f7` and the prior verdict as SHA-256 `c1353ff8232aaaaa74a0d418390f9ddef7d25dbf343db296db50ec1e9e124566`.
- Exact sources: `decompiled_minecraft/1.14.4/mojmap` and `decompiled_minecraft/1.15.2/mojmap`. Both ready markers identify the exact version and `mojmap`; their source-manifest SHA-256 values are `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` and `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`. Provenance records a 1.14.4 run with `feather,mojmap`, and a 1.15.2 `mojmap` run. Relevant source-file hashes were checked against each source manifest.

## Independent derivation

In both `WorldBorder.java` versions, the collision shape is `INFINITY` minus the floored/ceiled interior border prism, with Y extending from negative to positive infinity. The result has three X coordinate intervals and three Z intervals, with the interior X/Z cell omitted. `VoxelShape.toAabbs()` uses `forAllBoxes(..., true)`; `DiscreteVoxelShape.forAllBoxes` scans X, then Y, then contiguous Z strips, greedily extending the middle-X south/north strips into the right X interval after it clears the first left-X strip. The resulting order is:

1. left/west: X `[-∞, floor(minX)]`, Y `[-∞,+∞]`, Z `[-∞,+∞]`;
2. south: X `[floor(minX),+∞]`, Y `[-∞,+∞]`, Z `[-∞,floor(minZ)]`;
3. north: X `[floor(minX),+∞]`, Y `[-∞,+∞]`, Z `[ceil(maxZ),+∞]`;
4. right/east: X `[ceil(maxX),+∞]`, Y `[-∞,+∞]`, Z `[floor(minZ),ceil(maxZ)]`.

This follows from the actual greedy scan and its clearing/extension operations, rather than an assumed four-wall decomposition. It also explains why the south/north AABBs do not have a bounded X interval.

`AABB.getCenter()` calls `Mth.lerp(0.5, min, max)` and double `Mth.lerp` evaluates `min + t * (max - min)`. Thus the west AABB produces NaN for X, Y and Z; `Mth.floor(NaN)` returns zero, so `new BlockPos(center).above(1)` samples `(0,1,0)`. South produces X `+∞`, Z NaN; north produces X and Z `+∞`; east produces X `+∞` and finite Z. `Mth.floor(+∞)` casts to `Integer.MAX_VALUE`. Each of those later first-hit probes therefore samples a position with X MAX, outside the client chunk cache range.

`Level.getBlockState` checks build height, then requests the chunk; it does not use the world-bounds predicate to reject these coordinates. The client chunk cache returns its `EmptyLevelChunk` for the load-enabled lookup, and that chunk returns `VOID_AIR`, whose collision shape is empty. Consequently those samples do not replace the border candidate's initial `u=+∞`; with normal jump range, the final `w > q` gate prevents setting the timer. This conclusion applies to the centered south/north/east face cases that first hit their corresponding slabs and do not also encounter the west slab.

The west witness is source-reachable. With border west edge X=0, player feet Y=1 and width 0.6, player AABB X `[0,0.6]`, Z `[-0.7,-0.1]`, support `(0,0,-1)`, and a full cube at `(0,1,0)`, the sample cube is adjacent in Z and clear of the player's box. `getBlockCollisions` checks the border before cursor blocks and emits it when the player's `1.0E-7` inflated box intersects while the deflated box does not. The first west AABB's height initially sets `u=+∞`; its center causes the lookup of `(0,1,0)`, whose local shape top produces `u=2`. At player feet/minY 1 and `q=1.2F`, the height is 1: it does not trip the `> q` early return and passes the final `w>0.5F && w<=q` checks. Both LocalPlayer implementations fall back to input direction when collision clipping makes actual horizontal displacement too small, so the outward side probe remains eligible in this witness.

## Evidence slices

- `WorldBorder.java`: 1.14.4 and 1.15.2 moving/static collision shape builders, lines 330–343 and 409–422.
- `Shapes.java`: `INFINITY`, `create`, `join`, and optimized shape construction; 1.14.4 lines 28–35, 51–86, 119–159; corresponding 1.15.2 methods.
- `VoxelShape.java`: `optimize`, `forAllBoxes`, `toAabbs`; `DiscreteVoxelShape.java`: `forAllBoxes`, lines 202–257. The latter file is byte-identical across the pair.
- `AABB.java`: `getCenter`, line 348; byte-identical across the pair. `Mth.java`: `floor(double)` line 50 and `lerp(double,...)` line 482 in 1.14.4 (corresponding 1.15.2 implementations checked).
- 1.14.4 `LevelReader.java` lines 134–185 and 1.15.2 `CollisionGetter.java` lines 67–115: border-first collision enumeration, epsilon shell and client/nonloading collision cursor behavior.
- Both `Level.java`, `ClientChunkCache.java`, `EmptyLevelChunk.java`, and `AirBlock.java`: lookup/fallback path for the MAX-coordinate samples.
- Both `LocalPlayer.java`: auto-jump center sampling, above-state loop, height gates and timer assignment; 1.14.4 lines 856–957 and 1.15.2 lines 862–960.

No product implementation, wiki inventory, backlog, Git history, build, test, decompile, or gameplay input was consulted or changed.
