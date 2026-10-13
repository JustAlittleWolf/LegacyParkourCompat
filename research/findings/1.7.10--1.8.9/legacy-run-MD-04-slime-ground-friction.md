# MD-04: Slime blocks provide a distinct ground-friction value

- **1.7.10**, ornithe-feather: `1.7.10/ornithe-feather/net/minecraft/block/Blocks.java`.
- **1.8.9**, ornithe-feather: `1.8.9/ornithe-feather/net/minecraft/block/Blocks.java`; `1.8.9/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`.

## Difference

1.8.9 introduces a slime surface whose slipperiness is `0.8F`.

When the player is grounded on it, the living movement routine uses that value in both the ground-acceleration calculation and the post-move horizontal drag. 1.7.10 has no slime block registration, so this block-conditioned surface behavior does not exist in the older release.

## Movement meaning

Local player `moveRelative(sidewaysSpeed, forwardSpeed)` -> grounded support block lookup -> `slipperiness * 0.91F` -> input acceleration factor and later X/Z drag. 1.8.9's block registry resolves the `slime` entry to `SlimeBlock`; 1.7.10's `Blocks` source has no such registration.

Collision support and on-ground state select the branch.

The friction calculation is independent of the separate slime bounce and low-speed step callbacks in MD-02.

Source-proven: grounded movement on slime uses the block's `0.8F` slipperiness in acceleration and horizontal drag.

Inference: movement starting or continuing on slime can differ from the same input on other ground.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.7.10--1.8.9/findings/MD-04-slime-ground-friction.md).
