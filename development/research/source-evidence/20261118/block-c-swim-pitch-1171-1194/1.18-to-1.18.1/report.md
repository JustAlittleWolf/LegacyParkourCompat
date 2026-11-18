# Player swim pitch Y adjustment: 1.18 to 1.18.1

## Exact source evidence

Both canonical local source sets are published as ready, exact Mojmap sources:

- `decompiled_minecraft/1.18/mojmap`: ready record `decompiled_minecraft/1.18/mojmap.ready.json`; exact version `1.18`; mapping `mojmap`; source-manifest SHA-256 `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78`; provenance SHA-256 `e642f075b25d1b6e74bad05edacd740421c70f5d8e0c2a38767523cf864124c5`.
- `decompiled_minecraft/1.18.1/mojmap`: ready record `decompiled_minecraft/1.18.1/mojmap.ready.json`; exact version `1.18.1`; mapping `mojmap`; source-manifest SHA-256 `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d`; provenance SHA-256 `4b181e586201aa2695835e31155c96d8ae3d05b00c9bbb83e99eaa5b2b9db139`.

For both releases, ready status, exact version and mapping agree with the records; the recorded SHA-256 values for the source manifest, artifact manifest, movement diagnostics and provenance file match the published files on disk. The four relied-on Java files are byte-identical across the pair (their individual source-manifest hashes are the same on both sides):

- `net/minecraft/world/entity/player/Player.java` — `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2`
- `net/minecraft/world/entity/Entity.java` — `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1`
- `net/minecraft/util/Mth.java` — `10bd2dcc2b7084d0f9be24a3e54322510f1730e67fb84d35ec460c6953b3397f`
- `net/minecraft/world/entity/LivingEntity.java` — `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7`

## Compared operation

In both versions, `Player.travel(Vec3)` (`Player.java:1409–1438`) first snapshots position. Its pre-super adjustment is gated by `isSwimming() && !isPassenger()`. It reads `double lookY = getLookAngle().y`, then chooses a `double` coefficient: `0.085` when `lookY < -0.2`, otherwise `0.06`. It changes Y velocity only if `lookY <= 0.0`, `jumping`, or the sampled block's fluid state is nonempty. The sample is `new BlockPos(getX(), getY() + 1.0 - 0.1, getZ())` (the position at Y + 0.9, with current X/Z). The update is `delta.add(0.0, (lookY - delta.y) * coefficient, 0.0)`, followed by `setDeltaMovement`; X and Z are preserved. Thresholds are strict for choosing the coefficient (`<-0.2`) and inclusive for the normal correction gate (`<=0.0`). The above-fluid and jumping conditions independently enable correction for a positive look-Y value.

`Entity.getLookAngle()` (`Entity.java:1813–1815`) calls `calculateViewVector(getXRot(), getYRot())` using current rotations, without partial-tick interpolation. In `calculateViewVector` (`Entity.java:1311–1318`), degrees are converted to radians in `float` locals using multiplication by `(float)(Math.PI / 180.0)`; yaw is negated before conversion. `Mth.cos`/`Mth.sin` return `float`s. The Y component is unary-negated float `sin(pitchRadians)`. `Vec3`'s `(double, double, double)` constructor widens these float components to doubles, so the returned `.y` is a double representation of the float LUT result. `getViewVector(float)` also uses the same calculation but is not the call used by this swim adjustment.

`Mth.sin(float)` (`Mth.java:43–45`) indexes `SIN[(int)(angle * 10430.378F) & 65535]`; Java's float-to-int cast truncates toward zero before the 16-bit mask. `Mth.cos(float)` (`Mth.java:47–49`) indexes the same 65,536-entry table after adding `16384.0F` before the cast and mask. The table (`Mth.java:26–27, 29–31`) is initialized by casting `Math.sin(index * Math.PI * 2.0 / 65536.0)` to float. These definitions and the view-vector calculation are byte-identical in the compared source sets.

After the adjustment, `Player.travel` invokes `super.travel(input)` (`Player.java:1428 or 1435`). This dispatches to `LivingEntity.travel` (`LivingEntity.java:2005`), which on its effective-AI/local-control path enters its water branch when `isInWater() && isAffectedByFluids() && !canStandOnFluid(...)`; that branch calls `moveRelative` and then `move(MoverType.SELF, getDeltaMovement())` (`LivingEntity.java:2013–2038`). Thus the corrected Y velocity is present before the inherited move consumes it. Later water slowdown/falling adjustments can alter velocity after that move. In Player's flying, non-passenger path, the parent is still called; afterward the Y component is set from the pre-parent Y snapshot times `0.6` (`Player.java:1422–1428`).

## Result

No scoped source difference exists between 1.18 and 1.18.1. The Player adjustment, its caller's exact view-vector/trigonometric path, and the inherited move path are identical, with matching source bytes in all four files. This is a no-difference result for this operation only; it does not claim pair-wide tick coverage or a movement-parity result.
