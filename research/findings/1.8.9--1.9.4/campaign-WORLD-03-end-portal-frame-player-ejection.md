# WORLD-03: End Portal Frame solidity changes local player block-ejection gate

- **1.8.9**, ornithe-feather: `LocalClientPlayerEntity.java`; lines 546-549.
- **1.9.4**, ornithe-feather: `LocalClientPlayerEntity.java`; lines 657-660.

## Difference

1.8.9's End Portal Frame inherits `Block#isCube()==true`.

It has `Material.STONE`, whose `isSolidBlocking()` is true, and its analog output does not change the separate `isSignalSource()` predicate; 1.8.9's `Block#isSolid()` therefore returns true.

In 1.9.4, `EndPortalFrameBlock#isCube(BlockState)` returns false for its states, so `Block#isSolid(BlockState)` returns false for the same block despite unchanged material and signal-source inputs.

The local-player helper differs only in which solidity API it reads: 1.8.9 checks the block instance; 1.9.4 checks the sampled states.

The paired `pushAwayFrom` methods keep the same guards, coordinate conversion, west/east/north/south ordering, strict distance comparisons, and `±0.1F` horizontal velocity writes.

If the sampled position is the frame and the above position is non-solid, 1.8.9's `canSurvive` result is false and the escape search runs; 1.9.4's result is true and skips that write.

When an escape neighbor is survivable, this creates a reachable 1.8.9-only velocity overwrite for a locally controlled player before superclass movement.

## Movement meaning

Both registries contain the historical `end_portal_frame` block and both constructors use `Material.STONE`.

The path is the local player's four pre-travel body probes in `LocalClientPlayerEntity#mobTick()V`, then `pushAwayFrom(DDD)Z` and `canSurvive(BlockPos)Z`. 1.8.9 horizontal neighbor must pass the same `canSurvive` predicate for a direction to be selected.

The source proves a branch and possible velocity write, not that a particular world geometry produces a final trajectory.

The paired state-solid provider comparison found that piston base is non-solid on both endpoints (1.8.9 and 1.9.4 `isCube` are explicitly false), Air is non-solid on both due to `Material.AIR`, and the other 1.9.4-only `isCube` overrides are modern-only blocks without 1.8.9 counterparts.

Paired signal-source overrides are semantically equivalent, including trapped chest 1.8.9 `type == 1` versus 1.9.4 `Type.TRAP`; 1.9.4's only `getMaterial(BlockState)` override returns the block's unchanged material.

The source establishes a difference in the pre-travel branch gate and a possible direct horizontal velocity overwrite of `±0.1F` in 1.8.9 where 1.9.4 skips it under the stated preconditions.

It does not establish the resulting position or trajectory.

The endpoint pair does not establish the first release where the behavior changed.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-03-end-portal-frame-player-ejection.md).
