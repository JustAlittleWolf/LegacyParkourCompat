# 1.14.1–1.14.2 horizontal travel source review

## Scope and boundary

Compared the ordinary dry, unmounted, nonsprinting player travel path from already-consumed movement axes through relative horizontal acceleration and the horizontal friction/drag writes. `onGround`, block slipperiness, yaw, current velocity, and movement speed are treated as supplied inputs. The review excludes client keyboard/pose production, swimming/sneaking state production, jump, sprint activation/timers, flight/fall-flying, water/lava, climbing, vertical gravity and thresholds, collision/support/stepping internals, and friction/block-state providers.

## Exact source identity

- 1.14.1 Yarn: `decompiled_minecraft/1.14.1/yarn`; manifest SHA-256 `308537C5ADEC3104C5CA243EB5010554F00978635ED74F0D7801C804DD4D4907`. Its pinned inputs identify Minecraft 1.14.1, intermediary 1.14.1, and Yarn `1.14.1+build.10`.
- 1.14.2 Yarn: `decompiled_minecraft/1.14.2/yarn`; manifest SHA-256 `786546F99684423CC2563BE45C9C3334DFCC2C7DB58DDB74BA39CE403BD0869A`. Its pinned inputs identify Minecraft 1.14.2, intermediary 1.14.2, and Yarn `1.14.2+build.7`.
- The manifest hashes above were recomputed from each nested `.decompile-provenance.json` and matched the assigned values. Actual source bytes used below matched their corresponding manifest `sources` entries.

| Source | 1.14.1 SHA-256 | 1.14.2 SHA-256 |
|---|---|---|
| `net/minecraft/entity/LivingEntity.java` | `331E2D7DFA936A6F7197910BA374C4A60B479C8565FF7C64A4D6A9E1E8B52DDA` | `E8224E7DF98CCF0986BB390F35E64293263D17B9AB1DBDFEB661C65D28B6EE78` |
| `net/minecraft/entity/Entity.java` | `CE429699F2D8F0A54F4BE516783A474C6C7808572950164508CEED0BAF68ED5B` | `7C0E9CAB0842A45E42FA9F27C9661DE3FD0FD076C35FF989AA0477198EB38EDC` |
| `net/minecraft/entity/player/PlayerEntity.java` | `EBDA4AD25A265F8C85E88345EFCCE753B78E248A29A9B910C5CFD311A4A69319` | `333BB39DB18AF4CEF320EB73B7ABE359D438FB2A6CA7D8798EE449DA6C684398` |

## Caller and slice inventory

- `LivingEntity.tickMovement`: 1.14.1 lines 2127, 2204; 1.14.2 lines 2133, 2210. It passes `new Vec3d(sidewaysSpeed, upwardSpeed, forwardSpeed)` to `travel`; the input producers are outside this assignment.
- `PlayerEntity.tickMovement`: both versions line 492 call `super.tickMovement()` before writing `field_6281 = 0.02F` and optionally adding `0.005999999865889549` at lines 498–500. This field is the airborne acceleration consumed by the preceding travel call; for steady nonsprinting ticks its value is `0.02F`. The write/order and complete method body are the same in both versions.
- `PlayerEntity.travel`: both versions line 1362. Its swimming and flying wrappers do not run under the assigned ordinary path, which delegates to `LivingEntity.travel`.
- `LivingEntity.travel`: 1.14.1 line 1763; 1.14.2 line 1769. The ordinary branch is gated by the dry/non-lava and non-fall-flying branches. It reads slipperiness `v`, computes `w = onGround ? v * 0.91F : 0.91F`, calls `updateVelocity(method_18802(v), axes)`, applies `method_18801`, calls `move`, then writes horizontal velocity as `lv8.x * w` and `lv8.z * w`. Collision internals and the intervening vertical calculation are not evaluated here.
- `LivingEntity.method_18802`: 1.14.1 line 1938; 1.14.2 line 1944. It selects ground acceleration `getMovementSpeed() * (0.21600002F / (v * v * v))` when grounded, otherwise `field_6281`.
- Player speed override `PlayerEntity.getMovementSpeed`: line 1406 in both; returns `(float) getAttributeInstance(MOVEMENT_SPEED).getValue()`.
- `Entity.updateVelocity` / `movementInputToVelocity`: 1.14.1 lines 1009/1014; 1.14.2 lines 1011/1016. The latter returns zero for input length-squared `< 1.0E-7`; otherwise it normalizes only when length-squared `> 1.0`, multiplies by the float acceleration, rotates horizontal components using yaw converted to radians and `MathHelper.sin/cos`, and `updateVelocity` adds the result to existing velocity.
- `LivingEntity.method_18801`: 1.14.1 line 1921; 1.14.2 line 1927. Its only modification is under `isClimbing()`; outside the assigned climbing branch it returns the input vector unchanged.

## Paired result

The compared bodies for `LivingEntity.travel`, `method_18802`, `getMovementSpeed`, `method_18801`, `Entity.updateVelocity`, `Entity.movementInputToVelocity`, `PlayerEntity.travel`, `PlayerEntity.getMovementSpeed`, and `PlayerEntity.tickMovement` are equal after whitespace normalization. In the requested ordinary path, acceleration selection, input threshold/normalization, yaw transform, velocity addition, and ground/air horizontal drag are unchanged. No independent difference or finding is emitted for this bounded slice.

## Gate

Source-only bounded comparison completed. This does not establish complete 1.14.1–1.14.2 pair coverage, independent audit, implementation, or runtime parity.
