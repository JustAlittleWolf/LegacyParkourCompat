# 1.13.2 suppresses the vertical-gaze glide correction

- Older version A: 1.12.2
- Newer version B: 1.13.2
- Mechanic / coverage slice IDs: 3.6 (fall-flying glide pitch correction); 7.x pitch/input reachability
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player movement with Elytra fall-flying
- First changed release: unknown within (1.12.2, 1.13.2]
- Runtime validation: not performed

## Paired evidence

- A look vector: `decompiled_minecraft/1.12.2/ornithe-feather/net/minecraft/entity/Entity.java`, `getRotationVector(float,float)`, lines 1239-1244; SHA-256 `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`. For pitch `-90.0F`, yaw `0.0F`, the old expression applies separate `-PI` / negated-pitch offsets before sine-table lookup. Paired `MathHelper.sin/cos(float)` table lookups are at A lines 15-20; MathHelper SHA-256 `579e05d110460f444bcac86729141545095cb5932c0f8d34c9a4dcfcd9a35e80`.
- B look vector: `decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `getRotationVector(float,float)`, lines 1251-1257; SHA-256 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`. It computes radians from pitch and negated yaw without the old `-PI` offsets. Same-source sine-table lookup methods are at B `MathHelper.java:24-29`; MathHelper SHA-256 `2211b0cb3da7d8789a4a4df96509b95e6b1be8b222be2f530e7bd0695573bd69`.
- Evaluating those Java float expressions at yaw `0.0F`, pitch `-90.0F` gives A look vector approximately `(-1.50e-32, 1.0, 1.22464685e-16)` and B `(0.0, 1.0, 0.0)`. Thus the old horizontal look length is positive and tiny; the B horizontal length is exactly zero.
- A glide body: `decompiled_minecraft/1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `moveRelative(FFF)V`, lines 1508-1513; SHA-256 `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`. Its dive correction runs whenever pitch radians `i < 0.0F`, then divides horizontal look components by the horizontal length.
- B glide body: corresponding member, lines 1508-1513; SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`. It adds `k > 0.0` to the dive-correction guard.
- Pitch reachability: Entity camera pitch update clamps to `[-90.0F, 90.0F]` (A `Entity.java:307-313`, B `Entity.java:318-324`; hashes above), so the exact `-90.0F` endpoint is accepted. Both versions contain the fall-flying movement branch; Elytra is present in both endpoints.

## Source-level difference

Under fall-flying at yaw `0.0F`, pitch `-90.0F`, and nonzero horizontal speed, A's nonzero residual horizontal look vector satisfies its pitch-only dive guard and applies its dive lift and horizontal correction. B's look vector has zero horizontal length and the added `k > 0.0` guard skips those terms. The B condition also prevents division by a zero horizontal length. This endpoint comparison does not identify when either change first appeared.

## Reachability and dependencies

`LivingEntity.mobTick()` dispatches virtual `moveRelative()` on the player; `LivingEntity.moveRelative()` selects glide integration when `isFallFlying()` is true. The local player's accepted camera pitch reaches `Entity.getRotationVector()`. Fall-flying activation and equipment eligibility are not needed for the numeric method difference beyond the state precondition; server acceptance and Elytra application conditions remain external context.

## Consequence and uncertainty

Source and the sine-table expressions prove the different horizontal look length at the stated angles and the A/B branch decision. A applies a pitch dive term while B skips it, changing vertical and horizontal velocity before later drag and collision. No trajectory was measured. The normal-pitch glide gravity expression was also regrouped in B and remains a separate queued arithmetic candidate pending exact accumulated-velocity analysis.

## Handoff

Vertical-gaze Elytra glide correction. Related to the wider look-vector formula change and glide math, but independently scoped to the negative-pitch correction guard.
