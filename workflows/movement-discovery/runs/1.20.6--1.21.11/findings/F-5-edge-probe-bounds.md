# F-5: Edge-backoff support probe uses a smaller inset box in 1.21.11

- Older version A: 1.20.6.
- Newer version B: 1.21.11.
- Mechanic / coverage slice IDs: stage 4, slice 4.1 (player edge-backoff support query).
- Classification: changed behavior.
- Confidence: source-confirmed for query geometry; its movement consequence depends on nearby collision shapes.
- Applicability: local player edge-backoff probe with a collision shape intersecting only the changed AABB margin.
- First changed release: unknown within (1.20.6, 1.21.11].
- Runtime validation: not performed.

## Paired evidence

- A source: `decompiled_minecraft/1.20.6/mojmap/net/minecraft/world/entity/player/Player.java`, `Player.maybeBackOffFromEdge(Vec3,MoverType)` lines 1067–1117 and `Player.canFallAtLeast(double,double,float)` lines 1122–1126; SHA-256 `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2`. Its probe AABB spans `minX + dx` through `maxX + dx` and `minZ + dz` through `maxZ + dz`; its lower Y is `minY - step - 1.0E-5F`.
- B source: `decompiled_minecraft/1.21.11/mojmap/net/minecraft/world/entity/player/Player.java`, `Player.maybeBackOffFromEdge(Vec3,MoverType)` lines 889–939 and `Player.canFallAtLeast(double,double,double)` lines 944–951; SHA-256 `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`. The probe adds `1.0E-7` to minX/minZ, subtracts it from maxX/maxZ, and changes the lower Y margin to `1.0E-7`.
- Shared caller path: A `Entity.move()` calls virtual `maybeBackOffFromEdge()` at line 711 (Entity SHA-256 `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`); B calls it at line 711 (Entity SHA-256 `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`). The player override is reached through local-player inheritance on both sides.

## Source-level difference

The backoff loops and their entry guards match in the inspected methods, but the `noCollision` AABB used by `canFallAtLeast()` changes. B shrinks its horizontal footprint by `1.0E-7` on every side and uses a lower vertical extension of `1.0E-7` instead of A's `1.0E-5F`. Therefore the queries can disagree when collision geometry intersects only the excluded horizontal strip or the vertical band between those lower bounds.

## Reachability and dependencies

`Entity.move()` dispatches to the player override for `SELF` and `PLAYER` moves. The override enters the probe when the player is not flying, requested Y movement is not positive, the player must stay on the ground surface, and the player is within its step-height ground check. `canFallAtLeast()` asks the current level's collision query about the constructed probe AABB. Block collision shapes and world collision semantics determine whether the changed query result makes the loops reduce horizontal movement further.

## Consequence and uncertainty

The source proves the changed collision-query volume. It predicts different edge-backoff reductions only for collision geometry whose intersection lies within the changed strips/band; ordinary full-block support away from those margins is outside this finding's asserted consequence. The collision-shape/query closure is still under stage 4/5 review. No edge case was executed or runtime-tested.

## Handoff

Independent delta: edge-backoff support-probe AABB margins changed. Related finding IDs: none. Applicability depends on near-boundary collision geometry; implementation and testing decisions are deferred.
