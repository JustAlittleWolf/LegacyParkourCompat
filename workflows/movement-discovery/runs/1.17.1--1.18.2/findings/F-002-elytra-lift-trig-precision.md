# F-002: Elytra lift coefficient uses double cosine instead of the float lookup table

- Older version A: 1.17.1
- Newer version B: 1.18.2
- Mechanic / coverage slice IDs: stage-3/fall-flying-lift-coefficient
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.17.1, 1.18.2]
- Runtime validation: not performed

## Paired evidence

- A: `decompiled_minecraft/1.17.1/mojmap/net/minecraft/world/entity/LivingEntity.java`, `LivingEntity.travel(Vec3)`, lines 2077–2081 (source SHA-256 `33FD081AADB2B6FDC9EBF487DB6DA5B38C54F4B8676572790EE2203690D15E6F`): `float n = Mth.cos(j); n = (float)(n * (n * Math.min(1.0, m / 0.4)));` This value is used in the glide vertical adjustment at line 2082 and the dive pull at line 2084.
- B: `decompiled_minecraft/1.18.2/mojmap/net/minecraft/world/entity/LivingEntity.java`, `LivingEntity.travel(Vec3)`, lines 2085–2086 (source SHA-256 `DB4168D531CAF18F22E3FEFD073365E776DA4075CE01452BB9F7671D9B458782`): `double $$19 = Math.cos($$15); $$19 = $$19 * $$19 * Math.min(1.0, $$18 / 0.4);` Its callers use this coefficient in the same lift/pull expressions immediately afterward.
- A/B helper evidence: `net/minecraft/util/Mth.java`, `Mth.cos(float)`, line 47 on each side; A SHA-256 `24515C4549E01E985017227DCCF7159166A675B232BE9135D5F896022A9CB113`, B SHA-256 `32747C5B09FC184BAAE356E39A0088FD66C9F08F69D9E98B67C19E1DE6F1BB2E`. The helper returns a `float` from the sine lookup table indexed by `(int)(angle * 10430.378F + 16384.0F) & 65535`; B's travel branch instead calls `java.lang.Math.cos(double)` and retains a `double` through the squared coefficient.

## Source-level difference

When an entity is fall-flying, A obtains the pitch cosine from the float lookup table, then rounds the derived coefficient back to float before using it. B computes the pitch cosine using `Math.cos` in double precision and keeps the squared coefficient as double. Thus the source uses different trigonometric approximations, precision and rounding points in the coefficient that controls gliding vertical acceleration and dive pull. Other inputs and adjacent formulas are outside this finding.

## Reachability and dependencies

`LivingEntity.travel()` enters the branch when `isFallFlying()` is true. Local player elytra startup in `LocalPlayer.aiStep()` sets up fall flying, so the branch is player-reachable. The outputs update delta movement; later movement resolution is separate. Source proves changed numeric procedure; actual trajectory difference was not tested.

## Consequence and uncertainty

The coefficient can differ because the implementations have different approximations and precision. This predicts a potentially different vertical/horizontal velocity update under fall-flying, but no particular numeric sample or trajectory is asserted. Endpoint evidence does not locate the exact introducing release.

## Handoff

Independent delta: fall-flying lift coefficient changed from float lookup-table cosine and float rounding to double `Math.cos` arithmetic. Preserve operation types and order in any future implementation. No gameplay validation performed.
