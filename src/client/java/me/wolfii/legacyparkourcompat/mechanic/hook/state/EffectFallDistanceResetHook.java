package me.wolfii.legacyparkourcompat.mechanic.hook.state;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.LivingEntity;

/** Whether the effect-related fall-distance reset runs before travel in aiStep. */
@FunctionalInterface
@MechanicType("player.effect_fall_distance_reset")
public interface EffectFallDistanceResetHook extends VersionedMechanic {
    boolean resetBeforeTravel(LivingEntity entity, boolean vanilla);
}
