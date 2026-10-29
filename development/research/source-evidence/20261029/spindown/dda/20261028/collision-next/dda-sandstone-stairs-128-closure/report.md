# 1.8.9 block ID 128 sandstone stairs provider and state-alias closure

## Source identity

The older source is exact 1.8.9 `ornithe-feather` (Feather Gen2 1.8.9+build.2); the newer source is exact 26.2 `unobfuscated`. Ready markers, provenance, and complete source-manifest identities are included in `sources.sha256`. Every selected Java file hash below was recomputed from the canonical read-only roots and matched the corresponding ready source-manifest entry.

Canonical roots:

- `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.8.9/ornithe-feather`
- `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/26.2/unobfuscated`

## Alias and metadata

In 1.8.9 `net.minecraft.block.Block`, registry ID 128 is registered as key `sandstone_stairs` with a `StairsBlock` constructed from the smooth `SandstoneBlock` default state (the local `block3` created for ID 24). The public 1.8.9 `Blocks.SANDSTONE_STAIRS` resolves that same key. This proves the old identity; it does not make the old smooth base-state value part of the collision geometry.

In 26.2 `BlockItemIds.SANDSTONE_STAIRS` is the key `sandstone_stairs`, and `Blocks.SANDSTONE_STAIRS` is registered through `registerLegacyStair(..., SANDSTONE)`, constructing the same `StairBlock` implementation used for legacy stair registrations. The current base state is default sandstone, whereas old ID 128 passed smooth sandstone. Both stair collision providers select their geometry from stair state and neighboring stairs; neither uses the base block/state in its collision shape calculation.

All metadata values 0–15 produce a valid old block state. `getStateFromMetadata` sets `HALF` from bit 2 and selects `FACING` as `Direction.byId(5 - (metadata & 3))`; bit 3 is ignored. Under old Direction IDs this gives:

| Metadata low two bits | Old facing | 26.2 `FACING` |
|---:|---|---|
| 0 | EAST | EAST |
| 1 | WEST | WEST |
| 2 | SOUTH | SOUTH |
| 3 | NORTH | NORTH |

Bit 2 clear means bottom; set means top. The default state is north, bottom, straight. Old `SHAPE` is virtual and resolved from neighbor states; it is not encoded in the four facing/half metadata bits. Current `StairBlock` adds `WATERLOGGED`; its collision `getShape` does not read that property.

## Movement collision geometry

The old collision provider is `StairsBlock.addCollisions` (not the outline `rayTrace` routine). It first sets a full-X/Z half-height base box according to top/bottom and submits it to `Block.addCollisions`. It then computes the facing/half corner shape from neighboring old `StairsBlock`s and submits that box. If the opposite-side test selects an inner corner, it submits one additional quarter-box. It resets the shared block bounds to the unit cube after the provider returns. All box coordinates are 0, 0.5, or 1 (as floats), and integer-position translation preserves those exact half-grid planes.

The old corner decisions have two neighbor tests:

- An outer corner is considered from the neighbor at `pos + facing`: it must be a stair of the same half and perpendicular facing, with the diagonal same-half/same-facing predicate vetoing the corner.
- If no outer corner is selected, an inner corner is considered from `pos - facing`: it must be a stair of the same half and perpendicular facing; the same-half/same-facing diagonal predicate is again a veto.

The current `StairBlock.getStairsShape` uses the corresponding behind (`pos + facing`) then front (`pos - facing`) tests. `canTakeShape` is the negation of the same-half/same-facing diagonal-stair check. `getShape` maps straight/outer/inner plus left/right and facing to static `VoxelShape`s. Its base shapes are the same half-cube and upper half/quarter/three-quarter unions, built from 1/2 and unit coordinates and rotated horizontally. Thus the source-defined collision *union geometry* for a primary old ID-128 state matches the current `sandstone_stairs` shape when all neighbor stair identities/properties used by those tests correspond and the current stored `SHAPE` equals the recomputed legacy corner result.

The current collision gate is enabled: `registerLegacyStair` uses `BlockBehaviour.Properties.ofLegacyCopy(SANDSTONE)`, `hasCollision` defaults true and is copied; `BlockBehaviour.getCollisionShape` returns the state's collision shape when this gate is true. The current provider returns a state-based immutable shape, whereas the old provider writes shared bounds and adds separate boxes. This closure compares their collision union geometry only; it does not claim that every surrounding collision-list aggregation or axis operation is numerically equivalent.

## Applicability and remaining gates

A consumer can map old ID 128's primary state to current key `minecraft:sandstone_stairs` using the facing/half table above. For collision geometry it must also establish that:

1. The current `FACING` and `HALF` match the decoded old values.
2. The current `SHAPE` property matches the old outer/inner/straight decision for the same current neighbor states at query time. The old provider recomputes this from the world each time; the current provider reads stored `SHAPE`, which is derived at placement and horizontal-neighbor updates.
3. Any neighboring stair block that participates in a corner decision maps to a stair identity that existed in 1.8.9 and carries equivalent facing/half state. 26.2 has additional stair keys; a modern-only stair must not be treated as an old `StairsBlock` neighbor. If its state could affect the relevant neighbor/diagonal predicates, use native behavior.
4. The caller is the movement collision-shape operation. The old `StairsBlock.rayTrace` separately toggles `isCorner`/`stairType`, calls the base ray routine repeatedly, and relies on `updateShape` to alter the shared bounds. That outline/DDA ray operation is not covered by this movement collision geometry comparison.

This is an exact-source alias/provider closure for one primary stair key and its local movement collision geometry, subject to neighbor and stored-shape applicability checks. It does not close all legacy stair aliases, modern-only neighbor feature behavior, camera/DDA visitation, current-vs-old collision list order, runtime reachability, or movement parity.

## Source references

- 1.8.9 `Block.java`: ID/key registration and sandstone block assignment at lines 876–877, 1111; shared bound write and box-returning collision helpers at 292–299, 339–350.
- 1.8.9 `StairsBlock.java`: properties/default at 27–44; base/outer/inner collision helpers at 74–88, 216–380; `addCollisions` and bounds reset at 384–393; ray-test state fields at 49–70 and override at 502–530; metadata and virtual shape resolution at 538–581; properties at 583–585.
- 1.8.9 `Direction.java`: direction IDs and horizontal facing conversion used by metadata.
- 26.2 `Blocks.java`: sandstone stairs registration at 2647 and `registerLegacyStair` at 5955–5957.
- 26.2 `BlockItemIds.java`: `SANDSTONE_STAIRS` key at line 401.
- 26.2 `StairBlock.java`: shape-state schema/default at 33–67, `getShape` at 74–91, neighbor shape decision at 131–165, state properties at 216–219.
- 26.2 `Block.java` and `Shapes.java`: 16-unit box construction and horizontal rotation helpers at `Block.java:160–184` and `Shapes.java:404–433`.
- 26.2 `BlockBehaviour.java`: collision gate at 327–329, default and legacy-copy property behavior at 985, 1033–1052.
- 26.2 `BlockState.java`, `Half.java`, `StairsShape.java`, `BlockStateProperties.java`, and `Direction.java`: current property and direction identities used above.

No product source, tests, builds, game/runtime execution, or DDA visitation evidence was used.
