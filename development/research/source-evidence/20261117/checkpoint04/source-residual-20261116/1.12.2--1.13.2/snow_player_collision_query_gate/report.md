# Snow layer one: ordinary local player collision consumer gate

## Question and bounded inputs

This note closes only whether the accepted layer-one candidate difference can enter the ordinary local-player `Entity.move` collision query and its Y clipping loop. It does not repeat the snow provider comparison or claim a reachable trajectory.

Prior evidence:

- 1.12.2→1.13.2 snow provider report: `snow_layer_collision_provider/report.md`, SHA-256 `0286fec4882b27d5921fa086d183d48351f1e6a6e7c97bd6c944e24cc2ef6a93`; r1 audit: `snow_layer_collision_provider_audit/verdict.md`, SHA-256 `4241afc0aa165a2eb45add70437aad366864b26cfb0466996167278d4bde4187`.
- Conditional candidate correction r3: `snow_layer_collision_provider_r3/coverage-r3.md`, SHA-256 `124ccdf3667e28f9c241109cb3b1af60fb7bc88be84ae9a091413d2856e2f904`; r3 audit: `snow_layer_collision_provider_r3_audit/verdict-r3.md`, SHA-256 `57b79925ce42119d2444a3c79d5729fe9d0018e0577fa730790856045202dbf2`.
- Exact 1.12.2 source manifest: `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` (Ornithe Feather Gen2 build 2), matching the ready marker.

The current note adds only client/player call-path evidence and interprets the r3 inequalities at the `Entity.move` consumer.

## Local-player call path

`LocalClientPlayerEntity` extends client `ClientPlayerEntity`, which extends `PlayerEntity`, which extends `LivingEntity` (`LocalClientPlayerEntity.java:81`, `ClientPlayerEntity.java:21`, `PlayerEntity.java:94`). The local client tick calls `super.tick()` when the player's current chunk is loaded (`LocalClientPlayerEntity.java:165–166`). The normal player tick sets `noClip` from spectator status and calls its superclass (`PlayerEntity.java:174–204`); for a non-spectator player, `noClip` is false and the collision path is active.

The local `mobTick` calls `super.mobTick()` (`LocalClientPlayerEntity.java:649–812`), and `PlayerEntity.mobTick` calls `super.mobTick()` (`PlayerEntity.java:413–430`). `LivingEntity.tick` invokes `mobTick` (`LivingEntity.java:1619–1681`), and `LivingEntity.mobTick` reaches `moveRelative` (`LivingEntity.java:1842–1847`); the applicable travel branches call `this.move(MoverType.SELF, this.velocityX, this.velocityY, this.velocityZ)` (`LivingEntity.java:1464,1514,1543,1574`). Dynamic dispatch reaches `LocalClientPlayerEntity.move`, which calls `super.move(...)` (`LocalClientPlayerEntity.java:846–851`), then `Entity.move`.

On the ordinary non-`noClip` branch, `Entity.move` requests candidates using `world.getCollisions(this, this.getShape().expanded(x, y, z))` (`Entity.java:462–464,567`). It processes those boxes through `Box.intersectY(this.getShape(), y)` before applying the resulting Y offset (`Entity.java:571–576`). The local player therefore uses the same direct collision-candidate and Y-clip consumer analyzed in the accepted snow r3 note.

## Candidate and clipping inequalities at the player consumer

Let `P` be the old layer-one plane at `blockY`, `B` the player's current collision AABB, and `(dx,dy,dz)` the requested `Entity.move` offset. The old `Box.intersects` uses strict overlap on all three axes (`Box.java:249–251`), and `Box.intersectY` first requires strict X/Z overlap (`Box.java:205–222`). The accepted r3 source analysis establishes that the translated zero-height snow box can enter the list when the expanded query strictly straddles `P` and has positive X/Z overlap.

For an upward request, the query can straddle `P` while the clip helper can cap the move when:

`B.maxY <= P < B.maxY + dy`, `dy > 0`, with positive X/Z overlap.

Then `Box.intersectY` returns at most `P - B.maxY`, so it can shorten the requested positive Y offset to the plane. For a downward request, the corresponding conditions are:

`B.minY + dy < P <= B.minY`, `dy < 0`, with positive X/Z overlap.

Then the helper can raise the requested negative offset to `P - B.minY`. These are source-derived conditional inequalities, not a constructed gameplay example. The target's layer-one empty collision shape supplies no corresponding candidate, as established by the accepted r3 provider analysis.

## Disposition and remaining gate

The collision difference reaches an ordinary logical-client player's `Entity.move` call path and can mathematically constrain its requested Y offset when the stated AABB, movement and horizontal-overlap conditions hold. This is more than a provider-only candidate on an unrelated entity path.

The source trace does not establish that a legal stored `snow`/`snow_layer` world state occurs at the required location, that an ordinary local-player tick reaches one of the inequalities in that world, or that no other collision candidate yields the same final Y offset. Therefore this note establishes no ordinary player trajectory or net displacement finding. Those world-state/reachability dependencies remain open; pair coverage remains partial.

## Added source identities

All added hashes were recomputed from canonical 1.12.2 source bytes and match the exact source manifest:

| Source file | SHA-256 |
|---|---|
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` |
| `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` | `207a0ff4ee996916cd1fc73f47bfaae64f92719916cafed8395b1f2a8f797809` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e` |
| `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| `net/minecraft/util/math/Box.java` | `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a` |

No source, product, wiki, backlog, history, or Git files were modified. No tests, builds, gameplay, or runtime checks were performed.

