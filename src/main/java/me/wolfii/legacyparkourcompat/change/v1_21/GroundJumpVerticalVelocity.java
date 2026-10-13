package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.JumpVerticalVelocityHook;
import net.minecraft.world.entity.LivingEntity;

@MovementChange(emulates = ParkourVersion.V1_21)
public final class GroundJumpVerticalVelocity implements JumpVerticalVelocityHook {

    @Override
    public double jumpVerticalVelocity(
        LivingEntity entity,
        double jumpPower,
        double currentVerticalVelocity,
        double vanilla
    ) {
        return jumpPower;
    }
}
