# WORLD-10: cocoa age-2 collision shape

- **1.8.9**, ornithe-feather: `1.8.9/ornithe-feather/net/minecraft/block/CocoaBlock.java`.
- **1.9.4**, ornithe-feather: `CocoaBlock.java`.

## Difference

- Bounded geometry result: at age 0 and age 1, all four 1.8.9/1.9.4 facing boxes match.

At age 2, 1.8.9 computes an 8/16-wide cocoa body with lower Y 3/16; 1.9.4 returns the age-1 box, which is 6/16 wide with lower Y 5/16.

Both have upper Y 12/16.

The local facing-side and tangent extents differ by 2/16 on each applicable horizontal bound; no orientation is omitted.

## Movement meaning

- Player reachability: paired `World.getCollisions(Entity,Box)` provider dispatch feeds the ordinary local-player `Entity.move()` path in COLL-01.

The source-state precondition is a cocoa block with age 2 in the queried collision box; neighboring state is not read by this provider.
- Disposition: direct registered collision-shape delta established for age 2 in every facing.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-10-cocoa-age-2-collision-shape.md).
