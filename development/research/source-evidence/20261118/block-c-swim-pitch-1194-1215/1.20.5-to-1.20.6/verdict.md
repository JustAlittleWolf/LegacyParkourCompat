# Blind source freeze: 1.20.5 → 1.20.6 swim pitch Y correction

## Inputs and source identity

- Pair: Minecraft 1.20.5 → 1.20.6, official Mojang client mappings (`mojmap`).
- Canonical read-only source roots: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.20.5/mojmap/` and `.../1.20.6/mojmap/`.
- Both source sets are published `ready`, with exact version IDs matching the requested pair. Source counts and byte totals match their ready markers; every source entry in each SHA-256 manifest was checked against the current file bytes with zero mismatches.
- 1.20.5: source manifest SHA-256 `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484`; artifact manifest SHA-256 `f2dbd7a2e6c3fb452b0c28c93c9815e43ca24c179815f3d4fca34e484d465bb6`; provenance SHA-256 `1d9b247fb839ad0e234054cba1668073245e60d86783eca40d44c2b7b23c6242`; 5,328 files / 25,448,860 bytes.
- 1.20.6: source manifest SHA-256 `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`; artifact manifest SHA-256 `e5882622bcf22b3e3c2c96c73843a1308e11e3096f4feefd945ea5808933ce31`; provenance SHA-256 `ee53e67e7ab51069a0eea5e0ce13d49a9f972bdc13e3c31e041afa77f4d26e8`; 5,329 files / 25,451,020 bytes.
- Each ready marker agrees with its paired provenance and source-manifest identity. Exact-version metadata and client hashes are recorded in those ready/provenance files. No regeneration was performed.

## Frozen complete slice

The entire cited Java files are byte-identical across the pair, so the method comparisons below are exact no-difference checks. Each listed SHA-256 equals the source-manifest entry on both sides and was recomputed from both cited files:

| Source file (both releases) | SHA-256 |
| --- | --- |
| `net/minecraft/world/entity/player/Player.java` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` |
| `net/minecraft/world/entity/Entity.java` | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` |
| `net/minecraft/world/entity/LivingEntity.java` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` |
| `net/minecraft/util/Mth.java` | `f1b2d62f18b4b20ffad5843f40d34d8b5f9c40eee3e106dfe275859a24cfb18d` |

### Actual player writer and pitch lookup path

In `Player.java`, `Player.travel(Vec3)` (both versions, lines 1473–1493) first gates the correction on `isSwimming() && !isPassenger()`. It reads `double lookY = getLookAngle().y`, then selects `0.085` when `lookY < -0.2`, otherwise `0.06`. It writes a Y correction only when `lookY <= 0.0`, `jumping`, or the fluid state at `BlockPos.containing(x, y + 1.0 - 0.1, z)` is nonempty. The write uses the current delta vector and adds `(lookY - currentY) * coefficient` to Y via `setDeltaMovement`; X/Z receive zero additions. This precedes Player's separate flying/nonpassenger branch and its `super.travel` call. `Player.isSwimming()` (line 1832) is `!abilities.flying && !isSpectator() && super.isSwimming()` in both releases.

`Entity.getLookAngle()` (Entity.java lines 2027–2028) calls `calculateViewVector(getXRot(), getYRot())`. `calculateViewVector` (lines 1479–1486) keeps pitch and yaw in `float`: pitch radians are `xRot * (float)(Math.PI / 180.0)`, yaw radians are `-yRot * (float)(Math.PI / 180.0)`. It samples, in source order, `Mth.cos(yawRadians)`, `Mth.sin(yawRadians)`, `Mth.cos(pitchRadians)`, `Mth.sin(pitchRadians)`, then constructs `(yawSin * pitchCos, -pitchSin, yawCos * pitchCos)` as a `Vec3`. Thus the correction's Y input is the double-promoted negated float sine of float pitch radians; yaw does not enter that Y component.

`Mth` (lines 28–54) initializes private `float[65536] SIN`; for each integer index it stores `(float)Math.sin(index * Math.PI * 2.0 / 65536.0)`, with the calculation in double before the final float cast. `sin(float)` indexes `SIN[(int)(angle * 10430.378F) & 65535]`. `cos(float)` indexes `SIN[(int)(angle * 10430.378F + 16384.0F) & 65535]`. The float multiplication/addition precedes Java's truncating float-to-int cast; the bit mask wraps the signed result into the 16-bit table. `SIN_SCALE` is declared as `10430.378F`, while these two methods spell that constant directly. No LUT width, initialization, cast, index, or sample-order difference exists in this pair.

