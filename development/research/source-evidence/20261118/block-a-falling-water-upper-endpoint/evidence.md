# Falling-water current at the old liquid-scan upper endpoint

## Bounded claim

In the exact 1.8.9 and 1.9 Feather sources, a falling-water cell at the contracted player-box maximum integer can contribute current in 1.8.9 and is not visited in 1.9. The same-cell oak-fence cue is present in both releases. This composes the previously accepted outer-boundary and sampled-cell findings for one dry ordinary oak fence arrangement. It does not establish that ordinary gameplay naturally produces the fractional player state below, nor does it extend the fence claim to other materials.

## Exact call-boundary fixture

Use a stationary, non-mounted `PlayerEntity` at the `checkWaterCollisions` call boundary, with zero velocity, `abilities.flying == false`, center X/Z `(0.2, 0.5)`, and the ordinary 1.8F player height. Set its lower Y to `62.60100005364418`. In Java double evaluation, the source's `getShape().grown(0.0, -0.4F, 0.0).contract(0.001...)` yields contracted maximum Y exactly `64.0`:

- `y + (double)1.8F` is the uncontracted top;
- adding `(double)-0.4F` gives `64.001`;
- subtracting `0.001` gives exactly `64.0`.

With source width `0.6F`, the contracted X range is approximately `[-0.099, 0.499]`; the contracted Z range is approximately `[0.201, 0.799]`. In both World versions the X loop is `-1, 0` and Z loop is `0`: 1.8.9 has `floor(minX)=-1`, `floor(maxX+1)=1`, while 1.9 has `floor(minX)=-1`, `ceil(maxX)=1`; Z upper indices are 1 on both. Thus the falling cell at X=0 and lower water at X=-1 are both horizontally sampled by both scans. The 1.8.9 client-player tick checks `world.isChunkLoaded(new BlockPos(x, 0.0, z))` before `super.tick`; 1.9 has the same gate. Load the center chunk (0,0) and the touched X/Z chunks (-1,0) and (0,0) for the area check. The fixture is a call-boundary source witness; it does not claim a stable full tick or natural ground support at this fractional Y.

Set the only local liquid arrangement as follows, with `D = WEST`:

- falling water `LEVEL=8` at `p=(0,64,0)`;
- air at `p.offset(D)=(-1,64,0)`;
- level-0 water at `p.offset(D).down()=(-1,63,0)`;
- one dry default oak fence at `p.offset(D).up()=(-1,65,0)`;
- no other liquids and no other qualifying falling-flow faces in scanned cells.

The lower level-0 cell at `(-1,63,0)` is inside both X and Y scans and passes the liquid-height gate in both. It therefore makes `applyLiquidDrag` return true on both versions; this fixture isolates a flow-velocity contribution difference, not water-contact/inWater admission. Its own horizontal reads are the side cells `(-2,63,0)`, `(0,63,0)`, `(-1,63,-1)`, and `(-1,63,1)`, all air, so its flow is zero; it does not reach the falling cell by a horizontal query. The upper fence and same-height air cell are reads by `getFlow`, not current cells added to the World aggregate. Other blocks may be ordinary air. Keep the player non-flying and all above-mentioned chunks loaded.

## Paired source trace

1.8.9 `Entity.checkWaterCollisions` passes `getShape().grown(0.0, -0.4F, 0.0).contract(0.001, 0.001, 0.001)` to `World.applyLiquidDrag`. That method computes `l = floor(bounds.maxY + 1.0)`, giving `65`, and loops `p < l`, so Y=64 is visited. It calls the area-loaded gate before the loops. At Y=64, `LEVEL=8` is normalized by `LiquidBlock.getHeightLoss` to state 0 and `(0+1)/9.0F`; with its `double d = p + 1 - getHeightLoss(...)`, the liquid-height check `l >= d` passes (`65 >= 65 - 1/9F`).

