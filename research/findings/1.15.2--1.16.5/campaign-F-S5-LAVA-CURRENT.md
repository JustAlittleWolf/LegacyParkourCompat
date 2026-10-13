# F-S5-LAVA-CURRENT: non-flying players receive lava-current force

- **1.15.2**, mojmap: `net/minecraft/world/entity/Entity.java`; lines 361-363.
- **1.16.5**, mojmap: `net/minecraft/world/entity/Entity.java`; line 380.

## Difference

1.15.2's entity base tick updates water and its current; the inspected path does not update or push from lava. 1.16.5's entity base tick updates fluid heights and pushes from both water and lava. 1.15.2 non-flying Player satisfies 1.16.5's `isPushedByFluid()` guard, so the lava flow scan can add a dimension-scaled current vector to its delta movement.

Flying players remain excluded by the Player override.

## Movement meaning

`Entity.baseTick()` is called through the player tick superclass path.

The 1.16.5 lava call uses the same fluid scan and push operation as water, keyed to `FluidTags.LAVA`; the fluid-state scan contributes only when matching flow is present and intersects the deflated entity box. `Player.isPushedByFluid()` permits the current for a non-flying player. 1.15.2 has no corresponding lava-push call in the inspected `baseTick`/`updateWaterState` path; its lava movement state is used for lava handling, but not as a current producer.

The source proves 1.16.5 can add nonzero lava-flow delta velocity to a non-flying player in a matching flowing-lava state.

Force magnitude also depends on flow vector, occupancy, dimension warmth and the low-speed minimum described by F-S5-WATER-CURRENT.

Net motion and survival consequences are not claimed.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S5-LAVA-CURRENT.md).
