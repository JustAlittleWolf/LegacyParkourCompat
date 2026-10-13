# WORLD-26: neighbor-cell collision scan and protruding piston-head arms

- **1.8.9**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-26-neighbor-cell-collision-scan.md).
- **1.9.4**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-26-neighbor-cell-collision-scan.md).

## Difference

- Source hashes: 1.8.9 `World.java` , `Block.java` , `PistonHeadBlock.java` ; 1.9.4 `World.java` , `Block.java` , `PistonHeadBlock.java` .

## Movement meaning

- Disposition: this is a conditional source-level clipped-displacement difference for the specified local move and collision context: 1.8.9 retains `dx=0.2`, 1.9.4 clips to `dx=0.05`.

Full candidate enumeration, other block/entity collisions, world-border and loaded-chunk interactions, and step-candidate outcomes remain dependencies of the broad COLL-01 slice.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-26-neighbor-cell-collision-scan.md).
