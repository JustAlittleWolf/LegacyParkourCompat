package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.JumpVerticalVelocityHook;
import net.minecraft.world.entity.LivingEntity;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   jumpFromGround; lines 2069-2080.
 *   aiStep; lines 2656.
 *   Source SHA-256: c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2.
 * 1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   jumpFromGround; lines 2269-2281.
 *   Source SHA-256: 19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8.
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
