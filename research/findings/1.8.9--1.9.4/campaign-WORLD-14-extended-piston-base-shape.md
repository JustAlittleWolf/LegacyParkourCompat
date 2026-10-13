# WORLD-14: extended piston-base collision shape

- **1.8.9**, ornithe-feather: `1.8.9/ornithe-feather/net/minecraft/block/PistonBaseBlock.java`.
- **1.9.4**, ornithe-feather: `PistonBaseBlock.java`; `Block.java`.

## Difference

- 1.8.9 source: `1.8.9/ornithe-feather/net/minecraft/block/PistonBaseBlock.java` `updateShape()` reads the live block state, and for an extended piston sets the bound 0.25 short of the facing side; otherwise it sets a full cube (174-203). `getCollisionShape()` calls that resolver (216-219).

Its `addCollisions()` then dispatches through the collision method (211-214).
- 1.9.4 source: paired `PistonBaseBlock.java` no `getShape()`/`getCollisionShape()` override exists. 1.9.4 `addCollisions()` passes `state.getShape(world,pos)` to collision assembly (76-78), which reaches the inherited full-cube provider. 1.9.4 `Block.java` full-cube provider and registration are under

## Movement meaning

- Disposition: source-level collision-provider delta established for every extended facing.

World list composition, adjacent piston-head geometry and final player clipping remain separate open dependencies.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-14-extended-piston-base-shape.md).
