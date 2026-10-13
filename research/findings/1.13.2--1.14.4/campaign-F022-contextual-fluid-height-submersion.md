# F022: Contextual fluid height in the local submerged-water cache

- **1.13.2**, ornithe-feather: `Entity.java`; lines 979-981.
- **1.14.4**, ornithe-feather: `Entity.java`; lines 974-976.

## Difference

1.13.2 `Entity.isSubmergedIn` tests the eye-cell fluid tag and compares the eye Y against `blockY + (fluidState.getHeight() + 0.11111111F)`. 1.13.2's `FlowingFluid.getHeight(FluidState)` is `state.getLevel() / 9.0F` and has no world context. 1.14.4's `m_69330161(tag, boolean)` applies the same eye-cell test, optionally checks that the chunk is loaded, and calls `FluidState.getHeight(world,pos)`. 1.14.4's `FlowingFluid.getHeight` returns `1.0F` when the eye-cell fluid and the fluid above are the same exact fluid instance; otherwise it delegates to the same level/9 calculation.

This helper feeds two distinct caches: Entity's base-tick `submergedInWater` field (through `m_61708540`, with `true` in 1.14.4) and PlayerEntity's separate `isSubmergedInWater` field (through `updateWaterSubmersion`, with `true` in 1.14.4; 1.13.2 directly calls `isSubmergedIn`).

The source-visible conditional predicate difference is therefore: for an eye-cell state with level below 8, the same flowing-fluid instance above, and an eye fraction greater than 1.13.2's `level/9.0F + 0.11111111F` but below 1, 1.14.4 can return true where 1.13.2 returns false. 1.14.4's extra chunk guard cannot reject the ordinary local-player path: `LocalClientPlayerEntity.tick` checks that the chunk at the player's X/Z is loaded before calling the superclass tick, and the eye query has the same X/Z.

## Movement meaning

`PlayerEntity.tick` refreshes its own `isSubmergedInWater` cache before `super.tick()` in both versions; 1.13.2 has an extra bubble-column sound-state helper call immediately before this refresh, which remains outside this slice.

The inherited Entity tick separately refreshes the base `submergedInWater` cache before `updateSwimming`.

The player-owned value reaches LocalClientPlayerEntity's sprint/swim consumers during inherited tick (S001); the base Entity value reaches the `isSubmergedInWater()`/`updateSwimming` path (S051).

Both `FlowingFluid.getSpreadFluid` methods set a downward level-8 state when the same flowing-fluid instance above can flow upward.

Waterlogged slabs produce the source-water instance. 1.13.2 lower-level flowing state with that same flowing-fluid instance above would therefore require a precise transition or a source configuration not yet closed by this slice.

The source proves different height calculation and a conditional boolean outcome.

It does not yet prove that the distinguishing eye-cell state is reachable on the local player during a vanilla tick, nor that a stable-world movement outcome differs.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.13.2--1.14.4/findings/F022-contextual-fluid-height-submersion.md).
