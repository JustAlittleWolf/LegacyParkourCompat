# F-09: End lava flow updates less often in 1.11.2

- **1.10.2**, ornithe-feather: `1.10.2/ornithe-feather/net/minecraft/block/LiquidBlock.java`; lines 180-187.
- **1.11.2**, ornithe-feather: `1.11.2/ornithe-feather/net/minecraft/block/LiquidBlock.java`; lines 180-187.

## Difference

Both `LiquidBlock.getTickRate` bodies return 5 for water and 30 for lava in dimensions outside their special predicate. 1.10.2 uses `world.dimension.hasNoSky()` for lava's 10-tick rate; 1.11.2 uses `world.dimension.hasCeiling()`.

1.10.2's `TheEndDimension.initBiomeSource` sets `noSky=true`. 1.11.2's End dimension does not set `hasCeiling`; the 1.11.2 base `Dimension.hasCeiling` returns the `hasCeiling` field, which remains its Java-default `false`.

Thus flowing lava's cadence in the End changes from 10 ticks in 1.10.2 to 30 ticks in 1.11.2.

As a control, 1.10.2's Nether sets `noSky=true` and 1.11.2's Nether sets `hasCeiling=true`, retaining the 10-tick Nether cadence.

`FlowingLiquidBlock.tick` reads `getTickRate` into its reschedule delay and schedules the changed flowing state with that delay.

Its level updates and spread can therefore run at a different cadence in an End containing flowing lava.

This can change which lava blocks intersect the player's bounds, which changes `Entity.isInLava()`; the paired `LivingEntity.mobTick` checks that predicate to route jump input, and `moveRelative` uses it to select the fluid travel branch.

Those predicates and consumer bodies have the source ranges recorded above and in `../run.md`. 1.10.2 separate direct current path exists in `World.applyLiquidDrag`, but `Entity.checkWaterCollisions` supplies `Material.WATER`, so it does not support an End-lava movement claim.

The query/current methods and `LiquidBlock.getFlow/applyMaterialDrag` match after whitespace normalization.

## Movement meaning

For a map with flowing lava in the End, the scheduled lava level/spread state can differ over time between 1.10.2 and 1.11.2.

If the resulting lava footprint intersects a locally controlled player's bounds in one endpoint but not the other, the paired `isInLava` consumers can select different jump/travel branches.

This establishes a player movement-relevant source path under the stated world and fluid preconditions.

It does not establish a particular changed footprint, route, position, or trajectory.

The flow-tick body also changes 1.11.2's `updateNeighbors` call to pass `false`, suppressing observer updates.

Observer blocks were not available to 1.10.2-era maps and are excluded from this finding.

No water cadence change was found.

Nether lava remains 10 ticks in both versions.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.10.2--1.11.2/findings/F-09-end-lava-flow-update-cadence.md).
