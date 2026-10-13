# boat passenger skips player water collision update

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/entity/Entity.java`, `public boolean checkWaterCollisions() {`, lines 746-760.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/entity/Entity.java`, `public boolean checkWaterCollisions() {`, lines 849-865.

Difference: 1.8.9 always calls `World.applyLiquidDrag()` for the contracted entity box with `Material.WATER`.

If water is detected, it can reset `fallDistance`, set `inWater`, and clear `onFireTimer`; the World call can also add fluid-flow velocity. 1.9.4 first checks `this.getMount() instanceof BoatEntity`; in that case it sets `inWater` false and skips the entire water query and its state/velocity writes.

The check executes in the inherited `Entity.tick()` on both endpoints. 1.8.9 `World.applyLiquidDrag()` source is `World.java` lines 1486-1527 1.9.4 is lines 1560-1602

Movement meaning: The local player tick reaches inherited `Entity.tick()` through the paired player/living superclass chain; `Entity.tick()` calls `checkWaterCollisions()` at 1.8.9 line 300 and 1.9.4 line 361.

Under the concrete precondition that the player is mounted on a `BoatEntity` and its contracted box intersects water, 1.8.9 performs the water query and 1.9.4 skips it. 1.8.9's `World.applyLiquidDrag()` calls `Block.applyMaterialDrag()` for water cells; the paired `LiquidBlock` override contributes `getFlow()` to the accumulated vector.
