# 1.9 → 1.9.1 Elytra near-vertical look lookup

## Verdict

No source-confirmed version delta was found in the assigned path from the already-active fall-flying branch through its first movement call. The near-vertical lookup and the upward-pitch divide are present with the same expressions and gates in both releases. This is a bounded no-difference result, not a movement finding for this pair.

## Exact source identity

Both sides are the exact local decompilations for the stable release IDs 1.9 and 1.9.1, mapped with Ornithe Feather build 2. The pinned source provenance uses pipeline lpc-decompile-v1 and the same recorded Java 25 runtime/toolchain.

| Release | Mojang client SHA-256 | Feather metadata SHA-256 | Feather mergedv2 mapping JAR SHA-256 | Source provenance SHA-256 |
|---|---|---|---|---|
| 1.9 | 0273b5c79d6b96799c6480ae61366d6b1d042655970de87f77f1a3f1ebea9307 | ca32cbff5f91d52f6563a2d50a20e3f0fb1e1f76dbb6e41e53c847d2d6977371 | a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a (1.9+build.2) | 8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a |
| 1.9.1 | 56ad7e53f6f4ad393844e274aa2190692b61d2ad9f6b3d12b33a8b7332fd51f9 | da56c9e7efe5c929b2ec8d99a8e2bbc6825847f2852a0e717fcd6c674db73dc4 | 6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3 (1.9.1+build.2) | e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a |

Mojang version-metadata JSON SHA-256: 1.9 = 8d6f1e7f3a99f1236914b61e64eeade955ba21e8150563457142d3981aeee688; 1.9.1 = b423c2ee9dc43f9204fea8bf8cbad1780c52fbb62b107dc948cc72b56491fd56.

Relevant decompiled source-file SHA-256 (also recorded in each .decompile-provenance.json):

| Relative file | 1.9 | 1.9.1 |
|---|---|---|
| net/minecraft/util/math/MathHelper.java | 43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151 | same |
| net/minecraft/entity/Entity.java | 74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a | same |
| net/minecraft/entity/living/LivingEntity.java | 5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb | d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9 |

Source roots are decompiled_minecraft/1.9/ornithe-feather/ and decompiled_minecraft/1.9.1/ornithe-feather/.

## Compared slice and facts

- net/minecraft/entity/living/LivingEntity.java, 1.9 lines 1289–1328; 1.9.1 lines 1293–1332. In the isFallFlying() branch, the source obtains vec3d = getLookVector(), computes pitch radians i, horizontal look magnitude j = sqrt(vec3d.x * vec3d.x + vec3d.z * vec3d.z), and horizontal velocity magnitude k. In both releases the nose-up branch is gated by i < 0.0F; within it the source computes o = k * -MathHelper.sin(i) * 0.04, adds o * 3.2 to vertical velocity, and divides the look-vector X/Z components by j for the horizontal updates. The separate later steering block is gated by j > 0.0; it does not guard the preceding nose-up branch. Both paths then apply drag and call move(...) before any later collision handling.
- net/minecraft/entity/living/LivingEntity.java, 1.9 lines 1803–1816; 1.9.1 lines 1807–1820. getLookVector() delegates to getRotationVector(1.0F); the living-entity implementation uses current pitch and head yaw for the non-interpolated case and interpolated last/current pitch and head yaw otherwise. Expressions and gates match.
- net/minecraft/entity/Entity.java, both releases lines 1110–1125. Rotation-vector construction is byte-identical. It derives the vertical component from -MathHelper.cos(-pitch * (float)(Math.PI / 180.0)) and horizontal components by multiplying that result by the yaw lookup values.
- net/minecraft/util/math/MathHelper.java, both releases lines 8, 15–21 and 422–424. Both use a 65,536-element float sine table. sin(x) indexes SINE_TABLE[(int)(x * 10430.378F) & 65535]; cos(x) indexes SINE_TABLE[(int)(x * 10430.378F + 16384.0F) & 65535]. The cast truncates toward zero before the 16-bit mask; there is no interpolation. The table entries use (float)Math.sin(i * Math.PI * 2.0 / 65536.0). The lookup phase and table initialization are byte-identical in this pair.

For a finite negative pitch near vertical, i < 0.0F admits the nose-up branch. The source's actual denominator is the horizontal magnitude of the old lookup-derived look vector, j, rather than a separately recomputed ideal trigonometric value. The lookup quantization and unguarded j division are shared by 1.9 and 1.9.1, so this slice supports no adjacent-version change claim. No malformed inputs, live execution, reproduction, or A/B comparison were used.

## Scope

Examined only the ordinary dry, already-fall-flying path with the default chest Elytra as the stated precondition, finite negative near-vertical pitch, and local player movement through the first move(...) call. No Elytra activation route, other movement branches, later collision/damage behavior, non-player behavior, unrelated armor/world-border changes, wiki material, product implementation, or Git history was inspected. No sources were generated or modified.
