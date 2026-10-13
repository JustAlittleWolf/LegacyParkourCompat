# MD-01: Depth Strider changes water movement

- **1.7.10**, ornithe-feather: `1.7.10/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`; lines 1177-1188.
- **1.8.9**, ornithe-feather: `1.8.9/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`; lines 1199-1225.

## Difference

For a living player in water, 1.8.9 consults the highest Depth Strider level among equipped items and changes the horizontal input scale and post-move X/Z drag.

At level zero 1.8.9 retains `g = 0.02F` and `f = 0.8F`; positive levels change those values, with airborne players using half the level before interpolation. 1.7.10 has no such registration, query or consumer.

## Movement meaning

`LocalClientPlayerEntity.tick()` -> player/living tick -> `LivingEntity.mobTick()` -> `moveRelative(sidewaysSpeed, forwardSpeed)` -> water movement branch -> `EnchantmentHelper.getDepthStriderLevel(this)` -> `getEquipment()` aggregation. 1.8.9 registers the enchantment for feet armor and caps its level at three.

The source proves the client movement consumer and its conditions.

The exact equipment value is an entity input; packet synchronization and application remain queued in the run manifest.

Source-proven: a positive equipped level changes water input acceleration and horizontal drag, with a half-level factor while airborne.

Inference: with equal input and environment, this can change water displacement and retained horizontal velocity.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.7.10--1.8.9/findings/MD-01-depth-strider-water-movement.md).
