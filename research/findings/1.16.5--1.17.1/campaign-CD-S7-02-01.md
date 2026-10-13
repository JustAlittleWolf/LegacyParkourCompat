# CD-S7-02-01: Fluid pushing aborts on a wider unloaded-chunk margin

1.16.5, mojmap: `1.16.5/mojmap/net/minecraft/world/entity/Entity.java`, `updateFluidHeightAndDoFluidPushing`, lines 2641–2705.
1.17.1, mojmap: `1.17.1/mojmap/net/minecraft/world/entity/Entity.java`, `updateFluidHeightAndDoFluidPushing`, lines 2773–2846.

Difference: Both `Entity.baseTick()` methods call `updateInWaterStateAndDoFluidPushing()`, which checks lava and then water and delegates to `updateFluidHeightAndDoFluidPushing`. In 1.16.5, that method deflates the entity bounding box by `0.001`, computes the integer fluid-scan bounds, and returns early only if `level.hasChunksAt` says those scan bounds are unavailable. In 1.17.1, the method first calls `touchingUnloadedChunk()`, which inflates the full bounding box by `1.0` and checks the resulting horizontal chunk range; failure returns before the scan, fluid-height update, or velocity calculation. For a player whose deflated scan box intersects only loaded chunks, but whose one-block inflated margin intersects an unloaded chunk, 1.16.5 proceeds to sample the same fluid states while 1.17.1 exits before sampling. If matching fluid flow produces a nonzero vector, 1.16.5 can add the calculated push to the player's delta movement; 1.17.1 does not. The remaining sampled-fluid and push calculation is paired in the inspected method bodies. This is a changed guard on direct player velocity, not a claim about world or chunk generation.

Movement meaning: The client player tick reaches `Entity.tick()` and `baseTick()` through the paired player/entity tick chain recorded in S1-02.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.16.5--1.17.1/findings/CD-S7-02-01.md).
