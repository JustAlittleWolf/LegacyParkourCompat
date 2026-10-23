package me.wolfii.legacyparkourcompat.change.v1_12;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.GroundSpeedHook;
import net.minecraft.world.entity.LivingEntity;
/**
 * Ornithe Feather 1.12.2 LivingEntity.moveRelative's ordinary travel branch
 * multiplies block slipperiness by 0.91F, cubes that float left-to-right,
 * divides 0.16277136F by that cube, then multiplies getSpeed() while grounded.
 * Ornithe Feather 1.13 and 1.13.2 LivingEntity.moveRelative use 0.16277137F
 * in the same operation; the change therefore ends with the 1.12 profile.
 * 26.2 unobfuscated LivingEntity.getFrictionInfluencedSpeed instead divides
 * 0.21600002F by the cube of block friction. Translating just the numerator
 * would lose the historical float multiplication/division rounding, so the
 * complete scalar calculation is retained here. Air-speed selection stays
 * native. The mixin dispatch is restricted to historical client players.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class GroundAcceleration implements GroundSpeedHook {
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
