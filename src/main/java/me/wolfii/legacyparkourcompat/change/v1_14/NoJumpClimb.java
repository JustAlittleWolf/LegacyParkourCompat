package me.wolfii.legacyparkourcompat.change.v1_14;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.ClimbingBehavior;
import net.minecraft.world.entity.LivingEntity;

/**
 * Jump-to-climb was added in 1.14. Before that, climbing required a horizontal
 * collision; pressing jump while touching a ladder/vine did not push Y to 0.2.
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class NoJumpClimb implements ClimbingBehavior {
    @Override
    public boolean climbByJumping(LivingEntity entity, boolean jumping) {
        return false;
    }
}
