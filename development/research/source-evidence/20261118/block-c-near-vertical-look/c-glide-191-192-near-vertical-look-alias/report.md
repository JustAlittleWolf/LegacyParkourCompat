# 1.9.1 to 1.9.2 Elytra near-vertical look slice

## Result

No source difference found in the assigned slice. The compared `LivingEntity.java`, `Entity.java`, and `MathHelper.java` files are byte-identical across these two releases (their SHA-256 values match pairwise). Therefore this pair does not establish a glide change for the requested ordinary dry, already-active Elytra path. This is source-only evidence, not a runtime parity claim.

## Exact inputs and provenance

Canonical local Feather trees:

- `decompiled_minecraft/1.9.1/ornithe-feather/`
- `decompiled_minecraft/1.9.2/ornithe-feather/`

Both provenance manifests record the `lpc-decompile-v1` pipeline, single-threaded decompilation, and Eclipse Adoptium Java 25.0.3+9 on Windows 11 amd64. The manifest pins the corresponding Feather Gen2 build.2 artifact and exact release metadata:

| Release | Feather artifact SHA-256 | Version metadata SHA-256 | Provenance manifest SHA-256 |
|---|---|---|---|
| 1.9.1 | `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3` | `da56c9e7efe5c929b2ec8d99a8e2bbc6825847f2852a0e717fcd6c674db73dc4` | `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` |
| 1.9.2 | `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859` | `5bad5e67d0870fec69bbab8fcae8d0525a274668bb472eb09d1552359d08edd1` | `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510` |

The manifests list these source hashes in both trees:

| Source, relative to mapping root | SHA-256 in each release |
|---|---|
| `net/minecraft/entity/living/LivingEntity.java` | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` |
| `net/minecraft/entity/Entity.java` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` |
| `net/minecraft/util/math/MathHelper.java` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` |

The manifests record the Feather mapping family and build artifact; they do not provide a separate mapping-file identity. No online mappings or sources were used.

## Compared source path

All line numbers below are the same in both releases.

- `LivingEntity.moveRelative(float,float)`, lines 1293–1297: enters through `(isLocallyControlled() || isLogicalSideForUpdatingMovement())`, the water and lava gates, then `isFallFlying()`. The assignment assumes ordinary dry local movement and a pre-existing true fall-flying flag; activation is outside this slice.
- `LivingEntity.moveRelative`, lines 1302–1324: obtains `vec3d = getLookVector()`, computes `i = pitch * (float)(PI / 180.0)`, then `j = sqrt(vec3d.x*vec3d.x + vec3d.z*vec3d.z)`, horizontal speed `k`, and vector length `l`; it applies the cosine lift term before the negative vertical-velocity correction guarded by `velocityY < 0 && j > 0`. For negative `i`, it then applies `o = k * -sin(i) * 0.04`, adds `o * 3.2` vertically, and subtracts `vec3d.x * o / j` and `vec3d.z * o / j` with no `j > 0` guard on those divisions. The later horizontal alignment is separately guarded by `j > 0`; drag and movement follow.
- `LivingEntity.getLookVector()` and `getRotationVector(float)` lines 1808–1822: `getLookVector()` requests tick delta `1.0F`; that path uses current pitch and head yaw.
- `Entity.getRotationVector(float,float)`, lines 1120–1125: constructs look components from `MathHelper` cosine/sine calls, including pitch-derived `h = -cos(-pitch * (float)(PI / 180.0))`, and returns `(g*h, sin(-pitch*...), f*h)`.
- `MathHelper.sin`/`cos`, lines 15–20, and static table initialization lines 423–424: both releases use the same 65,536-entry float sine table; lookup uses `(int)(x * 10430.378F)` (cos adds `16384.0F`), masked with `65535`; entries are `(float)Math.sin(i * PI * 2.0 / 65536.0)`. The cast and mask therefore produce the same truncation/index behavior in both releases.

For the requested finite negative near-vertical camera case, the exact horizontal denominator is the `j` expression above, derived from the raw horizontal look-vector components. The glide pitch scalar `i` and the look vector's pitch trigonometry are computed separately in the same order on both sides. The source leaves the negative-pitch horizontal divisions unguarded in both releases. This report does not claim numerical values for any particular angle or trajectory.

## Limits

This bounded comparison covers only the active Elytra travel branch, look-vector construction, and its sine/cosine table implementation and relevant reachability gates. It excludes activation, malformed or non-finite inputs, end-of-glide behavior, other movement branches, and runtime/A-B testing. It does not close the full 1.9.1–1.9.2 player-movement pair.
