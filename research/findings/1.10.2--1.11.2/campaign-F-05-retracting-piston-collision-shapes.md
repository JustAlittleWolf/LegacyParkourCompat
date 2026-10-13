# F-05: Retracting moving piston uses separate base and head collision shapes

- **1.10.2**, ornithe-feather: `MovingBlock#getCollisionShape`; lines 132-137.
- **1.11.2**, ornithe-feather: `World.java`; lines 960-1010.

## Difference

1.10.2's `MovingBlock` does not override `addCollisions`.

Ordinary world collision dispatch therefore uses the base `Block#addCollisions`, which asks `MovingBlock#getCollisionShape`; that returns `MovingBlockEntity#getShape`, a union of the translated `movedState` shape at current and last progress.

For a retracting source, `PistonBaseBlock` constructs the moving entity with `extending=false, source=true` and `movedState` set to the piston base state.

1.11.2's ordinary world query passes `forceShape=false` to the new `MovingBlock#addCollisions` override.

The override delegates to `MovingBlockEntity#addCollisions`, which adds the extended base collision at the source and then constructs collision boxes from a piston-head state translated by progress.

The head arm uses new direction-specific short-arm boxes as the animation approaches completion.

## Movement meaning

The retracting source is created by the paired piston-base path in both versions. 1.10.2 local player's ordinary `World.getCollisions` query scans the moving-block cell and receives the version-specific boxes when its query box intersects them.

The collision-box set supplied to player movement differs for a reachable retracting source during partial progress.

Which axis clipping or step candidate changes depends on player position, box, progress and nearby geometry.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.10.2--1.11.2/findings/F-05-retracting-piston-collision-shapes.md).
