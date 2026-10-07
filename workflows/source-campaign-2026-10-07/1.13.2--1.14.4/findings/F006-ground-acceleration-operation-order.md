# F006: Ground acceleration operation order

- Older version A: 1.13.2
- Newer version B: 1.14.4
- Mechanic / coverage slice IDs: ground acceleration; S005
- Classification: changed behavior
- Confidence: candidate (A revision verified; original derived-artifact equivalence unproven; dependencies/reviewer open)
- Applicability: historical player behavior
- First changed release: unknown within (1.13.2, 1.14.4]
- Runtime validation: not performed

## Paired evidence

- A manifest: `../../../build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/`; `LivingEntity.java`::`moveRelative(float,float,float)`, lines 1537-1553, SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`.
- A derived-artifact revision: `feather-r1-2026-10-07`; immutable snapshot `../../../build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.13.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c`; `artifact.sha256` SHA-256 `4fa7f17f639f7cff0ff9db327b3c978b049cee0c96a6b40625601868692c7dd8`; `revision.json` SHA-256 `a4dfb51e36d4e52e1fc26d96647942e3dfcc2302bb4568a0a50dd49acf91b8f5`. Original source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; original artifact manifest SHA-256 `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e`. Original derived JAR SHA-256 `d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5` is unavailable; equivalence is unproven. All source and non-derived raw inputs verified identical; coordinator reports independent ops pass.
- B manifest: `../../../build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather/`; `LivingEntity.java`::`m_70235197(float)` and caller in `moveRelative(Vec3d)`, lines 1841-1845 and 1961-1962, SHA-256 `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61`.

## Source-level difference

A computes `t = slipperiness * 0.91F`, then `u = 0.16277137F / (t * t * t)`, then multiplies `getSpeed() * u`. B's helper returns `getSpeed() * (0.21600002F / (slipperiness * slipperiness * slipperiness))` when grounded. The algebraic forms are close, but constants and float operation order differ.

## Reachability and dependencies

Ordinary ground/air travel samples the block below the entity shape for slipperiness and uses onGround to select the helper result. Block slipperiness assignments/registrations and helper call coverage remain open in INV-WORLD-MOVEMENT and INV-TICK.

## Consequence and uncertainty

Source proves different float constants and intermediate rounding. A per-input numeric difference is plausible but not established by an executed numeric comparison; no trajectory claim is made.

## Handoff

Independent arithmetic delta; first changed release unknown. Implementation/testing deferred.
