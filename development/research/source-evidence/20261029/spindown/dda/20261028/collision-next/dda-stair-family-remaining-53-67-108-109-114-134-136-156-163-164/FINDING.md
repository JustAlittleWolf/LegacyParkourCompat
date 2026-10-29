# Remaining legacy stair registrations: source closure

Status: bounded source finding, not DDA traversal parity or product acceptance.

## Exact registered identities

The 1.8.9 registration table (`Block.java`) confirms the following IDs, registry names, and base-state families. The assignment shorthand was wrong for IDs 67 and 156/163/164: ID 67 is named `stone_stairs` (its flattened modern item/block alias is `cobblestone_stairs`); ID 156 is quartz; ID 163 is acacia; ID 164 is dark oak. ID 135 (birch) is included in the requested family.

| 1.8.9 numeric ID | 1.8.9 registered key | Base state in old registration | 26.2 stair key / registry | 26.2 base block |
|---:|---|---|---|---|
| 53 | `oak_stairs` | oak planks, `variant=oak` | `minecraft:oak_stairs` | oak planks |
| 67 | `stone_stairs` | stone block default state | `minecraft:cobblestone_stairs` | cobblestone |
| 108 | `brick_stairs` | bricks | `minecraft:brick_stairs` | bricks |
| 109 | `stone_brick_stairs` | stone bricks, default variant | `minecraft:stone_brick_stairs` | stone bricks |
| 114 | `nether_brick_stairs` | nether brick | `minecraft:nether_brick_stairs` | nether bricks |
| 134 | `spruce_stairs` | spruce planks | `minecraft:spruce_stairs` | spruce planks |
| 135 | `birch_stairs` | birch planks | `minecraft:birch_stairs` | birch planks |
| 136 | `jungle_stairs` | jungle planks | `minecraft:jungle_stairs` | jungle planks |
| 156 | `quartz_stairs` | quartz block, default variant | `minecraft:quartz_stairs` | quartz block |
| 163 | `acacia_stairs` | acacia planks | `minecraft:acacia_stairs` | acacia planks |
| 164 | `dark_oak_stairs` | dark oak planks | `minecraft:dark_oak_stairs` | dark oak planks |

`EntityBlockStateFix` independently maps all these numeric IDs to the old registry names. `ItemStackTheFlatteningFix` explicitly maps `minecraft:stone_stairs.0` to `minecraft:cobblestone_stairs`; this is the material alias for ID 67, not `stone_stairs`. The current `Blocks` table registers each listed modern key as a `StairBlock` using the corresponding base block. The modern state fixer contains state rows for these staircase keys. Current `StairBlock.createBlockStateDefinition` exposes facing, half, shape and waterlogged; the historical block state has facing, half and virtual shape, with no waterlogged property.

## Shared 1.8.9 state and geometry semantics

All listed IDs instantiate the same `net.minecraft.block.StairsBlock`; differences are only their base state/material. `getStateFromMetadata` uses bit 2 (`metadata & 4`) for top versus bottom and maps low two bits through `Direction.byId(5 - (metadata & 3))`, producing horizontal east/west/south/north; metadata bit 3 is ignored for this state conversion. `getMetadataFromState` writes the inverse low-bit mapping and top bit. Thus the source confirms eight effective orientation/half states (with aliases across the ignored high bit), not family-specific state logic.

For collision boxes, `addCollisions` mutates the registered block instance bounds: it emits the base top/bottom half; then `isInnerStair` sets a second half-step box and it emits that box; if the inner query and `setStepShapeForCollisions` both qualify, it emits one further quarter box. It finally resets bounds to the full unit cube. The corner/inner/outer predicates read neighboring stairs' `FACING` and `HALF`, and the same-half/same-facing diagonal veto. Stair identity is `instanceof StairsBlock`, so old stair families interoperate across materials. The output shape is not determined by the primary block's ID alone.

Old ray tracing also mutates instance fields (`isCorner`, `stairType`) and bounds, selecting the two corner-octant indices from a facing/half rotation table and ray tracing the other octants. This finding records that provider behavior as a shared mutable instance dependency; it does not close the DDA provider enumeration/visitation contract or prove how often it is queried.

## 26.2 stored-shape relation and limits

The modern `StairBlock` constructs a voxel shape from the state's facing, half and stored `SHAPE`; its shape definition adds waterlogged as well. Its `getStairsShape` checks behind-side outer corners before front-side inner corners. It recognizes any current `StairBlock` as a stair, including families/features that did not exist in 1.8.9, and its side veto (`canTakeShape`) also treats any current stair as eligible. The old code instead dynamically recognizes the old `StairsBlock` class and examines the side/corner block states at collision time.

For a current neighborhood consisting only of mapped old stair families and non-stair blocks, with equivalent facing/half inputs and an up-to-date stored current shape, the modern stored-state geometry follows the same classic stair-shape cases. The old collision routine's mutable multi-box emission and its exact query-time neighbor evaluation remain distinct provider semantics; this table is not a proof that modern collision or ray tracing has the same callbacks, ordering, query envelope or side effects.

If any neighbor or diagonal veto dependency is a modern-only stair, current `isStairs`/`canTakeShape` may classify it where the old source would see a non-stair. A historical provider must either compute the old identity-gated neighbor policy from mapped IDs and old properties, or use native behavior for that route. Do not infer universal neighbor equivalence from matching registry names or base materials. Waterlogged is modern-only state; its ordinary solid geometry may share the stair voxel boxes, but any path involving fluid semantics is outside this finding.

## Source references

- 1.8.9 IDs and base states: `Block.java` registrations at lines 941, 967, 1052–1056, 1072, 1130–1132, 1172, 1183–1185.
- 1.8.9 stair provider: `StairsBlock.java`, especially `setBaseShapeForCollisions` (74–80), `isStairSameHalfSameFacing` (86–90), outer/inner tests (92–295), step collision shape (297–379), collision emission/reset (384–395), ray trace (502–540), metadata conversion (544 onward), and virtual neighbor-shape resolution (552 onward).
- 26.2 current block registrations: `world/level/block/Blocks.java`; legacy item identity and shape data: `EntityBlockStateFix.java`, `ItemStackTheFlatteningFix.java`, and `BlockStateData.java`.
- 26.2 stair shape provider: `world/level/block/StairBlock.java`, especially state shape lookup (80–100), neighbor update (108–129), stored-shape calculation and veto (131–165), and state-property declaration (216–218).

Source files are SHA-256 pinned in `sources.sha256`. This source result can support mapping and bounded policy design; it cannot by itself close a complete ray provider adapter, traversal equivalence, or product behavior claim.
