package me.wolfii.legacyparkourcompat.change.v1_17;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.JumpPowerHook;

/**
 * Keeps the 1.17 jump-boost total at binary32 precision.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   getJumpStrength; lines [[1447, 1464]].
 *   Source SHA-256: bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c.
 * 1.14.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   getJumpStrength; lines [[1752, 1772]].
 *   Source SHA-256: 2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61.
 * 1.16.5/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   getJumpPower; lines [[1878, 1896]].
 *   Source SHA-256: b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88.
 * 1.17.1/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   getJumpPower; lines [[1967, 1985]].
 *   Source SHA-256: 33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f.
 * No exact independently accepted source binding is established for this operation.
 * Remaining source/applicability gates:
 *   1.13.2 original Feather mapped-client JAR d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5 remains unavailable; revised source equality does not prove original derived-JAR equivalence.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_17)
public final class FloatJumpBoostAddition implements JumpPowerHook {
    @Override
    public double jumpPower(net.minecraft.world.entity.LivingEntity entity, double vanilla) {
        float power = 0.42F * ((me.wolfii.legacyparkourcompat.accessor.JumpPowerAccess) entity).legacyparkour$getBlockJumpFactor();
        power += entity.getJumpBoostPower();
        return power;
    }
}
