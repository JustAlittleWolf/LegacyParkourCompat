# Early client entity push comparison

## Result

The first stable release in this interval with a reachable direct client-side player push is **1.9**. In 1.8.9, `LivingEntity` calls `pushAwayCollidingEntities()` only when `!world.isClient`. In 1.9 and each checked patch through 1.9.4, the call is unconditional. The new `EntityFilter.canBePushedBy(caller)` admits a local player as a client-side candidate after the spectator and pushability gates, while rejecting other client-world candidates in its client branch. The candidate's `PlayerEntity.push(neighbor)` then reaches `Entity.push` unless the player is sleeping.

This pins the caller/predicate boundary at 1.9, not an intermediate 1.9 patch. It is a source finding for a fixed client profile; no runtime parity claim is made.

## Paired source evidence

All paths below are relative to `decompiled_minecraft/<version>/ornithe-feather/`. Mapping family is Ornithe Feather throughout.

| Release | `LivingEntity` caller / method | `EntityFilter.canBePushedBy` | Player override / base writer |
|---|---|---|---|
| 1.8.9 | `net/minecraft/entity/living/LivingEntity.java:1452-1479` | No `canBePushedBy` method; `net/minecraft/entity/EntityFilter.java` only supplies the general `NOT_SPECTATOR` predicate used at 1465 | `net/minecraft/entity/Entity.java:947-978`; no `PlayerEntity.push` override |
| 1.9 | `net/minecraft/entity/living/LivingEntity.java:1700-1738` | `net/minecraft/entity/EntityFilter.java:55-92` | `net/minecraft/entity/living/player/PlayerEntity.java:1879-1890`; base `net/minecraft/entity/Entity.java:1057-1092` |
| 1.9.1 | `net/minecraft/entity/living/LivingEntity.java:1704-1742` | `net/minecraft/entity/EntityFilter.java:55-92` | `net/minecraft/entity/living/player/PlayerEntity.java:1879-1890`; base `net/minecraft/entity/Entity.java:1057-1092` |
| 1.9.2 | `net/minecraft/entity/living/LivingEntity.java:1704-1742` | `net/minecraft/entity/EntityFilter.java:55-92` | `net/minecraft/entity/living/player/PlayerEntity.java:1879-1890`; base `net/minecraft/entity/Entity.java:1057-1092` |
| 1.9.3 | `net/minecraft/entity/living/LivingEntity.java:1713-1751` | `net/minecraft/entity/EntityFilter.java:56-93` | `net/minecraft/entity/living/player/PlayerEntity.java:1886-1897`; base `net/minecraft/entity/Entity.java:1061-1096` |
| 1.9.4 | `net/minecraft/entity/living/LivingEntity.java:1713-1751` | `net/minecraft/entity/EntityFilter.java:56-93` | `net/minecraft/entity/living/player/PlayerEntity.java:1886-1897`; base `net/minecraft/entity/Entity.java:1061-1096` |

The row ranges include the enclosing method bodies and their adjacent call site where applicable. Across 1.9.0-1.9.4, the relevant filter decision, loop order, push dispatch, sleeping guard, and base writer behavior are unchanged. Line offsets in 1.9.3 reflect unrelated preceding source additions.

## Exact behavior and order

**1.8.9:** after travel/movement-relative work, `LivingEntity` opens profiler section `push`; it invokes the collision method only under `if (!this.world.isClient)`. The method queries `getShape().grown(0.2F, 0.0, 0.2F)` and filters `NOT_SPECTATOR` then `isPushable()`. It walks the returned list from index zero upward and calls `entity.push(this)`. Thus this route cannot write client-side player velocity in 1.8.9.

**1.9-1.9.4:** after `moveRelative`, the caller opens profiler section `push`, calls `pushAwayCollidingEntities()` without a logical-side guard, then pops the section. The method queries the un-grown `this.getShape()`, asks `EntityFilter.canBePushedBy(this)`, iterates list order by increasing index, and dispatches each result through `entity.push(this)`.

The 1.9 predicate first captures the caller's team and collision rule. A caller rule of `NEVER` returns an always-false predicate. Otherwise it composes `NOT_SPECTATOR` before a predicate that first rejects `!candidate.isPushable()`. On a client world it proceeds into the collision-rule checks only when the candidate is a `PlayerEntity` and `isLocal()`; other candidates return false. For the local player candidate it rejects candidate rule `NEVER`, then applies the existing `HIDE_FOR_OWN_TEAM` / `HIDE_FOR_OTHER_TEAMS` and allied-team conditions. On a server world, the collision-rule checks apply normally to every pushable candidate.

For a local player candidate, virtual dispatch enters `PlayerEntity.push(neighbor)`. It first checks `!this.isSleeping()` and then calls `super.push(neighbor)`. `Entity.push` next checks `!hasSameVehicle(neighbor)`, then both `noClip` flags, then computes `d = neighbor.x - player.x`, `e = neighbor.z - player.z`, `f = absMax(d,e)`. Only if `f >= 0.01F` does it take `sqrt(f)`, divide `d` and `e` by it, set `g = 1.0 / f`, clamp only `g > 1.0` to 1, multiply both components by `g`, then by `0.05F`, then by `1.0F - player.pushSpeedReduction`. It adds `(-d, 0, -e)` to the player only if the player has no passengers, and `(d, 0, e)` to the neighbor only if the neighbor has no passengers. `addVelocity` adds the values to the three velocity fields. This preserves the source's operation order and float-literal promotions.

The 1.8.9 base writer instead checks the direct rider/vehicle pair (`entity.rider != this && entity.vehicle != this`), uses rider-null checks for each velocity write, and has no `PlayerEntity.push` sleeping override in the checked class. Those are terminal-writer differences, but they do not make the client path reachable in 1.8.9 because the caller guard stops it first.

