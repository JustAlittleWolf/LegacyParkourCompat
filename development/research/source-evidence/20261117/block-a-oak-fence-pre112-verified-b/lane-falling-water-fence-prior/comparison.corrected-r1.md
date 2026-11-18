# Corrected rebind r1: falling-water fence cue and sampled bounds

This narrow rebind preserves the original `comparison.md` and the source availability note. It corrects only the unconditional outer-geometry equivalence claim. The independent audit verdict is `../lane-falling-water-fence-prior-audit/verdict.md`, SHA-256 `ab93268fad063c7365f86db3ead3a87fd5f0a0b1d2725a5367422787043482d8`. Exact sources remain the ready canonical `decompiled_minecraft/<release>/ornithe-feather` trees for 1.8.9, 1.9.4, 1.10.2 and 1.11.2.

## Same per-cell fence and flow predicate

For a falling water cell `p` with `LEVEL >= 8`, each inspected release runs `LiquidBlock.getFlow` over horizontal directions. It first reads the same-height side cell; if its liquid depth is negative and its material does not block movement, it reads exactly `side.down()`. With falling center depth `i = 0` and one lower horizontal water cell at level 0 (`j = 0`), that lower cell adds raw flow `8 * dir`. The same-height side cell is air in the stated arrangement.

For a dry default oak fence at `p.offset(dir).up()`, the falling-level face loop checks `side` then `side.up()`. The first check is false for air; the second is true. `LiquidBlock.isFaceSolid` delegates a horizontal face on WOOD material to `Block.isFaceSolid`, which checks `Material.isSolid()` without consulting the fence collision shape or cube predicate. Oak `FENCE` is registered as `FenceBlock(Material.WOOD, ...)`; default WOOD material is solid. This face result is the same in all four source versions. Fence connection bits do not participate in this fluid-flow predicate.

With one lower level-0 water neighbor and no other contributing liquid cells, the raw vector is `8 * dir`. The falling-level branch normalizes it to `dir`, adds `(0,-6,0)`, then normalizes, giving `(dir.x/√37, -6/√37, dir.z/√37)`. With no horizontal contribution, `Vec3d.normalize()` maps zero to zero; the downward addition and final normalization produce `(0,-1,0)`. The World caller normalizes its accumulated vector and, when `entity.hasLiquidCollision()` is true, adds the components times `0.014` to entity velocity. For PlayerEntity this predicate is `!abilities.flying`. These are source-derived cues, not simulated trajectories.

The same per-cell predicate, probe order and normalized results are present in 1.8.9, 1.9.4, 1.10.2 and 1.11.2. This statement applies only when the central falling-water cell is reached by the caller’s block loop and passes its liquid-height gate; the optional lower neighbor and upper fence are then read by `getFlow`.

## Different outer current-cell range at integer maxima

LocalClientPlayerEntity reaches `Entity.checkWaterCollisions` on its ordinary on-foot tick after its center-chunk `isChunkLoaded` check. The player supplies `getShape().grown(0,-0.4F,0).contract(0.001,...)` to `World.applyLiquidDrag`; 1.8.9 uses the three-axis overload and later versions use the scalar overload. The exact Box sources show that contraction moves each maximum inward by the supplied amount: 1.8.9 `contract(dx,dy,dz)` sets each max to `max - d`; 1.9.4 onward `contract(amount)` delegates to `expand(-amount)`, then `grown`, which sets each max to `max + (-amount)`.

The World routine computes an exclusive upper loop index and passes that same index to `isAreaLoaded(..., true)` before examining current liquid cells:

| Release(s) | Exclusive upper index for each axis | Result for contracted maximum `M = 1.0` |
|---|---|---|
| 1.8.9 | `floor(bounds.max + 1.0)` | `floor(2.0) = 2`; the `< 2` loop can sample block coordinate 1. |
| 1.9.4, 1.10.2, 1.11.2 | `ceil(bounds.max)` | `ceil(1.0) = 1`; the `< 1` loop does not sample block coordinate 1. |

For nonintegral contracted maximum `M`, these expressions agree (`floor(M + 1) = ceil(M)`). For integral `M`, they differ by one. The `M = 1.0` witness applies independently to X, Y and Z. Since `isAreaLoaded` receives those computed bounds too, its requested region can differ at the same edge; no equality of loaded-area gates is claimed there. The player path’s contraction does not establish that every resulting maximum is nonintegral.

