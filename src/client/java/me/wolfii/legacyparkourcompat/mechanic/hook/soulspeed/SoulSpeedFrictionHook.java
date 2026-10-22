package me.wolfii.legacyparkourcompat.mechanic.hook.soulspeed;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.LivingEntity;

/** Reproduces the Soul Speed branch in LivingEntity#getBlockSpeedFactor. */
@FunctionalInterface
@MechanicType("entity.soul_speed.friction")
public interface SoulSpeedFrictionHook extends VersionedMechanic {
    float blockSpeedFactor(LivingEntity entity, float nativeFactor);
}
