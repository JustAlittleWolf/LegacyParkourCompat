# F-FRICTION-SAMPLE: Ground travel samples a different support block

- **1.14.4**, mojmap: `net/minecraft/world/entity/LivingEntity.java`; lines 1837-1842.
- **1.15.2**, mojmap: `net/minecraft/world/entity/LivingEntity.java`; lines 1886-1891.

## Difference

At six snow layers, collision height is `(6 - 1) * 2/16 = 0.625` above the snow cell.

With the player's feet at that collision top, 1.14.4's `minY - 1.0` floors into the supporting Slime Block; 1.15.2's `minY - 0.5000001` floors into the Snow Layer Block.

Snow uses default friction 0.6, while Slime uses 0.8.

Thus the sampled friction and the input to ground acceleration differ on this reachable arrangement.

Snow's `canSurvive` rejects Ice, so Ice is not a valid example.

## Movement meaning

The player ordinary travel branch samples friction when not in the fall-flying branch, then passes it to `getFrictionInfluencedSpeed` and `moveRelative`.

Six-layer Snow has a collision surface and both versions' `SnowLayerBlock.canSurvive` permits a full-face Slime support.

The registry values and the shape calculation above establish the concrete support path.

The source proves differing friction inputs to travel.

Resulting acceleration and velocity depend on the movement input and current state; no measured trajectory is claimed.

More boundary configurations and related support lookups remain unaudited.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.14.4--1.15.2/findings/F-FRICTION-SAMPLE.md).