1.9 `Entity.checkWaterCollisions` passes the same grown box with scalar `contract(0.001)`. `Box.contract(amount)` delegates to `expand(-amount)`. `World.applyLiquidDrag` computes `l = ceil(bounds.maxY)`, giving `64`, and loops `p < l`; Y=64 is not read, so neither its height gate nor its `getFlow` is run. The lower level-0 cell at Y=63 remains in the range on both sides.

For the included falling cell, both `LiquidBlock.getFlow` versions check horizontal neighbors in `Direction.Plane.HORIZONTAL` order. For each direction, they first read the same-height side cell. In the selected direction it is air, so its material does not block movement; the next liquid-depth read is exactly `side.down()`, which is level 0. The center liquid depth is 0 because `getLiquidDepth` maps levels `>=8` to 0. Thus `k = j - (i - 8) = 0 - (0 - 8) = 8`, and the raw vector becomes `8 * D` (all other directions contribute zero).

The falling-level test then examines, for each direction, `isFaceSolid(side, direction) || isFaceSolid(side.up(), direction)` in that short-circuit order. In the selected direction, side air is false and the above-side default oak fence is true through the legacy WOOD material face predicate. The cue normalizes the raw vector, adds `(0,-6,0)`, then normalizes again. It does not use fence collision geometry or connection bits. In 1.9, the components are accumulated as doubles and materialized as `Vec3d` before the same normalization sequence; 1.8.9 accumulates immutable `Vec3d.add` results in direction order. `Vec3d.normalize` in both uses `MathHelper.sqrt(x*x+y*y+z*z)` and returns zero only below its small-length threshold. No hard-coded square-root result is assumed.

In `World.applyLiquidDrag`, the included falling-cell vector is added to the running aggregate after earlier scanned cells; the level-0 neighbor's vector is zero. World normalizes the completed aggregate only after the scan, checks its positive length and `entity.hasLiquidCollision()`, then adds its components multiplied by `0.014` to velocity. For the player, `hasLiquidCollision()` is exactly `!abilities.flying` on both sides. This establishes an old-only contribution at the operation boundary, not a simulated trajectory.

## Bounded oak-fence provider reach

Exact 1.8 `LiquidBlock.getFlow` has the same falling `LEVEL >= 8` side-then-above short-circuit, and `LiquidBlock.isFaceSolid` falls through to base `Block.isFaceSolid`; vanilla oak fence is registered as `FenceBlock(Material.WOOD)`. This matches the 1.8.9/1.9 cue for this material. Exact 1.12.2 retains the falling branch and side/above order but its `isFaceSolid` requires `FaceShape.SOLID` after its exception checks; `FenceBlock.getFaceShape` returns `MIDDLE_POLE` for horizontal faces, so this oak cue is false there. Together with the permitted accepted companion, this bounds only the ordinary-oak-fence cue through the inspected pre-1.12 source set and its later face-shape cutover; no other solid material is inferred.

## Source bindings

All paths below are relative to `decompiled_minecraft/<release>/ornithe-feather/`.

- 1.8.9: `net/minecraft/entity/Entity.java`, `checkWaterCollisions` 746–760; `net/minecraft/world/World.java`, `applyLiquidDrag` 1486–1526; `net/minecraft/block/LiquidBlock.java`, `getHeightLoss` 41–47, `getLiquidDepth` 53–57 and `getFlow` 129–165; `net/minecraft/util/math/Box.java`, `contract` 176–184; `net/minecraft/util/math/Vec3d.java`, `normalize` 34–37; `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `tick` 104–115; `net/minecraft/entity/living/player/PlayerEntity.java`, `hasLiquidCollision` 1617–1619; `net/minecraft/block/LiquidBlock.java`, `isFaceSolid` 74–83; `net/minecraft/block/Block.java`, oak fence registration 1016–1020.
- 1.9: `net/minecraft/entity/Entity.java`, `checkWaterCollisions` 845–861; `net/minecraft/world/World.java`, `applyLiquidDrag` 1554–1596; `net/minecraft/block/LiquidBlock.java`, `getHeightLoss` 47–53, `getLiquidDepth` 59–63 and `getFlow` 128–173; `net/minecraft/util/math/Box.java`, `grown` 100–105, `expand` 108–110, `contract` 220–222; `net/minecraft/util/math/Vec3d.java`, `normalize` 35–40; `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `tick` 140–155; `net/minecraft/entity/living/player/PlayerEntity.java`, `hasLiquidCollision` 1710–1712; `net/minecraft/block/LiquidBlock.java`, `isFaceSolid` 82–91; `net/minecraft/block/Block.java`, oak fence registration 917–925.
- 1.8: `net/minecraft/block/LiquidBlock.java`, `isFaceSolid` 74–82 and `getFlow` 129–165; `net/minecraft/block/Block.java`, oak fence registration 936–937 and base `isFaceSolid` 282–284; 
- 1.12.2: `net/minecraft/block/LiquidBlock.java`, `isFaceSolid` 82–100 and `getFlow` 139–179; `net/minecraft/block/FenceBlock.java`, `getFaceShape` 202–203; `net/minecraft/block/Block.java`, oak fence registration 947–954.

