# F02: Soul Sand slowdown is applied per overlapping cell in 1.14.4, once in 1.15.2

1.14.4, mojmap: `1.14.4/mojmap/net/minecraft/world/level/block/SoulsandBlock.java`, `entityInside`, lines 28–30.
1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/world/level/block/SoulsandBlock.java`, `class/shape initialization`, lines 16–34.
1.14.4, mojmap: `1.14.4/mojmap/net/minecraft/world/entity/Entity.java`, `checkInsideBlocks`, lines 810–831.
1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/world/entity/Entity.java`, `move, getBlockSpeedFactor`, lines 533–544, 590–598.

Difference: If the player's bounding box straddles the seam between two adjacent Soul Sand cells while standing on their 14/16-high top surface, 1.14.4's block-cell loop visits both cells. Each callback multiplies X and Z delta movement by `0.4`, so that movement invocation applies the factor twice. 1.15.2's callback is empty; its post-callback speed-factor lookup samples one block at the entity position and applies `0.4` once. The source proves the per-cell loop, callback behavior and single selected-cell factor path. Under the stated two-cell precondition, the horizontal multiplier is `0.4 * 0.4` in 1.14.4 and `0.4` in 1.15.2 for that movement invocation.

Movement meaning: Local player travel -> `Entity.move` -> `checkInsideBlocks` -> Soul Sand callbacks in 1.14.4; in 1.15.2, `Entity.move` continues to the one `getBlockSpeedFactor()` lookup. A player's 0.6-wide bounding box can intersect both cells near a seam while its center lies in one cell.

Original evidence: [archived report](../../archive/legacy-workflows/movement-discovery/runs/1.14.4--1.15.2/findings/F02-soul-sand-overlap-multiplicity.md).
