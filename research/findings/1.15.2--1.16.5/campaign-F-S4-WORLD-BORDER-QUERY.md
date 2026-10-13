# F-S4-WORLD-BORDER-QUERY: 1.16.5 skips the near-border shape check for interior boxes

- **1.15.2**, mojmap: `net/minecraft/world/level/CollisionGetter.java`; lines 79-90.
- **1.16.5**, mojmap: `net/minecraft/world/level/CollisionGetter.java`; lines 58-67.

## Difference

1.15.2 tests every non-null entity's current bounding box against the border collision shape with the same `1.0E-7` deflate/inflate pair. 1.16.5's `CollisionSpliterator` skips that test when the current box passes its strict floor/ceiling containment predicate.

Thus, for a box passing 1.16.5's predicate while 1.15.2's inflated-box test intersects the border shape, 1.15.2 yields the border shape to collision resolution; 1.16.5 yields no border shape from this check.

The block cursor scan preserves 1.15.2's bounds, collision context, large-shape and moving-piston filters, and block-shape intersection flow.

The entity-collider API was also rewritten, but the player-facing Boat and Shulker eligibility and boxes inspected here correspond, as detailed above.

The block collision resolver helpers (`collideBoundingBoxHeuristically`, legacy axis resolution and current axis resolution) have matching paired method bodies in these sources.

## Movement meaning

`Entity#move` calls the shared collision resolver with a Player source, which calls the collision stream while resolving a requested movement vector. 1.15.2 non-null Player source activates the 1.15.2 and 1.16.5 border-check paths.

The condition is restricted to the exact border-near box case; ordinary interior positions take no border collision shape on either endpoint.

The full `WorldBorder` extent and AABB shape/query audit remains part of INV-COLLISION.

The source proves a changed collision-shape stream at the stated boundary condition.

It does not establish a specific clipped movement vector or trajectory; the actual outcome also depends on the current border extent, request vector, block shapes and subsequent movement state.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S4-WORLD-BORDER-QUERY.md).
