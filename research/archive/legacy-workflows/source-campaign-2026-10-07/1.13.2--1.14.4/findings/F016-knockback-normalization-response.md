# F016: Knockback response normalization

- Older version A: 1.13.2
- Newer version B: 1.14.4
- Mechanic / coverage slice IDs: direct player knockback response; S016
- Classification: changed behavior
- Confidence: candidate (A revision verified; original derived-artifact equivalence unproven; dependencies/reviewer open)
- Applicability: player receiving a direct knockback response
- First changed release: unknown within (1.13.2,1.14.4]
- Runtime validation: not performed

## Paired evidence

- A manifest: `../../../build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/`; `LivingEntity.java`::`applyKnockback(Entity,float,double,double)`, lines 1011-1027, SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`; `Vec3d.java`::`normalize`, lines 25-28, SHA-256 `6941a43536e8aefaa435a4e5f1731b0fa26a9d96503d1f4d07de8950c297f3c5`.
- A derived-artifact revision: `feather-r1-2026-10-07`; immutable snapshot `../../../build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.13.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c`; `artifact.sha256` SHA-256 `4fa7f17f639f7cff0ff9db327b3c978b049cee0c96a6b40601868692c7dd8`; `revision.json` SHA-256 `a4dfb51e36d4e52e1fc26d96647942e3dfcc2302bb4568a0a50dd49acf91b8f5`. Original source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; original artifact manifest SHA-256 `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e`. Original derived JAR SHA-256 `d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5` is unavailable; equivalence is unproven. All source and non-derived raw inputs verified identical; coordinator reports independent ops pass.
- B manifest: `../../../build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather/`; `LivingEntity.java`::`applyKnockback(Entity,float,double,double)`, lines 1208-1215, SHA-256 `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61`; `Vec3d.java`::`normalize`, lines 25-28, SHA-256 `b69b990b768e52d6fac6b3ed82f60e7526e78d0837c868a61a4171ebca21040b`.

## Source-level difference

Both versions first apply the same random knockback-resistance guard and set `velocityDirty`. A computes a float horizontal magnitude with `MathHelper.sqrt(x*x + z*z)`, halves current X/Z velocity, then subtracts each incoming component divided by that magnitude times `amount`. There is no zero-length check. B constructs `(x,0,z)`, calls `Vec3d.normalize()` (which returns ZERO when its length is below `1.0E-4`), scales by `amount`, and subtracts it from half the current horizontal velocity. For a zero or sufficiently small input vector, B's direction term is zero while A's division can produce non-finite components. A's grounded Y path halves then adds `amount` and caps above `0.4F`; B evaluates `Math.min(0.4, oldY/2 + amount)` using the double literal.

## Reachability and dependencies

This is the direct response method inherited by players. A call reaches it only if the resistance random check passes. Whether zero or near-zero horizontal vectors reach the method depends on external callers and is not established here. Attribute sources and current velocity are state inputs. Attack/damage calculation and the reason for a knockback call were not inspected, consistent with scope.

## Consequence and uncertainty

Source proves a different normalization cutoff, numeric type, and operation sequence. It establishes a possible non-finite A result for a zero vector if that input is supplied, but no caller reachability or runtime result is claimed. The grounded cap also changes from float to double literal precision. No combat/damage behavior is included.

## Handoff

Independent source candidate. Implementation handoff blocked by open caller/attribute dependencies and absent independent reviewer acceptance. First changed release unknown.
