package me.wolfii.legacyparkourcompat.change.v1_19_4;

import me.wolfii.legacyparkourcompat.accessor.JumpPowerAccess;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.JumpBehavior;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;


/** Widens the two float operands before adding, as in 1.17.1. */
@MovementChange(emulates = ParkourVersion.V1_19_4)
public final class DoubleJumpBoostAddition implements JumpBehavior {
    @Override
    public double jumpPower(LivingEntity entity, double vanilla) {
        float jumpPower = 0.42F
            * ((JumpPowerAccess) entity).legacyparkour$getBlockJumpFactor();
        float jumpBoostPower = entity.getJumpBoostPower();
        return (double) jumpPower + (double) jumpBoostPower;
    }
}
