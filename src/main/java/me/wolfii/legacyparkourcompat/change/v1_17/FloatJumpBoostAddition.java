package me.wolfii.legacyparkourcompat.change.v1_17;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.JumpBehavior;


/** Keeps the 1.17 jump-boost total at binary32 precision. */
@MovementChange(emulates = ParkourVersion.V1_17)
public final class FloatJumpBoostAddition implements JumpBehavior {
    @Override
    public double jumpPower(net.minecraft.world.entity.LivingEntity entity, double vanilla) {
        float power = 0.42F * ((me.wolfii.legacyparkourcompat.accessor.JumpPowerAccess) entity).legacyparkour$getBlockJumpFactor();
        power += entity.getJumpBoostPower();
        return power;
    }
}
