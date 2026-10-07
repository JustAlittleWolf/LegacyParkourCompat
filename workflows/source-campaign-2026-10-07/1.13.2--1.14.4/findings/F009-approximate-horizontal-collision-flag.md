# F009: Approximate horizontal collision flag

- Older version A: 1.13.2
- Newer version B: 1.14.4
- Mechanic / coverage slice IDs: entity movement collision flags; S006
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.13.2, 1.14.4]
- Runtime validation: not performed

## Paired evidence

- A manifest: `../../../build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/`; `Entity.java`::`move(MoverType,double,double,double)`, lines 674-680, SHA-256 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`.
- B manifest: `../../../build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather/`; `Entity.java`::`move(MoverType,Vec3d)`, lines 471-475 and `MathHelper.m_68886716`, lines 155-157, SHA-256 `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55`.

## Source-level difference

A sets horizontal collision when requested and resolved X or Z differ exactly. B uses `!MathHelper.m_68886716(requested,resolved)` for each horizontal component; the helper treats values within its tolerance (`1.0E-5`) as equal. B still uses exact component inequality in the later velocity-cancellation checks.

## Reachability and dependencies

Entity.move stores the flag after the collision solver; LivingEntity travel and player logic consume collision flags in later movement decisions. Callers and downstream flag readers have not been fully enumerated.

## Consequence and uncertainty

For a horizontal component changed by a small amount within the helper tolerance, B may leave `collidingHorizontally` false where A sets it true, while exact velocity cancellation may still occur. Exact helper expression and consumer consequences should be independently audited; no movement result is tested.

## Handoff

Independent flag delta; first changed release unknown. Implementation/testing deferred.
