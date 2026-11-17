# Powder-snow wall-jump check switches to previous-tick contact state

1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/Entity.java`, getInBlockState 3360-3365; baseTick 451-452.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/Entity.java`, baseTick 459-460.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, handleRelativeFrictionAndCalculateMovement 2362-2372.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/LivingEntity.java`, handleRelativeFrictionAndCalculateMovement 2394-2404.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/level/block/PowderSnowBlock.java`, entityInside 60-82.

Difference: For horizontal-collision or jump cases where the powder-snow walk condition matters, 1.21.4 queries the block state returned by `getInBlockState()` during travel. 1.21.5 instead checks `wasInPowderSnow`, a field copied from `isInPowderSnow` at the entity tick boundary. 1.21.5 therefore uses the previous contact flag rather than the same-tick block-state predicate; the results can diverge around entering or leaving powder snow. The `0.2` upward velocity assignment and `canEntityWalkOnPowderSnow` condition are unchanged.

Movement meaning: Player `LivingEntity.travelInAir` reaches `handleRelativeFrictionAndCalculateMovement` before `Entity.move`, then evaluates this predicate when horizontal collision or jumping is true. Powder-snow contact updates the current flag through `entityInside` (1.21.4 directly; 1.21.5 through the inside-effect collector), and `Entity.tick` rolls that flag into `wasInPowderSnow` at the next tick boundary. `canEntityWalkOnPowderSnow`/equipment conditions remain a modifier dependency; F-01 covers the separate callback-application path. The source proves the predicate reads different state: a lazily accessed block state in 1.21.4 and the prior tick's contact flag in 1.21.5. This can change whether the `0.2` vertical velocity is applied when a collision or jump coincides with a powder-snow entry/exit boundary.

## Air-travel order and walkability

`LivingEntity.handleRelativeFrictionAndCalculateMovement` evaluates the boost after `move` and the resulting velocity read. When `horizontalCollision || jumping` is true, an independently climbable state admits the boost. Otherwise the Powder Snow branch tests the current block in 1.21.4 or the prior-tick contact flag in 1.21.5, then checks `PowderSnowBlock.canEntityWalkOnPowderSnow`. Vanilla's walkable-entity tag excludes players, so an ordinary player needs leather boots for the Powder Snow branch. The `onClimbable()` alternative has no boot requirement.

A carried contact can therefore admit the 1.21.5 boost when the post-move feet cell is air. The returned vector's Y is set to `0.2` before air travel applies gravity and friction. The adjacent Mojmap sources establish the exact boundary. The listed continuity sources retain their respective pre- and post-boundary predicates. The review does not establish a full tick trajectory or audit the generic block-intersection scanner. [Exact finding](../../source-evidence/20261117/block-a-powder-climb/powder-snow-climb-boost.md), [source comparison](../../source-evidence/20261117/block-a-powder-climb/comparison.md), [independent verdict](../../source-evidence/20261117/block-a-powder-climb/verdict.md).
