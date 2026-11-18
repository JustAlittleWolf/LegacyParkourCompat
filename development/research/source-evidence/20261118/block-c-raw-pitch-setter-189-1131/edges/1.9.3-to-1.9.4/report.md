# C: Direct local pitch setter and Elytra read path

## Comparison result

The ordinary local camera-pitch update has **no source change** from 1.9.3 to 1.9.4 in Feather. Both versions’ `net/minecraft/entity/Entity.java` have SHA-256 `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`; the `updateLocalPlayerCamera(float yaw, float pitch)` body at lines 286–297 is identical.

The method saves the old current pitch in float `f`, then computes `(float)(this.pitch - pitch * 0.15)`. Because the literal is unsuffixed, multiplication and subtraction are double precision; the explicit cast rounds the result back to float. It clamps that current pitch to `[-90.0F, 90.0F]` and only afterward updates the previous pitch with `this.lastPitch + (this.pitch - f)`, using float arithmetic and the clamped current value.

Finite overrange witness: with current pitch `89.0F`, previous pitch `88.0F`, and input pitch delta `-20.0F`, the double expression produces `92.0`, the cast gives `92.0F`, and clamp stores `90.0F`. The later float delta is `90.0F - 89.0F = 1.0F`, so previous pitch becomes `89.0F`. Updating previous pitch before clamping would instead produce `91.0F`. The caller path is the camera update in `GameRenderer.java` (lines 850 and 854), which passes its pitch delta into this method.

This is the direct local-camera update, not `Entity.setRotation`: the latter applies `% 360.0F` and is a separate operation.

## Local Elytra steering read

For an ordinary locally controlled, nonpassenger player with the Elytra-active flag set, abilities flight off, and neither water nor lava movement active, the tick reaches the inherited movement path as follows:

- `LocalClientPlayerEntity.tick()` (143–155) calls its superclass tick when the current chunk is loaded. `PlayerEntity.tick()` (170–200) reaches `LivingEntity.tick()`, which dispatches `this.mobTick()` at line 1551.
- Dynamic dispatch runs `LocalClientPlayerEntity.mobTick()`; it eventually calls `super.mobTick()` at line 755. `PlayerEntity.mobTick()` calls `super.mobTick()` at line 419, reaching `LivingEntity.mobTick()`.
- `LivingEntity.mobTick()` runs its travel section at lines 1706–1711 and invokes `this.moveRelative(...)`. `PlayerEntity.moveRelative()` (1372–1390) delegates to `super.moveRelative(...)` for this case (`abilities.flying` is false).
- `LocalClientPlayerEntity.isLocallyControlled()` returns `true` (447–449). In `LivingEntity.moveRelative()` (1302 onward), local control passes the outer guard; the dry gates pass, then `isFallFlying()` selects the Elytra branch. Its first pitch read for Elytra steering is the call `this.getLookVector()` at line 1311. `LivingEntity.getLookVector()` (1817–1819) calls `getRotationVector(1.0F)`, whose exact-tick branch reads the stored current pitch in `this.getRotationVector(this.pitch, this.headYaw)` at line 1824. That look vector is used immediately in the steering math. The branch then separately reads stored pitch at line 1312 into float `i` for its angle calculations.

Two preceding reads are outside that steering read: the local player updates `easedPitch` from stored pitch in `LocalClientPlayerEntity.mobTick()` at line 594; and `LivingEntity.mobTick()` interpolates pitch at line 1656 only when `lerpSteps > 0` and its interpolation gate passes. Thus, on the ordinary no-interpolation tick, the first pitch read in the Elytra steering calculation is through `getLookVector()`; with pending interpolation, the conditional interpolation read occurs earlier.

The setter, Elytra movement body and read path cited here are identical across the endpoints: `Entity.java` and `LivingEntity.java` are byte-identical; `LocalClientPlayerEntity.java` and `GameRenderer.java` are also byte-identical. `PlayerEntity.java` differs elsewhere, but the cited `tick`, `mobTick` and `moveRelative` sections were compared and show no pitch-path change.

## Exact source and provenance identities

- **1.9.3 / Feather gen2 1.9.3+build.2:** nested provenance at `decompiled_minecraft/1.9.3/ornithe-feather/.decompile-provenance.json`. It pins the exact 1.9.3 client (`ab8fdde789c5b4614ffc98249e04fdcce7dfce320b55215bfe5418608af6a63b`) and Feather merged mapping JAR (`fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3`); its declared hashes match the source bytes.
- **1.9.4 / Feather gen2 1.9.4+build.2:** release-root provenance at `decompiled_minecraft/1.9.4/ornithe-feather.provenance.json`, with artifact pins in `decompiled_minecraft/1.9.4/artifacts.sha256` and source hashes in `decompiled_minecraft/1.9.4/ornithe-feather.sources.sha256`. The pinned client is `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908`; the merged mapping JAR is `49a38d0adfbda1749e519c29844116e9f22e895cb505633261b7f587268f4125` and the Tiny mapping is `9e21708d4bc32a43ac404735ea3238465889797110204a0375bcb069a3798027`. The declared source hashes match the source bytes.

Compared source hashes (identical across both releases unless stated):

| Relative source path | SHA-256 |
| --- | --- |
| `net/minecraft/entity/Entity.java` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` |
| `net/minecraft/entity/living/LivingEntity.java` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d` |
| `net/minecraft/client/render/GameRenderer.java` | `e08c2034c7fd4621a76ff5a4ba26dd80e065c895bc5c0a4120d4d36141203d6b` |
| `net/minecraft/entity/living/player/PlayerEntity.java` (1.9.3) | `ef96c8a00443f724eadad05a6a4dcc8a8a2e23cd4c3390b9e0f1f78c9ff4d1af` |
| `net/minecraft/entity/living/player/PlayerEntity.java` (1.9.4) | `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85` |

This is a bounded no-difference result for the camera-pitch setter and its local Elytra read path; it does not claim full-pair movement inventory coverage.