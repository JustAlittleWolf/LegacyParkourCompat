# Independent audit: 1.10.1–1.10.2 near-vertical Elytra look

## Verdict

**Accepted for the assigned dry, already-gliding local-player slice.** The frozen claim is supported: the checked look-vector, glide, and sine-table sources are byte-identical in the exact 1.10.1 and 1.10.2 Ornithe Feather trees. For a finite downward view near vertical (positive pitch, negative look Y), both versions use the same current-pitch/head-yaw vector, float conversions, lookup-table casts, horizontal magnitude, and branch ordering. This is a bounded source verdict; it does not establish full-pair coverage or runtime parity.

## Audited evidence identity

The frozen input is `c-glide-1101-1102-near-vertical-look-alias/report.md`, SHA-256 `aba719842bdc89f48fa4ef9788683fa8b56f4c41154ef1284ac246a4ae6c6e83` (verified against the exact file bytes).

Canonical source roots:

- `decompiled_minecraft/1.10.1/ornithe-feather/`
- `decompiled_minecraft/1.10.2/ornithe-feather/`

The 1.10.1 `.decompile-provenance.json` hash is `a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f`. Its input identity includes client `338b205f20c22b9c676d9d3227a5e6c5e190f30ad84d7caad035150b3e05ba72`, Feather merged mapping jar `6e8ba44832b7b77d913962462bde55084005d5de6389232453afc0bc0339ea37`, and Feather metadata `8648e97ee398b11151cb2d441161629abde561afed9c933c18fbc622e2c9904a`.

The 1.10.2 sidecars verify the report's alternate manifest layout: `ornithe-feather.sources.sha256` SHA-256 `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`, `ornithe-feather.provenance.json` SHA-256 `13b208320bd6316d4b20eb0733f226cae151cae0ffd55ef3331a97223114adf6`, and `ornithe-feather.ready.json` SHA-256 `4c6d236e1d06ffc365452c48e73dfa2bf2414bbf4ea67a24a7010c5583906a2d`. The exact input pins in its provenance/artifact manifest match: client `7cdf7fcdc1c92584a233bf3c42bd7f0df1bdad3007d306831fe50410692be1e9`, Feather merged mapping jar `18cffc56c2d8de89b50cb0328b174566236553c4aafb62afdc58a1e0ff0cadb0`, and Tiny mapping `7c4055aa9becb027462fe4f4a9af8822de4df9b70ecb80a84e38fd9e81e33af1`.

## Source trace and independent check

All listed source SHA-256 values were computed from the canonical files on both sides; the 1.10.2 source manifest also contains the matching digests.

| Source/member | Lines | SHA-256 in both 1.10.1 and 1.10.2 |
|---|---:|---|
| `net/minecraft/entity/living/LivingEntity.java`, `moveRelative(float,float)` | 1332–1371 | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` |
| `net/minecraft/entity/living/LivingEntity.java`, `getLookVector()` / `getRotationVector(float)` | 1853–1861 | same file digest above |
| `net/minecraft/entity/Entity.java`, `getRotationVector(float,float)` | 1148–1153 | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` |
| `net/minecraft/util/math/MathHelper.java`, `sin`, `cos`, table initialization | 15–20, 422–424 | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` |

In the active glide branch, `getLookVector()` selects tick delta `1.0F`; `LivingEntity` then supplies current `pitch` and `headYaw`. `Entity.getRotationVector(float,float)` computes float trig arguments and components in the same order on both sides: yaw cosine/sine, pitch cosine/sine, then `(g*h, i, f*h)`. For a finite downward pitch around +90 degrees, the lookup-derived Y component is negative. The glide code separately computes float `i = pitch * (float)(PI / 180.0)`, then double `j = sqrt(x*x + z*z)`. The sine-table access uses `(int)(x * 10430.378F) & 65535` (cosine adds `16384.0F` before that cast); table entries use the same float cast of `Math.sin(...)` on both releases.

The downward path's fall-velocity correction divides by `j` only under `velocityY < 0.0 && j > 0.0`; horizontal steering divides by `j` only under `j > 0.0`. Its separate `i < 0.0F` branch is false for this positive-pitch downward view. Thus a table-aliased zero horizontal magnitude skips those guarded divisions, while any nonzero magnitude and selected LUT components follow identical source operations on both releases. This conclusion is restricted to that downward-view path; the upward branch is outside the assignment.

## Bounds

No malformed/non-finite inputs, glide activation/finalization, other Elytra conditions, post-first-move collision resolution, A/B or runtime tests, server behavior, or broader pair inventory were reviewed.
