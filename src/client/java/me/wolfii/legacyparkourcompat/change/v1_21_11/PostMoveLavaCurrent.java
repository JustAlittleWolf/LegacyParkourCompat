package me.wolfii.legacyparkourcompat.change.v1_21_11;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.PostMoveLavaCurrentHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.PostMoveLavaDepthHook;
import net.minecraft.world.entity.Entity;

/**
 * 1.21.11 Mojmap net.minecraft.world.entity.Entity#move calls
 * LivingEntity#checkFallDamage after updating position, and
 * LivingEntity#checkFallDamage calls Entity#updateInWaterStateAndDoWaterCurrentPushing,
 * preserving the cached lava depth and applying only water current. Mojmap 1.16
 * through 1.21.11 use this water-only call with a tagged depth cache.
 * 26.1 and 26.2 unobfuscated LivingEntity#checkFallDamage call
 * Entity#updateFluidInteraction, which refreshes both fluids and applies both currents.
 * LivingEntity#travelInLava tests lava depth after the move to select drag.
 */
@MovementChange(emulates = ParkourVersion.V1_21_11)
public final class PostMoveLavaCurrent implements PostMoveLavaCurrentHook, PostMoveLavaDepthHook {
    @Override
    public boolean appliesLavaCurrent(Entity entity) {
        return false;
    }

    @Override
    public boolean retainsLavaDepth() {
        return true;
    }
}
