# S5-02: Lava currents push entities in 1.16.5

- **1.15.2**, mojmap: `Entity.java`.
- **1.16.5**, mojmap: `Entity.java`.

## Difference

1.16.5 routes lava through the fluid-height/current helper each entity tick; 1.15.2 only routes water through its current helper. 1.15.2's lava contact flag does not add a lava flow vector.

## Movement meaning

1.15.2 non-flying player intersecting flowing lava with a nonzero averaged flow vector reaches 1.16.5's helper and can have its velocity changed.

Player flight disables pushing in both versions (`isPushedByWater` in 1.15.2 / `isPushedByFluid` in 1.16.5).

1.16.5 adds a dimension-dependent lava-current velocity increment.

The 1.15.2 client artifact was recovered and no historical lava-current call exists in its inspected base-tick path.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.15.2--1.16.5/findings/s5-02-lava-current-push.md).
