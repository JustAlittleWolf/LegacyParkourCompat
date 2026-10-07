# F004: Input acceleration cutoff and vector arithmetic

- Older version A: 1.13.2
- Newer version B: 1.14.4
- Mechanic / coverage slice IDs: movement input acceleration; S004
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.13.2, 1.14.4]
- Runtime validation: not performed

## Paired evidence

- A manifest: `../../../build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/`; `Entity.java`::`updateVelocity(float,float,float,float)`, lines 1057-1075, SHA-256 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`.
- B manifest: `../../../build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather/`; `Entity.java`::`updateVelocity(float,Vec3d)` and `m_51093791`, lines 1062-1077, SHA-256 `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55`.

## Source-level difference

A computes squared input magnitude in float, returns below `1.0E-4F`, square-roots and normalizes using float operations, then adds rotated components to double velocity. B computes Vec3d squared magnitude in double, returns below `1.0E-7`, conditionally normalizes in double and scales before yaw rotation and vector addition. Threshold, numeric type and operation sequence differ.

## Reachability and dependencies

LivingEntity travel calls the helper for ordinary movement and water/lava paths; player overrides may customize `moveRelative`. Full caller inventory and direct input ranges remain open. The output feeds velocity consumed by Entity.move.

## Consequence and uncertainty

Source proves changed arithmetic and cutoff. It does not establish which reachable movement inputs produce a different trajectory at a specific tick; that requires separate runtime validation, which is not part of this source report.

## Handoff

Independent delta; first changed release unknown. Implementation/testing deferred.
