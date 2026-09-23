package me.wolfii.legacyparkourcompat.change.v1_15;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.v1_14.FloatJumpThrough116;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mixin.access.EntityInvoker;
import net.minecraft.world.entity.LivingEntity;

/** 1.15–1.17.0 multiply 0.42F by the supporting block's jump factor before float Jump Boost. */
@MovementChange(emulates = ParkourVersion.V1_17)
public final class BlockFactorFloatJump extends FloatJumpThrough116 {
    @Override
    protected float basePower(LivingEntity entity) {
        return 0.42F * ((EntityInvoker) entity).lpc$getBlockJumpFactor();
    }
}
