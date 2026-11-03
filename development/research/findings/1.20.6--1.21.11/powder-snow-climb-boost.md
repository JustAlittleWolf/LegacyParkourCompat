# Powder-snow climb boost switches to prior-tick occupancy in 1.21.5

1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/world/entity/Entity.java`, `getInBlockState` 3169-3175.
1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java`, `handleRelativeFrictionAndCalculateMovement` 2268-2282.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/Entity.java`, tick occupancy rollover 451-452; `setPosRaw` 3440-3448.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, `handleRelativeFrictionAndCalculateMovement` 2362-2372.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/level/block/PowderSnowBlock.java`, `canEntityWalkOnPowderSnow` 123-129.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/Entity.java`, tick occupancy rollover 459-460.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/LivingEntity.java`, `handleRelativeFrictionAndCalculateMovement` 2394-2402.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/level/block/PowderSnowBlock.java`, `canEntityWalkOnPowderSnow` 137-143.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/world/entity/Entity.java`, `getInBlockState` 3627-3633.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java`, `handleRelativeFrictionAndCalculateMovement` 2533-2544.

Difference: The boost uses the current block state in 1.20.6 and 1.21.4, then uses `wasInPowderSnow` in 1.21.5 and 1.21.11. The 1.21.4-to-1.21.5 pair establishes the exact observed cutoff: the tick-start rollover copies `isInPowderSnow` to `wasInPowderSnow` and clears the current flag, while the post-move boost gate changes from `getInBlockState().is(Blocks.POWDER_SNOW)` to `wasInPowderSnow`. `setPosRaw` updates the floored block position and clears the cached in-block state when that cell changes. The powder-snow walkability predicate is unchanged: leather boots on a living entity pass it.

Movement meaning: A bounded entry witness is a player wearing leather boots, not on a climbable, with the jump gate true and prior-tick powder-snow occupancy false, whose movement ends with its feet position in a powder-snow block cell. At the post-move gate, 1.21.4 resolves the new feet cell as powder snow and selects vertical velocity `0.2`; 1.21.5's powder-snow branch does not pass because its prior-occupancy flag is false. This is a gate-input witness, not a claim about a complete ordinary tick trajectory or its later position and velocity. The 1.20.6 and 1.21.11 endpoints show the same respective gate forms outside the exact 1.21.4-to-1.21.5 cutoff.
