# F001: Farmland movement collision height changes

- **1.9.4**, ornithe-feather: `net/minecraft/block/FarmlandBlock.java`; lines 31-40.
- **1.10.2**, ornithe-feather: `net/minecraft/block/FarmlandBlock.java`; lines 20-39.

## Difference

When a player collision query intersects farmland, 1.9.4 contributes a full one-block-high collision box. 1.10.2 inherits the base collision implementation, which contributes the same 15/16-high shape used for farmland’s outline.

The top 1/16 of the farmland block therefore ceases to block player movement in 1.10.2 under the same world and player box preconditions.

## Movement meaning

Player movement reaches `Entity.move` → `World.getCollisions` → `BlockState.addCollisions` → `Block.getCollisionShape`.

The block-state dispatch invokes `FarmlandBlock#getCollisionShape` on 1.9.4 and the base `Block#getCollisionShape` on 1.10.2.

This finding depends on the normal registered farmland block state and collision candidate enumeration; it does not depend on farmland moisture, crops, or random ticks.

The paired Feather source and revised immutable mapped artifacts show collision boxes differing by 1/16 block vertically.

Independent operations verification passed, and the source trees and raw inputs match their original manifests.

The original derived jars are unavailable, so their byte identity with the revised artifacts is unproven.

When a player's queried box overlaps the top 1/16 band, the different collision shape can change clipping or support; exact player position, ground flags, and later movement depend on the full collision scenario and are not claimed.

The difference is established between these paired releases; no release outside this pair is assessed.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.9.4--1.10.2/findings/F001-farmland-collision-height.md).
