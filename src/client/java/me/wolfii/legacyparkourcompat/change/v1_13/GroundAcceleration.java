package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.GroundSpeedHook;
import net.minecraft.world.entity.LivingEntity;

/**
 * Ornithe Feather 1.13.2 LivingEntity#travel multiplies slipperiness by 0.91F,
 * cubes that float, divides 0.16277137F by the result, then multiplies
 * getSpeed. Mojmap 1.14.4 LivingEntity#getFrictionInfluencedSpeed instead uses
 * getSpeed() * (0.21600002F / (slipperiness^3)); unobfuscated 26.2 retains
 * that expression. The 1.13.2 and 1.14.4 terrestrial travel callers gate this
 * operation on grounded movement.
 * The independently resolved older change keeps 1.12.2's distinct
 * 0.16277136F numerator. Native air-speed selection remains untouched.
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class GroundAcceleration implements GroundSpeedHook {
    @Override
    public float speed(LivingEntity entity, float blockFriction, float vanilla) {
        if (!entity.onGround()) {
            return vanilla;
        }
        float friction = blockFriction * 0.91F;
        float acceleration = 0.16277137F / (friction * friction * friction);
        return entity.getSpeed() * acceleration;
    }
}
