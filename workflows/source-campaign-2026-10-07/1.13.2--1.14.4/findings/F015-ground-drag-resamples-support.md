# F015: Ground drag resamples support after movement

- Older version A: 1.13.2
- Newer version B: 1.14.4
- Mechanic / coverage slice IDs: ordinary grounded travel damping; S014
- Classification: changed behavior
- Confidence: candidate (A revision verified; original derived-artifact equivalence unproven; dependencies/reviewer open)
- Applicability: historical player behavior
- First changed release: unknown within (1.13.2,1.14.4]
- Runtime validation: not performed

## Paired evidence

- A manifest: `../../../build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/`; `LivingEntity.java`::`moveRelative(float,float,float)`, lines 1538-1558, SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`.
- A derived-artifact revision: `feather-r1-2026-10-07`; immutable snapshot `../../../build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.13.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c`; `artifact.sha256` SHA-256 `4fa7f17f639f7cff0ff9db327b3c978b049cee0c96a6b40601868692c7dd8`; `revision.json` SHA-256 `a4dfb51e36d4e52e1fc26d96647942e3dfcc2302bb4568a0a50dd49acf91b8f5`. Original source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; original artifact manifest SHA-256 `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e`. Original derived JAR SHA-256 `d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5` is unavailable; equivalence is unproven. All source and non-derived raw inputs verified identical; coordinator reports independent ops pass.
- B manifest: `../../../build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather/`; `LivingEntity.java`::`moveRelative(Vec3d)`, lines 1840-1866, SHA-256 `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61`.

## Source-level difference

In A's ordinary non-fluid, non-fall-flying branch, the block below the player's shape is sampled before movement to compute the ground acceleration. After `move`, A resets the drag coefficient from `0.91F`, rechecks `onGround`, and, if still grounded, reads slipperiness at the player's new position before multiplying horizontal velocity by the resulting value. B captures `onGround ? slipperiness * 0.91F : 0.91F` before movement in `w`, then uses that same `w` after movement. B therefore does not resample support state or the block below the resulting position for this drag step.

## Reachability and dependencies

The difference is reachable in ordinary land travel when the player crosses a support boundary or moves onto a block with a different slipperiness during the move. It can also differ if collision/movement changes `onGround` between branch entry and the post-move drag. The exact encountered block values and collision path depend on world state and remain open in `INV-WORLD-MOVEMENT` and `INV-COLLISION`.

## Consequence and uncertainty

Source proves that A's post-move horizontal damping may use a different coefficient from B's pre-move coefficient. No specific block transition, numeric delta, or trajectory is claimed. F006 separately records the acceleration-formula arithmetic; this finding covers coefficient sampling time and location.

## Handoff

Independent source candidate related to F006. Implementation handoff blocked by open world/collision dependencies and absent independent reviewer acceptance. First changed release unknown.
