# WORLD-05: boat passenger skips player water collision update

- **1.8.9**, ornithe-feather: `1.8.9/ornithe-feather/net/minecraft/entity/Entity.java`; lines 1486-1527.
- **1.9.4**, ornithe-feather: `1.9.4/ornithe-feather/net/minecraft/entity/Entity.java`.

## Difference

1.8.9 always calls `World.applyLiquidDrag()` for the contracted entity box with `Material.WATER`.

If water is detected, it can reset `fallDistance`, set `inWater`, and clear `onFireTimer`; the World call can also add fluid-flow velocity. 1.9.4 first checks `this.getMount() instanceof BoatEntity`; in that case it sets `inWater` false and skips the entire water query and its state/velocity writes.

The check executes in the inherited `Entity.tick()` on both endpoints. 1.8.9 `World.applyLiquidDrag()` source is `World.java` lines 1486-1527 1.9.4 is lines 1560-1602

## Movement meaning

The local player tick reaches inherited `Entity.tick()` through the paired player/living superclass chain; `Entity.tick()` calls `checkWaterCollisions()` at 1.8.9 line 300 and 1.9.4 line 361.

Under the concrete precondition that the player is mounted on a `BoatEntity` and its contracted box intersects water, 1.8.9 performs the water query and 1.9.4 skips it. 1.8.9's `World.applyLiquidDrag()` calls `Block.applyMaterialDrag()` for water cells; the paired `LiquidBlock` override contributes `getFlow()` to the accumulated vector. `PlayerEntity.hasLiquidCollision()` returns `!this.abilities.flying` in both versions (1.8.9 `PlayerEntity.java` lines 1617-1620; 1.9.4 lines 1717-1720), so for a non-flying player the world fluid-flow path may write the player's X/Y/Z velocity.

Boat movement itself remains out of scope.

Source proves that 1.9.4 suppresses the player's water-state query, fall-distance reset, fire-timer reset and fluid-flow callback while mounted on a boat; 1.8.9 does not.

It also proves that a non-flying player qualifies for liquid-flow velocity application when 1.8.9's world scan produces a nonzero flow vector.

The source does not establish a subsequent mounted-player velocity consumer or a world trajectory.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-05-water-state-while-riding-boat.md).
