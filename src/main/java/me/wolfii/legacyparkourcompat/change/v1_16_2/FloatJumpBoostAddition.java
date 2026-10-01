package me.wolfii.legacyparkourcompat.change.v1_16_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.JumpBehavior;
import net.minecraft.world.entity.LivingEntity;

/** Keeps the 1.16.2–1.16.5 jump-boost total at binary32 precision. */
@MovementChange(emulates = ParkourVersion.V1_16_2)
public final class FloatJumpBoostAddition implements JumpBehavior {
    @Override
    public double jumpPower(LivingEntity entity, double vanilla) {
        return (float) vanilla;
    }
}
