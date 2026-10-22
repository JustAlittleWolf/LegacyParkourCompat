package me.wolfii.legacyparkourcompat.change.v1_21_11;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.PostMoveLavaCurrentHook;
import net.minecraft.world.entity.Entity;

/**
 * 1.21.11 Mojmap net.minecraft.world.entity.Entity#move calls
 * LivingEntity#checkFallDamage after updating position, and
 * net.minecraft.world.entity.LivingEntity#checkFallDamage refreshes water current only.
 * 26.1.2 and 26.2 unobfuscated LivingEntity#checkFallDamage call
 * Entity#updateFluidInteraction, which applies both water and lava current.
 */
@MovementChange(emulates = ParkourVersion.V1_21_11)
public final class PostMoveLavaCurrent implements PostMoveLavaCurrentHook {
    @Override
    public boolean appliesLavaCurrent(Entity entity) {
        return false;
    }
}
