# C: raw pitch setter audit — 1.20.5 → 1.20.6

## Frozen source identity

Read-only canonical roots: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.20.5\` and `...\1.20.6\`, mapping `mojmap` (official Mojang client mappings), exact IDs 1.20.5 and 1.20.6. Both root readiness markers say `ready`, identify Vineflower and Java 25, and bind the source and artifact manifests below. The provenance for 1.20.5 records the official mapping family, client and mapping artifact hashes, toolchain, and exact invocation; 1.20.6 has batch provenance for 1.20.1/1.20.2/1.20.4/1.20.6.

| Version | `mojmap.ready.json` SHA-256 | `mojmap.provenance.json` SHA-256 | source manifest SHA-256 | artifact manifest SHA-256 |
|---|---|---|---|---|
| 1.20.5 | `4a587caa86e4ddd68ba63a5431417933dc4df4822e33aaf29ce77ebda53947c2` | `1d9b247fb839ad0e234054cba1668073245e60d86783eca40d44c2b7b23c6242` | `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484` | `f2dbd7a2e6c3fb452b0c28c93c9815e43ca24c179815f3d4fca34e484d465bb6` |
| 1.20.6 | `1ec1dc773ea2a8a3ef48a9fc868893bd2f83d6b13b057d75b151b711788c3d41` | `ee53e67e7ab51069a0eaae5a0ce13d49a9f972bdc13e3c31e041afa77f4d26e8` | `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311` | `e5882622bcf22b3e3c2c96c73843a1308e11e3096f4feefd945ea5808933ce31` |

The canonical source manifests list the same bytes for every cited file on both sides:

- `mojmap/net/minecraft/client/MouseHandler.java`: `ed1248f1d62a927907e6e10fae87a0d4ab0a99d91e44a149aeba40d05fbda1e1`
- `mojmap/net/minecraft/world/entity/Entity.java`: `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`
- `mojmap/net/minecraft/world/entity/LivingEntity.java`: `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`

I checked both manifest rows against the files in the canonical roots. The generated `.minecraft-decompile-cache/1.20.5` and `/1.20.6` raw caches are absent (`Test-Path` false for each); this is separate from the present canonical ready source roots and manifests. No cache contents are claimed or relied on.

## Ordinary local mouse-turn trace

The trace is byte-identical in both versions:

1. `MouseHandler` installs its cursor-position callback at line 172 and enqueues `this.onMove(window, x, y)` on the Minecraft client executor. At lines 187–202, `onMove` ignores a first sample, otherwise, only for the active window, adds each cursor-coordinate difference to double `accumulatedDX`/`accumulatedDY`, then stores the new coordinates. This is client input plumbing, not itself movement emulation.
2. `handleAccumulatedMovement` at lines 205–232 checks active window; if mouse is grabbed and local `minecraft.player != null`, it calls `turnPlayer(elapsed)`, then clears both accumulators. `turnPlayer` at lines 235–270 computes `s = sensitivity * 0.6F + 0.2F`, `s³`, and `8*s³`. Smooth camera uses the smooth-delta helper with accumulated deltas and elapsed time; otherwise first-person scoping resets smoothing and uses `accumulatedDelta*s³`; the ordinary unsmoothed branch resets smoothing and uses `accumulatedDelta*(8*s³)`. Invert-Y changes the multiplier to -1. After tutorial notification, if local player remains non-null, it calls `player.turn(dx, dy*invert)`. No finite check or clamp is applied to the double accumulated input or selected double delta here.
3. Inherited `Entity.turn(double dx, double dy)` at lines 400–412 first casts `dy` to float and multiplies by `0.15F` (`pitchDelta`); similarly it casts `dx` and multiplies by `0.15F` (`yawDelta`). It calls `setXRot(currentPitch + pitchDelta)`, then `setYRot(currentYaw + yawDelta)`, then calls `setXRot(clamp(currentPitch, -90.0F, 90.0F))`. It next increments `xRotO` and `yRotO` by the same float deltas, clamps `xRotO` to ±90, and notifies a vehicle if present. The first pitch setter therefore precedes yaw assignment and the second setter/clamp. Java float cast and `0.15F` multiplication happen before addition to current float pitch.
4. `Entity.setXRot(float)` at lines 3324–3330 rejects nonfinite input: it logs `Invalid entity rotation: ..., discarding.` and leaves `xRot` unchanged; finite values are stored as-is without normalization or clamping. Thus the first setter can temporarily store a finite pitch beyond 360 degrees; the second call in the same `turn` clamps it. For nonfinite first input, it leaves the old pitch in place, and the subsequent clamp operates on that old value.
5. The movement consumer is `LivingEntity.travel(Vec3)` at lines 2104 onward. Under its fall-flying branch (`isFallFlying()`, lines 2168–2205), it reads `getXRot()` to form float radians (`pitch * (float)(PI/180)`), reads `getLookAngle()`, and uses the resulting pitch/look values in glide gravity, lift, dive and horizontal velocity calculations before `move(SELF, deltaMovement)`. The same method is inherited by the local player. This direct pitch dependence is player-movement scope. Since the normal mouse `turn` completes its second setter before returning, glide reads the final clamped pitch, not the intermediate value. The branch is movement math; collision damage/sound inside the same source region is outside the mod's declared scope.

## Finite witness and boundary

A source-level ordinary unsmoothed witness with sensitivity value 1.0, no first-person scope, no smooth camera, invert-Y off, starting pitch 0, and accumulated `DY = 1000` gives `s = 0.8`, `s³ = 0.512`, and `8*s³ = 4.096`; `turnPlayer` supplies `dy = 4096` (double). In `Entity.turn`, `(float)4096 * 0.15F` is a finite float about `614.4F`. The first `setXRot(0 + pitchDelta)` therefore receives/stores a lawful finite value greater than 360, not infinity. The immediate second setter clamps current pitch to `90.0F`; that is the value subsequent fall-flying movement reads. The source permits this arithmetic path when accumulated cursor movement has that value; this is a method-level witness, not a claim that a particular mouse/device session was executed.

By contrast, an extreme finite double such as a `turn` dy beyond `Float.MAX_VALUE` casts to float infinity before `* 0.15F`; the first setter rejects it. The later clamp sees the prior pitch. This cast-overflow/nonfinite case is not the finite >360 witness and is not needed to establish the ordinary finite path. No unbounded pitch persists across completed ordinary mouse turns because of the second clamp.

## Verdict

**No 1.20.5→1.20.6 source delta for this trace.** All cited class bytes and the described gates, expressions, casts, setter semantics, clamp order, and glide reads match exactly. The finite intermediate >360 case exists in both releases, but is not a difference and does not establish a >360 pitch read by a later movement tick.

## Phase-two independent bounded binding

**ACCEPTED, bounded to the ordinary mouse pitch writer and fall-flying pitch consumer; exact source delta remains none.** This verdict is bound to the literal phase-two source report `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\c-raw-pitch-setter-1205-1206-source\report.md`, SHA-256 `104fd7bf66c9ed9247ceb38a5337b07ac3b578a4cef268ffec5217f2bc39ecde`, verified before reading. Its method sequence and seven manifest-verified class hashes agree with this frozen trace; its `Player.travel` local branch qualification refines the movement-call context. This is not a full-pair coverage verdict.

**Witness qualification:** the phase-two report's `accumulatedDY = 1.0e39` is finite at the double input, but casts to float positive infinity in `Entity.turn`; it is the extreme overflow/rejection case. It is not a finite post-cast pitch witness. A separate ordinary unsmoothed/unscoped finite witness is sensitivity `0.5` (the source default), invert-Y off, starting pitch `0`, and `accumulatedDY = 3000.0`: `s = 0.5 * 0.6F + 0.2F` is approximately `0.5000000149`, so `8*s^3` is approximately `1.0000000894`; the passed double delta is approximately `3000.000268`, and `(float)dy * 0.15F` is finite and approximately `450.00003F`. The first pitch setter therefore receives finite `450.00003F` (>360) and accepts it; the immediate second setter clamps `xRot` to `90F`. The next local fall-flying `travel` reads `xRot` after this synchronous clamp. Thus finite intermediate >360 and finite-double-to-float-infinity rejection are distinct cases, and neither establishes a >360 pitch persisted for the subsequent movement read.

No code, tests, runtime, or product evidence was consulted in this phase.
