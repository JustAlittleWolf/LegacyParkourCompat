package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.EffectFallDistanceResetHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.LevitationFallDistanceHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.SlowFallingFallDistanceHook;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

/**
 * Ornithe Feather 1.9 and 1.12.2 LivingEntity#moveRelative change vertical
 * velocity for Levitation after the ordinary move without resetting fall distance.
 * Ornithe Feather 1.13 adds that reset and registers Slow Falling in StatusEffects.
 * Keep the 26.2 unobfuscated LivingEntity#aiStep reset for modern-only Slow Falling;
 * the supplemental travel hooks add no resets for this range.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class EffectFallDistanceReset
    implements EffectFallDistanceResetHook, SlowFallingFallDistanceHook, LevitationFallDistanceHook {
    @Override
    public boolean resetBeforeTravel(LivingEntity entity, boolean vanilla) {
        return vanilla && (!entity.canSimulateMovement() || !entity.isEffectiveAi()
            || entity.hasEffect(MobEffects.SLOW_FALLING));
    }

    @Override
    public void beforeTravel(LivingEntity entity) {
        // Slow Falling retains its native pre-travel reset.
    }

    @Override
    public void beforeAirTravel(LivingEntity entity) {
        // The historical Levitation movement branch does not reset fall distance.
    }
}
