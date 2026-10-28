package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.accessor.JumpPowerAccess;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.JumpPowerHook;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;

/**
 * Ornithe Feather 1.13.2 LivingEntity#jump first widens the default float
 * jump strength 0.42F into velocityY, then adds the float expression
 * (integer amplifier + 1) * 0.1F as a double. Mojmap 1.14.4
 * LivingEntity#jumpFromGround adds base and boost in float before storing;
 * unobfuscated 26.2 LivingEntity#jumpFromGround retains that order.
 * Preserve the integer addition and float product, then the double sum.
 * Modern custom jump strength/scale and non-unit block jump factors retain
 * their supplied native power rather than suppressing modern-only features.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class DoubleJumpBoostAddition implements JumpPowerHook {
    @Override
    public double jumpPower(LivingEntity entity, double vanilla) {
        if (entity.getAttributeValue(Attributes.JUMP_STRENGTH) != (double)0.42F
            || entity.getAttributeValue(Attributes.SCALE) != 1.0
            || ((JumpPowerAccess)entity).legacyparkour$getBlockJumpFactor() != 1.0F) {
            return vanilla;
        }
        double power = 0.42F;
        var boost = entity.getEffect(MobEffects.JUMP_BOOST);
        if (boost != null) {
            power += (boost.getAmplifier() + 1) * 0.1F;
        }
        return power;
    }
}
