# 1.13.2 reduces ordinary water-travel gravity

- **1.12.2**, ornithe-feather: `1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`; lines 1555-1584.
- **1.13.2**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/movement-discovery/runs/1.12.2--1.13.2/findings/water-gravity.md); lines 1617-1655.

## Difference

For a non-sprinting player in water with gravity enabled and no Slow Falling effect, 1.12.2 subtracts `0.02` after multiplying vertical velocity by `0.8F`; 1.13.2 subtracts `0.005` after the same damping. 1.13.2 also applies a narrow near-zero adjustment before that subtraction.

The resulting water vertical integration differs even when the new Slow Falling effect is absent.

## Movement meaning

This is the water case of the `LivingEntity.moveRelative()` dispatcher.

Water contact is maintained by the entity fluid-state path; the local player invokes the same inherited movement route.

Sprinting skips 1.13.2's water gravity update and is separately documented in `water-sprint-travel.md`.

Fluid flow, depth strider, player swimming pitch input, collision and server-provided state remain dependencies in stages 4-6.

Source proves 1.13.2's ordinary downward adjustment is one quarter of 1.12.2's before the narrow clamp.

Slow Falling changes `d` and should be classified separately as a new effect; this finding excludes it from the stated comparison.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.12.2--1.13.2/findings/water-gravity.md).
