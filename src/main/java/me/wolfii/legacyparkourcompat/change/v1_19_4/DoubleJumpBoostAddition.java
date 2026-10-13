package me.wolfii.legacyparkourcompat.change.v1_19_4;

import me.wolfii.legacyparkourcompat.accessor.JumpPowerAccess;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.JumpPowerHook;
import net.minecraft.world.entity.LivingEntity;

/**
 * Widens the two float operands before adding, as in 1.17.1.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   getJumpPower; lines 1947-1953.
 *   aiStep; lines 2512-2537.
 *   getJumpPower; lines 1947-1967.
 *   Source SHA-256: c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165.
 * 1.19.4/mojmap/net/minecraft/world/entity/player/Player.java:
 *   jumpFromGround; lines 1429-1437.
 *   Source SHA-256: 5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2.
 * 1.20.1/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   getJumpPower; lines 1969-1975.
 *   aiStep; lines 2512-2537.
 *   Source SHA-256: decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f.
 * 1.20.1/mojmap/net/minecraft/world/entity/player/Player.java:
 *   jumpFromGround; lines 1435-1443.
 *   Source SHA-256: 873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac.
 * 1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   getJumpPower; lines 2057-2080.
 *   createLivingAttributes; lines 292-306.
 *   Source SHA-256: c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2.
 * 1.20.6/mojmap/net/minecraft/world/entity/ai/attributes/Attributes.java:
 *   class declaration / field initialization; lines 45-47.
 *   Source SHA-256: 7e30d87c7a58b14d0052d2f9f7319d997b49ae7d025579cd762d28e845b2e82e.
 * 1.20.6/mojmap/net/minecraft/world/entity/player/Player.java:
 *   createAttributes; lines 221-230.
 *   Source SHA-256: 785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2.
 * No exact independently accepted source binding is established for this operation.
 * Remaining source/applicability gates:
 *   Archived-to-canonical source identity for these checked secondary citations: 1.20.6/mojmap/net/minecraft/world/entity/player/Player.java. Neither a matching archived full-file hash nor an archived matching canonical source-manifest digest was resolved for them.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_19_4)
public final class DoubleJumpBoostAddition implements JumpPowerHook {
    @Override
    public double jumpPower(LivingEntity entity, double vanilla) {
        float jumpPower = 0.42F
            * ((JumpPowerAccess) entity).legacyparkour$getBlockJumpFactor();
        float jumpBoostPower = entity.getJumpBoostPower();
        return (double) jumpPower + (double) jumpBoostPower;
    }
}
