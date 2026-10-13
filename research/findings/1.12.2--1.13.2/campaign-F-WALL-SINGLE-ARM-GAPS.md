# F-WALL-SINGLE-ARM-GAPS — 1.13.2 wall collision unions leave corner gaps

- **1.12.2**, ornithe-feather: `1.12.2/ornithe-feather/net/minecraft/block/WallBlock.java`.
- **1.13.2**, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/block/WallBlock.java`.

## Difference

For a wall with only NORTH connected, both versions derive `UP=true`. 1.12.2 selects `COLLISION_SHAPES[4]`, the single Box X[4,12]×Z[0,12]×Y[0,24] in sixteenth-block coordinates. 1.13.2 selects `VoxelShapes.or(center,northArm)`: center X[4,12]×Z[4,12]×Y[0,24], north arm X[5,11]×Z[0,11]×Y[0,24].

The OR shape leaves two empty corner columns: X[4,5]×Z[0,4] and X[11,12]×Z[0,4]. 1.12.2's single Box fills both. `VoxelShapes.or` performs a Boolean OR over occupied voxels, so matching outer bounds do not make these shapes equal.

The state is reachable with a north-adjacent open east-west-facing fence gate and air on the other sides.

The gate's south face reports `MIDDLE_POLE`, which the wall connection helper accepts for fence gates; its open collision shape is empty in both versions.

For one NORTH connection, 1.12.2's and 1.13.2's placement/neighbor formulas both set `UP=true`, selecting the shapes above.

## Movement meaning

The local player reaches the shared `Entity.move` resolver through the recorded player tick/travel path in `run.md` and `WORLD-COLLISIONS`. 1.12.2 gathers translated Box collisions from its swept player Box. 1.13.2 builds a swept VoxelShape query, filters block shapes by Boolean overlap, and passes the returned shape stream to the same player's Y/X/Z movement resolution.

Concrete query relative to the wall at (0,0,0): the player has width `0.6F`, height `1.8F`, center approximately x=-0.06,z=-0.31,y=0, giving a Box approximately X[-0.36,0.24], Z[-0.61,-0.01].

Request `dx=0.07`, `dy=0`, `dz=0.25`. 1.12.2's expanded Box reaches X≈0.31,Z≈0.24 and intersects its wall Box.

X resolves fully because the current player Box is still north of the wall's z=0 face; after X, the player overlaps the wall in X, so 1.12.2 clips Z at approximately 0.01. 1.13.2's swept query, including its `1.0E-7` expansion, still ends before the north arm's X=0.3125 boundary and the center's Z=0.25 boundary; no 1.13.2 wall collision shape overlaps that query.

The open gate supplies no other collision. 1.13.2 therefore resolves the requested Z displacement without the wall candidate.

The source proves a different returned collision set and a reachable player movement query whose ordered axis clipping differs.

The remaining wall arm combinations, multi-arm states, `UP=false` inherited PaneBlock path, and full stored-neighbor state lifecycle are still open.

The broader collector/representation candidate remains `F-COLLISION-REPRESENTATION`.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-WALL-SINGLE-ARM-GAPS.md).
