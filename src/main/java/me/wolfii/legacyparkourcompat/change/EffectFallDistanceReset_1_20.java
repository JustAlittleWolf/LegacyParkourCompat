package me.wolfii.legacyparkourcompat.change;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.EffectFallDistanceResetBehavior;
import net.minecraft.world.entity.LivingEntity;

@MovementChange(emulates = ParkourVersion.V1_20)
public final class EffectFallDistanceReset_1_20 implements EffectFallDistanceResetBehavior {
    @Override
    public boolean resetBeforeTravel(LivingEntity entity, boolean vanilla) {
        return false;
    }
}
