# F02: Soul Sand slowdown is applied per overlapping cell in 1.14.4, once in 1.15.2

- **1.14.4**, mojmap: `SoulsandBlock.java`; lines 28–30.
- **1.15.2**, mojmap: `Block.java`; lines 644–646.

## Difference

If the player's bounding box straddles the seam between two adjacent Soul Sand cells while standing on their 14/16-high top surface, 1.14.4's block-cell loop visits both cells.

Each callback multiplies X and Z delta movement by `0.4`, so that movement invocation applies the factor twice. 1.15.2's callback is empty; its post-callback speed-factor lookup samples one block at the entity position and applies `0.4` once.

The source proves the per-cell loop, callback behavior and single selected-cell factor path.

Under the stated two-cell precondition, the horizontal multiplier is `0.4 * 0.4` in 1.14.4 and `0.4` in 1.15.2 for that movement invocation.

## Movement meaning

Local player travel -> `Entity.move` -> `checkInsideBlocks` -> Soul Sand callbacks in 1.14.4; in 1.15.2, `Entity.move` continues to the one `getBlockSpeedFactor()` lookup. 1.14.4 player's 0.6-wide bounding box can intersect both cells near a seam while its center lies in one cell.

The 14/16 collision top leaves the entity's lower bounding-box coordinates in the same block row used by the callback loop.

Source confirms a stronger horizontal velocity reduction in 1.14.4 when two Soul Sand cells are processed during one movement call, compared with 1.15.2's one selected block factor.

This is distinct from the general grounded friction lookup in F01.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.14.4--1.15.2/findings/F02-soul-sand-overlap-multiplicity.md).
