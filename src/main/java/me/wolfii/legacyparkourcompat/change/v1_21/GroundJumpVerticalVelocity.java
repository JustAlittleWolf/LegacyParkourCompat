package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.JumpBehavior;
import net.minecraft.world.entity.LivingEntity;

@MovementChange(emulates = ParkourVersion.V1_21)
public final class GroundJumpVerticalVelocity implements JumpBehavior {
    @Override
    public void jumpFromGround(LivingEntity entity, VanillaCall vanilla) {
        vanilla.run();
    }

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
