package me.wolfii.legacyparkourcompat.change.v1_19_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.EffectFallDistanceResetHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.LevitationFallDistanceHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.SlowFallingFallDistanceHook;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

/**
 * Restores 1.19.4 effect-driven fall-distance reset timing. In 1.19.4 Mojmap
 * LivingEntity.travel resets distance only in the downward slow-falling branch
 * and the ordinary non-fluid levitation branch. Mojmap 1.20 LivingEntity.aiStep
 * moves both resets before travel; 1.20.6 and 26.2 retain that timing. Keep
 * this emulation to the locally simulated entity, matching the old controlled-
 * instance travel gate. This concerns fall-distance state only; custom GRAVITY
 * behavior and fall-damage calculation remain native.
 *
 * Sources (paths relative to decompiled_minecraft/):
 * 1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java: travel.
 * 1.20/mojmap/net/minecraft/world/entity/LivingEntity.java: aiStep.
 * 1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java: aiStep.
 * 26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java: aiStep, travelInAir.
 */
@MovementChange(emulates = ParkourVersion.V1_19_4)
public final class EffectFallDistanceReset implements EffectFallDistanceResetHook, SlowFallingFallDistanceHook, LevitationFallDistanceHook {
    @Override
    public boolean resetBeforeTravel(LivingEntity entity, boolean vanilla) {
        return !isLocallySimulated(entity) && vanilla;
    }

    @Override
    public void beforeTravel(LivingEntity entity) {
        if (isLocallySimulated(entity)
            && entity.getDeltaMovement().y <= 0.0
            && entity.hasEffect(MobEffects.SLOW_FALLING)) {
            entity.resetFallDistance();
        }
    }

    @Override
    public void beforeAirTravel(LivingEntity entity) {
        if (isLocallySimulated(entity) && entity.hasEffect(MobEffects.LEVITATION)) {
            entity.resetFallDistance();
        }
    }

    private static boolean isLocallySimulated(LivingEntity entity) {
        return entity.canSimulateMovement() && entity.isEffectiveAi();
    }
}
