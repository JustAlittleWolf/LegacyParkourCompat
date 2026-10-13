# Powder snow writes the entity stuck-movement multiplier

- **1.16.5**, mojmap: `PowderSnowBlock.java`; lines 1991–1994.
- **1.17.1**, mojmap: `world/level/block/PowderSnowBlock.java`; lines 54–57.

## Difference

1.17.1 adds a Powder Snow callback that writes the existing stuck multiplier.

For a player, the callback condition is met when its feet block is Powder Snow.

On the next movement consumption, the requested X/Z displacement is multiplied by `0.9F` and Y by `1.5`; the same branch clears the stored multiplier and delta movement.

The generic multiplier operation itself is present in 1.16.5, but 1.16.5 has no Powder Snow block capable of supplying this multiplier.

## Movement meaning

The player is a `LivingEntity`, so it takes the callback's feet-block condition.

Entity tick dispatch visits `entityInside` for intersected blocks; the shared `Entity.move` consumer applies the stored value to movement.

The block is registered only in 1.17.1.

This callback effect is separate from the Powder Snow climb-assist predicate documented in [S3-powder-snow-climb.md](../../archive/legacy-workflows/movement-discovery/runs/1.16.5--1.17.1/findings/S3-powder-snow-climb.md).

The source proves the callback arguments, fall-distance reset, multiplier storage, and movement-consumption order.

It does not establish a full trajectory.

Powder Snow did not exist in 1.16.5, so this is modern-only and does not imply historical behavior for 1.16.5-era maps.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.16.5--1.17.1/findings/S3-powder-snow-stuck-motion.md).
