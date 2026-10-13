# F-10: Player edge support probe geometry changes in 1.21.5

1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/player/Player.java`, maybeBackOffFromEdge 1069-1117; isAboveGround 1120-1126.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/player/Player.java`, maybeBackOffFromEdge 1026-1074; isAboveGround 1077-1085.

Difference: When the player is not flying, has non-upward movement of type `SELF` or `PLAYER`, is staying on a ground surface and passes `isAboveGround`, `maybeBackOffFromEdge` repeatedly probes collision support while reducing horizontal movement in `0.05` increments. 1.21.5's `canFallAtLeast` tests a horizontally inset AABB and a shallower downward extent than 1.21.4. The probe therefore asks a different `Level.noCollision` question near horizontal boundaries and near the lower support margin. Its distance argument widens from `float` to `double`; existing callers still form the `isAboveGround` difference from float operands, while max-step height is also float.

Movement meaning: `Entity.move` invokes virtual `maybeBackOffFromEdge` before collision resolution. `Player` supplies this override, so the player route reaches the changed query for `SELF`/`PLAYER` moves satisfying the method guards. The source proves that 1.21.4 and 1.21.5 test different support volumes before deciding whether to retain or subtract horizontal movement. It can change the loop outcome for world/player positions where collision intersects the changed boundary regions.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.4--1.21.5/findings/F-10-player-edge-support-query.md).
