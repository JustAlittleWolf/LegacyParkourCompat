# Corrected creative-flight fall-distance boundary witness (r5)

## Result and correction scope

This is the narrow geometry correction to r4 (`coverage-r4.md`, SHA-256 `FFCA062E77F47E05017551115FD2C08FE53167705800B323EA55DE284F44920C`). The independent r4 audit accepted the adjacent source cutover and the `-0.15` preceding flight displacement, but rejected its geometric witness because it placed the player at feet `0.525` before that descent. This r5 moves the initial feet height to `0.675`; after the source-derived descent, the edge-backoff query is evaluated at feet `0.525`. No boundary, operation, or pair-coverage expansion is claimed.

The bounded adjacent cutover remains **1.21.1→1.21.2** for non-passenger creative-flight fall-distance reset timing. The 1.21.1 `Player.travel` flight branch resets fall distance after `super.travel`; the 1.21.2 `Player.aiStep` resets it before inherited movement, and its `Player.travel` branch no longer resets after travel. This report concerns the resulting input to the unchanged player edge-backoff predicate only.

## Exact source identity

Both endpoints are canonical Mojmap source trees. 1.21.1 ready marker `decompiled_minecraft/1.21.1/mojmap.ready.json` binds version and mapping and source-manifest SHA-256 `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`; marker SHA-256 is `988b38ac59491f9430d5bd1968db0fdaacb777a63fef745ca3e20342237a0310`. 1.21.2 mapping-local `.decompile-provenance.json` SHA-256 is `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575` and binds its Java source paths/hashes. Listed bytes were checked against those ready/provenance source bindings.

| Canonical relative path | 1.21.1 SHA-256 | 1.21.2 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/player/Player.java` | `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` | `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de` |
| `net/minecraft/client/player/LocalPlayer.java` | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` |
| `net/minecraft/world/entity/LivingEntity.java` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` |
| `net/minecraft/world/entity/Entity.java` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` |
| `net/minecraft/world/level/CollisionGetter.java` | `f21daa1ed0365cc16a2638373d702fbaeb4242ad12a80a41105cf43c711e6d95` | `42301653b90eea59caf787a92699bd707d2454155b7aa19e4ff2de57beef774f` |

## Reset and post-flight state

In 1.21.1, `Player.aiStep` proceeds to `super.aiStep()` without a flight reset (`Player.java:513–534`). Its non-passenger flight branch in `Player.travel` calls `super.travel`, applies the saved vertical velocity times `0.6`, and calls `resetFallDistance()` after inherited travel (`Player.java:1456–1477`). In 1.21.2, `Player.aiStep` resets when `abilities.flying && !isPassenger()` before `super.aiStep()` (`Player.java:543–555`); the flight branch of `Player.travel` applies the vertical velocity write but has no post-travel reset (`Player.java:1445–1466`).

For the preceding local-player flight tick, hold shift with creative flight and use the exact source path's `-1 * abilities.getFlyingSpeed() * 3.0F`; the default flying speed is `0.05F`, so it contributes `-0.15F` vertically. Keep `isFallFlying() == false` before this tick and the later edge-backoff tick. With no collision/effect that changes the vertical move, `Entity.move` applies the downward `-0.15` displacement; its airborne negative-Y fall accounting increments by `0.15` (`Entity.java:1163–1178` in 1.21.1 and `1216–1231` in 1.21.2). Consequently, after this tick 1.21.1's travel wrapper has reset the accumulated value to `0`, whereas 1.21.2 retains `0.15` after its pre-move reset.

## Corrected edge geometry

Use a full-collision support block with top face at `y=0` and a straight edge at `z=0`, occupying the supported side `z<=0`. Place the airborne player's bounding box so its lower face is at `y=0.675` before the preceding flight tick. Let its horizontal footprint straddle the edge by `0.001` (`minZ=-0.001`), with its other horizontal extent over the block. After the preceding `-0.15` descent, its feet are at `y=0.525`; keep that post-descent position for the next predicate evaluation. The starting height in r4 was incorrectly given as the pre-descent height.

The unchanged `Player.isAboveGround(maxUpStep)` first accepts `onGround`; for this airborne witness it instead requires `fallDistance < maxUpStep` and a negative `canFallAtLeast(0,0,maxUpStep-fallDistance)` result. Default non-passenger `maxUpStep` is `0.6`. `canFallAtLeast` queries the AABB from the player's current feet minus the requested depth and `1.0E-5F` through the current feet (`Player.java:1121–1127` in 1.21.1; `1117–1124` in 1.21.2).

- On 1.21.1, `fallDistance=0`, so the query depth is `0.6` and its lower face is `0.525 - 0.6 - 0.00001 = -0.07501`. The query intersects the support block, so `noCollision` is false and `isAboveGround(0.6)` is true.
- On 1.21.2, `fallDistance=0.15`, so the query depth is `0.6 - 0.15 = 0.45` and its lower face is `0.525 - 0.45 - 0.00001 = +0.07499`. It stays above the support top, so `noCollision` is true and `isAboveGround(0.6)` is false.

For the follow-up move, turn flight off, keep shift held, and request a small positive-Z SELF displacement from the footprint that is only `0.001` over the support edge. The shared `maybeBackOffFromEdge` gate accepts a non-flying, non-positive-Y SELF move while staying on a ground surface; `isStayingOnGroundSurface()` is shift state. When the old profile's `isAboveGround` is true, the backoff loop probes horizontal increments in `0.05` steps and zeros a positive-Z request no larger than `0.05` when it would leave the support. When the new profile's predicate is false, this edge-backoff loop is skipped and the same request reaches ordinary collision clipping. At feet `0.525`, horizontal movement has no block overlap in the open volume beyond the edge. Thus the geometry supports the predicate/backoff divergence at this constructed state.

**Collision-volume precondition:** `CollisionGetter.noCollision(entity, AABB)` checks block collisions and entity collisions; with a non-null entity it also checks world-border collision (`CollisionGetter.java:46–63, 88–90` in 1.21.1; `48–66, 98–100` in 1.21.2). The queried volumes must contain no other colliding entity and must not intersect the world-border collision shape. The support block is the only collision in the downward query. There must also be no other block collision in the query or requested horizontal travel volume. The player is off ground, nonpassenger, not fall-flying, and has no effect/state that alters the stated displacement; flight is disabled before the follow-up SELF move. These are explicit construction assumptions, not inferred world guarantees.

## Limits

This is source-derived static reasoning for one constructed adjacent-version witness, not a runtime result. It establishes only this reset-order boundary and its corrected edge-backoff dependency. It does not freeze the full 1.20.6→1.21.11 pair or close its other release pairs, movement branches, predicates, or coverage gates. No product, implementation, build, test, or runtime state was inspected or changed.
