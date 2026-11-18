# C — 1.9.2 → 1.9.3 near-vertical Elytra view

**Verdict:** No source difference found in this bounded operation. Do not treat this pair slice as a movement-change finding or as full-pair coverage.

## Exact local source identity

Read-only canonical outputs: `decompiled_minecraft/1.9.2/ornithe-feather/` and `decompiled_minecraft/1.9.3/ornithe-feather/` (no alias or alternate mapping substituted). Each local provenance manifest identifies the exact release and Feather input:

| Release | Feather mapping input SHA-256 | client input SHA-256 | provenance manifest SHA-256 |
|---|---|---|---|
| 1.9.2 | `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859` (`1.9.2+build.2-mergedv2.jar`) | `ef87eb71250df1b871deab8d3ef10aac3290b9e78667ad548fde4d0e14af0d65` | `9928B467DEB35C7E7F891B4FEE1627A68FEECDF233C2076909FD4834B0380510` |
| 1.9.3 | `fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3` (`1.9.3+build.2-mergedv2.jar`) | `ab8fdde789c5b4614ffc98249e04fdcce7dfce320b55215bfe5418608af6a63b` | `43325CDD53CA34B88EE9AF8464782B323A948B98C8A556C4761F1E5737B84C3A` |

Provenance `sources` hashes: `LivingEntity.java` = 1.9.2 `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9`, 1.9.3 `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`; `Entity.java` = `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` / `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`; `MathHelper.java` is identical, `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151`.

## Compared source and bounded result

- `net/minecraft/entity/living/LivingEntity.java`: `moveRelative(float,float)` Elytra branch, 1.9.2 lines 1293–1341; 1.9.3 lines 1302–1350. The branch order and expressions match: active fall-flight guard, current look vector, radian pitch, horizontal-look denominator `j`, velocity-horizontal magnitude `k`, gliding lift, negative-vertical-velocity `j > 0.0` gate, raw-pitch `i < 0.0F` gate, subsequent `j > 0.0` steering gate, drag, then first `move(...)` call. The dry/non-lava/local movement gates are unchanged in this slice.
- `net/minecraft/entity/Entity.java`: `getRotationVector(float,float)`, 1.9.2 lines 1120–1125; 1.9.3 lines 1124–1129. Same float casts, expression order, lookup calls and `(g*h, i, f*h)` result.
- `net/minecraft/entity/living/LivingEntity.java`: `getLookVector()` and `getRotationVector(float)`, 1.9.2 lines 1808–1820; 1.9.3 lines 1817–1829. Both return the current rotation vector for tick delta `1.0F`, using `pitch` and `headYaw`; they do not interpolate the view for this call.
- `net/minecraft/util/math/MathHelper.java`: `sin(float)` line 15 and `cos(float)` line 19 use the same lookup (`SINE_TABLE[(int)(x * 10430.378F [+ 16384.0F]) & 65535]`) in both releases. The shared table initialization at lines 422–425 fills each entry with `(float)Math.sin(i * Math.PI * 2.0 / 65536.0)`.
- Caller bounds checked: `LivingEntity.travel` calls `moveRelative` before push handling (1.9.2 line 1702; 1.9.3 line 1711). `PlayerEntity.moveRelative` delegates to `super.moveRelative` on the ordinary non-abilities-flying path (1.9.2 lines 1366–1381; 1.9.3 lines 1372–1387).

Static numeric witness for a finite upward near-vertical view, `pitch = -89.999F`, `headYaw = 0.0F` (not a runtime test): the source float conversion gives `i = pitch * (float)(PI/180) < 0`; the lookup index for `MathHelper.cos(i)` is `0` and its table value is `0.0F`. In contrast, the current-look vector's vertical trig uses `cos(-pitch * (float)(PI/180))`, whose lookup index is `32768`; the table entry is the nonzero float `(float)sin(PI) = 1.22464685e-16`. With head yaw zero, the resulting horizontal look magnitude `j = sqrt(vec3d.x² + vec3d.z²)` is `1.22464685e-16 > 0.0`. Thus both the gliding `j > 0.0` gates and the raw-pitch `i < 0.0F` gate are true before the first move at this witness. This demonstrates the table-index alias/epsilon behavior, but it is present identically in both releases and establishes no 1.9.2→1.9.3 delta.

## Scope boundary

This is one adjacent-pair operation slice only. It does not establish full tick/inventory coverage, close dependencies outside the cited look/travel path, identify an intermediate introduction boundary, or claim runtime parity. No wiki, product implementation/history, online lookup, generation, build, test, game launch or server activity was used.

