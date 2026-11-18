# 1.14.2–1.14.3 source comparison: ordinary dry horizontal travel

## Scope and verdict

Bounded slice: ordinary dry, unmounted, local, nonsprinting player travel; relative input acceleration, dry ground/air input scaling and movement-speed selection, and horizontal drag. Yaw, block slipperiness, and `onGround` are supplied inputs. **No behavioral difference found in this slice** between Java 1.14.2 and 1.14.3. This is slice coverage only, not pair completion, acceptance, implementation, or runtime-parity evidence.

## Immutable source identity

Both trees are `yarn` mapped. The manifest hashes below were recomputed from `.decompile-provenance.json`; used source hashes were recomputed from source bytes and equal their manifest `sources` entries.

| Release | Yarn artifact | Yarn artifact SHA-256 | Intermediary SHA-256 | Raw client SHA-256 | Provenance SHA-256 |
|---|---|---|---|---|---|
| 1.14.2 | 1.14.2+build.7 | `3c91c928d10797ba688b1f7e61063fd4bc56a3ee0f66fa2609ef960ec08b239b` | `f73883d007c64858ae0f26533fe47ec5d3a051cede6892c8d6531fc5122e459e` | `9b740c9fd4955981125ebd3cd086baa8b4392963d043a3480b5b942a96637c25` | `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a` |
| 1.14.3 | 1.14.3+build.13 | `ad9e77f907cfc6780fb348ad4b3a948ffc8cea3bfe50abcc792428645e49e06e` | `f2e9990c00fa87ddc7158a95afb735a947ae26d1997b52916226e11c612542a9` | `4063062677433988e012fba53e42e4c21adb6919ac7f061f19f3c6e75b334e07` | `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a` |

| Relative source path | 1.14.2 SHA-256 | 1.14.3 SHA-256 |
|---|---|---|
| `net/minecraft/entity/Entity.java` | `7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc` | `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14` |
| `net/minecraft/entity/LivingEntity.java` | `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78` | `e8aed9c63c0dfbe88e4b9e11c58881bc6e04b9cbaecbde5e3606e3afa406d72b` |
| `net/minecraft/entity/player/PlayerEntity.java` | `333bb39db18af4cef320eb73b7abe359d438fb2a6ca7d8798ee449da6c684398` | `e3f361ac4e369d01473f3d84044ee33f9685d182c4485e2e80b7f62756950963` |

## Caller and slice inventory

| Slice | 1.14.2 | 1.14.3 | Result |
|---|---|---|---|
| Living tick input preparation and virtual travel call | `LivingEntity.java:2204–2210` | `LivingEntity.java:2214–2220` | Same order: multiply `sidewaysSpeed` and `forwardSpeed` by `0.98F`, damp the separate upward field by `0.9F`, run `initAi`, construct `Vec3d(sidewaysSpeed, upwardSpeed, forwardSpeed)`, then call `this.travel`. Local `PlayerEntity.tick` calls `super.tick` (`PlayerEntity.java:191–219` / `192–220`), and `LivingEntity.tick` calls virtual `this.tickMovement()` (`LivingEntity.java:2029` / `2039`). `PlayerEntity.tickMovement` enters `super.tickMovement()` before the base implementation reaches travel; its coefficient/attribute writes occur after that travel call and therefore affect subsequent travel. The differently named `BoundingBox`/`Box` local is outside the target math. |
| Player travel override and movement-speed accessor | `PlayerEntity.java:1362–1390, 1406–1408` | `PlayerEntity.java:1364–1392, 1408–1410` | Same applicable nonswimming, nonflying delegation to `super.travel(arg)`; no extra dry input scale or movement-speed override logic. The accessor returns `(float) getAttributeInstance(MOVEMENT_SPEED).getValue()` on both sides. The swimming/flying branches are excluded. |
| Player movement coefficient setup | `PlayerEntity.java:475–503` and `LivingEntity.java:2133` | `PlayerEntity.java:476–504` and `LivingEntity.java:2143` | Same local/player path: `PlayerEntity.tickMovement` calls `super.tickMovement()` first, so the base travel uses the existing `field_6281`; afterward it resets that field to `0.02F` and applies the sprint-only `0.005999999865889549` increment for subsequent travel. For a sustained nonsprinting case this is `0.02F`. The attribute local keeps the same server-only base-value update and `setMovementSpeed((float)lv.getValue())`; the player override above reads the movement-speed attribute as a float. No change to the coefficient, cast or gate. |
| Dry normal branch, input acceleration, movement and horizontal drag | `LivingEntity.java:1770, 1823–1850` | `LivingEntity.java:1780, 1833–1860` | Same enclosing `canMoveVoluntarily() || isLogicalSideForUpdatingMovement()` gate and same sequence and expressions: obtain supplied block slipperiness `v`; compute `w = onGround ? v * 0.91F : 0.91F`; call `updateVelocity(method_18802(v), arg)`; apply the climb-only `method_18801` gate; move with current velocity; then write horizontal components as `lv8.x * w` and `lv8.z * w`. No horizontal expression, gate, type or operation-order difference found. Climb/collision effects are not assessed. |
| Ground/air input acceleration selector | `LivingEntity.java:1944–1949` | `LivingEntity.java:1954–1959` | Same `onGround ? getMovementSpeed() * (0.21600002F / (f * f * f)) : field_6281` selector and same inherited accessor. Float literal, multiplication grouping and division order are unchanged. For a player, the accessor above selects its movement-speed attribute cast to `float`; in air the inherited base input-speed field is used. |
| Relative input acceleration and yaw rotation | `Entity.java:1011–1025` | `Entity.java:1055–1069` | Same `updateVelocity` adds `movementInputToVelocity(arg, f, yaw)` to current velocity. Same length-squared threshold `< 1.0E-7`, normalization only when `d > 1.0`, scale-before-rotation order, float sine/cosine angle conversion, and x/z rotation expressions. |
| Client subclass travel override check | `client/network/ClientPlayerEntity.java` | `client/network/ClientPlayerEntity.java` | Search found no `travel(Vec3d)` override on either side; the player override above is the applicable dispatch layer. Client input setters/camera plumbing were not inspected because input is assigned and those paths are excluded. |

## Bounded movement meaning

For a supplied input vector, yaw, slipperiness, and ground flag, both releases add the same yaw-rotated acceleration to existing velocity. On ground, the acceleration derives from the player movement-speed attribute and supplied slipperiness using the same float arithmetic; in air it uses the same base input-speed field. After movement, both multiply horizontal velocity by the same ground/air drag factor. This establishes no delta for the requested dry horizontal slice only.

## Exclusions and remaining coverage gates

Not examined as candidate changes: water/lava and fluid input scaling, fall-flying/Elytra, flight, swimming, sprint modifiers/timers, jumping, vertical gravity and thresholds, climbing behavior, collision/support/stepping, block slipperiness providers, camera and input setters, and movement branches for other entities. The supplied `onGround`, yaw, and block coefficient are not traced to their producers. No whole-tick dependency audit, independent audit, or full-pair freeze is claimed.




