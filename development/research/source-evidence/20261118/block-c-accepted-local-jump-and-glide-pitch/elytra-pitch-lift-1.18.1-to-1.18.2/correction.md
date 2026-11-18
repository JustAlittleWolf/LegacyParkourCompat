# Correction to 1.18.1 → 1.18.2 Elytra glide source report

This correction supplements, and does not modify, the immutable source report whose SHA-256 is `4e5094d335e3f6082cba82ca2a7d9987d48662390a97f4368699b8a6cf56b3ea`. It binds the independent audit SHA-256 `615a282617c3aea6258e1f013eddfd5090d1e0106183c7dfbd01ea404f9bab5d`.

## Corrections

1. **Affected math:** In `LivingEntity.travel(Vec3)`, the changed `$$19` factor is consumed by the glide gravity expression at 1.18.1 `LivingEntity.java:2083` / 1.18.2 `:2087`, and the downward-velocity correction at 1.18.1 `:2084–2087` / 1.18.2 `:2088–2091`. The separate pitch-up term for negative pitch is `$$21 = $$17 * -Mth.sin($$15) * 0.04` at 1.18.1 `:2090–2092` / 1.18.2 `:2094–2096`; it does **not** use `$$19`. The original report's phrase saying the changed factor feeds the negative-pitch pitch-up contribution is incorrect. Its later statement that this `Mth.sin` term remains unchanged is correct.

2. **Witness denominator:** For yaw 0° and pitch +45°, `Entity.calculateViewVector` (`Entity.java:1311–1319` in 1.18.1; `:1314–1322` in 1.18.2) builds equal-magnitude vertical and forward components from the Mth float table. Their length is approximately `0.999999982885729`, not exactly 1. The ratio to `0.4` is approximately `2.4999999572`, so the cap `Math.min(1.0, length / 0.4)` is exactly 1.0 on both source paths. The original report's witness phrase “length 1” should be read as an approximation; its denominator conclusion is unaffected.

3. **Witness damping:** The final Y multiplier is `0.98F` (`LivingEntity.java:2098` in 1.18.1; `:2102` in 1.18.2), a float converted to double by `Vec3.multiply(double, double, double)`. It is `0.9800000190734863`, not the double literal `0.98`. Reapplying the equations in source order with this exact float multiplier gives unobstructed move-Y values of approximately `-0.1396500048208237` (1.18.1) and `-0.1396500042601097` (1.18.2), difference `5.607140030061686E-10` blocks. Inputs remain the original lawful witness: active dry glide, empty unobstructed space, yaw 0°, pitch +45°, velocity `(0, -0.1, 0.3)`, gravity `0.08`, and no modifiers.

## Exact source trace

- 1.18.1 source: `decompiled_minecraft/1.18.1/mojmap/net/minecraft/world/entity/LivingEntity.java`, SHA-256 `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7`.
- 1.18.2 source: `decompiled_minecraft/1.18.2/mojmap/net/minecraft/world/entity/LivingEntity.java`, SHA-256 `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782`.
- Both exact-release Mojmap source trees and manifest/provenance identities are recorded in the bound original report. The source excerpts show the changed cosine factor, the unchanged independent `Mth.sin` pull-up, and `multiply(0.99F, 0.98F, 0.99F)` before the real `move(MoverType.SELF, ...)` call.

Signed: source investigator `/root/c_glide_pitch_cos_1181_1182_source`.