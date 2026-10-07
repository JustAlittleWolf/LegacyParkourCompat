# F010: Water-travel climb impulse

- Older version A: 1.13.2
- Newer version B: 1.14.4
- Mechanic / coverage slice IDs: water travel; S007
- Classification: changed behavior
- Confidence: candidate (A revision verified; original derived-artifact equivalence unproven; dependencies/reviewer open)
- Applicability: historical player behavior
- First changed release: unknown within (1.13.2, 1.14.4]
- Runtime validation: not performed

## Paired evidence

- A manifest: `../../../build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/`; `net/minecraft/entity/living/LivingEntity.java`::`moveRelative(float,float,float)`, water branch lines 1617-1655, SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`.
- A derived-artifact revision: `feather-r1-2026-10-07`; immutable snapshot `../../../build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.13.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c`; `artifact.sha256` SHA-256 `4fa7f17f639f7cff0ff9db327b3c978b049cee0c96a6b40625601868692c7dd8`; `revision.json` SHA-256 `a4dfb51e36d4e52e1fc26d96647942e3dfcc2302bb4568a0a50dd49acf91b8f5`. Original source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; original artifact manifest SHA-256 `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e`. Original derived JAR SHA-256 `d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5` is unavailable; equivalence is unproven. All source and non-derived raw inputs verified identical; coordinator reports independent ops pass.
- B manifest: `../../../build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather/`; `net/minecraft/entity/living/LivingEntity.java`::`moveRelative(Vec3d)`, water branch lines 1882-1928, especially 1904-1911, SHA-256 `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61`.

## Source-level difference

In A's water branch, movement is followed by velocity damping and descent handling; there is no `isClimbing()` impulse in this branch. B, after `move`, checks `collidingHorizontally && isClimbing()` and replaces the vertical component with `0.2` before applying water drag/gravity.

## Reachability and dependencies

LivingEntity water travel -> Entity.move collision result -> `collidingHorizontally` and `isClimbing` -> vertical velocity. Whether the player is simultaneously considered in water and climbing depends on fluid contact and registered climbable block state; those providers remain open.

## Consequence and uncertainty

Source proves B can assign upward velocity in the water branch under both guards. It does not establish which exact block/fluid configurations satisfy them or the resulting trajectory. Those are open dependencies in INV-WORLD-MOVEMENT/INV-COLLISION.

## Handoff

Independent delta; related finding F005 (ordinary travel climb guard). Applicability requires water travel, horizontal collision and climbing state. First changed release unknown. Implementation/testing deferred.