## Standalone reachable witness

In a 1.9 client world, let an awake, non-spectator local player with `isPushable() == true` and collision rule `ALWAYS` overlap a nearby ordinary living entity whose caller collision rule is `ALWAYS`. When that other living entity runs its normal tick, its unguarded push section queries candidates. The local player passes `NOT_SPECTATOR`, `isPushable`, and the client `PlayerEntity && isLocal()` branch; with default/unrestricted team rules it passes the remaining rule checks. The loop invokes `localPlayer.push(otherEntity)`. If they are not in the same vehicle, neither is no-clip, horizontal separation reaches the `0.01F` threshold, and the player has no passengers, the base writer adds its negative horizontal delta directly to the local player's velocity. In 1.8.9 the same client-side living tick skips the call before querying candidates, so this witness does not reach the writer.

This witness is standalone and does not rely on climbing, vehicles, another player's movement, damage, or a server running historical physics.

## Provenance and hashes

The canonical source identity is the exact checked-out Feather tree plus its local provenance, not an online lookup. 1.8.9 and 1.9.4 carry adjacent provenance and source-hash sidecars; 1.9-1.9.3 carry hidden nested `.decompile-provenance.json` manifests. Every requested class hash below matches that release's local provenance source entry (or, for the two adjacent-manifest trees, its `.sources.sha256` inventory). Manifest hashes identify the exact local provenance bytes used here.

| Release | Local provenance file | Provenance SHA-256 | Feather mapping artifact identity |
|---|---|---|---|
| 1.8.9 | `decompiled_minecraft/1.8.9/ornithe-feather.provenance.json` | `BA066FC8F1C7CEB9819D2106338D9E9F2C2B96EB5A8329EB40E171B1F9889185` | `feather-gen2-1.8.9+build.2` |
| 1.9 | `decompiled_minecraft/1.9/ornithe-feather/.decompile-provenance.json` | `8DAACB7AF8F210E716EE4B34C43B8A5D6F6B4B19831D2FD725CBE16ECF330A1A` | `feather-gen2-1.9+build.2`; artifact input SHA-256 `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a` |
| 1.9.1 | `decompiled_minecraft/1.9.1/ornithe-feather/.decompile-provenance.json` | `E9FB0DA6511597E2DE953A143F9570B82243E5B9504603686E557F06271DA64A` | `feather-gen2-1.9.1+build.2`; artifact input SHA-256 `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3` |
| 1.9.2 | `decompiled_minecraft/1.9.2/ornithe-feather/.decompile-provenance.json` | `9928B467DEB35C7E7F891B4FEE1627A68FEECDF233C2076909FD4834B0380510` | `feather-gen2-1.9.2+build.2`; artifact input SHA-256 `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859` |
| 1.9.3 | `decompiled_minecraft/1.9.3/ornithe-feather/.decompile-provenance.json` | `43325CDD53CA34B88EE9AF8464782B323A948B98C8A556C4761F1E5737B84C3A` | `feather-gen2-1.9.3+build.2`; artifact input SHA-256 `fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3` |
| 1.9.4 | `decompiled_minecraft/1.9.4/ornithe-feather.provenance.json` | `5C913715BED763369CEB5FD171CAB3E63C43298023078C3683F96C7EA8FED3CD` | `feather-gen2-1.9.4+build.2` |

Source-file SHA-256 values (paths are relative to each release's `ornithe-feather/` root):

| Release | `LivingEntity.java` | `EntityFilter.java` | `PlayerEntity.java` | `Entity.java` |
|---|---|---|---|---|
| 1.8.9 | `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e` | `d97be58e0b8e965e06a0b989685f156be517d89d3b4d0f3154adf7bd3612b6b9` | `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88` | `d4c10932cb5bb1067a5e58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` |
| 1.9 | `5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb` | `f45827ffebad59c151aa5efa834824ba4d63bf60a645d14909df5d41f4b6e440` | `edbb46236d1e42630e600e4afcb434eb6e9adecbfc4ac7e15710a5dea50010b1` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` |
| 1.9.1 | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` | `f45827ffebad59c151aa5efa834824ba4d63bf60a645d14909df5d41f4b6e440` | `d7035f08f03b01823dff70ca504f7244f9b0753542cc0f6dd7301e66f8866b71` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` |
| 1.9.2 | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` | `f45827ffebad59c151aa5efa834824ba4d63bf60a645d14909df5d41f4b6e440` | `1fc5107b9b7f5711105d521a28829f2466fe9f297e832f4d209e6ec422111aac` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` |
| 1.9.3 | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` | `91158a5477935c911178433e1b2628d1d063604a23b08baee4706dd286e7e813` | `ef96c8a00443f724eadad05a6a4dcc8a8a2e23cd4c3390b9e0f1f78c9ff4d1af` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` |
| 1.9.4 | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` | `91158a5477935c911178433e1b2628d1d063604a23b08baee4706dd286e7e813` | `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` |

For the 1.8.9 and 1.9.4 source-hash sidecars, respectively, the inventory-file SHA-256 values are `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`.

## Limits

- This is a bounded comparison of the requested call, filter, and direct player velocity writer across 1.8.9 and every stable 1.9 patch through 1.9.4; it is not full tick-graph or pair coverage.
- It does not locate the exact change within 1.9 pre-releases or prove changes in unexamined versions.
- It does not follow later climbing-specific `isPushable()` changes or non-finite fallback behavior.
- It makes no claims about vehicle/non-player movement, server-side historical physics, runtime parity, implementation, or tests.