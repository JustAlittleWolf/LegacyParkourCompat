# liquid-drag scan excludes exact upper-bound cells in 1.9.4

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


The subsequent exact 1.9 comparison and 1.9–1.9.4 alias audit establish the first changed stable release as 1.9. The 1.8.9 water-current component is implemented within the supported nonfalling-water/material bundle; its old Y-overlap admission and direct empty-chunk reads do not inherit the modern full-chunk preflight. Current is applied once before water contact effects. The historical body-contact Boolean is a separate operation. Source comparisons, witness audits and client area-gate closure are retained in `development/research/source-evidence/20261028/water-flow-scan/`. No runtime parity is asserted.

## Exact nonfalling water-current witness

With a non-flying, non-riding standing player at `x=z=0.5` and `y=62.60100005364418`, the symmetric `-0.4F` Y adjustment and subsequent `0.001` contraction yield a maximum Y of exactly `64.0`. Place level-1 water at `(0,64,0)` and source water at `(1,64,0)`, with the other relevant cells empty and the area loaded. Exact 1.8.9 scans the level-1 cell, admits its liquid height and applies the westward current impulse `(-0.014,0,0)`. Exact 1.9 excludes the cell before its height check and contributes zero. The inspected 1.9 patches and 1.10.2 retain that new bound. This witness establishes the direct current contribution and leaves the separate body-contact, boat, falling-face and later movement paths outside its verdict.

[Exact finding](../../source-evidence/20261117/block-a-early-fluid-bounds/water-upper-bound-liquid-admission.md), [source comparison](../../source-evidence/20261117/block-a-early-fluid-bounds/water-comparison.md), [independent verdict](../../source-evidence/20261117/block-a-early-fluid-bounds/water-verdict.md).

## Body-contact Boolean aliases

The same shrunk, contracted body box and half-open `ceil(maxY)` scan remain in exact 1.10, 1.10.1, 1.10.2, 1.11, 1.11.1 and 1.11.2. With a loaded ordinary local standing player at `x=z=0.5` and `y=62.60100005364418`, the box maximum is exactly `64.0`. A level-1 water block present only at `(0,64,0)` is outside the contact scan in every reviewed release. The existing body-contact provider covers the loaded ordinary nonflying, unmounted case through 1.11.2. [Exact source evidence](../../source-evidence/20261118/block-a-early-body-ceil-mid-aliases/evidence.md), [independent verdict](../../source-evidence/20261118/block-a-early-body-ceil-mid-aliases/verdict.md).
