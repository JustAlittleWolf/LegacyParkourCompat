# 1.13.2 changes the on-ground acceleration coefficient by one float step

- **1.12.2**, ornithe-feather: `1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`; line 1485.
- **1.13.2**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/movement-discovery/runs/1.12.2--1.13.2/findings/ground-accel-float.md); line 1545.

## Difference

The input acceleration coefficient uses adjacent binary32 literals.

For the default `0.6F` slipperiness and `0.91F` factor, the computed coefficient differs by one binary32 step before it scales movement input.

Other slipperiness values should be resolved from their exact block source in stage 5; no general claim is made that every denominator preserves a distinct quotient.

## Movement meaning

The branch runs for local movement outside water, lava and fall-flying; when `onGround`, it uses the block beneath the player to calculate friction.

The player speed attribute and input values supply the remaining multiplier inputs.

Block-specific slipperiness and any version differences in those values remain in stage 5.

Source and the exact default-block float calculation prove different acceleration coefficients under the stated precondition.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.12.2--1.13.2/findings/ground-accel-float.md).
