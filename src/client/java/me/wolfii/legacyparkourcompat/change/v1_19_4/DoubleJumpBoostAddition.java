package me.wolfii.legacyparkourcompat.change.v1_19_4;

import me.wolfii.legacyparkourcompat.accessor.JumpPowerAccess;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.JumpPowerHook;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;

/**
 * Widens the two float operands before adding, as in 1.17.1.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   getJumpPower.
 *   aiStep.
 *   getJumpPower.
 * 1.19.4/mojmap/net/minecraft/world/entity/player/Player.java:
 *   jumpFromGround.
 * 1.20.1/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   getJumpPower.
 *   aiStep.
 * 1.20.1/mojmap/net/minecraft/world/entity/player/Player.java:
 *   jumpFromGround.
 * 1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   getJumpPower.
 *   createLivingAttributes.
 * 1.20.6/mojmap/net/minecraft/world/entity/ai/attributes/Attributes.java:
 *   class declaration / field initialization.
 * 1.20.6/mojmap/net/minecraft/world/entity/player/Player.java:
 *   createAttributes.
 * No exact independently accepted source binding is established for this operation.
 * Remaining source/applicability gates:
 *   Archived-to-canonical source identity for these checked secondary citations: 1.20.6/mojmap/net/minecraft/world/entity/player/Player.java. Archived source identity correspondence remains unresolved for them.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_19_4)
public final class DoubleJumpBoostAddition implements JumpPowerHook {
    @Override
    public double jumpPower(LivingEntity entity, double vanilla) {
        // The reconstructed historical operands are valid only with the native
        // jump strength and scale defaults; keep modern custom attributes native.
        if (entity.getAttributeValue(Attributes.JUMP_STRENGTH) != (double) 0.42F
            || entity.getAttributeValue(Attributes.SCALE) != 1.0) {
            return vanilla;
        }
        float jumpPower = 0.42F
            * ((JumpPowerAccess) entity).legacyparkour$getBlockJumpFactor();
        float jumpBoostPower = entity.getJumpBoostPower();
        return (double) jumpPower + (double) jumpBoostPower;
    }
}
