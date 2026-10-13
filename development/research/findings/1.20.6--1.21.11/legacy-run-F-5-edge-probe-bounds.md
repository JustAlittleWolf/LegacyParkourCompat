# F-5: Edge-backoff support probe uses a smaller inset box in 1.21.11

1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/world/entity/Entity.java`, move 711.
1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/world/entity/player/Player.java`, maybeBackOffFromEdge 1067-1117; canFallAtLeast 1122-1126.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/world/entity/player/Player.java`, maybeBackOffFromEdge 889-939; canFallAtLeast 944-951.

Difference: The backoff loops and their entry guards match in the inspected methods, but the `noCollision` AABB used by `canFallAtLeast()` changes. 1.21.11 shrinks its horizontal footprint by `1.0E-7` on every side and uses a lower vertical extension of `1.0E-7` instead of 1.20.6's `1.0E-5F`. Therefore the queries can disagree when collision geometry intersects only the excluded horizontal strip or the vertical band between those lower bounds.

Movement meaning: `Entity.move()` dispatches to the player override for `SELF` and `PLAYER` moves. The override enters the probe when the player is not flying, requested Y movement is not positive, the player must stay on the ground surface, and the player is within its step-height ground check. `canFallAtLeast()` asks the current level's collision query about the constructed probe AABB. Block collision shapes and world collision semantics determine whether the changed query result makes the loops reduce horizontal movement further. The source proves the changed collision-query volume. It predicts different edge-backoff reductions only for collision geometry whose intersection lies within the changed strips/band; ordinary full-block support away from those margins is outside this finding's asserted consequence. The collision-shape/query closure is still under stage 4/5 review.

Original evidence: [archived report](../../archive/legacy-workflows/movement-discovery/runs/1.20.6--1.21.11/findings/F-5-edge-probe-bounds.md).
