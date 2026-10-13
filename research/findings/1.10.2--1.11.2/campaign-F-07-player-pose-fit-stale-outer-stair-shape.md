# F-07: The 1.11.2 player pose-fit query uses a stale straight-stair shape

- **1.10.2**, ornithe-feather: `1.10.2/ornithe-feather/net/minecraft/world/World.java`.
- **1.11.2**, ornithe-feather: `1.11.2/ornithe-feather/net/minecraft/world/World.java`.

## Difference

Use a registered bottom oak stair at `(0,0,0)`, facing NORTH, with same-half neighboring stairs that cause `getStairShape` to resolve this stair as `OUTER_LEFT`.

Its stored `SHAPE` remains `STRAIGHT`: `getPlacementState` sets that value, metadata decoding starts from the same default shape, and `StairsBlock` inherits the empty `Block.neighborChanged` implementation. `resolveVirtualProperties` calculates the neighbor-derived value for a query without writing it back to the world.

For a sneaking player's pose-fit candidate box `[0.70,1.30] × [0.50,2.15] × [0.20,0.80]`, the 1.10.2 collision query resolves `OUTER_LEFT`.

The outer shape contributes only `BOTTOM_NORTH_WEST_SHAPE`, whose x range is `[0.0,0.5]` and y range is `[0.5,1.0]`; the full-width base ends at the query's minimum y, so the candidate does not intersect this stair's collision boxes.

1.10.2's `World.getCollisions(Box)` dispatches the stored state to `StairsBlock.addCollisions`, which always resolves virtual properties before choosing boxes. 1.11.2's boolean wrapper calls the shared helper with `forceShape=true`; 1.11.2's stair provider resolves virtual properties only when `!forceShape`.

It therefore reads stored `STRAIGHT` and adds the bottom-north upper-half box, `[0.0,1.0] × [0.5,1.0] × [0.0,0.5]`, which intersects the candidate.

The result is 1.10.2 `false` and 1.11.2 `true` for this box.

## Movement meaning

The local player inherits `PlayerEntity.updatePlayerPose` through `LocalClientPlayerEntity -> ClientPlayerEntity -> PlayerEntity`.

The per-tick player update calls this method.

When the player begins sneaking, the target height changes from 1.8 to 1.65 while width stays 0.6.

The method builds a candidate box from the current shape minimum coordinates and target width/height; if `World.getCollisions(box)` is false, it calls `setSize`. 1.10.2 player with current bounds `[0.70,1.30] × [0.50,2.30] × [0.20,0.80]` produces the stated candidate on that pose update.

The neighboring stair arrangement supplies the `OUTER_LEFT` virtual shape, and the registered oak stair's collision provider supplies the query result.

The candidate bounds are chosen so their x interval starts beyond the outer-left quarter-step while still overlapping the stored straight north-half box.

Ordinary movement's 1.11.2 list query passes `forceShape=false` and computes the outer shape, so the player can occupy this air region beside the stair.

On the sneak pose update, 1.11.2's boolean query reads the stored straight shape and reports a collision; 1.10.2 recomputes the outer shape and does not.

This isolates the query-mode difference from ordinary movement collision resolution.

The source proves that the same sneak pose-fit candidate is accepted by 1.10.2's boolean collision gate and rejected by 1.11.2's.

For the stated current dimensions, 1.10.2 can shrink the player's height while 1.11.2 leaves the existing size unchanged.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.10.2--1.11.2/findings/F-07-player-pose-fit-stale-outer-stair-shape.md).
