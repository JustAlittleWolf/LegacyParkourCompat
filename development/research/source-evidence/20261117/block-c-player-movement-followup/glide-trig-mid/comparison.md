# Elytra look-vector and trig source comparison

## Scope

Blind source-only comparison of the body-yaw/look-vector provider expression and `Mth` sine-table index arithmetic consumed by dry Elytra travel. This does not compare Elytra activation, acceleration, gravity, effects, collision response, sound, or other travel math. In the examined source, “body-yaw” is not the yaw input: the provider consumes entity yaw (`yRot` / `getYRot()`), not `yBodyRot` or `yHeadRot`.

Mapping family: Mojmap. Every exact stable release from 1.14.4 through 1.20.6 present in the canonical tree was checked. For each version, the exact `Entity.java`, `LivingEntity.java`, and `Mth.java` source hashes matched its published outer source hash manifest or nested `.decompile-provenance.json` source map. Outer readiness/provenance records agreed on the release and Mojmap namespace; outer source-manifest hashes matched. Nested source maps matched for versions without outer records. All checks passed.

Legend: `O` = outer `<mapping>.ready.json`, `<mapping>.provenance.json`, and `<mapping>.sources.sha256`; `N` = hidden `.decompile-provenance.json` source map. Paths below are relative to `decompiled_minecraft/<version>/mojmap/`. Line anchors identify `Entity.getLookAngle`, `Entity.calculateViewVector`, `Mth.sin`, and the `LivingEntity.travel` line that obtains the look vector, respectively.

| Exact release | Provenance | Entity look | Entity calc | Mth sin | Travel look |
|---|---:|---:|---:|---:|---:|
| 1.14.4 | O | 1697 | 1251 | 25 | 1799 |
| 1.15 | O | 1661 | 1219 | 25 | 1848 |
| 1.15.1 | O | 1661 | 1219 | 25 | 1848 |
| 1.15.2 | O | 1661 | 1219 | 25 | 1848 |
| 1.16 | O | 1680 | 1237 | 25 | 1983 |
| 1.16.1 | O | 1680 | 1237 | 25 | 1983 |
| 1.16.2 | O | 1700 | 1243 | 25 | 1985 |
| 1.16.3 | N | 1700 | 1243 | 25 | 1985 |
| 1.16.4 | N | 1700 | 1243 | 25 | 1985 |
| 1.16.5 | O | 1700 | 1243 | 25 | 1985 |
| 1.17 | N | 1829 | 1337 | 43 | 2070 |
| 1.17.1 | O | 1831 | 1339 | 43 | 2074 |
| 1.18 | O | 1813 | 1311 | 43 | 2076 |
| 1.18.1 | O | 1813 | 1311 | 43 | 2076 |
| 1.18.2 | O | 1816 | 1314 | 43 | 2080 |
| 1.19 | O | 1841 | 1339 | 43 | 2113 |
| 1.19.1 | O | 1841 | 1334 | 44 | 2113 |
| 1.19.2 | O | 1841 | 1334 | 44 | 2113 |
| 1.19.3 | O | 1850 | 1339 | 49 | 2120 |
| 1.19.4 | O | 1867 | 1345 | 42 | 2051 |
| 1.20 | N | 1970 | 1448 | 42 | 2071 |
| 1.20.1 | O | 1970 | 1448 | 42 | 2071 |
| 1.20.2 | O | 2004 | 1458 | 42 | 2105 |
| 1.20.3 | N | 2005 | 1459 | 47 | 2102 |
| 1.20.4 | O | 2005 | 1459 | 47 | 2102 |
| 1.20.5 | O | 2027 | 1479 | 48 | 2171 |
| 1.20.6 | O | 2027 | 1479 | 48 | 2171 |

For each row, `Entity.java` is `net/minecraft/world/entity/Entity.java`, `Mth.java` is `net/minecraft/util/Mth.java`, and `LivingEntity.java` is `net/minecraft/world/entity/LivingEntity.java`. The `Mth.cos(float)` declaration follows the listed `sin(float)` declaration four lines later; both method bodies were checked in every exact release.

## Source findings

`LivingEntity.travel` obtains its Elytra look vector with `this.getLookAngle()` (the `Travel look` line above) and passes that vector into the glide branch. The provider is inherited from `Entity` in the examined source. Across 1.14.4–1.16.5, `Entity.getLookAngle()` returns `calculateViewVector(this.xRot, this.yRot)`. Across 1.17–1.20.6, it calls `calculateViewVector(this.getXRot(), this.getYRot())`. These are the entity's pitch and yaw inputs; they do not use the head- or body-rotation fields.

The getter transition is present in the exact 1.17 source, not inferred from the endpoints. In every exact 1.17–1.20.6 snapshot, `getXRot()` returns `this.xRot` and `getYRot()` returns `this.yRot`. The declaration line pairs (Y then X) are: 1.17 `3005/3017`; 1.17.1 `3007/3019`; 1.18 and 1.18.1 `2998/3010`; 1.18.2 `3015/3027`; 1.19, 1.19.1, and 1.19.2 `3039/3055`; 1.19.3 `3074/3090`; 1.19.4 `3135/3151`; 1.20 and 1.20.1 `3253/3269`; 1.20.2 `3283/3299`; 1.20.3 and 1.20.4 `3289/3305`; 1.20.5 and 1.20.6 `3304/3320`. Each getter body immediately returns the corresponding field. This closes the provider alias dependency release by release.

In every examined `Entity.calculateViewVector` body, operation order and types are unchanged: convert pitch to radians as `pitch * (float)(Math.PI / 180.0)`; convert yaw as `-yaw * (float)(Math.PI / 180.0)`; compute cosine then sine of yaw radians, then cosine and sine of pitch radians; construct `(sinYaw * cosPitch, -sinPitch, cosYaw * cosPitch)`. The source variable names and `protected`/`public` visibility vary, but no arithmetic, cast, sign, evaluation order, or component order change was found. In 1.20.5 the method is public; its body remains the same as 1.20.4 and 1.20.6.

In every examined `Mth.java`, the 65,536-entry sine table is initialized in index order using `(float)Math.sin(index * Math.PI * 2.0 / 65536.0)`. `sin(angle)` indexes `SIN[(int)(angle * 10430.378F) & 65535]`. `cos(angle)` indexes `SIN[(int)(angle * 10430.378F + 16384.0F) & 65535]`. The float scale, float quarter-turn offset, cast position, masking, and shared table are unchanged in all 27 exact releases. Local variable names and surrounding class contents shift source line numbers only.

## Coverage and disposition

Examined every exact stable source snapshot from 1.14.4 through 1.20.6 listed in the table, including all patches and minors available in the canonical tree. Checked readiness/provenance and source hashes; the dry Elytra travel consumer call; `Entity.getLookAngle`, its exact input fields/getters, and `calculateViewVector`; exact getter bodies for all releases using getter calls; and `Mth` table initialization plus `sin`/`cos` index expressions.

Result: no historical difference found in the look-vector arithmetic or trig-table index arithmetic in this interval. The direct-field-to-getter source transition is an exact alias with matching field-return bodies and has no numeric effect. No missing source, provenance, or method dependency remains for this bounded operation. Elytra activation, glide acceleration/gravity/effect operations, and other travel behavior remain outside this assignment.