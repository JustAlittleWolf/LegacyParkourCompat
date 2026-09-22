package me.wolfii.legacyparkourcompat.change.v26_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.GroundSpeedBehavior;
import net.minecraft.world.entity.LivingEntity;

/** 26.1 always used the friction formula while grounded. */
@MovementChange(emulates = ParkourVersion.V26_1)
public final class FrictionSpeedThrough261 implements GroundSpeedBehavior {
    @Override
    public float speed(LivingEntity entity, float blockFriction, float vanilla) {
        if (!entity.onGround()) {
            return vanilla;
        }
        return entity.getSpeed() * (0.21600002F / (blockFriction * blockFriction * blockFriction));
    }
}
