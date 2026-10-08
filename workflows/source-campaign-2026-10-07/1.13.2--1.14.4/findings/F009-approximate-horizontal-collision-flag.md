# F009: Approximate horizontal collision flag

- Older version A: 1.13.2
- Newer version B: 1.14.4
- Mechanic / coverage slice IDs: entity movement collision flags; S006
- Classification: changed behavior
- Confidence: candidate (A revision verified; original derived-artifact equivalence unproven; dependencies/reviewer open)
- Applicability: historical player behavior
- First changed release: unknown within (1.13.2, 1.14.4]
- Runtime validation: not performed

## Paired evidence

- A manifest: `../../../build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/`; `Entity.java`::`move(MoverType,double,double,double)`, lines 674-680, SHA-256 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`.
- A derived-artifact revision: `feather-r1-2026-10-07`; immutable snapshot `../../../build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.13.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c`; `artifact.sha256` SHA-256 `4fa7f17f639f7cff0ff9db327b3c978b049cee0c96a6b40625601868692c7dd8`; `revision.json` SHA-256 `a4dfb51e36d4e52e1fc26d96647942e3dfcc2302bb4568a0a50dd49acf91b8f5`. Original source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; original artifact manifest SHA-256 `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e`. Original derived JAR SHA-256 `d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5` is unavailable; equivalence is unproven. All source and non-derived raw inputs verified identical; coordinator reports independent ops pass.
- B manifest: `../../../build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather/`; `Entity.java`::`move(MoverType,Vec3d)`, lines 471-475 and `MathHelper.m_68886716`, lines 155-157, `MathHelper.java` SHA-256 `e39d5dfc69c17a9032be086d7d4d14cc63525962a0ff83615b1befdcca599843`; Entity SHA-256 `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55`.

## Source-level difference

A sets horizontal collision when requested and resolved X or Z differ exactly. B uses `!MathHelper.m_68886716(requested,resolved)` for each horizontal component; the helper treats values within its tolerance (`1.0E-5`) as equal. B still uses exact component inequality in the later velocity-cancellation checks.

## Reachability and dependencies

Entity.move stores the flag after the collision solver; LivingEntity travel and player logic consume collision flags in later movement decisions. Callers and downstream flag readers have not been fully enumerated.

## Consequence and uncertainty

For a horizontal component changed by a small amount within the helper tolerance, B may leave `collidingHorizontally` false where A sets it true, while exact velocity cancellation may still occur. Exact helper expression and consumer consequences should be independently audited; no movement result is tested.

## Handoff

Independent flag delta; first changed release unknown. Implementation/testing deferred.
