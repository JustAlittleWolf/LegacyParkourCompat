# F-COLLISION-REPRESENTATION — 1.13.2 changes player collision resolution representation

- **1.12.2**, ornithe-feather: `1.12.2/ornithe-feather/net/minecraft/entity/Entity.java`; lines 462-796.
- **1.13.2**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-COLLISION-REPRESENTATION.md); lines 473-782.

## Difference

1.12.2 collects translated Box candidates and clips each axis against the list; 1.13.2 collects VoxelShapes and uses VoxelShapes.calculateMaxOffset for axis clipping.

Both use Y, X, then Z axis resolution and step alternatives, but candidate representation/helper semantics differ.
- Collector boundary: on the reachable movement path, 1.12.2 scans block positions and calls `BlockState.addCollisions` against the requested Box, then appends intersecting entity and entity-against-player Boxes except same-vehicle entities. 1.13.2's displacement overload builds a swept VoxelShape query with `1.0E-7` offsets and subtracts the initial shape; `WorldView` gathers block shapes and merges out-of-border cells into a boundary shape, while `EntityView` appends entity shapes after exclusion-set and same-vehicle checks.

## Movement meaning

- Reachability and dependencies: Entity.move is the player collision path for self/player moves.

Sneak edge probes, step height, support lookup, border and block callbacks are within the caller's reachable path.

Every old block's effective collision shape, registration/defaults, neighbors, WorldView collision stream, entity exclusions, border and voxel offset/tie behavior must be compared with concrete reachable cases before this becomes a behavioral finding.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-COLLISION-REPRESENTATION.md).
