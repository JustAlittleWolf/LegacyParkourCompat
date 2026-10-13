# F-MOVE-TINY-POSITION-WRITE — tiny free movement updates position only in 1.21.3

- **1.21.1**, mojmap: `net/minecraft/world/entity/Entity.java`; lines 598-649.
- **1.21.3**, mojmap: `net/minecraft/world/entity/Entity.java`; lines 619-652.

## Difference

For a nonzero movement result with squared length at or below `1.0E-7`, 1.21.1 does not write the entity's new position.

If the post-edge-backoff request is collision-free, 1.21.3's expression has a zero difference between requested and actual squared lengths, so its second condition is true and it writes the position even when the actual squared length is at or below `1.0E-7`.

More generally, 1.21.3 also writes when the difference `requested.lengthSqr() - actual.lengthSqr()` is below `1.0E-7`, including some small collision-shortened movements.

The literal threshold and operation order above are preserved.

## Movement meaning

Both ordinary local-player tick paths reach `LivingEntity.travel` and then `Entity.move`; the requested vector is passed through stuck-speed handling, edge backoff and `collide` before the compared condition. `setPos` writes the position and recomputes the bounding box on both versions.

The concrete sufficient precondition is a nonzero, unobstructed post-backoff movement whose squared length is at most `1.0E-7`: 1.21.1 skips `setPos`, 1.21.3 calls it.

No external input, attribute, effect, equipment, resource or block-provider dependency is needed to establish this bounded branch difference.

Source-proven: under the sufficient precondition, 1.21.3 writes the small actual displacement and rebuilds the bounding box; 1.21.1 does not perform that write.

This can change the stored position and subsequent position-based queries.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.1--1.21.3/findings/F-MOVE-TINY-POSITION-WRITE.md).
