# F-S4-PLAYER-ENTITY-PUSH — source finding snapshot

## Claim

For a Player in the paired `can_glide_through` block states while fall-flying, the 1.21.11 source can apply a direct Player velocity response from nearby entity pushing where 1.21.10 does not. This is a separate consumer of the climbable tag difference recorded in `F-S3-GLIDE-THROUGH-CLIMBABLE`; it does not broaden that finding's fall-flying travel claim.

## Exact boundary

The path is `LivingEntity.isPushable()` -> `LivingEntity.pushEntities()` -> `LivingEntity.doPush(Entity)` -> neighboring `Entity.push(Entity)` -> the Player argument's `Entity.push(double,double,double)`. Player has no `isPushable` override in this inheritance chain (`LocalPlayer` -> `AbstractClientPlayer` -> `Player` -> `Avatar` -> `LivingEntity`). The paired `isPushable` bodies are identical and return `isAlive() && !isSpectator() && !onClimbable()`.

## Reachability and result

The bounded state requires an alive, nonspectator Player who is fall-flying with the flight ability disabled, and whose current block state is one of the seven states added to the 1.21.11 `can_glide_through` tag expansion. Existing `onClimbable()` logic then returns true in 1.21.10 and false in 1.21.11. The vanilla tick path reaches `LivingEntity.pushEntities()` through virtual `tick()`/`aiStep()` dispatch and Player's super calls. If a nearby pushable entity is returned, the entities are not in the same vehicle, neither relevant entity is `noPhysics`, the Player is not a vehicle, and the horizontal separation meets `Mth.absMax(dx,dz) >= 0.01F`, the Player's argument-side push guard rejects the direct velocity write in A and permits it in B. The write adds the computed vector to Player delta movement. A/B preserve the same push formula; B checks all three vector components are finite and sets `needsSync`, while A adds the vector and sets `hasImpulse`.

This is a source-level movement consequence only. It does not claim that a nearby entity is always available, that the other entity's movement differs, or that the result was measured at runtime.

## Paired source evidence

Source publication IDs: `PUB-1.21.10-MOJMAP-2026-10-07` and `PUB-1.21.11-MOJMAP-2026-10-07`.

| Owner/member | 1.21.10 | 1.21.11 |
|---|---|---|
| `LivingEntity.isPushable()Z` | `LivingEntity.java` 3077-79; file SHA-256 `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` | `LivingEntity.java` 3203-05; file SHA-256 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` |
| `LivingEntity.pushEntities()` | `LivingEntity.java` 2940-64 | `LivingEntity.java` 3066-90 |
| `LivingEntity.doPush(Entity)V` | `LivingEntity.java` 2989-91 | `LivingEntity.java` 3115-17 |
| `Entity.push(Entity)V` | `Entity.java` 1720-49; file SHA-256 `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18` | `Entity.java` 1738-67; file SHA-256 `32314478c6036fa9f3f3cc409c61c622eeffc5a282d60e1011a1a33cc29cf18a3` |
| `Entity.push(DDD)V` | `Entity.java` 1755-58 | `Entity.java` 1775-80 |
| `Level.getPushableEntities` | `Level.java` 752-54 | `Level.java` 740-42 |
| Player inheritance / no override | `Player.java` class 121; `Avatar.java` class 172; `LivingEntity.isPushable()` as above | `Player.java` class 124; `Avatar.java` class 172; `LivingEntity.isPushable()` as above |

The `Entity.push(Entity)` bodies are not claimed identical. Only their shared threshold/gating and direct Player result described above are relied on. The climbable tag resource entry and exact block-state expansion are documented in F-S3; the original client artifact records and hashes are listed in the run ledger.

## Scope and disposition

`S4-player-entity-push` closes the direct Player pushability dependency `D-S3-CLIMBABLE-PUSHABILITY`. Non-player movement, vehicle movement, damage, and combat are excluded. Independent finding-specific blind source review is pending. This file is an immutable finding snapshot and does not freeze the source pair or authorize implementation.
