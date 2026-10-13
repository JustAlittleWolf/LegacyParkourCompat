# F-S2-POSE-EPSILON: 1.16.5 shrinks the candidate box for pose clearance

1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/world/entity/Entity.java`, `canEnterPose`, lines 1605–1607.
1.16.5, mojmap: `1.16.5/mojmap/net/minecraft/world/entity/Entity.java`, `canEnterPose`, lines 1640–1642.

Difference: 1.15.2 `Entity#canEnterPose(pose)` submits `getBoundingBoxForPose(pose)` directly to `level.noCollision`. 1.16.5 submits that candidate box after `.deflate(1.0E-7)`. AABB deflation shrinks the candidate on all axes by the supplied epsilon. Player pose selection calls this predicate for swimming eligibility, its selected pose, and crouching fallback; the method body and branch order are otherwise the same between endpoints. When a collision shape overlaps only the thin boundary removed by 1.16.5's deflation, the two calls give the collision query different candidate boxes and may select different poses. Once a pose is written to synced entity data, the unchanged listener refreshes dimensions, eye height and the bounding box.

Movement meaning: The finding is restricted to the player pose-entry predicate and candidate AABB. The same canEnterPose predicate also supplies same-tick crouch input slowdown; adjacent cache writer/reader introduces no tick delay.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S2-POSE-EPSILON.md).
