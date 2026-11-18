# 1.10.1 to 1.10.2: near-vertical Elytra look

## Result

No source difference found in the assigned ordinary client glide slice. The relevant `LivingEntity.java`, `Entity.java`, and `MathHelper.java` bytes are identical across 1.10.1 and 1.10.2 (SHA-256 values below). This closes only the requested near-vertical look / pre-move slice; it is not full-pair coverage or an implementation/runtime claim.

## Exact source identities

Both sides use Ornithe Feather mappings, Feather Gen2 build.2, with the local canonical source roots `decompiled_minecraft/1.10.1/ornithe-feather/` and `decompiled_minecraft/1.10.2/ornithe-feather/`.

- 1.10.1 client artifact SHA-256: `338b205f20c22b9c676d9d3227a5e6c5e190f30ad84d7caad035150b3e05ba72`.
- 1.10.1 Feather `1.10.1+build.2-mergedv2.jar` SHA-256: `6e8ba44832b7b77d913962462bde55084005d5de6389232453afc0bc0339ea37`.
- 1.10.1 Feather metadata SHA-256: `8648e97ee398b11151cb2d441161629abde561afed9c933c18fbc622e2c9904a`.
- 1.10.1 local provenance file SHA-256: `a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f`.
- 1.10.2 client artifact SHA-256: `7cdf7fcdc1c92584a233bf3c42bd7f0df1bdad3007d306831fe50410692be1e9`.
- 1.10.2 Feather `1.10.2+build.2-mergedv2.jar` SHA-256: `18cffc56c2d8de89b50cb0328b174566236553c4aafb62afdc58a1e0ff0cadb0`; `1.10.2+build.2.tiny` SHA-256: `7c4055aa9becb027462fe4f4a9af8822de4df9b70ecb80a84e38fd9e81e33af1`.
- 1.10.2 local source-manifest SHA-256: `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`; provenance SHA-256: `13b208320bd6316d4b20eb0733f226cae151cae0ffd55ef3331a97223114adf6`; ready-marker SHA-256: `4c6d236e1d06ffc365452c48e73dfa2bf2414bbf4ea67a24a7010c5583906a2d`.

Relevant source hashes (equal on both releases):

| Relative source file | SHA-256 |
| --- | --- |
| `net/minecraft/entity/living/LivingEntity.java` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` |
| `net/minecraft/entity/Entity.java` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` |
| `net/minecraft/util/math/MathHelper.java` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` |

## Trace and bounds

- `LivingEntity.moveRelative(float,float)`, lines 1332–1371, contains the local movement gate, dry-medium gates, active `isFallFlying()` branch, glide math, damping, and first `move(...)` call. The branch is reached only after `(isLocallyControlled() || isLogicalSideForUpdatingMovement())`, and for the ordinary dry case the water/lava exclusions pass. It sets `fallDistance` to `1.0F` when `velocityY > -0.5`, reads `getLookVector()`, computes pitch radians as `float`, horizontal look magnitude `j = sqrt(vec3d.x * vec3d.x + vec3d.z * vec3d.z)`, horizontal velocity magnitude `k`, and look length `l`.
- `Entity.getRotationVector(float pitch, float yaw)`, lines 1148–1153, computes float angles and components in this order: `cos(-yaw * (float)(PI/180) - (float)PI)`, `sin(...)`, `-cos(-pitch * (float)(PI/180))`, `sin(...)`, then constructs `(g*h, i, f*h)`. `LivingEntity.getLookVector()` (lines 1853–1855) delegates to `getRotationVector(1.0F)`, which uses current pitch/headYaw.
- `MathHelper.sin/cos`, lines 15–20, use `SINE_TABLE[(int)(x * 10430.378F) & 65535]`, with cosine adding `16384.0F` before integer conversion. Table entries are initialized at line 424 using `(float)Math.sin(i * Math.PI * 2.0 / 65536.0)`. This truncating float-index path is identical on both versions, so any finite view angles that alias to the same table index do so on both.
- For a downward view near negative Y (positive pitch), finite horizontal magnitude is used only behind `j > 0.0` gates: the falling-velocity correction at lines 1349–1354 and horizontal steering at 1363–1366. The upward-only branch `if (i < 0.0F)` at 1356–1361 is separate. After these branches, velocity is damped (`0.99F`, `0.98F`, `0.99F`) and the first glide movement call occurs at line 1371. Thus the `j` guards precede the divisions by `j` relevant to this downward near-vertical path. A table-aliased zero horizontal magnitude skips those guarded calculations; a nonzero finite magnitude uses the same indexed components and arithmetic on both releases.

This report excludes malformed/non-finite inputs, the server side, glide activation/start/finalization, and A/B/runtime tests. It does not assess collision resolution after the first move or other glide conditions.
