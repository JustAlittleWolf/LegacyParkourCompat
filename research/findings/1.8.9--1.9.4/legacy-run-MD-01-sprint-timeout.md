# MD-01: Local sprint timeout is removed

- **1.8.9**, ornithe-feather: `1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`; lines 349-352.
- **1.9.4**, ornithe-feather: `1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`; lines 406-409.

## Difference

When a local player starts sprinting and continues to meet the existing forward-input, collision, and hunger/flying gates, 1.8.9 forcibly clears sprint after 600 local `mobTick` calls. 1.9.4 has no elapsed-time stop; the same sustained sprint can remain active past 600 ticks.

The 1.9.4 timer increment is not itself a duration cap, and the source-wide reference check found no consumer for it outside the local-player class.

## Movement meaning

The camera's `LocalClientPlayerEntity.mobTick()` samples input and runs the sprint gates.

Both versions route `setSprinting` to `LivingEntity.setSprinting`, which updates the sprint flag and movement-speed modifier.

Ground movement reads that attribute through `PlayerEntity.getSpeed()` and `LivingEntity.moveRelative`.

The modifier and consumer are otherwise identical in the paired methods.

Source proves that the 1.8.9 sprint flag and its speed modifier are removed at the 600-tick timeout, while 1.9.4 retains them until another stop predicate runs.

The predicted consequence is a longer interval of sprint-speed movement under sustained eligible input.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.8.9--1.9.4/findings/MD-01-sprint-timeout.md).
