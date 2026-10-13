# F-008: Edge gate treats nearby world border as support in 1.18.2

1.17.1, mojmap: `1.17.1/mojmap/net/minecraft/world/entity/player/Player.java`, `maybeBackOffFromEdge`, lines 1017–1067.
1.18.2, mojmap: `1.18.2/mojmap/net/minecraft/world/entity/player/Player.java`, `maybeBackOffFromEdge`, lines 1032–1080.

Difference: With no block or entity collision under an outward candidate, 1.17.1 can report `noCollision=true` because its border check sees only the current in-bounds player box. 1.18.2 can report `noCollision=false` because the candidate AABB intersects the border shape and passes the proximity guard. The edge loop therefore returns different pre-solver horizontal components.

Movement meaning: The player guard is reachable while grounded, crouching (`Player#isStayingOnGroundSurface` returns `isShiftKeyDown` in both versions, Player.java lines 303-305), not flying, and moving with `MoverType.SELF` or `PLAYER`. 1.17.1 near-border platform with no floor beneath the outward candidate satisfies the source path.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-008-border-edge-gate.md).
