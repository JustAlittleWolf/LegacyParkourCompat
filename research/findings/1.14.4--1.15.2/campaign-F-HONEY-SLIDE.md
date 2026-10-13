# F-HONEY-SLIDE: Honey contact redirects a descending player's motion

- **1.14.4**, mojmap: `net/minecraft/world/level/block/Blocks.java`; lines 1-2035.
- **1.15.2**, mojmap: `1.15.2/mojmap/net/minecraft/world/level/block/HoneyBlock.java`; lines 21-35.

## Difference

Honey Block exists in 1.15.2 and not 1.14.4.

Its collision shape is inset by 1/16 horizontally and reaches 15/16 height.

On contact, `entityInside` checks that the entity is airborne, its feet are at or below blockY + 0.9375 - 1e-7, vertical velocity is below -0.08, and it is near a block edge.

When y velocity is below -0.13, 1.15.2 multiplies X/Z by `-0.05 / y` and sets Y to -0.05; otherwise it retains X/Z and sets Y to -0.05.

Both paths reset fallDistance.

## Movement meaning

Player collision movement reaches block contact callbacks through `Entity.move` -> `checkInsideBlocks`; the Honey Block callback accepts a player because it is an Entity.

The effect applies only when the modern Honey Block is present and the slide predicate succeeds.

It does not establish a historical map mechanic for 1.14.4.

The source proves the predicate and velocity/fallDistance writes, not an observed trajectory.

Landing fall damage and sound/particles are outside this movement finding.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.14.4--1.15.2/findings/F-HONEY-SLIDE.md).
