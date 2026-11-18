# 1.11 → 1.11.1: near-vertical Elytra look-vector alias

## Bounded result

**No source difference found in the assigned operation.** For an ordinary, dry, locally controlled player whose Elytra flight is already active, the near-negative-vertical camera path before the first `move` is the same in exact 1.11 and 1.11.1 Feather sources. This is a bounded no-difference result, not full pair coverage and not a runtime-parity verdict. No intermediate patch boundary is inferred.

## Exact evidence

Both sides are locally published `ornithe-feather` sources, generated as Feather-mapped exact client versions. The `ready` marker's source-manifest and provenance digests match the corresponding files; the listed source hashes match each manifest entry. The `MathHelper.java` SHA-256 is identical across the pair.

| Release | Client SHA-256 | Feather artifacts (merged mapping JAR / Tiny) SHA-256 | Source manifest SHA-256 | Provenance SHA-256 |
|---|---|---|---|---|
| 1.11 | `f0d11987ea743f55a2c03bf77216e6dc9baea7201cc7bf498ceb18043e984634` | `658ad8c5a5d661eb5c2f97c00a47f6e02c02d5eee1eb1005f8e2926eb95dec44` / `46bd6702a0983ad667109aadad90b2e6390ed5cd5999561a8a904956f4baf9c0` | `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` | `6f775c806fd1401a4d1756e9ef88da8d73185fe1d13598235f85c424405f4c40` |
| 1.11.1 | `eff72a31339cd9a96a61092eed2d95b95e4cc91516bea4e9105c3d73574147f6` | `83c2f49749b8eff663bf67664204e9cf02c75174e264d4cd57a6ba9f01bd2928` / `4fa160c09d83bf61ae21bb74ab1e33b6aabe9b8ec89904b266ad53cecc9c36e6` | `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d` | `0f6c92b8fbf4b1053a26a4cb61dc8f20c695e74a1963b49d911b8c7fbf01a055` |

Exact local files and hashes:

| File relative to `decompiled_minecraft/<release>/` | 1.11 SHA-256 | 1.11.1 SHA-256 |
|---|---|---|
| `ornithe-feather/net/minecraft/entity/living/LivingEntity.java` | `1f2e31fa6a905ccc2a51a0338f2aaf448f2b0569f28644e8205d864e570d9b2b` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` |
| `ornithe-feather/net/minecraft/entity/Entity.java` | `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97` | `5a906de1d40a4a5ba8aedc9a6a9dc704e0ed862df177cf967fb1a9454765fa96` |
| `ornithe-feather/net/minecraft/util/math/MathHelper.java` | `97aa2499681fdd8cee6564f777310ee396bc447fef78c2273a990e9202d9641b` | `97aa2499681fdd8cee6564f777310ee396bc447fef78c2273a990e9202d9641b` |

### Compared expressions and call order

- `LivingEntity.moveRelative`: 1.11 `LivingEntity.java:1386–1425`; 1.11.1 `LivingEntity.java:1388–1427`. Under the local movement and already-fall-flying gates, both call `getLookVector()`, convert `pitch` to radians, compute horizontal look length `j = sqrt(x*x + z*z)`, horizontal velocity length `k`, look-vector length `l`, and the cosine-derived term in that order. Both then apply gravity. When descending and `j > 0`, both apply the same dive acceleration. Both use the same `i < 0.0F` pitch-sign branch and subtract `vec3d.x/z * o / j` before horizontal-speed alignment. Both apply X/Y/Z drag and then make the first Elytra-branch `move(MoverType.SELF, ...)` call.
- `LivingEntity.getLookVector`: 1.11 `LivingEntity.java:1914–1915`; 1.11.1 `LivingEntity.java:1916–1917`. Both are the same alias: `return this.getRotationVector(1.0F);`.
- `LivingEntity.getRotationVector(float)`: 1.11 `LivingEntity.java:1919–1927`; 1.11.1 `LivingEntity.java:1921–1929`. The `tickDelta == 1.0F` branch in both uses current pitch and head yaw; the other branch interpolates last/current pitch and head yaw in the same order. This call passes `1.0F`, so the current-angle branch is selected.
- `Entity.getRotationVector(float,float)`: 1.11 `Entity.java:1170–1175`; 1.11.1 `Entity.java:1229–1234`. Both evaluate cosine of negated yaw in radians minus π, sine of that same yaw expression, negative cosine of negated pitch in radians, then sine of negated pitch in radians, and return `(g*h, i, f*h)` in the same order.
- `MathHelper.sin/cos`: both exact files are byte-identical (same SHA-256). `MathHelper.java:15–20` uses `SINE_TABLE[(int)(x * 10430.378F) & 65535]` and `SINE_TABLE[(int)(x * 10430.378F + 16384.0F) & 65535]`; the shared static initializer at `MathHelper.java:426–429` fills the same 65,536-entry table with `(float)Math.sin(i * Math.PI * 2.0 / 65536.0)`.

For the assigned ordinary near-vertical case with a finite positive horizontal look length, neither version changes the LUT-selected look vector, the `j` computation, the negative-pitch branch or its division order. No trajectory or numeric outcome beyond that source-level equivalence is asserted.

## Source manifest checks

The ready markers declare source-manifest SHA-256 `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` and provenance SHA-256 `6f775c806fd1401a4d1756e9ef88da8d73185fe1d13598235f85c424405f4c40` for 1.11; for 1.11.1 they declare `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d` and `0f6c92b8fbf4b1053a26a4cb61dc8f20c695e74a1963b49d911b8c7fbf01a055`. The `sources.sha256` entries for all three compared Java files match their on-disk SHA-256 values above.

## Boundary

This comparison covers only the named pre-first-move look-vector alias/math path for already-active Elytra flight in ordinary dry local movement. It does not compare activation/deactivation, final velocity/position effects after movement, other Elytra/gliding callers, malformed inputs, or the rest of the player tick. No game launch, tests, build, decompilation, network lookup, product-source inspection, or Git-history inspection was performed.
