# F011: Slow-falling water descent predicate timing

- Older version A: 1.13.2
- Newer version B: 1.14.4
- Mechanic / coverage slice IDs: water travel and slow falling; S008
- Classification: changed behavior
- Confidence: candidate (A artifact-integrity repair pending before acceptance)
- Applicability: historical player behavior
- First changed release: unknown within (1.13.2, 1.14.4]
- Runtime validation: not performed

## Paired evidence

- A manifest: `../../../build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/`; `net/minecraft/entity/living/LivingEntity.java`::`moveRelative(float,float,float)`, slow-falling input at lines 1480-1484 and water descent after movement at lines 1639-1649, SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`.
- B manifest: `../../../build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather/`; `net/minecraft/entity/living/LivingEntity.java`::`moveRelative(Vec3d)`, `bl` captured from velocity at lines 1786-1793 and used in water descent at lines 1912-1921, SHA-256 `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61`.

## Source-level difference

Both use `d=0.01` when slow falling applies at the start of movement. A's special water descent condition reads current `velocityY` after movement and water damping. B stores `bl = initialVelocityY <= 0.0` before travel and later gates the special descent assignment on that stored boolean. The remaining tolerance expressions are structurally similar.

## Reachability and dependencies

Slow Falling active and water travel -> initial vertical velocity snapshot -> movement/collision may change vertical velocity -> water descent/gravity adjustment. Exact collision and effect application paths remain open.

## Consequence and uncertainty

If the player enters this branch with positive vertical velocity and movement/collision changes it to a nonpositive value before the descent check, A may take the special near-zero descent branch while B's captured `bl` remains false. This is a source-derived precondition, not a reproduced trajectory.

## Handoff

Independent state-timing delta. Related finding F004 for the vectorized travel refactor. First changed release unknown. Implementation/testing deferred.
