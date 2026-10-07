package me.wolfii.legacyparkourcompat.change.v1_19_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.EffectFallDistanceResetBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.LevitationFallDistanceBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SlowFallingFallDistanceBehavior;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

@MovementChange(emulates = ParkourVersion.V1_19_4)
public final class EffectFallDistanceReset implements EffectFallDistanceResetBehavior, SlowFallingFallDistanceBehavior, LevitationFallDistanceBehavior {
    @Override
    public boolean resetBeforeTravel(LivingEntity entity, boolean vanilla) {
        return false;
    }

    @Override
    public void beforeTravel(LivingEntity entity) {
        if (entity.getDeltaMovement().y <= 0.0
            && entity.hasEffect(MobEffects.SLOW_FALLING)) {
            entity.resetFallDistance();
        }
    }

    @Override
    public void beforeAirTravel(LivingEntity entity) {
        if (entity.hasEffect(MobEffects.LEVITATION)) {
            entity.resetFallDistance();
        }
    }
}