Consequently, if a current falling-water cell is present only at one of the extra old endpoint coordinates and passes the 1.8.9 liquid-height test, 1.8.9 can accumulate its flow while 1.9.4 and later do not sample that cell. If the relevant central cell lies in the ranges actually sampled by both versions, and the loaded-area and height gates pass, the per-cell fence cue above is equal. Whether ordinary gameplay places the LocalClientPlayer bounds at the exact integer witness was not checked.

## Exact source bindings

Paths are relative to each release’s `ornithe-feather/` tree; line ranges are inclusive.

| Operation | 1.8.9 | 1.9.4 | 1.10.2 | 1.11.2 |
|---|---|---|---|---|
| Flow, lower-neighbor read, falling-level face probes, material-drag dispatch | `net/minecraft/block/LiquidBlock.java`, `getFlow` 129–161; `applyMaterialDrag` 164–166 | `LiquidBlock.java`, `getFlow` 131–172; `applyMaterialDrag` 175–177 | same as 1.9.4 | same as 1.9.4 |
| Liquid and base face predicate | `LiquidBlock.java`, `isFaceSolid` 74–83; `net/minecraft/block/Block.java`, `isFaceSolid` 329–331 | `LiquidBlock.java` 82–91; `Block.java` 327–329 | `LiquidBlock.java` 82–91; `Block.java` 332–334 | `LiquidBlock.java` 83–92; `Block.java` 343–345 |
| Oak fence and WOOD material | `net/minecraft/block/Block.java`, oak fence registration 1016–1020; `net/minecraft/block/material/Material.java`, `WOOD` 7, `isSolid` 60–62 | `Block.java` 917–925; `Material.java`, `WOOD` 9, `isSolid` 62–64 | `Block.java` 922–930; `Material.java`, `WOOD` 9, `isSolid` 63–65 | `Block.java` 927–935; `Material.java`, `WOOD` 9, `isSolid` 63–65 |
| Zero and nonzero vector normalization | `net/minecraft/util/math/Vec3d.java`, `normalize` 34–37 | `Vec3d.java`, `normalize` 37–40 | same as 1.9.4 | same as 1.9.4 |
| Local-player entry and entity water call | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `tick` 104–115; `net/minecraft/entity/Entity.java`, `tick` 299–304 and `checkWaterCollisions` 746–760; `net/minecraft/entity/living/player/PlayerEntity.java`, `hasLiquidCollision` 1617–1619 | `LocalClientPlayerEntity.java`, `tick` 142–157; `Entity.java`, `tick` 360–365 and `checkWaterCollisions` 849–865; `PlayerEntity.java`, `hasLiquidCollision` 1717–1719 | `LocalClientPlayerEntity.java`, `tick` 151–165; `Entity.java`, `tick` 365–370 and `checkWaterCollisions` 862–878; `PlayerEntity.java`, `hasLiquidCollision` 1736–1738 | `LocalClientPlayerEntity.java`, `tick` 158–173; `Entity.java`, `tick` 378–383 and `checkWaterCollisions` 927–943; `PlayerEntity.java`, `hasLiquidCollision` 1736–1738 |
| World current-cell bounds and flow velocity | `net/minecraft/world/World.java`, `applyLiquidDrag` 1486–1526 | `World.java`, `applyLiquidDrag` 1560–1602 | `World.java`, `applyLiquidDrag` 1570–1612 | `World.java`, `applyLiquidDrag` 1571–1613 |
| Player-shape contraction | `net/minecraft/util/math/Box.java`, `contract(dx,dy,dz)` 176–184 | `Box.java`, `grown(dx,dy,dz)` 100–108; `expand(amount)` 110–112; `contract(amount)` 222–224 | `Box.java`, `grown(dx,dy,dz)` 104–112; `expand(amount)` 114–116; `contract(amount)` 237–239 | `Box.java`, `grown(dx,dy,dz)` 132–140; `expand(amount)` 142–144; `contract(amount)` 279–281 |

## Corrected disposition

The fence-driven per-cell flow cue is source-equal across the four inspected releases when that liquid cell is sampled. The old and newer LocalClientPlayer caller bounds are not unconditionally equivalent: the outer current-cell loop and its `isAreaLoaded` request differ at any integer contracted maximum, with `M = 1.0` as an exact witness. No WallBlock behavior, uninspected release, intermediate patch boundary or runtime reachability of the integer witness is asserted here.