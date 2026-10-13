# WORLD-15: piston-head arm collision boxes

- **1.8.9**, ornithe-feather: `1.8.9/ornithe-feather/net/minecraft/block/PistonHeadBlock.java`.
- **1.9.4**, ornithe-feather: `PistonHeadBlock.java`.

## Difference

- 1.8.9 source: `1.8.9/ornithe-feather/net/minecraft/block/PistonHeadBlock.java` `addCollisions()` selects the face shape, dispatches it, selects the arm shape, dispatches it, then restores full mutable bounds (87-93); the six arm boxes are selected in `setArmShapeForCollisions()` (95-120).
- 1.9.4 source: paired `PistonHeadBlock.java` `addCollisions()` adds the state face shape plus one direction-indexed arm constant (65-85; constants 25-36).

## Movement meaning

- Disposition: paired head-face boxes match, while each direction's connecting-arm collision box differs.

The states and adjacent piston base are not folded into this provider comparison; no player clipping outcome is claimed.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-15-piston-head-arm-collision.md).
