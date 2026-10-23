package me.wolfii.legacyparkourcompat.change.v1_19_4;

import me.wolfii.legacyparkourcompat.accessor.JumpPowerAccess;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.JumpPowerHook;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;

/**
 * Reproduces the 1.19.4 default jump-power addition: the base product is a
 * float, then the float boost is promoted and added as a double. Mojmap 1.20
 * changes this operation to float addition; the changed arithmetic remains in
 * 1.20.6 and 26.2. Custom JUMP_STRENGTH and SCALE values stay native. The
 * sprint impulse is independently resolved by SprintJumpImpulseHook.
 *
 * Sources (paths relative to decompiled_minecraft/):
 * 1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java: getJumpPower,
 * getJumpBoostPower, jumpFromGround.
 * 1.20/mojmap/net/minecraft/world/entity/LivingEntity.java: getJumpPower,
 * jumpFromGround.
 * 1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java: getJumpPower,
 * jumpFromGround; net/minecraft/world/entity/ai/attributes/Attributes.java:
 * JUMP_STRENGTH.
 * 26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java: getJumpPower,
 * getJumpPower(float), getJumpBoostPower, jumpFromGround; ai attributes
 * Attributes.JUMP_STRENGTH and SCALE.
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
