# CD-S7-02-01: Fluid pushing aborts on a wider unloaded-chunk margin

- **1.16.5**, mojmap: `1.16.5/mojmap/net/minecraft/world/entity/Entity.java`; `1.16.5/mojmap/net/minecraft/world/level/LevelReader.java`.
- **1.17.1**, mojmap: `1.17.1/mojmap/net/minecraft/world/entity/Entity.java`; `1.17.1/mojmap/net/minecraft/world/level/LevelReader.java`.

## Difference

Both `Entity.baseTick()` methods call `updateInWaterStateAndDoFluidPushing()`, which checks lava and then water and delegates to `updateFluidHeightAndDoFluidPushing`.

In 1.16.5, that method deflates the entity bounding box by `0.001`, computes the integer fluid-scan bounds, and returns early only if `level.hasChunksAt` says those scan bounds are unavailable.

In 1.17.1, the method first calls `touchingUnloadedChunk()`, which inflates the full bounding box by `1.0` and checks the resulting horizontal chunk range; failure returns before the scan, fluid-height update, or velocity calculation.

For a player whose deflated scan box intersects only loaded chunks, but whose one-block inflated margin intersects an unloaded chunk, 1.16.5 proceeds to sample the same fluid states while 1.17.1 exits before sampling.

If matching fluid flow produces a nonzero vector, 1.16.5 can add the calculated push to the player's delta movement; 1.17.1 does not.

The remaining sampled-fluid and push calculation is paired in the inspected method bodies.

This is a changed guard on direct player velocity, not a claim about world or chunk generation.

## Movement meaning

The client player tick reaches `Entity.tick()` and `baseTick()` through the paired player/entity tick chain recorded in S1-02.

The fluid branch is reached for water or lava overlap; the finding's concrete condition additionally requires the scan bounds to be loaded, an unloaded chunk inside the newer inflated margin, fluid tagged with the checked tag, `isPushedByFluid()` true, and a nonzero accumulated flow. 1.16.5 `Player` is exempt from the non-player flow normalization in both method bodies. `LevelReader.hasChunksAt` in 1.16.5 checks the supplied vertical limits against `[0,256)` and then checks the x/z chunk rectangle. 1.17.1 checks the supplied vertical limits against the dimension build-height range and delegates to the x/z overload; `touchingUnloadedChunk()` uses that x/z overload for the one-block margin.

Source proves that the newer guard can suppress fluid-height sampling and the immediate player velocity addition in the stated chunk-margin condition.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.16.5--1.17.1/findings/CD-S7-02-01.md).
