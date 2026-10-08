# F018: World block-collision enumeration

- Older version A: 1.13.2
- Newer version B: 1.14.4
- Mechanic / coverage slice IDs: block collision query used by local player movement; S020
- Classification: changed behavior candidate
- Confidence: candidate (query algorithms differ in source; common-era outcome depends on shape bounds, chunk/world-border conditions, and provider behavior)
- Applicability: local-player collision queries; first changed release unknown within (1.13.2,1.14.4]
- Runtime validation: not performed

## Paired evidence

- A source root: `../../../build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/`; `WorldView.java`::`getBlockCollisions(VoxelShape,VoxelShape,boolean)`, lines 112-151, SHA-256 `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`; `World.java`::`getCollisions(Entity,VoxelShape,VoxelShape,Set)`, lines 1755-1757, SHA-256 `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a`.
- B source root: `../../../build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather/`; `WorldView.java`::`getCollisions(Entity,Box,Set)`, lines 129-130, and `getBlockCollisions(Entity,Box)`, lines 133-187, SHA-256 `fcdf8dc98f97781f4639c4b8cda8a238edf834f3963e401e6eeef6cb475c4909`; traversal helper `net/minecraft/unmapped/C_29205863.java`::`m_97468855`, lines 24-48, SHA-256 `d15373ef21f58ed67755269d32b2dd5ff65eb33dd4eea33e854b505b9ac9afd`.

## Source-level difference

Both movement paths request block collision shapes through the paired world-view query, with A's `World` adding entity obstacles and B's default query concatenating block and entity collision streams. The block enumerators differ. A computes integer bounds as floor(min)-1 through ceil(max)+1, iterates the upper endpoint exclusively, rejects selected outer-corner positions, checks `isChunkLoaded`, and combines full-block results in an `IntVoxelShape`; it also handles the world border through a `checkWorldBorder` flag. B computes bounds using a `1.0E-7` adjustment, and its traversal helper visits both endpoints inclusively. It checks chunk availability through `getChunk(..., getChunkStatusForCollisions(), false)`, filters some positions using `hasLargeCollisionShape` / moving-block flags, obtains the collision context from the queried entity, and emits intersecting block shapes individually. It first tests whether the entity's own shape crosses the world border and may emit the border shape.

At exact integral query bounds, the B enumerator's adjusted inclusive bounds can visit an additional outer cell compared with A. The B query also has explicit per-block flags and collision context, while A has perimeter skipping, full-block aggregation, and a separate border mode. These are source-level algorithm changes; they do not by themselves establish that a common-era block returns a differing intersecting obstacle for a player movement query.

## Reachability and dependencies

The path is reachable from local player collision resolution and the post-move auto-jump scan. Exact outcomes depend on the queried box, block state and collision shape, chunk status, world-border relation, and the entity context. `D-COLLISION-SHAPES` remains open for provider and neighbor-state enumeration; `D-TICK-CLOSURE` remains open for the complete local-player call path. S019 covers the entity-obstacle branch of auto-jump and remains separately conditional.

## Consequence and uncertainty

A and B enumerate and construct candidate block-collision streams differently. Whether the difference changes resolved player displacement, clipping flags, step choice, or the delayed auto-jump decision for a common-era state is unresolved without the remaining provider and boundary analysis. No modern-only block behavior is included as evidence. Runtime validation is deferred.

## Handoff

Independent source candidate. Continue provider and neighbor-state inventory; do not treat this as a validated movement outcome. No implementation or wiki feedback was used. First changed release unknown within (1.13.2,1.14.4].