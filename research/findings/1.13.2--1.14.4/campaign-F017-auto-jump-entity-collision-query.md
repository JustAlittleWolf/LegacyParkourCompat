# F017: Auto-jump entity collision query bounds

- **1.13.2**, ornithe-feather: `LocalClientPlayerEntity.java`; lines 871-875.
- **1.14.4**, ornithe-feather: `LocalClientPlayerEntity.java`; lines 846-852.

## Difference

The paired local-player `move` overrides call `autoJump` after `super.move`, using the resulting horizontal displacement.

Both auto-jump methods share the same enabled/timer/on-ground/sneak/riding/input gates and the same corridor, obstacle-height, and delayed-jump checks. 1.13.2 calls `world.getCollisions(this, box)`; 1.14.4 calls `world.getCollisions(this, box, Collections.emptySet())`.

The empty excluded-entity set matches 1.13.2's default overload.

Both query block and entity collision shapes.

The entity-shape enumeration differs. 1.13.2 returns early only for an empty shape, asks for entities in `shape.bounds().expand(0.25)`, then retains collision shapes that intersect the original bounds. 1.14.4 returns early when the average side length is below `1.0E-7`, asks for entities in `shape.expand(1.0E-7)`, and retains shapes that intersect that epsilon-expanded box.

These predicates are not identical: the returned obstacle set can differ at the epsilon boundary or when an entity collision shape extends beyond its entity-query bounds. 1.14.4 also passes an entity collision context to block shapes; the only context-reading collision provider identified here is modern-only scaffolding and is out of scope.

## Movement meaning

`LocalClientPlayerEntity.move` -> superclass collision resolution -> `autoJump(actualDx,actualDz)` -> block/entity obstacle stream -> `ticksToNextAutojump = 1` when the scan finds a rise in the configured height interval -> next local `mobTick` synthesizes jump input.

No non-player movement or vehicle physics is analyzed.

Source proves distinct entity-obstacle query bounds and intersection predicates in a reachable player auto-jump scan.

Whether a common-era world/entity shape occupies the differing boundary, and whether that changes the delayed jump decision, remains conditional and unmeasured.

The collision-context effect on scaffolding is excluded because the block did not exist in 1.13.2.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.13.2--1.14.4/findings/F017-auto-jump-entity-collision-query.md).
