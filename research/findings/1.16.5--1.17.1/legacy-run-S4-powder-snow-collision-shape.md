# Powder snow adds an entity-dependent collision shape

- **1.16.5**, mojmap: `world/level/CollisionGetter.java`.
- **1.17.1**, mojmap: `world/level/block/PowderSnowBlock.java`; lines 87–102.

## Difference

The shared entity collision pipeline asks each block state for a collision shape using the moving entity's collision context. 1.17.1's newly registered Powder Snow block supplies a shape conditionally: high-fall entities receive the falling shape; entities allowed to walk on it receive the superclass shape only while above and not descending; otherwise the block is non-colliding. 1.16.5 has no corresponding block or shape provider.

## Movement meaning

`Entity.move` reaches `Entity.collide`, which requests block collision shapes through `CollisionGetter`; the generic consumer correspondence was checked on both sides and is recorded in the run manifest.

For a player with leather boots, the 1.17.1 predicate permits the full block shape in the above/non-descending case.

This affects the shape used when the player's movement is clipped.

The separate Powder Snow contact callback and climb-assist behavior have their own findings.

The source confirms the shape-selection predicates and that the player collision path consumes the returned shape.

It does not establish a complete trajectory or every interaction with falling, stepping, or contact callbacks.

Powder Snow did not exist in 1.16.5, so this modern-only geometry must not be added to 1.16.5-era maps.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.16.5--1.17.1/findings/S4-powder-snow-collision-shape.md).
