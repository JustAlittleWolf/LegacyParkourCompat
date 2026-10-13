# F-009: Border overlap changes one-axis player collision

1.17.1, mojmap: `1.17.1/mojmap/net/minecraft/world/entity/Entity.java`, `applyPistonMovementRestriction, collide`, lines 749–758, 803–816.
1.18.2, mojmap: `1.18.2/mojmap/net/minecraft/world/entity/Entity.java`, `collide, collideBoundingBox`, lines 777–801, 803–817.

Difference: For a player already overlapping the east outside-border region, with no block or entity obstacles, request a one-axis move north across the north boundary. 1.17.1 sees the current-box overlap and omits the entire border shape; its scalar collision path has no border candidate and returns the requested movement. 1.18.2's near-border guard passes for the swept box, includes the outside-border shape, and clips the northward movement at the north boundary. The VoxelShape collision kernel and axis arithmetic match across versions. This changes the returned movement vector and resulting player position. 1.17.1 reachable setup is a world-border size or center change that places the border across a stationary player; `WorldBorder#setSize` and `#setCenter` replace/update the extent without relocating entities (1.17.1 lines 97-125; 1.18.2 lines 109-136). The collision precondition then persists into the player's next ordinary movement.

Movement meaning: The path is the normal non-zero `Entity#move` collision call used by Player.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-009-border-overlap-axis-collision.md).
