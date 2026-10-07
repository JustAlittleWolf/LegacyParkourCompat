package me.wolfii.legacyparkourcompat.change.v1_12;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.GroundSpeedBehavior;
import net.minecraft.world.entity.LivingEntity;
@MovementChange(emulates = ParkourVersion.V1_12)
public final class GroundAcceleration implements GroundSpeedBehavior {
    @Override
    public float speed(LivingEntity entity, float blockFriction, float vanilla) {
        if (!entity.onGround()) {
            return vanilla;
        }
        float friction = blockFriction * 0.91F;
        float acceleration = 0.16277136F / (friction * friction * friction);
        return entity.getSpeed() * acceleration;
    }
}
