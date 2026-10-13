# COLL-02: pane collision boxes differ by connection mask

- **1.8.9**, ornithe-feather: `1.8.9/ornithe-feather/net/minecraft/block/PaneBlock.java`; lines 62-92.
- **1.9.4**, ornithe-feather: `1.9.4/ornithe-feather/net/minecraft/block/PaneBlock.java`; lines 53-70.

## Difference

For each pane collision query, 1.8.9 samples north, south, west, and east neighbors.

It conditionally emits an east-west strip and a north-south strip, selecting full-length or half-length pieces based on opposing connection booleans.

With no connected neighbors it emits both full strips. 1.9.4 resolves the same four virtual connection properties, always submits the center post, then submits one arm for each true direction.

Boxes are clipped to the supplied collision-query box on both sides.

The neighbor predicate also differs: 1.8.9 accepts an opaque neighbor, this pane, glass, stained glass, stained glass pane, or any `PaneBlock`; 1.9.4 accepts a neighbor whose default state is a cube, this pane, the same explicit glass cases, or any `PaneBlock`.

Each direction is sampled independently.

Stone supplies a true connection and air a false connection under both predicates, so all sixteen N/E/S/W masks are reachable on both sides.

The table compares collision assembly for the same mask; it does not infer mask equality for every other neighboring block class.

## Movement meaning

Both releases register iron bars and clear glass panes directly as `PaneBlock`; stained glass panes use `StainedGlassPaneBlock`, which inherits the same collision implementation.

These blocks existed on both sides.

No 1.9.4-only block or block-state behavior is required for this finding.

The normal world collision collection calls the registered block state's collision method for blocks in the query region.

Player movement reaches `Entity#move(DDD)V`, which calls `World#getCollisions(Entity,Box)` before axis clipping and for step candidates.

The local-player collision path is therefore source reachable.

The mask producer is closed through all four neighbor reads and the connection predicate.

The table explicitly accounts for every reachable mask using independent stone/air choices; the changed neighbor predicate may additionally change which mask a particular non-stone/non-air neighbor produces, which is not needed for the conditional geometry result above.

For a player collision query intersecting a pane-derived shape in any of the thirteen differing masks, 1.8.9 and 1.9.4 can supply different collision-box unions to the shared movement clipping code.

Rendering and non-player or vehicle outcomes are outside this finding.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/COLL-02-pane-collision-shapes.md).
