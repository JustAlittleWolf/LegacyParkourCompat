package me.wolfii.legacyparkourcompat.change.v1_8_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.EffectFallDistanceResetHook;
import net.minecraft.world.entity.LivingEntity;

/**
 * Ornithe Feather 1.8.9 StatusEffects has neither Levitation nor Slow Falling.
 * Restore the 26.2 unobfuscated LivingEntity#aiStep pre-travel reset for both
 * modern-only effects; the resolved supplemental travel providers already do nothing.
 */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class NativeEffectFallDistanceReset implements EffectFallDistanceResetHook {
    @Override
    public boolean resetBeforeTravel(LivingEntity entity, boolean vanilla) {
        return vanilla;
    }
}
