# Entity-contact response to the player: no delta found

Compared exact Mojmap source under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.20.4,1.20.5,1.20.6}\mojmap`, against the assigned three-way Java path inventory at `.native-source/20261030/1204-1206-pool44/whole-pair-inventory/three-way-java-path-inventory.csv`. The inventory confirms the source files below and supplies their whole-file SHA-256 identities. For the compared methods, the body hashes below are SHA-256 over the exact UTF-8 source substring from the method declaration through its matching closing brace, including whitespace.

## Ordinary entity-contact path

`world/entity/Entity.java`: `collide(Vec3)` (1.20.4: line 873; 1.20.5/1.20.6: line 874) asks `Level.getEntityCollisions` for shapes, then resolves movement against them. `world/level/EntityGetter.java:57-74` builds those shapes from nearby entities' bounding boxes after the `canCollideWith` predicate. This path constrains movement; it does not call `Entity.push` or itself add player velocity.

The overlapping-entity response is a separate path. `world/entity/LivingEntity.java` calls `checkAutoSpinAttack` and `pushEntities` late in `aiStep` (1.20.4: lines 2624-2630; 1.20.5/1.20.6: 2704-2710). On the client, `pushEntities` queries only `Player` candidates with the caller's bounding box and `EntitySelector.pushableBy(this)`, then calls `doPush`; `doPush` calls the candidate's `push(this)`. `RemotePlayer.tick` also calls `pushEntities` (1.20.4 line 77; 1.20.5/1.20.6 line 76). In the client selector, the candidate must be pushable and, for the team-collision part of the predicate, be the local player. The server branch instead queries all eligible entities, handles cramming damage and then pushes; that branch is not historical client movement.

For a local player candidate, dispatch reaches `LivingEntity.push(Entity)` (sleeping players do nothing), then `Entity.push(Entity)`. The latter rejects same-vehicle pairs and either entity with `noPhysics`; it also requires `Mth.absMax(dx,dz) >= 0.01F`. It derives the horizontal push from `sqrt(absMax(dx,dz))`, caps the reciprocal at 1, scales each component by `0.05F`, and calls `push(double,double,double)` for each non-vehicle pushable participant. `push(double,double,double)` adds the components to delta movement and sets `hasImpulse`. `LivingEntity.isPushable` requires alive, non-spectator and not on a climbable block. `Player.java` has no `push` or `isPushable` override in these sources.

Two vehicle tick call sites also reach a player response. `world/entity/vehicle/Boat.java` (1.20.4 line 338 onward; 1.20.5/1.20.6 line 333 onward) queries entities in the boat box inflated by `(0.2F,-0.01F,0.2F)` and invokes `this.push(candidate)` unless the candidate already carries the boat. The client does not take the server-only mounting branch; `Boat.push(Entity)` then admits another boat only when its box's `minY` is below this boat's `maxY`, and other entities only when their `minY` is at or below this boat's `minY`, before calling `super.push`.

`world/entity/vehicle/AbstractMinecart.java` (1.20.4 lines 290-304; 1.20.5/1.20.6 lines 289-303) checks overlaps after cart movement. A moving rideable cart (`horizontalDistanceSqr() > 0.01`) queries its box inflated by `(0.2F,0,0.2F)` through `pushableBy`; players take the `candidate.push(this)` branch. In the other branch, the type gate only pushes other minecarts. This call reaches `Player.push(Entity)` when the candidate is the local player. `AbstractMinecart.push(Entity)` itself is guarded by `!level.isClientSide`; that server-only override is not the client response taken by this candidate-directed call.

The client-only response bodies and candidate gates above are identical in the compared sources. Other inspected neighbor overrides add no changed player response: `Slime.push` only has a damage side effect for an Iron Golem; `Shulker.push` is empty; the minecart override is server-only. The remaining Bat, ArmorStand, Parrot, AbstractHorse and Warden pushability overrides are closed in [IS-PUSHABLE-RESIDUAL.md](IS-PUSHABLE-RESIDUAL.md). `LivingEntity.checkAutoSpinAttack` is a separate direct player-velocity response: while spinning, it scans the swept box, attacks the first overlapping living entity, sets spin ticks to zero and scales the player's velocity by `-0.2`; the code and call order are unchanged.

## Exact member-body hashes

Each listed member has the same hash in 1.20.4, 1.20.5 and 1.20.6.

| Source member | Lines (.4; .5/.6) | SHA-256 |
|---|---:|---|
| `world/entity/Entity.java` `push(Entity)` | 1398; 1414 | `f21db61f124b2eddd6b0643d7fa207a979cf3959e4639c54161bdc601d4ea113` |
| `world/entity/Entity.java` `push(double,double,double)` | 1429; 1445 | `85f53d8458e6b485be40dbfb87f4f5e971bb00c5381f0b4e57531e330179eb99` |
| `world/entity/Entity.java` `isPushable()` | 1514; 1534 | `0fef6371efa441af58da46a410872b77edc82a444ff0af1539be9efa5b92aaca` |
| `world/entity/LivingEntity.java` `push(Entity)` | 1975; 2032 | `96c85874f5236d61d488643f011302441335e79accf4c23c6febbed3c7829401` |
| `world/entity/LivingEntity.java` `pushEntities()` | 2671; 2751 | `1327c9f339d4ea36c9e0ce321ba657ca9bbd1715c2908a0768484590cc6c81ec` |
| `world/entity/LivingEntity.java` `checkAutoSpinAttack(AABB,AABB)` | 2699; 2779 | `f3144549306bdf950ffbed3cc26c88c3ae20caf76959fa28cdb9d7692888b4d8` |
| `world/entity/LivingEntity.java` `doPush(Entity)` | 2720; 2800 | `2c273a242b02c87adcbe7dbe9b8b66755b236512ce5d53c8e33057021e2f24e8` |
| `world/entity/LivingEntity.java` `isPushable()` | 2838; 2918 | `dd68ad807f9e6de73a8cc29814139deb1bda43b1efc7810d7ae404a5c6efeb0e` |
| `world/entity/EntitySelector.java` `pushableBy(Entity)` | 28 | `a2254f6b12f7fc612560608b05a7bc8207366dc4cb9b4f06d38a985c795de82a` |
| `world/level/EntityGetter.java` `getEntityCollisions(Entity,AABB)` | 57 | `cf5f501d3df02cc4a4bcbb339574b7cca3adf1b45b2e6ea8a0c51e0e65dc2952` |
| `world/level/Level.java` `getEntities(Entity,AABB,Predicate)` | 687; 686 | `c7d57fe6ccbeaffe3ce1d7ca5a6cad2b2f95e315e752a5236b8ef771506e9f3c` |
| `world/level/Level.java` typed `getEntities(EntityTypeTest,AABB,Predicate)` | 707; 706 | `4358e72a4af5fd5d1b4aa4170eab81e5a0c4b7401fb7ecfeff9400ba56ab6ce7` |
| `world/entity/vehicle/Boat.java` `push(Entity)` | 188; 183 | `0b1b9abe4ca1e82acdcc5aa30c9445067bb0846d4ac7a95f68853676c785746a` |
| `world/entity/vehicle/AbstractMinecart.java` `push(Entity)` | 638; 637 | `55121f4be0a05a617abe2662526cfe79e1ba72886720a0858312904f95f01e4d` |
| `world/entity/monster/Slime.java` `push(Entity)` | 225; 227 | `e184f9afce629633efe5c713db9ffb3a2386a9c10022c198a0065d31ba0a4ef6` |
| `world/entity/monster/Shulker.java` `push(Entity)` | 525; 517 | `e8176173d746b12bbc075ed3ae2a334282175fb068337090f6aacb4cc77fde3c` |
| `world/entity/vehicle/AbstractMinecart.java` `tick()` | 230; 233 | `718b05374607e744a8ba1176a5caaeac321cb65764253409b57412fb7f032701` in 1.20.4; `da20efd6f9a90182e33124b225fd8ac2e782ae9285511dc940ebc3c96b8e6166` in 1.20.5/1.20.6 |

The complete `AbstractMinecart.tick()` body hash changes between 1.20.4 and 1.20.5. In the inspected entity-contact segment, the query, moving-rideable gate, player branch and fallback gate retain the same order and conditions; local identifiers differ. The response method hashes and the surrounding queried/push-gated path were checked separately.

## Exclusions and residual scope

`ClientPacketListener.handleSetEntityMotion` applies a server-sent `ClientboundSetEntityMotionPacket` through `lerpMotion`; `handlePlayerCombatEnter` adds packet knockback to the local player's velocity. Those are network inputs, not the local entity-contact calculation. Attack/damage knockback and entity-specific attack logic were not folded into this ordinary contact response. `Player.attack` pushes its target, so that outgoing effect is outside the player-response slice.

`LocalPlayer.java` contains no direct entity push call. Its direct `setDeltaMovement` sites inspected here are suffocation correction, reset, flight input and spectator-mode cleanup; they do not implement entity-contact response. This closes only the assigned entity-contact response slice for the stated three versions. It is not a full tick or pair coverage audit and makes no pair-freeze claim. Runtime parity was not tested.
