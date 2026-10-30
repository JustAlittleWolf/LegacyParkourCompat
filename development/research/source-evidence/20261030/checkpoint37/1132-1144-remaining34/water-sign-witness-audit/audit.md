# Independent audit: water sign witness attempt

Pair: 1.13.2 → 1.14.4, Ornithe Feather Gen2 build 2. Scope: audit only the water-threshold witness claims and referenced ordinary-gates closure/audit. Canonical sources were read from the two stated `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\ornithe-feather` roots. No collision trajectory was constructed.

## Verdict

The slow-falling threshold arithmetic, placement of the player swimming correction before the base `moveRelative` snapshot, player `verticalSpeed` producer search, and same-tick swimming/sprinting state account are source-supported. The collision-free ordinary-player route is correctly rejected under the stated preconditions (nonpositive entry Y, zero vertical input, no collision, ordinary gravity enabled, not sprinting). No collision-based route is closed.

One source-trace omission affects only the still-open collision-dependent case: 1.14.4 water movement has a pre-damping `collidingHorizontally && isClimbing()` correction that sets Y to `0.2` (`LivingEntity.java:1907–1911`). The witness discusses the later post-gravity `0.3F` climb-up correction but not this earlier new-side correction. This does not weaken the collision-free rejection; it should be included in any collision-route analysis because it can alter the value entering the threshold window.

## Claim checks

### Threshold interval and conditional divergence

1.13.2 takes `d=0.01` only when entry `velocityY <= 0` and slow falling is active (`LivingEntity.java:1478–1484`). It updates velocity, moves, applies the `0.8F` Y damping, then tests the post-damping value (`:1639–1649`). 1.14.4 caches entry sign in `bl` (`LivingEntity.java:1786–1793`), then updates velocity and moves before scaling by `(f, 0.8F, f)` and applying the special-case test to the scaled value (`:1904–1921`).

For `d=0.01`, the second bound is `|y - 0.000625| < 0.003`, hence `-0.002375 < y < 0.003625`. The first bound is `|y - 0.005| >= 0.003`, hence `y <= 0.002` or `y >= 0.008`. Their positive intersection is `0 < y <= 0.002`; `y=0.000625` satisfies both. With entry Y nonpositive, 1.14.4 has `bl=true`; at `y=0.000625` it assigns `-0.003`. 1.13.2's fresh post-damping `y <= 0` test is false, so it subtracts `d/16` and reaches zero. This is conditional arithmetic only, not evidence that movement attains the value.

For default `d=0.08`, both magnitude predicates refer to the same center `0.005` with mutually exclusive `<0.003` and `>=0.003` constraints, so the special-case window is empty. Threshold arithmetic in the witness is correct.

### Swimming correction versus base-call sign snapshot

The 1.13.2 `PlayerEntity.moveRelative` first executes its swimming correction (`PlayerEntity.java:1442–1451`) and only then calls `super.moveRelative` (`:1458–1465`). The 1.14.4 override follows the same order (`PlayerEntity.java:1381–1391`, then `:1394–1405`). The base slow-fall sign is captured inside the later superclass call (`LivingEntity.java:1480–1484` old; `:1788–1793` new). Therefore swimming correction can change the value before the snapshot; it cannot be treated as a post-snapshot sign crossing. If the base entry remains nonpositive, the cached 1.14.4 sign and the 1.13.2 entry test both take the slow-falling `d=0.01` choice.

### Vertical-input producers for players

`LivingEntity.verticalSpeed` is read as the second movement input at the travel call (`LivingEntity.java:1926` old; `:2227` new). In 1.13.2, `moveRelative(forwards, sideways, upwards)` passes those components to `Entity.updateVelocity`; its named `forwards` component is added to Y (`Entity.java:1057–1073`). Since travel passes `verticalSpeed` as the second `moveRelative` argument, it reaches that Y component. In 1.14.4 the supplied vector's Y component is preserved by rotation and added to velocity (`Entity.java:1062–1076`); travel supplies `verticalSpeed` as vector Y.

A whole-pair source search for `verticalSpeed` references found only the `LivingEntity` field and travel read plus writes in `MobEntity` and `DolphinEntity`; there are no writes in player, local-player, or other `LivingEntity` source. The ordinary player's vertical input is therefore its default zero under this source-only model. This closes the specific producer question for direct source references; it does not prove arbitrary external/reflection mutation impossible.

### Same-tick state gates

Both `Entity.tick` bodies call the water-state helper before returning to `LivingEntity.tick`: old `Entity.java:393–395` then helper at `:947–950`; new `:355–357` then helper at `:942–945`. The helper runs `checkWaterState`, fluid-height update, and `updateSwimming` in that order. `LivingEntity.tick` calls `super.tick()` before `mobTick()` (old `:1689–1692`, `:1752`; new `:1979–1982`, `:2046`). The local-player sprint cancellation runs during `mobTick`; its old and new swimming branches can clear sprint without immediately recomputing the swimming flag (`LocalClientPlayerEntity.java:748–758` old; `:679–689` new). Thus the witness's same-tick `isSwimming() && !isSprinting()` possibility is consistent with call order. The water gravity block itself is skipped while no-gravity or sprinting (`LivingEntity.java:1644–1649` old; `:1912–1922` new).

### Collision-free rejection and boundary

With base-call entry `Y <= 0`, ordinary player's `verticalSpeed == 0`, and collision-free `move`, `updateVelocity` adds zero Y and `move` does not collision-resolve Y. The 1.13.2 `0.8F` damping and 1.14.4 `0.8F` vector scale preserve a nonpositive Y, so neither can reach the required `0 < Y <= 0.002` post-damping window. This supports rejecting the collision-free witness.

Collision-dependent movement remains open. In addition to the later `0.3F` climb-up correction (old `:1652–1654`, new `:1924–1927`), the new water path has its earlier `collidingHorizontally && isClimbing()` Y=`0.2` adjustment after move and before damping (`:1907–1911`). The exact shapes, movement resolution, threshold value, and masking effects have not been audited here.

## Evidence identity

Complete-file SHA-256 hashes rechecked against the witness report:

| Endpoint | Source | SHA-256 |
|---|---|---|
| 1.13.2 | `net/minecraft/entity/living/LivingEntity.java` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| 1.14.4 | `net/minecraft/entity/living/LivingEntity.java` | `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61` |
| 1.13.2 | `net/minecraft/entity/living/player/PlayerEntity.java` | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |
| 1.14.4 | `net/minecraft/entity/living/player/PlayerEntity.java` | `2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df` |
| 1.13.2 | `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.14.4 | `net/minecraft/entity/Entity.java` | `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55` |
| 1.13.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |
| 1.14.4 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce` |
| 1.13.2 | `net/minecraft/entity/living/mob/MobEntity.java` | `525837fde9f889b47f32875a2d2ad5da0a4d8fb4bd91be92f5ea7da00e835502` |
| 1.14.4 | `net/minecraft/entity/living/mob/MobEntity.java` | `3396c220d5402b01bf84cabc3f4eb39cc41ea162de2c22e92bb6d13d6f32aef9` |
| 1.13.2 | `net/minecraft/entity/living/mob/water/DolphinEntity.java` | `628ac17aa19c849acb292736f5dc8a56362a31f90d1c0ac9eda5a52cb69f522c` |
| 1.14.4 | `net/minecraft/entity/living/mob/water/DolphinEntity.java` | `b772648d3ca14acb2160127cbca6b9826dbf982d6b66624d1cbecfa19dccce9a` |
