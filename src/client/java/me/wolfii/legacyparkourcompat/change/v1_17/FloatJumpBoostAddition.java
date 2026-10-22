package me.wolfii.legacyparkourcompat.change.v1_17;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.accessor.JumpPowerAccess;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.JumpPowerHook;
import net.minecraft.world.entity.ai.attributes.Attributes;

/**
 * Keeps the 1.17 jump-boost total at binary32 precision.
 *
 * Historical source evidence (paths are relative to decompiled_minecraft/):
 * 1.16.5/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   getJumpPower, jumpFromGround.
 * 1.17/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   getJumpPower, jumpFromGround.
 * 1.17.1/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   getJumpPower, getJumpBoostPower, jumpFromGround.
 * Native modern-input evidence:
 * 26.2/unobfuscated/net/minecraft/world/entity/ai/attributes/Attributes.java:
 *   JUMP_STRENGTH (default 0.42F), SCALE (default 1.0).
 * 26.2/unobfuscated/net/minecraft/world/entity/ai/attributes/RangedAttribute.java:
 *   <init> (default value parameter is double; the 0.42F argument widens exactly).
 * 26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java:
 *   getJumpPower, getJumpPower(float), getJumpBoostPower.
 * The attribute guards keep this historical reconstruction from replacing
 * custom modern jump strength or scale; the accepted finding establishes only
 * the bounded float-addition source difference recorded there.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_17)
public final class FloatJumpBoostAddition implements JumpPowerHook {
    @Override
    public double jumpPower(net.minecraft.world.entity.LivingEntity entity, double vanilla) {
        // Preserve modern custom jump strength and scale; these are not inputs
        // in the historical operation this change restores.
        if (entity.getAttributeValue(Attributes.JUMP_STRENGTH) != (double) 0.42F
            || entity.getAttributeValue(Attributes.SCALE) != 1.0) {
            return vanilla;
        }
        float power = 0.42F * ((JumpPowerAccess) entity).legacyparkour$getBlockJumpFactor();
        power += entity.getJumpBoostPower();
        return power;
    }
}
