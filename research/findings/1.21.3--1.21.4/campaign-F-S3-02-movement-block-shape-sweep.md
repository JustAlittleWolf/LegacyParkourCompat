# F-S3-02: 1.21.4 changes the movement-through-block shape sweep

- **1.21.3**, mojmap: `1.21.3/mojmap/net/minecraft/world/entity/Entity.java`; lines 740-784.
- **1.21.4**, mojmap: `Entity#applyEffectsFromBlocks(Vec3,Vec3)`; lines 751-797.

## Difference

1.21.4 changes which cells are yielded near the movement path's grid boundaries and replaces 1.21.3's recentered-final-box/reverse-vector test with a segment-start box and forward displacement for non-full inside shapes.

The callback set can therefore differ when a recorded player segment passes a partial shape boundary, including the End Portal slab.

The callback state changes on that path can affect later player portal processing and position.

## Movement meaning

`LivingEntity.aiStep()` -> `Entity.applyEffectsFromBlocks(oldPosition, position)` records the local player's movement segment after travel.

The block scan asks each candidate state for its entity-inside collision shape; full `Shapes.block()` cases call through the existing fast path, while partial shapes use the changed swept-box helper.

When the End Portal partial shape is intersected and portal use is allowed, `EndPortalBlock.entityInside()` calls `setAsInsidePortal()`, writing the player portal process.

The exact movement segment, endpoint, box height and shape must place the swept boxes on different sides of the partial-shape boundary for 1.21.3/1.21.4 to select different callbacks.

Source proves a changed block-cell traversal and changed swept-box geometry used by the reachable local-player callback path.

It also proves that a hit on the End Portal shape can update the player's portal process.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.3--1.21.4/findings/F-S3-02-movement-block-shape-sweep.md).
