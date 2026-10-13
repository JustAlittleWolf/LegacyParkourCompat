# F-02: Bubble-column upper contact accepts empty shapes without fluid

- **1.21.4**, mojmap: `net/minecraft/world/level/block/BubbleColumnBlock.java`; lines 49-76.
- **1.21.5**, mojmap: `net/minecraft/world/level/block/BubbleColumnBlock.java`; lines 50-59.

## Difference

Given an entity inside a bubble-column block, 1.21.4 applies the above-column velocity rule only when the block above is air. 1.21.5 applies it whenever the above block has an empty collision shape and contains no fluid.

This includes non-air, collision-empty, fluid-empty blocks.

The vertical velocity formula is unchanged; the contact condition expands.

## Movement meaning

Entity block-contact traversal -> `BubbleColumnBlock#entityInside` -> above-position block state -> collision shape and fluid state in 1.21.5 -> `Entity#onAboveBubbleColumn` -> vertical delta movement.

BubbleColumnBlock is the callback provider; the condition is evaluated directly from the adjacent block state.

The same entity movement state writer is reached for player instances.

The source proves an expanded precondition for the same direct vertical velocity update. 1.21.4 player overlapping a bubble column under a collision-empty, fluid-empty non-air block can receive the above-column update in 1.21.5 where 1.21.4 does not.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.4--1.21.5/findings/F-02-bubble-column-above-contact.md).
