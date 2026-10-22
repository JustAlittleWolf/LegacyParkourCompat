package me.wolfii.legacyparkourcompat.mechanic.hook.soulspeed;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.LivingEntity;

/** Support-block predicate used by the historical Soul Speed friction operation. */
@FunctionalInterface
@MechanicType("entity.soul_speed.support_block")
public interface SoulSpeedBlockHook extends VersionedMechanic {
    boolean onSoulSpeedBlock(LivingEntity entity);
}
