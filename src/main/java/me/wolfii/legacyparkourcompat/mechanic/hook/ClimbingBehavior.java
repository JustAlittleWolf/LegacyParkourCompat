package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

/**
 * Historical climbing (ladder/vine detection, jump-to-climb, climb speed).
 */
@MechanicType("player.climb")
public interface ClimbingBehavior extends VersionedMechanic {
    default boolean onClimbable(LivingEntity entity, boolean vanilla) {
        return vanilla;
    }

    default Vec3 handleOnClimbable(LivingEntity entity, Vec3 delta, VanillaFn<Vec3> vanilla) {
        return vanilla.get();
    }

    /**
     * 1.14 lets jump start a climb ({@code horizontalCollision || jumping}).
     * Earlier versions only climbed on {@code horizontalCollision}.
     */
    default boolean climbByJumping(LivingEntity entity, boolean jumping) {
        return jumping;
    }
}
