# Knockback normalization audit

Terminal status: **COMPLETED**

## Verdict

**PASS for the source disposition, with a reachability wording correction.** Exact Feather sources confirm the `applyKnockback` normalization change between 1.13.2 and 1.14. The changed producer is not reached by a logical-client local player through the vanilla call graph reviewed here. The comparison draft should explicitly list the `PlayerEntity.attack` direct call sites: those are syntactically outside `takeDamage`, but they are conditional on successful `takeDamage`; on the logical client that call returns false. `LocalClientPlayerEntity.takeDamage` also directly returns false in all three inspected snapshots. Thus the draft's no-client-producer conclusion holds, but its caller inventory is incomplete if read as covering all direct call sites.

The narrower description “server-only damage response production” should also mention the direct attack knockback branches in `PlayerEntity.attack`. Those branches can call `applyKnockback` on the logical server after a successful hit; their client-side branches do not reach the call. This audit does not assess damage/combat eligibility.

## Exact arithmetic and boundary

- 1.13.2 `LivingEntity.applyKnockback` (`LivingEntity.java:1011–1026`) passes the horizontal squared magnitude to `MathHelper.sqrt(double)`, whose result is a `float`; stores it in `float f`; then divides each input component by `f` and multiplies by float `amount`. There is no small-magnitude guard. A zero horizontal input therefore divides `0.0` by `0.0` and produces NaN components; any nonzero input uses the float-rounded square root.
- 1.14 and 1.14.4 use `new Vec3d(velocityX, 0.0, velocityZ).normalize().scale(amount)` and subtract its components after halving the old X/Z velocity. `Vec3d.normalize` computes `double d = MathHelper.sqrt(x*x + y*y + z*z)`; `MathHelper.sqrt(double)` still returns a float, widened into `double`. The exact guard is `d < 1.0E-4` (strict): below it the normalized result is `ZERO`; at `d == 1.0E-4` the divide branch is taken, subject to the float-rounded square-root value. This removes the old zero-vector NaNs and suppresses the horizontal impulse for magnitudes under the cutoff. Grounded Y handling remains `min(0.4, oldY / 2.0 + amount)`; airborne Y is preserved. The resistance predicate and dirty flag remain in place.
- The first exact checked newer snapshot with the Vec3d formula is 1.14. Intermediate 1.14.1–1.14.3 were not accepted as exact source: their directories have no top-level Feather provenance record. No narrower patch boundary is established.

## Callers and client reachability

- Full exact-source `applyKnockback(` search in the compared Feather trees finds the method plus callers in `LivingEntity.takeDamage`, `LivingEntity.blockWithShield`, `PlayerEntity.attack` (target hit and sweep), and `MobEntity.tryDamage`.
- The `takeDamage` implementations return false when `world.isClient` before reaching the damage-response `applyKnockback` call (1.13.2 `LivingEntity.java:752–759`; 1.14 `:845–852`; 1.14.4 `:852–859`). `blockWithShield` is reached from this damage handling.
- The two `PlayerEntity.attack` call sites are under `if (bl6)`, where `bl6` is the result of `target.takeDamage(...)`; the sweep call is nested under that same success branch. Thus their direct syntax does not imply a logical-client invocation. For the local player specifically, `LocalClientPlayerEntity.takeDamage` returns false in each checked version.
- `MobEntity.tryDamage` similarly guards its direct knockback call with the boolean result of `target.takeDamage(...)`. No other `applyKnockback` call sites were found in the three exact decompiled trees.

## Incoming velocity equivalence

For 1.13.2, 1.14 and 1.14.4, `ClientPlayNetworkHandler.handleEntityVelocity` performs the same operation: schedule on the client thread, look up the entity, and if present call `lerpVelocity(packetX / 8000.0, packetY / 8000.0, packetZ / 8000.0)`. `Entity.lerpVelocity` overwrites the three components directly in 1.13.2; in 1.14+ it forwards them to the vector setter. `EntityVelocityS2CPacket.read` reads three signed shorts in all snapshots; `write` writes three shorts. On packet construction, each axis is clamped to [-3.9, 3.9], multiplied by 8000.0 and cast to `int`. The 1.14 and 1.14.4 packet source files are byte-identical. The decoded receive path is equivalent, and it applies a clamped, quantized server velocity rather than re-running normalization.

## Bound evidence

Draft reviewed: `knockback-normalization-source/comparison.md`, SHA-256 `347fe676372550af01945e956e28cdbf6492e8bd785f2a4c77312720830b6ed8`.

Mapping namespace: `ornithe-feather` (Feather Gen2). Provenance SHA-256 values:

- 1.13.2: `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383`; Feather `1.13.2+build.2`.
- 1.14: `bfb011801a64e9afaa4aca43e8a8ed40bf2a02f136ffc02e65b32e79315764fa`; `net.ornithemc:feather-gen2:1.14+build.2`.
- 1.14.4: `7167b135e0222fcf58ff5f36a36fbb604d5068f63a59165c8c4d18ce7d9b21c9`; Feather `1.14.4+build.2`.

The following decompiled-source hashes were independently recomputed and matched their `ornithe-feather.sources.sha256` entries:

| Version | LivingEntity | PlayerEntity | MobEntity | ClientPlayNetworkHandler | EntityVelocityS2CPacket | Vec3d |
| --- | --- | --- | --- | --- | --- | --- |
| 1.13.2 | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` | `525837fde9f889b47f32875a2d2ad5da0a4d8fb4bd91be92f5ea7da00e835502` | `4f65502fbee6476cef0838563f03a66f07a1a9ed8c63e580f5338ba5fe274f11` | `b86de977593e5c638977328c183647f1913bd7b94e601b9e858fa0057cd88dea` | `6941a43536e8aefaa435a4e5f1731b0fa26a9d96503d1f4d07de8950c297f3c5` |
| 1.14 | `1db4dd1aa95a06c48511e5567c0d3836f2a6e5a12a5cf03ab5c892e429198b79` | `f73879bd42103fa45f39cfe178ac82c7574c6306f00555af7f7e795976275dd7` | `dddecea2949b8db21bbcf6b459bd281b6b28d30ea3591d6ef594b648db66f443` | `d5c63c25b086b370588810ea999cab06a538fbf8a01a6d357b59bd6fd608ba5c` | `e71f8451b11a77c089afc8e60939e9f23934eb38e266d7fcae2481abdfcdc3f9` | `b69b990b768e52d6fac6b3ed82f60e7526e78d0837c868a61a4171ebca21040b` |
| 1.14.4 | `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61` | `2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df` | `3396c220d5402b01bf84cabc3f4eb39cc41ea162de2c22e92bb6d13d6f32aef9` | `6c840580285c7c7d4617e895edac82d4eebf8289dd4994e7b593cebeb49b43ef` | `e71f8451b11a77c089afc8e60939e9f23934eb38e266d7fcae2481abdfcdc3f9` | `b69b990b768e52d6fac6b3ed82f60e7526e78d0837c868a61a4171ebca21040b` |

## Limits

This is a bounded audit of the supplied immutable comparison and the cited exact Feather snapshots. It does not review damage/combat eligibility, establish a patch introduction date within the 1.13.2–1.14 interval, infer behavior for 1.14.1–1.14.3, audit other movement operations, or prove runtime parity. No tests, game launches, source generation, product edits, findings/backlog edits, Git inspection or wiki review were performed.