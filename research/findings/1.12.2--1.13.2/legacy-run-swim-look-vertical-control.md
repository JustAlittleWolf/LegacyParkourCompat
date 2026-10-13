# 1.13.2 adds look-directed vertical control while swimming

- **1.12.2**, ornithe-feather: `1.12.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`; lines 1385-1404.
- **1.13.2**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/movement-discovery/runs/1.12.2--1.13.2/findings/swim-look-vertical-control.md); lines 1441-1468.

## Difference

1.13.2 changes vertical velocity toward the look vector’s vertical component during swimming, using a pitch-dependent factor and an additional branch gate when looking upward. 1.12.2 has no corresponding player override behavior.

The change is independent of the separate `input.sneaking` water impulse in `sneak-water-descent.md` and the water-height jump dispatch in `water-jump-height.md`.

## Movement meaning

This code is reached during the living movement tick after jump handling and before base relative movement.

Its state inputs are 1.13.2’s swimming flag, riding state, look vector, jump state, and fluid occupancy just above the player.

Those flags are produced by the water/sprint path; fluid state and collision effects remain in stages 4-5.

Player movement attributes affect the delegated horizontal movement but are not consumed by this vertical adjustment itself.

Source proves a conditional vertical-velocity update in 1.13.2 and no corresponding update in 1.12.2’s method.

It predicts pitch-dependent vertical movement while swimming, subject to the branch gate and later travel/collision processing.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.12.2--1.13.2/findings/swim-look-vertical-control.md).
