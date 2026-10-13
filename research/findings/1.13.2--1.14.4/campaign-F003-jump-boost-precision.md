# F003: Jump Boost vertical impulse precision

- **1.13.2**, ornithe-feather: `LivingEntity.java`; lines 1447-1464.
- **1.14.4**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.13.2--1.14.4/findings/F003-jump-boost-precision.md); lines 1752-1772.

## Difference

With Jump Boost, 1.13.2 first assigns float `getJumpStrength()` into double `velocityY`, then adds `(amplifier+1)*0.1F` to that double. 1.14.4 adds the base and boost as float into local `f`, then stores that float in the vertical velocity. 1.14.4 therefore rounds the sum to float before storage; 1.13.2 performs the addition at double precision after base assignment.

Sprint horizontal impulse uses the same apparent formula and ordering in the inspected bodies.

## Movement meaning

LivingEntity jump is reached from living tick jump handling; PlayerEntity overrides call the superclass on both sides.

Effect registration and amplifier production were not traced fully, so source finding is conditional on the Jump Boost effect being active.

The source establishes a precision/order difference.

The resulting vertical velocity differs only when float rounding of the sum is distinguishable from 1.13.2's double addition.

No measured jump trajectory is claimed.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.13.2--1.14.4/findings/F003-jump-boost-precision.md).
