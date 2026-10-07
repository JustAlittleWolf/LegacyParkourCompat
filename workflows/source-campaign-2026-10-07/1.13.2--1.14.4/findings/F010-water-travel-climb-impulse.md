# F010: Water-travel climb impulse

- Older version A: 1.13.2
- Newer version B: 1.14.4
- Mechanic / coverage slice IDs: water travel; S007
- Classification: changed behavior
- Confidence: candidate (A artifact-integrity repair pending before acceptance)
- Applicability: historical player behavior
- First changed release: unknown within (1.13.2, 1.14.4]
- Runtime validation: not performed

## Paired evidence

- A manifest: `../../../build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/`; `net/minecraft/entity/living/LivingEntity.java`::`moveRelative(float,float,float)`, water branch lines 1617-1655, SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`.
- B manifest: `../../../build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather/`; `net/minecraft/entity/living/LivingEntity.java`::`moveRelative(Vec3d)`, water branch lines 1882-1928, especially 1904-1911, SHA-256 `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61`.

## Source-level difference

In A's water branch, movement is followed by velocity damping and descent handling; there is no `isClimbing()` impulse in this branch. B, after `move`, checks `collidingHorizontally && isClimbing()` and replaces the vertical component with `0.2` before applying water drag/gravity.

## Reachability and dependencies

LivingEntity water travel -> Entity.move collision result -> `collidingHorizontally` and `isClimbing` -> vertical velocity. Whether the player is simultaneously considered in water and climbing depends on fluid contact and registered climbable block state; those providers remain open.

## Consequence and uncertainty

Source proves B can assign upward velocity in the water branch under both guards. It does not establish which exact block/fluid configurations satisfy them or the resulting trajectory. Those are open dependencies in INV-WORLD-MOVEMENT/INV-COLLISION.

## Handoff

Independent delta; related finding F005 (ordinary travel climb guard). Applicability requires water travel, horizontal collision and climbing state. First changed release unknown. Implementation/testing deferred.
