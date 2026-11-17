# Independent audit: snow layer player collision-query gate

## Verdict

**ACCEPT** for the bounded source claim in `snow_player_collision_query_gate/report.md` (SHA-256 `4b66255e6cea235b4bed189c00c7f3185f64f7555a39b8a3e3db3deb42a59ea9`). This is a source-only consumer-path result; it does not establish a reachable ordinary-player trajectory or net displacement.

## Evidence checked

The report hash matches the current bytes. The six added 1.12.2 Java source hashes match both current canonical files and their entries in `decompiled_minecraft/1.12.2/ornithe-feather.sources.sha256`; the manifest hashes to `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`, consistent with the ready marker. The cited prior snow report and r3 correction/audit identities are the ones referenced by the report.

The call path is supported: `LocalClientPlayerEntity.tick` invokes its superclass when the local player's chunk is loaded; player tick sets `noClip = isSpectator()`, so a non-spectator follows the collision branch. The class hierarchy reaches `PlayerEntity` and `LivingEntity`; `LivingEntity.tick` calls `mobTick`, `PlayerEntity.mobTick` calls its superclass, and `LivingEntity.mobTick` reaches `moveRelative` and the applicable travel branches invoke `move(SELF, velocityX, velocityY, velocityZ)`. `LocalClientPlayerEntity.move` delegates to `Entity.move`.

On the collision branch, `Entity.move` queries `World.getCollisions` with `getShape().expanded(x, y, z)` and applies each returned candidate through `Box.intersectY` before moving the AABB. `Box.intersectY` requires strict X/Z overlap. For positive `dy`, it clips against a candidate plane at `P` when `B.maxY <= P` and the plane is inside the expanded query; the report's equivalent condition `B.maxY <= P < B.maxY + dy` yields the cap `P - B.maxY`. For negative `dy`, `B.minY + dy < P <= B.minY` yields the cap `P - B.minY`. These inequalities align with the strict query overlap documented by the accepted snow r3 provider analysis. All statements remain conditional on the report's explicit AABB and movement conditions.

## Limits retained

The report correctly leaves open whether a legal stored snow state occurs at the required location, whether an ordinary local-player tick reaches those relative coordinates, whether another collision produces the same final offset, and whether any net displacement or trajectory changes. It does not claim runtime behavior, server replay, or pair completion. No correction is required within this assigned gate.
