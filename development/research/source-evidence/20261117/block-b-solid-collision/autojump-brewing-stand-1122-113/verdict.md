# Independent review: AutoJump brewing-stand witness

**Verdict: ACCEPT, source-only.** The claim is reachable and the stated floor-height-specific first-hit difference follows from the exact decompiled sources. This accepts only the reviewed witness; it does not establish full-pair discovery coverage or runtime parity.

## Reviewed artifact identity

The approved input `early-autojump-brewing-stand-witness/witness.md` has SHA-256 `502a93ed7fcdd065d20900d8d70103c37d87f245bea7ec463018e123d672fd53`, matching the assigned digest exactly.

For each exact Feather source tree, its ready marker pins the matching source-manifest and artifact-manifest SHA-256; I recomputed those manifest hashes, and each cited Java file's SHA-256 matches its source-manifest entry. Exact releases are 1.12.2, 1.13 and 1.13.2, each using Ornithe Feather gen2 build.2. The source files and their source-manifest evidence are therefore intact for this bounded review. The 1.12.2 and 1.13.2 provenance JSON files are shorter run summaries than the fuller 1.13 provenance JSON; their ready markers and artifact manifests supply the raw-client and mapping-artifact pins cited in the approved witness.

## Independent derivation

Use the approved arrangement: dry bottom slabs at `(-1,0,0)`, `(-1,0,1)` and `(0,0,0)`; brewing stand at `(0,1,0)`; player at `(-0.3,0.5,0.8)`, width `0.6`, height `1.8`, on ground. The two slabs at x=-1 support the player's initial AABB; the slab at `(0,0,0)` supports the stand's placement cell. The player's initial AABB is x `[-0.6,0]`, y `[0.5,2.3]`, z `[0.5,1.1]`: it is clear of the stand's x range, though the edges touch. Neither client requires a support predicate for this brewing stand placement: 1.12.2 inherits `Block.canBePlaced`'s replaceability check, and 1.13.2 inherits `Block.canBePlaced(...)=true` through `BlockState.canSurvive`. The slab top is y=0.5 in both.

Set raw input to `movementSideways=0`, `movementForward=1`, sprinting true, yaw `-90°`, pitch `0°`, AutoJump enabled, not sneaking/riding, and timer `0`. The look vector points +X. With the normal 0.1 base speed and sprint modifier amount 0.3, `f=0.13`.

After an ordinary +X move attempt, 1.12.2's full-width plate begins at x=0, so the player's initial maxX=0 clips the attempted move to `dx=0`; `dz=0`. The modern plate begins at x=1/16, so the same box can move to maxX=1/16: `dx=0.0625`, `dz=0`. The old AutoJump therefore takes its small-delta fallback: raw forward input and yaw produce +X, with `g=0.13²`; the modern branch normalizes actual +X displacement (`g=0.0625²`). Both produce probe reach `p=max(0.13*7,1/l)=0.91` and look dot `m=+1` (up to the source's fast-inverse-square-root rounding). Both pass enabled/timer/on-ground/not-sneaking/not-riding/nonzero-input and view-direction gates.

The post-move ray starts are y=`0.5+0.51F=1.01`, z=`0.5` and `1.1`. The z=0.5 ray runs in +X through the stand; the opposite ray at z=1.1 clears it. The old ray's x interval is `[-0.3,0.61]`; the modern ray's interval is `[-0.2375,0.735]`. The swept query includes the stand. The player-height block test floors `getShape().maxY` to y=2 (x=-1) and checks y=3; both are empty. Supporting bottom slabs end exactly at the query's minY=0.5 and do not become hit candidates.

In 1.12.2, `BrewingStandBlock.addCollisions` appends its stem (`x/z=0.4375..0.5625`, y=0..0.875) before its full-footprint plate (y=0..0.125). `World.getCollisions` preserves those append positions for this one obstacle. The z=0.5 probe at y=1.01 intersects both translated boxes, and the stem is the first match. Its maxY is 1.875; its center floors to block `(0,1,0)`, with `(0,2,0)` empty. The computed rise is `1.875-0.5=1.375`; the `rise > 1.2` early return rejects AutoJump.

In 1.13 and 1.13.2, `BrewingStandBlock.SHAPE` is `VoxelShapes.or(plate,stem)` with the plate `(1,0,1)..(15,2,15)/16` and stem `(7,0,7)..(9,14,9)/16`. `or` optimizes the union; `VoxelSet.forEachBox` enumerates the plate box first, then the central upper-stem box. The plate intersects the y=1.01 ray; the upper stem starts at world y=1.125 and cannot intersect it. The first match has maxY=1.125, center flooring to `(0,1,0)`, and empty `(0,2,0)`. The rise is `1.125-0.5=0.625`, satisfying `rise > 0.5 && rise <= 1.2`; the timer is set to 1. 1.13 uses the aggregate collision `VoxelShape`'s bounding boxes; 1.13.2 flattens the collision stream's shapes with `getBoundingBoxes()`. Both preserve the stand's optimized decomposition order for this sole obstacle, and the 1.13/1.13.2 BrewingStandBlock source hashes are identical.

The full-block-floor control also checks out. Put full blocks at the same support positions, keeping the stand at `(0,1,0)`, so the player feet are y=1. The probe y becomes 1.51. That ray misses the low plate and intersects the old stem / modern upper stem; each reports maxY=1.875 and rise `1.875-1=0.875`. The center again floors to `(0,1,0)` with empty `(0,2,0)`, so both clients arm the timer. The floor-level dependence is real.

## Evidence references

Paths are relative to `decompiled_minecraft/<version>/ornithe-feather/` unless noted.

- 1.12.2 `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:845-957`; `net/minecraft/block/BrewingStandBlock.java:35-73`; `net/minecraft/block/SlabBlock.java:20-43`; `net/minecraft/world/World.java:964-1020`.
- 1.13 `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:866-975`; `net/minecraft/block/BrewingStandBlock.java:32-47`; `net/minecraft/world/WorldView.java:112-186`.
- 1.13.2 `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:870-980`; `net/minecraft/block/BrewingStandBlock.java:31-47`; `net/minecraft/block/SlabBlock.java:27-60`; `net/minecraft/block/shape/VoxelShapes.java:96-129`; `net/minecraft/block/shape/VoxelShape.java:69-112`; `net/minecraft/block/shape/VoxelSet.java:200-251`; `net/minecraft/world/WorldView.java:112-170`; `net/minecraft/block/Block.java:562-564`; `net/minecraft/block/state/BlockState.java:301-302`.

No product code, implementation diffs, wiki material, backlog or Git history was used; no source was edited and no build, test, decompilation or runtime check was run.
