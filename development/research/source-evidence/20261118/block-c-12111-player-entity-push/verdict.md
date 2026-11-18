# Independent source audit: player entity push

**Decision:** The working finding's bounded movement claim is supported by the exact 1.21.10→1.21.11 Mojmap sources, conditional on the stated paired `CAN_GLIDE_THROUGH` / `CLIMBABLE` tag membership. This verdict is bound to finding SHA-256 `948856DC680F13BDA9CE47A18F65182BC4B3AE9D4862A0F55009DBCD36BF0670` (Git blob `5ac78867bdda00fbc6ba39e82b7c765f8b9ff617`) at product launch commit `583f8a9b`.

## Trace and finding

`Player.tick()` calls through `LivingEntity.tick()` to `aiStep()`; `Player.aiStep()` calls `super.aiStep()`, and `LivingEntity.aiStep()` reaches `pushEntities()` on client and server. Its server-only cramming branch does not gate entity pushing. `pushEntities()` calls `doPush(entity)`, which calls `entity.push(this)`. In `Entity.push(Entity)`, the nearby entity is the receiver and the Player is the argument; the Player's `isPushable()` check gates the argument-side direct velocity write.

In 1.21.10, `LivingEntity.onClimbable()` has no fall-flying / `CAN_GLIDE_THROUGH` early return. In 1.21.11 it returns false for that condition before the `CLIMBABLE` test. `LivingEntity.isPushable()` is unchanged: alive, nonspectator, and not climbable. Given the finding's tag-state precondition, that makes the Player fail the argument-side pushability check in 1.21.10 and pass it in 1.21.11.

The entity-push vector formula and operation order are unchanged. The scalar `push(x, y, z)` adds the vector to delta movement in both versions. The 1.21.11 source adds finite-value guards and marks `needsSync`; 1.21.10 sets `hasImpulse`. This supports the bounded movement consequence for finite vectors while retaining the distinct side effects.

## Exact source identities

Mapping family is Mojmap, as identified by the local source paths. SHA-256 values are for the exact source files reviewed.

| Release | File | SHA-256 |
|---|---|---|
| 1.21.10 | `net/minecraft/world/entity/Entity.java` | `8361DBB86FE6C975D21F69D008377B6F191669BE751150C842517E9D0346FA18` |
| 1.21.10 | `net/minecraft/world/entity/LivingEntity.java` | `B8B49D60769203F7BD5AFE4A1BFFCDCDBEC30BE28960324CDC43A2DF85A6EB66` |
| 1.21.10 | `net/minecraft/world/level/Level.java` | `DC79073F598A2CB86FC1384E4459E9D384B10F4BEB3694159786F2804B7D19FE` |
| 1.21.11 | `net/minecraft/world/entity/Entity.java` | `32314478C6036FA9F3F3CC409C61C622EEFC5A282D60E1011A1A33CC29CF18A3` |
| 1.21.11 | `net/minecraft/world/entity/LivingEntity.java` | `19ED2D565858C401C69A06750B054A633A20EA864CAB3A753B5C767F0BDD60E8` |
| 1.21.11 | `net/minecraft/world/level/Level.java` | `DE329FDBECA632A1B63074E1735890B2624ABFC57B7A579F43EEF36CAEFEF98D` |

Key witnesses: `LivingEntity.onClimbable()` (1.21.10 line 1662; 1.21.11 line 1669), `LivingEntity.pushEntities()` (2940; 3066), `LivingEntity.doPush()` (2989; 3115), `LivingEntity.isPushable()` (3077; 3203), `Entity.push(Entity)` (1720; 1738), scalar `Entity.push(double,double,double)` (1755; 1775), and `Level.getPushableEntities()` (752; 740). `Player.tick()` and `Player.aiStep()` call-chain witnesses are at lines 240 / 455 / 466 in 1.21.10 and 238 / 452 / 463 in 1.21.11; `LivingEntity.tick()` invokes `aiStep()` at lines 2582 and 2650 respectively.

## Limits

Tag membership is a dependency assumed from the paired tag claim and was not reaudited here. The local decompiled trees lacked `.decompile-provenance.json`, so exact mapping-build provenance could not be attested beyond the Mojmap paths. No 26.2 analogue was needed for this bounded pair claim. Static source audit only; no tests or launches.
