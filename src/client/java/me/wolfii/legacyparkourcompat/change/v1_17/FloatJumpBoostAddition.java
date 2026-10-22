package me.wolfii.legacyparkourcompat.change.v1_17;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.accessor.JumpPowerAccess;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.JumpPowerHook;
import net.minecraft.world.entity.ai.attributes.Attributes;

/**
 * Keeps the 1.17 jump-boost total at binary32 precision.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   getJumpStrength.
 * 1.14.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   getJumpStrength.
 * 1.16.5/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   getJumpPower.
 * 1.17.1/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   getJumpPower.
 * No exact independently accepted source binding is established for this operation.
 * Remaining source/applicability gates:
 *   1.13.2 original Feather mapped-client JAR remains unavailable; revised source equality does not prove original derived-JAR equivalence.
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
