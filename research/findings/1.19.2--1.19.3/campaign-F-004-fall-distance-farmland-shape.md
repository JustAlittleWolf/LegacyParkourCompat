# F-004 — Fall-flying fall distance changes farmland trample input

- **1.19.2**, mojmap: `net/minecraft/world/entity/LivingEntity.java`; lines 2107-2111.
- **1.19.3**, mojmap: `Entity.java`; lines 2117-2119.

## Difference

For the same fall-flying branch predicate, 1.19.2 unconditionally overwrites `fallDistance` with `1.0F`; 1.19.3 overwrites it only when the prior value exceeds `1.0F`.

Therefore, when the pre-branch value is below `1.0F`, 1.19.2 supplies `1.0F` to the landing callback path and 1.19.3 preserves the smaller value.

## Movement meaning

`LivingEntity.travel` -> player `Entity.move` -> `Entity.checkFallDamage` -> landing block `fallOn` -> `FarmBlock.fallOn` -> server `setBlockAndUpdate(farmland -> dirt)` -> replicated block state -> later player collision shape query.

The movement response hook `updateEntityAfterFallOn` is a separate call and does not consume `fallDistance`.

FarmBlock's relevant code and shapes are identical across 1.19.2/1.19.3; only the upstream travel value differs.

Source-proven: the 1.19.2 and 1.19.3 values can produce different farmland-trample predicates.

If the server-side random check succeeds, farmland is replaced with dirt; farmland's shape is 15/16 block height while dirt inherits the full-block shape.

Predicted only: after the world update reaches a client and the player later collides with that location, the collision surface can differ.

The server RNG, block update, and delivery are external inputs; no actual world update or trajectory is claimed.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.19.2--1.19.3/findings/F-004-fall-distance-farmland-shape.md).
