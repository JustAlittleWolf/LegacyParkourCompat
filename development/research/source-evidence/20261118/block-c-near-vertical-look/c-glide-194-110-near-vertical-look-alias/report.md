# 1.9.4 → 1.10: Elytra glide look vector near vertical

## Result

No source difference found in the bounded operation. For an ordinary local player already in Elytra flight, dry, with a finite negative near-limit pitch whose trigonometric lookup yields a nonzero horizontal component (for example `pitch = -89.0F`), both releases use the same look-vector math, the same glide branch order, and the same denominator sign/type. This comparison does not establish any intermediate patch boundary.

On the first dry glide movement call, `LivingEntity.moveRelative` enters `isFallFlying()`, obtains `getLookVector()`, and computes `j` as the `double` square root of the horizontal look-vector components squared. A negative pitch makes `i = pitch * (float)(PI / 180.0)` negative; in that branch, `j > 0.0` is checked before the descent correction division, while the later negative-pitch correction divides by `j` without a separate guard. For the stated finite, non-aliased view, `j` is positive: it is produced from squared float-derived components, promoted into double arithmetic, and passed through `Math.sqrt`. The source is identical on both sides; no changed aliasing or sign behavior is evidenced here.

`LivingEntity.getLookVector()` calls `getRotationVector(1.0F)`. The living-entity implementation supplies `pitch` and `headYaw`; inherited `Entity.getRotationVector(float,float)` computes float `cos`/`sin` via the original LUT and returns `Vec3d(g * h, i, f * h)`. `MathHelper.sin/cos` use the same `(int)(x * 10430.378F [ + 16384.0F]) & 65535` indexing expressions in both releases. The player's `moveRelative` override delegates to `super` on the ordinary non-creative-flying path, and `LivingEntity`'s travel path calls `moveRelative` in both releases. The newly present `Vec3d.fromRotation` in 1.10 is not called by this path.

## Exact source evidence

All paths below are relative to `decompiled_minecraft/<version>/ornithe-feather/`.

| Slice | 1.9.4 | 1.10 |
|---|---|---|
| Elytra movement / `LivingEntity.moveRelative` | `net/minecraft/entity/living/LivingEntity.java:1302–1341` | `net/minecraft/entity/living/LivingEntity.java:1332–1371` |
| Local player delegation / `PlayerEntity.moveRelative` | `net/minecraft/entity/living/player/PlayerEntity.java:1372–1392` | `net/minecraft/entity/living/player/PlayerEntity.java:1391–1411` |
| Look-vector source / `LivingEntity.getLookVector`, `getRotationVector(float)` | `net/minecraft/entity/living/LivingEntity.java:1817–1829` | `net/minecraft/entity/living/LivingEntity.java:1853–1865` |
| Rotation components / `Entity.getRotationVector(float,float)` | `net/minecraft/entity/Entity.java:1124–1130` | `net/minecraft/entity/Entity.java:1148–1154` |
| Trig LUT access / `MathHelper.sin`, `cos` | `net/minecraft/util/math/MathHelper.java:15–21` | `net/minecraft/util/math/MathHelper.java:15–21` |
| Travel call site / `LivingEntity` travel | `net/minecraft/entity/living/LivingEntity.java:1711` | `net/minecraft/entity/living/LivingEntity.java:1747` |

The bounded glide block and trig LUT methods are textually identical across the two source trees. `MathHelper.java` is byte-identical (`43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151`).

## Source publication identity and hashes

- **1.9.4, Ornithe Feather:** exact release `1.9.4`; mapping jar `feather-gen2-1.9.4+build.2-mergedv2.jar` and Tiny `feather-gen2-1.9.4+build.2.tiny`; client SHA-256 `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908`. `ornithe-feather.ready.json` records source-manifest SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`, which matches the local `ornithe-feather.sources.sha256` bytes. The local artifact manifest pins the Feather merged jar at `49a38d0adfbda1749e519c29844116e9f22e895cb505633261b7f587268f4125` and Tiny at `9e21708d4bc32a43ac404735ea3238465889797110204a0375bcb069a3798027`.
- **1.10, Ornithe Feather build.2:** `.decompile-provenance.json` pins Feather metadata input SHA-256 `a1eb1ccff25e2b76fa1d08e9b515508e97ac4837fbcacbd7f77b3a01026d3950`, merged mapping jar SHA-256 `ad7cf6744d81d0260c9d97ddfe8250b2fdbd54fe332d4d00266b3545d8ddf8dd`, and client SHA-256 `c00f8170ebf4fd834f552246743a64885a6dffb3dd1e3a3f3dba9676b7a0af35`. The same provenance file contains per-source SHA-256 entries; each below was independently recomputed from the present source bytes.

| Relative source file | 1.9.4 SHA-256 | 1.10 SHA-256 |
|---|---|---|
| `net/minecraft/entity/living/LivingEntity.java` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` |
| `net/minecraft/entity/Entity.java` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85` | `db7bbbc15e2bcb0de18476fec1b88e73eccf8ae63b170276b1d8327dd0be796b` |
| `net/minecraft/util/math/MathHelper.java` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` |
| `net/minecraft/util/math/Vec3d.java` | `644edb1d25ba496f9a0bb74f8148554163975bd42ee5de0d0cac295307f64aa5` | `059dd21505c13b6febbbe880889f3aa6b00f7648ba1b770268eb8841674e90e8` |

## Scope limits

This checks only the standard finite negative near-limit look path used by an already-active local player's dry Elytra movement on exact 1.9.4 and 1.10 Feather sources. It does not cover exact vertical/LUT-aliased angles, malformed state, activation/startup, server behavior, later movement, rendering or any other movement mechanics.
