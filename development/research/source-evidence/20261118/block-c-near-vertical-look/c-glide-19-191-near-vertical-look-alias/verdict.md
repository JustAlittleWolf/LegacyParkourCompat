# Independent source audit: 1.9 → 1.9.1 Elytra near-vertical look lookup

## Verdict

**Accepted, bounded no-difference.** The frozen claim is supported for the assigned dry, already-active local-player slice through the first `move(...)` call. The source computes the look vector using the legacy sine-table lookup, then derives the horizontal denominator from that lookup result. For finite negative pitch near vertical, the `i < 0.0F` nose-up branch does not require `j > 0.0`, although the later steering block does. The look-vector X/Z divisions by `j` therefore remain observable in this path. These expressions, casts, order, and gates match across 1.9 and 1.9.1. The source confirms the shared behavior; it does not imply that the behavior is safe or that this audit proves runtime parity.

## Immutable input and local provenance

Frozen report: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-glide-19-191-near-vertical-look-alias/report.md`, SHA-256 `e6be06d5666bfc27b902ccd947b94fb36c1ee1cacf91843b3882323d1cea2600`.

Read-only source roots: `decompiled_minecraft/1.9/ornithe-feather/` and `decompiled_minecraft/1.9.1/ornithe-feather/`.

| Release | Provenance manifest SHA-256 | Mojang client SHA-256 | Feather metadata SHA-256 | Feather mergedv2 JAR SHA-256 |
|---|---|---|---|---|
| 1.9 | `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a` | `0273b5c79d6b96799c6480ae61366d6b1d042655970de87f77f1a3f1ebea9307` | `ca32cbff5f91d52f6563a2d50a20e3f0fb1e1f76dbb6e41e53c847d2d6977371` | `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a` (`1.9+build.2`) |
| 1.9.1 | `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` | `56ad7e53f6f4ad393844e274aa2190692b61d2ad9f6b3d12b33a8b7332fd51f9` | `da56c9e7efe5c929b2ec8d99a8e2bbc6825847f2852a0e717fcd6c674db73dc4` | `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3` (`1.9.1+build.2`) |

The manifest hashes, manifest input identities, and mappings' local metadata/pin records agree with the report. The actual local SHA-256 values for each of these source files match the corresponding manifest entries on both releases:

| Source file | 1.9 SHA-256 | 1.9.1 SHA-256 |
|---|---|---|
| `net/minecraft/util/math/MathHelper.java` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` | same |
| `net/minecraft/entity/Entity.java` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | same |
| `net/minecraft/entity/living/LivingEntity.java` | `5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb` | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` |

## Independently retraced slice

- `LivingEntity.moveRelative(float,float)`: 1.9 lines 1289–1328; 1.9.1 lines 1293–1332. With the ordinary dry local path and an already-set fall-flying flag, the method obtains `getLookVector()`, computes `i = pitch * (float)(PI / 180.0)`, and computes `j = sqrt(vec3d.x * vec3d.x + vec3d.z * vec3d.z)`. The nose-up gate is `i < 0.0F`; its `o` expression and X/Z updates divide by `j` without a local `j > 0.0` guard. The later steering block separately has `if (j > 0.0)`. Both then apply X/Y/Z drag and call `move(...)` before subsequent collision handling. The compared method slice is identical in operation order and expressions.
- `LivingEntity.getLookVector()` delegates to `getRotationVector(1.0F)`; the living-entity override uses current pitch/head yaw for this non-interpolated case (1.9 lines 1803–1816; 1.9.1 lines 1807–1820). The override is unchanged across the pair.
- `Entity.getRotationVector(float,float)` uses the same float degree conversion, lookup calls, signs, products and `Vec3d` construction in both releases (lines 1120–1125).
- `MathHelper.sin/cos` use the same 65,536-entry float table, `(int)` truncation and mask; initialization is identical (lines 15–20, 422–424). Thus `j` is the horizontal magnitude of the lookup-derived vector, not an independently recomputed ideal cosine. A lookup alias can make `j` differ from the unquantized geometric expectation; the source confirms that both releases feed that same `j` into the unguarded nose-up divisions.
- `isFallFlying()` reads flag 7 in both files. In the bounded slice the outer local-control and dry-fluid checks admit the path, and the active fall-flying branch reaches its first `move(...)` call with matching gates and behavior.

## Boundary

This audit covers only the frozen report's adjacent 1.9→1.9.1 claim and stated already-active local Elytra path. Activation/eligibility, startup/final handling, A/B or gameplay execution, unrelated movement branches, later collision/damage, non-player behavior and pair-wide coverage are outside this verdict. No source generation, product inspection, Git/history inspection, tests or launches were performed.
