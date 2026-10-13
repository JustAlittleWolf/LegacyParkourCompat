package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.WaterGravityHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.WaterSprintSlowdownHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   public void moveRelative(float forwards, float sideways, float upwards) {; lines 1425-1599.
 *   Source SHA-256: 190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6.
 * 1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   public void moveRelative(float forwards, float sideways, float upwards) {; lines 1478-1669.
 *   Source SHA-256: bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c.
 * No exact independently accepted source binding is established for this operation.
 * Remaining source/applicability gates:
 *   Later applicability gate: exact introduction of campaign-F-WATER-GRAVITY is unknown within (1.12.2, 1.13.2]
 *   Exact independent source verdict/snapshot identity for F-WATER-GRAVITY
 *   Later applicability gate: exact introduction of campaign-F-WATER-SPRINT-TRAVEL is unknown within (1.12.2, 1.13.2]
 *   Exact independent source verdict/snapshot identity for F-WATER-SPRINT-TRAVEL
 *   Exact independent source verdict/snapshot identity for water-gravity
 *   Exact independent source verdict/snapshot identity for water-sprint-travel
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class WaterTravel implements WaterSprintSlowdownHook, WaterGravityHook {
    @Override
    public float sprintSlowdown(Player player, float vanilla) {
        return player.isSprinting() ? 0.8F : vanilla;
    }

    @Override
    public Vec3 gravityAdjustedMovement(
        Player player,
        double baseGravity,
        boolean falling,
        Vec3 movement,
        Vec3 vanilla
    ) {
        if (baseGravity == 0.0D) {
            return movement;
        }
        return new Vec3(movement.x, movement.y - 0.02D, movement.z);
    }
}
