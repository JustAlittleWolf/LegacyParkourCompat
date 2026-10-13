# WORLD-13: moving piston direct player response

- **1.8.9**, ornithe-feather: `1.8.9/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java`.
- **1.9.4**, ornithe-feather: `MovingBlockEntity.java`.

## Difference

- 1.8.9 source: `1.8.9/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java` `moveEntities(progress,amount)` obtains the MovingBlock collision box and queries entities (80-90); for an extending moved slime block it sets the facing-axis velocity and does not call `Entity.move`, while other moved states call `entity.move(amount * facing offsets)` (93-107).
- 1.9.4 source: paired `MovingBlockEntity.java` `moveEntities()` queries the progress-union shape translated by block position (90-93), skips entities only when `getPistonMoveBehavior() == IGNORE`, sets the slime-facing velocity for slime states, then computes the entity-to-box edge gap plus `0.01` and calls `entity.move(gap * movement direction)` for both slime and non-slime states (94-146).

## Movement meaning

- Disposition: direct player response differs.

For an extending piston-moving slime block and a player returned by the respective entity query, 1.8.9 assigns facing-axis velocity only; 1.9.4 assigns the same velocity and also calls `Entity.move`.

For other moved blocks, 1.8.9 passes a progress-derived amount while 1.9.4 computes the edge gap and adds `0.01` before moving.

WORLD-12 records the query-box shape difference; no final position or trajectory is claimed.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-13-moving-piston-player-response.md).
