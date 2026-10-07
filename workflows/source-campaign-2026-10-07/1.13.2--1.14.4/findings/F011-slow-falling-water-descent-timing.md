# F011: Slow-falling water descent predicate timing

- Older version A: 1.13.2
- Newer version B: 1.14.4
- Mechanic / coverage slice IDs: water travel and slow falling; S008
- Classification: changed behavior
- Confidence: candidate (A revision verified; original derived-artifact equivalence unproven; dependencies/reviewer open)
- Applicability: historical player behavior
- First changed release: unknown within (1.13.2, 1.14.4]
- Runtime validation: not performed

## Paired evidence

- A manifest: `../../../build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/`; `net/minecraft/entity/living/LivingEntity.java`::`moveRelative(float,float,float)`, slow-falling input at lines 1480-1484 and water descent after movement at lines 1639-1649, SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`.
- A derived-artifact revision: `feather-r1-2026-10-07`; immutable snapshot `../../../build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.13.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c`; `artifact.sha256` SHA-256 `4fa7f17f639f7cff0ff9db327b3c978b049cee0c96a6b40625601868692c7dd8`; `revision.json` SHA-256 `a4dfb51e36d4e52e1fc26d96647942e3dfcc2302bb4568a0a50dd49acf91b8f5`. Original source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; original artifact manifest SHA-256 `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e`. Original derived JAR SHA-256 `d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5` is unavailable; equivalence is unproven. All source and non-derived raw inputs verified identical; coordinator reports independent ops pass.
- B manifest: `../../../build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather/`; `net/minecraft/entity/living/LivingEntity.java`::`moveRelative(Vec3d)`, `bl` captured from velocity at lines 1786-1793 and used in water descent at lines 1912-1921, SHA-256 `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61`.

## Source-level difference

Both use `d=0.01` when slow falling applies at the start of movement. A's special water descent condition reads current `velocityY` after movement and water damping. B stores `bl = initialVelocityY <= 0.0` before travel and later gates the special descent assignment on that stored boolean. The remaining tolerance expressions are structurally similar.

## Reachability and dependencies

Slow Falling active and water travel -> initial vertical velocity snapshot -> movement/collision may change vertical velocity -> water descent/gravity adjustment. Exact collision and effect application paths remain open.

## Consequence and uncertainty

If the player enters this branch with positive vertical velocity and movement/collision changes it to a nonpositive value before the descent check, A may take the special near-zero descent branch while B's captured `bl` remains false. This is a source-derived precondition, not a reproduced trajectory.

## Handoff

Independent state-timing delta. Related finding F004 for the vectorized travel refactor. First changed release unknown. Implementation/testing deferred.
