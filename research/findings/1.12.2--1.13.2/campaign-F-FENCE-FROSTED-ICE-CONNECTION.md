# F-FENCE-FROSTED-ICE-CONNECTION — 1.13.2 fences omit the Frosted Ice arm

- **1.12.2**, ornithe-feather: `FenceBlock.java`; lines 59-78.
- **1.13.2**, ornithe-feather: `FenceBlock.java`; lines 46-61.

## Difference

For a solid-face neighbor, 1.12.2's fence predicate rejects the neighbor only when it appears in `Block.isExceptionForAttachment` or the fence's additional exception list.

Neither list contains `Blocks.FROSTED_ICE`, so an east-adjacent Frosted Ice block with its default `FaceShape.SOLID` sets EAST=true. 1.13.2 adds `Blocks.FROSTED_ICE` to `FenceBlock.shouldConnectTo(Block)`, so the same solid face sets EAST=false. 1.13.2's placement and horizontal `updateShape` both use that predicate; 1.12.2 resolves the connection from neighbors during collision collection.

Frosted Ice existed in both versions under the same `frosted_ice` registry key.

It inherits the full-block face and collision shapes.

In 1.13.2, `Block.Properties.hasCollision` defaults true and the Frosted Ice registration does not disable it.

This finding therefore concerns an old block present in both eras.

## Movement meaning

Place a fence at (0,0,0) and Frosted Ice at (1,0,0), leaving the remaining fence neighbors open.

On the paired movement path, 1.12.2 resolves EAST=true and appends its east collision arm X[10,16] × Z[6,10] × Y[0,24] in sixteenth-block coordinates. 1.13.2 resolves EAST=false and returns only the center post; the 1.13.2 fence geometry and inherited pane-provider route are bounded in `FENCE-COLLISION-GEOMETRY`.

For an isolated source-derived player query, set a 0.6-wide player box to X[1.0,1.6], Z[0.2,0.8], Y[1.0001,2.8001], with `onGround=false`, not sneaking, and request `dx=-0.1`, `dy=dz=0`.

The player is just above the Frosted Ice top, so the Frosted Ice full cube does not overlap vertically. 1.12.2's east arm extends to x=1.0 and up to y=1.5, so the negative X movement is clipped at zero. 1.13.2's center post ends at x=0.625 and does not overlap this query; with no east arm, 1.13.2 applies the requested -0.1 X displacement.

The airborne precondition prevents the step candidate path from replacing this axis result.

This is a source-derived ordered player movement consequence, not a measured trajectory.

It is bounded to the stated historical block arrangement and movement query; no frequency claim is made.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-FENCE-FROSTED-ICE-CONNECTION.md).
