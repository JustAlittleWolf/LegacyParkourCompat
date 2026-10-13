# F-S2-EDGE: edge restraint applies during a near-ground fall

- **1.15.2**, mojmap: `net/minecraft/world/entity/player/Player.java`; lines 1002-1049.
- **1.16.5**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S2-EDGE.md); lines 1010-1065.

## Difference

The x/z reduction loops are unchanged: each component is backed off in 0.05 increments while a box shifted down by `maxUpStep` has no collision, then the reduced vector is returned. 1.16.5 changes when the loops run. 1.15.2 requires `onGround`; 1.16.5 replaces that requirement with a near-ground test and additionally excludes flying players.

Therefore for a non-flying player using SELF/PLAYER movement while shift is held, not currently on ground, with `fallDistance < maxUpStep` and a collision found by the helper's downward box query, 1.16.5 enters the edge-restraint loops while 1.15.2 returns the original vector.

## Movement meaning

`Entity.move()` calls the virtual `maybeBackOffFromEdge(vec3,moverType)` before collision resolution in both endpoint sources.

The shift predicate comes from the local player input state. 1.16.5's helper uses the entity bounding box, `fallDistance`, `maxUpStep`, and `Level.noCollision`; those inputs are read directly and do not require movement through any excluded system.

The query participates in the changed guard only; the 0.05-step reduction body is common.

The source proves that 1.16.5 can reduce horizontal requested movement for a near-ground, not-yet-grounded player in the stated guard, while 1.15.2 skips this method body.

The exact resulting position depends on the surrounding collision resolution and world shapes; no observed trajectory is claimed.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S2-EDGE.md).