### Reach into the ordinary water branch

After Player's pre-correction and flying routing, the ordinary nonpassenger player reaches inherited `LivingEntity.travel(Vec3)`. Its outer selector is `isControlledByLocalInstance()`. It reads gravity, computes the nonpositive-Y falling flag, clamps gravity for Slow Falling when applicable, and reads the fluid at `blockPosition()`. The water arm is selected only by `isInWater() && isAffectedByFluids() && !canStandOnFluid(fluidAtBlockPosition)`; it is tested before lava and fall-flying arms. For this Player, `canStandOnFluid` returns false, and `getWaterSlowDown` is inherited as `0.8F`.

The selected water arm saves current Y, chooses horizontal slowdown `0.9F` while sprinting or the water slowdown otherwise, and starts input speed at `0.02F`. Depth Strider is capped at `3.0F`, halved off ground, then adjusts slowdown toward `0.54600006F` and input speed toward `getSpeed()`, each divided by `3.0F`; Dolphin's Grace overrides slowdown to `0.96F`. It calls `moveRelative(speed, input)` and then `move(MoverType.SELF, getDeltaMovement())`. After that move, horizontal collision plus climbability can set Y to `0.2`; velocity is multiplied by `(slowdown, 0.8F, slowdown)`, passed through `getFluidFallingAdjustedMovement(gravity, wasFalling, velocity)`, and stored. A horizontal collision can then set Y to `0.3F` when the `isFree` escape query succeeds. This is the complete ordinary water selector and its immediate movement/writer path; no branch or ordering difference exists between releases.

### Real inherited `Entity.move` path

The water arm calls inherited `Entity.move(MoverType, Vec3)` (Entity.java lines 597 onward); both complete Entity files have the same SHA above. For the ordinary SELF call, `noPhysics` directly adds the requested vector to position. Otherwise it records fire state, applies piston limiting only for PISTON, profiles movement, applies/clears stuck-speed multiplier and zeroes velocity when that multiplier is active, backs off from edges, and calls collision resolution. Nontrivial resolved movement can reset fall distance through the fall-damage-resetting clip and updates position. The rest path computes horizontal/vertical collision flags, ground state, fall damage, zeroes blocked horizontal velocity, runs block landing/step effects and movement emissions, updates step/swim events and distances, processes inside-block callbacks, applies block speed factor to horizontal velocity, handles fire state, and closes profiling. This actual inherited method body is byte-identical for 1.20.5 and 1.20.6; the subsequent water-arm post-move handling is likewise in the identical LivingEntity source.

## Frozen result

No source difference was found in the complete assigned swim pitch Y correction slice or its requested dependencies: Player writer, Entity pitch/view-vector writer, Mth float LUT, ordinary LivingEntity water selector and post-move handling, and the inherited Entity.move body. There is no candidate difference to carry forward for this operation. This records source coverage only and makes no claim about other movement slices or runtime parity.

## Phase 2 independent review

Reviewed the single authorized author report `c-swim-pitch-vertical-1205-1206-source/report.md`, SHA-256 `b4a8bc5e4dc3978ef2580117dcaabf622279664c1f9507f8b7553ee44c3a703a`. The original frozen independent verdict was rehashed before review and matched SHA-256 `217842fce6da4664f0e8c9c8e423f7dc9c69ed4b7f434ba2b2ad8e42f9d34e88`.

The author report's no-difference conclusion and its scoped evidence agree with this freeze: same four complete Mojmap source files and hashes, Player.travel correction gates and write, float pitch/view-vector LUT path, inherited ordinary water travel reach, and Entity.move. Its additional sequencing details are consistent with the frozen writer and dependencies. No contradiction or unsupported change to the assigned conclusion was identified. Acceptance is limited to this operation and evidence pair; this does not establish full-pair movement coverage or runtime parity.

Signed: `c-swim-pitch-vertical-1205-1206-blind-audit`, independent phase 2 source review.