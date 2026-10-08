# F-02: Bubble columns apply their above-column impulse below any empty-fluid collision space

- Older version A: 1.21.4
- Newer version B: 1.21.5
- Mechanic / coverage slice IDs: S5-BLOCK-FACTORS, S5-NEIGHBORS
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.21.4, 1.21.5]
- Runtime validation: not performed

## Paired evidence

- A: manifest `ready/1.21.4/mojmap.sources.sha256`; `net/minecraft/world/level/block/BubbleColumnBlock.java`; `net.minecraft.world.level.block.BubbleColumnBlock#entityInside(BlockState, Level, BlockPos, Entity)` lines 49-76 (SHA-256 `9427b3e617753f5b3adc7a724fd80c87d4aa0e2f5d0eb38cd2ca34092e449b47`) calls `onAboveBubbleCol` only when the block directly above `isAir()`.
- B: manifest `ready/1.21.5/mojmap.sources.sha256`; `net/minecraft/world/level/block/BubbleColumnBlock.java`; `BubbleColumnBlock#entityInside(BlockState, Level, BlockPos, Entity, InsideBlockEffectApplier)` lines 50-59 (SHA-256 `c5e854a05c314e63275e24f30eac79dd0f0303071953284efcbc8773af9aa3c7`) calls `onAboveBubbleColumn` when the above block's collision shape is empty and its fluid state is empty.
- A: `net/minecraft/world/entity/Entity.java`, lines 2483-2493 (SHA-256 `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad`); B: same source path, lines 2455-2483 (SHA-256 `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4`). The above-column handlers apply the same vertical velocity clamp/update (`max(-0.9, y - 0.03)` for a downward column; otherwise `min(1.8, y + 0.1)`).

## Source-level difference

Given an entity inside a bubble-column block, 1.21.4 applies the above-column velocity rule only when the block above is air. 1.21.5 applies it whenever the above block has an empty collision shape and contains no fluid. This includes non-air, collision-empty, fluid-empty blocks. The vertical velocity formula is unchanged; the contact condition expands.

## Reachability and dependencies

Entity block-contact traversal -> `BubbleColumnBlock#entityInside` -> above-position block state -> collision shape and fluid state in B -> `Entity#onAboveBubbleColumn` -> vertical delta movement. BubbleColumnBlock is the callback provider; the condition is evaluated directly from the adjacent block state. The same entity movement state writer is reached for player instances.

## Consequence and uncertainty

The source proves an expanded precondition for the same direct vertical velocity update. A player overlapping a bubble column under a collision-empty, fluid-empty non-air block can receive the above-column update in B where A does not. No trajectory was observed; exact contacted block and velocity depend on world state and current vertical velocity.

## Handoff

Independent delta: above-bubble-column contact predicate. Related finding IDs: F-01. Applicability requires a player overlapping a bubble column with a non-air block above whose collision shape and fluid state are both empty. Exact first release within the pair is unknown.
