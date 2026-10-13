# F-08: Powder snow contact shape can follow the player's collision shape in 1.21.5

- **1.21.4**, mojmap: `net/minecraft/world/level/block/PowderSnowBlock.java`; lines 353-355.
- **1.21.5**, mojmap: `PowderSnowBlock#getEntityInsideCollisionShape(BlockState, BlockGetter, BlockPos, Entity)`; lines 107-111.

## Difference

For a player whose entity collision context gives powder snow a nonempty collision shape, 1.21.5 uses that shape for movement contact callbacks; in particular, `fallDistance > 2.5` selects the `0.9F`-high falling collision shape. 1.21.4 uses the full block shape for the inside callback scan regardless of that entity collision shape.

When 1.21.5's entity collision shape is empty, 1.21.5 falls back to the same full block shape used by 1.21.4.

## Movement meaning

Player movement -> `Entity#checkInsideBlocks` -> PowderSnowBlock entity-inside shape -> callback intersection -> `PowderSnowBlock#entityInside` -> `makeStuckInBlock`.

The player's fallDistance and entity collision context select 1.21.5's shape.

This callback's stuck movement multiplier is a direct movement-state writer. 1.21.4's inherited shape and 1.21.5's override/collision-shape helper paths are cited above; registry defaults are not needed for this condition.

The source proves that 1.21.5 can test a smaller inside-contact shape where 1.21.4 tests the full block.

Contact callbacks may therefore differ for movement that intersects only the portion removed by 1.21.5's context-specific shape.

The callback affects the stuck speed multiplier, but no concrete trajectory was measured; exact intersection depends on the player's bounding box and movement segment.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.4--1.21.5/findings/F-08-powder-snow-inside-shape.md).
