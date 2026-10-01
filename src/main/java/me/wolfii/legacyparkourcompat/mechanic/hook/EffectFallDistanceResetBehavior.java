package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.LivingEntity;

/** Historical placement of Slow Falling and Levitation fall-distance resets. */
@MechanicType("player.effect_fall_distance_reset")
public interface EffectFallDistanceResetBehavior extends VersionedMechanic {
    boolean resetBeforeTravel(LivingEntity entity, boolean vanilla);

    default void beforeTravel(LivingEntity entity) {
    }

    default void beforeAirTravel(LivingEntity entity) {
    }
}
