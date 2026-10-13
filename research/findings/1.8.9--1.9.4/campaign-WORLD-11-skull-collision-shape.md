# WORLD-11: SkullBlock collision shape

- **1.8.9**, ornithe-feather: `1.8.9/ornithe-feather/net/minecraft/block/SkullBlock.java`.
- **1.9.4**, ornithe-feather: `SkullBlock.java`; lines 39-57.

## Difference

- 1.8.9 source: `1.8.9/ornithe-feather/net/minecraft/block/SkullBlock.java` `getCollisionShape()` calls `updateShape(world,pos)` (91-94); `updateShape()` selects the floor box for UP/default and fixed boxes for NORTH/SOUTH/WEST/EAST (70-89).
- 1.9.4 source: paired `SkullBlock.java` `getShape(state,world,pos)` maps the same five direction outcomes to constants at lines 39-57; the constants reproduce every 1.8.9 bound at 25-37.

## Movement meaning

- Disposition: representation changed from mutating shared block bounds to returning state-selected constants, but the bounded boxes match.

World list intersection and final player clipping remain separate open dependencies.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-11-skull-collision-shape.md).
