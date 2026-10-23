package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.WaterGravityHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.WaterSprintSlowdownHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import me.wolfii.legacyparkourcompat.mechanic.water.WaterContext;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   public void moveRelative(float forwards, float sideways, float upwards).
 * 1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   public void moveRelative(float forwards, float sideways, float upwards).
 * Downward acceleration: the complete 1.12/1.12.1/1.12.2 Feather LivingEntity
 * moveRelative water branch subtracts double 0.02 after float 0.8F drag whenever
 * gravity is enabled. 1.13 Feather moveRelative introduces gravity/16 and the
 * vertical snap; 26.2 unobfuscated LivingEntity.travelInWater retains it.
 * The shared ordinary-water context leaves swimming and modern effects native.
 * Sprint slowdown remains the existing independent operation; its earlier
 * source/applicability evidence is unchanged by this water-gravity integration.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class WaterTravel implements WaterSprintSlowdownHook, WaterGravityHook {
    @Override
    public float sprintSlowdown(Player player, float vanilla) {
        return !player.isSwimming() && player.isSprinting() ? 0.8F : vanilla;
    }

    @Override
    public Vec3 gravityAdjustedMovement(
        Player player,
        double baseGravity,
        boolean falling,
        Vec3 movement,
        Vec3 vanilla
    ) {
        if (!((WaterContext)player).legacyparkourcompat$hasSupportedWaterContext()) {
            return vanilla;
        }
        if (player.isNoGravity()) {
            return movement;
        }
        return new Vec3(movement.x, movement.y - 0.02D, movement.z);
    }
}
