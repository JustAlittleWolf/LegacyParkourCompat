package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.JumpVerticalVelocityHook;
import net.minecraft.world.entity.LivingEntity;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   jumpFromGround.
 *   aiStep.
 * 1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   jumpFromGround.
 * No exact independently accepted source binding is established for this operation.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
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