## Evidence identity

The three assigned evidence inputs were verified against their specified SHA-256 values:

- `water-upper-bound-liquid-admission.md`: `a82ce04764f3d0c5f2155c0fda86b0cf29aa460d39be2ac3bd296baef32cd330`.
- `comparison.corrected-r1.md`: `0e9bebe9aba84ae1b6d4fc8f9e319b064092eb727e4e808cbaddc4229df67846`.
- `falling-water-wall-face-solidity-boundary.md`: `f7a69e9ac847e7a9e844fa685424d839eb4741c83bfc1984b6dade021cdaa7e1`.

The exact 1.9 Feather build.2 provenance manifest is present. It pins the Feather mergedv2 artifact to `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a`, the 1.9 client to `0273b5c79d6b96799c6480ae61366d6b1d042655970de87f77f1a3f1ebea9307`, and lists source hashes including `World.java` `d1e23db2e20c86eb37b964a0df6838f1422ade7b0c2fff9c869ff8345979aa98` and `LiquidBlock.java` `7e76c5ebb1a59d36be3517d09e5df60a0eb206ff5ace5ce889eac9152b53c6e8`.

The exact flat 1.8.9 root is ready for version `1.8.9`, mapping `ornithe-feather`, with mapping jars `feather-gen2-1.8.9+build.2-mergedv2.jar` and `feather-gen2-1.8.9+build.2.tiny`. Its ready marker pins `ornithe-feather.sources.sha256` at `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; the source-manifest file hash matches, and the current World, LiquidBlock, Vec3d, Entity, PlayerEntity, LocalClientPlayerEntity, and Block files all match their manifest entries. The provenance run identity records exact version 1.8.9 and Feather build.2. Ready marker SHA-256: `fe0ec792349ae43c91d0a30f23660c94b2f5c8d31a29643f73ed0de367110b95`; provenance SHA-256: `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`.

The exact flat 1.12.2 root is likewise ready for version `1.12.2`, mapping `ornithe-feather`, with mapping jars `feather-gen2-1.12.2+build.2-mergedv2.jar` and `feather-gen2-1.12.2+build.2.tiny`. Its ready marker pins `ornithe-feather.sources.sha256` at `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; the source-manifest file hash matches, and current World, LiquidBlock, Vec3d, Entity, PlayerEntity, LocalClientPlayerEntity, Block, and FenceBlock files all match their manifest entries. The provenance run identity records exact version 1.12.2 and Feather build.2. Ready marker SHA-256: `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a`; provenance SHA-256: `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce`.

No source was generated or modified in the product checkout. No build, test, game launch, runtime simulation, or backlog write was performed.

## Next gate

An independent reviewer should confirm the composed claim and the exact-call-boundary fixture. The 1.8.9 and 1.12.2 flat readiness/provenance/source identities are now confirmed. An independent reviewer should check the corrected X-centered fixture and the natural in-game reachability gate before treating this as a stable player-tick scenario.





