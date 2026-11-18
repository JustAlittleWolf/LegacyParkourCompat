# 1.16.4 → 1.16.5 Mojmap source review: direct `Entity.turn` pitch

## Result

No source difference was found for the reviewed operation. In both exact Mojmap trees, public `Entity.turn(double d, double e)` computes `f = e * 0.15` as a `double`, assigns `xRot = (float)(xRot + f)`, then immediately clamps that `float` to `[-90.0F, 90.0F]`. The old-pitch update follows afterward: `xRotO = (float)(xRotO + f)`, then a separate clamp. The operation order, operand widths, casts, and bounds are byte-identical across the pair.

The ordinary direct `Entity.turn` path cannot leave a finite pitch above 360 for an active Elytra travel read, provided the pre-turn authoritative `xRot` is finite and this turn is the last pitch write before that read. A finite witness shows why checking only the post-cast intermediate would be misleading: from `xRot = 0.0F`, `turn(0.0, 2401.0)` computes `f = 360.15` (`double`) and the cast result is finite `360.15F`, greater than 360. The next statement clamps authoritative `xRot` to `90.0F` before `turn` returns.

## Source trace

- `decompiled_minecraft/1.16.4/mojmap/net/minecraft/world/entity/Entity.java`, `setRot` and `turn`, lines 326–352; `decompiled_minecraft/1.16.5/mojmap/net/minecraft/world/entity/Entity.java`, same lines. `setRot(float,float)` stores `g % 360.0F`; `turn(double,double)` is the requested ordinary direct operation and contains the finite intermediate/clamp sequence above. Neither `Entity.java` contains a `setXRot` member.
- `decompiled_minecraft/1.16.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, `travel`, lines 1914–1986; same lines in 1.16.5. The Elytra branch is gated by `isFallFlying()` at line 1979. Its first pitch-dependent read is `getLookAngle()` at line 1985 (inherited `Entity.getLookAngle()` directly passes `this.xRot` to `calculateViewVector`, `Entity.java` lines 1700–1702); the next is `float j = this.xRot * (float)(Math.PI / 180.0)` at line 1986.
- In `LivingEntity.aiStep`, `updateFallFlying()` precedes `travel(...)` (lines 2453–2459). The update retains/sets the fall-flying state only through the airborne, not-passenger, no-levitation path and checks chest-slot `Items.ELYTRA` plus `ElytraItem.isFlyEnabled` (lines 2478–2497). This ties the travel branch to enabled Elytra state; it does not make the excluded device/input route part of this review.

## Evidence integrity

- Windows host, PowerShell 7.6.9. Sources are the local generated Mojmap trees; no source was generated or modified.
- 1.16.4’s hidden `.decompile-provenance.json` records the exact 1.16.4 metadata input and source hashes. The recorded SHA-256 values for `Entity.java` and `LivingEntity.java` are respectively `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` and `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`; local file hashes matched.
- 1.16.5’s `mojmap.ready.json` declares version `1.16.5`, mapping `mojmap`, and status `ready`. Its source-manifest, artifact-manifest, and diagnostics digests match the local sidecar files. The source manifest records the same two source hashes above, and the local files matched. The two relevant Java files are therefore byte-identical between releases. These checks rely on the published local sidecars; no raw cache location or cache contents are assumed.

## Bounded conclusion

This pair establishes no pitch-setter behavior delta for direct `Entity.turn`. It establishes the narrower property that a finite `turn` pitch intermediate—even one above 360 after the cast—is clamped before a subsequent active Elytra `LivingEntity.travel` pitch read. It does not establish that every other pitch writer or every way to initialize/restore/network an entity rotation has the same bound, nor does it establish a runtime trajectory.