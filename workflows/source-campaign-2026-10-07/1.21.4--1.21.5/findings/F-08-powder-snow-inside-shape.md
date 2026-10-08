# F-08: Powder snow contact shape can follow the player's collision shape in 1.21.5

- Older version A: 1.21.4
- Newer version B: 1.21.5
- Mechanic / coverage slice IDs: S4-CALLBACKS, S5-BLOCK-SHAPES
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.21.4, 1.21.5]
- Runtime validation: not performed

## Paired evidence

- A: `ready/1.21.4/mojmap.sources.sha256`; `net/minecraft/world/level/block/PowderSnowBlock.java` has no entity-inside shape override. Its entity callback scan therefore uses inherited `BlockBehaviour#getEntityInsideCollisionShape(BlockState, Level, BlockPos)` lines 353-355, which returns `Shapes.block()`. PowderSnowBlock SHA-256: `a018411167f5d8b60b131c3f6387dcdd256b736ef64126bdcf33fd4457151654`; BlockBehaviour SHA-256: `a549c5dcf7768f89f18519bdd2f985ef498d1bd4f6b329a30e0088e437819ddf`.
- B: `ready/1.21.5/mojmap.sources.sha256`; `PowderSnowBlock#getEntityInsideCollisionShape(BlockState, BlockGetter, BlockPos, Entity)` lines 107-111 builds the entity's collision context, returns the PowderSnow collision shape if nonempty, and falls back to `Shapes.block()` when empty. `getCollisionShape` lines 114-129 returns `FALLING_COLLISION_SHAPE` (height `0.9F`) when `fallDistance > 2.5`; otherwise it returns a walk-on/full collision shape only under the existing powder-snow walking conditions, or empty. PowderSnowBlock SHA-256: `de72fb6e70616d7727bf324b0da4a98250cabd97ad39ecbe1062732d47f39770`; BlockBehaviour SHA-256: `15418b0026919ef5ab97780a6ce8844677dc82d36cc4433a58f379b837d5cc50`.
- B: `ready/1.21.5/mojmap.sources.sha256`; `Entity#checkInsideBlocks(List<Movement>, StepBasedCollector)` lines 1092-1098 uses this shape to decide whether to call the block's `entityInside` callback. Entity.java SHA-256: `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4`.

## Source-level difference

For a player whose entity collision context gives powder snow a nonempty collision shape, B uses that shape for movement contact callbacks; in particular, `fallDistance > 2.5` selects the `0.9F`-high falling collision shape. A uses the full block shape for the inside callback scan regardless of that entity collision shape. When B's entity collision shape is empty, B falls back to the same full block shape used by A.

## Reachability and dependencies

Player movement -> `Entity#checkInsideBlocks` -> PowderSnowBlock entity-inside shape -> callback intersection -> `PowderSnowBlock#entityInside` -> `makeStuckInBlock`. The player's fallDistance and entity collision context select B's shape. This callback's stuck movement multiplier is a direct movement-state writer. A's inherited shape and B's override/collision-shape helper paths are cited above; registry defaults are not needed for this condition.

## Consequence and uncertainty

The source proves that B can test a smaller inside-contact shape where A tests the full block. Contact callbacks may therefore differ for movement that intersects only the portion removed by B's context-specific shape. The callback affects the stuck speed multiplier, but no concrete trajectory was measured; exact intersection depends on the player's bounding box and movement segment.

## Handoff

Independent delta: Powder snow entity-inside contact geometry. Related finding IDs: F-01. Applicability requires a player collision context with nonempty powder-snow collision shape, including the cited falling-distance branch. Exact first release within the pair is unknown.
