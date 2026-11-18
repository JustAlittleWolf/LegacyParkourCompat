# 1.21.3 to 1.21.4 player swim-pitch Y adjustment

## Evidence identity and integrity

Both inputs are exact Mojmap decompilations, marked `ready` for their exact version. Their manifests identify the same preparation run (`1.21.1,1.21.3,1.21.4,1.21.5`, Mojmap, Vineflower 1.12.0, Java 25.0.3+9, Gradle 9.7.1). `mojmap.sources.sha256` file hashes match each ready marker's declared digest. SHA-256 values below were recomputed from the canonical source files and match the corresponding source-manifest entries.

- `1.21.3/mojmap`: `mojmap.sources.sha256` SHA-256 `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`; `Player.java` `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de`; `Entity.java` `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9`; `Mth.java` `98dfb57e239348cf71bf00695b84299232bd5e0cfe206ee8c23a69b72f1099da`.
- `1.21.4/mojmap`: `mojmap.sources.sha256` SHA-256 `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0`; `Player.java` `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1`; `Entity.java` `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad`; `Mth.java` `98dfb57e239348cf71bf00695b84299232bd5e0cfe206ee8c23a69b72f1099da`.

## Bounded comparison

- `1.21.3`, `net/minecraft/world/entity/player/Player.java`, `Player.travel(Vec3)`, lines 1445-1466.
- `1.21.4`, same member, lines 1448-1469.

For an ordinary nonpassenger player, the direct adjustment is identical: if `isSwimming()`, sample `double lookY = getLookAngle().y`; choose `0.085` when `lookY < -0.2`, otherwise `0.06`; and when `lookY <= 0.0 || jumping || !fluidState(BlockPos.containing(x, y + 1.0 - 0.1, z)).isEmpty()`, set movement to `oldMovement.add(0.0, (lookY - oldMovement.y) * factor, 0.0)`. The test order, strict/inclusive comparisons, short-circuit order, fluid sample coordinates and `BlockPos.containing` floor behavior, constants, double precision and additive update are unchanged. This runs before the subsequent travel call. Passenger branch skips the local swim adjustment and immediately calls `super.travel`; flying afterward preserves prior Y times `0.6` as before.

## Angle and inherited travel trace

In both versions, `Entity.getLookAngle()` passes the current raw `getXRot()` and `getYRot()` values to `calculateViewVector`; the no-argument getters return `this.xRot` and `this.yRot` directly. The vector routine converts each float angle to radians in float arithmetic: `pitch * (float)(Math.PI / 180.0)` and `-yaw * (float)(Math.PI / 180.0)`. It then computes `cos(yawRadians)`, `sin(yawRadians)`, `cos(pitchRadians)`, `sin(pitchRadians)` in that order and returns `(sinYaw*cosPitch, -sinPitch, cosYaw*cosPitch)`. No interpolation or extra normalization is used. Its method body is identical across the pair (`Entity.java` 1.21.3 lines 1619-1627; 1.21.4 lines 1640-1648); `getLookAngle` and raw getters are also unchanged (1.21.3 lines 2192-2194, 3469-3471, 3485-3487; 1.21.4 lines 2220-2222, 3493-3495, 3509-3511).

`Mth.sin/cos` are identical (both `Mth.java` lines 32-54): initialization fills `float[65536]` by `SIN[i] = (float)Math.sin(i * Math.PI * 2.0 / 65536.0)` for integer `i` from zero through 65535. `sin(float)` indexes `SIN[(int)(angle * 10430.378F) & 65535]`; `cos(float)` indexes `SIN[(int)(angle * 10430.378F + 16384.0F) & 65535]`. The multiplications and offset are float operations, cast truncates to `int` before the 16-bit mask, and the table payload is source-identical (same `Mth.java` hash in both releases).

The later `super.travel($$0)` from `Player.travel` dispatches to inherited `LivingEntity.travel(Vec3)` in both versions (Player extends LivingEntity). That method is unchanged: if locally controlled, it reads fluid at `blockPosition()` and invokes `travelInFluid` when in water/lava, affected by fluids and unable to stand on that fluid; otherwise it chooses fall-flying or air movement (`LivingEntity.java`: 1.21.3 lines 2178-2188; 1.21.4 lines 2189-2199). The Player adjustment therefore precedes the inherited movement dispatch in both releases.

## Result

No source difference found in the assigned direct swim-pitch Y adjustment, its raw angle/trig lookup path, or the immediate inherited travel dispatch. This is a bounded no-difference result, not whole-pair tick coverage. No implementation or runtime claim is made.
