# WORLD-06: liquid-drag scan excludes exact upper-bound cells in 1.9.4

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/world/World.java`, `public boolean applyLiquidDrag(Box bounds, Material material, Entity entity) {`, lines 1486-1527.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/world/World.java`, `public boolean applyLiquidDrag(Box bounds, Material material, Entity entity) {`, lines 1560-1602.

Difference: 1.8.9 derives each exclusive loop end as `MathHelper.floor(bounds.maxAxis + 1.0)` for X/Y/Z. 1.9.4 derives it as `MathHelper.ceil(bounds.maxAxis)`.

For non-integer maxima these values match; at an exact integer maximum 1.8.9 visits the cell at that coordinate while 1.9.4 excludes it. 1.8.9 and 1.9.4 use the same lower bound `floor(bounds.minAxis)`.

Both then filter candidate blocks by material and the liquid-height test before setting `bl` and invoking `Block.applyMaterialDrag()`; both normalize and add the resulting vector at scale `0.014` when `entity.hasLiquidCollision()`.

The bounding box comes from the player/entity shape grown by `(0.0, -0.4F, 0.0)` and contracted by `0.001` on each axis. 1.8.9 `Box.contract(double,double,double)` adds each amount to minima and subtracts it from maxima; 1.9.4 `Box.contract(double)` delegates to symmetric expansion with the negative amount, so the input bounds otherwise match.

Movement meaning: Local player tick -> inherited `Entity.tick()` -> `checkWaterCollisions()` -> `World.applyLiquidDrag()` -> liquid block's `applyMaterialDrag()`/`getFlow()` -> possible player velocity write.

The normal, non-boat route is present in both endpoints; 1.9.4's boat early branch is documented separately in `WORLD-05`. 1.8.9 player not flying passes `PlayerEntity.hasLiquidCollision()` in both versions.

Concrete boundary precondition: at least one contracted box maximum is an exact integer and the world cell at that coordinate is water with its liquid surface meeting the scan's vertical bound. 1.8.9 scans that cell; 1.9.4 does not.

Source proves the differing upper-bound calculation and the one-cell inclusion rule at exact integer maxima.

If the included cell passes the liquid material/height filter, 1.8.9 can set the water result and invoke its liquid drag callback for a cell 1.9.4 omits.

Original evidence: [WORLD-06-liquid-scan-upper-bound.md](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-06-liquid-scan-upper-bound.md).
