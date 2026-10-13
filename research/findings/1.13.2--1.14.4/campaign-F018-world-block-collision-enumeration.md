# F018: World block-collision enumeration

- **1.13.2**, ornithe-feather: `WorldView.java`; lines 112-151.
- **1.14.4**, ornithe-feather: `WorldView.java`; lines 129-130.

## Difference

Both movement paths request block collision shapes through the paired world-view query, with 1.13.2's `World` adding entity obstacles and 1.14.4's default query concatenating block and entity collision streams.

The block enumerators differ. 1.13.2 computes integer bounds as floor(min)-1 through ceil(max)+1, iterates the upper endpoint exclusively, rejects selected outer-corner positions, checks `isChunkLoaded`, and combines full-block results in an `IntVoxelShape`; it also handles the world border through a `checkWorldBorder` flag. 1.14.4 computes bounds using a `1.0E-7` adjustment, and its traversal helper visits both endpoints inclusively.

It checks chunk availability through `getChunk(..., getChunkStatusForCollisions(), false)`, filters some positions using `hasLargeCollisionShape` / moving-block flags, obtains the collision context from the queried entity, and emits intersecting block shapes individually.

It first tests whether the entity's own shape crosses the world border and may emit the border shape.

At exact integral query bounds, the 1.14.4 enumerator's adjusted inclusive bounds can visit an additional outer cell compared with 1.13.2.

The 1.14.4 query also has explicit per-block flags and collision context, while 1.13.2 has perimeter skipping, full-block aggregation, and a separate border mode.

These are source-level algorithm changes; they do not by themselves establish that a common-era block returns a differing intersecting obstacle for a player movement query.

## Movement meaning

The path is reachable from local player collision resolution and the post-move auto-jump scan.

S019 covers the entity-obstacle branch of auto-jump and remains separately conditional.

1.13.2 and 1.14.4 enumerate and construct candidate block-collision streams differently.

No modern-only block behavior is included as evidence.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.13.2--1.14.4/findings/F018-world-block-collision-enumeration.md).
