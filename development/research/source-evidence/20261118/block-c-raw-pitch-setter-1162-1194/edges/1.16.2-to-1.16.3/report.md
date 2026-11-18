# Entity.turn pitch comparison: 1.16.2 → 1.16.3

## Result

No source difference in direct `Entity.turn` pitch handling. The `Entity.java` files have identical SHA-256 hashes in both exact Mojmap trees, and the method bodies match byte-for-byte. There is no `setXRot` method here: `turn` assigns the pitch fields directly.

## Exact behavior in both releases

`Entity.java`, `turn(double d, double e)`, lines 340–351: `f` is computed as `e * 0.15` in `double`; current `xRot` is added to that `double` and cast to `float`, then clamped using `Mth.clamp(float, float, float)`. The old pitch is updated separately, after the current pitch clamp, with the same double increment, float cast and float clamp. Yaw writes occur between those pitch operations, then a passenger callback is conditional on `vehicle != null`.

For finite ordinary method arguments whose intermediate result exceeds 360 degrees, the cast occurs before the clamp. For example, from `xRot == 0`, `turn(0.0, 3000.0)` produces `f == 450.0` and the first float pitch value is `450.0F`; the final current pitch is `90.0F`. Both releases behave identically. The same independent update/clamp sequence applies to `xRotO`.

`Mth.java`, lines 94–100, confirms the selected float overload compares and returns float values. The double overload at lines 102–108 is not selected by the float field and float bounds.

## Movement consumer checked

In both releases, `LivingEntity.travel(Vec3)`, lines 1979–2008, enters the Elytra branch when `isFallFlying()` is true. It reads the current pitch for `j = this.xRot * (float)(Math.PI / 180.0)` (line 1986) and uses `j` in the cosine and sine terms that update vertical and horizontal velocity. It also obtains `getLookAngle()` immediately before that pitch conversion. Thus after the example `turn`, an active Elytra travel step reads the clamped `90.0F` current pitch in either release. This is a source-path check, not a claim about device input, scheduling or rendered behavior.

## Source identity and integrity

- 1.16.2: canonical `decompiled_minecraft/1.16.2/mojmap/`; legacy readiness marker is `ready`, 3,516 source files. Its source-manifest digest and artifact-manifest digest match the ready marker. The `Entity.java` and `LivingEntity.java` hashes match their source-manifest entries.
- 1.16.3: canonical `decompiled_minecraft/1.16.3/mojmap/`; hidden-root `.decompile-provenance.json` records the source and pinned-input SHA-256 values. All 36 recorded inputs were found in the local `.minecraft-decompile-cache/1.16.3/` tree and rehashed to their recorded values; the local `version.json` identifies 1.16.3. `Entity.java` and `LivingEntity.java` hashes match the hidden-root source manifest.
- Compared source SHA-256 (both releases): `Entity.java` `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`; `LivingEntity.java` `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`.
- 1.16.2 ready marker: `e5f69bab646b6143b0660a97fade59595ff2e924d36567b9cd285c8babd31214`; 1.16.2 provenance: `a9b166406064e877751542fe7df27acc32be3843f6fdc2280dac32ce0b54207c`; 1.16.3 hidden provenance: `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498`.

This bounded operation has no finding to publish. No caller scheduling, rendering, teleport or network path was examined.
