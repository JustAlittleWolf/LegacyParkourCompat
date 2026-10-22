package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.SlowFallingWaterDescentHook;

/**
 * 1.13.2 (Ornithe Feather), LivingEntity.moveRelative(float, float, float),
 * checks the post-drag vertical velocity for Slow Falling's terminal-descent
 * rule. In 1.14 and 1.14.4 (Ornithe Feather), LivingEntity.moveRelative(Vec3d)
 * captures the entry predicate and uses it after movement and water drag;
 * Entity.move(MoverType, Vec3d) invokes the colliding block's
 * beforeCollision, where SlimeBlock reverses descending vertical velocity.
 * Modern 26.2 (unobfuscated), LivingEntity.travelInWater(Vec3, double, boolean,
 * double) passes its entry predicate and post-drag movement to
 * getFluidFallingAdjustedMovement(double, boolean, Vec3). Slow Falling was
 * introduced in 1.13, so earlier selected profiles retain the native predicate.
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class SlowFallingWaterDescent implements SlowFallingWaterDescentHook {
    public SlowFallingWaterDescent() {
    }

    @Override
    public boolean isFallingAtWaterDescentCheck(
        ParkourVersion selectedVersion,
        boolean entryPredicate,
        double verticalVelocityAfterDrag
    ) {
        if (selectedVersion.olderThan(ParkourVersion.V1_13)) {
            return entryPredicate;
        }
        return verticalVelocityAfterDrag <= 0.0;
    }
}
