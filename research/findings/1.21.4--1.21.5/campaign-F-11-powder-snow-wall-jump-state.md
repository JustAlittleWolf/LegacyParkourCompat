# F-11: Powder-snow wall-jump check switches to previous-tick contact state

- **1.21.4**, mojmap: `LivingEntity#handleRelativeFrictionAndCalculateMovement()`; lines 2362-2372.
- **1.21.5**, mojmap: `LivingEntity#handleRelativeFrictionAndCalculateMovement()`; lines 2394-2404.

## Difference

For horizontal-collision or jump cases where the powder-snow walk condition matters, 1.21.4 queries the block state returned by `getInBlockState()` during travel. 1.21.5 instead checks `wasInPowderSnow`, a field copied from `isInPowderSnow` at the entity tick boundary. 1.21.5 therefore uses the previous contact flag rather than the same-tick block-state predicate; the results can diverge around entering or leaving powder snow.

The `0.2` upward velocity assignment and `canEntityWalkOnPowderSnow` condition are unchanged.

## Movement meaning

Player `LivingEntity.travelInAir` reaches `handleRelativeFrictionAndCalculateMovement` before `Entity.move`, then evaluates this predicate when horizontal collision or jumping is true.

Powder-snow contact updates the current flag through `entityInside` (1.21.4 directly; 1.21.5 through the inside-effect collector), and `Entity.tick` rolls that flag into `wasInPowderSnow` at the next tick boundary. `canEntityWalkOnPowderSnow`/equipment conditions remain a modifier dependency; F-01 covers the separate callback-application path.

The source proves the predicate reads different state: a lazily accessed block state in 1.21.4 and the prior tick's contact flag in 1.21.5.

This can change whether the `0.2` vertical velocity is applied when a collision or jump coincides with a powder-snow entry/exit boundary.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.4--1.21.5/findings/F-11-powder-snow-wall-jump-state.md).
