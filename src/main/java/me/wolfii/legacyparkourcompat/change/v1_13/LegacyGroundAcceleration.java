package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;
import me.wolfii.legacyparkourcompat.mechanic.hook.GroundAccelerationBehavior;
import net.minecraft.world.entity.LivingEntity;

/** Preserve the historical float operation order of ground acceleration. */
public final class LegacyGroundAcceleration implements GroundAccelerationBehavior {
    private final float numerator;

    private LegacyGroundAcceleration(float numerator) {
        this.numerator = numerator;
    }

    public static void register(MovementChangeRegistry registry) {
        registry.register(GroundAccelerationBehavior.class, ParkourVersion.V1_13, new LegacyGroundAcceleration(0.16277137F));
        registry.register(GroundAccelerationBehavior.class, ParkourVersion.V1_12, new LegacyGroundAcceleration(0.16277136F));
    }

    @Override
    public float speed(LivingEntity entity, float blockFriction, float vanilla) {
        if (!entity.onGround()) {
            return vanilla;
        }
        float friction = blockFriction * 0.91F;
        float influence = this.numerator / (friction * friction * friction);
        return entity.getSpeed() * influence;
    }
}
