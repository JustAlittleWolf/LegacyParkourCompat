# F003: Jump Boost vertical impulse precision

- Older version A: 1.13.2
- Newer version B: 1.14.4
- Mechanic / coverage slice IDs: jump impulse; S003
- Classification: changed behavior
- Confidence: candidate (A artifact-integrity repair pending before acceptance)
- Applicability: historical player behavior
- First changed release: unknown within (1.13.2, 1.14.4]
- Runtime validation: not performed

## Paired evidence

- A manifest: `../../../build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/`; `LivingEntity.java`::`jump()`, lines 1447-1464, SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`.
- B manifest: `../../../build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather/`; same member, lines 1752-1772, SHA-256 `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61`.

## Source-level difference

With Jump Boost, A first assigns float `getJumpStrength()` into double `velocityY`, then adds `(amplifier+1)*0.1F` to that double. B adds the base and boost as float into local `f`, then stores that float in the vertical velocity. B therefore rounds the sum to float before storage; A performs the addition at double precision after base assignment. Sprint horizontal impulse uses the same apparent formula and ordering in the inspected bodies.

## Reachability and dependencies

LivingEntity jump is reached from living tick jump handling; PlayerEntity overrides call the superclass on both sides. Effect registration and amplifier production were not traced fully, so source finding is conditional on the Jump Boost effect being active.

## Consequence and uncertainty

The source establishes a precision/order difference. The resulting vertical velocity differs only when float rounding of the sum is distinguishable from A's double addition. No measured jump trajectory is claimed.

## Handoff

Independent delta; first changed release unknown. Implementation/testing deferred.
