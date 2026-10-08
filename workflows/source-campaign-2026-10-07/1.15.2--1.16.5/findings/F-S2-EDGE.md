# F-S2-EDGE: edge restraint applies during a near-ground fall

- Older version A: 1.15.2, Mojmap
- Newer version B: 1.16.5, Mojmap
- Mechanic / coverage slice IDs: player collision edge restraint; S2-EDGE
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.15.2, 1.16.5]
- Runtime validation: not performed

## Paired evidence

- A manifest artifact reference: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap`; `net/minecraft/world/entity/player/Player.java`; `Player#maybeBackOffFromEdge(Vec3,MoverType)`, lines 1002-1049; SHA-256 `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793`. The outer guard requires SELF or PLAYER mover, `onGround`, and `isStayingOnGroundSurface()`.
- B manifest artifact reference: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap`; same class/member, lines 1010-1065; SHA-256 `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`. The guard instead requires not flying, SELF or PLAYER mover, `isStayingOnGroundSurface()`, and `isAboveGround()`. The helper returns `onGround || fallDistance < maxUpStep && !level.noCollision(this, boundingBox.move(0, fallDistance - maxUpStep, 0))`.
- `Player#isStayingOnGroundSurface()` returns `isShiftKeyDown()` on both sides; A line 294-296 and B line 289-291 in the corresponding Player hashes.

## Source-level difference

The x/z reduction loops are unchanged: each component is backed off in 0.05 increments while a box shifted down by `maxUpStep` has no collision, then the reduced vector is returned. B changes when the loops run. A requires `onGround`; B replaces that requirement with a near-ground test and additionally excludes flying players. Therefore for a non-flying player using SELF/PLAYER movement while shift is held, not currently on ground, with `fallDistance < maxUpStep` and a collision found by the helper's downward box query, B enters the edge-restraint loops while A returns the original vector.

## Reachability and dependencies

`Entity.move()` calls the virtual `maybeBackOffFromEdge(vec3,moverType)` before collision resolution in both endpoint sources. The shift predicate comes from the local player input state. B's helper uses the entity bounding box, `fallDistance`, `maxUpStep`, and `Level.noCollision`; those inputs are read directly and do not require movement through any excluded system. The query participates in the changed guard only; the 0.05-step reduction body is common.

## Consequence and uncertainty

The source proves that B can reduce horizontal requested movement for a near-ground, not-yet-grounded player in the stated guard, while A skips this method body. The exact resulting position depends on the surrounding collision resolution and world shapes; no observed trajectory is claimed.

## Handoff

Independent source delta: B extends edge restraint to supported near-ground motion and disables it during player flight. Related finding IDs: none. Boundary within the endpoint interval remains unknown.
