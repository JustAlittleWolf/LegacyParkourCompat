# F-3 correction snapshot: Shallow-overlap Nether lava current crosses 1.21.11's Player minimum impulse but 26.1.2's tracker cutoff returns

- **1.21.11**, mojmap: `1.21.11/mojmap/net/minecraft/world/entity/Entity.java`; lines 501-507.
- **26.1.2**, unobfuscated: `26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java`; lines 522-528.

## Difference

Use a Nether dimension in which the exact vanilla `DimensionTypes` sets FAST_LAVA=true.

Build a supported, side-walled horizontal channel with flowing LAVA LEVEL states at `(100,64,100)` through `(106,64,100)` with amounts 1 through 7, and a LAVA source amount 8 at `(107,64,100)`.

The source-to-current direction is east.

The Nether drop-off is 1, and the exact `FlowingFluid.getNewLiquid()` chain constructs each non-falling level by subtracting one from its higher neighbor.

Keep north, south and west of `(100,64,100)` as solid channel walls so empty neighboring fluid states add no fallback flow; leave the block above the level-1 cell empty.

All scan coordinates can remain in a fully loaded chunk.

For the level-1 target cell, `getOwnHeight()` is `1.0F/9.0F`; the east level-2 cell contributes the horizontal difference `1.0F/9.0F - 2.0F/9.0F` in X, and the three solid-wall directions block empty-neighbor fallback.

The exact paired `getFlow()` normalization therefore supplies the nonzero `(-1,0,0)` current to the player's one-cell scan.

This is a concrete reachable fluid state and source-derived vector, not a hypothetical caller-supplied flow.

## Movement meaning

The relevant tick chain is 1.21.11 `Entity.tick()` -> `updateInWaterStateAndDoFluidPushing()` -> `updateFluidHeightAndDoFluidPushing(LAVA, 0.007)` versus 26.1.2 `Entity.tick()` -> `updateFluidInteraction()` -> LAVA tracker scan -> `applyCurrentTo(LAVA, this, 0.007)`.

The player is unmounted, so 26.1.2's vehicle-specific `modifyPassengerFluidInteractionBox` does not alter the box.

The loaded gate passes when the queried scan plus its one-block horizontal margin stays in the selected loaded chunk and the expanded section contains the lava; 1.21.11's `touchingUnloadedChunk()` passes for the same loaded box.

The lava tag is present in both exact client resources. 1.21.11 and 26.1.2 both reach the same FlowingFluid state/current producer, while the relevant difference is the 1.21.11 overlap scaling and minimum impulse occurring before 26.1.2's early accumulated-current cutoff.

The broader S4.6 inventory remains in-progress for other lava heights/vectors, mounted boxes, fluid eye/contact consumers, full water/lava state/resource callers and cross-consumers.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.11--26.1.2/findings/F-3-nether-lava-shallow-overlap-r2-2026-10-09.md).
